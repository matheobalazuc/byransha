package labmodel.model.v0;

import main.java.BNode;
import main.java.BBGraph;
import main.java.ListNode;
import main.java.StringNode;

public class Publication extends BNode {
	public Publication(BBGraph g) {
		super(g);
		// TODO Auto-generated constructor stub
	}

	public StringNode title = new StringNode(graph);
	private ListNode<Person> positions = new ListNode<>(graph);

	@Override
	public String toString() {
		return title.get();
	}
}
