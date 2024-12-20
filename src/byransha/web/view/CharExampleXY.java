package byransha.web.view;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import byransha.BNode;
import byransha.User;
import byransha.web.JSONView;

public class CharExampleXY extends JSONView<BNode> {
	public CharExampleXY()
	{
		sendContentByDefault = true;
	}

	@Override
	protected JsonNode jsonData(BNode n, User u) {
		var r = new ObjectNode(null);
		
		{
			var f = new ObjectNode(null);
			var xs = new ArrayNode(null);
			var ys = new ArrayNode(null);

			for (double x = 0; x < 50; ++x) {
				xs.add(new DoubleNode(x));
				ys.add(new DoubleNode(Math.cos(x)));
			}

			f.set("x", xs);
			f.set("y", xs);
			r.set("cos(x)", f);
		}
		
		{
			var f = new ObjectNode(null);
			var xs = new ArrayNode(null);
			var ys = new ArrayNode(null);

			for (double x = 0; x < 50; ++x) {
				xs.add(new DoubleNode(x));
				ys.add(new DoubleNode(Math.sin(x)));
			}

			f.set("x", xs);
			f.set("y", xs);
			r.set("sin(x)", f);
		}
		
		return r;
	}
	
	@Override
	protected String jsonDialect() {
		return "xy";
	}

}
