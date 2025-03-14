package labmodel.model.v0.event;

import main.java.Event;
import labmodel.model.v0.Lab;
import labmodel.model.v0.Person;

public class NewStaffMember extends Event<Lab> {
	public NewStaffMember(Lab target) {
		super(target);
	}

	public Person p;

	@Override
	public void apply(Lab lab) {
		lab.members.add(p);
	}

	@Override
	public void undo(Lab lab) {
		lab.members.remove(p);
	}

}
