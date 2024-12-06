package labmodel.model.v0.view;

import java.io.PrintWriter;

import byransha.HTMLView;
import byransha.User;
import labmodel.model.v0.Structure;

final public class StructureView extends HTMLView<Structure> {

	@Override
	protected void content(Structure s, User user, PrintWriter pw) {
		pw.println("<ul>");
		pw.println("<li>Director: " + s.director.name.get());
		pw.println("<li>" + s.members.size() + " members");
		pw.println("<li>#offices: " + s.offices.size());
		pw.println("<li>Office surface: " + s.totalSurface());
		pw.println("<li>avg surface/user: " + s.occupationRatio());
		pw.println("<li>occupationRatio: " + s.occupationRatio());
		pw.println("</ul>");
	}
	@Override
	public boolean isTechnical() {
		return false;
	}

}