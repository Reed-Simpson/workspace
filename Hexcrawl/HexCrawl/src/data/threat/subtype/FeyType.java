package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum FeyType implements CreatureSubtype {
	SEALIE(),
	UNSEALIE(),
	HAG(),
	SATYR(),
	CENTAUR();
	

	private static WeightedTable<FeyType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<FeyType>();
		weights.put(SEALIE, 100);
		weights.put(UNSEALIE, 100);
		weights.put(HAG, 100);
		weights.put(SATYR, 10);
		weights.put(CENTAUR, 10);
	}
	public static FeyType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	
	
	@Override
	public ThreatNameGenerator getNameGen() {
		return CreatureType.FEY.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		return new Species[]{this};
	}

}
