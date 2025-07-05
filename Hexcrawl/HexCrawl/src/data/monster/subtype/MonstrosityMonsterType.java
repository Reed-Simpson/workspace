package data.monster.subtype;

import java.util.HashMap;

import data.Indexible;
import data.WeightedTable;
import data.biome.BiomeType;
import data.population.Species;
import names.IndexibleNameGenerator;

public enum MonstrosityMonsterType implements Species {
	GRIFFON,MANTICORE,WINTER_WOLF,YETI,YOUNG_REMORHAZ,LOST_SORROWSWORN,ABOMINABLE_YETI,REMORHAZ,ROC,HARPY,MERROW,LONELY_SORROWSWORN,YOUNG_KRUTHIK,DEATH_DOG,ADULT_KRUTHIK,
	LEUCROTTA,PHASE_SPIDER,YUANTI_MALISON,LAMIA,YUANTI_MIND_WHISPERER,YUANTI_NIGHTMARE_SPEAKER,KRUTHIK_HIVE_LORD,TLINCALLI,YUANTI_PIT_MASTER,MEDUSA,YUANTI_ABOMINATION,
	GUARDIAN_NAGA,GYNOSPHINX,YUANTI_ANATHEMA,PURPLE_WORM,ANDROSPHINX,WORG,ANKHEG,CENTAUR,ETTERCAP,GRICK,SHADOW_MASTIFF,DISPLACER_BEAST,OWLBEAR,SHADOW_MASTIFF_ALPHA,
	GIRALLON,GORGON,GRICK_ALPHA,HUNGRY_SORROWSWORN,GRAY_RENDER,COCKATRICE,HIPPOGRIFF,BULETTE,CHIMERA,GIANT_STRIDER,PERYTON,BASILISK,WRETCHED_SORROWSWORN,CATOBLEPAS,HYDRA,
	FROGHEMOTH,MALE_STEEDER,CHITINE,DARKMANTLE,PIERCER,RUST_MONSTER,FEMALE_STEEDER,CARRION_CRAWLER,MIMIC,CAVE_FISHER,CHOLDRITH,DOPPELGANGER,HOOK_HORROR,MINOTAUR,TRAPPER,
	ROPER,UMBER_HULK,DRIDER,SPIRIT_NAGA,BEHIR,ANGRY_SORROWSWORN,KRAKEN,BANDERHOBB,TARRASQUE
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
			Species[] array = new Species[] {GRIFFON,MANTICORE,WINTER_WOLF,YETI,YOUNG_REMORHAZ,LOST_SORROWSWORN,ABOMINABLE_YETI,REMORHAZ,ROC};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.SNOW, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {HARPY,GRIFFON,MERROW,MANTICORE,LONELY_SORROWSWORN,ROC};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.BEACH, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {YOUNG_KRUTHIK,DEATH_DOG,ADULT_KRUTHIK,LEUCROTTA,PHASE_SPIDER,YUANTI_MALISON,LAMIA,YUANTI_MIND_WHISPERER,YUANTI_NIGHTMARE_SPEAKER,
					KRUTHIK_HIVE_LORD,TLINCALLI,YUANTI_PIT_MASTER,MEDUSA,LOST_SORROWSWORN,YUANTI_ABOMINATION,LONELY_SORROWSWORN,GUARDIAN_NAGA,GYNOSPHINX,ROC,YUANTI_ANATHEMA,
					PURPLE_WORM,ANDROSPHINX};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.DESERT, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {WORG,HARPY,ANKHEG,CENTAUR,ETTERCAP,GRICK,SHADOW_MASTIFF,DISPLACER_BEAST,OWLBEAR,PHASE_SPIDER,SHADOW_MASTIFF_ALPHA,YUANTI_MALISON,
					GIRALLON,YUANTI_MIND_WHISPERER,YUANTI_NIGHTMARE_SPEAKER,GORGON,YUANTI_PIT_MASTER,GRICK_ALPHA,LOST_SORROWSWORN,YUANTI_ABOMINATION,GUARDIAN_NAGA,HUNGRY_SORROWSWORN,
					GRAY_RENDER,YUANTI_ANATHEMA};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.FOREST, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {COCKATRICE,WORG,HIPPOGRIFF,ANKHEG,CENTAUR,GRIFFON,LEUCROTTA,MANTICORE,PHASE_SPIDER,BULETTE,GORGON,CHIMERA};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.GRASSLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {WORG,GIANT_STRIDER,HARPY,HIPPOGRIFF,GRIFFON,PERYTON,SHADOW_MASTIFF,MANTICORE,PHASE_SPIDER,SHADOW_MASTIFF_ALPHA,BULETTE,GORGON,CHIMERA,ROC,GRAY_RENDER};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.ROCKYHILLS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {YUANTI_MALISON,GIRALLON,YUANTI_MIND_WHISPERER,YUANTI_NIGHTMARE_SPEAKER,YUANTI_PIT_MASTER,YUANTI_ABOMINATION,YUANTI_ANATHEMA};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.JUNGLE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {YOUNG_KRUTHIK,GIANT_STRIDER,HARPY,HIPPOGRIFF,ADULT_KRUTHIK,GRIFFON,PERYTON,BASILISK,MANTICORE,BULETTE,KRUTHIK_HIVE_LORD,CHIMERA,
					LOST_SORROWSWORN,LONELY_SORROWSWORN,ROC};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.MOUNTAINS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {WRETCHED_SORROWSWORN,SHADOW_MASTIFF,SHADOW_MASTIFF_ALPHA,YUANTI_MALISON,CATOBLEPAS,LOST_SORROWSWORN,YUANTI_ABOMINATION,HYDRA,FROGHEMOTH};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WETLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {YOUNG_KRUTHIK,MALE_STEEDER,WRETCHED_SORROWSWORN,CHITINE,DARKMANTLE,PIERCER,RUST_MONSTER,FEMALE_STEEDER,GIANT_STRIDER,ADULT_KRUTHIK,
					CARRION_CRAWLER,GRICK,MIMIC,CAVE_FISHER,CHOLDRITH,DOPPELGANGER,HOOK_HORROR,MINOTAUR,PHASE_SPIDER,TRAPPER,YUANTI_MIND_WHISPERER,YUANTI_NIGHTMARE_SPEAKER,
					KRUTHIK_HIVE_LORD,ROPER,UMBER_HULK,YUANTI_PIT_MASTER,CHIMERA,DRIDER,GRICK_ALPHA,LOST_SORROWSWORN,SPIRIT_NAGA,LONELY_SORROWSWORN,FROGHEMOTH,BEHIR,HUNGRY_SORROWSWORN,
					YUANTI_ANATHEMA,ANGRY_SORROWSWORN,PURPLE_WORM};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.VOID, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {MERROW,KRAKEN};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.LAKE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {MERROW,KRAKEN};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WATER, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {WRETCHED_SORROWSWORN,MIMIC,DOPPELGANGER,PHASE_SPIDER,BANDERHOBB,LOST_SORROWSWORN,LONELY_SORROWSWORN,HUNGRY_SORROWSWORN,ANGRY_SORROWSWORN,TARRASQUE};
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
