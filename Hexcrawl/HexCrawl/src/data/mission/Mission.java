package data.mission;

import data.Indexible;
import data.Reference;
import util.Util;

public class Mission extends Indexible{
	String verb;
	Reference questgiver;
	Reference antagonist;
	Reference location;
	String[] object;
	

	public Mission(float... floats) {
		super(floats);
	}
	public Mission(int... index) {
		super(index);
	}
	
	
	public String getVerb() {
		return verb;
	}
	public void setVerb(String verb) {
		this.verb = verb;
	}
	public Reference getQuestgiver() {
		return questgiver;
	}
	public void setQuestgiver(Reference questgiver) {
		this.questgiver = questgiver;
	}
	public Reference getAntagonist() {
		return antagonist;
	}
	public void setAntagonist(Reference antagonist) {
		this.antagonist = antagonist;
	}
	public Reference getLocation() {
		return location;
	}
	public void setLocation(Reference location) {
		this.location = location;
	}
	public String[] getObject() {
		return object;
	}
	public void setObject(String[] object) {
		this.object = object;
	}
	
	public String toString() {
		StringBuilder c1Text = new StringBuilder();
		c1Text.append(verb).append(" ").append(Util.parseArray(object));
		if(questgiver!=null) c1Text.append("\r\n").append("Questgiver:").append(questgiver.toString());
		if(antagonist!=null) c1Text.append("\r\n").append("Antagonist:").append(antagonist.toString());
		if(location!=null) c1Text.append("\r\n").append("Location:").append(location.toString());
		return c1Text.toString();
	}

}
