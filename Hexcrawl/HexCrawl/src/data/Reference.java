package data;

import java.awt.Point;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reference {
	public static String PARTIAL = "\\{(\\D+):(-?\\d+),(-?\\d+),(\\d+)";
	public static String FULL = PARTIAL+"\\}\\$";
	public static Pattern PATTERN = Pattern.compile(FULL);
	
	HexData type;
	Point point;
	int index;
	
	public Reference(HexData type,Point point,int index) {
		this.type = type;
		this.point = point;
		this.index = index;
	}
	public Reference(String s) {
		Matcher matcher = Reference.PATTERN.matcher(s);
		if(matcher.matches()) {
			this.type = HexData.get(matcher.group(1));
			this.point = new Point(Integer.valueOf(matcher.group(2)),Integer.valueOf(matcher.group(3)));
			this.index = Integer.valueOf(matcher.group(4))-1;
		}else {
			throw new IllegalArgumentException("Unrecognized reference text: "+s);
		}
	}
	public HexData getType() {
		return type;
	}

	public void setType(HexData type) {
		this.type = type;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public String toString() {
		return "{"+type.getText()+":"+point.x+","+point.y+","+(index+1)+"}$";
	}


}
