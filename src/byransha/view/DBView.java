package byransha.view;

import java.io.PrintWriter;
import java.lang.management.ManagementFactory;

import byransha.DB;
import byransha.TextView;
import byransha.User;
import toools.text.TextUtilities;

final public class DBView extends TextView<DB> {

	@Override
	public String name() {
		return "DB info";
	}

	@Override
	protected String contentType() {
		return "text/html";
	}

	@Override
	protected void content(DB node, User u, PrintWriter pw) {
		pw.println("<ul>");
		pw.println("<li>" + DB.defaultDB.countNodes() + " nodes");
		pw.println("<li>Arch: " + ManagementFactory.getOperatingSystemMXBean().getArch());
		pw.println("<li>OS: " + ManagementFactory.getOperatingSystemMXBean().getName());
		pw.println("<li>Load avg: " + ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage());
		pw.println("<li>#cores: " + Runtime.getRuntime().availableProcessors());
		pw.println("<li>Heap size: "
				+ TextUtilities.toHumanString(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed())
				+ "B");
		pw.println("</ul>");
	}
}