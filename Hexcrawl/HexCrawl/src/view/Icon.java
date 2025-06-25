package view;

import java.awt.Color;
import java.awt.Point;

public class Icon {
	Character ch;
	Point offset;
	Color c;
	double scale;
	
	public Icon(Character ch,Point offset,Color c,double scale) {
		this.ch = ch;
		this.offset = offset;
		this.c = c;
		this.scale = scale;
	}
	public Character getCh() {
		return ch;
	}

	public Point getOffset() {
		return offset;
	}

	public Color getC() {
		return c;
	}
	public double getScale() {
		return scale;
	}

}
