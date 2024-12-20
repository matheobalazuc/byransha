package byransha.web.view;

import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import byransha.BNode;
import byransha.User;
import byransha.web.DevelopmentView;
import byransha.web.EndpointResponse;
import byransha.web.JSONView;
import byransha.web.TextView;

final public class AllViews extends TextView<BNode> implements DevelopmentView {

	static List<String> imgFormats = List.of("svg", "png", "jpeg", "jpg");

	@Override
	protected void print(BNode n, User u, PrintWriter pw) throws Throwable {
		pw.println("<ul>");

		for (var v : n.compliantViews()) {
			if (v == this)
				continue;

			pw.println("<li><h3>" + v.name() + "</h3>");
			pw.println("<ul>");
			EndpointResponse r = v.content(n, u);
			pw.println("<li>");

			if (v instanceof TextView) {
				pw.println(new String((String) r.data));
			} else if (imgFormats.contains(r.contentType)) {
				pw.println("<img src=\"data:image/png;base64," + Base64.getEncoder().encode((byte[]) r.data) + "\" />");
			} else if (v instanceof JSONView) {
				pw.println("<script>obj = " + r.data
						+ "; document.write(\"<pre>\" + JSON.stringify(obj) + \"</pre>\");</script>");
			} else {
				pw.println("Raw data: " + new String((byte[]) r.data));
			}

			pw.println("</ul>");
		}

		pw.println("</ul>");
	}

	@Override
	protected String textMimeType() {
		return "text/html";
	}
}