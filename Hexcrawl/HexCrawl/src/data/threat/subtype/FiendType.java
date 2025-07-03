package data.threat.subtype;

import java.util.ArrayList;

import data.Indexible;
import data.WeightedTable;
import data.population.NPCSpecies;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum FiendType implements CreatureSubtype {
	DEVIL(),
	DEMON(),
	YUGOLOTH(),
	OBYRITH(),
	LOUMARA(),
	RAKSHASA();
	

	private static WeightedTable<FiendType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<FiendType>();
		weights.put(DEVIL, 100);
		weights.put(DEMON, 100);
		weights.put(RAKSHASA, 50);
		weights.put(YUGOLOTH, 20);
		weights.put(LOUMARA, 20);
		weights.put(OBYRITH, 1);
	}
	public static FiendType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	
	@Override
	public ThreatNameGenerator getNameGen() {
		return CreatureType.FIEND.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		ArrayList<Species> list = new ArrayList<Species>();
		list.add(this);
		list.add(null);
		list.add(NPCSpecies.TIEFLING);
		list.add(CreatureType.HUMANOID);
		switch(this) {
		case YUGOLOTH:{
			list.add(DEMON);
			list.add(DEVIL);
			list.add(LOUMARA);
			list.add(RAKSHASA);
			break;
		}
		case OBYRITH:{
			list.add(DEMON);
			list.add(DEVIL);
			list.add(LOUMARA);
			list.add(RAKSHASA);
			list.add(YUGOLOTH);
			break;
		}
		case RAKSHASA:
			list.add(DEVIL);
			list.add(YUGOLOTH);break;
		case DEMON:
			list.add(LOUMARA);
			list.add(YUGOLOTH);break;
		case DEVIL:
			list.add(RAKSHASA);
			list.add(YUGOLOTH);break;
		case LOUMARA:
			list.add(DEMON);
			list.add(YUGOLOTH);break;
		}
		return list.toArray(new Species[] {});
	}

}
