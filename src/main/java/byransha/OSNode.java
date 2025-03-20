package byransha;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.web.EndpointResponse;
import byransha.web.EndpointTextResponse;
import byransha.web.NodeEndpoint;
import byransha.web.TechnicalView;
import byransha.web.WebServer;

public class OSNode extends BNode {

	public OSNode(BBGraph db) {
		super(db);
	}

	public static class View extends NodeEndpoint<BBGraph> implements TechnicalView {

		public View(BBGraph g) {
			super(g);
		}

		@Override
		public boolean sendContentByDefault() {
			return false;
		}

		@Override
		public EndpointResponse exec(ObjectNode input, User user, WebServer webServer, HttpsExchange exchange,
				BBGraph node) throws Throwable {
			return new EndpointTextResponse(comment, pw -> {
				pw.println("<ul>");
				try {
					pw.println("<li>IP adress: " + InetAddress.getLocalHost().getHostName());
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pw.println("<li>Arch: " + ManagementFactory.getOperatingSystemMXBean().getArch());
				pw.println("<li>OS: " + ManagementFactory.getOperatingSystemMXBean().getName());
				pw.println("<li>Load avg: " + ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage());
				pw.println("<li>#cores: " + Runtime.getRuntime().availableProcessors());
				pw.println("</ul>");
			});
		}
	}
}
