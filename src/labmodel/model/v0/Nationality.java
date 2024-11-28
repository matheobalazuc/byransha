package labmodel.model.v0;

import java.util.Objects;

import byransha.GOBMNode;

public class Nationality extends GOBMNode {
	public Nationality(String s) {
		this.name = s;
	}

	public final String name;

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nationality other = (Nationality) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return name;
	}

}
