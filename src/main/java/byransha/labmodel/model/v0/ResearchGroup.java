package byransha.labmodel.model.v0;

import byransha.BBGraph;
import byransha.ListNode;

public class ResearchGroup extends Structure {
	public ResearchGroup(BBGraph g) {
		super(g);
		// TODO Auto-generated constructor stub
	}

	ListNode<ACMClassifier> keywords = new ListNode<>(graph);
}
