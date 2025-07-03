package data.threat.subtype;

import java.util.ArrayList;

import data.Indexible;
import data.WeightedTable;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum OozeType implements CreatureSubtype {
	OBLEX(),
	BLOB_OF_ANNIHILATION(),
	OOZE_MASTER(),
	DRAGON_BLOOD_OOZE(),
	DISEASE(),
	OCHRE_JELLY(),
	BLACK_PUDDING(),
	PSYCHIC_GREY_OOZE(),
	GELATINOUS_CUBE(),
	DRUID(),
	AWAKENED();
	

	private static WeightedTable<OozeType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<OozeType>();
		weights.put(OBLEX, 100);
		weights.put(OOZE_MASTER, 100);
		weights.put(DRUID, 100);
		weights.put(PSYCHIC_GREY_OOZE, 10);
		weights.put(AWAKENED, 10);
		weights.put(BLOB_OF_ANNIHILATION, 1);
	}
	public static OozeType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	@Override
	public ThreatNameGenerator getNameGen() {
		return CreatureType.OOZE.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		ArrayList<Species> list = new ArrayList<Species>();
		switch(this) {
		case BLOB_OF_ANNIHILATION:
		case OOZE_MASTER:
		case OBLEX:
			list.add(OBLEX);
		case DRUID:
		case AWAKENED:
		case PSYCHIC_GREY_OOZE:
		default:
			list.add(CreatureType.HUMANOID);
			list.add(AWAKENED);
			list.add(DRUID);
			list.add(PSYCHIC_GREY_OOZE);
		}
		return list.toArray(new Species[] {});
	}

}
