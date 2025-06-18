package data.dungeon;

import data.Indexible;
import util.Util;

public class Dungeon extends Indexible{
	private String entrance;
	private String form;
	private String layout;
	private String ruination;
	private String reward;
	private String[] trick;
	private String monster;
	
	
	
	public Dungeon(float... floats) {
		super(floats);
	}
	public String getEntrance() {
		return entrance;
	}
	public void setEntrance(String entrance) {
		this.entrance = entrance;
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
	public String[] getTrick() {
		return trick;
	}
	public void setTrick(String[] trick) {
		this.trick = trick;
	}
	
	public String toString(int i) {
		StringBuilder e1Text = new StringBuilder();
		//e1Text.append("~~~~~ Dungeon #"+(i)+" ~~~~~\r\n");
		e1Text.append("Layout & Form: "+this.getLayout()+" "+this.getForm() + "\r\n");
		e1Text.append("Entrance: "+this.getEntrance() +"\r\n");
		//e1Text.append("Layout: "+this.getLayout() + "\r\n");
		e1Text.append("Ruination: "+this.getRuination() + "\r\n");
		e1Text.append("Reward: "+this.getReward() + "\r\n");
		e1Text.append("Tricks: "+Util.parseArray(this.getTrick()).toLowerCase()+"\r\n");
		e1Text.append("Monster: "+this.getMonster());
		return e1Text.toString();
	}
	public String getMonster() {
		return monster;
	}
	public void setMonster(String monster) {
		this.monster = monster;
	}

}
