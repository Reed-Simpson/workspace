package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum ConstructType implements CreatureSubtype {
	MODRON(),
	AWAKENED_CONSTRUCT(),
	INEVITABLE(),
	ARTIFICER_GENERAL(),
	ANCIENT_RELIC();
	

	private static WeightedTable<ConstructType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<ConstructType>();
		weights.put(MODRON, 100);
		weights.put(ARTIFICER_GENERAL, 100);
		weights.put(ANCIENT_RELIC, 100);
		weights.put(AWAKENED_CONSTRUCT, 10);
		weights.put(INEVITABLE, 10);
	}
	public static ConstructType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	
	@Override
	public ThreatNameGenerator getNameGen() {
		return CreatureType.CONSTRUCT.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		switch(this) {
		case INEVITABLE:
		case MODRON: return new Species[]{MODRON,INEVITABLE};
		case ANCIENT_RELIC:
		case ARTIFICER_GENERAL:
		case AWAKENED_CONSTRUCT:
		default:
			return new Species[]{null,this,CreatureType.HUMANOID,ARTIFICER_GENERAL,AWAKENED_CONSTRUCT,ANCIENT_RELIC};
		}
	}

}
