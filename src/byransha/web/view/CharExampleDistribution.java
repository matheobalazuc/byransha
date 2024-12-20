package byransha.web.view;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import byransha.BNode;
import byransha.User;
import byransha.web.JSONView;

public class CharExampleDistribution extends JSONView<BNode> {

	public CharExampleDistribution() {
		sendContentByDefault = true;
	}

	@Override
	protected JsonNode jsonData(BNode n, User u) {
		var r = new ObjectNode(null);

		{
			var g = new ObjectNode(null);

			for (int x = 0; x < 50; ++x) {
				g.set("value" + x, new DoubleNode(Math.random()));
			}

			r.set("group 1", g);
		}

		{
			var g = new ObjectNode(null);

			for (int x = 0; x < 10; ++x) {
				g.set("value" + x, new DoubleNode(Math.random()));
			}

			r.set("group 2", g);
		}

		return r;
	}

	@Override
	protected String jsonDialect() {
		return "distribution";
	}

}
