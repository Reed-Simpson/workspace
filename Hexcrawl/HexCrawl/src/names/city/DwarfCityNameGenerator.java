package names.city;

import data.Indexible;
import names.IndexibleNameGenerator;

public class DwarfCityNameGenerator extends IndexibleNameGenerator {
	private static final String[] prefixes = {"Deep","Strong","Shallow","Black","Dark","Grey","Stone","Sturdy","Sandy","Mud","Gemstone",
											"Keghto","Thulwohr","Bhegul","Kerboramm","Helfal","Digfur","Korn Dar","Thun Bad","Mughuluhm","Dhirgrun"};
	private static final String[] suffixes = {"rock"," Mountain"," Hill"," Chasm","bur","dor","rim","aral","dim"," Mine"};

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
