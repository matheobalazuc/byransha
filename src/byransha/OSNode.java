package byransha;

import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

import byransha.web.HTMLView;
import byransha.web.TechnicalView;

public class OSNode extends BNode {

	static {
		View.views.add(new View());
	}
	
	public static class View extends HTMLView<DB> implements TechnicalView {

		@Override
		protected void print(DB node, User user, PrintWriter pw) {
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
