package byransha;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import byransha.view.AllViews;
import byransha.view.BasicView;
import byransha.view.DBView;
import byransha.view.ModelDOTView;
import byransha.view.ModelGraphivzSVGView;
import byransha.view.ModelJSONDOTView;
import byransha.view.OutsInsView;
import byransha.view.ToStringView;
import toools.io.JavaResource;
import toools.reflect.Clazz;

public abstract class View<N extends BNode> {

	public final static List<View> views = new ArrayList<>();

	static {
		views.add(new BasicView());
		views.add(new ToStringView());
		views.add(new OutsInsView());
		views.add(new DBView());
		views.add(new ModelDOTView());
		views.add(new ModelGraphivzSVGView());
		views.add(new ModelJSONDOTView());
		views.add(new AllViews());
	}

	public boolean sendContentByDefault = false;
	
	public View() {
		if (getClass().isAnonymousClass() || getClass().getDeclaringClass() != null)
			throw new IllegalStateException("views must in declaring in their own file");
	}

	public ObjectNode toJSONNode(N node, User u, boolean includeContent) {
		var n = new ObjectNode(null);
		n.set("name", new TextNode(name()));
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
		return getClass().getSimpleName();
	}

	public abstract byte[] content(N currentNode, User user);
}
