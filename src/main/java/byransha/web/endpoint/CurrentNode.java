package byransha.web.endpoint;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.BNode;
import byransha.User;
import byransha.web.EndpointJsonResponse;
import byransha.web.NodeEndpoint;
import byransha.web.View;
import byransha.web.WebServer;

public class CurrentNode extends NodeEndpoint<BNode> {

	public CurrentNode(BBGraph db) {
		super(db);
	}

	@Override
	public EndpointJsonResponse exec(ObjectNode inputJson, User user, WebServer webServer, HttpsExchange exchange,
			BNode currentNode) {
		var r = new ObjectNode(null);
		r.set("id", new TextNode("" + currentNode.id()));
		r.set("class", new TextNode(currentNode.getClass().getName()));
		r.set("to_string", new TextNode(currentNode.toString()));
		r.set("can read", new TextNode("" + currentNode.canSee(user)));
		r.set("can write", new TextNode("" + currentNode.canSee(user)));
		r.set("views",
				graph.findEndpoint(View.Views.class).exec(inputJson, user, webServer, exchange, currentNode).toJson());
		return new EndpointJsonResponse(r, this);
	}
}
