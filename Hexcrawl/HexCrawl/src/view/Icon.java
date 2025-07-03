package view;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import data.biome.BiomeType;

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
	

	public static List<Icon> getIcons(BiomeType biome) {
		List<Icon> result = new ArrayList<Icon>();
		Character c = biome.getCh();
		Point offset;
		if(BiomeType.CITY.getCh().equals(c)) {
			offset = new Point(-80,60);
			result.add( new Icon(c, offset, Color.black,2,1f,true));
		}else if(BiomeType.WATER.getCh().equals(c)) {
			offset = new Point(-80,10);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,true));
			offset = new Point(-30,60);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,true));
			offset = new Point(10,-10);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,true));
		}else if(BiomeType.ROCKYHILLS.getCh().equals(c)) {
			offset = new Point(-20,20);
			result.add( new Icon(c, offset, Color.darkGray,1,0.8f,false));
			offset = new Point(40,-20);
			result.add( new Icon(c, offset, Color.darkGray,1,0.8f,false));
		}else if(BiomeType.CLIFFS.getCh().equals(c)) {
			offset = new Point(-60,0);
			result.add( new Icon(c, offset, Color.darkGray,0.7,1f,false));
			offset = new Point(-20,40);
			result.add( new Icon(c, offset, Color.darkGray,0.7,1f,false));
			offset = new Point(20,0);
			result.add( new Icon(c, offset, Color.darkGray,0.7,1f,false));
		}else if(BiomeType.MOUNTAINS.getCh().equals(c)) {
			offset = new Point(-30,30);
			result.add( new Icon(c, offset, Color.black,1,0.5f,true));
			offset = new Point(-70,40);
			result.add( new Icon(c, offset, Color.black,0.8,0.5f,true));
//			offset = new Point(10,10);
//			result.add( new Icon(BiomeType.ROCKYHILLS.getCh(), offset, Color.black,1.5,0.5f));
		}else if(BiomeType.DESERT.getCh().equals(c)) {
			offset = new Point(-60,40);
			result.add( new Icon(c, offset, Color.darkGray,1,0.5f,false));
			offset = new Point(-20,60);
			result.add( new Icon(c, offset, Color.darkGray,1,0.5f,false));
			offset = new Point(20,20);
			result.add( new Icon(c, offset, Color.darkGray,1,0.5f,false));
		}else if(BiomeType.FOREST.getCh().equals(c))  {
			offset = new Point(-60,20);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,true));
			offset = new Point(-20,40);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,true));
			offset = new Point(20,0);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,true));
		}else {
			offset = new Point(-60,20);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,false));
			offset = new Point(-20,40);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,false));
			offset = new Point(20,0);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,false));
		}
		return result;
	}

}
