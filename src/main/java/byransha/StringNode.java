package byransha;

public class StringNode extends ValuedNode<String> {

	public StringNode(BBGraph g, String init) {
		this(g);
		set(init);
	}

	public StringNode(BBGraph g) {
		super(g);
	}

	@Override
	public void fromString(String s) {
		set(s);
	}

	public void set(String newValue) {
		super.set(newValue);
	}

	public String get() {
		return super.get();
	}

}
