package data.dungeon;

import data.Indexible;
import data.Reference;

public class Dungeon extends Indexible{
	private String entrance;
	private Reference location;
	private String form;
	private String layout;
	private String ruination;
	private String reward;
	private String trick;
	private String monster;
	private String hazard;
	private String trap;
	
	
	
	public Dungeon(float... floats) {
		super(floats);
	}
	public Dungeon(int[] ints) {
		super(ints);
	}
	public String getEntrance() {
		return entrance;
	}
	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}
	public Reference getLocation() {
		return location;
	}
	public void setLocation(Reference location) {
		this.location = location;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public String getRuination() {
		return ruination;
	}
	public void setRuination(String ruination) {
		this.ruination = ruination;
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}
	public String getTrick() {
		return trick;
	}
	public void setTrick(String string) {
		this.trick = string;
	}
	
	public String toString() {
		StringBuilder e1Text = new StringBuilder();
		//e1Text.append("~~~~~ Dungeon #"+(i)+" ~~~~~\r\n");
		e1Text.append(this.getEntrance() +" leading to ");
		e1Text.append(this.getLayout()+" "+this.getForm() + " of " + this.getTrick() + "\r\n");
		//e1Text.append("Entrance: "+this.getEntrance() + " near " + this.location.toString() +"\r\n      ");
		e1Text.append("Ruination: "+this.getRuination() + "\r\n");
		e1Text.append("Reward: "+this.getReward() + "\r\n");
		//e1Text.append("Tricks: "+this.getTrick().toLowerCase()+"\r\n");
		e1Text.append("Hazard: "+this.getHazard().toLowerCase()+"\r\n");
		e1Text.append("Traps: "+this.getTrap().toLowerCase()+"\r\n");
		e1Text.append("Monster: "+this.getMonster());
		return e1Text.toString();
	}
	public String getMonster() {
		return monster;
	}
	public void setMonster(String monster) {
		this.monster = monster;
	}
	public String getHazard() {
		return hazard;
	}
	public void setHazard(String hazard) {
		this.hazard = hazard;
	}
	public String getTrap() {
		return trap;
	}
	public void setTrap(String trap) {
		this.trap = trap;
	}

}
