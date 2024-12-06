package labmodel.model.v0;

import byransha.BNode;
import byransha.BooleanNode;
import byransha.EmailNode;
import byransha.ImageNode;
import byransha.ListNode;
import byransha.StringNode;

public class Person extends BNode {
	public StringNode name = new StringNode();
	private ListNode<Position> positions = new ListNode<>();
	private ListNode<ImageNode> pics = new ListNode<>();
	private BooleanNode hdr = new BooleanNode();
	private Nationality nationality;

	protected ListNode<EmailNode> emailAddresses;
	protected ListNode<ACMClassifier> topics;

	@Override
	public String toString() {
		return name.get();
	}
}
