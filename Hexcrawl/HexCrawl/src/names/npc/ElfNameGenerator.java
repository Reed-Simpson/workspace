package names.npc;

import data.Indexible;
import names.IndexibleNameGenerator;

public class ElfNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Arannis","Damaia","Darsis","Dweomer","Evabeth","Jhessail","Keyleth","Netheria","Orianna","Sorcyl","Umarion","Velissa","Arsula",
			"Naidita","Belesunna","Vidarna","Ninsunu","Balathu","Dorosi","Gezera","Zursan","Seleeku","Utamara","Nebakay","Dismashk","Mitunu","Atani","Kinzura","Sumula","Ukames","Ahmeshki",
			"Ilsit","Mayatanay","Etana","Gamanna","Nessana","Uralar","Tishetu","Leucia","Sutahe","Dotani","Uktannu","Retenay","Kendalanu","Tahuta","Mattissa","Anatu","Aralu","Arakhi","Ibrahem",
			"Sinosu","Jemshida","Visapni","Hullata","Sidura","Kerihu","Ereshki","Cybela","Anunna","Otani","Ditani","Faraza",
			"Selanar","Mhaenal","Kuskyn","Garynnon","Naertho","Sihnion","Kolvar","Aymon","Pywaln","Tasar","Kendel","Ruehnar","Tannyll","Hubys","Dalyor","Dain","Wyninn","Itham","Garrik","Myrin",
			"Taeral","Riluaneth","Elen","Caeda","Jandar","Ehlark","Virion","Laeroth","Elidyr","Halueth","Irhaal","Gweyir","Eletha","Ygannea","Syvis","Fhaertala","Ciliren","Annallee","Amisra",
			"Mariona","Aimon","Goren","Nesterin","Vulre","Lamruil","Tanithil","Tannatar","Aymer","Ailmon","Aywin","Alanis","Halueth","Paeral","Inchel","Goren","Alasse","Halamar","Taeral",
			"Chaenath","Folen","Thalia","Axilya","Anarzee","Lethhonel","Darshee","Alvaerelle","Kaylin","Ariawyn","Alenia","Kylantha"};
	private static final String[] LAST = {"Arvannis","Brawnanvil","Daardendrian","Drachedandion","Endryss","Meliamne","Mishann","Silverfrond","Snowmantle","Summerbreeze","Thunderfoot","Zashir",
			"Yeslamin","Ralororis","Qindan","Valyarus","Thephyra","Qindithas","Beizumin","Theqirelle","Fakian","Erlynn","Pageiros","Omanelis","Yllarel","Crarel","Ianthyra","Torydark","Eilroris",
			"Olakrana","Caiquinal","Ianhana","Darieth","Trarieth","Oloris","Inaxidor","Quixina","Addithas","Sarrel","Yesgwyn","Phiro","Reysatra","Balsys","Valfaren","Herkas","Holazana",
			"Crayarus","Shakrana","Mirakalyn","Aena","Iarxina","Keyjyre","Mirabalar","Chaegella","Gilcaryn","Gensys","Naewarin","Carjyre","Crasatra","Grevalur","Quihorn","Morynore","Rozana",
			"Dorna","Dadithas","Fengella","Beimoira","Helezumin","Yinlen","Ralolamin","Baltris","Elgwyn","Valjeon","Virsalor","Hergella","Pergeiros","Iarsatra","Oriqirelle","Eildove","Bixidor",
			"Balrieth","Fencaryn"};
	


	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(FIRST,obj)+" "+getElementFromArray(LAST,obj);
	}

}
