package names.npc;

import data.Indexible;
import names.IndexibleNameGenerator;

public class GoliathNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Abzug","Bajok","Bharash","Grovis","Gruuna","Hokrun","Mardred","Rhogar","Skuldark","Thokk","Urzul","Varka",
			"Dench","Feng","Gell","Henk","Holg","Imsh","Keth","Krusk","Mhurren","Ront","Shump","Thokk",
			"Baggi","Emen","Engong","Kansif","Myev","Neega","Ovak","Ownka","Shautha","Sutha","Vola","Volen","Yevelda",
			"Ayesha","Uthra","Namesh","Kell","Deindra","Kalla","Meninta","Ell","Baghi","Cayillka","Fenog","Inenn","Wethrya","Kaya","Corwynth","Feresk","Jaegesh","Shakar","Veltar","Hasksha",
			"Kor","Nudin","Ruben","Throkk","Yorsh","Orwen","Andyesh","Bikar","Gaster","Corwek","Grammesh","Uthor","Norwick","Velsork","Zandthar","Blud","Operoh","Jask","Wovek","Viskoth",
			"Iraghan","Arzak","Keoglath","Lazariak","Ergdak","Ergglath","Pumak","Khuthak","Lagath","Korathag",
			"Vaathe","Guagu","Thama","Gemeo","Mennio","Kiggeo","Maulai","Diagia","Ilakia"};
	private static final String[] TITLE_PREFIX = {"Rock","Wild","Bright","River","Fearless","Steady","Flint","Lone","Mountain",
			"Flower","Sly","Hard","Master","Rain","Hide","Fear","Night","Truth","Root","Deer","Low"};
	private static final String[] TITLE_SUFFIX = {"finder","hunter","helper","mender","storm","picker","eye","killer","warrior",
			"vigor","chief","stalker","weaver","smasher","maker","guard","watcher","feller","carver","leaper","hand","walker","herder"};
	

	@Override
	public String getName(int... val) {
		Indexible obj = new Indexible(val);
		return getName(obj);
	}
	
	public String getTitle(Indexible obj) {
		return getElementFromArray(TITLE_PREFIX,obj)+getElementFromArray(TITLE_SUFFIX,obj);
	}


	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(FIRST,obj)+" "+getTitle(obj)+" ${town index}";
	}

}
