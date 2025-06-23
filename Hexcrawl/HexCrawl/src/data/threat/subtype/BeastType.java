package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.threat.CreatureSubtype;

public enum BeastType implements CreatureSubtype{
	AWAKENED(1,"AWAKENED"),
	LYCANTHROPE(2,"LYCANTHROPE"),
	DRUID(3,"DRUID");
	

	private static WeightedTable<BeastType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<BeastType>();
		weights.put(AWAKENED, 100);
		weights.put(LYCANTHROPE, 100);
		weights.put(DRUID, 100);
	}
	@Deprecated
	public static BeastType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	public static BeastType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	public static BeastType getFromID(int id) {
		id = id%BeastType.values().length;
		if(id<0) id+=BeastType.values().length;
		BeastType result = null;
		for(BeastType type:BeastType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}
	
	private int id;
	private String name;
	private BeastType(int id, String name) {
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
