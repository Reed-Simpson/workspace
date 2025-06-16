package threat.subtype;

import general.WeightedTable;
import threat.CreatureSubtype;

public enum FeyType implements CreatureSubtype {
	SEALIE(0,"SEALIE"),
	UNSEALIE(1,"UNSEALIE"),
	HAG(2,"HAG"),
	SATYR(3,"SATYR"),
	CENTAUR(4,"CENTAUR");
	

	private static WeightedTable<FeyType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<FeyType>();
		weights.put(SEALIE, 100);
		weights.put(UNSEALIE, 100);
		weights.put(HAG, 100);
		weights.put(SATYR, 10);
		weights.put(CENTAUR, 10);
	}
	
	public static FeyType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	public static FeyType getFromID(int id) {
		id = id%FeyType.values().length;
		if(id<0) id+=FeyType.values().length;
		FeyType result = null;
		for(FeyType type:FeyType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}
	
	private int id;
	private String name;
	private FeyType(int id, String name) {
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
