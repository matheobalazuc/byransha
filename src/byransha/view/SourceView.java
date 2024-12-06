package byransha.view;

import java.io.PrintWriter;

import byransha.BNode;
import byransha.TextView;
import byransha.User;
import toools.src.Source;

public class SourceView extends TextView<BNode> {

	@Override
	public String contentType() {
		return "text/java";
	}

	@Override
	protected void content(BNode node, User u, PrintWriter pw) {
		pw.print(Source.getClassSourceCode(node.getClass()));
	}
}