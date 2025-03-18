package byransha.web.endpoint;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.BNode;
import byransha.User;
import byransha.web.NodeEndpoint;
import byransha.web.EndpointJsonResponse;
import byransha.web.EndpointResponse;
import byransha.web.WebServer;

public class CurrentNode extends NodeEndpoint<BNode> {

	public CurrentNode(BBGraph db) {
		super(db);
	}

	@Override
	public EndpointResponse exec(ObjectNode inputJson, User user, WebServer webServer, HttpsExchange exchange,
			BNode n) {
		if (n == null) {
			return null;
		} else {
			ArrayNode viewsNode = new ArrayNode(null);
			var views = webServer.compliantEndpoints(n);

			for(var v : views){
				var root = new ObjectNode(null);
				root.set("label", new TextNode(v.label()));
				root.set("id", new TextNode("" + v.id()));
				root.set("target", new TextNode(v.getTargetNodeType().getName()));
				root.set("development", new TextNode("" + v.isDevelopmentView()));
				root.set("technical", new TextNode("" + v.isTechnicalView()));
				root.set("can read", new TextNode("" + v.canSee(user)));
				root.set("can write", new TextNode("" + v.canSee(user)));

//				if (v.sendContentByDefault) {
//					root.set("content", v.exec(inputJson, user, webServer, exchange, user).toJson());
//				}

				viewsNode.add(root);
			}
			return new EndpointJsonResponse(viewsNode, this);

		}
	}
}
