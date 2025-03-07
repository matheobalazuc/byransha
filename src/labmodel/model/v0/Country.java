package labmodel.model.v0;

import byransha.BNode;
import byransha.BBGraph;
import byransha.ImageNode;
import byransha.StringNode;

public class Country extends BNode {
	StringNode name = new StringNode(graph);
	ImageNode flag;

	public Country(BBGraph g) {
		super(g);
	}

}
