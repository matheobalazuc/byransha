package byransha.view;

import java.io.PrintWriter;

import byransha.BNode;
import byransha.TextView;
import byransha.User;

public class ToStringView extends TextView<BNode> implements DevelopmentView {

	@Override
	public String contentType() {
		return "text/plain";
	}

	@Override
	public String name() {
		return "toString()";
	}

	@Override
	protected void content(BNode node, User u, PrintWriter pw) {
		pw.print(node.toString());
	}
	
}