package main.java.labmodel.model.v0;

import main.java.BNode;
import main.java.BBGraph;
import main.java.ListNode;
import main.java.SetNode;
import main.java.StringNode;

public class Structure extends BNode {
	public StringNode name = new StringNode(graph);
	public SetNode<Structure> subStructures = new SetNode<>(graph);
	public ListNode<Person> members = new ListNode<>(graph);
	public Person director;
	public ListNode<Status> status = new ListNode<>(graph);
	public ListNode<Office> offices = new ListNode<>(graph);

	public Structure(BBGraph g) {
		super(g);
	}

	public double occupationRatio() {
		return offices.l.stream().mapToDouble(o -> o.occupationRatio()).average().getAsDouble();
	}

	public double avgSurfacePerUser() {
		return offices.l.stream().mapToDouble(o -> o.surfacePerUser()).average().getAsDouble();
	}

	public double totalSurface() {
		return offices.l.stream().mapToDouble(o -> o.surface.get()).sum();
	}

}