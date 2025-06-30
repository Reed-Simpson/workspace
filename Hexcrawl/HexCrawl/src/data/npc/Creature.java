package data.npc;

import data.Indexible;
import data.population.Species;

public abstract class Creature extends Indexible{
	public abstract String getName();
	public abstract Species getSpecies();
	public abstract void setSpecies(Species s);
	

	public Creature(float... floats) {
		super(floats);
	}
	public Creature(int[] ints) {
		super(ints);
	}

}
