package names;

import data.Indexible;

public abstract class AdjectiveNounNameGenerator extends IndexibleNameGenerator {

	public abstract String getAdj(Indexible obj);
	public abstract String getNoun(Indexible obj);
	
	public String getName(Indexible obj) {
		return "The "+getAdj(obj)+" "+getNoun(obj);
	}

}
