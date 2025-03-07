package byransha;

public class IntNode extends ValuedNode<Integer> {

	public IntNode(BBGraph db) {
		super(db);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void fromString(String s) {
		set(Integer.valueOf(s));
	}
}
