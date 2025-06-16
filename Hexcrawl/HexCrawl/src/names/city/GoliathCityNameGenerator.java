package names.city;

import names.NameGenerator;

public class GoliathCityNameGenerator extends NameGenerator{
	private static final String[] prefixes = {"Akann","Thuunla","Anakal","Kalag","Nugale","Agu","Uthenu","Elani","Vuma","Kolae"};
	private static final String[] suffixes = {"athi","kalaga","amino","iago","kali","-Vigane","-Keaku","thino","-Thiaga","-Guthea"};

	@Override
	public String getName(int... val) {
		if(val.length<2) throw new IllegalArgumentException("Expected 2 or more values");
		return getElementFromArray(prefixes,val[0])+getElementFromArray(suffixes,val[1]);
	}

}