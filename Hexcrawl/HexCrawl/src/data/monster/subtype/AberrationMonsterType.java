package data.monster.subtype;

import java.util.HashMap;

import data.Indexible;
import data.WeightedTable;
import data.biome.BiomeType;
import data.population.Species;
import data.threat.CreatureType;
import names.IndexibleNameGenerator;

public enum AberrationMonsterType implements Species{
	BALHANNOTH,MORKOTH,BERBALANG,CHOKER,NEOGI_HATCHLING,NEOGI,NEOGI_MASTER,STAR_SPAWN_GRUE,STAR_SPAWN_SEER,STAR_SPAWN_LARVA_MAGE,FLUMPH,GAZER,GIBBERING_MOUTHER,
	INTELLECT_DEVOURER,NOTHIC,GRELL,SPECTATOR,CHUUL,MINDWITNESS,OTYUGH,GAUTH,MIND_FLAYER,CLOAKER,MIND_FLAYER_ARCANIST,ULITHARID,ABOLETH,DEATH_KISS,BEHOLDER,
	NEOTHELID,ELDER_BRAIN,GRAY_SLAAD
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
			Species[] array = new Species[] {BALHANNOTH,MORKOTH};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.BEACH, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {BERBALANG,CHOKER};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.DESERT, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {};
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
			Species[] array = new Species[] {NEOGI_HATCHLING,NEOGI,NEOGI_MASTER};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.ROCKYHILLS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.JUNGLE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {STAR_SPAWN_GRUE,CHOKER,BALHANNOTH,STAR_SPAWN_SEER,STAR_SPAWN_LARVA_MAGE};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.MOUNTAINS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {STAR_SPAWN_GRUE,STAR_SPAWN_SEER};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WETLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {FLUMPH,NEOGI_HATCHLING,GAZER,CHOKER,GIBBERING_MOUTHER,INTELLECT_DEVOURER,NOTHIC,GRELL,NEOGI,SPECTATOR,CHUUL,NEOGI_MASTER,MINDWITNESS,
					OTYUGH,GAUTH,MIND_FLAYER,CLOAKER,MIND_FLAYER_ARCANIST,ULITHARID,ABOLETH,DEATH_KISS,BALHANNOTH,BEHOLDER,NEOTHELID,ELDER_BRAIN};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.VOID, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {MORKOTH};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.LAKE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {MORKOTH};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WATER, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {GRAY_SLAAD,STAR_SPAWN_SEER};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.CITY, list);
		}
	}

	@Override
	public IndexibleNameGenerator getNameGen() {
		return CreatureType.ABERRATION.getNameGen();
	}

}
