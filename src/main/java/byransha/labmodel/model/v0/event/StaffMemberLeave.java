package byransha.labmodel.model.v0.event;

import byransha.Event;
import byransha.labmodel.model.v0.Lab;
import byransha.labmodel.model.v0.Person;

public class StaffMemberLeave extends Event<Lab> {
	public StaffMemberLeave(Lab target) {
		super(target);
	}

	public Person m;

	@Override
	public void apply(Lab lab) {
		lab.members.remove(m);
	}

	@Override
	public void undo(Lab lab) {
		lab.members.add(m);
	}

}
