package labmodel.model.v0;

import byransha.BNode;
import byransha.ListNode;
import byransha.SetNode;
import byransha.StringNode;

public class Structure extends BNode {
	public StringNode name = new StringNode();
	public SetNode<Structure> subStructures = new SetNode<>();
	public ListNode<Person> members = new ListNode<>();
	public Person director;
	ListNode<Status> status = new ListNode<>();

}