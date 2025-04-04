package byransha.labmodel.model.v0;

import byransha.BNode;
import byransha.BBGraph;
import byransha.ListNode;
import byransha.StringNode;

public class Campus extends BNode {
	ListNode<Office> offices = new ListNode<>(graph);

  public Campus(BBGraph g) {
		super(g);
	}

	@Override
	public String getDescription() {
		return "Campus: " + name.get();
	}


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
