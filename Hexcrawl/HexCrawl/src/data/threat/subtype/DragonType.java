package data.threat.subtype;

import data.Indexible;
import data.WeightedTable;
import data.population.NPCSpecies;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum DragonType implements CreatureSubtype {
	BLACK(),
	BLUE(),
	GREEN(),
	RED(),
	WHITE(),
	BRASS(),
	BRONZE(),
	COPPER(),
	GOLD(),
	SILVER(),
	AMETHYST(),
	CRYSTAL(),
	EMERALD(),
	SAPPHIRE(),
	TOPAZ(),
	DEEP(),
	MOONSTONE(),
	SHADOW(),
	DRACOLICH();
	

	private static WeightedTable<DragonType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<DragonType>();
		weights.put(BLACK, 100);
		weights.put(BLUE, 100);
		weights.put(GREEN, 100);
		weights.put(RED, 100);
		weights.put(WHITE, 100);
		weights.put(BRASS, 10);
		weights.put(BRONZE, 10);
		weights.put(COPPER, 10);
		weights.put(GOLD, 10);
		weights.put(SILVER, 10);
		weights.put(AMETHYST, 1);
		weights.put(CRYSTAL, 1);
		weights.put(EMERALD, 1);
		weights.put(SAPPHIRE, 1);
		weights.put(TOPAZ, 1);
		weights.put(DEEP, 1);
		weights.put(MOONSTONE, 1);
		weights.put(SHADOW, 1);
		weights.put(DRACOLICH, 1);
	}

	public static DragonType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	
	
	@Override
	public String getSpeciesName() {
		if(DRACOLICH.equals(this)) return CreatureSubtype.super.getSpeciesName();
		else return CreatureSubtype.super.getSpeciesName()+" Dragon";
	}
	@Override
	public ThreatNameGenerator getNameGen() {
		return CreatureType.DRAGON.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		switch(this) {
		case SHADOW:
		case DRACOLICH:return UndeadType.DRACOLICH.getMinionSpeciesList();
		case AMETHYST:
		case BLACK:
		case BLUE:
		case BRASS:
		case BRONZE:
		case COPPER:
		case CRYSTAL:
		case DEEP:
		case EMERALD:
		case GOLD:
		case GREEN:
		case MOONSTONE:
		case RED:
		case SAPPHIRE:
		case SILVER:
		case TOPAZ:
		case WHITE:
		default:
			return new Species[]{this,CreatureType.HUMANOID,NPCSpecies.KOBOLD,NPCSpecies.DRAGONBORN,NPCSpecies.LIZARDFOLK};
		}
	}

}
