package byransha.web.endpoint;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.BNode;
import byransha.User;
import byransha.web.EndpointJsonResponse;
import byransha.web.NodeEndpoint;
import byransha.web.WebServer;

public class CurrentNode extends NodeEndpoint<BNode> {

	public CurrentNode(BBGraph db) {
		super(db);
	}

	@Override
	public EndpointJsonResponse exec(ObjectNode inputJson, User user, WebServer webServer, HttpsExchange exchange,
			BNode currentNode) {
		var r = new ObjectNode(null);
		r.set("id", new TextNode("" + currentNode.id()));
		r.set("class", new TextNode(currentNode.getClass().getName()));
		r.set("to_string", new TextNode(currentNode.toString()));
		r.set("can read", new TextNode("" + currentNode.canSee(user)));
		r.set("can write", new TextNode("" + currentNode.canSee(user)));

		ArrayNode viewsNode = new ArrayNode(null);
		var endpoints = webServer.endpointsUsableFrom(currentNode);

		for (var v : endpoints) {
			var ev = new ObjectNode(null);
			ev.set("label", new TextNode(v.label()));
			ev.set("id", new TextNode("" + v.id()));
			ev.set("target", new TextNode(v.getTargetNodeType().getName()));
			ev.set("development", new TextNode("" + v.isDevelopmentView()));
			ev.set("technical", new TextNode("" + v.isTechnicalView()));
			ev.set("can read", new TextNode("" + v.canSee(user)));
			ev.set("can write", new TextNode("" + v.canSee(user)));

			if (v.sendContentByDefault) {
				try {
					ev.set("result", v.exec(inputJson, user, webServer, exchange, user).toJson());
				} catch (Throwable err) {
					err.printStackTrace();
					var sw = new StringWriter();
					err.printStackTrace(new PrintWriter(sw));
					ev.set("error", new TextNode(sw.toString()));
				}
			}

			viewsNode.add(ev);
		}

		r.set("views", viewsNode);

		return new EndpointJsonResponse(r, this);
	}
}
