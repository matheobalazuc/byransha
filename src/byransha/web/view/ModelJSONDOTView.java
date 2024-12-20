package byransha.web.view;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import byransha.DB;
import byransha.User;
import byransha.web.DevelopmentView;
import byransha.web.JSONView;
import toools.extern.Proces;

public abstract class ModelJSONDOTView extends JSONView<DB> implements DevelopmentView {
	protected static ObjectMapper mapper = new ObjectMapper();

	@Override
	protected JsonNode jsonData(DB db, User u) throws Throwable {
		var dot = new ModelDOTView().content(db, u).data.getBytes();
		var stdout = Proces.exec("dot", dot, "-T" + jsonDialect());
		return mapper.readTree(new String(stdout));
	}
}
