package data.population;

import java.util.ArrayList;

import data.Indexible;

public class Settlement extends Indexible {
	private String leadership;
	private String theme;
	private String event;
	private ArrayList<String> districts;
	
	public Settlement(float... vals) {
		super(vals);
	}
	
	public Settlement(int[] vals) {
		super(vals);
	}

	public String getLeadership() {
		return leadership;
	}
	public void setLeadership(String leadership) {
		this.leadership = leadership;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public ArrayList<String> getDistricts() {
		return districts;
	}
	public void setDistricts(ArrayList<String> districts) {
		this.districts = districts;
	}
	public void putDistrict(String district) {
		if(this.districts==null) this.districts = new ArrayList<String>();
		this.districts.add(district);
	}
	
	public String toString() {
		StringBuilder c1Text = new StringBuilder();
		c1Text.append("City Theme: "+this.getTheme()+"\r\n");
		c1Text.append("City Leadership: "+this.getLeadership()+"\r\n");
		c1Text.append("City Event: "+this.getEvent()+"\r\n");
		c1Text.append("~~~~~ Districts ~~~~~\r\n");
		for(String s:districts) {
			c1Text.append(s+"\r\n");
		}
		return c1Text.toString();
		
	}

}
