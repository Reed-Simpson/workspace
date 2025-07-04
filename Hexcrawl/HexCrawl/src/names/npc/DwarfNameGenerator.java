package names.npc;

import data.Indexible;
import data.population.NPCSpecies;
import names.IndexibleNameGenerator;

public class DwarfNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Adrik","Alberich","Baern","Barendd","Brottor","Bruenor","Dain","Darrak","Delg","Eberk","Einkil","Fargrim","Flint",
		"Gardain","Harbek","Kildrak","Morgran","Orsik","Oskar","Rangrim","Rurik","Taklinn","Thoradin","Thorin","Tordek","Traubon","Travok","Ulfgar","Veit","Vondal",
		"Amber","Artin","Audhild","Bardryn","Dagnal","Diesa","Eldeth","Falkrunn","Finellen","Gunnloda","Gurdis","Helja","Hlin","Kathra","Kristryd","Ilde","Liftrasa","Mardred","Riswynn","Sannl","Torbera","Torgga","Vistra"};
	private static final String[] LAST = {"Balderk","Battlehammer","Brawnanvil","Dankil","Fireforge","Frostbeard","Gorunn","Holderhek","Ironfist","Loderr","Lutgehr","Rumnaheim","Strakeln","Torunn","Ungart"};
	

	@Override
	public String getName(Indexible obj) {
		if(obj.reduceTempId(10)==0) return NPCSpecies.HUMAN.getNameGen().getName(obj);
		return getElementFromArray(FIRST,obj)+" "+getElementFromArray(LAST,obj);
	}

}
