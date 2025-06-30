package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum UndeadType implements CreatureSubtype {
	LICH(0,"LICH"),
	VAMPIRE(1,"VAMPIRE"),
	MUMMYLORD(2,"MUMMY LORD"),
	NIGHTWALKER(3,"NIGHTWALKER"),
	SKULLLORD(4,"SKULL LORD"),
	DEATHKNIGHT(5,"DEATH KNIGHT"),
	DEATHTYRANT(6,"DEATH TYRANT"),
	DEMILICH(7,"DEMILICH"),
	DRACOLICH(8,"DRACOLICH"),
	GHOSTDRAGON(9,"GHOST DRAGON"),
	GHOST(10,"GHOST"),
	GHAST(11,"GHAST"),
	WRAITH(12,"WRAITH");

	private static WeightedTable<UndeadType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<UndeadType>();
		weights.put(LICH, 100);
		weights.put(VAMPIRE, 100);
		weights.put(MUMMYLORD, 100);
		weights.put(DEATHTYRANT, 100);
		weights.put(GHAST, 100);
		weights.put(WRAITH, 100);
		weights.put(SKULLLORD, 50);
		weights.put(DEATHKNIGHT, 50);
		weights.put(DRACOLICH, 10);
		weights.put(GHOST, 10);
		weights.put(NIGHTWALKER, 1);
		weights.put(DEMILICH, 1);
		weights.put(GHOSTDRAGON, 1);
	}
	@Deprecated
	public static UndeadType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	public static UndeadType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	public static UndeadType getFromID(int id) {
		id = id%UndeadType.values().length;
		if(id<0) id+=UndeadType.values().length;
		UndeadType result = null;
		for(UndeadType type:UndeadType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}
	
	private int id;
	private String name;
	private UndeadType(int id, String name) {
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
		return CreatureType.UNDEAD.getNameGen();
	}

}
