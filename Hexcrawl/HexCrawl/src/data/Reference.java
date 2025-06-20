package data;

import java.awt.Point;

public class Reference {
	HexData type;
	Point point;
	int index;
	
	public Reference(HexData type,Point point,int index) {
		this.type = type;
		this.point = point;
		this.index = index;
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


}
