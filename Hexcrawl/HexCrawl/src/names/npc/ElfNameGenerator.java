package names.npc;

import general.Indexible;
import names.IndexibleNameGenerator;

public class ElfNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Arannis","Damaia","Darsis","Dweomer","Evabeth","Jhessail","Keyleth","Netheria","Orianna","Sorcyl","Umarion","Velissa"};
	private static final String[] LAST = {"Arvannis","Brawnanvil","Daardendrian","Drachedandion","Endryss","Meliamne","Mishann","Silverfrond","Snowmantle","Summerbreeze","Thunderfoot","Zashir"};
	

	@Override
	public String getName(int... val) {
		if(val.length<2) throw new IllegalArgumentException("Expected 2 or more values");
		return getElementFromArray(FIRST,val[0])+" "+getElementFromArray(LAST,val[1]);
	}


	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(FIRST,obj)+" "+getElementFromArray(LAST,obj);
	}

}
