package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.encounters.EncounterModel;
import data.magic.MagicModel;
import data.npc.NPC;
import data.npc.NPCModel;
import data.population.Species;
import data.threat.Threat;
import data.threat.subtype.ConstructType;
import util.Util;

public class ConstructNameGenerator extends ThreatNameGenerator {
	private static final String INEVITABLE_NOUNS= "Watcher,Sentry,Vanguard,Enforcer,Raider,Killer,Marauder,Crusader,Assassin,Punisher,Tracker,Hunter,Stalker,Pursuer,Investigator,Slayer";
	private static final String JOB_NOUNS= "Sentinel,Guard,Warden,Shield,Bastion,Gatekeeper,Aegis,Protector,Watcher,Sentry,Vanguard,Enforcer,Raider,Killer,Marauder,Slayer";
	private static final String[] ADJECTIVES = {"${material}","${metal}","${color},Metal,Stone"};
	private static final String NAME_NOUNS = "${physical element},${ethereal element},${physical form},${animal}";
	private static final String DEFECTS = "${insanity},is ${item trait},is ${object element}";

	private static WeightedTable<String> namenouns;
	private static WeightedTable<String> jobNouns;
	private static WeightedTable<String> inevitableNouns;
	private static WeightedTable<String> modrons;
	private static WeightedTable<String> defects;
	
	private static void populateAll() {
		jobNouns = new WeightedTable<String>();
		populate(jobNouns,JOB_NOUNS,",");
		namenouns = new WeightedTable<String>();
		populate(namenouns,NAME_NOUNS,",");
		modrons = new WeightedTable<String>();
		modrons.put("Decaton", 100);
		modrons.put("Nonaton", 81);
		modrons.put("Octon", 64);
		modrons.put("Septon", 49);
		modrons.put("Hexon", 36);
		modrons.put("Quinton", 25);
		modrons.put("Quarton", 16);
		modrons.put("Tertian", 9);
		modrons.put("Secundus", 4);
		modrons.put("Primus", 1);
		defects = new WeightedTable<String>();
		populate(defects,DEFECTS,",");
		inevitableNouns = new WeightedTable<String>();
		populate(inevitableNouns,INEVITABLE_NOUNS,",");
	}
	
	public static String getJobNoun(Indexible obj) {
		if(jobNouns==null) populateAll();
		return namenouns.getByWeight(obj);
	}	
	public static String getNameNoun(Indexible obj) {
		if(namenouns==null) populateAll();
		return namenouns.getByWeight(obj);
	}
	public static String getInevitableNoun(Indexible obj) {
		if(inevitableNouns==null) populateAll();
		return inevitableNouns.getByWeight(obj);
	}
	public static String getModronType(Indexible obj) {
		if(modrons==null) populateAll();
		return modrons.getByWeight(obj);
	}
	public static String getDefect(Indexible obj) {
		if(defects==null) populateAll();
		return defects.getByWeight(obj);
	}
	public static String getMissionNoun(Indexible obj) {
		String mission = NPCModel.getMission(obj);
		if(mission.endsWith("e")) mission+="r";
		else mission+="er";
		return mission;
	}

	@Deprecated
	@Override
	public String getName(int... val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName(Threat threat) {
		ConstructType type = (ConstructType) threat.getSubtype();
		switch(type) {
		case ARTIFICER: return getArtificerName(threat);
		case AWAKENED: return getGolemName(threat);
		case INEVITABLE: return getInevitableName(threat);
		case MODRON: return getModronName(threat);
		case RELIC: return getGolemName(threat);
		default: throw new IllegalArgumentException("Unrecognized ConstructType: "+type);
		}
	}
	private String getArtificerName(Threat threat) {
		String artificer = MagicModel.getArtificer(threat);
		NPC npc = threat.getNPC();
		Species species = npc.getSpecies();
		String speciesName = species.name();
		if(Species.GOBLINOID.equals(species)) speciesName = HumanoidNameGenerator.getGoblinoid(threat);
		String result = npc.getName();
		if(result==null) result = species.getNPCNameGen().getName(threat);
		return Util.toCamelCase(result+", The "+speciesName+" "+artificer);
	}
	private String getGolemName(Threat threat) {
		String name = getPartialName(threat);
		String title = getGolemTitle(threat);
		return Util.toCamelCase(Util.formatTableResult(name+", "+title,threat));
	}
	private String getGolemTitle(Threat threat) {
		String titleDescriptor = EncounterModel.getObj(threat);
		String titleNoun;
		if(threat.reduceTempId(2)%2==0) titleNoun = getJobNoun(threat);
		else titleNoun = getMissionNoun(threat);
		String title = titleDescriptor+" "+titleNoun;
		return "The "+title;
	}
	private String getPartialName(Threat threat) {
		String part1 = getElementFromArray(ADJECTIVES, threat);
		String part2 = getNameNoun(threat);
		String name = part1+part2;
		return name;
	}
	private String getInevitableName(Threat threat) {
		String name = getPartialName(threat);
		String title = getInevitableTitle(threat);
		return Util.toCamelCase(Util.formatTableResult(name+", "+title,threat));
	}
	private String getInevitableTitle(Threat threat) {
		String titleDescriptor = EncounterModel.getObj(threat);
		String titleNoun = getInevitableNoun(threat);
		String title = titleDescriptor+" "+titleNoun;
		return "The "+title;
	}
	private String getModronName(Threat threat) {
		String type = getModronType(threat);
		int count = modrons.get(type);
		int number = threat.reduceTempId(count)+1;
		String defect = getDefect(threat);
		return "#"+number+" The "+type+" who "+defect;
	}

}
