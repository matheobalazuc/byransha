package byransha.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class EndpointHtmlResponse extends EndpointResponse<String> {

    public EndpointHtmlResponse(int statusCode, String contentType, byte[] content) {
        super(new String(content), contentType);
    }

    @Override
    public String toRawText() {
        return data;
    }

    @Override
    public JsonNode data() {
        return new TextNode(data);
    }
}