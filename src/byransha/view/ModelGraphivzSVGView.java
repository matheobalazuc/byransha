package byransha.view;

import byransha.DB;
import byransha.User;
import byransha.View64;
import toools.extern.Proces;

public class ModelGraphivzSVGView extends View64<DB> implements TechnicalView {

	@Override
	public String contentType() {
		return "image/svg+xml";
	}

	@Override
	public String name() {
		return "Model (DOT/SVG)";
	}

	@Override
	public byte[] content(DB db, User u) {
		var dot = new ModelDOTView().content(db, u);
		return Proces.exec("dot", dot, "-Tsvg");
//		return Proces.exec("fdp", dot, "-Tsvg", "-Gmaxiter=10000", "-GK=1");

	}

}
