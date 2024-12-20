package byransha.web.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import byransha.BNode;
import byransha.User;
import byransha.web.JSONView;
import byransha.web.View;

public class Jump extends JSONView<BNode> {

	@Override
	protected JsonNode jsonData(BNode node, User user) throws Throwable {
		user.stack.push(node);

		var r = new ObjectNode(null);
		r.set("id", new TextNode("" + node.id()));
		r.set("class", new TextNode(node.getClass().getName()));
		r.set("name", new TextNode(node.toString()));
		r.set("can read", new TextNode("" + node.canSee(user)));
		r.set("can write", new TextNode("" + node.canSee(user)));

		ArrayNode viewsNode = new ArrayNode(null);
		var views = node.compliantViews();

		for (var v : views) {
			var n = new ObjectNode(null);
			n.set("label", new TextNode(name()));
			n.set("target", new TextNode(v.getTargetNodeType().getName()));
			n.set("development", new TextNode("" + v.isDevelopmentView()));
			n.set("technical", new TextNode("" + v.isTechnicalView()));

			if (v.sendContentByDefault) {
				n.set("content", v.content(node, user).toJson());
			}

			viewsNode.add(n);
		}

		r.set("views", viewsNode);

		return r;
	}

	@Override
	protected String jsonDialect() {
		return "jump";
	}

}
