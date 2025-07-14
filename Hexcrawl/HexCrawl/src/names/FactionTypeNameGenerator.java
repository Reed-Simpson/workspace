package names;

import data.Indexible;
import util.Util;

public abstract class FactionTypeNameGenerator extends IndexibleNameGenerator {

	public abstract String getAdj(Indexible obj);
	public abstract String getNoun(Indexible obj);
	
	public final String getName(Indexible obj) {
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

}
