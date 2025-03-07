package byransha.web.endpoint;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.User;
import byransha.web.EndPoint;
import byransha.web.EndpointJsonResponse;
import byransha.web.WebServer;

public class NodeIDs extends EndPoint {

	public NodeIDs(BBGraph db) {
		super(db);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EndpointJsonResponse exec(ObjectNode inputJson, User user, WebServer webServer, HttpsExchange exchange) {
		var a = new ArrayNode(null);

		for (var n : graph.nodes) {
			a.add(new TextNode("" + n.id()));
		}

		return new EndpointJsonResponse(a, this);
	}
}
