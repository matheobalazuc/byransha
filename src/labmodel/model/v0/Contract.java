package labmodel.model.v0;

import java.util.List;
import java.util.function.Consumer;

import byransha.GOBMNode;
import byransha.ListNode;
import byransha.StringNode;

public class Contract  extends GOBMNode{
	StringNode name;
	Person holder;
	List<Person> subHolders;
	ListNode<Person> coordinators;
	ListNode<Person> partners;
	ListNode<Person> misc;

	public void forEachMember(Consumer<Person> c) {

	}
}
