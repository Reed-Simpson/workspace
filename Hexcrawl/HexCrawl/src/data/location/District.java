package data.location;

import data.npc.NPCJobType;

public class District {
	private DistrictType type;
	private String weirdness;
	private LocationType building;
	private NPCJobType job;
	
	public District(DistrictType type) {
		this.type = type;
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
		String typeString;
		if(building!=null) typeString = building.toString();
		else if(job!=null) typeString = job.toString();
		else typeString = type.toString();
		if(weirdness!=null) return weirdness+" "+typeString;
		else return type.toString();
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
