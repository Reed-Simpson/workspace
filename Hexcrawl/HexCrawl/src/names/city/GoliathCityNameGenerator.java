package names.city;

import data.Indexible;
import names.IndexibleNameGenerator;

public class GoliathCityNameGenerator extends IndexibleNameGenerator{
	private static final String[] prefixes = {"Akann","Thuunla","Anakal","Kalag","Nugale","Agu","Uthenu","Elani","Vuma","Kolae","Nola","Kolak","Thunu","Maluk","Kalag","Kaluk","Gatha","Vuma","Muna",
			"Katho","Eguma","Geana","Veom","Vathun","Egena","Ganu","Ogo","Valu","Apuna","Nala","Kulan"};
	private static final String[] suffixes = {"athi","kalaga","amino","iago","kali","-Vigane","-Keaku","thino","-Thiaga","-Guthea","eaku","-Kutha","elo","kuthea","ugate","iano","atake","-Kiaga",
			"avone","kathala","-Thigone","kamune","-Kileana","athala","-Giano","-Ulavi","olake","liago","-Olavea","kanu","geane","-Uliala","igane","lavea","igo","kigane","avi","-Oligone","-Vutha",
			"-Mavone","lupine","kelo","-Nulane","-Mathala","kugoni"};


	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(prefixes,obj)+getElementFromArray(suffixes,obj);
	}

}