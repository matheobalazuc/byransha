package byransha.web.endpoint;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.User;
import byransha.web.NodeEndpoint;
import byransha.web.EndpointJsonResponse;
import byransha.web.View;
import byransha.web.WebServer;

import java.util.ArrayList;

public class NodeIDs extends NodeEndpoint<BBGraph> implements View {

	@Override
	public String getDescription() {
		return "NodeIDs endpoint provides a list of node IDs.";
	}

	public NodeIDs(BBGraph db) {
		super(db);
	}

	@Override
	public EndpointJsonResponse exec(ObjectNode inputJson, User user, WebServer webServer, HttpsExchange exchange,
			BBGraph g) {
		var a = new ArrayNode(null);

		synchronized (g.nodes) {
			for (var n : g.nodes) {
				a.add(new TextNode("" + n.id()));
			}
		}

		return new EndpointJsonResponse(a, this);
	}

	@Override
	public boolean sendContentByDefault() {
		return false;
	}
}
