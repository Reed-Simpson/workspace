package names.city;

import names.NameGenerator;

public class OrcCityNameGenerator extends NameGenerator{
	private static final String[] prefixes = {"Mar","Bhaz","Ez","Brul","U","E","Grod","Krog","Ghadokh","Kimmug"};
	private static final String[] suffixes = {" Qadzud"," Vroghakh","rugh","ben","dord","diz","erde","mugviz"," Udh"," Lor"};
	

	@Override
	public String getName(int... val) {
		if(val.length<2) throw new IllegalArgumentException("Expected 2 or more values");
		return getElementFromArray(prefixes,val[0])+getElementFromArray(suffixes,val[1]);
	}

}