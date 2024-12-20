package labmodel.model.v0;

import java.util.HashMap;
import java.util.Map;

import byransha.web.View;
import labmodel.model.v0.view.LabView;

public class Lab extends Structure {

	static {
		View.views.add(new LabView());
	}

	public Person HFDS;

	enum HFDSAvisE {
		YES, NO, INBETWEEN
	}

	Map<Person, HFDSAvisE> HFDSAvisfds = new HashMap<>();

}
