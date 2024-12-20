package byransha.web.view;

import java.io.PrintWriter;

import byransha.BNode;
import byransha.User;
import byransha.web.HTMLView;

public class OutsInsView extends HTMLView<BNode> {
	public OutsInsView() {
		sendContentByDefault = true;
	}

	@Override
	protected void print(BNode n, User u, PrintWriter pw) {
		pw.println("Outs:<ul>");
		n.forEachOut((name, o) -> pw.println("<li>" + linkTo(o, name)));
		pw.println("</ul>");

		pw.println("Ins:<ul>");
		n.forEachIn((name, o) -> pw.println("<li>" + linkTo(o, name)));
		pw.println("</ul>");

	}

}
