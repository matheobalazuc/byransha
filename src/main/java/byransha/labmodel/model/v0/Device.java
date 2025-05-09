package byransha.labmodel.model.v0;

import byransha.BNode;
import byransha.BBGraph;
import byransha.StringNode;

public class Device extends BNode {
	public Device(BBGraph g) {
		super(g);
		// TODO Auto-generated constructor stub
	}

	public StringNode serialNumber;

	@Override
	public String getDescription() {
		return "Device with serial number: " + (serialNumber != null ? serialNumber.toString() : "unknown");
	}
}
