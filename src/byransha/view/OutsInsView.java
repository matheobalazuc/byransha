package byransha.view;

import java.io.PrintWriter;

import byransha.GOBMNode;
import byransha.TextView;
import byransha.User;

public class OutsInsView extends TextView<GOBMNode> {

	@Override
	protected String contentType() {
		return "text/html";
	}

	@Override
	public String name() {
		return "navigation";
	}

	@Override
	protected void content(GOBMNode n, User u, PrintWriter pw) {
		pw.println("Outs:<ul>");
		n.forEachOut((name, o) -> pw.println("<li><a href='?node=" + name + "'>next</a>"));
		pw.println("</ul>");

		pw.println("Ins:<ul>");
		n.forEachIn((name, o) -> pw.println("<li><a href='?node=" + name + "'>next</a>"));
		pw.println("</ul>");

	}
}
