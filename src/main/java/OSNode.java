package main.java;

import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import main.java.web.HTMLView;
import main.java.web.TechnicalView;
import main.java.web.WebServer;

public class OSNode extends BNode {

	public OSNode(BBGraph db) {
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
		}
	}
}
