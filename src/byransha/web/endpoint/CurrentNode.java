package byransha.web.endpoint;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.User;
import byransha.web.EndPoint;
import byransha.web.EndpointTextResponse;
import byransha.web.WebServer;

public class CurrentNode extends EndPoint {

	@Override
	public EndpointTextResponse exec(ObjectNode inputJson, User user, WebServer webServer, HttpsExchange exchange) {
		var n = user.currentNode();
		return n == null ? null : new EndpointTextResponse(n.id() + "", "text/plain");
	}
}
