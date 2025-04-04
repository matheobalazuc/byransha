package byransha;

public class DateNode extends StringNode {

	public DateNode(BBGraph g, String v) {
		super(g, v);
	}

	public DateNode(BBGraph g, int id) {
		super(g, id);
	}

	@Override
	public void fromString(String s) {
		set(s);
	}
}
