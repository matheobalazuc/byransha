package byransha;

import java.io.PrintWriter;
import java.util.Stack;

import javax.net.ssl.SSLSession;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.web.HTMLView;
import byransha.web.TechnicalView;
import byransha.web.WebServer;
import toools.text.TextUtilities;

public class User extends BNode {

	public StringNode name;
	public StringNode passwordNode;
	public Stack<BNode> stack = new Stack<BNode>();
	public SSLSession session;

	public User(BBGraph g, String u, String password) {
		super(g);
		name = new StringNode(g);
		passwordNode = new StringNode(g);
		passwordNode.set(password);
	}

	public BNode currentNode() {
		return stack.isEmpty() ? null : stack.peek();
	}

	@Override
	public boolean canSee(User user) {
		return user == this;
	}

	@Override
	public boolean canEdit(User user) {
		return user == this;
	}

	public boolean accept(String username, String p) {
		return name.get().equals(username) && passwordNode.get().equals(p);
	}

	public static class UserView extends HTMLView<User> implements TechnicalView {
		public UserView(BBGraph g) {
			super(g);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void print(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange, User u,
				PrintWriter pw) {
			pw.println("<ul>");
			pw.print("<li>Navigation history: ");
			u.stack.forEach(n -> pw.print(linkTo(n, "X")));
			pw.println("<li>admin? " + false);
			pw.println("<li>Session ID: "
					+ (u.session.isValid() ? TextUtilities.toHex(u.session.getId()) : "no active session"));
			pw.println("</ul>");
		}
	}
}
