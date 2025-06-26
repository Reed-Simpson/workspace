package names.threat;

import data.threat.Threat;
import data.threat.subtype.ElementalType;
import util.Util;

public class ElementalNameGenerator extends ThreatNameGenerator{
	private static final String[] FIRE = {"Ach","Agn","Aodh","Azar","Blyz","Bryn","Cinaed","Dian","Eguzk","Fajr","Fiammett","Haul","Fueg","Helen","Heliod","Hest",
			"Ign","Ignat","Int","Iskr","Joash","Keah","Mz","Nin","Pel","Pyrrh","Rav","Sams","Savitr","Scald","Seraph","Shul","Smoldr","Sol","Tesn"};
	private static final String[] WATER = {"Aalt","Afon","Agam","Ald","Aqual","Arethus","Belin","Burim","Daml","Dary","Entem","Eur","Firm","Gal","Heremoan","Iar","Ib","Indr",
			"Jubal","Lain","Llyr","Maraj","May","Meraud","Mer","Moan","Moonfl","Mons","Mort","Muirg","Nanam","Nereid","On","Pelag","Rosem","Saew","Sarasv","Tirt","Tas","Typh","Undin","Yam"};
	private static final String[] EARTH = {"Aramb","Arl","Avant","Atl","Bhum","Carb","Cirotral","Cobal","Cor","Datol","Demet","Dr","Eard","G","Gnom","Iron","Lamb","Mason","Pap","Petr","Rik",
			"Rolan","Steel","Ston","Terr","Titan","Ther","Tlall","Vlanch"};
	private static final String[] AIR = {"Aly","Amater","Anan","Anemon","Anil","Aracel","Ar","Av","Caelest","Cerull","Er","Esen","Eter","Heiran","Hod","Ilm","Inann",
			"Lan","Nephel","Pilv","Rang","Samir","Sor","Tsis","Tumul","Tuul","Vay","Xyg","Yanseb","Yun","Zeph","Zer"};
	private static final String[] MAGMA = {"Volc","Erupt","Maeg","Heatm","Mantl","Slag","Labes"};
	private static final String[] OOZE = {"Slim","Mois","Bwimb","Burbl","Tox","Corros","Ulig"};
	private static final String[] ICE = {"Flurr","Glaci","Cry","Durbaag","Meltem","Nebul","Nix"};
	private static final String[] LIGHTNING = {"Plasm","Skar","Auror","Hal","Zenon","Lichten","Fulg"};
	private static final String[] VOID = {"Cimmer","Obscur","Nothing"};
	private static final String[] SALT = {"Lim","Sla","Saltom"};
	private static final String[] ENTROPY = {"Nigh","Aeon","Decay"};
	private static final String[] CHAOS = {"Narch","Disar","Anarch"};
	private static final String[] SPACE = {"Nex","Circ","Cosm"};
	private static final String[] MIND = {"Precip","Luss","Mentim"};
	private static final String[] EMOTION = {"Sens","Elan","Mot"};
	private static final String[] RADIANT = {"Lumin","Alb","Anatol","Apoll","Ausr","Avtand","Belen","Drit","Eos","Fioral","Lucast","Luc","Lug","Luz"};
	private static final String[] BLOOD = {"Hem","Art","Sang"};
	private static final String[] NECROTIC = {"Necr","Malign","Atrop"};
	private static final String[] RAGE = {"Ragn","Spyt","Ir"};
	private static final String[] SOUND = {"Son","Temp","Tonitr"};
	private static final String[] STEAM = {"Fern","Vent","Vapor"};
	private static final String[] SAND = {"Chron","Aev","Haran"};
	private static final String[] SUFFIX = {"","i","is","us","all","a","en","ian","or","onne","um","ius","ate","eron","azar","ix"};
	private static final String[] TITLE = {"Primordial","Archomental","Prince","God","Ancient","Elder","First"};
	
	@Deprecated
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

	@Override
	public String getName(Threat threat) {
		String[] prefixArray = getPrefixArray((ElementalType) threat.getSubtype());
		String part1 = getElementFromArray(prefixArray, threat);
		String part2 = getElementFromArray(SUFFIX, threat);
		String title = getElementFromArray(TITLE, threat);
		return part1+part2+", "+title+" of "+Util.toCamelCase(threat.getSubtype().getName());
	}

}
