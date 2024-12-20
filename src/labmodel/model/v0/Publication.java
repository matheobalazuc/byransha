package labmodel.model.v0;

import byransha.BNode;
import byransha.ListNode;
import byransha.StringNode;

public class Publication extends BNode {
	public StringNode title = new StringNode();
	private ListNode<Person> positions = new ListNode<>();

	@Override
	public String toString() {
		return title.get();
	}
}
