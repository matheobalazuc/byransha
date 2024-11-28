package byransha.view;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import byransha.GOBMNode;
import byransha.User;
import byransha.View;

public class OutsInsView extends View<GOBMNode> {

	@Override
	protected String contentType() {
		return "text/json";
	}

	@Override
	protected byte[] content(GOBMNode n, User u) {
		var r = new ObjectNode(null);

		n.forEachOut((name, o) -> {
			r.set(name, new TextNode(o.getClass().getName()));
		});

		n.forEachIn((name, o) -> {
			r.set(name, new TextNode(o.getClass().getName()));
		});

		return r.toString().getBytes();
	}
	

	@Override
	public String name() {
		return "navigation";
	}

	@Override
	protected Object content() {
		return null;
	}
}
