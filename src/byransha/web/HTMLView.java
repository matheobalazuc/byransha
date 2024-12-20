package byransha.web;

import byransha.BNode;

public abstract class HTMLView<E extends BNode> extends TextView<E> {

	@Override
	public String textMimeType() {
		return "text/html";
	}

	protected String linkTo(BNode n, String label) {
		return "<a href='?node=" + n.id() + "'>" + label + "</a>";
	}

}
