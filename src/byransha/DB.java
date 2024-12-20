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
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.net.ssl.SSLSession;

import com.fasterxml.jackson.databind.JsonNode;

import byransha.graph.BGraph;
import byransha.web.HTMLView;
import byransha.web.JSONView;
import byransha.web.TechnicalView;
import byransha.web.View;
import byransha.web.view.ModelDOTView;
import byransha.web.view.ModelGraphivzDotDOT_JSON;
import byransha.web.view.ModelGraphivzDotJSON0;
import byransha.web.view.ModelGraphivzDotXDOT_JSON;
import byransha.web.view.ModelGraphivzSVGView;
import toools.reflect.Clazz;

public class DB extends BNode {
	public static DB instance = new DB(new File(System.getProperty("user.home") + "/." + DB.class.getPackageName()));

	public static Consumer<File> sysoutPrinter = f -> System.out.println("writing " + f.getAbsolutePath());

	public final File directory;
	public ListNode<BNode> nodes = new ListNode<>();
	public BNode root;

	static {
		View.views.add(new DBView());
		View.views.add(new ModelDOTView());
		View.views.add(new ModelGraphivzSVGView());
		View.views.add(new ModelGraphivzDotDOT_JSON());
		View.views.add(new ModelGraphivzDotJSON0());
		View.views.add(new ModelGraphivzDotDOT_JSON());
		View.views.add(new ModelGraphivzDotXDOT_JSON());
		View.views.add(new GraphView());
	}

	public DB(File directory) {
		this.directory = directory;
		accept(root = this);
		accept(new User("user", "test", false));
		accept(new User("admin", "test", true));
	}

	public static class Ref {
		String role;
		BNode c;

		public Ref(String role, BNode c) {
			this.role = role;
			this.c = c;
		}

		@Override
		public String toString() {
			return c + "." + role;
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
				String targetClassName = targetFile.getName(targetFile.getNameCount() - 2).toString();
				var targetNodeClass = (Class<? extends BNode>) Class.forName(targetClassName);
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
		nodes.forEachOut((name, node) -> h.accept(node));
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

	public void accept(BNode n) {
		nodes.add(n);

		if (root == null) {
			root = n;
		}
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
		for (var n : nodes.l) {
			if (n.id() == id) {
				return n;
			}
		}

		return null;
	}

	public List<User> users() {
		return (List<User>) (List) nodes.l.stream().filter(n -> n instanceof User).toList();
	}

	public User findUser(SSLSession s) {
		for (var n : nodes.l) {
			if (n instanceof User u && u.session != null && Arrays.equals(u.session.getId(), s.getId())) {
				return u;
			}
		}

		return null;
	}

	public static class DBView extends HTMLView<DB> implements TechnicalView {
		@Override
		protected void print(DB node, User user, PrintWriter pw) {
			pw.println("<ul>");
			pw.println("<li>" + DB.instance.countNodes() + " nodes");
			pw.println(
					"<li>Node classes: <ul>" + DB.instance.nodes.l.stream().map(n -> "<li>" + n.getClass()).toList());
			pw.println("</ul>");
			var users = DB.instance.users();
			pw.println("<li>" + users.size() + " users: "
					+ users.stream().map(u -> u.name.get() + (u.isAdmin() ? "*" : "")).toList());
			pw.println("</ul>");
		}

	}

	public static class GraphView extends JSONView<DB> {

		@Override
		protected JsonNode jsonData(DB db, User u) {
			var g = new BGraph();

			db.forEachNode(v -> {
				g.addVertex(v.toVertex());
				v.forEachOut((s, o) -> {
					var a = g.newArc(g.ensureHasVertex(v), g.ensureHasVertex(o));
					a.label = s;
				});
			});

			return g.toNivoJSON();
		}

		@Override
		protected String jsonDialect() {
			return "nivo";
		}
	}
}
