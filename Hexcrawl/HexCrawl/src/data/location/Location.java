package data.location;

import java.util.ArrayList;

import data.Indexible;
import data.Reference;
import util.Util;

public class Location extends Indexible {
	String name;
	String[] descriptors;
	String visibility;
	LocationType type;
	String quirk;
	Reference proprietor;
	ArrayList<Reference> dungeons;

	
	public Location(float... floats) {
		super(floats);
	}
	public Location(int... index) {
		super(index);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getDescriptors() {
		return descriptors;
	}
	public void setDescriptors(String[] descriptors) {
		this.descriptors = descriptors;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public LocationType getType() {
		return type;
	}
	public void setType(LocationType type) {
		this.type = type;
	}
	public String getQuirk() {
		return quirk;
	}
	public void setQuirk(String quirk) {
		this.quirk = quirk;
	}
	public Reference getProprietor() {
		return proprietor;
	}
	public void setProprietor(Reference proprietor) {
		this.proprietor = proprietor;
	}
	
	public ArrayList<Reference> getDungeons() {
		return dungeons;
	}
	public void setDungeons(ArrayList<Reference> dungeons) {
		this.dungeons = dungeons;
	}
	public String toString() {
		String result = Util.parseArray(this.descriptors)+" "+this.type.toString();
		if(name!=null) result+="\r\nName: "+this.name;
		if(visibility!=null) result+="\r\nVisibility: "+this.visibility;
		if(quirk!=null) result+="\r\nQuirk: "+this.quirk;
		if(proprietor!=null) result+="\r\nProprietor: "+this.proprietor.toString();
		if(dungeons!=null&&!dungeons.isEmpty()) {
			if(dungeons.size()>0) {
				for(Reference s:dungeons) {
					result+="\r\n"+s.toString();
				}
			}
		}
		return result;
	}

}
