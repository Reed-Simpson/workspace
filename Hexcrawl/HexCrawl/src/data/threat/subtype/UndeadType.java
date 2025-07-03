package data.threat.subtype;

import java.util.ArrayList;

import data.Indexible;
import data.WeightedTable;
import data.population.NPCSpecies;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum UndeadType implements CreatureSubtype {
	LICH(),
	VAMPIRE(),
	MUMMY_LORD(),
	NIGHTWALKER(),
	SKULL_LORD(),
	DEATH_KNIGHT(),
	DEATH_TYRANT(),
	DEMILICH(),
	DRACOLICH(),
	GHOST_DRAGON(),
	GHOST(),
	WIGHT(),
	WRAITH(),
	DEATHLOCK();

	private static WeightedTable<UndeadType> weights;

	private static void populateWeights() {
		weights = new WeightedTable<UndeadType>();
		weights.put(LICH, 100);
		weights.put(VAMPIRE, 100);
		weights.put(MUMMY_LORD, 100);
		weights.put(DEATH_TYRANT, 100);
		weights.put(WIGHT, 100);
		weights.put(WRAITH, 100);
		weights.put(DEATH_KNIGHT, 100);
		weights.put(DRACOLICH, 10);
		weights.put(NIGHTWALKER, 1);
		weights.put(DEMILICH, 1);
		weights.put(GHOST_DRAGON, 1);
	}
	public static UndeadType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	@Override
	public ThreatNameGenerator getNameGen() {
		return CreatureType.UNDEAD.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		ArrayList<Species> list = new ArrayList<Species>();
		list.add(null);
		switch(this) {
		case DEATH_TYRANT: return AberrationType.BEHOLDER.getMinionSpeciesList();
		case MUMMY_LORD: list.add(MUMMY_LORD);break;
		case GHOST_DRAGON:
		case DRACOLICH:
			list.add(this);
			list.add(NPCSpecies.KOBOLD);
			list.add(NPCSpecies.DRAGONBORN);
			list.add(NPCSpecies.LIZARDFOLK);
			break;
		case NIGHTWALKER: list.add(NIGHTWALKER);
		case DEMILICH: list.add(DEMILICH);
		case LICH: list.add(LICH);
		case DEATH_KNIGHT: list.add(DEATH_KNIGHT);
		case VAMPIRE: {
			list.add(VAMPIRE);
			list.add(null);
			list.add(CreatureType.HUMANOID);
		}
		case WRAITH:  
		case WIGHT: 
		default:{
		}
		list.add(HumanoidType.SPELLCASTER);
		list.add(HumanoidType.CULTIST);
		list.add(WRAITH);
		list.add(WIGHT);
		list.add(GHOST);
		list.add(DEATHLOCK);
		}
		return list.toArray(new Species[] {});
	}

}
