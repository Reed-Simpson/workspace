package names.city;

import names.NameGenerator;

public class HalflingCityNameGenerator extends NameGenerator{
	private static final String[] prefixes = {"Elder","Plum","Pine","Grass","Moon","Alol","Bume","Rar","Aln","Violet"};
	private static final String[] suffixes = {"bourne","dale","bell","reach","mere","ver","vol"," Savyld"," Vamneld","grove"};

	@Override
	public String getName(int... val) {
		if(val.length<2) throw new IllegalArgumentException("Expected 2 or more values");
		return getElementFromArray(prefixes,val[0])+getElementFromArray(suffixes,val[1]);
	}

}