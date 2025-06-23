package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.threat.CreatureSubtype;

public enum MonstrosityType implements CreatureSubtype {
	SPHINX(0,"SPHINX"),
	MANTICORE(1,"MANTICORE"),
	MEDUSA(2,"MEDUSA"),
	MIMIC(3,"MIMIC"),
	MINOTAUR(4,"MINOTAUR"),
	BASILISK(5,"BASILISK"),
	BULETTE(6,"BULETTE"),
	OWLBEAR(7,"OWLBEAR"),
	NAGA(8,"NAGA"),
	PURPLEWORM(9,"PURPLE WORM"),
	PHASESPIDER(10,"PHASE SPIDER"),
	CHIMERA(11,"CHIMERA"),
	COCKATRICE(12,"COCKATRICE"),
	RUSTMONSTER(13,"RUST MONSTER"),
	REMORHAZ(14,"REMORHAZ"),
	DISPLACERBEAST(15,"DISPLACER BEAST"),
	DOPPELGANGER(16,"DOPPELGANGER"),
	TERRASQUE(17,"TERRASQUE"),
	GRIFFON(18,"GRIFFON"),
	HARPY(19,"HARPY"),
	HYDRA(20,"HYDRA"),
	LAMIA(21,"LAMIA"),
	YUANTI(22,"YUANTI");
	

	private static WeightedTable<MonstrosityType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<MonstrosityType>();
		weights.put(SPHINX, 100);
		weights.put(MEDUSA, 100);
		weights.put(MIMIC, 100);
		weights.put(MINOTAUR, 100);
		weights.put(NAGA, 100);
		weights.put(PURPLEWORM, 100);
		weights.put(PHASESPIDER, 100);
		weights.put(RUSTMONSTER, 100);
		weights.put(DOPPELGANGER, 100);
		weights.put(HARPY, 100);
		weights.put(HYDRA, 100);
		weights.put(YUANTI, 100);
		weights.put(BASILISK, 10);
		weights.put(MANTICORE, 10);
		weights.put(GRIFFON, 10);
		weights.put(REMORHAZ, 10);
		weights.put(DISPLACERBEAST, 10);
		weights.put(CHIMERA, 10);
		weights.put(COCKATRICE, 10);
		weights.put(BULETTE, 10);
		weights.put(OWLBEAR, 10);
		weights.put(LAMIA, 10);
		weights.put(TERRASQUE, 1);
	}
	@Deprecated
	public static MonstrosityType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	public static MonstrosityType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	public static MonstrosityType getFromID(int id) {
		id = id%MonstrosityType.values().length;
		if(id<0) id+=MonstrosityType.values().length;
		MonstrosityType result = null;
		for(MonstrosityType type:MonstrosityType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}
	
	private int id;
	private String name;
	private MonstrosityType(int id, String name) {
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
