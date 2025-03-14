package main.java;

public class StringNode extends ValuedNode<String> {

	public StringNode(BBGraph g) {
		super(g);

	}

	public StringNode(BBGraph g, String init) {
		this(g);
		set(init);
	}

	@Override
	public void fromString(String s) {
		set(s);
	}
}
