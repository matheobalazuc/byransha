package byransha.view;

import java.io.PrintWriter;

import byransha.BNode;
import byransha.HTMLView;
import byransha.User;

public class OutsInsView extends HTMLView<BNode> {
	public OutsInsView() {
		sendContentByDefault = true;
	}

	@Override
	public String name() {
		return "navigation";
	}

	@Override
	protected void content(BNode n, User u, PrintWriter pw) {
		pw.println("Outs:<ul>");
		n.forEachOut((name, o) -> pw.println("<li>" + linkTo(o, name)));
		pw.println("</ul>");

		pw.println("Ins:<ul>");
		n.forEachIn((name, o) -> pw.println("<li>" + linkTo(o, name)));
		pw.println("</ul>");

	}
	
}
