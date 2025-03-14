package main.java.labmodel.model.v0.view;

import java.io.PrintWriter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import main.java.BBGraph;
import main.java.User;
import main.java.web.HTMLView;
import main.java.web.WebServer;
import main.java.labmodel.model.v0.Structure;

final public class StructureView extends HTMLView<Structure> {

	public StructureView(BBGraph g) {
		super(g);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void print(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange, Structure s,
			PrintWriter pw) {
		pw.println("<ul>");
		pw.println("<li>Director: " + s.director.etatCivil.name.get());
		pw.println("<li>" + s.members.size() + " members");
		pw.println("<li>#offices: " + s.offices.size());
		pw.println("<li>Office surface: " + s.totalSurface());
		pw.println("<li>avg surface/user: " + s.occupationRatio());
		pw.println("<li>occupationRatio: " + s.occupationRatio());
		pw.println("</ul>");
	}
}