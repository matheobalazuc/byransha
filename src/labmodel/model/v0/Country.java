package labmodel.model.v0;

import main.java.BNode;
import main.java.BBGraph;
import main.java.ImageNode;
import main.java.StringNode;

public class Country extends BNode {
	StringNode name = new StringNode(graph);
	ImageNode flag;

	public Country(BBGraph g) {
		super(g);
	}

}
