package byransha;

import java.io.PrintWriter;
import java.lang.management.ManagementFactory;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.web.HTMLView;
import byransha.web.TechnicalView;
import byransha.web.WebServer;
import toools.text.TextUtilities;

public class JVMNode extends BNode {


	public JVMNode(BBGraph db) {
		super(db);
		// TODO Auto-generated constructor stub
	}

	public static class View extends HTMLView<BBGraph> implements TechnicalView {

		public View(BBGraph g) {
			super(g);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void print(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange, BBGraph node,
				PrintWriter pw) {
			pw.println("<ul>");
			pw.println("<li>Heap size: "
					+ TextUtilities.toHumanString(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed())
					+ "B");
			pw.println("</ul><hr>System properties:<ul>");

			for (var e : System.getProperties().entrySet()) {
				pw.println("<li>" + e.getKey() + "=" + e.getValue());
			}
			pw.println("</ul>");
		}

	}
}
