package byransha.view;

import java.io.PrintWriter;

import byransha.BNode;
import byransha.TextView;
import byransha.User;

public class OutsInsView extends TextView<BNode> {
	public OutsInsView() {
		sendContentByDefault = true;
	}

	@Override
	public String contentType() {
		return "text/html";
	}

	@Override
	public String name() {
		return "navigation";
	}

	@Override
	protected void content(BNode n, User u, PrintWriter pw) {
		pw.println("Outs:<ul>");
		n.forEachOut((name, o) -> pw.println("<li><a href='?node=" + o.id() + "'>" + name + "</a>"));
		pw.println("</ul>");

		pw.println("Ins:<ul>");
		n.forEachIn((name, o) -> pw.println("<li><a href='?node=" + o.id() + "'>" + name + "</a>"));
		pw.println("</ul>");

	}

}
