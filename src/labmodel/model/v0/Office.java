package labmodel.model.v0;

import byransha.BNode;
import byransha.IntNode;
import byransha.ListNode;

public class Office extends BNode {
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
