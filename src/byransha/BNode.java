package byransha;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import byransha.DB.Ref;
import toools.reflect.Clazz;

public class BNode {
	public String comment;
	private List<Ref> refs;
	static int idCount = 0;
	private int id = idCount++;

	public List<Ref> refs() {
		return refs == null ? DB.defaultDB.findRefsTO(this) : refs;
	}

	public void forEachOutNodeField(Consumer<Field> consumer) {
		for (var c : Clazz.bfs(getClass())) {
			for (var f : c.getDeclaredFields()) {
				if (BNode.class.isAssignableFrom(f.getType())) {
					try {
						f.setAccessible(true);
						consumer.accept(f);
					} catch (IllegalArgumentException err) {
						throw new IllegalStateException(err);
					}
				}
			}
		}
	}

	public void forEachOut(BiConsumer<String, BNode> consumer) {
		forEachOutNodeField(f -> {
			try {
				var outNode = (BNode) f.get(this);

				if (outNode != null) {
					consumer.accept(f.getName(), outNode);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new IllegalStateException(e);
			}
		});
	}

	public void forEachIn(BiConsumer<String, BNode> consumer) {
		refs().forEach(r -> consumer.accept(r.role, r.c));
	}

	public boolean isLeaf() {
		return outDegree() == 0;
	}

	public void bfs(Consumer<BNode> consumer) {
		search(consumer, q -> q.remove(0));
	}

	public void dfs(Consumer<BNode> consumer) {
		search(consumer, q -> q.remove(q.size() - 1));
	}

	private void search(Consumer<BNode> consumer, Function<List<BNode>, BNode> producer) {
		List<BNode> q = new ArrayList<>();
		BNode c = this;
		q.add(c);
		var visited = new HashSet<BNode>();

		while (!q.isEmpty()) {
			c = producer.apply(q);
			consumer.accept(c);
			c.forEachOut((f, n) -> {
				if (!visited.contains(n)) {
					visited.add(n);
					q.add(n);
				}
			});
		}
	}

	public List<BNode> bfs2list() {
		List<BNode> r = new ArrayList<>();
		bfs(n -> r.add(n));
		return r;
	}

	public LinkedHashMap<String, BNode> outs() {
		var m = new LinkedHashMap<String, BNode>();
		forEachOut((name, o) -> m.put(name, o));
		return m;
	}

	public int outDegree() {
		AtomicInteger i = new AtomicInteger(0);
		forEachOut((name, prop) -> i.incrementAndGet());
		return i.get();
	}

	public List<SearchResult> search(String query) {
		var r = new ArrayList<SearchResult>();
		bfs(n -> r.add(new SearchResult(query, n, n.distanceToSearchString(query))));
		Collections.sort(r);
		return r;
	}

	public int distanceToSearchString(String s) {
		return 1;
	}

	public boolean canSee(User user) {
		return true;
	}

	public boolean canEdit(User user) {
		return user.isAdmin();
	}

	public List<View> compliantViews() {
		// gets only the views that can be used for this class of node
		return View.views.stream().filter(v -> matches(v)).toList();
	}

	public boolean matches(View<?> v) {
		return v.getTargetNodeType().isAssignableFrom(getClass());
	}

	@Override
	public String toString() {
		return getClass().getName() + "@" + id();
	}

	public void saveOuts(Consumer<File> writingFiles) {
		var outD = outsDirectory();

		if (!outD.exists()) {
			writingFiles.accept(outD);
			outD.mkdirs();
		}

		forEachOut((name, outNode) -> {
			try {
				var symlink = new File(outD, name + "@" + outNode.id());
				writingFiles.accept(symlink);
				Files.createSymbolicLink(symlink.toPath(), outNode.directory().toPath());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public void saveIns(Consumer<File> writingFiles) {
		var inD = new File(directory(), "ins");

		if (!inD.exists()) {
			writingFiles.accept(inD);
			inD.mkdirs();
		}

		forEachIn((name, inNode) -> {
			try {
				var symlink = new File(inD, name);
				writingFiles.accept(symlink);
				System.err.println(symlink.toPath());
				System.err.println(inNode.directory().toPath());
				Files.createSymbolicLink(symlink.toPath(), inNode.directory().toPath());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public File directory() {
		if (DB.defaultDB == null)
			return null;
		return new File(DB.defaultDB.directory, getClass().getName() + "/" + id());
	}

	public File outsDirectory() {
		return new File(directory(), "outs");
	}

	public final int id() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	@Override
	public final int hashCode() {
		return System.identityHashCode(this);
	}

	@Override
	public final boolean equals(Object obj) {
		return this.hashCode() == ((BNode) obj).hashCode();
	}

}
