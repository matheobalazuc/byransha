package byransha.view;

import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import byransha.BNode;
import byransha.TextView;
import byransha.User;

final public class AllViews extends TextView<BNode> {

	@Override
	public String name() {
		return "all";
	}

	@Override
	public String contentType() {
		return "text/html";
	}

	static List<String> imgFormats = List.of("svg", "png", "jpeg", "jpg");

	@Override
	protected void content(BNode n, User u, PrintWriter pw) {
		pw.println("<ul>");

		for (var v : n.compliantViews()) {
			if (v == this)
				continue;

			pw.println("<li><h3>" + v.name() + "</h3>");
			pw.println("<ul>");
			pw.println("<li>content type: " + v.contentType());
			byte[] content = v.content(n, u);
			pw.println("<li>");
			if (v.contentType().equals("text/html")) {
				pw.println(new String(content));
			} else if (imgFormats.contains(v.contentType())) {
				pw.println("<img src=\"data:image/png;base64," + Base64.getEncoder().encode(content) + "\" />");
			} else if (v.contentType().equals("text/json")) {
				pw.println("<script>obj = " + new String(content)
						+ "; document.write(\"<pre>\" + JSON.stringify(obj) + \"</pre>\");</script>");
			} else {
				pw.println("Raw data: " + new String(content));
			}
			pw.println("</ul>");
		}

		pw.println("</ul>");
	}

}