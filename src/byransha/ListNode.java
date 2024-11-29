package byransha;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ListNode<N extends GOBMNode> extends GOBMNode {
	private final List<N> l = new ArrayList<>();

	@Override
	public void forEachOut(BiConsumer<String, GOBMNode> consumer) {
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

}
