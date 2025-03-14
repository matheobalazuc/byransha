package main.java.graph;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.BNode;

public class BGraph {
	public List<BVertex> nodes = new ArrayList<BVertex>();
	public List<BLinks> links = new ArrayList<BLinks>();

	public static void main(String[] args) {
		var g = new BGraph();
		g.newArc(new BVertex("a"), new BVertex("b"));
		System.out.println(g.toNivoJSON().toPrettyString());
	}

	public BLinks newArc(BVertex v, BVertex w) {
		nodes.add(v);
		nodes.add(w);

		var a = new BLinks(v, w);
		links.add(a);
		return a;
	}

	public JsonNode toNivoJSON() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}

	public void addVertex(BVertex bVertex) {
		nodes.add(bVertex);
	}

	public BVertex findVertexByID(String id) {
		for (var v : nodes) {
			if (v.id.equals(id)) {
				return v;
			}
		}
		return null;
	}

	public BVertex ensureHasVertex(BNode o) {
		var v = findVertexByID("" + o.id());

		if (v == null) {
			addVertex(v = o.toVertex());
		}

		return v;
	}
}
