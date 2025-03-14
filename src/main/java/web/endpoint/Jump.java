package main.java.web.endpoint;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpsExchange;

import main.java.BBGraph;
import main.java.BNode;
import main.java.User;
import main.java.web.EndpointJsonResponse;
import main.java.web.NodeEndpoint;
import main.java.web.WebServer;

public class Jump extends NodeEndpoint<BNode> {

	public Jump(BBGraph g) {
		super(g);
	}

	@Override
	public EndpointJsonResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange, BNode node)
			throws Throwable {
		user.stack.push(node);

		var r = new ObjectNode(null);
		r.set("id", new TextNode("" + node.id()));
		r.set("class", new TextNode(node.getClass().getName()));
		r.set("name", new TextNode(node.toString()));
		r.set("can read", new TextNode("" + node.canSee(user)));
		r.set("can write", new TextNode("" + node.canSee(user)));

		ArrayNode viewsNode = new ArrayNode(null);
		var views = webServer.compliantEndpoints(node);

		for (var v : views) {
			var n = new ObjectNode(null);
			n.set("label", new TextNode(v.label()));
			n.set("id", new TextNode("" + v.id()));
			n.set("target", new TextNode(v.getTargetNodeType().getName()));
			n.set("development", new TextNode("" + v.isDevelopmentView()));
			n.set("technical", new TextNode("" + v.isTechnicalView()));

			if (v.sendContentByDefault) {
				n.set("content", v.exec(in, user, webServer, exchange, user).toJson());
			}

			viewsNode.add(n);
		}

		r.set("views", viewsNode);
		return new EndpointJsonResponse(r, this);
	}

	
}
