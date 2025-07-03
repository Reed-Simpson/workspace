package names.npc;

import data.Indexible;
import names.IndexibleNameGenerator;

public class ElfNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Arannis","Damaia","Darsis","Dweomer","Evabeth","Jhessail","Keyleth","Netheria","Orianna","Sorcyl","Umarion","Velissa"};
	private static final String[] LAST = {"Arvannis","Brawnanvil","Daardendrian","Drachedandion","Endryss","Meliamne","Mishann","Silverfrond","Snowmantle","Summerbreeze","Thunderfoot","Zashir"};
	


	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(FIRST,obj)+" "+getElementFromArray(LAST,obj);
	}

}
