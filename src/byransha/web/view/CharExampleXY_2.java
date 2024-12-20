package byransha.web.view;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import byransha.BNode;
import byransha.User;
import byransha.web.JSONView;

public class CharExampleXY_2 extends JSONView<BNode> {
	public CharExampleXY_2()
	{
		sendContentByDefault = true;
	}

	@Override
	protected JsonNode jsonData(BNode n, User u) {
		var r = new ObjectNode(null);

		{
			var f = new ObjectNode(null);
			var data = new ObjectNode(null);

			for (double x = 0; x < 50; ++x) {
				data.set("" + x, new DoubleNode(Math.cos(x)));
			}

			f.set("data", data);
			r.set("cos(x)", f);
		}

		{
			var f = new ObjectNode(null);
			var data = new ObjectNode(null);

			for (double x = 10; x < 30; ++x) {
				data.set("" + x, new DoubleNode(Math.sin(x)));
			}

			f.set("data", data);
			r.set("sin(x)", f);
		}
		return r;
	}
	
	@Override
	protected String jsonDialect() {
		return "xy2";
	}

}
