package byransha.labmodel.model.v0;

import byransha.BNode;
import byransha.BBGraph;
import byransha.ListNode;
import byransha.StringNode;

public class Publication extends BNode {
	public Publication(BBGraph g) {
		super(g);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getDescription() {
		return "Publication: " + title.get();
	}

	public StringNode title = new StringNode(graph);
	private ListNode<Person> positions = new ListNode<>(graph);

	@Override
	public String toString() {
		return title.get();
	}
}
