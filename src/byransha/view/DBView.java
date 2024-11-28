package byransha.view;

import java.io.PrintWriter;

import byransha.DB;
import byransha.DBNode;
import byransha.TextView;
import byransha.User;
import toools.text.TextUtilities;

final public class DBView extends TextView<DBNode> {

	@Override
	public String name() {
		return "DB info";
	}

	@Override
	protected String contentType() {
		return "text/html";
	}

	@Override
	protected void content(DBNode node, User u, PrintWriter pw) {
		pw.println("<ul>");
		pw.println("<li>" + DB.defaultDB.countNodes() + " nodes");
		pw.println("<li>" + TextUtilities.toHumanString(DB.defaultDB.sizeOf()) + "B");
		pw.println("</ul>");
	}
}