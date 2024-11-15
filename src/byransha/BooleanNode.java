package byransha;

public class BooleanNode extends ValuedNode<Boolean> {

	@Override
	public void fromString(String s) {
		set(Boolean.valueOf(s));
	}
}
