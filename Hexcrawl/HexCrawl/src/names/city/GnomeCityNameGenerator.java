package names.city;

import data.Indexible;
import names.IndexibleNameGenerator;

public class GnomeCityNameGenerator extends IndexibleNameGenerator{
	private static final String[] prefixes = {"Skip","Dyna","Draxle","Swelling","Rozzer","Numdick","Ten","Arp","Brol","Fram"};
	private static final String[] suffixes = {"helm","ham","point","guard","hollow","un"," Mapple"," Glegriol","menthool","das"};

	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(prefixes,obj)+getElementFromArray(suffixes,obj);
	}

}