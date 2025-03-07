package byransha.web;

import java.util.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class EndpointBinaryResponse extends EndpointResponse<byte[]> {
	final String binaryContentType;

	public EndpointBinaryResponse(byte[] d, String binaryContentType) {
		super(d, binaryContentType);
		this.binaryContentType = binaryContentType;
	}

	@Override
	public ObjectNode toJson() {
		var r = super.toJson();
		r.set("binaryContentType", new TextNode(binaryContentType));
		return r;
	}

	@Override
	public JsonNode data() {
		return new TextNode(new String(Base64.getMimeEncoder().encode(data)));
	}

	@Override
	public String toRawText() {
		return new String(data);
	}
}
