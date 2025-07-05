package view;

import java.awt.Color;
import java.awt.Point;

public class Icon {
	Character ch;
	Point offset;
	Color c;
	double scale;
	float opacity;
	boolean top;
	
	public Icon(Character ch,Point offset,Color c,double scale,float opacity,boolean top) {
		this.ch = ch;
		this.offset = offset;
		this.c = c;
		this.scale = scale;
		this.opacity = opacity;
		this.top = top;
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
	
	public float getOpacity() {
		return opacity;
	}
	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}
	


}
