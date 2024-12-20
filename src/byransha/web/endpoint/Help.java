package byransha.web.endpoint;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.User;
import byransha.web.EndPoint;
import byransha.web.EndpointJsonResponse;
import byransha.web.View;
import byransha.web.WebServer;

public class Help extends EndPoint {

	@Override
	public EndpointJsonResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange http)
			throws Throwable {
		var r = new ObjectNode(null);
		var currentNode = user.currentNode();

		var data = new ArrayNode(null);
		webServer.endpoints.values().stream().filter(e -> !(e instanceof View))
				.forEach(e -> data.add(new TextNode(e.name())));

		if (currentNode != null) {
			r.set("current node class", new TextNode(currentNode.getClass().getName()));
			webServer.endpoints.values().stream().filter(e -> e instanceof View v && currentNode.matches(v))
					.forEach(e -> data.add(new TextNode(e.name())));
		}

		return new EndpointJsonResponse(data, this);
	}

}
