package names.threat;

import data.Indexible;
import data.npc.Creature;
import data.npc.NPCModel;
import data.threat.Threat;
import names.IndexibleNameGenerator;
import util.Util;

public abstract class ThreatNameGenerator extends IndexibleNameGenerator{

	
	public String formatThreat(Threat threat,String result) {
		result = Util.formatSubtype(result,threat.getSubtype());
		result = Util.formatSpecies(result,threat.getNPC().getSpecies());
		return result;
	}
	
	public String getName(Indexible obj) {
		if(obj instanceof Creature) {
			Creature threat = (Creature) obj;
			return getName(threat);
		}
		throw new IllegalArgumentException("Expected threat object");
	}

	public abstract String getName(Creature threat) ;

	public abstract String getFactionAdjective(Indexible threat);
	public abstract String getFactionNoun(Indexible threat);
	public String getDomain(Threat threat) {
		return NPCModel.getDomain(threat);
	}
}
