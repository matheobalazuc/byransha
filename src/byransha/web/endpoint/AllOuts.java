package byransha.web.endpoint;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.User;
import byransha.web.EndPoint;
import byransha.web.EndpointTextResponse;
import byransha.web.WebServer;

public class AllOuts extends EndPoint {

	public AllOuts(BBGraph db) {
		super(db);
	}

	@Override
	public EndpointTextResponse exec(ObjectNode inputJson, User user, WebServer webServer, HttpsExchange exchange) {
		var a = new ArrayNode(null);

		for (var n : graph.nodes) {
			var nn = new ObjectNode(null);
			nn.set("id", new TextNode("" + n.id()));
			nn.set("class", new TextNode(n.getClass().getName()));
			a.add(nn);
		}

		return new EndpointTextResponse("", "");
	}
}
