package byransha.web.view;

import byransha.DB;
import byransha.User;
import byransha.web.EndpointBinaryResponse;
import byransha.web.TechnicalView;
import byransha.web.View;
import toools.extern.Proces;

public class ModelGraphivzSVGView extends View<DB> implements TechnicalView {

	@Override
	public EndpointBinaryResponse content(DB db, User u) throws Throwable {
		var dot = new ModelDOTView().content(db, u);
		var stdout = Proces.exec("dot", dot.data.getBytes(), "-Tsvg");
		return new EndpointBinaryResponse(stdout, "image/svg");
//		return Proces.exec("fdp", dot, "-Tsvg", "-Gmaxiter=10000", "-GK=1");

	}

}
