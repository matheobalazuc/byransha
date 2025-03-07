package byransha.web;

import byransha.BNode;
import byransha.BBGraph;

public abstract class HTMLView<E extends BNode> extends TextOutputEndpoint<E> {

	public HTMLView(BBGraph g) {
		super(g);
	}

	@Override
	public String textMimeType() {
		return "text/html";
	}

	protected String linkTo(BNode n, String label) {
		return "<a href='?endpoint=jump&nodeID=" + n.id() + "'>" + label + "</a>";
	}
}
