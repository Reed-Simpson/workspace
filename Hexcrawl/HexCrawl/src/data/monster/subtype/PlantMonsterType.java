package data.monster.subtype;

import java.util.HashMap;

import data.Indexible;
import data.WeightedTable;
import data.biome.BiomeType;
import data.population.Species;
import names.IndexibleNameGenerator;

public enum PlantMonsterType implements Species{
	AWAKENED_SHRUB,TWIG_BLIGHT,NEEDLE_BLIGHT,VEGEPYGMY,VINE_BLIGHT,THORNY_VEGEPYGMY,AWAKENED_TREE,VEGEPYGMY_CHIEF,SHAMBLING_MOUND,WOOD_WOAD,CORPSE_FLOWER,TREANT,MYCONID_SPROUT,
	SHRIEKER,VIOLET_FUNGUS,GAS_SPORE,MYCONID_ADULT,QUAGGOTH_SPORE_SERVANT
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
			Species[] array = new Species[] {};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.SNOW, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.BEACH, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.DESERT, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {AWAKENED_SHRUB,TWIG_BLIGHT,NEEDLE_BLIGHT,VEGEPYGMY,VINE_BLIGHT,THORNY_VEGEPYGMY,AWAKENED_TREE,VEGEPYGMY_CHIEF,SHAMBLING_MOUND,WOOD_WOAD,
					CORPSE_FLOWER,TREANT};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.FOREST, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.GRASSLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.ROCKYHILLS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {VEGEPYGMY};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.JUNGLE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.MOUNTAINS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {VEGEPYGMY,THORNY_VEGEPYGMY,VEGEPYGMY_CHIEF,SHAMBLING_MOUND,CORPSE_FLOWER};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WETLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {MYCONID_SPROUT,SHRIEKER,VIOLET_FUNGUS,GAS_SPORE,MYCONID_ADULT,QUAGGOTH_SPORE_SERVANT};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.VOID, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.LAKE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WATER, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {CORPSE_FLOWER};
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
