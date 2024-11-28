package labmodel.model.v0;

/*
 * https://cran.r-project.org/web/classifications/ACM.html
 */

public class ACMClassifier {
	public String code, descr;

	public ACMClassifier(String code, String descr) {
		this.code = code;
		this.descr = descr;
	}

	@Override
	public boolean equals(Object obj) {
		return code.equals(((ACMClassifier) obj).code);
	}

	@Override
	public String toString() {
		return code + ": " + descr;
	}

	@Override
	public int hashCode() {
		return code.hashCode();
	}

}
