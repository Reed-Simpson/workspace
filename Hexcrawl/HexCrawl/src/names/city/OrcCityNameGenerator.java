package names.city;

import data.Indexible;
import names.IndexibleNameGenerator;

public class OrcCityNameGenerator extends IndexibleNameGenerator{
	private static final String[] prefixes = {"Mar","Bhaz","Ez","Brul","U","E","Grod","Krog","Ghadokh","Kimmug"};
	private static final String[] suffixes = {" Qadzud"," Vroghakh","rugh","ben","dord","diz","erde","mugviz"," Udh"," Lor"};
	


	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(prefixes,obj)+getElementFromArray(suffixes,obj);
	}

}