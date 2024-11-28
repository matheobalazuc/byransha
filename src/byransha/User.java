package byransha;

public class User {
	public User(String u, boolean admin) {
		this.name = u;
		this.admin = admin;
	}

	String name;
	boolean admin;
	public boolean isAdmin() {
		return admin;
	}

}
