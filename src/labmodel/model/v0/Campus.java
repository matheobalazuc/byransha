package labmodel.model.v0;

import main.java.BNode;
import main.java.BBGraph;
import main.java.ListNode;
import main.java.StringNode;

public class Campus extends BNode {
	public Campus(BBGraph g) {
		super(g);
	}

	StringNode name = new StringNode(graph);
	ListNode<Office> offices = new ListNode<>(graph);

//	ListNode<Building> buildings;
	public Office findOffice(String name) {
		for (var o : offices.l) {
			if (o.name.get().equals(name)) {
				return o;
			}
		}

		return null;
	}
}
