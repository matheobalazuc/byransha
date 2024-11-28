package labmodel.model.v0.event;

import java.util.List;

import byransha.Event;
import labmodel.model.v0.Lab;
import labmodel.model.v0.Person;
import toools.exceptions.NotYetImplementedException;

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

	@Override
	public void initFromCSV(List<String> a, Lab s) {
		throw new NotYetImplementedException();
	}

}
