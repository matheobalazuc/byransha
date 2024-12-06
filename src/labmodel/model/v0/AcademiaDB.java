package labmodel.model.v0;

import java.io.File;

import byransha.DB;
import labmodel.I3S;

public class AcademiaDB extends DB {

	public AcademiaDB() {
		this(null);
	}

	public AcademiaDB(File directory) {
		super(directory);
		accept(new I3S());
		accept(new Contract());
		accept(new Person());
		accept(new Nationality());
		accept(new I3S());
		accept(new Building());
		accept(new Campus());
		accept(new Office());
		accept(new Position());
		accept(new Structure());
		accept(new Status());
	}
}
