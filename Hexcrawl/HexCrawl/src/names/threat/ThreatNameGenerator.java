package names.threat;

import data.threat.Threat;
import names.NameGenerator;
import util.Util;

public abstract class ThreatNameGenerator extends NameGenerator{
	
	public String getName(Threat threat,int... index) {
		int[] result = new int[index.length+2];
		if(threat.getSubtype()!=null) result[0] = threat.getSubtype().getId();
		if(threat.getNPC()!=null) result[1] = threat.getNPC().getSpecies().getIndex();
		for(int i=0;i<index.length;i++) {
			result[i+2]=index[i];
		}
		return Util.formatSubtype(getName(result),threat.getSubtype());
	}


}
