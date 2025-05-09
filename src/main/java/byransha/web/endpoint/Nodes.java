package byransha.web.endpoint;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.BNode;
import byransha.User;
import byransha.web.NodeEndpoint;
import byransha.web.EndpointJsonResponse;
import byransha.web.WebServer;

import java.util.ArrayList;

public class Nodes extends NodeEndpoint<BNode> {

	@Override
	public String getDescription() {
		return "Nodes endpoint description";
	}

	public Nodes(BBGraph db) {
		super(db);
	}

	@Override
	public EndpointJsonResponse exec(ObjectNode inputJson, User user, WebServer webServer, HttpsExchange exchange, BNode g) {
		var a = new ArrayNode(null);

		synchronized (graph.nodes) {
			for (var n : graph.nodes) {
				var nn = new ObjectNode(null);
				nn.set("id", new TextNode("" + n.id()));
				nn.set("class", new TextNode(n.getClass().getName()));
				nn.set("to_string", new TextNode(n.toString()));
				a.add(nn);
			}
		}

		return new EndpointJsonResponse(a, this);
	}
}
