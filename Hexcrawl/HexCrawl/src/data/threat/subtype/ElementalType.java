package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum ElementalType implements CreatureSubtype {
	FIRE(1,"FIRE"),
	WATER(2,"WATER"),
	EARTH(3,"EARTH"),
	AIR(4,"AIR"),
	MAGMA(5,"MAGMA"),//fire+earth
	OOZE(6,"OOZE"),//water+earth
	ICE(7,"ICE"),//water+air
	LIGHTNING(8,"LIGHTNING"),//fire+air
	
	VOID(9,"VOID"),//air
	SALT(10,"SALT"),//water
	ENTROPY(11,"ENTROPY"),//water
	CHAOS(12,"CHAOS"),//fire
	SPACE(13,"SPACE"),//earth
	MIND(14,"MIND"),
	EMOTION(15,"EMOTION"),
	RADIANT(16,"RADIANT"),//fire
	BLOOD(17,"BLOOD"),
	NECROTIC(18,"NECROTIC"),//earth
	RAGE(19,"RAGE"),
	SOUND(20,"SOUND"),//air
	
	STEAM(21,"STEAM"),//water+fire
	SAND(22,"SAND");//air+earth
	

	private static WeightedTable<ElementalType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<ElementalType>();
		weights.put(FIRE, 500);
		weights.put(WATER, 500);
		weights.put(EARTH, 500);
		weights.put(AIR, 500);
		weights.put(MAGMA, 100);
		weights.put(OOZE, 100);
		weights.put(ICE, 100);
		weights.put(LIGHTNING, 100);
		weights.put(VOID, 1);
		weights.put(SALT, 1);
		weights.put(ENTROPY, 1);
		weights.put(CHAOS, 1);
		weights.put(SPACE, 1);
		weights.put(MIND, 1);
		weights.put(EMOTION, 1);
		weights.put(RADIANT, 1);
		weights.put(BLOOD, 1);
		weights.put(NECROTIC, 1);
		weights.put(RAGE, 1);
		weights.put(SOUND, 1);
		weights.put(STEAM, 1);
		weights.put(SAND, 1);
	}
	
	@Deprecated
	public static ElementalType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	public static ElementalType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	
	private int id;
	private String name;
	private ElementalType(int id, String name) {
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
	public static ElementalType getFromID(int id) {
		id = id%ElementalType.values().length;
		if(id<0) id+=ElementalType.values().length;
		ElementalType result = null;
		for(ElementalType type:ElementalType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}
	@Override
	public ThreatNameGenerator getNameGen() {
		return CreatureType.ELEMENTAL.getNameGen();
	}

}
