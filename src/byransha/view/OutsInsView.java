package byransha.view;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import byransha.GOBMNode;
import byransha.User;
import byransha.View;

public class OutsInsView extends View<GOBMNode> {

	@Override
	public JsonNode toJSONNode(GOBMNode n, User u) {
		var r = new ObjectNode(null);

		n.forEachOut((name, o) -> {
			r.set(name, new TextNode(o.getClass().getName()));
		});
		
		n.forEachIn((name, o) -> {
			r.set(name, new TextNode(o.getClass().getName()));
		});

		return r;
	}

	@Override
	protected String contentType() {
		return "";
	}

	@Override
	protected Object content() {
		return null;
	}
}
