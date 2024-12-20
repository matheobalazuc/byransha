package byransha.web;

import java.io.PrintWriter;
import java.io.StringWriter;

import byransha.BNode;
import byransha.User;

public abstract class TextView<N extends BNode> extends View<N> {

	@Override
	public EndpointTextResponse content(N node, User u) throws Throwable {
		var sw = new StringWriter();
		var pw = new PrintWriter(sw);
		print(node, u, pw);
		return new EndpointTextResponse(sw.getBuffer().toString(), textMimeType());
	}

	protected abstract String textMimeType();

	protected abstract void print(N node, User u, PrintWriter pw) throws Throwable;

}
