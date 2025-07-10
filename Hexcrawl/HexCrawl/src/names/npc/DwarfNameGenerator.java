package names.npc;

import data.Indexible;
import data.population.NPCSpecies;
import names.IndexibleNameGenerator;

public class DwarfNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Adrik","Alberich","Baern","Barendd","Brottor","Bruenor","Dain","Darrak","Delg","Eberk","Einkil","Fargrim","Flint","Gardain","Harbek","Kildrak",
			"Morgran","Orsik","Oskar","Rangrim","Rurik","Taklinn","Thoradin","Thorin","Tordek","Traubon","Travok","Ulfgar","Veit","Vondal","Amber","Artin","Audhild","Bardryn","Dagnal","Diesa",
			"Eldeth","Falkrunn","Finellen","Gunnloda","Gurdis","Helja","Hlin","Kathra","Kristryd","Ilde","Liftrasa","Mardred","Riswynn","Sannl","Torbera","Torgga","Vistra",
			"Thimun","Nodgrir","Orivrok","Kikhean","Berherlun","Thikhatum","Barizmeam","Whuver","Vaveag","Nonoc","Gindaetalyn","Lolgotryd","Nargrethra","Alfotgraethra","Thudgrulydd","Ragraekara",
			"Thrassistr","Orizobo","Nozzounelynn","Durithearen","Yurfoli","Skarrulir","Kikkunri","Nadmoir","Lugnac","Jolor","Yazzol","Dalorfumi","Grurgraed","Dwobum","Yovreadrid","Strograenelyn",
			"Hemmeana","Josdruline","Hardreginn","Finwagret","Fokinelynn","Luthoni","Whufarika","Glatmaelydd","Bunerlig","Yodmol","Barivrolin","Dovurim","Homnul","Grouneac","Doubruik","Tholgor",
			"Bralgulim","Skastrurum","Reifraelda","Vorhoukara","Nurasgrewynn","Koggaenelyn","Darergehulda","Umitdrobella","Nadgretalin","Brafrunelynn","Erivrebena","Erimalydd"};
	private static final String[] LAST = {"Balderk","Battlehammer","Brawnanvil","Dankil","Fireforge","Frostbeard","Gorunn","Holderhek","Ironfist","Loderr","Lutgehr","Rumnaheim","Strakeln",
			"Torunn","Ungart","Earthheart","Browngranite","Caskborn","Flintdelver","Barrelsword","Beasthead","Noblefoot","Lavaheart","Beastcloak","Mudjaw","Battlebreaker","Woldtoe","Boneback",
			"Blazingriver","Giantgranite","Aleview","Cragbrew","Platebelly","Ingotbraids","Emberminer","Runefeet","Silverstone","Graybelly","Bristlehelm","Flintbow","Drakehorn","Stormshield",
			"Hillflayer","Blackmaul","Chainshield","Trollchest","Lightbrow","Bristledigger","Lavathane","Caveshaper","Platebuckle","Orcview","Goldarmour","Blessedbelt","Orcjaw","Largehand",
			"Pebblemace","Bitterchest","Leadcloak","Jadeforge","Coinflayer","Largearmour","Alefinger","Grayview","Trollback","Mithrilmaster","Hornbasher","Graybuster","Drakehelm","Thundergrip",
			"Rubydelver","Magmamaster","Woldbow","Bloodbow","Heavybrew"};
	

	@Override
	public String getName(Indexible obj) {
		if(obj.reduceTempId(10)==0) return NPCSpecies.HUMAN.getNameGen().getName(obj);
		return getElementFromArray(FIRST,obj)+" "+getElementFromArray(LAST,obj);
	}

}
