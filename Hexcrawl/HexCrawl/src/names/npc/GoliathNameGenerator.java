package names.npc;

import data.Indexible;
import names.IndexibleNameGenerator;

public class GoliathNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Chony","Banda","Jochu","Kira","Khatir","Chaidu","Atan","Buandu","Javyn","Khashin","Bayara","Temura","Kidha","Kathos","Tanua","Bashtu","Jaran",
			"Othos","Khutan","Otaan","Martu","Baku","Tuban","Qudan","Denua","Iraghan","Arzak","Keoglath","Lazariak","Ergdak","Ergglath","Pumak","Khuthak","Lagath","Korathag",
			"Vaathe","Guagu","Thama","Gemeo","Mennio","Kiggeo","Maulai","Diagia","Ilakia","Laupath","Augrad","Keonihl","Authok","Vegaghan","Zanihl","Meamahl","Vaghan","Kavadak",
			"Padhan","Zaulea","Ilarrea","Geghu","Thekko","Lathe","Kanea","Naune","Thauri","Karia","Lonnio","Neorein","Agmahk","Vegamahl","Geanath","Vapath","Kazarhak","Naraghan",
			"Ghavek","Taraphak","Maranak","Mevi","Nalthea","Thugu","Vekea","Vorea","Inala","Norheo","Lathio","Lenea","Vethia"};
	private static final String[] TITLE_PREFIX = {"Rock","Wild","Bright","River","Fearless","Steady","Flint","Lone","Mountain","Flower","Sly","Hard","Master","Rain","Hide","Fear","Night",
			"Truth","Root","Deer","Low","Lone","Tribe","Thunder","Long","Silent","Boulder","Horn","Wise","Mind","High","Tree","Day","Strong","Sky","Wound","Swift"};
	private static final String[] TITLE_SUFFIX = {"finder","hunter","helper","mender","storm","picker","eye","killer","warrior","vigor","chief","stalker","weaver","smasher",
			"maker","guard","watcher","feller","carver","leaper","hand","walker","herder","dream","chaser","worker","friend","caller","tanner","twister","stitcher"};

	
	public String getTitle(Indexible obj) {
		return getElementFromArray(TITLE_PREFIX,obj)+getElementFromArray(TITLE_SUFFIX,obj);
	}


	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(FIRST,obj)+" "+getTitle(obj)+" ${town index}";
	}

}
