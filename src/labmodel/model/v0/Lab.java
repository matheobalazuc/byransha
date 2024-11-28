package labmodel.model.v0;

import java.util.HashMap;
import java.util.Map;

public class Lab extends Structure {
	Person HFDS;

	enum HFDSAvisE {
		YES, NO, INBETWEEN
	}

	Map<Person, HFDSAvisE> HFDSAvisfds = new HashMap<>();
}
