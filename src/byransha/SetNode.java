package byransha;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

import toools.SizeOf;

public class SetNode<N extends GOBMNode> extends GOBMNode {
	private final Set<N> l = new HashSet<>();

	@Override
	public void forEachOut(BiConsumer<String, GOBMNode> consumer) {
		for (var e : l) {
			consumer.accept(e.id(), e);
		}
	}

	public void add(N n) {
		l.add(n);
	}

	public void remove(N p) {
		l.remove(p);
	}


	@Override
	public long sizeOf() {
		return super.sizeOf() + l.size() * 8 ;
	}

}
