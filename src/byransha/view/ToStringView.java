package byransha.view;

import byransha.GOBMNode;
import byransha.User;
import byransha.View;

public class ToStringView extends View<GOBMNode> {

	@Override
	public byte[] content(GOBMNode nn, User u) {
		return nn.toString().getBytes();
	}

	@Override
	protected String contentType() {
		return "text/plain";
	}

	@Override
	public String name() {
		return "toString()";
	}

}