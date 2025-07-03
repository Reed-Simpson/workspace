package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum BeastType implements CreatureSubtype{
	AWAKENED(),
	LYCANTHROPE(),
	DRUID();
	

	private static WeightedTable<BeastType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<BeastType>();
		weights.put(AWAKENED, 100);
		weights.put(LYCANTHROPE, 100);
		weights.put(DRUID, 100);
	}
	public static BeastType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	
	
	@Override
	public ThreatNameGenerator getNameGen() {
		return CreatureType.BEAST.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		switch(this) {
		case DRUID:
		case LYCANTHROPE:
		case AWAKENED:
		default:
			return new Species[]{DRUID,LYCANTHROPE,AWAKENED};
		}
	}

}
