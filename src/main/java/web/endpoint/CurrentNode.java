package main.java.web.endpoint;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpsExchange;

import main.java.BBGraph;
import main.java.BNode;
import main.java.User;
import main.java.web.NodeEndpoint;
import main.java.web.EndpointJsonResponse;
import main.java.web.EndpointResponse;
import main.java.web.WebServer;

public class CurrentNode extends NodeEndpoint<BNode> {

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
