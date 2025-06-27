package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.threat.CreatureSubtype;

public enum AberrationType implements CreatureSubtype{
	ABOLETH(1,"ABOLETH"),
	BEHOLDER(2,"BEHOLDER"),
	SLAAD(3,"SLAAD"),
	MINDFLAYER(4,"MIND FLAYER"),
	ELDERBRAIN(5,"ELDER BRAIN"),
	ELDERBRAINDRAGON(6,"ELDER BRAIN DRAGON"),
	FEYR(7,"FEYR"),
	GRIMLOCK(8,"GRIMLOCK"),
	STARSPAWN(9,"STAR SPAWN");
	

	private static WeightedTable<AberrationType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<AberrationType>();
		weights.put(ABOLETH, 100);
		weights.put(BEHOLDER, 100);
		weights.put(SLAAD, 100);
		weights.put(ELDERBRAIN, 100);
		weights.put(ELDERBRAINDRAGON, 10);
		weights.put(FEYR, 10);
		weights.put(GRIMLOCK, 10);
		weights.put(STARSPAWN, 10);
	}
	@Deprecated
	public static AberrationType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	public static AberrationType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	public static AberrationType getFromID(int id) {
		id = id%AberrationType.values().length;
		if(id<0) id+=AberrationType.values().length;
		AberrationType result = null;
		for(AberrationType type:AberrationType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}
	
	private int id;
	private String name;
	private AberrationType(int id, String name) {
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
