package byransha.graph;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import byransha.BNode;

public class BGraph {
	public List<BVertex> nodes = new ArrayList<BVertex>();
	public List<BLinks> links = new ArrayList<BLinks>();

	public static void main(String[] args) {
		var g = new BGraph();
		g.newArc(new BVertex("a"), new BVertex("b"));
		System.out.println(g.toNivoJSON().toPrettyString());
	}

	public synchronized BLinks newArc(BVertex v, BVertex w) {
		synchronized (nodes) {
			nodes.add(v);
			nodes.add(w);
		}

		synchronized (links) {
			var a = new BLinks(v, w);
			links.add(a);
			return a;
		}
	}

	public synchronized JsonNode toNivoJSON() {
		synchronized (nodes) {
			synchronized (links) {
				ObjectMapper mapper = new ObjectMapper();
				return mapper.valueToTree(this);
			}
		}
	}

	public synchronized void addVertex(BVertex bVertex) {
		synchronized (nodes) {
			nodes.add(bVertex);
		}
	}

	public synchronized BVertex findVertexByID(String id) {
		synchronized (nodes) {
			for (var v : nodes) {
				if (v.id.equals(id)) {
					return v;
				}
			}
			return null;
		}
	}

	public synchronized BVertex ensureHasVertex(BNode o) {
		var v = findVertexByID("" + o.id());

		if (v == null) {
			addVertex(v = o.toVertex());
		}

		return v;
	}
}
