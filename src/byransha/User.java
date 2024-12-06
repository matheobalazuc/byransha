package byransha;

import java.util.Stack;

import javax.net.ssl.SSLSession;

public class User extends BNode {
	public StringNode name = new StringNode();
	StringNode passwordNode = new StringNode();
	BooleanNode adminNode = new BooleanNode();
	Stack<BNode> stack = new Stack<BNode>();
	public SSLSession session;

	public User(String u, String password, boolean admin) {
		name.set(u);
		adminNode.set(admin);
		passwordNode.set(password);
	}

	public boolean isAdmin() {
		return adminNode.get();
	}

	BNode currentNode() {
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
}
