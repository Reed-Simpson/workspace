package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum ConstructType implements CreatureSubtype {
	MODRON(0,"MODRON"),
	AWAKENED(1,"AWAKENED CONSTRUCT"),
	INEVITABLE(2,"INEVITABLE"),
	ARTIFICER(3,"ARTIFICER GENERAL"),
	RELIC(4,"ANCIENT RELIC");
	

	private static WeightedTable<ConstructType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<ConstructType>();
		weights.put(MODRON, 100);
		weights.put(ARTIFICER, 100);
		weights.put(RELIC, 100);
		weights.put(AWAKENED, 10);
		weights.put(INEVITABLE, 10);
	}
	@Deprecated
	public static ConstructType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	public static ConstructType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	public static ConstructType getFromID(int id) {
		id = id%ConstructType.values().length;
		if(id<0) id+=ConstructType.values().length;
		ConstructType result = null;
		for(ConstructType type:ConstructType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}
	
	private int id;
	private String name;
	private ConstructType(int id, String name) {
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
		return CreatureType.CONSTRUCT.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		switch(this) {
		case INEVITABLE:
		case MODRON: return new Species[]{MODRON,INEVITABLE};
		case RELIC:
		case ARTIFICER:
		case AWAKENED:
		default:
			return new Species[]{null,this,CreatureType.HUMANOID,ARTIFICER,AWAKENED,RELIC};
		}
	}

}
