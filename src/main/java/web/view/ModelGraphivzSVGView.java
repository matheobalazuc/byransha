package main.java.web.view;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import main.java.BBGraph;
import main.java.User;
import main.java.web.NodeEndpoint;
import main.java.web.EndpointBinaryResponse;
import main.java.web.TechnicalView;
import main.java.web.WebServer;
import toools.extern.Proces;

public class ModelGraphivzSVGView extends NodeEndpoint<BBGraph> implements TechnicalView {

	public ModelGraphivzSVGView(BBGraph db) {
		super(db);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EndpointBinaryResponse exec(ObjectNode in, User u, WebServer webServer, HttpsExchange exchange, BBGraph db)
			throws Throwable {
		var dot = new ModelDOTView(graph).exec(in, u, webServer, exchange, db);
		var stdout = Proces.exec("dot", dot.data.getBytes(), "-Tsvg");
		return new EndpointBinaryResponse(stdout, "image/svg");
//		return Proces.exec("fdp", dot, "-Tsvg", "-Gmaxiter=10000", "-GK=1");
	}

}
