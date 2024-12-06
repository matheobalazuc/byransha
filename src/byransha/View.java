package byransha;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import byransha.view.DevelopmentView;
import byransha.view.TechnicalView;
import toools.io.JavaResource;
import toools.reflect.Clazz;

public abstract class View<N extends BNode> {

	public final static Map<Class<? extends View>, View> views = new HashMap<>();

	public boolean sendContentByDefault = false;

	public View() {
		if (getClass().isAnonymousClass() || getClass().getDeclaringClass() != null)
			throw new IllegalStateException("views must in declaring in their own file");
	}

	public ObjectNode toJSONNode(N node, User u, boolean includeContent) {
		var n = new ObjectNode(null);
		n.set("name", new TextNode(name()));
		n.set("development", new TextNode("" + DevelopmentView.class.isAssignableFrom(getClass())));
		n.set("technical", new TextNode("" + TechnicalView.class.isAssignableFrom(getClass())));
		n.set("contentType", new TextNode(contentType()));
		return n;
	}

	public abstract String contentType();

	public <N extends BNode> Class<N> getTargetNodeType() {
		return (Class<N>) Clazz.getGenericTypes(getClass()).get(0);
	}

	public String html() {
		return new String(new JavaResource(getClass(), getClass().getSimpleName()).getByteArray());
	}

	public String name() {
		var n = getClass().getSimpleName();
		return n.toLowerCase().endsWith("view") ? n.substring(0, n.length() - 4) : n;
	}

	public abstract byte[] content(N currentNode, User user);
}
