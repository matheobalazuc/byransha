package byransha;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import toools.SizeOf;

public class MapNode<N extends GOBMNode> extends GOBMNode {
	private final Map<String, N> l = new HashMap<>();

	@Override
	public void forEachOut(BiConsumer<String, GOBMNode> consumer) {
		for (var e : l.entrySet()) {
			consumer.accept(e.getKey(), e.getValue());
		}
	}

	public void add(String key, N n) {
		l.put(key, n);
	}

	public void remove(String key) {
		l.remove(key);
	}

}
