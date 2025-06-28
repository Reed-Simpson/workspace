package names.threat;

import data.Indexible;
import data.npc.NPCModel;
import data.threat.Threat;
import names.IndexibleNameGenerator;
import util.Util;

public abstract class ThreatNameGenerator extends IndexibleNameGenerator{

	@Deprecated
	public String getName(Threat threat,int... index) {
		int[] result = new int[index.length+2];
		if(threat.getSubtype()!=null) result[0] = threat.getSubtype().getId();
		if(threat.getNPC()!=null) result[1] = threat.getNPC().getSpecies().getIndex();
		for(int i=0;i<index.length;i++) {
			result[i+2]=index[i];
		}
		return formatThreat(threat,getName(result));
	}
	
	public String formatThreat(Threat threat,String result) {
		result = Util.formatSubtype(result,threat.getSubtype());
		result = Util.formatSpecies(result,threat.getNPC().getSpecies());
		return result;
	}
	
	public String getName(Indexible obj) {
		if(obj instanceof Threat) {
			Threat threat = (Threat) obj;
			return getName(threat);
		}
		throw new IllegalArgumentException("Expected threat object");
	}
	
	public abstract String getName(Threat threat) ;

	public abstract String getFactionAdjective(Indexible threat);
	public abstract String getFactionNoun(Indexible threat);
	public String getDomain(Threat threat) {
		return NPCModel.getDomain(threat);
	}
}
