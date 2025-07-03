package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.population.NPCSpecies;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum CelestialType implements CreatureSubtype{
	ANGEL(),
	ARCHON(),
	GUARDINAL(),
	AZATA(),
	COUATL(),
	UNICORN(),
	PEGASI();
	

	private static WeightedTable<CelestialType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<CelestialType>();
		weights.put(ANGEL, 100);
		weights.put(ARCHON, 100);
		weights.put(GUARDINAL, 100);
		weights.put(AZATA, 100);
		weights.put(COUATL, 10);
		weights.put(UNICORN, 10);
		weights.put(PEGASI, 10);
	}
	public static CelestialType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	
	private CelestialType() {
	}
	
	
	@Override
	public ThreatNameGenerator getNameGen() {
		return CreatureType.CELESTIAL.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		return new Species[]{this,CreatureType.HUMANOID,NPCSpecies.AASIMAR};
	}

}
