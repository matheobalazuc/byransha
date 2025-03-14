package byransha.labmodel.model.v0.view;

import java.io.PrintWriter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.User;
import byransha.web.TextOutputEndpoint;
import byransha.web.WebServer;
import byransha.labmodel.model.v0.Lab;

final public class LabView extends TextOutputEndpoint<Lab> {

	public LabView(BBGraph db) {
		super(db);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String textMimeType() {
		return "text/html";
	}

	@Override
	protected void print(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange, Lab lab,
			PrintWriter pw) {
		pw.println("<ul>");
		pw.println("<li>HFDS: " + lab.HFDS.etatCivil.name.get());
		pw.println("</ul>");
	}

}