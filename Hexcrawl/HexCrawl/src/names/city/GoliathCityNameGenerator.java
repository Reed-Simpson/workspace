package names.city;

import data.Indexible;
import names.IndexibleNameGenerator;

public class GoliathCityNameGenerator extends IndexibleNameGenerator{
	private static final String[] prefixes = {"Akann","Thuunla","Anakal","Kalag","Nugale","Agu","Uthenu","Elani","Vuma","Kolae"};
	private static final String[] suffixes = {"athi","kalaga","amino","iago","kali","-Vigane","-Keaku","thino","-Thiaga","-Guthea"};

	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(prefixes,obj)+getElementFromArray(suffixes,obj);
	}

}