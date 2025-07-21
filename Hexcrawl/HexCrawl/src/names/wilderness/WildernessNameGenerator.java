package names.wilderness;

import data.Indexible;
import data.WeightedTable;
import data.biome.BiomeType;
import names.IndexibleNameGenerator;
import util.Util;

public class WildernessNameGenerator extends IndexibleNameGenerator{

	private static final String NAMES = "${color}${landmark},${animal}${monster feature},${wilderness npc}'s Doom,${misfortune} Man's,${personality} Man's,${domain},"+
			"Old ${structure},${hazard},${edible plant},${poisonous plant},${inn prefix} ${inn suffix},${town index}";
	private static WeightedTable<String> names;

	public static String getRegionName(Indexible obj) {
		if(names==null) {
			names = new WeightedTable<String>();
			populate(names, NAMES, ",");
		}
		String name = names.getByWeight(obj);
		return Util.formatTableResult(name, obj);
	}
	
	public static String getName(BiomeType b,Indexible obj) {
		return Util.toCamelCase(getRegionName(obj) + " " + getBiomeName(b));
	}
	
	public static String getBiomeName(BiomeType b) {
		switch(b) {
		case VOID: return "abyss";
		case WATER: return "ocean";
		case SHALLOWS: return "coast";
		case GRASSLAND: return "plains";
		case FOREST: return "forest";
		case HIGHLAND: return "highland";
		case HIGHLANDFOREST: return "forest";
		case SNOW: return "snowfield";
		case SALTMARSH: return "saltmarsh";
		case DESERT: return "desert";
		case STEPPE: return "steppe";
		case FJORDS: return "fjords";
		case CLIFFS: return "cliffs";
		case BEACH: return "beach";
		case JUNGLE: return "jungle";
		case SAVANNA: return "savanna";
		case WOODYHILLS: return "hills";
		case ROCKYHILLS: return "hills";
		case GLACIERS: return "glacier";
		case SALTPAN: return "salt pan";
		case TAIGA: return "forest";
		case MOUNTAINS: return "mountains";
		case BADLANDS: return "hills";
		case VOLCANIC: return "volcano";
		case WETLAND: return "swamp";
		case LAKE: return "lake";
		case FLOODPLAIN: return "plains";
		case RIVER: return "river"; 
		case CITY: return "city";
		case TOWN: return "town";
		case DELTA: return "delta";
		default: return null;
		}
	}
	
	
	@Override
	public String getName(Indexible obj) {
		return getRegionName(obj);
	}

}
