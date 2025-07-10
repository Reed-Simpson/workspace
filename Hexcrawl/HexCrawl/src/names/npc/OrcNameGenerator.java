package names.npc;

import data.Indexible;
import names.IndexibleNameGenerator;

public class OrcNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Abzug","Bajok","Bharash","Grovis","Gruuna","Hokrun","Mardred","Rhogar","Skuldark","Thokk","Urzul","Varka",
			"Dench","Feng","Gell","Henk","Holg","Imsh","Keth","Krusk","Mhurren","Ront","Shump","Thokk",
			"Baggi","Emen","Engong","Kansif","Myev","Neega","Ovak","Ownka","Shautha","Sutha","Vola","Volen","Yevelda",
			"Ayesha","Uthra","Namesh","Kell","Deindra","Kalla","Meninta","Ell","Baghi","Cayillka","Fenog","Inenn","Wethrya","Kaya","Corwynth","Feresk","Jaegesh","Shakar","Veltar","Hasksha",
			"Kor","Nudin","Ruben","Throkk","Yorsh","Orwen","Andyesh","Bikar","Gaster","Corwek","Grammesh","Uthor","Norwick","Velsork","Zandthar","Blud","Operoh","Jask","Wovek","Viskoth",
			
			"Rattle","Scratch","Wallow","Groak","Gimble","Scar","Cratch","Creech","Shush","Glush","Slar","Gnash","Stoad","Grig","Bleat","Chortle","Cluck","Slith","Mongo","Creak","Burble",
			"Vrusk","Snuffle","Leech","Herk"};
	private static final String[] LAST = {"Burska","Gruuthok","Hrondl","Jarzzok","Kraltus","Shamog","Skrangval","Ungart","Uuthrakt","Vrakir","Yuldra","Zulrax",
			"Bigaxe","Bloodbane","Blackbeast","Bogfang","Bonecrusher","Brokunblade","Darkcleaver","Doomhammer","Dreadboar","Firebringer","Frostskull",
			"Gorehide","Greenstalker","Grimklaw","Helltaker","Ironjaw","Killgor","Nighthowl","Skullhorn","Smashrock","Soulslayer","Steeltooth","Stonesplitter",
			"Stormrage","Strongarm","Swifthound","Terrordrinker","Thunderfoot","Warchanter","Wildsoul","Wormbreath","Wratheater",
			"Orsla","Craken","Yunt","Nyanda","Rhell","Inkaya","Ansk","Thrumush","Bloodknell","Nurn","Ibbleh","Urk","Ayaski","Cayaska","Nyrt","Hagthar","Skirmish","Diender","Breakinak","Vorst",
			"Otin","Thoryik","Allinkt","Greumm","Kayank","Nummush","Kilk","Nastyem","Hervik","Calaka","Urtuk","Nekab","Vestrok","Isterr","Bellos","Goreson","Negmesh","Illukta","Caryon","Tehnroth"};
	

	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(FIRST,obj)+" "+getElementFromArray(LAST,obj);
	}

}
