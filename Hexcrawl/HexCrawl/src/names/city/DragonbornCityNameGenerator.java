package names.city;

import data.Indexible;
import names.IndexibleNameGenerator;

public class DragonbornCityNameGenerator extends IndexibleNameGenerator{
	private static final String[] prefixes = {"Arangh","Arush","Rel","Rok","Ruin","Djerad","Tymanche","Har","Harone","Xorvin"};
	private static final String[] suffixes = {"thal"," Ashuak"," Vayem","maur","thorl","spoke"," Kethendi"," Kusold"," Thymar","bar","ther","glast"," Thar","troth"};
	

	@Override
	public String getName(Indexible val) {
		return getElementFromArray(prefixes,val)+getElementFromArray(suffixes,val);
	}

}