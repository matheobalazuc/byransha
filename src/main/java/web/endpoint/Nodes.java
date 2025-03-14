package main.java.web.endpoint;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpsExchange;

import main.java.BBGraph;
import main.java.User;
import main.java.web.NodeEndpoint;
import main.java.web.EndpointJsonResponse;
import main.java.web.WebServer;

public class Nodes extends NodeEndpoint<BBGraph> {

	public Nodes(BBGraph db) {
		super(db);
	}

	@Override
	public EndpointJsonResponse exec(ObjectNode inputJson, User user, WebServer webServer, HttpsExchange exchange, BBGraph g) {
		var a = new ArrayNode(null);

		for (var n : g.nodes) {
			var nn = new ObjectNode(null);
			nn.set("id", new TextNode("" + n.id()));
			nn.set("class", new TextNode(n.getClass().getName()));
			a.add(nn);
		}

		return new EndpointJsonResponse(a, this);
	}
}
