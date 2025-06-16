package threat.subtype;

import general.WeightedTable;
import threat.CreatureSubtype;

public enum ConstructType implements CreatureSubtype {
	MODRON(0,"MODRON"),
	AWAKENED(1,"AWAKENED CONSTRUCT"),
	INEVITABLE(2,"INEVITABLE"),
	ARTIFICER(3,"ARTIFICER GENERAL"),
	RELIC(4,"ANCIENT RELIC");
	

	private static WeightedTable<ConstructType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<ConstructType>();
		weights.put(MODRON, 100);
		weights.put(ARTIFICER, 100);
		weights.put(RELIC, 100);
		weights.put(AWAKENED, 10);
		weights.put(INEVITABLE, 10);
	}
	
	public static ConstructType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	public static ConstructType getFromID(int id) {
		id = id%ConstructType.values().length;
		if(id<0) id+=ConstructType.values().length;
		ConstructType result = null;
		for(ConstructType type:ConstructType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}
	
	private int id;
	private String name;
	private ConstructType(int id, String name) {
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
