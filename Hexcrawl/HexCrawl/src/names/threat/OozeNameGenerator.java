package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.magic.MagicModel;
import data.npc.Creature;
import data.npc.NPC;
import data.population.Species;
import data.threat.Threat;
import data.threat.subtype.OozeType;
import util.Util;

public class OozeNameGenerator extends ThreatNameGenerator {
	private static final String DISEASES = "Pox,Plague,Pestilence,Blight,Scourge,Sickness,Disease,Curse,Affliction,Death";
	private static WeightedTable<String> diseases;
	private static final String DISEASEADJS = "${weirdness},${effect},${mutation},${insanity}";
	private static WeightedTable<String> diseaseadjs;

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

	public static String getDiseaseNoun(Indexible obj) {
		if(diseases==null) {
			diseases = new WeightedTable<String>();
			populate(diseases, DISEASES, ",");
		}
		return diseases.getByWeight(obj);
	}
	public static String getDiseaseAdj(Indexible obj) {
		if(diseaseadjs==null) {
			diseaseadjs = new WeightedTable<String>();
			populate(diseaseadjs, DISEASEADJS, ",");
		}
		return diseaseadjs.getByWeight(obj);
	}

	@Deprecated
	@Override
	public String getName(int... val) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public String getName(Creature threat) {
		NPC npc;
		if(threat instanceof Threat) {
			npc = ((Threat)threat).getNPC();
		}else {
			npc = (NPC)threat;
		}
		OozeType type = (OozeType) threat.getSpecies();
		switch (type) {
		case BLACKPUDDING:return "none";
		case BLOBOFANNIHILATION:return "none";
		case DISEASE:return getDisease(threat);
		case DRAGONBLOODOOZE:return "none";
		case DRUID:return getDruidName(threat);
		case GELATINOUSCUBE:return "none";
		case GREYOOZE:return "none";
		case OBLEX:return npc.getName();
		case OCHREJELLY:return "none";
		case OOZEMASTER:return getDisease(threat);
		default: throw new IllegalArgumentException("Unrecognized subtype: "+type);
		}
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
	private String getDisease(Indexible threat) {
		String adj = Util.formatTableResult(getDiseaseAdj(threat),threat);
		return "The "+adj+" "+getDiseaseNoun(threat);
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

}
