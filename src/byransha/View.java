package byransha;

import com.fasterxml.jackson.databind.JsonNode;

import toools.io.JavaResource;

public abstract class View<N extends GOBMNode> {

//	final N node;

	public View() {
		if (getClass().isAnonymousClass() || getClass().getDeclaringClass() != null)
			throw new IllegalStateException("views must in declaring in their own file");

//		this.node = n;
	}

	public abstract JsonNode toJSONNode(N node, User u);

	public String html() {
		return new String(new JavaResource(getClass(), getClass().getSimpleName()).getByteArray());
	}

	public String name() {
		return getClass().getSimpleName();
	}
}
