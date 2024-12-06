package byransha;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.net.ssl.SSLSession;

import byransha.view.DBView;
import byransha.view.ModelDOTView;
import byransha.view.ModelGraphivzSVGView;
import byransha.view.ModelJSONDOTView;
import toools.reflect.Clazz;

public class DB extends BNode {
	public static DB defaultDB = new DB(new File(System.getProperty("user.home") + "/." + DB.class.getPackageName()));

	public static Consumer<File> sysoutPrinter = f -> System.out.println("writing " + f.getAbsolutePath());

	public final File directory;
	public ListNode<BNode> nodes = new ListNode<>();
	public BNode root;

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

	public User findUser(SSLSession sslSession) {
		for (var n : nodes.l) {
			if (n instanceof User u && u.session == sslSession) {
				return u;
			}
		}

		return null;
	}

	@Override
	public void views(List<View> l) {
		super.views(l);
		l.add(new DBView());
		l.add(new ModelDOTView());
		l.add(new ModelGraphivzSVGView());
		l.add(new ModelJSONDOTView());
	}

}
