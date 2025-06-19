package view;

import java.awt.Color;

public enum HighlightColor {
	None(null),
	Red(Color.red),
	Orange(Color.orange),
	Yellow(Color.yellow),
	Green(Color.getHSBColor(115f/360f, 100f/100, 100f/100)),
	Cyan(Color.cyan),
	Blue(Color.getHSBColor(250f/360f, 100f/100, 90f/100)),
	Purple(Color.getHSBColor(280f/360f, 100f/100, 100f/100)),
	Magenta(Color.magenta);
	
	Color color;
	private HighlightColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

}
