package data.threat.subtype;

import java.util.ArrayList;

import data.Indexible;
import data.WeightedTable;
import data.population.NPCSpecies;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum GiantType implements CreatureSubtype {
	FIRE(0,"FIRE"),
	STORM(1,"STORM"),
	FROST(2,"FROST"),
	CLOUD(3,"CLOUD"),
	HILL(4,"HILL"),
	STONE(5,"STONE"),
	TITAN(6,"TITAN"),
	ETTIN(7,"ETTIN"),
	OGRE(8,"OGRE"),
	CYCLOPS(9,"CYCLOPS"),
	FOMORIAN(10,"FOMORIAN");
	

	private static WeightedTable<GiantType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<GiantType>();
		weights.put(FIRE, 100);
		weights.put(STORM, 100);
		weights.put(FROST, 100);
		weights.put(CLOUD, 100);
		weights.put(HILL, 100);
		weights.put(STONE, 100);
		weights.put(ETTIN, 50);
		weights.put(OGRE, 50);
		weights.put(FOMORIAN, 50);
		weights.put(TITAN, 1);
	}
	@Deprecated
	public static GiantType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	public static GiantType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	public static GiantType getFromID(int id) {
		id = id%GiantType.values().length;
		if(id<0) id+=GiantType.values().length;
		GiantType result = null;
		for(GiantType type:GiantType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}
	
	private int id;
	private String name;
	private GiantType(int id, String name) {
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
		return CreatureType.GIANT.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		ArrayList<Species> list = new ArrayList<Species>();
		list.add(this);
		list.add(NPCSpecies.GOLIATH);
		switch(this) {
		case TITAN:
			list.add(STORM);
		case STORM:
			list.add(CLOUD);
		case CLOUD:
			list.add(FIRE);
		case FIRE:
			list.add(FROST);
		case FROST:
			list.add(STONE);
		case STONE:
			list.add(HILL);
		case HILL:
			list.add(ETTIN);
		case ETTIN:
			list.add(FOMORIAN);
		case FOMORIAN:
			list.add(OGRE);
		case OGRE:
			list.add(NPCSpecies.GOBLINOID);
		default:
		}
		return list.toArray(new Species[] {});
	}

}
