package byransha.web.endpoint;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.BNode;
import byransha.User;
import byransha.web.Endpoint;
import byransha.web.EndpointJsonResponse;
import byransha.web.EndpointResponse;
import byransha.web.WebServer;

public class CurrentNode extends Endpoint<BNode> {

	public CurrentNode(BBGraph db) {
		super(db);
	}

	@Override
	public EndpointResponse exec(ObjectNode inputJson, User user, WebServer webServer, HttpsExchange exchange,
			BNode n) {
		if (n == null) {
			return null;
		} else {
			var root = new ObjectNode(null);
			root.set("id", new TextNode("" + n.id()));
			root.set("class", new TextNode(n.getClass().getName()));
			return new EndpointJsonResponse(root, this);
		}
	}
}
