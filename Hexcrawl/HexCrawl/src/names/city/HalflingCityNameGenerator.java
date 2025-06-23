package names.city;

import data.Indexible;
import names.IndexibleNameGenerator;

public class HalflingCityNameGenerator extends IndexibleNameGenerator{
	private static final String[] prefixes = {"Elder","Plum","Pine","Grass","Moon","Alol","Bume","Rar","Aln","Violet"};
	private static final String[] suffixes = {"bourne","dale","bell","reach","mere","ver","vol"," Savyld"," Vamneld","grove"};

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