package threat.subtype;

import general.WeightedTable;
import threat.CreatureSubtype;

public enum ElementalType implements CreatureSubtype {
	FIRE(1,"FIRE"),
	WATER(2,"WATER"),
	EARTH(3,"EARTH"),
	AIR(4,"AIR"),
	MAGMA(5,"MAGMA"),
	OOZE(6,"OOZE"),
	ICE(7,"ICE"),
	LIGHTNING(8,"LIGHTNING"),
	VOID(9,"VOID"),
	SALT(10,"SALT"),
	ENTROPY(11,"ENTROPY"),
	CHAOS(12,"CHAOS"),
	SPACE(13,"SPACE"),
	MIND(14,"MIND"),
	EMOTION(15,"EMOTION"),
	RADIANT(16,"RADIANT"),
	BLOOD(17,"BLOOD"),
	NECROTIC(18,"NECROTIC"),
	RAGE(19,"RAGE"),
	SOUND(20,"SOUND"),
	STEAM(21,"STEAM"),
	SAND(22,"SAND");
	

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
	
	public static ElementalType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
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

}
