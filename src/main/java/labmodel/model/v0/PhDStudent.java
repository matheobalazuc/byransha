package main.java.labmodel.model.v0;

import java.util.List;

import main.java.BBGraph;

public class PhDStudent extends Position {
	public PhDStudent(BBGraph g) {
		super(g);
	}

	List<Person> directors;
	Structure team;
}
