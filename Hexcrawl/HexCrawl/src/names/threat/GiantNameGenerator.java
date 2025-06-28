package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.threat.Threat;
import data.threat.subtype.GiantType;
import names.FactionNameGenerator;
import util.Util;

public class GiantNameGenerator extends ThreatNameGenerator {
	private static final String NAMEPART = "Kjorlling,Snigelf,Mentag,Gesvatr,Beirmar,Jorufmare,Jerrvar,Hedferth,Esbelen,Brirsianus,Ingmifk,Alfark,Sohrarn,"
			+ "Rakhild,Hroasl,Orlinna,Evetfrodi,Grosdvild,Aetael,Hillfa,Fjorma,Engman,Vigmor,Torvarik,Temdvild,Ulfrarm"; //fantasy name generator nordic
	private static WeightedTable<String> nameparts;

	private static final String FACTION_ADJECTIVES = FactionNameGenerator.OUTLANDERCLAN_ADJECTIVES;
	private static final String FACTION_NOUNS = FactionNameGenerator.OUTLANDERCLAN_NOUNS;
	private static WeightedTable<String> faction_adjectives;
	private static WeightedTable<String> faction_nouns;

	private static void populateAllTables() {
		faction_adjectives = new WeightedTable<String>();
		populate(faction_adjectives,FACTION_ADJECTIVES,",");
		faction_nouns = new WeightedTable<String>();
		populate(faction_nouns,FACTION_NOUNS,",");
	}

	private static void populateAll() {
		nameparts = new WeightedTable<String>();
		populate(nameparts,NAMEPART,",");
	}
	public static String getNamePart(Indexible obj) {
		if(nameparts==null) populateAll();
		return nameparts.getByWeight(obj);
	}	

	@Deprecated
	@Override
	public String getName(int... val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName(Threat threat) {
		GiantType type = (GiantType) threat.getSubtype();
		// TODO Auto-generated method stub
		return Util.toCamelCase(getNamePart(threat)+" The "+type);
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
