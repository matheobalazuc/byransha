package byransha;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class JSONView<N extends BNode> extends View<N> {

	protected abstract String openWith();

	protected abstract JsonNode jsonData(N n, User u);

	@Override
	public ObjectNode toJSONNode(N node, User u, boolean includeContent) {
		var n = super.toJSONNode(node, u, includeContent);
		// n.set("openWith", new TextNode(openWith()));

		if (includeContent) {
			n.set("content", jsonData(node, u));
		}

		return n;
	}

	@Override
	public String contentType() {
		return "text/json";
	}

	@Override
	public byte[] content(N n, User user) {
		return jsonData(n, user).toString().getBytes();
	}
}
