package labmodel.model.v0;

import byransha.BooleanNode;
import byransha.EmailNode;
import byransha.GOBMNode;
import byransha.ListNode;
import byransha.StringNode;

public class Person extends GOBMNode {
	public StringNode name = new StringNode();
	private ListNode<Position> positions = new ListNode<>();
	private BooleanNode hdr = new BooleanNode();

	protected ListNode<EmailNode> emailAddresses;

	@Override
	public String toString() {
		return name.get();
	}
}
