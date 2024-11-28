package byransha.view;

import java.io.PrintWriter;

import byransha.GOBMNode;
import byransha.TextView;
import byransha.User;

final public class BasicView extends TextView<GOBMNode> {

	@Override
	protected String contentType() {
		return "text/json";
	}

	@Override
	public String name() {
		return "basic info";
	}

	@Override
	protected void content(GOBMNode node, User u, PrintWriter pw) {
		pw.println("id: " + node.id());
		pw.println("sizeOf: " + node.sizeOf());
		pw.println("class: " + node.getClass().getName());
	}

}