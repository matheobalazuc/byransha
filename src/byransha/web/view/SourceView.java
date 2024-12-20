package byransha.web.view;

import java.io.PrintWriter;

import byransha.BNode;
import byransha.User;
import byransha.web.DevelopmentView;
import byransha.web.TextView;
import toools.src.Source;

public class SourceView extends TextView<BNode> implements DevelopmentView {

	@Override
	public String textMimeType() {
		return "text/java";
	}

	@Override
	protected void print(BNode node, User u, PrintWriter pw) {
		pw.print(Source.getClassSourceCode(node.getClass()));
	}

}