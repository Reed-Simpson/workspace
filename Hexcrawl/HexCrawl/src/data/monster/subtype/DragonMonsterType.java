package data.monster.subtype;

import java.util.HashMap;

import data.Indexible;
import data.WeightedTable;
import data.biome.BiomeType;
import data.population.Species;
import names.IndexibleNameGenerator;

public enum DragonMonsterType implements Species{
	GUARD_DRAKE,YOUNG_WHITE_DRAGON,ADULT_WHITE_DRAGON,ANCIENT_WHITE_DRAGON,PSEUDODRAGON,YOUNG_BRONZE_DRAGON,YOUNG_BLUE_DRAGON,ADULT_BRONZE_DRAGON,ADULT_BLUE_DRAGON,
	DRAGON_TURTLE,ANCIENT_BRONZE_DRAGON,ANCIENT_BLUE_DRAGON,YOUNG_BRASS_DRAGON,ADULT_BRASS_DRAGON,ANCIENT_BRASS_DRAGON,YOUNG_FAERIE_DRAGON,ADULT_FAERIE_DRAGON,
	YOUNG_GREEN_DRAGON,YOUNG_GOLD_DRAGON,ADULT_GREEN_DRAGON,ADULT_GOLD_DRAGON,ANCIENT_GREEN_DRAGON,ANCIENT_GOLD_DRAGON,WYVERN,YOUNG_COPPER_DRAGON,YOUNG_RED_DRAGON,
	ADULT_COPPER_DRAGON,ADULT_RED_DRAGON,ANCIENT_COPPER_DRAGON,ANCIENT_RED_DRAGON,YOUNG_SILVER_DRAGON,ADULT_SILVER_DRAGON,ANCIENT_SILVER_DRAGON,YOUNG_BLACK_DRAGON,
	ADULT_BLACK_DRAGON,ANCIENT_BLACK_DRAGON,YOUNG_RED_SHADOW_DRAGON
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
			Species[] array = new Species[] {GUARD_DRAKE,YOUNG_WHITE_DRAGON,ADULT_WHITE_DRAGON,ANCIENT_WHITE_DRAGON};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.SNOW, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {PSEUDODRAGON,YOUNG_BRONZE_DRAGON,YOUNG_BLUE_DRAGON,ADULT_BRONZE_DRAGON,ADULT_BLUE_DRAGON,DRAGON_TURTLE,ANCIENT_BRONZE_DRAGON,ANCIENT_BLUE_DRAGON};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.BEACH, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {PSEUDODRAGON,GUARD_DRAKE,YOUNG_BRASS_DRAGON,YOUNG_BLUE_DRAGON,ADULT_BRASS_DRAGON,ADULT_BLUE_DRAGON,ANCIENT_BRASS_DRAGON,ANCIENT_BLUE_DRAGON};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.DESERT, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {PSEUDODRAGON,YOUNG_FAERIE_DRAGON,ADULT_FAERIE_DRAGON,GUARD_DRAKE,YOUNG_GREEN_DRAGON,YOUNG_GOLD_DRAGON,ADULT_GREEN_DRAGON,ADULT_GOLD_DRAGON,ANCIENT_GREEN_DRAGON,ANCIENT_GOLD_DRAGON};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.FOREST, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {YOUNG_GOLD_DRAGON,ADULT_GOLD_DRAGON,ANCIENT_GOLD_DRAGON};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.GRASSLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {PSEUDODRAGON,WYVERN,YOUNG_COPPER_DRAGON,YOUNG_RED_DRAGON,ADULT_COPPER_DRAGON,ADULT_RED_DRAGON,ANCIENT_COPPER_DRAGON,ANCIENT_RED_DRAGON};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.ROCKYHILLS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {PSEUDODRAGON,YOUNG_FAERIE_DRAGON,ADULT_FAERIE_DRAGON,GUARD_DRAKE,YOUNG_GREEN_DRAGON,YOUNG_GOLD_DRAGON,ADULT_GREEN_DRAGON,ADULT_GOLD_DRAGON,ANCIENT_GREEN_DRAGON,ANCIENT_GOLD_DRAGON};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.JUNGLE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {PSEUDODRAGON,GUARD_DRAKE,WYVERN,YOUNG_SILVER_DRAGON,YOUNG_RED_DRAGON,ADULT_SILVER_DRAGON,ADULT_RED_DRAGON,ANCIENT_SILVER_DRAGON,ANCIENT_RED_DRAGON};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.MOUNTAINS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {GUARD_DRAKE,YOUNG_BLACK_DRAGON,ADULT_BLACK_DRAGON,ANCIENT_BLACK_DRAGON};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WETLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {GUARD_DRAKE,YOUNG_RED_SHADOW_DRAGON};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.VOID, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {DRAGON_TURTLE};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.LAKE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {DRAGON_TURTLE};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WATER, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {PSEUDODRAGON,GUARD_DRAKE,YOUNG_SILVER_DRAGON,ADULT_SILVER_DRAGON,ANCIENT_SILVER_DRAGON};
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
