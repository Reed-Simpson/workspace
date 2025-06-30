package data.threat;

import data.population.Species;
import names.threat.ThreatNameGenerator;

public interface CreatureSubtype extends Species {

	public abstract String getName();
	
	public abstract int getId() ;
	
	public default Species[] getMinionSpeciesList() {
		return new Species[]{this};
	}
	
	@Override
	public ThreatNameGenerator getNameGen();
	
}
