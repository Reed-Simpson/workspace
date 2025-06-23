package names.npc;

import data.Indexible;
import data.population.Species;
import names.IndexibleNameGenerator;
import util.Util;

public class DwarfNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Adrik","Alberich","Baern","Barendd","Brottor","Bruenor","Dain","Darrak","Delg","Eberk","Einkil","Fargrim","Flint",
		"Gardain","Harbek","Kildrak","Morgran","Orsik","Oskar","Rangrim","Rurik","Taklinn","Thoradin","Thorin","Tordek","Traubon","Travok","Ulfgar","Veit","Vondal",
		"Amber","Artin","Audhild","Bardryn","Dagnal","Diesa","Eldeth","Falkrunn","Finellen","Gunnloda","Gurdis","Helja","Hlin","Kathra","Kristryd","Ilde","Liftrasa","Mardred","Riswynn","Sannl","Torbera","Torgga","Vistra"};
	private static final String[] LAST = {"Balderk","Battlehammer","Brawnanvil","Dankil","Fireforge","Frostbeard","Gorunn","Holderhek","Ironfist","Loderr","Lutgehr","Rumnaheim","Strakeln","Torunn","Ungart"};
	
	@Deprecated
	@Override
	public String getName(int... val) {
		if(val.length<5) throw new IllegalArgumentException("Expected 5 or more values");
		int[] remainder = Util.getRemainder(val, 1);
		if(val[0]%10==0) return Species.HUMAN.getNPCNameGen().getName(remainder);
		return getElementFromArray(FIRST,val[1])+" "+getElementFromArray(LAST,val[2]);
	}


	@Override
	public String getName(Indexible obj) {
		if(obj.reduceTempId(10)==0) return Species.HUMAN.getNPCNameGen().getName(obj);
		return getElementFromArray(FIRST,obj)+" "+getElementFromArray(LAST,obj);
	}

}
