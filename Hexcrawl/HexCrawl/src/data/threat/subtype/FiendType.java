package data.threat.subtype;

import data.WeightedTable;
import data.threat.CreatureSubtype;

public enum FiendType implements CreatureSubtype {
	DEVIL(0,"DEVIL"),
	DEMON(1,"DEMON"),
	YUGOLOTH(2,"YUGOLOTH"),
	OBYRITH(3,"OBYRITH"),
	LOUMARA(4,"LOUMARA"),
	RAKSHASA(5,"RAKSHASA");
	

	private static WeightedTable<FiendType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<FiendType>();
		weights.put(DEVIL, 100);
		weights.put(DEMON, 100);
		weights.put(RAKSHASA, 50);
		weights.put(YUGOLOTH, 20);
		weights.put(LOUMARA, 20);
		weights.put(OBYRITH, 1);
	}
	
	public static FiendType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	public static FiendType getFromID(int id) {
		id = id%FiendType.values().length;
		if(id<0) id+=FiendType.values().length;
		FiendType result = null;
		for(FiendType type:FiendType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}
	
	private int id;
	private String name;
	private FiendType(int id, String name) {
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
