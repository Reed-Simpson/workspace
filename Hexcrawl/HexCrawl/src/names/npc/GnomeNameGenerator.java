package names.npc;

import data.Indexible;
import data.population.NPCSpecies;
import names.IndexibleNameGenerator;

public class GnomeNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Cricket","Daisy","Dimble","Ellywick","Erky","Fiddlestyx","Fonkin","Golly","Mimsy","Pumpkin","Quarrel","Sybilwick",
			"Orukur","Nijin","Valji","Lanmin","Erdri","Brijin","Umpos","Mermop","Ipavyn","Farryn","Xyroniana","Fenssa","Venlin","Welza","Krihana","Phitra","Nybi","Vokini","Xyrokasys","Qina",
			"Valpip","Tover","Orhik","Quotor","Hismorn","Raslen","Merdon","Valzu","Nesyur","Eniwin","Uriqys","Celsys","Arihani","Quesany","Yoqaryn","Mintina","Foltina","Yorhana","Xyromila",
			"Yowyse","Umzu","Davnan","Xalni","Felhik","Hormorn","Quahim","Wrekur","Tankur","Lodri","Farcryn","Yosys","Aluzyre","Ellys","Isofi","Lilzyre","Wrorhana","Qissa","Nymiphi","Heskini",
			"Ylodysa","luckygem","dazzleflight","wobblepeak","silverboot","deepbranch","zesgitan","noosades","aruteser","helbasin"};
	private static final String[] LAST = {"Borogrove","Goldjoy","Hoddypeak","Huddle","Jollywind","Oneshoe","Scramblewise","Sunnyhill","Tallgrass","Timbers","Underbough","Wimbly",
			"temmlebisind","loudspark","sparklespark","thunderlob","deeptwist","puddlecloak","esgroreters","tapleliple","happlelipple","saplatidomp","wemlorotos","fiddlebrand","flickerwander",
			"tosslegem","stoutbranch","darkboots","bimplezimple","homplefample","bipleseple","tibbleribble","debleloble","bellowdwadle","fapplefront","flickermantle","tosslegrace",
			"silverfront","mompederas","eebblesen","boddlefeddle","debblerebble","tilwaseben","bronzebell","glitterpocket","springgem","stoutreach","twistbadge","pinbatis","luparas",
			"zasgren","bakko","gonggubatill","kindspark","tinkerspan","laststamp","lastcrag","strongback","bomplezemple","roddlemaddle","tenbiseter","irkasend","eppaberer"};
	


	@Override
	public String getName(Indexible obj) {
		int index = obj.reduceTempId(8);
		if(index==0) return NPCSpecies.HUMAN.getNameGen().getName(obj);
		else if(index==1) return NPCSpecies.DWARF.getNameGen().getName(obj);
		return getElementFromArray(FIRST,index)+" "+getElementFromArray(LAST,index);
	}

}
