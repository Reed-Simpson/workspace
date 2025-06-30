package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum CelestialType implements CreatureSubtype{
	ANGEL(0,"ANGEL"),
	ARCHON(1,"ARCHON"),
	GUARDINAL(2,"GUARDINAL"),
	AZATA(3,"AZATA"),
	COUATL(4,"COUATL"),
	UNICORN(5,"UNICORN"),
	PEGASI(6,"PEGASI");
	

	private static WeightedTable<CelestialType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<CelestialType>();
		weights.put(ANGEL, 100);
		weights.put(ARCHON, 100);
		weights.put(GUARDINAL, 100);
		weights.put(AZATA, 100);
		weights.put(COUATL, 10);
		weights.put(UNICORN, 10);
		weights.put(PEGASI, 10);
	}
	@Deprecated
	public static CelestialType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	public static CelestialType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	public static CelestialType getFromID(int id) {
		id = id%CelestialType.values().length;
		if(id<0) id+=CelestialType.values().length;
		CelestialType result = null;
		for(CelestialType type:CelestialType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}
	
	private int id;
	private String name;
	private CelestialType(int id, String name) {
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
		return CreatureType.CELESTIAL.getNameGen();
	}

}
