package byransha.web;

import com.fasterxml.jackson.databind.JsonNode;

import byransha.BNode;
import byransha.User;

public abstract class JSONView<N extends BNode> extends View<N> {

	protected abstract String jsonDialect();

	protected abstract JsonNode jsonData(N n, User u) throws Throwable;

	@Override
	public EndpointJsonResponse content(N n, User user) throws Throwable {
		return new EndpointJsonResponse(jsonData(n, user), jsonDialect());
	}

}
