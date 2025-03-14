package main.java;

public class EmailNode extends StringNode {

	public EmailNode(BBGraph g, String s) {
		super(g, s);
	}

	public static final String re = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

}
