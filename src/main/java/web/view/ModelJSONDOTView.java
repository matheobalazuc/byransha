package main.java.web.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import main.java.BBGraph;
import main.java.User;
import main.java.web.DevelopmentView;
import main.java.web.NodeEndpoint;
import main.java.web.EndpointJsonResponse;
import main.java.web.EndpointResponse;
import main.java.web.WebServer;
import toools.extern.Proces;

public abstract class ModelJSONDOTView extends NodeEndpoint<BBGraph> implements DevelopmentView {
	public ModelJSONDOTView(BBGraph db) {
		super(db);
	}

	protected static ObjectMapper mapper = new ObjectMapper();

	@Override
	public EndpointResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange, BBGraph n)
			throws Throwable {
		String dialect = requireParm(in, "dialect").asText();
		var dot = new ModelDOTView(graph).exec(in, user, webServer, exchange, n).data.getBytes();
		var stdout = Proces.exec("dot", dot, "-T" + dialect);
		return new EndpointJsonResponse(mapper.readTree(new String(stdout)), dialect);
	}

}
