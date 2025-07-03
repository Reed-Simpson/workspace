package data.threat.subtype;

import java.util.ArrayList;

import data.Indexible;
import data.WeightedTable;
import data.population.NPCSpecies;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum ElementalType implements CreatureSubtype {
	FIRE(),
	WATER(),
	EARTH(),
	AIR(),
	MAGMA(),//fire+earth
	OOZE(),//water+earth
	ICE(),//water+air
	LIGHTNING(),//fire+air
	
	VOID(),//air
	SALT(),//water
	ENTROPY(),//water
	CHAOS(),//fire
	SPACE(),//earth
	MIND(),
	EMOTION(),
	RADIANT(),//fire
	BLOOD(),
	NECROTIC(),//earth
	RAGE(),
	SOUND(),//air
	
	STEAM(),//water+fire
	SAND();//air+earth
	

	private static WeightedTable<ElementalType> weights;
	
	private static void populateWeights() {
		weights = new WeightedTable<ElementalType>();
		weights.put(FIRE, 500);
		weights.put(WATER, 500);
		weights.put(EARTH, 500);
		weights.put(AIR, 500);
		weights.put(MAGMA, 100);
		weights.put(OOZE, 100);
		weights.put(ICE, 100);
		weights.put(LIGHTNING, 100);
		weights.put(VOID, 1);
		weights.put(SALT, 1);
		weights.put(ENTROPY, 1);
		weights.put(CHAOS, 1);
		weights.put(SPACE, 1);
		weights.put(MIND, 1);
		weights.put(EMOTION, 1);
		weights.put(RADIANT, 1);
		weights.put(BLOOD, 1);
		weights.put(NECROTIC, 1);
		weights.put(RAGE, 1);
		weights.put(SOUND, 1);
		weights.put(STEAM, 1);
		weights.put(SAND, 1);
	}
	
	public static ElementalType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	
	private ElementalType() {
	}
	
	@Override
	public String getSpeciesName() {
		return getName()+" Elemental";
	}
	@Override
	public ThreatNameGenerator getNameGen() {
		return CreatureType.ELEMENTAL.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		ArrayList<Species> list = new ArrayList<Species>();
		list.add(this);
		list.add(NPCSpecies.GENASI);
		if(this.containedIn(new Species[] {AIR,ICE,LIGHTNING,VOID,SOUND,SAND})) {
			list.add(NPCSpecies.AARAKOCRA);
			list.add(AIR);
		}
		if(this.containedIn(new Species[] {WATER,OOZE,ICE,SALT,ENTROPY,STEAM})) {
			list.add(NPCSpecies.FISHPEOPLE);
			list.add(WATER);
		}
		if(this.containedIn(new Species[] {EARTH,MAGMA,OOZE,SPACE,NECROTIC,SAND})) {
			list.add(NPCSpecies.DWARF);
			list.add(EARTH);
		}
		if(this.containedIn(new Species[] {FIRE,MAGMA,LIGHTNING,CHAOS,RADIANT,STEAM})) {
			list.add(NPCSpecies.TIEFLING);
			list.add(FIRE);
		}
		if(this.containedIn(new Species[] {MIND,EMOTION,BLOOD,RAGE})) {
			list.add(MIND);
			list.add(EMOTION);
			list.add(BLOOD);
			list.add(RAGE);
		}
		return list.toArray(new Species[] {});
	}

}
