package labmodel.model.v0;

import main.java.BNode;
import main.java.BBGraph;
import main.java.StringNode;

public class EtatCivil extends BNode {

	public StringNode firstName = new StringNode(graph);
	public StringNode name = new StringNode(graph);
	public StringNode familyNameBeforeMariage = new StringNode(graph);
	public StringNode birthDate = new StringNode(graph);
	public StringNode cityOfBirth = new StringNode(graph);
	public StringNode nationality = new StringNode(graph);
	public StringNode countryOfBirth = new StringNode(graph);
	public StringNode address = new StringNode(graph);

	public EtatCivil(BBGraph g) {
		super(g);
		// TODO Auto-generated constructor stub
	}
}
