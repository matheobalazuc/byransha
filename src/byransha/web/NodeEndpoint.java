package byransha.web;

import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.BNode;
import byransha.User;

public abstract class NodeEndpoint<N extends BNode> extends EndPoint {

	public NodeEndpoint(BBGraph db) {
		super(db);
	}

	public boolean sendContentByDefault = false;

	@Override
	public final EndpointResponse<?> exec(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange)
			throws Throwable {
		N node = (N) node(in, user);
		Objects.requireNonNull(node);
		return exec(in, user, webServer, exchange, node);
	}

	private BNode node(ObjectNode in, User user) {
		var idNode = in.remove("nodeID");

		if (idNode == null) { // no explicit node
			if (user.currentNode() == null)
				return graph.root();

			return user.currentNode();
		} else {
			var nodeID = idNode.asText();

			if (nodeID.equals("random")) {
				return graph.nodes.get(new Random().nextInt(graph.nodes.size()));
			} else if (nodeID.equals("current")) {
				return user.stack.isEmpty() ? null : user.stack.peek();
			} else if (nodeID.equals("previous")) {
				if (user.stack.size() > 1) {
					user.stack.pop();
					return user.stack.peek();
				}

				return null;
			} else {
				return node(Integer.valueOf(nodeID));
			}
		}
	}

	public abstract EndpointResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange, N node)
			throws Throwable;

	public <N extends BNode> Class<N> getTargetNodeType() {
		for (Class c = getClass(); c != null; c = c.getSuperclass()) {
			var t = c.getGenericSuperclass();

			if (t instanceof ParameterizedType pt) {
				return (Class<N>) pt.getActualTypeArguments()[0];
			}
		}

		throw new IllegalStateException();
	}

	public BNode node(int id) {
		return graph.findByID(Integer.valueOf(id));
	}

	public List<BNode> nodes(int... ids) {
		return Arrays.stream(ids).mapToObj(id -> node(id)).toList();
	}

	public static class V extends HTMLView<NodeEndpoint> {
		public V(BBGraph g) {
			super(g);
		}

		@Override
		protected void print(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange, NodeEndpoint node,
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
