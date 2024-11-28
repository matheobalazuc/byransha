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

import toools.reflect.Clazz;

public class DB extends GOBMNode {
	public static DB defaultDB = new DB(new File(System.getProperty("user.home") + "/." + DB.class.getPackageName()));

	public static Consumer<File> sysoutPrinter = f -> System.out.println("writing " + f.getAbsolutePath());

	public final File directory;
	public ListNode<GOBMNode> nodes = new ListNode<>();
	public GOBMNode root;

	public DB(File directory) {
		this.directory = directory;
		accept(root = this);
	}

	public static class Ref {
		String role;
		GOBMNode c;

		public Ref(String role, GOBMNode c) {
			this.role = role;
			this.c = c;
		}
	}

	public List<Ref> findRefsTO(GOBMNode searchedNode) {
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

	public void load(Consumer<GOBMNode> newNodeInstantiated, BiConsumer<GOBMNode, String> setRelation) {
		intantiateNodes(newNodeInstantiated);
		forEachNode(n -> loadOuts(n, setRelation));
	}

	private void intantiateNodes(Consumer<GOBMNode> newNodeInstantiated) {
		for (File classDir : directory.listFiles()) {
			String className = classDir.getName();
			var nodeClass = (Class<? extends GOBMNode>) Clazz.findClassOrFail(className);

			for (File nodeDir : classDir.listFiles()) {
				try {
					GOBMNode node = nodeClass.getConstructor().newInstance();
					node.setID(nodeDir.getName());
					nodes.add(node);
					newNodeInstantiated.accept(node);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException err) {
					throw new RuntimeException(err);
				}
			}
		}
	}

	private void loadOuts(GOBMNode node, BiConsumer<GOBMNode, String> setRelation) {
		var d = node.outsDirectory();

		if (!d.exists())
			return;

		try {
			for (var symlink : d.listFiles()) {
				Path targetFile = Files.readSymbolicLink(symlink.toPath());
				String relationName = targetFile.getFileName().toString();
				String targetClassName = targetFile.getName(targetFile.getNameCount() - 2).toString();
				var targetNodeClass = (Class<? extends GOBMNode>) Class.forName(targetClassName);
				String id = targetFile.getFileName().toString();
				GOBMNode targetNode = findByID(id);
				node.getClass().getField(relationName).set(node, targetNode);
				setRelation.accept(node, relationName);
			}
		} catch (Exception err) {
			throw new RuntimeException(err);
		}
	}

	public void forEachNode(Consumer<GOBMNode> h) {
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

	public void accept(GOBMNode n) {
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

	public GOBMNode findByID(String id) {
		class R {
			GOBMNode r;
		}

		R r = new R();

		forEachNode(n -> {
			if (n.id().equals(id)) {
				r.r = n;
			}
		});

		return r.r;
	}

}
