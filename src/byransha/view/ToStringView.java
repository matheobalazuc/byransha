package byransha.view;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import byransha.GOBMNode;
import byransha.User;
import byransha.View;

public class ToStringView extends View<GOBMNode> {

	@Override
	public JsonNode toJSONNode(GOBMNode nn, User u) {
		var n = new ObjectNode(null);
		n.set("toString", new TextNode(nn.toString()));
		return n;
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