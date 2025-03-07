package byransha.web.endpoint;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.User;
import byransha.web.EndPoint;
import byransha.web.EndpointJsonResponse;
import byransha.web.WebServer;

public class Authenticate extends EndPoint {

	public Authenticate(BBGraph db) {
		super(db);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EndpointJsonResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange https)
			throws Throwable {
		user = auth(requireParm(in, "username").asText(), requireParm(in, "password").asText());

		if (user == null) {
			return null;
		} else {
			user.session = https.getSSLSession();
			return new EndpointJsonResponse(new TextNode("" + user.id()), this);
		}

	}

	private  User auth(String username, String password) {
		for (var n : graph.nodes) {
			if (n instanceof User u && u.accept(username, password)) {
				return u;
			}
		}
		return null;
	}

}
