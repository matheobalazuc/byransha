package byransha.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

public abstract class EndpointResponse<R> {
	public final String contentType;
	public final R data; // byte[] or JsonNode

	public EndpointResponse(R d, String contentType) {
		this.data = d;
		this.contentType = contentType;
	}

	public ObjectNode toJson() {
		ObjectNode r = new ObjectNode(null);
		r.set("contentType", new TextNode(contentType));
		r.set("data", data());
		return r;
	}

	protected abstract JsonNode data();
}
