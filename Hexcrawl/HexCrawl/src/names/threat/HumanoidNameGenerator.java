package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.magic.MagicModel;
import data.npc.Creature;
import data.npc.NPC;
import data.population.NPCSpecies;
import data.population.Species;
import data.threat.Threat;
import data.threat.subtype.BeastType;
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
	public String getName(Creature threat) {
		HumanoidType subtype = (HumanoidType) threat.getSpecies();
		switch(subtype) {
		case CULTIST: return getHumanoidName(threat);
		case WARLORD: return getHumanoidName(threat);
		case SPELLCASTER: return getHumanoidName(threat);
		case BANDIT: return getHumanoidName(threat);
		case LYCANTHROPE: return getHumanoidName(threat);
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
	private NPC getNPC(Creature threat) {
		NPC npc;
		if(threat instanceof Threat) {
			npc = ((Threat)threat).getNPC();
		}else {
			npc = (NPC)threat;
		}
		return npc;
	}

	private String getHumanoidName(Creature threat) {
		NPC npc = getNPC(threat);
		Species species = npc.getSpecies();
		String result = npc.getName();
		if(result==null) result = species.getNameGen().getName(threat);
		return result;
	}
	private String getGoblinoidName(Creature threat) {
		NPC npc = getNPC(threat);
		String goblinoid = getGoblinoid(threat);
		npc.setSubspecies(goblinoid);
		String result = NPCSpecies.GOBLINOID.getNameGen().getName(threat);
		return result;
	}
	private String getKoboldName(Creature threat) {
		String result = NPCSpecies.KOBOLD.getNameGen().getName(threat);
		return result;
	}
	private String getOrcName(Creature threat) {
		String result = NPCSpecies.ORC.getNameGen().getName(threat);
		return result;
	}
	private String getLizardfolkName(Creature threat) {
		String result = NPCSpecies.LIZARDFOLK.getNameGen().getName(threat);
		return result;
	}
	private String getDrowName(Creature threat) {
		String result = NPCSpecies.ELF.getNameGen().getName(threat);
		return result;
	}
	private String getDuregarName(Creature threat) {
		String result = NPCSpecies.DWARF.getNameGen().getName(threat);
		return result;
	}
	private String getGnollName(Creature threat) {
		String result = NPCSpecies.GNOLL.getNameGen().getName(threat);
		return result;
	}
	private String getTrogName(Creature threat) {
		String result = NPCSpecies.TROG.getNameGen().getName(threat);
		return result;
	}
	@Override
	public String getTitle(Creature obj) {
		NPC npc = getNPC(obj);
		HumanoidType subtype = (HumanoidType) obj.getSpecies();
		Species species = npc.getSpecies();
		String speciesName = species.toString();
		if(npc.getSubspecies()!=null) speciesName = npc.getSubspecies();
		switch(subtype) {
		case CULTIST: return ", The "+speciesName+" Cult Leader";
		case WARLORD: return ", The "+speciesName+" Warlord";
		case SPELLCASTER: return ", The "+speciesName+" "+MagicModel.getSpellcaster(obj);
		case BANDIT: return ", The "+speciesName+" Bandit Lord";
		case LYCANTHROPE: return ", The "+speciesName+" Were-${placeholder domain}";
		case GOBLINOID: return ", The "+speciesName+" Warlord";
		case KOBOLD: return ", The Kobold Denmaster";
		case ORC: return ", The Orc Warlord";
		case LIZARDFOLK: return ", The Lizardfolk Chieftan";
		case DROW: return ", The Drow High Priestess";
		case DUREGAR: return ", The Duregar General";
		case GNOLL: return ", The Gnoll Alpha";
		case TROGLODYTE: return ", The Troglodyte Chieftan";
		default: throw new IllegalArgumentException(subtype.name());
		}
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
		if(threat.getSubtype().containedIn(new Species[] {HumanoidType.LYCANTHROPE,BeastType.LYCANTHROPE})) {
			return HumanoidNameGenerator.getLycanthrope(threat);
		}else {
			return super.getDomain(threat);
		}
	}

}
