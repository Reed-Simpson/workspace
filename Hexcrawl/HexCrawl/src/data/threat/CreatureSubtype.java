package data.threat;

import data.population.Species;
import names.threat.ThreatNameGenerator;
import util.Util;

public interface CreatureSubtype extends Species {

	public default String getName() {
		return Util.replace(this.toString(), "_", " ");
	}
	
	public abstract Species[] getMinionSpeciesList() ;
	//return null for npcspecies
	//return this for this
	//return Humanoid for random local
	
	@Override
	public ThreatNameGenerator getNameGen();
	
}
