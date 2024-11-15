package byransha;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import toools.reflect.Clazz;

public abstract class FieldedNode extends GOBMNode {

	private void forEachOutField(Consumer<Field> consumer) {
		for (var c : Clazz.bfs(getClass())) {
			for (var f : c.getDeclaredFields()) {
				if (GOBMNode.class.isAssignableFrom(f.getType())) {
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

	@Override
	public void forEachOut(BiConsumer<String, GOBMNode> consumer) {
		forEachOutField(f -> {
			try {
				var outNode = (GOBMNode) f.get(this);

				if (outNode != null) {
					consumer.accept(f.getName(), outNode);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new IllegalStateException(e);
			}
		});
	}
}
