package data.threat;

import data.population.Species;
import names.threat.ThreatNameGenerator;

public interface CreatureSubtype extends Species {

	public abstract String getName();
	
	public abstract int getId() ;
	
	@Override
	public ThreatNameGenerator getNameGen();
	
}
