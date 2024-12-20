package byransha.web.view;

import java.io.PrintWriter;

import byransha.BNode;
import byransha.User;
import byransha.web.DevelopmentView;
import byransha.web.TextView;

public class ToStringView extends TextView<BNode> implements DevelopmentView {

	@Override
	public String textMimeType() {
		return "text/plain";
	}

	@Override
	protected void print(BNode node, User u, PrintWriter pw) {
		pw.print(node.toString());
	}

}