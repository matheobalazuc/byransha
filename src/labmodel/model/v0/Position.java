package labmodel.model.v0;

import byransha.BNode;
import byransha.BBGraph;
import byransha.DateNode;

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
