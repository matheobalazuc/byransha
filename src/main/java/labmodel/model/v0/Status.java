package main.java.labmodel.model.v0;

import main.java.BBGraph;
import main.java.BNode;
import main.java.StringNode;

public class Status extends BNode {
	StringNode name = new StringNode(graph);

	public Status(BBGraph g) {
		super(g);
	}

}
