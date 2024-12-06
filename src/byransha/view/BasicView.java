package byransha.view;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import byransha.BNode;
import byransha.JSONView;
import byransha.User;

final public class BasicView extends JSONView<BNode> {

	public BasicView() {
		sendContentByDefault = true;
	}

	@Override
	public String name() {
		return "basic info";
	}

	@Override
	protected String openWith() {
		return "boh";
	}

	@Override
	public ObjectNode jsonData(BNode node, User u) {
		var n = new ObjectNode(null);
		n.set("class", new TextNode(node.getClass().getName()));
		n.set("id", new TextNode("" + node.id()));
		n.set("comment", new TextNode(node.comment));
		n.set("directory", new TextNode(node.directory().getAbsolutePath()));
		n.set("out-degree", new TextNode("" + node.outDegree()));
		n.set("outs", new TextNode(node.outs().entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).toList().toString()));
		n.set("views", new TextNode(node.compliantViews().stream().map(v -> v.name()).toList().toString()));
		n.set("in-degree)", new TextNode("" + node.refs().size()));
		n.set("#refs", new TextNode(node.refs().stream().map(e -> e.toString()).toList().toString()));
		n.set("canSee", new TextNode("" + node.canSee(u)));
		n.set("canEdit", new TextNode("" + node.canEdit(u)));
		return n;
	}
	

}