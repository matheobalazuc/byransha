package main.java.labmodel.model.v0;

import main.java.BBGraph;

public class EPST extends Structure {

	EPST(BBGraph g) {
		super(g);
		status.add(new IR(g));
		status.add(new CR(g));
		status.add(new DR(g));
	}
}
