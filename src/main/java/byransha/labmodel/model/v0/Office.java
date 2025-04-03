package byransha.labmodel.model.v0;

import byransha.BNode;
import byransha.BBGraph;
import byransha.IntNode;
import byransha.ListNode;
import byransha.StringNode;

public class Office extends BNode {
	public Office(BBGraph g) {
		super(g);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getDescription() {
		return "Office: " + (name != null ? name.get() : "Unnamed");
	}

	StringNode name;
	ListNode<Person> users;
	IntNode surface;
	IntNode capacity;

	public double occupationRatio() {
		return ((double) capacity.get()) / users.size();
	}

	public double surfacePerUser() {
		return ((double) surface.get()) / users.size();
	}
}
