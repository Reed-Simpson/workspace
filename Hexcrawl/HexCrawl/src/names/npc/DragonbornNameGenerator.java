package names.npc;

import data.Indexible;
import names.IndexibleNameGenerator;

public class DragonbornNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Arjan","Balasar","Bharash","Donaar","Ghesh","Heskan","Kriv","Medrash","Mehen","Nadarr","Pandjed","Patrin","Rhogar","Shamash","Shedinn","Tarhun","Torinn",
			"Akra","Biri","Daar","Farideh","Haran","Havilar","Jheri","Kava","Korinn","Mishann","Nala","Perra","Raiann","Sora","Surina","Thava","Uadjit"};
	private static final String[] LAST1 = {"Cleth","Daar","Del","Drache","Fenken","Ke","Kerr","Kim","Linxa","My","Nem","No","Ophin","Prex","Shes","Tur","Verth","Yar"};
	private static final String[] LAST2 = {"tin","den","mi","dan","ka","peshk","hy","ba","kasen","as","mon","ri","shtal","ijan","ten","nu","isath","jer"};
	private static final String[] LAST3 = {"thiallor","drian","rev","dion","bradon","molik","lon","tuul","dalor","tan","nis","xius","ajiir","dilin","deliath","roth","urgiesh","it"};
	


	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(FIRST,obj)+" "+getElementFromArray(LAST1,obj)+getElementFromArray(LAST2,obj)+getElementFromArray(LAST3,obj);
	}

}
