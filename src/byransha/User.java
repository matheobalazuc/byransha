package byransha;

import java.io.PrintWriter;
import java.util.Stack;

import javax.net.ssl.SSLSession;

import byransha.web.HTMLView;
import byransha.web.TechnicalView;
import byransha.web.View;
import toools.text.TextUtilities;

public class User extends BNode {

	static {
		View.views.add(new UserView());
	}

	public StringNode name = new StringNode();
	public StringNode passwordNode = new StringNode();
	public BooleanNode adminNode = new BooleanNode();
	public BooleanNode isTechnicianNode = new BooleanNode();
	public Stack<BNode> stack = new Stack<BNode>();
	public SSLSession session;

	public User(String u, String password, boolean admin) {
		name.set(u);
		adminNode.set(admin);
		passwordNode.set(password);
	}

	public boolean isAdmin() {
		return adminNode.get();
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
		return user.isAdmin();
	}

	public boolean accept(String username, String p) {
		return name.get().equals(username) && passwordNode.get().equals(p);
	}

	public static class UserView extends HTMLView<User> implements TechnicalView {
		@Override
		protected void print(User u, User user, PrintWriter pw) {
			pw.println("<ul>");
			pw.print("<li>Navigation history: ");
			u.stack.forEach(n -> pw.print(linkTo(n, "X")));
			pw.println("<li>admin? " + u.adminNode.get());
			pw.println("<li>Session ID: "
					+ (u.session.isValid() ? TextUtilities.toHex(u.session.getId()) : "no active session"));
			pw.println("</ul>");
		}
	}
}
