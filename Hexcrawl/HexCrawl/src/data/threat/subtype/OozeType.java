package data.threat.subtype;

import java.util.ArrayList;

import data.Indexible;
import data.WeightedTable;
import data.population.Species;
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
	PSYCHICGREYOOZE(7,"PSYCHIC GREY OOZE"),
	GELATINOUSCUBE(8,"GELATINOUS CUBE"),
	DRUID(9,"DRUID"),
	AWAKENED(10,"AWAKENED");
	

	private static WeightedTable<OozeType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<OozeType>();
		weights.put(OBLEX, 100);
		weights.put(OOZEMASTER, 100);
		weights.put(DRUID, 100);
		weights.put(PSYCHICGREYOOZE, 10);
		weights.put(AWAKENED, 10);
		weights.put(BLOBOFANNIHILATION, 1);
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
	@Override
	public Species[] getMinionSpeciesList() {
		ArrayList<Species> list = new ArrayList<Species>();
		switch(this) {
		case BLOBOFANNIHILATION:
		case OOZEMASTER:
		case OBLEX:
			list.add(OBLEX);
		case DRUID:
		case AWAKENED:
		case PSYCHICGREYOOZE:
		default:
			list.add(CreatureType.HUMANOID);
			list.add(AWAKENED);
			list.add(DRUID);
			list.add(PSYCHICGREYOOZE);
		}
		return list.toArray(new Species[] {});
	}

}
