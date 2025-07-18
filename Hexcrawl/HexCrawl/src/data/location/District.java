package data.location;

import java.awt.Point;

import data.HexData;
import data.Reference;
import data.npc.NPCJobType;

public class District {
	private DistrictType type;
	private String weirdness;
	private LocationType building;
	private NPCJobType job;
	private int index;
	private Point pos;
	
	public District(DistrictType type,int index,Point pos) {
		this.type = type;
		this.index = index;
		this.pos = pos;
	}

	public String getWeirdness() {
		return weirdness;
	}

	public void setWeirdness(String weirdness) {
		this.weirdness = weirdness;
	}
	
	public DistrictType getType() {
		return type;
	}

	public void setType(DistrictType type) {
		this.type = type;
	}

	public String toString() {
		String result;
		if(building!=null) result = building.toString();
		else if(job!=null) result = job.toString();
		else result = type.toString();
		if(weirdness!=null) result = weirdness+" "+result;
		result+="\r\n"+new Reference(HexData.LOCATION,pos,index+1);
		return result;
	}

	public LocationType getBuilding() {
		return building;
	}

	public void setBuilding(LocationType building) {
		this.building = building;
	}

	public NPCJobType getJob() {
		return job;
	}

	public void setJob(NPCJobType job) {
		this.job = job;
	}

}
