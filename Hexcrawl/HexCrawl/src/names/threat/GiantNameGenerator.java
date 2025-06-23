package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.threat.Threat;
import data.threat.subtype.GiantType;
import util.Util;

public class GiantNameGenerator extends ThreatNameGenerator {
	private static final String NAMEPART = "Kjorlling,Snigelf,Mentag,Gesvatr,Beirmar,Jorufmare,Jerrvar,Hedferth,Esbelen,Brirsianus,Ingmifk,Alfark,Sohrarn,"
			+ "Rakhild,Hroasl,Orlinna,Evetfrodi,Grosdvild,Aetael,Hillfa,Fjorma,Engman,Vigmor,Torvarik,Temdvild,Ulfrarm"; //fantasy name generator nordic
	private static WeightedTable<String> nameparts;
	

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


}
