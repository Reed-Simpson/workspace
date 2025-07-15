package names;

import data.Indexible;
import names.threat.ThreatNameGenerator;
import util.Util;

public abstract class FactionTypeNameGenerator extends IndexibleNameGenerator {

	public abstract String getAdj(Indexible obj);
	public abstract String getNoun(Indexible obj);
	
	public String getName(Indexible obj) {
		String adj;
		String noun;
		int index = obj.reduceTempId(4);
		if(index==0) {
			adj = FactionNameGenerator.getAdj(obj) ;
			noun = getNoun(obj);
		} else if(index<3) {
			adj = getAdj(obj);
			noun = getNoun(obj);
		} else {
			adj = getAdj(obj);
			noun = FactionNameGenerator.getNoun(obj);
		}
		if(noun.contains("${adj}")) return Util.replace(noun,"${adj}",adj);
		else return "The "+adj+" "+noun;
	}
	public String getThreatFactionName(Indexible obj, ThreatNameGenerator threatGen) {
		int index = obj.reduceTempId(6);
		String adj = threatGen.getFactionAdjective(obj);
		if("".equals(adj)) adj = this.getAdj(obj);
		String noun = threatGen.getFactionNoun(obj);
		if("".equals(noun)) noun = this.getNoun(obj);
		if(index==0) adj = FactionNameGenerator.getAdj(obj);
		else if(index==1) adj = this.getAdj(obj);
		else if(index==2) noun = FactionNameGenerator.getNoun(obj);
		else if(index==3) noun = this.getNoun(obj);
		if(noun.contains("${adj}")) return Util.replace(noun,"${adj}",adj);
		else return "The "+adj+" "+noun;
	}

}
