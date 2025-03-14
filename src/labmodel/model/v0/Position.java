package labmodel.model.v0;

import main.java.BNode;
import main.java.BBGraph;
import main.java.DateNode;

public class Position extends BNode {
	public Position(BBGraph g) {
		super(g);
		// TODO Auto-generated constructor stub
	}
	Structure employer;
	DateNode from;
	DateNode to;
	Status status;
}
