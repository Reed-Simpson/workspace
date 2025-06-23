package names.city;

import data.Indexible;
import names.IndexibleNameGenerator;

public class GenasiCityNameGenerator extends IndexibleNameGenerator{
	private static final String[] prefixes = {"Steam","Ice","Magma","Smoke","Light","Crystal","Steel","Thunder","Mud","Clay"};
	private static final String[] suffixes = {"fire","water","earth","air","heat","mist","dust","wind"};

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