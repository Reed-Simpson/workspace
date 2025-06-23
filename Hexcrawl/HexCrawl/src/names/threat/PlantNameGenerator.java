package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.location.LocationModel;
import data.magic.MagicModel;
import data.monster.MonsterModel;
import data.npc.NPC;
import data.population.Species;
import data.threat.Threat;
import data.threat.subtype.PlantType;
import names.wilderness.WildernessNameGenerator;

public class PlantNameGenerator extends ThreatNameGenerator {
	private static final String MYCONIDS = "Capbloom,Blightbloom,Moldshade,Shroomtide,Luminveil,Fungalith,Moss-shroud,Mycoria,Sporethra,Toadstool,Embercap,Sporethun the Mellow,Myceloth,Mushcrown,"
			+ "Mycota,Trufflejade";
	private static WeightedTable<String> myconids;

	public static String getMyconidName(Indexible obj) {
		if(myconids==null) {
			myconids = new WeightedTable<String>();
			populate(myconids, MYCONIDS, ",");
		}
		return myconids.getByWeight(obj);
	}
	@Deprecated
	@Override
	public String getName(int... val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName(Threat threat) {
		PlantType type = (PlantType) threat.getSubtype();
		switch(type) {
		case AWAKENED:return getPlantName(threat);
		case BODYTAKER:return getBodytakerName(threat);
		case DRUID:return getDruidName(threat);
		case MYCONID:return getMyconidName(threat);
		case TREANT:return getTreantName(threat);
		default: throw new IllegalArgumentException("Unrecognized subtype: "+type);
		}
	}

	private String getPlantName(Threat threat) {
		String personality = MonsterModel.getPersonality(threat);
		String plant = LocationModel.getPoisonousPlant(threat);
		return personality+" "+plant;
	}

	private String getBodytakerName(Threat threat) {
		return threat.getNPC().getName();
	}
	private String getDruidName(Threat threat) {
		String druid = MagicModel.getDruid(threat);
		NPC npc = threat.getNPC();
		Species species = npc.getSpecies();
		String result = npc.getName();
		if(result==null) result = species.getNPCNameGen().getName(threat);
		return result+", The "+Species.getString(species, npc)+" "+druid;
	}

	private String getTreantName(Threat threat) {
		return "Treants of the "+WildernessNameGenerator.getRegionName(threat)+" Forest";
	}


}
