package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum OozeType implements CreatureSubtype {
	OBLEX(0,"OBLEX"),
	BLOBOFANNIHILATION(1,"BLOB OF ANNIHILATION"),
	OOZEMASTER(2,"OOZE MASTER"),
	DRAGONBLOODOOZE(3,"DRAGON BLOOD OOZE"),
	DISEASE(4,"PLAGUE"),
	OCHREJELLY(5,"OCHRE JELLY"),
	BLACKPUDDING(6,"BLACK PUDDING"),
	GREYOOZE(7,"GREY OOZE"),
	GELATINOUSCUBE(8,"GELATINOUS CUBE"),
	DRUID(9,"DRUID");
	

	private static WeightedTable<OozeType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<OozeType>();
		weights.put(DISEASE, 200);
		weights.put(OBLEX, 100);
		weights.put(OOZEMASTER, 100);
		weights.put(DRAGONBLOODOOZE, 100);
		weights.put(DRUID, 100);
		weights.put(OCHREJELLY, 10);
		weights.put(BLACKPUDDING, 10);
		weights.put(GREYOOZE, 10);
		weights.put(GELATINOUSCUBE, 10);
		weights.put(BLOBOFANNIHILATION, 1);
	}
	@Deprecated
	public static OozeType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	public static OozeType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	public static OozeType getFromID(int id) {
		id = id%OozeType.values().length;
		if(id<0) id+=OozeType.values().length;
		OozeType result = null;
		for(OozeType type:OozeType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}
	
	private int id;
	private String name;
	private OozeType(int id, String name) {
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
		return CreatureType.OOZE.getNameGen();
	}

}
