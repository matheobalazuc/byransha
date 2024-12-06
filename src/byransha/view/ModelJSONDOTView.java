package byransha.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import byransha.DB;
import byransha.JSONView;
import byransha.User;
import toools.extern.Proces;

public class ModelJSONDOTView extends JSONView<DB>  implements DevelopmentView{

	@Override
	public String contentType() {
		return "image/jsondot";
	}

	@Override
	public String name() {
		return "JSON/DOT";
	}

	@Override
	protected String openWith() {
		return "boh";
	}

	@Override
	protected JsonNode jsonData(DB db, User u) {
		var dot = new ModelDOTView().content(db, u);
		var s = new String(Proces.exec("dot", dot, "-Tjson"));// json0 dot_json xdot_json
		// Jackson main object
		ObjectMapper mapper = new ObjectMapper();

		// read the json strings and convert it into JsonNode
		try {
			return mapper.readTree(s);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	

}
