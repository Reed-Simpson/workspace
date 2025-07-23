package data.encounters;

import data.Indexible;
import util.Util;

public class Encounter extends Indexible {
	private String type;
	private String focus;
	private String[] action;
	private String[] descriptor;
	private String[] location;
	private String[] character;
	private String[] object; 
	private String[] hazard;
	private String[] spice;
	

	public Encounter(float... floats) {
		super(floats);
	}
	public Encounter(int[] ints) {
		super(ints);
	}
	public String getFocus() {
		return focus;
	}
	public String[] getLocation() {
		return location;
	}
	public String[] getCharacter() {
		return character;
	}
	public String[] getObject() {
		return object;
	}
	public void setFocus(String focus) {
		this.focus = focus;
	}
	public void setLocation(String[] location) {
		this.location = location;
	}
	public void setCharacter(String[] character) {
		this.character = character;
	}
	public void setObject(String[] object) {
		this.object = object;
	}
	public String[] getHazard() {
		return hazard;
	}
	public void setHazard(String[] hazard) {
		this.hazard = hazard;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String[] getAction() {
		return action;
	}
	public void setAction(String[] action) {
		this.action = action;
	}
	public String[] getDescriptor() {
		return descriptor;
	}
	public void setDescriptor(String[] descriptor) {
		this.descriptor = descriptor;
	}
	public String[] getSpice() {
		return spice;
	}
	public void setSpice(String[] spice) {
		this.spice = spice;
	}
	
	public String toString() {
		StringBuilder e1Text = new StringBuilder();
		//e1Text.append("~~~~~ "+this.getType()+" Encounter #"+(i)+" ~~~~~\r\n");
		e1Text.append("Focus: "+this.getFocus());
		if(this.getAction()!=null) e1Text.append("\r\nAction: "+Util.parseArray(this.getAction()) +" for "+Util.parseArray(this.getDescriptor()));
		if(this.getLocation()!=null) e1Text.append("\r\nLocation: "+Util.parseArray(this.getLocation(),"and/or"));
		if(this.getCharacter()!=null) e1Text.append("\r\nCharacter: "+Util.parseArray(this.getCharacter(),"and/or"));
		if(this.getObject()!=null) e1Text.append("\r\nObject: "+Util.parseArray(this.getObject()));
		if(this.getHazard()!=null) e1Text.append( "\r\nHazard: "+Util.parseArray(this.getHazard()) );
		if(this.getSpice()!=null) e1Text.append( "\r\nSpice: "+Util.parseArray(this.getSpice()) );
		return e1Text.toString();
	}

}
