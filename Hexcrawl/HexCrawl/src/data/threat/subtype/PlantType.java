package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum PlantType implements CreatureSubtype {
	AWAKENED(0,"AWAKENED"),
	TREANT(1,"TREANT"),
	DRUID(2,"DRUID"),
	MYCONID(3,"MYCONID"),
	BODYTAKER(4,"BODYTAKER");
	

	private static WeightedTable<PlantType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<PlantType>();
		weights.put(AWAKENED, 100);
		weights.put(TREANT, 100);
		weights.put(DRUID, 100);
		weights.put(MYCONID, 100);
		weights.put(BODYTAKER, 100);
	}
	@Deprecated
	public static PlantType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	public static PlantType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	public static PlantType getFromID(int id) {
		id = id%PlantType.values().length;
		if(id<0) id+=PlantType.values().length;
		PlantType result = null;
		for(PlantType type:PlantType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}
	
	private int id;
	private String name;
	private PlantType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	@Override
	public ThreatNameGenerator getNameGen() {
		return CreatureType.PLANT.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		switch(this) {
		case DRUID:
			return new Species[]{null};
		default:
			return new Species[]{this};
		}
	}

}
