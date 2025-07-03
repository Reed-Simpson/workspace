package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum PlantType implements CreatureSubtype {
	AWAKENED(),
	TREANT(),
	DRUID(),
	MYCONID(),
	BODYTAKER();
	

	private static WeightedTable<PlantType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<PlantType>();
		weights.put(AWAKENED, 100);
		weights.put(TREANT, 100);
		weights.put(DRUID, 100);
		weights.put(MYCONID, 100);
		weights.put(BODYTAKER, 100);
	}
	public static PlantType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	@Override
	public ThreatNameGenerator getNameGen() {
		return CreatureType.PLANT.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		switch(this) {
		case BODYTAKER:
		case MYCONID:
			return new Species[] {this};
		case DRUID:
		case AWAKENED:
		case TREANT:
		default:
			return new Species[]{DRUID,AWAKENED,TREANT};
		}
	}

}
