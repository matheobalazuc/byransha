package labmodel.model.v0;

import java.util.List;

import byransha.BNode;
import byransha.ListNode;
import byransha.SetNode;
import byransha.StringNode;
import byransha.View;
import labmodel.model.v0.view.StructureView;

public class Structure extends BNode {
	public StringNode name = new StringNode();
	public SetNode<Structure> subStructures = new SetNode<>();
	public ListNode<Person> members = new ListNode<>();
	public Person director;
	public ListNode<Status> status = new ListNode<>();
	public ListNode<Office> offices = new ListNode<>();

	public double occupationRatio() {
		return offices.l.stream().mapToDouble(o -> o.occupationRatio()).average().getAsDouble();
	}

	public double avgSurfacePerUser() {
		return offices.l.stream().mapToDouble(o -> o.surfacePerUser()).average().getAsDouble();
	}

	public double totalSurface() {
		return offices.l.stream().mapToDouble(o -> o.surface.get()).sum();
	}

	@Override
	public void views(List<View> l) {
		super.views(l);
		l.add(new StructureView());
	}
}