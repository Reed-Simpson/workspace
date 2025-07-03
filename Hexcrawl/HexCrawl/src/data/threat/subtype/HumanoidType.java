package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum HumanoidType implements CreatureSubtype {
	ORC(),
	LIZARDFOLK(),
	GOBLINOID(),
	DROW(),
	DUREGAR(),
	GNOLL(),
	TROGLODYTE(),
	LYCANTHROPE(),
	KOBOLD(),
	BANDIT(),
	CULTIST(),
	WARLORD(),
	SPELLCASTER();
	

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

	public static HumanoidType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	
	@Override
	public ThreatNameGenerator getNameGen() {
		return CreatureType.HUMANOID.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		switch(this) {
		case SPELLCASTER:
			return new Species[]{SPELLCASTER,BANDIT,CULTIST,WARLORD};
		case LYCANTHROPE:
			return new Species[] {LYCANTHROPE,BANDIT,CreatureType.HUMANOID,null};
		case DROW:
		case DUREGAR:
		case GNOLL:
		case GOBLINOID:
		case KOBOLD:
		case LIZARDFOLK:
		case ORC:
		case TROGLODYTE:
		case BANDIT:
		case CULTIST:
		case WARLORD:
		default:
			return new Species[]{this};
		}
	}


}
