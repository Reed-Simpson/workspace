package names.city;

import names.NameGenerator;

public class ElfCityNameGenerator extends NameGenerator{
	private static final String[] prefixes = {"Se","Rana","Uma","Lilal","Thean","Thyana","Osla","Myth","Syve","Olym"};
	private static final String[] suffixes = {"lean","groth","nora"," Caelora","hona"," Elunore","shys","valian","belle","hone"};
	


	@Override
	public String getName(int... val) {
		if(val.length<2) throw new IllegalArgumentException("Expected 2 or more values");
		return getElementFromArray(prefixes,val[0])+getElementFromArray(suffixes,val[1]);
	}

}