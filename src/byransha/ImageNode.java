package byransha;

import java.util.Base64;

public class ImageNode extends ValuedNode<byte[]> {

	@Override
	public void fromString(String s) {
		set(Base64.getDecoder().decode(s.getBytes()));
	}
}
