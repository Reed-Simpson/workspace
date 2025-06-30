package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.magic.MagicModel;
import data.npc.NPC;
import data.population.NPCSpecies;
import data.population.Species;
import data.threat.Threat;
import data.threat.subtype.HumanoidType;
import util.Util;

public class HumanoidNameGenerator extends ThreatNameGenerator{
	private static final String[] GOBLINOIDS = NPCSpecies.GOBLINOIDS;
	private static final String[] LYCANTHROPE = {"Tiger","Bear","Boar","Rat","Rat","Wolf","Wolf","${animal}"};

	private static final String FACTION_ADJECTIVES = "";
	private static final String FACTION_NOUNS = "";
	private static WeightedTable<String> faction_adjectives;
	private static WeightedTable<String> faction_nouns;

	private static void populateAllTables() {
		faction_adjectives = new WeightedTable<String>();
		populate(faction_adjectives,FACTION_ADJECTIVES,",");
		faction_nouns = new WeightedTable<String>();
		populate(faction_nouns,FACTION_NOUNS,",");
	}
	public static String getGoblinoid(Indexible obj) {
		return getElementFromArray(GOBLINOIDS,obj);
	}
	public static String getLycanthrope(Indexible obj) {
		return "Were-"+Util.formatTableResult(getElementFromArray(LYCANTHROPE,obj),obj);
	}

	
	
	@Override
	public String getName(Threat threat) {
		HumanoidType subtype = (HumanoidType) threat.getSubtype();
		switch(subtype) {
		case CULTIST: return getHumanoidName(threat,"Cult Leader");
		case WARLORD: return getHumanoidName(threat,"Warlord");
		case SPELLCASTER: return getHumanoidName(threat,MagicModel.getSpellcaster(threat));
		case BANDIT: return getHumanoidName(threat,"Bandit Lord");
		case LYCANTHROPE: return getHumanoidName(threat,getLycanthrope(threat));
		case GOBLINOID: return getGoblinoidName(threat);
		case KOBOLD: return getKoboldName(threat);
		case ORC: return getOrcName(threat);
		case LIZARDFOLK: return getLizardfolkName(threat);
		case DROW: return getDrowName(threat);
		case DUREGAR: return getDuregarName(threat);
		case GNOLL: return getGnollName(threat);
		case TROGLODYTE: return getTrogName(threat);
		default: throw new IllegalArgumentException(subtype.name());
		}
	}

	private String getHumanoidName(Threat threat,String profession) {
		NPC npc = threat.getNPC();
		Species species = npc.getSpecies();
		String result = npc.getName();
		String speciesName = species.toString();
		if(npc.getSubspecies()!=null) speciesName = npc.getSubspecies();
		if(result==null) result = species.getNameGen().getName(threat);
		return result+", The "+speciesName+" "+profession;
	}
	private String getGoblinoidName(Threat threat) {
		String goblinoid = getGoblinoid(threat);
		NPC npc = threat.getNPC();
		npc.setSubspecies(goblinoid);
		String result = NPCSpecies.GOBLINOID.getNameGen().getName(threat);
		return result+", The "+goblinoid+" Warlord";
	}
	private String getKoboldName(Threat threat) {
		String result = NPCSpecies.KOBOLD.getNameGen().getName(threat);
		return result+", The Kobold Denmaster";
	}
	private String getOrcName(Threat threat) {
		String result = NPCSpecies.ORC.getNameGen().getName(threat);
		return result+", The Orc Warlord";
	}
	private String getLizardfolkName(Threat threat) {
		String result = NPCSpecies.LIZARDFOLK.getNameGen().getName(threat);
		return result+", The Lizardfolk Chieftan";
	}
	private String getDrowName(Threat threat) {
		String result = NPCSpecies.ELF.getNameGen().getName(threat);
		return result+", The Drow High Priestess";
	}
	private String getDuregarName(Threat threat) {
		String result = NPCSpecies.DWARF.getNameGen().getName(threat);
		return result+", The Duregar General";
	}
	private String getGnollName(Threat threat) {
		String result = NPCSpecies.GNOLL.getNameGen().getName(threat);
		return result+", The Gnoll Alpha";
	}
	private String getTrogName(Threat threat) {
		String result = NPCSpecies.TROG.getNameGen().getName(threat);
		return result+", The Troglodyte Chieftan";
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

}
