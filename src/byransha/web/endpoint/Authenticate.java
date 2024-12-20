package byransha.web.endpoint;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.DB;
import byransha.User;
import byransha.web.EndPoint;
import byransha.web.EndpointJsonResponse;
import byransha.web.EndpointResponse;
import byransha.web.WebServer;

public class Authenticate extends EndPoint {

	@Override
	public EndpointResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange https) throws Throwable {
		user = auth(parm(in, "username").asText(), parm(in, "password").asText());

		if (user == null) {
			return null;
		} else {
			user.session = https.getSSLSession();
			return new EndpointJsonResponse(new TextNode("" + user.id()), this);
		}

	}


	private static User auth(String username, String password) {
		for (var n : DB.instance.nodes.l) {
			if (n instanceof User u && u.accept(username, password)) {
				return u;
			}
		}
		return null;
	}

}
