package labmodel.model.v0;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byransha.View;
import labmodel.model.v0.view.LabView;

public class Lab extends Structure {
	public Person HFDS;

	enum HFDSAvisE {
		YES, NO, INBETWEEN
	}

	Map<Person, HFDSAvisE> HFDSAvisfds = new HashMap<>();

	@Override
	public void views(List<View> l) {
		super.views(l);
		l.add(new LabView());
	}

}
