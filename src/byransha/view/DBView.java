package byransha.view;

import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

import byransha.DB;
import byransha.HTMLView;
import byransha.User;
import toools.text.TextUtilities;

final public class DBView extends HTMLView<DB>  implements TechnicalView{

	@Override
	protected void content(DB node, User user, PrintWriter pw) {
		pw.println("<ul>");
		pw.println("<li>" + DB.defaultDB.countNodes() + " nodes");
		pw.println("<li>Node classes: <ul>" + DB.defaultDB.nodes.l.stream().map(n -> "<li>" + n.getClass()).toList());
		pw.println("</ul>");
		var users = DB.defaultDB.users();
		pw.println("<li>" + users.size() + " users: "
				+ users.stream().map(u -> u.name.get() + (u.isAdmin() ? "*" : "")).toList());
		try {
			pw.println("<li>IP adress: " + InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.println("<li>directory: " + DB.defaultDB.directory);
		pw.println("<li>Arch: " + ManagementFactory.getOperatingSystemMXBean().getArch());
		pw.println("<li>OS: " + ManagementFactory.getOperatingSystemMXBean().getName());
		pw.println("<li>Load avg: " + ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage());
		pw.println("<li>#cores: " + Runtime.getRuntime().availableProcessors());
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