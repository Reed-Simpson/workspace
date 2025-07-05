package data.monster.subtype;

import java.util.HashMap;

import data.Indexible;
import data.WeightedTable;
import data.biome.BiomeType;
import data.population.Species;
import names.IndexibleNameGenerator;

public enum UndeadMonsterType implements Species{
	GNOLL_WITHERLING,VAMPIRIC_MIST,REVENANT,BONECLAW,NIGHTWALKER,BANSHEE,EIDOLON,MUMMY,WIGHT,SPAWN_OF_KYUSS,MUMMY_LORD,SKULL_LORD,ADULT_BLUE_DRACOLICH,WILLOWISP,SWORD_WRAITH_WARRIOR,
	SWORD_WRAITH_COMMANDER,ZOMBIE,GHOUL,GHAST,ALLIP,BODAK,SHADOW,SPECTER,MINOTAUR_SKELETON,BONE_NAGA,FLAMESKULL,GHOST,BEHOLDER_ZOMBIE,VAMPIRE_SPAWN,WRAITH,ALHOON,DEATH_TYRANT,
	MIND_FLAYER_LICH,SKELETON,DEATHLOCK,DEATHLOCK_MASTERMIND,VAMPIRE
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
			Species[] array = new Species[] {GNOLL_WITHERLING,VAMPIRIC_MIST,REVENANT,BONECLAW,NIGHTWALKER};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.SNOW, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {VAMPIRIC_MIST,BANSHEE,EIDOLON};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.BEACH, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {MUMMY,WIGHT,REVENANT,SPAWN_OF_KYUSS,BONECLAW,EIDOLON,MUMMY_LORD,SKULL_LORD,ADULT_BLUE_DRACOLICH,NIGHTWALKER};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.DESERT, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {GNOLL_WITHERLING,WILLOWISP,VAMPIRIC_MIST,BANSHEE,REVENANT,EIDOLON};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.FOREST, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {GNOLL_WITHERLING,SWORD_WRAITH_WARRIOR,VAMPIRIC_MIST,SWORD_WRAITH_COMMANDER,EIDOLON};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.GRASSLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {GNOLL_WITHERLING,REVENANT};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.ROCKYHILLS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {ZOMBIE,GHOUL};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.JUNGLE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {VAMPIRIC_MIST,EIDOLON};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.MOUNTAINS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {GHOUL,GHAST,WILLOWISP,SWORD_WRAITH_WARRIOR,VAMPIRIC_MIST,WIGHT,ALLIP,REVENANT,BODAK,SWORD_WRAITH_COMMANDER,SKULL_LORD,NIGHTWALKER};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WETLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {SHADOW,GHOUL,SPECTER,GHAST,MINOTAUR_SKELETON,VAMPIRIC_MIST,WIGHT,BONE_NAGA,FLAMESKULL,GHOST,BEHOLDER_ZOMBIE,SPAWN_OF_KYUSS,VAMPIRE_SPAWN,
					WRAITH,BODAK,ALHOON,DEATH_TYRANT,SKULL_LORD,NIGHTWALKER,MIND_FLAYER_LICH};
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
			Species[] array = new Species[] {SKELETON,ZOMBIE,SHADOW,GHOUL,SPECTER,GHAST,WILLOWISP,VAMPIRIC_MIST,WIGHT,DEATHLOCK,GHOST,ALLIP,REVENANT,VAMPIRE_SPAWN,BODAK,
					DEATHLOCK_MASTERMIND,BONECLAW,EIDOLON,VAMPIRE};
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
