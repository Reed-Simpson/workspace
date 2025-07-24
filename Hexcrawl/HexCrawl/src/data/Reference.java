package data;

import java.awt.Point;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controllers.DataController;
import io.SaveRecord;

public class Reference implements Serializable{
	private static final long serialVersionUID = 3816076390422175045L;
	public static String PARTIAL = "\\{(\\w+):(-?\\d+),(-?\\d+),(\\d+)(?:\\|(.+))?";
	public static String FULL = PARTIAL+"\\}[\\$x]";
	public static Pattern PATTERN = Pattern.compile(FULL);
	public static Pattern LINKDETECT = Pattern.compile("(\\{\\w+\\:\\d+,\\d+,\\d+(?:\\|.+)?}[\\$x])");
	
	HexData type;
	Point point;
	int index;
	String text;
	boolean active;
	
	public Reference(HexData type,Point point,int index) {
		this.type = type;
		this.point = point;
		this.index = index;
		this.active = true;
	}
	public Reference(String s) {
		Matcher matcher = Reference.PATTERN.matcher(s);
		if(matcher.matches()) {
			this.type = HexData.get(matcher.group(1));
			this.point = new Point(Integer.valueOf(matcher.group(2)),Integer.valueOf(matcher.group(3)));
			this.index = Integer.valueOf(matcher.group(4))-1;
			this.text = matcher.group(5);
			if(s.endsWith("x")) {
				active = false;
			}else {
				active = true;
			}
		}else {
			throw new IllegalArgumentException("Unrecognized reference text: "+s);
		}
	}
	public Reference(HexData type,Point point,Indexible obj,SaveRecord record) {
		this.type = type;
		if(record!=null) this.point = record.normalizePOS(point);
		else this.point = point;
		int count = type.getCount();
		if(HexData.CHARACTER.equals(type)) count = record.getCampaignCharacterCount();
		else if(HexData.THREAD.equals(type)) count = record.getCampaignThreadCount();
		if(count<=0) this.index = -1;
		else this.index = obj.reduceTempId(count);
		this.active = true;
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
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String toString() {
		String text = "";
		if(this.text!=null) text = "|"+this.text;
		String suffix = "x";
		if(active) suffix = "$";
		return "{"+type.getText()+":"+point.x+","+point.y+","+(index+1)+text+"}"+suffix;
	}
	
	public String getLinkText(DataController controller) {
		return controller.getLinkText(this);
	}


}
