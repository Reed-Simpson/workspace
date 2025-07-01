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
			Creature creature = (Creature) obj;
			return getName(creature);
		}
		throw new IllegalArgumentException("Expected threat object");
	}

	public abstract String getName(Creature obj) ;
	public abstract String getTitle(Creature obj) ;

	public abstract String getFactionAdjective(Indexible obj);
	public abstract String getFactionNoun(Indexible obj);
	public String getDomain(Threat threat) {
		return NPCModel.getDomain(threat);
	}
	
	public String getNameAndTitle(Creature obj) {
		return getName(obj)+getTitle(obj);
	}
}
