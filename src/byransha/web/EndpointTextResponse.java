package byransha.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class EndpointTextResponse extends EndpointResponse<String> {

	public EndpointTextResponse(String d, String textMimeType) {
		super(d, textMimeType);
	}

	@Override
	protected JsonNode data() {
		return new TextNode(data.replaceAll("\n", "\n"));
	}
}
