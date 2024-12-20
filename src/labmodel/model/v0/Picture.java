package labmodel.model.v0;

import byransha.OSNode.View;
import byransha.User;
import byransha.ValuedNode;
import byransha.web.EndpointBinaryResponse;

public class Picture extends ValuedNode<byte[]> {

	static {
		View.views.add(new V());
	}

	public static class V extends byransha.web.View<Picture> {

		@Override
		public EndpointBinaryResponse content(Picture p, User user) {
			return new EndpointBinaryResponse(p.get(), "image/jpeg");
		}
	}

	@Override
	public void fromString(String s) {
	}
}
