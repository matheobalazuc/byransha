package byransha.view;

import java.io.PrintWriter;

import byransha.DB;
import byransha.TextView;
import byransha.User;

public class ModelDOTView extends TextView<DB> {

	@Override
	protected String contentType() {
		return "text/plain";
	}

	@Override
	public String name() {
		return "Model (DOT)";
	}

	@Override
	protected void content(DB db, User u, PrintWriter pw) {
		pw.println("digraph G {");

		DB.defaultDB.forEachNode(n -> pw
				.println("\t " + id(n.getClass()) + " [shape=box, label=\"" + n.getClass().getSimpleName() + "\"];"));

		DB.defaultDB.forEachNode(n -> n.forEachOutField(f -> pw.println(
				"\t " + id(n.getClass()) + " -> " + id(f.getType().getClass()) + " [label=\"" + f.getName() + "\"];")));

		pw.println("}");
	}

	public static int id(Class n) {
		return Math.abs(n.hashCode());
	}

}
