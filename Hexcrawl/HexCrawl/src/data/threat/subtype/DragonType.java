package data.threat.subtype;

import data.WeightedTable;
import data.threat.CreatureSubtype;

public enum DragonType implements CreatureSubtype {
	BLACK(0,"BLACK"),
	BLUE(1,"BLUE"),
	GREEN(2,"GREEN"),
	RED(3,"RED"),
	WHITE(4,"WHITE"),
	BRASS(5,"BRASS"),
	BRONZE(6,"BRONZE"),
	COPPER(7,"COPPER"),
	GOLD(8,"GOLD"),
	SILVER(9,"SILVER"),
	AMETHYST(10,"AMETHYST"),
	CRYSTAL(11,"CRYSTAL"),
	EMERALD(12,"EMERALD"),
	SAPPHIRE(13,"SAPPHIRE"),
	TOPAZ(14,"TOPAZ"),
	DEEP(15,"DEEP"),
	MOONSTONE(16,"MOONSTONE"),
	SHADOW(17,"SHADOW"),
	DRACOLICH(18,"DRACOLICH");
	

	private static WeightedTable<DragonType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<DragonType>();
		weights.put(BLACK, 100);
		weights.put(BLUE, 100);
		weights.put(GREEN, 100);
		weights.put(RED, 100);
		weights.put(WHITE, 100);
		weights.put(BRASS, 10);
		weights.put(BRONZE, 10);
		weights.put(COPPER, 10);
		weights.put(GOLD, 10);
		weights.put(SILVER, 10);
		weights.put(AMETHYST, 1);
		weights.put(CRYSTAL, 1);
		weights.put(EMERALD, 1);
		weights.put(SAPPHIRE, 1);
		weights.put(TOPAZ, 1);
		weights.put(DEEP, 1);
		weights.put(MOONSTONE, 1);
		weights.put(SHADOW, 1);
		weights.put(DRACOLICH, 1);
	}
	
	public static DragonType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	public static DragonType getFromID(int id) {
		id = id%DragonType.values().length;
		if(id<0) id+=DragonType.values().length;
		DragonType result = null;
		for(DragonType type:DragonType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}
	
	private int id;
	private String name;
	private DragonType(int id, String name) {
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

}
