package labmodel.model.v0;

import byransha.BNode;

/*
 * https://cran.r-project.org/web/classifications/ACM.html
 */

public class ACMClassifier extends BNode {
	public String code, descr;

	public ACMClassifier(String code, String descr) {
		this.code = code;
		this.descr = descr;
	}

	@Override
	public String toString() {
		return code + ": " + descr;
	}

}
