package byransha;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import toools.text.TextUtilities;

public abstract class View64<N extends BNode> extends View<N> {

	@Override
	public ObjectNode toJSONNode(N node, User u, boolean includeContent) {
		var n = super.toJSONNode(node, u, includeContent);

		if (includeContent) {
			n.set("content", new TextNode(TextUtilities.base64(content(node, u))));
		}
	
		return n;
	}

}