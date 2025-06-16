package names.city;

import names.NameGenerator;

public class DragonbornCityNameGenerator extends NameGenerator{
	private static final String[] prefixes = {"Arangh","Arush","Rel","Rok","Ruin","Djerad","Tymanche","Har","Harone","Xorvin"};
	private static final String[] suffixes = {"thal"," Ashuak"," Vayem","maur","thorl","spoke"," Kethendi"," Kusold"," Thymar","bar","ther","glast"," Thar","troth"};
	

	@Override
	public String getName(int... val) {
		if(val.length<2) throw new IllegalArgumentException("Expected 2 or more values");
		return getElementFromArray(prefixes,val[0])+getElementFromArray(suffixes,val[1]);
	}

}