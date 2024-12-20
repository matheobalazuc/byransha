package byransha.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BNode;
import byransha.User;
import toools.text.TextUtilities;

public abstract class EndPoint extends BNode
{
	public abstract EndpointResponse exec(ObjectNode inputJson, User user, WebServer webServer, HttpsExchange exchange)
			throws Throwable;

	public final String name() {
		return TextUtilities.camelToSnake(getClass().getSimpleName());
	}

	protected final JsonNode parm(ObjectNode in, String s) {
		var node = in.remove(s);

		if (node == null) {
			throw new IllegalArgumentException("missing parameter: " + s);
		} else {
			return node;
		}
	}

}
