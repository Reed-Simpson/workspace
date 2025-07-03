package names.city;

import data.Indexible;
import names.IndexibleNameGenerator;

public class DwarfCityNameGenerator extends IndexibleNameGenerator {
	private static final String[] prefixes = {"Deep","Strong","Shallow","Black","Dark","Grey","Stone","Sturdy","Sandy","Mud","Gemstone",
											"Keghto","Thulwohr","Bhegul","Kerboramm","Helfal","Digfur","Korn Dar","Thun Bad","Mughuluhm","Dhirgrun"};
	private static final String[] suffixes = {"rock"," Mountain"," Hill"," Chasm","bur","dor","rim","aral","dim"," Mine"};


	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(prefixes,obj)+getElementFromArray(suffixes,obj);
	}

}
