package byransha;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class ListNode<N extends BNode> extends BNode {
	 public ListNode(BBGraph db) {
		super(db);
	}

	@Override
	public String getDescription() {
		return "ListNode containing " + l.size() + " elements.";
	}

	public final List<N> l = new ArrayList<>();

	@Override
	public void forEachOut(BiConsumer<String, BNode> consumer) {
		int i = 0;

		for (var e : l) {
			consumer.accept(i++ + ". " + e.id(), e);
		}
	}

	public void add(N n) {
		l.add(n);
	}

	public void remove(N p) {
		l.remove(p);
	}

	public N get(int i) {
		return l.get(i);
	}

	public List<N> elements() {
		return List.copyOf(l);
	}

	public int size() {
		return l.size();
	}

	public BNode random() {
		return l.get(new Random().nextInt(l.size()));
	}

	public Object[] getAll() {
		return l.toArray();
	}

}
