package byransha.web;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.BNode;
import byransha.User;

public abstract class TextOutputEndpoint<N extends BNode> extends NodeEndpoint<N> {

	public TextOutputEndpoint(BBGraph db) {
		super(db);
	}

	@Override
	public EndpointTextResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange, N node)
			throws Throwable {
		var sw = new StringWriter();
		var pw = new PrintWriter(sw);
		print(in, user, webServer, exchange, node, pw);
		return new EndpointTextResponse(sw.getBuffer().toString(), textMimeType());
	}

	protected abstract String textMimeType();

	protected abstract void print(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange, N node,
			PrintWriter pw) throws Throwable;

}
