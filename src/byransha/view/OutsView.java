package byransha.view;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import byransha.GOBMNode;
import byransha.User;
import byransha.View;

public abstract class OutsView extends View<GOBMNode> {

	@Override
	public JsonNode toJSONNode(GOBMNode n, User u) {
		var r = new ObjectNode(null);

		n.forEachOut((name, o) -> {
			r.set(name, new TextNode(o.getClass().getName()));
		});

		return r;
	}
}
