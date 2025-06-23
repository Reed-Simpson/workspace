package names.threat;

import java.util.ArrayList;

import data.threat.Threat;
import data.threat.subtype.DragonType;
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

}
