package main.java.labmodel.model.v0;

import main.java.BBGraph;
import main.java.ListNode;

public class University extends Structure {

	ListNode<Campus> campuses = new ListNode<Campus>(graph);

	public University(BBGraph g) {
		super(g);
		status.add(new IGR(g));
		status.add(new MCF(g));
		status.add(new PR(g));
	}
}
