package byransha;

import java.io.PrintWriter;
import java.lang.management.ManagementFactory;

import byransha.web.HTMLView;
import byransha.web.TechnicalView;
import toools.text.TextUtilities;

public class JVMNode extends BNode {
	static {
		View.views.add(new View());
	}

	public static class View extends HTMLView<DB> implements TechnicalView {

		@Override
		protected void print(DB node, User user, PrintWriter pw) {
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
