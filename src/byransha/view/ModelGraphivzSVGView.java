package byransha.view;

import byransha.DB;
import byransha.User;
import byransha.View;
import toools.extern.Proces;

public class ModelGraphivzSVGView extends View<DB> {

	@Override
	protected String contentType() {
		return "image/svg+xml";
	}

	@Override
	public String name() {
		return "Model (DOT/SVG)";
	}

	@Override
	protected byte[] content(DB db, User u) {
		var dot = new ModelDOTView().content(db, u);
		return Proces.exec("dot", dot, "-Tsvg");
	}

}
