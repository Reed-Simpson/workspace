package names.city;

import data.Indexible;
import names.IndexibleNameGenerator;

public class ElfCityNameGenerator extends IndexibleNameGenerator{
	private static final String[] prefixes = {"Se","Rana","Uma","Lilal","Thean","Thyana","Osla","Myth","Syve","Olym"};
	private static final String[] suffixes = {"lean","groth","nora"," Caelora","hona"," Elunore","shys","valian","belle","hone"};
	



	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(prefixes,obj)+getElementFromArray(suffixes,obj);
	}

}