package names.npc;

import data.Indexible;
import data.population.NPCSpecies;
import names.IndexibleNameGenerator;

public class HalflingNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Alton","Ander","Cade","Corrin","Eldon","Errich","Finnan","Garret","Lindal","Lyle","Merric","Milo","Osborn","Perrin","Reed","Roscoe","Wellby",
			"Andry","Bree","Callie","Cora","Euphemia","Jillian","Kithri","Lavinia","Lidda","Merla","Nedda","Paela","Portia","Seraphina","Shaena","Trym","Vani","Verna","Erdak","Terin",
			"Garner","Anyas","Iraton","Sharwan","Xozor","Mernan","Jory","Erdal","Corster","Nornan","Kaspher","Oritran","Osorin","Corfire","Golan","Danvon","Anrich","Quoras","Arivira",
			"Shaecaryn","Grarana","Unani","Fayzana","Elisira","Isaola","Breara","Nene","Kelnys","Oralienne","Kithwyn","Isaeni","Ariara","Zenora","Quda","Salyse","Malgrace","Breri","Yoora",
			"Ricser","Davbul","Xodak","Ricser","Orifer","Eros","Sharkin","Belver","Condon","Oszu","Kelzana","Sajen","Uviora","Thamzana","Idakath","Chenfice","Qilyse","Zenna","Diala","Elina",
			"Quonan","Golan","Ricser","Corpos","Dankin","Neamin","Yenzin","Perster","Corgin","Uririn","Isafice","Therola","Yesne","Unani","Eilile","Jayris","Mardrey","Chenyola","Idalienne","Diasica"
		};
	private static final String[] LAST = {"Brushgather","Goodbarrel","Greenbottle","High-hill","Hilltopple","Leagallow","Tealeaf","Thorngage","Tosscobble","Underbough","summerbrace",
			"nightberry","flintglide","prouddancer","fatsky","lighttop","stoutheart","silentmane","rumblesurge","mildkettle","nimblerabbit","longwind","rumbleleaf","autumndew","havenbrush",
			"brightseeker","stillshine","marblecrest","deephollow","glowbottle","hoghand","truespell","twilightwood","brighttopple","deepcheeks","longhill","strongvale","barleybridge",
			"cloudlade","bronzeace","reedgrove","applemoon","moonfound","hilldancer","bronzeearth","earthrabbit","stilldream","tossbeam","warmbelly","fatbluff","highstride","stillcrest",
			"tallfound","greatsun","rumblecheeks","freeseeker","brushbridge","mossfoot","teaspark","mildflower","keengather","wilddancer","grandrabbit","longmoon","marbleshadow","brambleflow",
			"mistbridge","leaman","rosebluff","earthhill","fatblossom","shadowhands","dustbloom","thistledancer","marblegather","teahand","silenthill","twilightwater","lightshadow","thornmane",
			"rumbleeyes","strongbloom","greenbrush","stillbarrel","freehollow","leafdream","laughinghands","mistshine","lunarsun","hogfoot"};
	

	@Override
	public String getName(Indexible obj) {
		int index = obj.reduceTempId(8);
		if(index==0) return NPCSpecies.HUMAN.getNameGen().getName(obj);
		else if(index==1) return NPCSpecies.ELF.getNameGen().getName(obj);
		return getElementFromArray(FIRST,obj)+" "+getElementFromArray(LAST,obj);
	}

}
