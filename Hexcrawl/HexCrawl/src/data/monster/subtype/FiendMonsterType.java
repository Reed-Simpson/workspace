package data.monster.subtype;

import java.util.HashMap;

import data.Indexible;
import data.WeightedTable;
import data.biome.BiomeType;
import data.population.Species;
import names.IndexibleNameGenerator;

public enum FiendMonsterType implements Species{
	SHOOSUVA,MERRENOLOTH,CANOLOTH,WASTRILITH,BLUE_ABISHAI,VARGOUILLE,DYBBUK,GNOLL_FANG_OF_YEENOGHU,HOWLER,ORTHON,OINOLOTH,BARGHEST,TANARUKK,NIGHT_HAG,HELL_HOUND,
	RED_ABISHAI,MAUREZHI,NABASSU,MAW_DEMON,BABAU,ARMANITE,DHERGOLOTH,DRAEGLOTH,ALKILITH,DEVOURER,SIBRIEX,INCUBUS,SUCCUBUS,CAMBION,WHITE_ABISHAI,BLACK_ABISHAI,
	YAGNOLOTH,RAKSHASA,GREEN_ABISHAI,;

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
			Species[] array = new Species[] {SHOOSUVA};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.SNOW, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {MERRENOLOTH,CANOLOTH,WASTRILITH,BLUE_ABISHAI};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.BEACH, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {VARGOUILLE,DYBBUK,GNOLL_FANG_OF_YEENOGHU,HOWLER,ORTHON,OINOLOTH};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.DESERT, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {BARGHEST,GNOLL_FANG_OF_YEENOGHU,SHOOSUVA};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.FOREST, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {BARGHEST,GNOLL_FANG_OF_YEENOGHU,HOWLER,SHOOSUVA};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.GRASSLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {BARGHEST,GNOLL_FANG_OF_YEENOGHU,TANARUKK,HOWLER,SHOOSUVA};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.ROCKYHILLS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {NIGHT_HAG};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.JUNGLE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {HELL_HOUND,BARGHEST,TANARUKK,RED_ABISHAI};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.MOUNTAINS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {VARGOUILLE,MAUREZHI,WASTRILITH,NABASSU};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WETLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {MAW_DEMON,VARGOUILLE,HELL_HOUND,BABAU,BARGHEST,TANARUKK,ARMANITE,DHERGOLOTH,DRAEGLOTH,CANOLOTH,HOWLER,ORTHON,ALKILITH,OINOLOTH,DEVOURER,WASTRILITH,NABASSU,SIBRIEX};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.VOID, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {WASTRILITH};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.LAKE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {WASTRILITH};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WATER, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {BABAU,DYBBUK,INCUBUS,SUCCUBUS,CAMBION,WHITE_ABISHAI,BLACK_ABISHAI,MAUREZHI,CANOLOTH,ORTHON,ALKILITH,YAGNOLOTH,RAKSHASA,GREEN_ABISHAI,NABASSU,BLUE_ABISHAI,RED_ABISHAI};
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
