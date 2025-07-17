package data.location;

import data.Indexible;
import data.npc.NPCJobType;
import util.Util;

public enum DistrictType {
	Catacombs,civilized_npc,Construction,Crafts,Criminality,Culture,Dining,Education,Entertainment,Finance,Foreigners,Ghettoes,
	Government,Graveyards,Green_Space,Industrialization,Judgement,Livestock,Marketplace,Memorials,Military,Opulence,Pollution,Poverty,
	Punishment,Religion,Science,Trade,Trash,underworld_npc,upper_class_building,lower_class_building,Vices,wilderness_npc,Wizardry,Wonders;


	public static DistrictType getDistrict(Indexible obj) {
		DistrictType result = (DistrictType) Util.getElementFromArray(DistrictType.values(), obj);
		switch(result) {
		case upper_class_building: result.setBuilding(LocationType.getUCBuilding(obj));
		case lower_class_building: result.setBuilding(LocationType.getLCBuilding(obj));
		case civilized_npc: result.setJob(NPCJobType.getCivilized(obj));
		case underworld_npc: result.setJob(NPCJobType.getUnderworld(obj));
		case wilderness_npc: result.setJob(NPCJobType.getWilderness(obj));
		default: break;
		}
		return result;
	}

	private NPCJobType job;
	private LocationType building;


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

	public String toString() {
		if(building!=null) return building.toString();
		else if(job!=null) return job.toString();
		else return Util.replace(this.name(), "_", " ");
	}


}
