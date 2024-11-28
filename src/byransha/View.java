package byransha;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import byransha.view.BasicView;
import byransha.view.ToStringView;
//import jaseto.Jaseto;
import toools.io.JavaResource;
import toools.text.TextUtilities;

public abstract class View<N extends GOBMNode> {

//	final N node;

	public final static List<View<? extends GOBMNode>> views = new ArrayList<>();

	static {
		views.add(new BasicView());
		views.add(new ToStringView());
	}

	public View() {
		if (getClass().isAnonymousClass() || getClass().getDeclaringClass() != null)
			throw new IllegalStateException("views must in declaring in their own file");

//		this.node = n;
	}

	public JsonNode toJSONNode(GOBMNode node, User u) throws IOException {
		var n = new ArrayNode(null);
		{
			var o = new ObjectNode(null);
			o.set("name", new TextNode(getClass().getName()));
			n.add(o);
		}
		{
			var o = new ObjectNode(null);
			o.set("content-type", new TextNode(contentType()));
			var content = content();

			if (content instanceof byte[] a) {
				o.set("content", new TextNode(TextUtilities.base64(a)));
			} else if (content instanceof JsonNode a) {
				o.set("content", a);
			} else {
				throw new IllegalStateException(content.getClass().getName());
			}
			n.add(o);
		}
		
		
		f("ls").forEach(System.out::println);

		return n;
	}

	Stream<String> f(String cmd) throws IOException {
		return 	new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(cmd).getInputStream())).lines(); 
	}



	//private final static JasetoSerializer jaseto = new JasetoSerializer<>(new Jaseto());

	protected abstract String contentType();

	protected abstract Object content();

	public String html() {
		return new String(new JavaResource(getClass(), getClass().getSimpleName()).getByteArray());
	}

	public String name() {
		return getClass().getSimpleName();
	}
}
