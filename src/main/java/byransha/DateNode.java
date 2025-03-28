package byransha;

public class DateNode extends StringNode {

	public DateNode(BBGraph g, String v) {
		super(g, v);
	}

	public DateNode(BBGraph g) {
		super(g);
	}

	@Override
	public void fromString(String s) {
		set(s);
	}
}
