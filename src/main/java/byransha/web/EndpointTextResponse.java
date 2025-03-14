package byransha.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class EndpointTextResponse extends EndpointResponse<String> {

	public EndpointTextResponse(String d, String textMimeType) {
		super(d, textMimeType);
	}

	@Override
	public JsonNode data() {
		return new TextNode(data.replaceAll("\n", "\n"));
	}	
	
	@Override
	public String toRawText() {
		return data;
	}

}
