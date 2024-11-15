package byransha;

public class EmailNode extends StringNode {

	public static final String re = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

	@Override
	public String id() {
		return get();
	}
}
