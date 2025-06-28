package names.threat;

import java.util.ArrayList;

import data.Indexible;
import data.WeightedTable;
import data.threat.CreatureSubtype;
import data.threat.Threat;
import data.threat.subtype.DragonType;
import names.FactionNameGenerator;
import util.Util;

public class DragonNameGenerator extends ThreatNameGenerator{
	private static final String[] PART1 = {"Ar","Rel","Rok","Ruin","Dj","Tym","Har","Har","Xorv"};
	private static final String[] PART2 = {"angh","ush","erad","anche","one","in","keth"};
	private static final String[] PART3 = {"thal","ashuak","vayem","maur","thorl","spoke","endi","kusold","thymar","bar","ther","glast","thar","troth"};

	private static final String[] ADJECTIVES = {"The Magnificent","The Elder","The Winged","The Devouring","The Superior","The Enduring"};
	private static final String[] BLACK = {"The Black ${noun}","Lord of Swamps","The Black Terror","${adjective} Corrosion"};
	private static final String[] BLUE = {"The Blue ${noun}","Lord of Deserts","The Blue Hunter","${adjective} Lightning"};
	private static final String[] GREEN = {"The Green ${noun}","Lord of Forests","The Green Deceiver","${adjective} Poison"};
	private static final String[] RED = {"The Red ${noun}","Lord of Mountains","The Red Destroyer","${adjective} Conflagration"};
	private static final String[] WHITE = {"The White ${noun}","Lord of Tundra","The White Scavenger","${adjective} Frost"};
	private static final String[] BRASS = {"The Brass ${noun}","Guardian of The Sand","The Great Speaker","${adjective} Benefactor"};
	private static final String[] BRONZE = {"The Bronze ${noun}","Guardian of The Sea","The Great Savior","${adjective} Judge"};
	private static final String[] COPPER = {"The Copper ${noun}","Guardian of The Stone","The Great Bard","${adjective} Sage"};
	private static final String[] GOLD = {"The Gold ${noun}","Guardian of The Plains","The Great Seeker","${adjective} Resplendence"};
	private static final String[] SILVER = {"The Silver ${noun}","Guardian of the City","The Great Protector","${adjective} Observer"};
	private static final String[] AMETHYST = {"The Purple ${noun}","The Logical ${noun}","${adjective} Philosopher","The Arcane ${noun}"};
	private static final String[] CRYSTAL = {"The Crystal ${noun}","The Gregarious ${noun}","${adjective} Starsage","The Radiant ${noun}"};
	private static final String[] EMERALD = {"The Emerald ${noun}","The Reclusive ${noun}","${adjective} Historian","The Psychic ${noun}"};
	private static final String[] SAPPHIRE = {"The Sapphire ${noun}","The Strategic ${noun}","${adjective} General","The Thundering ${noun}"};
	private static final String[] TOPAZ = {"The Yellow ${noun}","The Cynical ${noun}","${adjective} Futurist","The Withering ${noun}"};
	private static final String[] DEEP = {"The Deep ${noun}","${adjective} Survivor"};
	private static final String[] MOONSTONE = {"The Opalescent ${noun}","${adjective} Muse"};
	private static final String[] SHADOW = {"The Shadowy ${noun}","${adjective} Hermit"};
	private static final String[] DRACOLICH = {"The Immortal ${noun}","${adjective} Fiend"};
	private static final String[] NOUNS = {"Enigma","Conqueror","Tyrant","Mastermind","Slumberer","Glutton","Sorcerer","Beguiler","Missile"};

	private static final String FACTION_ADJECTIVES = "Draconic,Winged,${subtype} Scaled,${subtype} Winged,${subtype} Shield,${subtype} Fang,${subtype} Claw,${subtype} Steel,${subtype} Heart";
	private static final String FACTION_NOUNS = FactionNameGenerator.CHURCH_NOUNS+","+FactionNameGenerator.FREECOMPANY_NOUNS;
	private static WeightedTable<String> faction_adjectives;
	private static WeightedTable<String> faction_nouns;

	private static final String[] BLACK_DOMAIN = {"Swamps","Acid"};
	private static final String[] BLUE_DOMAIN = {"Deserts","Lightning"};
	private static final String[] GREEN_DOMAIN = {"Forests","Poison"};
	private static final String[] RED_DOMAIN = {"Mountains","Fire"};
	private static final String[] WHITE_DOMAIN = {"Tundra","Frost"};
	private static final String[] BRASS_DOMAIN = {"Sand","Diplomacy"};
	private static final String[] BRONZE_DOMAIN = {"Sea","Justice"};
	private static final String[] COPPER_DOMAIN = {"Stone","Knowledge"};
	private static final String[] GOLD_DOMAIN = {"Plains","Pride"};
	private static final String[] SILVER_DOMAIN = {"City","Protection"};
	private static final String[] AMETHYST_DOMAIN = {"Logic","Philosophy"};
	private static final String[] CRYSTAL_DOMAIN = {"Diplomacy","Stars"};
	private static final String[] EMERALD_DOMAIN = {"Isolation","History"};
	private static final String[] SAPPHIRE_DOMAIN = {"Strategy","Military"};
	private static final String[] TOPAZ_DOMAIN = {"Cynicism","Technology"};
	private static final String[] DEEP_DOMAIN = {"Underdark","Survival"};
	private static final String[] MOONSTONE_DOMAIN = {"Inspiration"};
	private static final String[] SHADOW_DOMAIN = {"Shadows","Deception"};
	private static final String[] DRACOLICH_DOMAIN = {"Undead","Immortality"};

	private static void populateAllTables() {
		faction_adjectives = new WeightedTable<String>();
		populate(faction_adjectives,FACTION_ADJECTIVES,",");
		faction_nouns = new WeightedTable<String>();
		populate(faction_nouns,FACTION_NOUNS,",");
	}
	@Deprecated
	@Override
	public String getName(int... val) {
		if(val.length<5) throw new IllegalArgumentException("Expected 5 or more values");
		DragonType type = DragonType.getFromID(val[0]);
		String part1 = getElementFromArray(PART1, val[2]);
		String part2 = getElementFromArray(PART2, val[3]);
		String part3 = getElementFromArray(PART3, val[4]);
		String title = getTitle(type, val[5]);
		return part1+part2+part3+", "+title;
	}

	@Deprecated
	private String getTitle(DragonType type,int index) {
		ArrayList<String> array = new ArrayList<String>();
		for(String s:getTitleArray(type)) {
			if(s.contains("${noun}")) {
				for(String s1:NOUNS) {
					array.add(Util.replace(s, "${noun}", s1));
				}
			}else if(s.contains("${adjective}")) {
				for(String s1:ADJECTIVES) {
					array.add(Util.replace(s, "${adjective}", s1));
				}
			}else {
				array.add(s);
			}
		}
		return getElementFromArray(array.toArray(new String[0]),index);
	}

	private static String[] getTitleArray(DragonType type) {
		switch (type) {
		case BLACK: return BLACK;
		case BLUE: return BLUE;
		case GREEN: return GREEN;
		case RED: return RED;
		case WHITE: return WHITE;
		case BRASS: return BRASS;
		case BRONZE: return BRONZE;
		case COPPER: return COPPER;
		case GOLD: return GOLD;
		case SILVER: return SILVER;
		case AMETHYST: return AMETHYST;
		case CRYSTAL: return CRYSTAL;
		case EMERALD: return EMERALD;
		case SAPPHIRE: return SAPPHIRE;
		case TOPAZ: return TOPAZ;
		case DEEP: return DEEP;
		case MOONSTONE: return MOONSTONE;
		case SHADOW: return SHADOW;
		case DRACOLICH: return DRACOLICH;
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}
	private static String[] getDomainArray(CreatureSubtype creatureSubtype) {
		if(creatureSubtype instanceof DragonType) {
			DragonType type = (DragonType) creatureSubtype;
			switch (type) {
			case BLACK: return BLACK_DOMAIN;
			case BLUE: return BLUE_DOMAIN;
			case GREEN: return GREEN_DOMAIN;
			case RED: return RED_DOMAIN;
			case WHITE: return WHITE_DOMAIN;
			case BRASS: return BRASS_DOMAIN;
			case BRONZE: return BRONZE_DOMAIN;
			case COPPER: return COPPER_DOMAIN;
			case GOLD: return GOLD_DOMAIN;
			case SILVER: return SILVER_DOMAIN;
			case AMETHYST: return AMETHYST_DOMAIN;
			case CRYSTAL: return CRYSTAL_DOMAIN;
			case EMERALD: return EMERALD_DOMAIN;
			case SAPPHIRE: return SAPPHIRE_DOMAIN;
			case TOPAZ: return TOPAZ_DOMAIN;
			case DEEP: return DEEP_DOMAIN;
			case MOONSTONE: return MOONSTONE_DOMAIN;
			case SHADOW: return SHADOW_DOMAIN;
			case DRACOLICH: return DRACOLICH_DOMAIN;
			}
		}
		throw new IllegalArgumentException("Unexpected value: " + creatureSubtype);
	}

	@Override
	public String getName(Threat threat) {
		DragonType type = (DragonType) threat.getSubtype();
		return getName(threat, type);
	}

	public static String getName(Threat threat,DragonType type) {
		String part1 = getElementFromArray(PART1, threat);
		String part2 = getElementFromArray(PART2, threat);
		String part3 = getElementFromArray(PART3, threat);
		String title = getTitle(threat,type);
		return part1+part2+part3+", "+title;
	}
	private static String getTitle(Threat threat,DragonType type) {
		ArrayList<String> array = new ArrayList<String>();
		for(String s:getTitleArray(type)) {
			if(s.contains("${noun}")) {
				for(String s1:NOUNS) {
					array.add(Util.replace(s, "${noun}", s1));
				}
			}else if(s.contains("${adjective}")) {
				for(String s1:ADJECTIVES) {
					array.add(Util.replace(s, "${adjective}", s1));
				}
			}else {
				array.add(s);
			}
		}
		return getElementFromArray(array.toArray(new String[0]),threat);
	}
	@Override
	public String getFactionAdjective(Indexible threat) {
		if(faction_adjectives==null) populateAllTables();
		return faction_adjectives.getByWeight(threat);
	}

	@Override
	public String getFactionNoun(Indexible threat) {
		if(faction_nouns==null) populateAllTables();
		return faction_nouns.getByWeight(threat);
	}

	@Override
	public String getDomain(Threat threat) {
		if(threat.reduceTempId(2)==0) return super.getDomain(threat);
		else{
			return (String) Util.getElementFromArray(getDomainArray(threat.getSubtype()),threat);
		}
	}

}
