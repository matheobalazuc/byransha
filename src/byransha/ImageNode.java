package byransha;

import java.util.Base64;

public class ImageNode extends ValuedNode<byte[]> {

	public ImageNode(BBGraph db) {
		super(db);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void fromString(String s) {
		set(Base64.getDecoder().decode(s.getBytes()));
	}
}
