package byransha.web.endpoint;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.User;
import byransha.web.EndPoint;
import byransha.web.EndpointJsonResponse;
import byransha.web.NodeEndpoint;
import byransha.web.WebServer;

public class Endpoints extends EndPoint {

	public Endpoints(BBGraph db) {
		super(db);
	}

	@Override
	public EndpointJsonResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange http) {
		var currentNode = user.currentNode();

		var data = new ArrayNode(null);
		webServer.endpoints.values().forEach(e -> {
			var nn = new ObjectNode(null);
			nn.set("name", new TextNode(e.name()));
			boolean nodeEndpoint = e instanceof NodeEndpoint;
			nn.set("node-endpoint", BooleanNode.valueOf(nodeEndpoint));

			if (nodeEndpoint) {
				nn.set("applicable to current node", BooleanNode.valueOf(currentNode.matches((NodeEndpoint) e)));
			}

			data.add(nn);
		});

		return new EndpointJsonResponse(data, this);
	}
}
