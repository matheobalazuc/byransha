package labmodel.model.v0;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import main.java.BBGraph;
import main.java.User;
import main.java.ValuedNode;
import main.java.web.NodeEndpoint;
import main.java.web.EndpointBinaryResponse;
import main.java.web.EndpointResponse;
import main.java.web.WebServer;

public class Picture extends ValuedNode<byte[]> {

	public Picture(BBGraph db) {
		super(db);
	}

	public static class V extends NodeEndpoint<Picture> {

		public V(BBGraph db) {
			super(db);
		}

		@Override
		public EndpointResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange,
				Picture node) {
			return new EndpointBinaryResponse(node.get(), "image/jpeg");
		}
	}

	@Override
	public void fromString(String s) {
	}
}
