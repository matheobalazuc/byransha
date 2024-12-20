package labmodel.model.v0.view;

import java.io.PrintWriter;

import byransha.User;
import byransha.web.TextView;
import labmodel.model.v0.Lab;

final public class LabView extends TextView<Lab> {

	@Override
	public String textMimeType() {
		return "text/html";
	}

	@Override
	protected void print(Lab lab, User user, PrintWriter pw) {
		pw.println("<ul>");
		pw.println("<li>HFDS: " + lab.HFDS.name.get());
		pw.println("</ul>");
	}

}