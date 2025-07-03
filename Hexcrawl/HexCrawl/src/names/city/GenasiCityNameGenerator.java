package names.city;

import data.Indexible;
import names.IndexibleNameGenerator;

public class GenasiCityNameGenerator extends IndexibleNameGenerator{
	private static final String[] prefixes = {"Steam","Ice","Magma","Smoke","Light","Crystal","Steel","Thunder","Mud","Clay"};
	private static final String[] suffixes = {"fire","water","earth","air","heat","mist","dust","wind"};

	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(prefixes,obj)+getElementFromArray(suffixes,obj);
	}

}