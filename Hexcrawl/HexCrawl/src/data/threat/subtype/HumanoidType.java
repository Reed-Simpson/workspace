package data.threat.subtype;

import data.WeightedTable;
import data.threat.CreatureSubtype;

public enum HumanoidType implements CreatureSubtype {
	ORC(0,"ORC"),
	LIZARDFOLK(1,"LIZARDFOLK"),
	GOBLINOID(2,"GOBLINOID"),
	DROW(3,"DROW"),
	DUREGAR(4,"DUREGAR"),
	GNOLL(5,"GNOLL"),
	TROGLODYTE(6,"TROGLODYTE"),
	LYCANTHROPE(7,"LYCANTHROPE"),
	KOBOLD(8,"KOBOLD"),
	BANDIT(9,"BANDIT"),
	CULTIST(10,"CULTIST"),
	WARLORD(11,"WARLORD"),
	SPELLCASTER(12,"SPELLCASTER");
	

	private static WeightedTable<HumanoidType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<HumanoidType>();
		weights.put(CULTIST, 100);
		weights.put(WARLORD, 100);
		weights.put(SPELLCASTER, 100);
		weights.put(BANDIT, 30);
		weights.put(GOBLINOID, 30);
		weights.put(KOBOLD, 30);
		weights.put(ORC, 10);
		weights.put(LIZARDFOLK, 10);
		weights.put(DROW, 10);
		weights.put(DUREGAR, 10);
		weights.put(GNOLL, 10);
		weights.put(TROGLODYTE, 10);
		weights.put(LYCANTHROPE, 10);
	}

	public static HumanoidType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	public static HumanoidType getFromID(int id) {
		id = id%HumanoidType.values().length;
		if(id<0) id+=HumanoidType.values().length;
		HumanoidType result = null;
		for(HumanoidType type:HumanoidType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}
	
	private int id;
	private String name;
	private HumanoidType(int id, String name) {
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
