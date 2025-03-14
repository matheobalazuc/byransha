package main.java.labmodel.model.v0;

import java.util.List;

import main.java.BNode;
import main.java.BBGraph;
import main.java.ListNode;
import main.java.StringNode;

public class Contract extends BNode {
	public Contract(BBGraph g) {
		super(g);
		// TODO Auto-generated constructor stub
	}

	StringNode name;
	Person holder;
	List<Person> subHolders;
	ListNode<Person> coordinators;
	ListNode<Person> partners;
	ListNode<Person> misc;

}
