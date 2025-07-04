package data.threat;

import data.population.Species;
import names.threat.ThreatNameGenerator;

public interface CreatureSubtype extends Species {

	
	public abstract Species[] getMinionSpeciesList() ;
	//return null for npcspecies
	//return this for this
	//return Humanoid for random local
	
	@Override
	public ThreatNameGenerator getNameGen();
	
}
