package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.location.LocationModel;
import data.magic.MagicModel;
import data.monster.MonsterModel;
import data.npc.Creature;
import data.npc.NPC;
import data.population.Species;
import data.threat.Threat;
import data.threat.subtype.PlantType;
import names.wilderness.WildernessNameGenerator;

public class PlantNameGenerator extends ThreatNameGenerator {
	private static final String MYCONIDS = "Capbloom,Blightbloom,Moldshade,Shroomtide,Luminveil,Fungalith,Moss-shroud,Mycoria,Sporethra,Toadstool,Embercap,Sporethun the Mellow,Myceloth,Mushcrown,"
			+ "Mycota,Trufflejade";
	private static WeightedTable<String> myconids;

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
	public static String getMyconidName(Indexible obj) {
		if(myconids==null) {
			myconids = new WeightedTable<String>();
			populate(myconids, MYCONIDS, ",");
		}
		return myconids.getByWeight(obj);
	}

	@Override
	public String getName(Creature threat) {
		PlantType type = (PlantType) threat.getSpecies();
		switch(type) {
		case AWAKENED:return getPlantName(threat);
		case BODYTAKER:return getBodytakerName(threat);
		case DRUID:return getDruidName(threat);
		case MYCONID:return getMyconidName(threat);
		case TREANT:return getTreantName(threat);
		default: throw new IllegalArgumentException("Unrecognized subtype: "+type);
		}
	}

	private String getPlantName(Indexible threat) {
		String personality = MonsterModel.getPersonality(threat);
		String plant = LocationModel.getPoisonousPlant(threat);
		return personality+" "+plant;
	}

	private String getBodytakerName(Creature threat) {
		NPC npc;
		if(threat instanceof Threat) {
			npc = ((Threat)threat).getNPC();
		}else {
			npc = (NPC)threat;
		}
		return npc.getName();
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
		String result = npc.getName();
		String speciesName = species.toString();
		if(npc.getSubspecies()!=null) speciesName = npc.getSubspecies();
		if(result==null) result = species.getNameGen().getName(threat);
		return result+", The "+speciesName+" "+druid;
	}

	private String getTreantName(Indexible threat) {
		return "Treants of the "+WildernessNameGenerator.getRegionName(threat)+" Forest";
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
		return super.getDomain(threat);
	}

}
