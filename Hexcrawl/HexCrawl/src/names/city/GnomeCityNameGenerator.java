package names.city;

import data.Indexible;
import names.IndexibleNameGenerator;

public class GnomeCityNameGenerator extends IndexibleNameGenerator{
	private static final String[] prefixes = {"Skip","Dyna","Draxle","Swelling","Rozzer","Numdick","Ten","Arp","Brol","Fram"};
	private static final String[] suffixes = {"helm","ham","point","guard","hollow","un"," Mapple"," Glegriol","menthool","das"};

	@Override
	public String getName(int... val) {
		if(val.length<2) throw new IllegalArgumentException("Expected 2 or more values");
		return getElementFromArray(prefixes,val[0])+getElementFromArray(suffixes,val[1]);
	}

	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(prefixes,obj)+getElementFromArray(suffixes,obj);
	}

}