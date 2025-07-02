package data.threat.subtype;

import java.util.ArrayList;

import data.Indexible;
import data.WeightedTable;
import data.population.NPCSpecies;
import data.population.Species;
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
	WIGHT(11,"GHAST"),
	WRAITH(12,"WRAITH"),
	DEATHLOCK(13,"DEATHLOCK");

	private static WeightedTable<UndeadType> weights;

	private static void populateWeights() {
		weights = new WeightedTable<UndeadType>();
		weights.put(LICH, 100);
		weights.put(VAMPIRE, 100);
		weights.put(MUMMYLORD, 100);
		weights.put(DEATHTYRANT, 100);
		weights.put(WIGHT, 100);
		weights.put(WRAITH, 100);
		weights.put(DEATHKNIGHT, 100);
		weights.put(DRACOLICH, 10);
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
	@Override
	public Species[] getMinionSpeciesList() {
		ArrayList<Species> list = new ArrayList<Species>();
		list.add(null);
		switch(this) {
		case DEATHTYRANT: return AberrationType.BEHOLDER.getMinionSpeciesList();
		case MUMMYLORD: list.add(MUMMYLORD);break;
		case GHOSTDRAGON:
		case DRACOLICH:
			list.add(this);
			list.add(NPCSpecies.KOBOLD);
			list.add(NPCSpecies.DRAGONBORN);
			list.add(NPCSpecies.LIZARDFOLK);
			break;
		case NIGHTWALKER: list.add(NIGHTWALKER);
		case DEMILICH: list.add(DEMILICH);
		case LICH: list.add(LICH);
		case DEATHKNIGHT: list.add(DEATHKNIGHT);
		case VAMPIRE: {
			list.add(VAMPIRE);
			list.add(null);
			list.add(CreatureType.HUMANOID);
		}
		case WRAITH:  
		case WIGHT: 
		default:{
		}
		list.add(HumanoidType.SPELLCASTER);
		list.add(HumanoidType.CULTIST);
		list.add(WRAITH);
		list.add(WIGHT);
		list.add(GHOST);
		list.add(DEATHLOCK);
		}
		return list.toArray(new Species[] {});
	}

}
