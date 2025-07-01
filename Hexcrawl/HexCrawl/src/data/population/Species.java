package data.population;

import names.IndexibleNameGenerator;

public interface Species {
	public IndexibleNameGenerator getNameGen();
	
	public default String getSpeciesName() {
		return this.toString();
	}

}
