package byransha.view;

import java.io.PrintWriter;

import byransha.HTMLView;
import byransha.User;
import toools.text.TextUtilities;

final public class UserView extends HTMLView<User> implements TechnicalView {

	@Override
	protected void content(User u, User user, PrintWriter pw) {
		pw.println("<ul>");
		pw.print("<li>Navigation history: ");
		u.stack.forEach(n -> pw.print(linkTo(n, "X")));
		pw.println("<li>admin? " + u.adminNode.get());
		pw.println("<li>Session ID: "
				+ (u.session.isValid() ? TextUtilities.toHex(u.session.getId()) : "no active session"));
		pw.println("</ul>");
	}

}