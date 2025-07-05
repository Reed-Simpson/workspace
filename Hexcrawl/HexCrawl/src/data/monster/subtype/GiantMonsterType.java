package data.monster.subtype;

import java.util.HashMap;

import data.Indexible;
import data.WeightedTable;
import data.biome.BiomeType;
import data.population.Species;
import names.IndexibleNameGenerator;

public enum GiantMonsterType implements Species{
	HALF_OGRE,OGRE,TROLL,FROST_GIANT,FROST_GIANT_EVERLASTING_ONE,DIRE_TROLL,STORM_GIANT_QUINTESSENT,CYCLOPS,STONE_GIANT_DREAMWALKER,SPIRIT_TROLL,STORM_GIANT,ROT_TROLL,ONI,
	VENOM_TROLL,OGRE_BOLT_LAUNCHER,OGRE_HOWDAH,OGRE_CHAIN_BRUTE,OGRE_BATTERING_RAM,MOUTH_OF_GROLANTOR,ETTIN,HILL_GIANT,STONE_GIANT,CLOUD_GIANT,FIRE_GIANT,CLOUD_GIANT_SMILING_ONE,
	FIRE_GIANT_DREADNOUGHT,FOMORIAN
	;

	private static HashMap<BiomeType,WeightedTable<Species>> habitats;

	public static WeightedTable<Species> getSpecies(BiomeType type){
		if(habitats==null) populateHabitats();
		WeightedTable<Species> result = habitats.get(type);
		if(result==null) result = new WeightedTable<Species>();
		return result;
	}
	public static Species getSpeciesByWeight(BiomeType type,Indexible obj) {
		WeightedTable<Species> species = getSpecies(type);
		if(species == null) return null;
		else return species.getByWeight(obj);
	}

	private static void populateHabitats() {
		habitats = new HashMap<BiomeType,WeightedTable<Species>>();
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {HALF_OGRE,OGRE,TROLL,FROST_GIANT,FROST_GIANT_EVERLASTING_ONE,DIRE_TROLL,STORM_GIANT_QUINTESSENT};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.SNOW, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {OGRE,CYCLOPS,STONE_GIANT_DREAMWALKER,SPIRIT_TROLL,FROST_GIANT_EVERLASTING_ONE,STORM_GIANT,STORM_GIANT_QUINTESSENT};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.BEACH, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {HALF_OGRE,OGRE,CYCLOPS,ROT_TROLL,STORM_GIANT_QUINTESSENT};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.DESERT, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {HALF_OGRE,OGRE,TROLL,ONI,VENOM_TROLL,ROT_TROLL,SPIRIT_TROLL,DIRE_TROLL};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.FOREST, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {OGRE,OGRE_BOLT_LAUNCHER,OGRE_HOWDAH,OGRE_CHAIN_BRUTE,OGRE_BATTERING_RAM,CYCLOPS,MOUTH_OF_GROLANTOR};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.GRASSLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {HALF_OGRE,OGRE,OGRE_BOLT_LAUNCHER,OGRE_HOWDAH,OGRE_CHAIN_BRUTE,ETTIN,OGRE_BATTERING_RAM,HILL_GIANT,TROLL,CYCLOPS,MOUTH_OF_GROLANTOR,STONE_GIANT,STONE_GIANT_DREAMWALKER,DIRE_TROLL};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.ROCKYHILLS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {TROLL,CYCLOPS};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.JUNGLE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {HALF_OGRE,OGRE,OGRE_BOLT_LAUNCHER,OGRE_HOWDAH,OGRE_CHAIN_BRUTE,ETTIN,OGRE_BATTERING_RAM,TROLL,CYCLOPS,STONE_GIANT,FROST_GIANT,CLOUD_GIANT,FIRE_GIANT,STONE_GIANT_DREAMWALKER,CLOUD_GIANT_SMILING_ONE,DIRE_TROLL,FIRE_GIANT_DREADNOUGHT,STORM_GIANT_QUINTESSENT};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.MOUNTAINS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {OGRE,TROLL,VENOM_TROLL,ROT_TROLL,SPIRIT_TROLL};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WETLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {HALF_OGRE,OGRE,ETTIN,TROLL,CYCLOPS,STONE_GIANT,VENOM_TROLL,FOMORIAN,FIRE_GIANT,ROT_TROLL,SPIRIT_TROLL,DIRE_TROLL,FIRE_GIANT_DREADNOUGHT};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.VOID, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {STORM_GIANT_QUINTESSENT};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.LAKE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {STORM_GIANT_QUINTESSENT};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WATER, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {HALF_OGRE,ONI};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.CITY, list);
		}
	}
	@Override
	public IndexibleNameGenerator getNameGen() {
		// TODO Auto-generated method stub
		return null;
	}

}
