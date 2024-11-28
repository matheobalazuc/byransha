package labmodel.model.v0;

import byransha.GOBMNode;
import byransha.ListNode;
import byransha.SetNode;
import byransha.StringNode;

public class Structure extends GOBMNode {
	public StringNode name = new StringNode();
	public SetNode<Structure> subStructures = new SetNode<>();
	public ListNode<Person> members = new ListNode<>();

	@Override
	public String id() {
		return name.get();
	}
}