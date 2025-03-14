package labmodel.model.v0;

import main.java.BNode;
import main.java.BBGraph;

/*
 * https://cran.r-project.org/web/classifications/ACM.html
 */

public class ACMClassifier extends BNode {
	public String code, descr;

	public ACMClassifier(BBGraph g, String code, String descr) {
		super(g);
		this.code = code;
		this.descr = descr;
	}

	@Override
	public String toString() {
		return code + ": " + descr;
	}

}
