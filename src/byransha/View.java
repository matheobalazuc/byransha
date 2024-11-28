package byransha;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import byransha.view.BasicView;
import byransha.view.OutsInsView;
import byransha.view.ToStringView;
import toools.io.JavaResource;
import toools.reflect.Clazz;
import toools.text.TextUtilities;

public abstract class View<N extends GOBMNode> {

	public final static List<View> views = new ArrayList<>();

	static {
		views.add(new BasicView());
		views.add(new ToStringView());
		views.add(new OutsInsView());
	}

	public View() {
		if (getClass().isAnonymousClass() || getClass().getDeclaringClass() != null)
			throw new IllegalStateException("views must in declaring in their own file");
	}

	public final JsonNode toJSONNode(N node, User u) {
		var n = new ArrayNode(null);
		{
			var o = new ObjectNode(null);
			o.set("name", new TextNode(name()));
			n.add(o);
		}
		{
			if (node.canSee(u)) {
				var o = new ObjectNode(null);
				o.set("content-type", new TextNode(contentType()));
				o.set("content", new TextNode(TextUtilities.base64(content(node, u))));
				n.add(o);
			}
		}

		return n;
	}

	public <N extends GOBMNode> Class<N> getTargetNodeType() {
//		System.out.println("trying " + getClass());

		return (Class<N>) Clazz.getGenericTypes(getClass()).get(0);
	}

	protected abstract String contentType();

	protected abstract byte[] content(N node, User u);

	public String html() {
		return new String(new JavaResource(getClass(), getClass().getSimpleName()).getByteArray());
	}

	public String name() {
		return getClass().getSimpleName();
	}

	protected abstract Object content();
}
