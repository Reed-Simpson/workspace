package data.threat.subtype;

import java.util.ArrayList;

import data.Indexible;
import data.WeightedTable;
import data.population.NPCSpecies;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum AberrationType implements CreatureSubtype{
	ABOLETH(),
	BEHOLDER(),
	SLAAD(),
	MIND_FLAYER(),
	ELDER_BRAIN(),
	ELDER_BRAIN_DRAGON(),
	FEYR(),
	GRIMLOCK(),
	STAR_SPAWN(),
	BEHOLDERKIN(),
	CHUUL(),
	NOTHIC();


	private static WeightedTable<AberrationType> weights;

	private static void populateWeights() {
		weights = new WeightedTable<AberrationType>();
		weights.put(ABOLETH, 100);
		weights.put(BEHOLDER, 100);
		weights.put(SLAAD, 100);
		weights.put(ELDER_BRAIN, 100);
		weights.put(ELDER_BRAIN_DRAGON, 10);
		weights.put(GRIMLOCK, 10);
		weights.put(STAR_SPAWN, 10);
	}
	public static AberrationType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	@Override
	public ThreatNameGenerator getNameGen() {
		return CreatureType.ABERRATION.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		ArrayList<Species> list = new ArrayList<Species>();
		switch(this) {
		case ELDER_BRAIN:
		case ELDER_BRAIN_DRAGON:
			list.add(MIND_FLAYER);
			list.add(HumanoidType.DUREGAR);
			break;
		case GRIMLOCK:
			list.add(GRIMLOCK);
			break;
		case SLAAD:
			list.add(SLAAD);
		case STAR_SPAWN:
		case ABOLETH:
		case BEHOLDER:
		default:
			list.add(STAR_SPAWN);
			list.add(BEHOLDERKIN);
			list.add(HumanoidType.DUREGAR);
			list.add(HumanoidType.CULTIST);
			list.add(HumanoidType.SPELLCASTER);
			list.add(CHUUL);
			list.add(NPCSpecies.FISHPEOPLE);
			list.add(NOTHIC);
		}
		return list.toArray(new Species[] {});
	}

}
