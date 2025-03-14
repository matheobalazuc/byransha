package main.java;

public class BooleanNode extends ValuedNode<Boolean> {

	public BooleanNode(BBGraph db) {
		super(db);
	}

	@Override
	public void fromString(String s) {
		set(Boolean.valueOf(s));
	}
}
