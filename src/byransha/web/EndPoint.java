package byransha.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.BNode;
import byransha.User;
import toools.text.TextUtilities;

public abstract class EndPoint extends BNode {
	public EndPoint(BBGraph db) {
		super(db);
	}

	public int nbCalls = 0;
	public long timeSpentNs = 0;

	public abstract EndpointResponse exec(ObjectNode inputJson, User user, WebServer webServer, HttpsExchange exchange)
			throws Throwable;

	public final String name() {
		var name = getClass().getSimpleName();
		var enclosingClass = getClass().getEnclosingClass();

		if (enclosingClass != null) {
			name += enclosingClass.getSimpleName() + "_" + name;
		}

		return TextUtilities.camelToSnake(name);
	}

	public String label() {
		return name().replace('_', ' ');
	}

	protected final JsonNode requireParm(ObjectNode in, String s) {
		var node = in.remove(s);

		if (node == null) {
			throw new IllegalArgumentException("missing parameter: " + s);
		} else {
			return node;
		}
	}

	public boolean isDevelopmentView() {
		return DevelopmentView.class.isAssignableFrom(getClass());
	}

	public boolean isTechnicalView() {
		return TechnicalView.class.isAssignableFrom(getClass());
	}
}
