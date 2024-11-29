package byransha.view;

import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import byransha.DB;
import byransha.GOBMNode;
import byransha.ListNode;
import byransha.TextView;
import byransha.User;
import byransha.ValuedNode;

public class ModelDOTView extends TextView<DB> {

	@Override
	protected String contentType() {
		return "text/plain";
	}

	@Override
	public String name() {
		return "Model (DOT)";
	}

	HashSet<String> rels = new HashSet<>();

	@Override
	protected void content(DB db, User u, PrintWriter pw) {
		pw.println("digraph G {");

		var class_attrs = new HashMap<Class<?>, Set<String>>();

		DB.defaultDB.forEachNode(n -> {
			List<Class<GOBMNode>> stack = new ArrayList<>();

			for (Class c = n.getClass(); c != null && c != GOBMNode.class
					&& !ValuedNode.class.isAssignableFrom(c); c = c.getSuperclass()) {
				if (!stack.isEmpty()) {
					newRel(pw, stack.getLast(), c, Map.of("arrowhead", "empty"), true);
				}

				stack.add(c);

				if (!class_attrs.containsKey(c)) {
					class_attrs.put(c, new HashSet<>());
				}

				for (var f : c.getDeclaredFields()) {
					if (!GOBMNode.class.isAssignableFrom(f.getType()))
						continue;

					boolean list = ListNode.class.isAssignableFrom(f.getType());
					var target = list ? (Class) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0]
							: f.getType();

					if (ValuedNode.class.isAssignableFrom(target)) {
						class_attrs.get(c).add((list ? "*" : "") + f.getName());
					} else {
						var label = f.getName().equalsIgnoreCase(target.getSimpleName()) ? "" : f.getName();
						var m = new HashMap<>(Map.of("label", label));

						if (list) {
							m.put("taillabel", "0..*");
						}

						newRel(pw, c, target, m, false);
					}
				}
			}
		});

		class_attrs.entrySet().forEach(n -> {
			var clazz = n.getKey();
			var attrs = n.getValue();
			pw.print("\t " + id(clazz) + " [shape=record, label=\"{" + clazz.getSimpleName());

			if (!attrs.isEmpty()) {
				pw.print("|");
				attrs.forEach(a -> pw.print(a + "\\l"));
			}
			pw.print("}\"];");
		});
		pw.println("}");
	}

	private void newRel(PrintWriter pw, Class<GOBMNode> a, Class b, Map<String, String> attrs, boolean inheritance) {
		String relID = id(a) + " -> " + id(b);

		if (!rels.contains(relID)) {
			pw.print("\t " + id(a) + " -> " + id(b) + "[");
			attrs.entrySet().forEach(e -> pw.print(e.getKey() + "=" + '"' + e.getValue() + '"' + ','));
			pw.print("arrowhead=" + (inheritance ? "empty" : "none") + "];");

			rels.add(relID);
		}
	}

	public static int id(Class n) {
//		System.err.println(n);
		return Math.abs(n.hashCode());
	}

}
