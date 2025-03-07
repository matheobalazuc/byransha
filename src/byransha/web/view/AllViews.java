package byransha.web.view;

import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BNode;
import byransha.BBGraph;
import byransha.User;
import byransha.web.DevelopmentView;
import byransha.web.EndpointJsonResponse;
import byransha.web.EndpointResponse;
import byransha.web.TextOutputEndpoint;
import byransha.web.WebServer;

final public class AllViews extends TextOutputEndpoint<BNode> implements DevelopmentView {

	public AllViews(BBGraph db) {
		super(db);
		// TODO Auto-generated constructor stub
	}

	static List<String> imgFormats = List.of("svg", "png", "jpeg", "jpg");

	@Override
	protected void print(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange, BNode n, PrintWriter pw)
			throws Throwable {
		pw.println("<ul>");

		for (var v : webServer.compliantEndpoints(n)) {
			if (v == this)
				continue;

			pw.println("<li><h3>" + v.name() + "</h3>");
			pw.println("<ul>");
			EndpointResponse r = v.exec(in, user, webServer, exchange, n);
			pw.println("<li>");

			if (v instanceof TextOutputEndpoint) {
				pw.println(new String((String) r.data));
			} else if (imgFormats.contains(r.contentType)) {
				pw.println("<img src=\"data:image/png;base64," + Base64.getEncoder().encode((byte[]) r.data) + "\" />");
			} else if (r instanceof EndpointJsonResponse) {
				pw.println("<script>obj = " + r.data()
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