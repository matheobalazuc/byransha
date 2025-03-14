package byransha.graph;

import java.awt.Color;

public class BGElement {
	public String label;
	public String color = color(Color.gray);
	public String style;

	private String color(Color c) {
		return "rgb(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")";
	}
}
