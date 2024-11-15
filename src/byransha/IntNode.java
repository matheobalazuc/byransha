package byransha;

public class IntNode extends ValuedNode<Integer> {

	@Override
	public void fromString(String s) {
		set(Integer.valueOf(s));
	}
}
