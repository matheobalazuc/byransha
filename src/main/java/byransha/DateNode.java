package byransha;

public class DateNode extends StringNode {

	public DateNode(BBGraph g, String v) {
		super(g, v);
	}

	@Override
	public void fromString(String s) {
		set(s);
	}
}
