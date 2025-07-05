package data.monster.subtype;

import java.util.HashMap;

import data.Indexible;
import data.WeightedTable;
import data.biome.BiomeType;
import data.population.Species;
import data.threat.CreatureType;
import names.IndexibleNameGenerator;

public enum HumanoidMonsterType implements Species{
	AARAKOCRA,BUGBEAR,BULLYWUG,DEEP_SCION,DERRO_SAVANT,DERRO,DROW,DUERGAR,FLIND,GIFF,GITHYANKI,GITHZERAI,GNOLL,GOBLIN,GRIMLOCK,
	GRUNG,HOBGOBLIN,JACKALWERE,KENKU,KOBOLD,KUOTOA,LIZARDFOLK,MEAZEL,MERFOLK,NAGPA,NILBOG,
	ORC,OROG,QUAGGOTH,SAHUAGIN,SEA_SPAWN,SOUL_MONGER,SVIRFNEBLIN,THRIKREEN,TORTLE,TROGLODYTE,
	WEREBEAR,WEREBOAR,WERERAT,WERETIGER,WEREWOLF,XVART,YUANTI_ABOMINATION,YUANTI_PUREBLOOD;

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
			Species[] array = new Species[] {KOBOLD,GNOLL,ORC,OROG,WEREBEAR,FLIND};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.SNOW, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {KOBOLD,MERFOLK,TORTLE,SAHUAGIN,SEA_SPAWN,DEEP_SCION,NAGPA};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.BEACH, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {KOBOLD,GNOLL,HOBGOBLIN,JACKALWERE,MEAZEL,THRIKREEN,YUANTI_PUREBLOOD,YUANTI_ABOMINATION,WERETIGER,GITHYANKI,NAGPA};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.DESERT, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {KOBOLD,GOBLIN,GRUNG,KENKU,GNOLL,HOBGOBLIN,LIZARDFOLK,ORC,BUGBEAR,GOBLIN,MEAZEL,NILBOG,YUANTI_PUREBLOOD,LIZARDFOLK,OROG,WERERAT,
					YUANTI_ABOMINATION,WEREWOLF,WEREBOAR,WERETIGER,WEREBEAR,FLIND,NAGPA};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.FOREST, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {GOBLIN,GNOLL,HOBGOBLIN,JACKALWERE,ORC,BUGBEAR,GOBLIN,MEAZEL,THRIKREEN,OROG,WEREBOAR,WERETIGER,FLIND,};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.GRASSLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {KOBOLD,XVART,GOBLIN,GNOLL,HOBGOBLIN,ORC,MEAZEL,NILBOG,OROG,WEREWOLF,WEREBOAR,WEREBEAR,FLIND,};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.ROCKYHILLS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {GOBLIN,GRUNG,LIZARDFOLK,YUANTI_PUREBLOOD,YUANTI_ABOMINATION,WEREBOAR,WERETIGER,};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.JUNGLE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {KOBOLD,AARAKOCRA,DERRO,ORC,DUERGAR,MEAZEL,OROG,GITHYANKI,GITHZERAI};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.MOUNTAINS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {KOBOLD,BULLYWUG,LIZARDFOLK,ORC,MEAZEL,YUANTI_PUREBLOOD,YUANTI_ABOMINATION,NAGPA,};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WETLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {KOBOLD,XVART,DERRO,DROW,GOBLIN,GRIMLOCK,KUOTOA,TROGLODYTE,HOBGOBLIN,ORC,SVIRFNEBLIN,BUGBEAR,DUERGAR,MEAZEL,NILBOG,OROG,QUAGGOTH,
					YUANTI_ABOMINATION,DERRO_SAVANT,SOUL_MONGER,NAGPA};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.VOID, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {MERFOLK,SAHUAGIN};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.LAKE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {MERFOLK,SAHUAGIN,SEA_SPAWN,DEEP_SCION};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WATER, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {KOBOLD,KENKU,MEAZEL,YUANTI_PUREBLOOD,WERERAT,GIFF,GITHYANKI,GITHZERAI,SOUL_MONGER,NAGPA};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.CITY, list);
			}
		}
	
	@Override
	public IndexibleNameGenerator getNameGen() {
		return CreatureType.HUMANOID.getNameGen();
	}

}
