package data.monster;

import data.population.Species;

public class Monster {
	Species species;
	String speciesName;
	String feature;
	String trait;
	String ability;
	String tactic;
	String personality;
	String weakness;
	
	public Monster(Species species) {
		this.species = species;
		this.speciesName = species.getSpeciesName();
	}
	public Monster(String speciesName) {
		this.speciesName = speciesName;
	}

	public Species getSpecies() {
		return species;
	}

	public void setSpecies(Species species) {
		this.species = species;
		this.speciesName = species.getSpeciesName();
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getTrait() {
		return trait;
	}

	public void setTrait(String trait) {
		this.trait = trait;
	}

	public String getAbility() {
		return ability;
	}

	public void setAbility(String ability) {
		this.ability = ability;
	}

	public String getTactic() {
		return tactic;
	}

	public void setTactic(String tactic) {
		this.tactic = tactic;
	}

	public String getPersonality() {
		return personality;
	}

	public void setPersonality(String personality) {
		this.personality = personality;
	}

	public String getWeakness() {
		return weakness;
	}

	public void setWeakness(String weakness) {
		this.weakness = weakness;
	}
	
	public String toString() {
		if(this.speciesName==null) return null;
		String part1 = speciesName;
		if(personality!=null && trait != null) {
			part1 = trait+", "+personality+" "+part1;
		}else if(personality !=null) {
			part1 = personality+" "+part1;
		}else if(trait !=null) {
			part1 = trait+" "+part1;
		}
		if(ability!=null && feature != null) {
			part1+=" with "+ability+" "+feature;
		}else if(ability!=null) {
			part1+=" with "+ability+" powers";
		}else if(feature!=null) {
			part1+=" with "+feature;
		}
		part1 = Character.toUpperCase(part1.charAt(0))+part1.substring(1).toLowerCase();
		String part2 = "";
		if(tactic!=null && weakness != null) {
			part2 = "Uses "+tactic.toLowerCase()+" tactics and has a weakness to "+weakness.toLowerCase()+".";
		}else if(tactic!=null) {
			part2 = "Uses "+tactic.toLowerCase()+" tactics.";
		}else if(weakness!=null) {
			part2 = "Has a weakness to "+weakness.toLowerCase()+".";
		}
		return part1+". "+part2;
	}

}
