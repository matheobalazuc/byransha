package byransha.web;

import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.BNode;
import byransha.User;
import toools.text.TextUtilities;

public abstract class Endpoint extends BNode {
	public int nbCalls = 0;
	public long timeSpentNs = 0;
	public boolean sendContentByDefault = false;

	public Endpoint(BBGraph db) {
		super(db);
	}

	public abstract EndpointResponse exec(ObjectNode input, User user, WebServer webServer, HttpsExchange exchange) throws Throwable;

	public <N extends BNode> Class<N> getTargetNodeType() {
		for (Class c = getClass(); c != null; c = c.getSuperclass()) {
			var t = c.getGenericSuperclass();

			if (t instanceof ParameterizedType pt) {
				return (Class<N>) pt.getActualTypeArguments()[0];
			}
		}

		throw new IllegalStateException();
	}


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

	public static class V extends HTMLView<Endpoint> {
		public V(BBGraph g) {
			super(g);
		}

		@Override
		protected void print(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange, Endpoint node,
				PrintWriter pw) {
			pw.println("<ul>");
			pw.println("<li>name: " + node.name());
			pw.println("<li>label: " + node.label());
			pw.println("<li>target: " + node.getTargetNodeType().getName());
			pw.println("<li>development" + isDevelopmentView());
			pw.println("<li>technical" + node.isTechnicalView());
			pw.println("<li>content by default" + node.sendContentByDefault);
			pw.println("</ul>");
		}
	}

}
