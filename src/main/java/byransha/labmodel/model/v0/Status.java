package byransha.labmodel.model.v0;

import byransha.BBGraph;
import byransha.BNode;
import byransha.StringNode;

public class Status extends BNode {
	StringNode name = new StringNode(graph);

	public Status(BBGraph g) {
		super(g);
	}

	@Override
	public String getDescription() {
		return "Status node";
	}
}
