package byransha.labmodel.model.v0;

import byransha.BNode;
import byransha.BBGraph;
import byransha.ListNode;
import byransha.StringNode;

public class Campus extends BNode {
	public Campus(BBGraph g) {
		super(g);
	}

	StringNode name = new StringNode(graph, null);
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
