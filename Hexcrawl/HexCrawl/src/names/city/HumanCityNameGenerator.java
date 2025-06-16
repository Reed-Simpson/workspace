package names.city;

import names.NameGenerator;

public class HumanCityNameGenerator extends NameGenerator{
	private static final String[] prefixes = {"River","Stone","Hill","Green","Dark","Sun","Steel","God","Mist","Book"};
	private static final String[] suffixes = {"bridge","port","burgh","well","wood","field","dale","haven"," City","ford"};
	

	@Override
	public String getName(int... val) {
		if(val.length<2) throw new IllegalArgumentException("Expected 2 or more values");
		return getElementFromArray(prefixes,val[0])+getElementFromArray(suffixes,val[1]);
	}

}
