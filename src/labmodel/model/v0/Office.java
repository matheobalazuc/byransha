package labmodel.model.v0;

import byransha.BNode;
import byransha.IntNode;
import byransha.ListNode;

public class Office extends BNode{
	ListNode<Person> users;
	IntNode surface;
}
