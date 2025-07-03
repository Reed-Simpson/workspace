package names.city;

import data.Indexible;
import names.IndexibleNameGenerator;

public class HumanCityNameGenerator extends IndexibleNameGenerator{
	private static final String[] prefixes = {"River","Stone","Hill","Green","Dark","Sun","Steel","God","Mist","Book"};
	private static final String[] suffixes = {"bridge","port","burgh","well","wood","field","dale","haven"," City","ford"};
	


	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(prefixes,obj)+getElementFromArray(suffixes,obj);
	}

}
