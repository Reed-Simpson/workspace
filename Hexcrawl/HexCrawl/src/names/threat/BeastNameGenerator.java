package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.magic.MagicModel;
import data.npc.Creature;
import data.npc.NPC;
import data.population.Species;
import data.threat.CreatureType;
import data.threat.Threat;
import data.threat.subtype.BeastType;
import data.threat.subtype.HumanoidType;
import names.FactionNameGenerator;
import util.Util;

public class BeastNameGenerator extends ThreatNameGenerator{
	private static final String BEASTNOUNS = "Beak,Claw,Eye,Fang,Fin,Fur,Feet,Leg,Horn,Slime,Nose,Scale,Shell,Spike,Spine,Sting,Teeth,Tail,Talon,Arm,Tusk,Wing,"+
			"Beast,Horror,Creature,Menace,Threat,Nuisance,Plague,Pest,Curse,Scourge,Thorn,Bristle,Shadow,Hunter,Killer,Raider,Invader";
	private static WeightedTable<String> beastnouns;
	private static final String BEASTADJS = "${monster personality},${basic color}";

	private static final String FACTION_ADJECTIVES = "Primal,Natural,Beastial";
	private static final String FACTION_NOUNS = FactionNameGenerator.CHURCH_NOUNS;
	private static final String FACTION_DOMAINS = "${animal}s,balance,betrayal,chaos,conquest,cycles,death,destiny,dreams,${element},"+
			"judgement,monsters,moon,motherhood,plague,purification,summer,sun,sea,wild,winter";
	private static WeightedTable<String> faction_adjectives;
	private static WeightedTable<String> faction_nouns;
	private static WeightedTable<String> faction_domains;

	private static void populateAllTables() {
		faction_adjectives = new WeightedTable<String>();
		populate(faction_adjectives,FACTION_ADJECTIVES,",");
		faction_nouns = new WeightedTable<String>();
		populate(faction_nouns,FACTION_NOUNS,",");
		faction_domains = new WeightedTable<String>();
		populate(faction_domains,FACTION_DOMAINS,",");
	}
	
	
	
	private static WeightedTable<String> beastadjs;
	public static String getBeastNoun(Indexible obj) {
		if(beastnouns==null) {
			beastnouns = new WeightedTable<String>();
			populate(beastnouns, BEASTNOUNS, ",");
		}
		return beastnouns.getByWeight(obj);
	}
	public static String getBeastAdj(Indexible obj) {
		if(beastadjs==null) {
			beastadjs = new WeightedTable<String>();
			populate(beastadjs, BEASTADJS, ",");
		}
		return Util.formatTableResult(beastadjs.getByWeight(obj),obj);
	}
	
	@Override
	public String getName(Creature obj) {
		BeastType subtype = (BeastType) obj.getSpecies();
		switch(subtype) {
		case AWAKENED: return getBeastName(obj);
		case LYCANTHROPE: return getLycanthropeName(obj);
		case DRUID: return getDruidName(obj);
		default: throw new IllegalArgumentException(subtype.name());
		}
	}
	public static String getBeastName(Indexible threat) {
		return "The "+getBeastAdj(threat)+" "+getBeastNoun(threat);
	}
	private String getLycanthropeName(Creature threat) {
		threat.setSpecies(HumanoidType.LYCANTHROPE);
		return CreatureType.HUMANOID.getNameGen().getName(threat);
	}
	private String getDruidName(Creature threat) {
		NPC npc;
		if(threat instanceof Threat) {
			npc = ((Threat)threat).getNPC();
		}else {
			npc = (NPC)threat;
		}
		String druid = MagicModel.getDruid(threat);
		Species species = npc.getSpecies();
		String speciesName = species.toString();
		if(npc.getSubspecies()!=null) speciesName = npc.getSubspecies();
		String result = npc.getName();
		if(result==null) result = species.getNameGen().getName(threat);
		return result+", The "+speciesName+" "+druid;
	}
	@Override
	public String getTitle(Creature obj) {
		return "";
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
		if(faction_domains==null) populateAllTables();
		if(threat.getSubtype().containedIn(new Species[] {HumanoidType.LYCANTHROPE,BeastType.LYCANTHROPE})) {
			return HumanoidNameGenerator.getLycanthrope(threat);
		}else {
			return faction_domains.getByWeight(threat);
		}
	}

}
