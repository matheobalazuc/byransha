package main.java.labmodel.model.v0;

import main.java.BNode;
import main.java.BBGraph;
import main.java.IntNode;
import main.java.ListNode;
import main.java.StringNode;

public class Office extends BNode {
	public Office(BBGraph g) {
		super(g);
		// TODO Auto-generated constructor stub
	}

	StringNode name;
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
