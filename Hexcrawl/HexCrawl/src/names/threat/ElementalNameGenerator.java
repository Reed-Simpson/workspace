package names.threat;

import threat.subtype.ElementalType;

public class ElementalNameGenerator extends ThreatNameGenerator{
	private static final String[] FIRE = {"Fueg","Blyz","Smoldr","Ign","Scald"};
	private static final String[] WATER = {"Mons","Aqual","Typh","Moonfl","Firm"};
	private static final String[] EARTH = {"Valanch","Carb","Ther","Terr","Cor"};
	private static final String[] AIR = {"Tumul","Xyg","Av","Flurr","Cerull"};
	private static final String[] MAGMA = {"Volc","Erupt"};
	private static final String[] OOZE = {"Slim","Mois"};
	private static final String[] ICE = {"Glaci","Cry"};
	private static final String[] LIGHTNING = {"Plasm","Skar"};
	private static final String[] VOID = {"Cimmer","Obscur"};
	private static final String[] SALT = {"Lim","Sla"};
	private static final String[] ENTROPY = {"Nigh","Aeon"};
	private static final String[] CHAOS = {"Narch","Disar"};
	private static final String[] SPACE = {"Nex","Circ"};
	private static final String[] MIND = {"Precip","Luss"};
	private static final String[] EMOTION = {"Sens","Elan"};
	private static final String[] RADIANT = {"Lumin","Hal"};
	private static final String[] BLOOD = {"Hem","Art"};
	private static final String[] NECROTIC = {"Necr","Malign"};
	private static final String[] RAGE = {"Ragn","Spyt"};
	private static final String[] SOUND = {"Son","Temp"};
	private static final String[] STEAM = {"Fern","Vent"};
	private static final String[] SAND = {"Chron","Aev"};
	private static final String[] SUFFIX = {"is","e","us","all","a","os","it","en","ian","or","onne","ere","er","ise","um","ios","ate"};
	

	@Override
	public String getName(int... val) {
		if(val.length<3) throw new IllegalArgumentException("Expected 3 or more values");
		ElementalType type = ElementalType.getFromID(val[0]);
		String part1 = getElementFromArray(getPrefixArray(type), val[2]);
		String part2 = getElementFromArray(SUFFIX, val[3]);
		return part1+part2;
	}


	

	private String[] getPrefixArray(ElementalType type) {
		switch (type) {
		case FIRE: return FIRE;
		case WATER: return WATER;
		case EARTH: return EARTH;
		case AIR: return AIR;
		case MAGMA: return MAGMA;
		case OOZE: return OOZE;
		case ICE: return ICE;
		case LIGHTNING: return LIGHTNING;
		case VOID: return VOID;
		case SALT: return SALT;
		case ENTROPY: return ENTROPY;
		case CHAOS: return CHAOS;
		case SPACE: return SPACE;
		case MIND: return MIND;
		case EMOTION: return EMOTION;
		case RADIANT: return RADIANT;
		case BLOOD: return BLOOD;
		case NECROTIC: return NECROTIC;
		case RAGE: return RAGE;
		case SOUND: return SOUND;
		case STEAM: return STEAM;
		case SAND: return SAND;
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}

}
