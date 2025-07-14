package data.population;

import java.awt.Point;
import java.util.ArrayList;

import controllers.DataController;
import data.HexData;
import data.Indexible;
import data.Reference;
import names.FactionType;

public class Settlement extends Indexible {
	private String reputation;
	private FactionType leadership;
	private String theme;
	private String event;
	private Point pos;
	private ArrayList<String> districts;
	private ArrayList<Point> neighbors;
	private transient DataController controller;
	
	public Settlement(float... vals) {
		super(vals);
	}
	
	public Settlement(int[] vals) {
		super(vals);
	}
	public String getReputation() {
		return reputation;
	}

	public void setReputation(String reputation) {
		this.reputation = reputation;
	}

	public FactionType getLeadership() {
		return leadership;
	}

	public void setLeadership(FactionType leadership) {
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
		Reference factionref = new Reference(HexData.FACTION,controller.getRecord().normalizePOS(pos),0);
		factionref.setText(this.getLeadership().toString());
		c1Text.append("City Leadership: "+this.getReputation()+" "+factionref+"\r\n");
		c1Text.append("   Leader: "+new Reference(HexData.FACTION_NPC,controller.getRecord().normalizePOS(pos),0)+"\r\n");
		if(this.getEvent()!=null) c1Text.append("City Event: "+this.getEvent()+"\r\n");
		c1Text.append("~~~~~ Districts ~~~~~\r\n");
		for(String s:districts) {
			c1Text.append(s+"\r\n");
		}
		c1Text.append("~~~~~ Neighbors ~~~~~\r\n");
		if(neighbors!=null) {
			if(neighbors.isEmpty()) {
				c1Text.append("None");
			}
			for(Point p:neighbors) {
				c1Text.append(controller.getTownInfotext(pos,p));
				String relationship = controller.getSettlements().getRelationship(pos, p,true);
				c1Text.append("    "+relationship+"\r\n");
			}
		}else {
			c1Text.append("...Loading...");
		}
		return c1Text.toString();
	}

	public ArrayList<Point> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(ArrayList<Point> neighbors) {
		this.neighbors = neighbors;
	}

	public DataController getController() {
		return controller;
	}

	public void setController(DataController controller) {
		this.controller = controller;
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

}
