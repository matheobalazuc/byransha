package byransha.labmodel.model.v0;

import java.util.List;

import byransha.BNode;
import byransha.BBGraph;
import byransha.ListNode;
import byransha.StringNode;

public class Contract extends BNode {
	public Contract(BBGraph g) {
		super(g);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getDescription() {
		return "Contract Node";
	}

	StringNode name;
	Person holder;
	List<Person> subHolders;
	ListNode<Person> coordinators;
	ListNode<Person> partners;
	ListNode<Person> misc;

}
