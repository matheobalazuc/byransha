package labmodel.model.v0.view;

import java.io.PrintWriter;

import byransha.TextView;
import byransha.User;
import labmodel.model.v0.Lab;

final public class LabView extends TextView<Lab> {

	
	@Override
	public String contentType() {
		return "text/html";
	}

	@Override
	protected void content(Lab lab, User user, PrintWriter pw) {
		pw.println("<ul>");
		pw.println("<li>HFDS: " + lab.HFDS.name.get());
		pw.println("</ul>");
	}
	@Override
	public boolean isTechnical() {
		return false;
	}

}