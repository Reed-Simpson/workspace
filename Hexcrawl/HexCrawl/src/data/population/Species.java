package data.population;

import names.IndexibleNameGenerator;
import util.Util;

public interface Species {
	public IndexibleNameGenerator getNameGen();
	
	public default String getSpeciesName() {
		return Util.replace(this.toString(), "_", " ");
	}

	public default boolean containedIn(Species[] list) {
		for(Species s:list) {
			if(this.equals(s)) return true;
		}
		return false;
	}

}
