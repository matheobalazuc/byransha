package main.java.web.view;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import main.java.BBGraph;
import main.java.BNode;
import main.java.Byransha.Function;
import main.java.User;
import main.java.web.NodeEndpoint;
import main.java.web.EndpointJsonResponse;
import main.java.web.EndpointJsonResponse.dialects;
import main.java.web.WebServer;

public class CharExampleXY extends NodeEndpoint<BNode> {
	public CharExampleXY(BBGraph g) {
		super(g);
		sendContentByDefault = true;
	}

	@Override
	public EndpointJsonResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange, BNode n) {
		var r = new ObjectNode(null);

		{
			Function f = new Function();

			for (double x = 0; x < 50; ++x) {
				f.addXY(x, Math.cos(x));
			}

			r.set("cos(x)", f.toJson());
		}

		{
			Function f = new Function();

			for (double x = 0; x < 50; ++x) {
				f.addXY(x, Math.sin(x));
			}

			r.set("sin(x)", f.toJson());
		}

		return new EndpointJsonResponse(r, dialects.xy);
	}
}
