package byransha.web;

import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BNode;
import byransha.ListNode;
import byransha.User;
import labmodel.model.v0.AcademiaDB;

public abstract class View<N extends BNode> extends EndPoint {

	static {
		views = new ListNode<>();
		View.views.add(new V());
	}

	public static final ListNode<View> views;

	public boolean sendContentByDefault = false;

	@Override
	public EndpointResponse<?> exec(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange)
			throws Throwable {
		var id = parm(in, "nodeID");
		N node = (N) node(id, user);
		Objects.requireNonNull(node, "can't find node: " + id);
		return content(node, user);
	}

	public abstract EndpointResponse content(N currentNode, User user) throws Throwable;

	public static BNode node(JsonNode idNode, User user) {
		if (idNode == null) {
			return user.currentNode() != null ? user.currentNode() : AcademiaDB.instance.root;
		} else {
			var nodeID = idNode.asText();

			if (nodeID.equals("random")) {
				return AcademiaDB.instance.nodes.random();
			} else if (nodeID.equals("current")) {
				return user.stack.isEmpty() ? null : user.stack.peek();
			} else {
				return node(Integer.valueOf(nodeID));
			}
		}
	}

	public static BNode node(int id) {
		return AcademiaDB.instance.findByID(Integer.valueOf(id));
	}

	public static List<BNode> nodes(int... ids) {
		return Arrays.stream(ids).mapToObj(id -> node(id)).toList();
	}

	public String label() {
		return name().replace('_', ' ');
	}

	public boolean isDevelopmentView() {
		return DevelopmentView.class.isAssignableFrom(getClass());
	}

	public boolean isTechnicalView() {
		return TechnicalView.class.isAssignableFrom(getClass());
	}

	public <N extends BNode> Class<N> getTargetNodeType() {
		for (Class c = getClass(); c != null; c = c.getSuperclass()) {
			var t = c.getGenericSuperclass();

			if (t instanceof ParameterizedType pt) {
				return (Class<N>) pt.getActualTypeArguments()[0];
			}
		}

		throw new IllegalStateException();
	}

	public static class V extends HTMLView<View> {
		@Override
		protected void print(View node, User u, PrintWriter pw) {
			pw.println("<ul>");
			pw.println("<li>name: " + node.name());
			pw.println("<li>target: " + node.getTargetNodeType().getName());
			pw.println("<li>development" + isDevelopmentView());
			pw.println("<li>technical" + node.isTechnicalView());
			pw.println("<li>content by default" + node.sendContentByDefault);
			pw.println("</ul>");
		}
	}
}
