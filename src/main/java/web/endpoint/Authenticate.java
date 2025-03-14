package main.java.web.endpoint;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpsExchange;

import main.java.BBGraph;
import main.java.User;
import main.java.web.NodeEndpoint;
import main.java.web.EndpointJsonResponse;
import main.java.web.WebServer;

public class Authenticate extends NodeEndpoint<BBGraph> {

	public Authenticate(BBGraph db) {
		super(db);
	}

	@Override
	public EndpointJsonResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange https, BBGraph g)
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
