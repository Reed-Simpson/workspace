package names.threat;

import data.Indexible;
import data.threat.CreatureSubtype;
import data.threat.Threat;
import data.threat.subtype.CelestialType;
import util.Util;

public class CelestialNameGenerator extends ThreatNameGenerator{
	private static final String[] TITLE = {"Whose ${form} is ${effect}","The ${personality} ${subtype} of ${domain}","The ${personality} Patron of ${job}",
			"The ${personality} Patron of ${hobby}","The ${personality} Patron of ${relationship}s","The ${personality} Champion of the ${misfortune}"};
	
	@Deprecated
	@Override
	public String getName(int... val) {
		if(val.length<5) throw new IllegalArgumentException("Expected 5 or more values");
		CreatureSubtype type = CelestialType.getFromID(val[0]);
		String partial = FiendNameGenerator.getPartialName(val[1],val[2]);
		int[] remainder = Util.getRemainder(val, 3);
		String title = getTitle(type, remainder);
		return partial+", "+title;
	}

	@Deprecated
	private String getTitle(CreatureSubtype type, int... val) {
		int[] remainder = Util.getRemainder(val, 1);
		String title = Util.formatTableResult(getElementFromArray(TITLE, val[0]),new Indexible(remainder));
		
		return title;
	}

	@Override
	public String getName(Threat threat) {
		String partial = FiendNameGenerator.getPartialName(threat);
		String title = Util.formatTableResult(getElementFromArray(TITLE, threat),threat);
		return partial+", "+title;
	}
}
