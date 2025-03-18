package byransha;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.net.ssl.SSLSession;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.graph.BGraph;
import byransha.web.NodeEndpoint;
import byransha.web.EndpointJsonResponse;
import byransha.web.EndpointJsonResponse.dialects;
import byransha.web.EndpointResponse;
import byransha.web.HTMLView;
import byransha.web.TechnicalView;
import byransha.web.WebServer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import toools.reflect.Clazz;

public class BBGraph extends BNode {
	public static Consumer<File> sysoutPrinter = f -> System.out.println("writing " + f.getAbsolutePath());
	public final File directory;
	public final List<BNode> nodes;
	private Map<Class<? extends BNode>, List<BNode>> byClass;
	private Int2ObjectMap<BNode> byID;// = new Int2ObjectOpenHashMap<>();
	int idCount = 1;

	public BBGraph(File directory) {
		super(null);
		nodes = new ArrayList<BNode>();
		accept(this);
		new User(this, "user", "test");
		new User(this, "admin", "test");
		this.directory = directory;
	}

	public static class Ref {
		final String role;
		final BNode source;

		public Ref(String role, BNode c) {
			this.role = role;
			this.source = c;
		}

		@Override
		public String toString() {
			return source + "." + role;
		}
	}

	public List<Ref> findRefsTO(BNode searchedNode) {
		var r = new ArrayList<Ref>();

		forEachNode(n -> {
			n.forEachOut((role, outNode) -> {
				if (outNode == searchedNode) {
					r.add(new Ref(role, n));
				}
			});
		});

		return r;
	}

	public void load(Consumer<BNode> newNodeInstantiated, BiConsumer<BNode, String> setRelation) {
		intantiateNodes(newNodeInstantiated);
		forEachNode(n -> loadOuts(n, setRelation));
	}

	private void intantiateNodes(Consumer<BNode> newNodeInstantiated) {
		for (File classDir : directory.listFiles()) {
			String className = classDir.getName();
			var nodeClass = (Class<? extends BNode>) Clazz.findClassOrFail(className);

			for (File nodeDir : classDir.listFiles()) {
				try {
					BNode node = nodeClass.getConstructor().newInstance();
					node.setID(Integer.valueOf(nodeDir.getName()));
					nodes.add(node);
					newNodeInstantiated.accept(node);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException err) {
					throw new RuntimeException(err);
				}
			}
		}
	}

	private void loadOuts(BNode node, BiConsumer<BNode, String> setRelation) {
		var d = node.outsDirectory();

		if (!d.exists())
			return;

		try {
			for (var symlink : d.listFiles()) {
				Path targetFile = Files.readSymbolicLink(symlink.toPath());
				String relationName = targetFile.getFileName().toString();
//				String targetClassName = targetFile.getName(targetFile.getNameCount() - 2).toString();
//				var targetNodeClass = (Class<? extends BNode>) Class.forName(targetClassName);
				var fn = targetFile.getFileName().toString();
				int id = Integer.valueOf(fn.substring(fn.indexOf("@") + 1));
				BNode targetNode = findByID(id);
				node.getClass().getField(relationName).set(node, targetNode);
				setRelation.accept(node, relationName);
			}
		} catch (Exception err) {
			throw new RuntimeException(err);
		}
	}

	public void forEachNode(Consumer<BNode> h) {
		for (var n : nodes) {
			h.accept(n);
		}
	}

	public void saveAll(Consumer<File> writingFiles) throws IOException {
		forEachNode(n -> {
			if (n instanceof ValuedNode<?> vn) {
				vn.saveValue(writingFiles);
			}
		});

		forEachNode(n -> n.saveOuts(writingFiles));
		forEachNode(n -> n.saveIns(writingFiles));
	}

	public long countNodes() {
		var r = new AtomicLong();
		forEachNode(n -> {
			r.incrementAndGet();
		});
		return r.get();
	}

	void accept(BNode n) {
		var already = findByID(n.id());

		if (already != null)
			throw new IllegalStateException("can't add node " + n + " with id " + n.id() + " because of : " + already);

		nodes.add(n);

		if (byClass != null) {
			var s = byClass.get(n.getClass());

			if (s == null) {
				s = byClass.put(n.getClass(), new ArrayList<>());
			}

			s.add(n);
		}

		if (byID != null) {
			byID.put(n.id(), n);
		}
	}

	public BNode root() {
		return this;
	}

	public void delete() {
		delete(directory);
	}

	private void delete(File d) {
		if (d.isDirectory()) {
			for (var c : d.listFiles()) {
				delete(c);
			}
		}

		System.out.println("delete " + d);
		d.delete();
	}

	public BNode findByID(int id) {
		if (byID != null) {
			return byID.get(id);
		} else {
			for (var n : nodes) {
				if (n.id() == id) {
					return n;
				}
			}
		}

		return null;
	}

	public <C extends BNode> C find(Class<C> nodeClass, Predicate<C> p) {
		if (byClass != null) {
			for (var s : byClass.entrySet()) {
				if (s.getKey().isAssignableFrom(nodeClass)) {
					for (var node : s.getValue()) {
						C nn = (C) node;

						if (p.test(nn)) {
							return nn;
						}
					}
				}
			}
		} else {
			for (var node : nodes) {
				if (nodeClass.isAssignableFrom(node.getClass())) {
					C nn = (C) node;

					if (p.test(nn)) {
						return nn;
					}
				}
			}
		}

		return null;
	}

	public List<User> users() {
		return (List<User>) (List) nodes.stream().filter(n -> n instanceof User).toList();
	}

	public User findUser(SSLSession s) {
		return find(User.class, u -> u.session != null && Arrays.equals(u.session.getId(), s.getId()));
	}

	public static class DBView extends HTMLView<BBGraph> implements TechnicalView {

		public DBView(BBGraph g) {
			super(g);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void print(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange, BBGraph node,
				PrintWriter pw) throws Throwable {
			pw.println("<ul>");
			pw.println("<li>" + graph.countNodes() + " nodes");
			pw.println("<li>Node classes: <ul>" + graph.nodes.stream().map(n -> "<li>" + n.getClass()).toList());
			pw.println("</ul>");
			var users = graph.users();
			pw.println("<li>" + users.size() + " users: " + users.stream().map(u -> u.name.get()).toList());
			pw.println("</ul>");
		}
	}

	public static class GraphView extends NodeEndpoint<BBGraph> {

		public GraphView(BBGraph db) {
			super(db);
		}

		@Override
		public EndpointResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange,
				BBGraph db) {
			var g = new BGraph();

			db.forEachNode(v -> {
				g.addVertex(v.toVertex());
				v.forEachOut((s, o) -> {
					var a = g.newArc(g.ensureHasVertex(v), g.ensureHasVertex(o));
					a.label = s;
				});
			});
			return new EndpointJsonResponse(g.toNivoJSON(), dialects.nivoNetwork);
		}
	}

}
