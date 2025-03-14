package byransha;

import java.io.PrintWriter;
import java.lang.management.ManagementFactory;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.web.NodeEndpoint;
import byransha.web.EndpointResponse;
import byransha.web.EndpointTextResponse;
import byransha.web.HTMLView;
import byransha.web.TechnicalView;
import byransha.web.WebServer;
import toools.text.TextUtilities;

public class JVMNode extends BNode {


	public JVMNode(BBGraph db) {
		super(db);
	}

	public static class View extends HTMLView<BBGraph> implements TechnicalView {

		public View(BBGraph g) {
			super(g);
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
	
	public static class Kill extends NodeEndpoint<JVMNode> {

		public Kill(BBGraph db) {
			super(db);
		}

		@Override
		public EndpointResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange http, JVMNode jvm) throws Throwable {
			new Thread(() -> {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
				System.exit(0);
			}).start();

			return new EndpointTextResponse("exiting...", "text/plain");
		}
	}

}
