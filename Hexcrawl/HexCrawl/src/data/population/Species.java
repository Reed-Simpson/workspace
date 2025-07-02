package data.population;

import names.IndexibleNameGenerator;

public interface Species {
	public IndexibleNameGenerator getNameGen();
	
	public default String getSpeciesName() {
		return this.toString();
	}
	public default boolean containedIn(Species[] list) {
		for(Species s:list) {
			if(this.equals(s)) return true;
		}
		return false;
	}

}
