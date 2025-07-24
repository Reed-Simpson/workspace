package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum MonstrosityType implements CreatureSubtype {
	SPHINX(),
	MEDUSA(),
	MIMIC(),
	MINOTAUR(),
	NAGA(),
	DOPPELGANGER(),
	TERRASQUE(),
	HARPY(),
	LAMIA(),
	YUANTI();
	

	private static WeightedTable<MonstrosityType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<MonstrosityType>();
		weights.put(SPHINX, 100);
		weights.put(MEDUSA, 100);
		weights.put(MIMIC, 100);
		weights.put(MINOTAUR, 100);
		weights.put(NAGA, 100);
		weights.put(DOPPELGANGER, 100);
		weights.put(HARPY, 100);
		weights.put(YUANTI, 100);
		weights.put(LAMIA, 10);
		weights.put(TERRASQUE, 1);
	}
	public static MonstrosityType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	@Override
	public ThreatNameGenerator getNameGen() {
		return CreatureType.MONSTROSITY.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		return new Species[]{this};
	}

}
