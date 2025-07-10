package names.npc;

import data.Indexible;
import data.threat.subtype.DragonType;
import names.IndexibleNameGenerator;
import util.Util;

public class DragonbornNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Arjan","Balasar","Bharash","Donaar","Ghesh","Heskan","Kriv","Medrash","Mehen","Nadarr","Pandjed","Patrin","Rhogar","Shamash","Shedinn","Tarhun","Torinn",
			"Akra","Biri","Daar","Farideh","Haran","Havilar","Jheri","Kava","Korinn","Mishann","Nala","Perra","Raiann","Sora","Surina","Thava","Uadjit",
			"Drakonis","Zygtor","Tzharak","Fyrakthos","Zarek","Tharvok","Krithun","Jorathis","Volthar","Tazgarn","Vyraxis","Zythrax","Drakmirah","Tzylaya","Jirvanna","Elzyndra","Vashara",
			"Xhalara","Hexylthra","Zephyri","Brethyla","Tazmira","Vensari","Zyrissa","Xyroth","Niveth","Xylor","Vistrana","Yxilan","Nyvrax","Jivara","Cryssyl","Qilshar","Nyxath","Pyrgoth","Thryzik",
			
			"Quis'thal","Kharidax","Krelith","Kael'thun","Brethyn","Kynaz","Quorath","Vythessa","Thraskor","Qorzhul","Varquen","Daerith","Drakthar","Zorunth","Thronar",
			"Sylgrynth","Vexthir","Draxell","Pyrillian","Luminestra","Klendash"
	};
	private static final String[] PART1 = {"Cleth","Daar","Del","Drache","Fenken","Ke","Kerr","Kim","Linxa","My","Nem","No","Ophin","Prex","Shes","Tur","Verth","Yar"};
	private static final String[] PART2 = {"tin","den","mi","dan","ka","peshk","hy","ba","kasen","as","mon","ri","shtal","ijan","ten","nu","isath","jer"};
	private static final String[] PART3 = {"thiallor","drian","rev","dion","bradon","molik","lon","tuul","dalor","tan","nis","xius","ajiir","dilin","deliath","roth","urgiesh","it"};
	private static final String[] DRAGONNOUNS = {"flame","eater","venom","bolt","frost"};
	private static final String[] DRAGONADJECTIVES = {"ember","acid","toxic","shock","cold"};
	private static final String[] PARTS = {"Horn","Skull","Eye","Snout","Tongue","Fang","Tooth","Spine","Scale","Wing","Heart","Blood","Claw","Tail"};
	private static final String[] WORDS = {"${adjective}${part}","${adjective}${weapon}","${dragon type}${part}","${dragon type}${apparel}","${personality}${noun}","${character element}${noun}"};
	


	@Override
	public String getName(Indexible obj) {
		String last = getLastName(obj);
		return getElementFromArray(FIRST,obj)+" "+last;
	}



	private String getLastName(Indexible obj) {
		if(obj.reduceTempId(2)==0) {
			return getElementFromArray(PART1,obj)+getElementFromArray(PART2,obj)+getElementFromArray(PART3,obj);
		}else {
			return format(getElementFromArray(WORDS,obj),obj);
		}
	}
	
	private String format(String name,Indexible obj) {
		if(name.contains("${dragon type}")) name = Util.replace(name,"${dragon type}",DragonType.getByWeight(obj).toString());
		if(name.contains("${noun}")) name = Util.replace(name,"${noun}",getElementFromArray(DRAGONNOUNS,obj));
		if(name.contains("${adjective}")) name = Util.replace(name,"${adjective}",getElementFromArray(DRAGONADJECTIVES,obj));
		if(name.contains("${part}")) name = Util.replace(name,"${part}",getElementFromArray(PARTS,obj));
		return Util.toCamelCase(Util.formatTableResult(name, obj));
	}

}
