package names.npc;

import data.Indexible;
import data.population.NPCSpecies;
import names.IndexibleNameGenerator;

public class HalflingNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Alton","Ander","Cade","Corrin","Eldon","Errich","Finnan","Garret","Lindal","Lyle","Merric","Milo","Osborn","Perrin","Reed","Roscoe","Wellby",
		"Andry","Bree","Callie","Cora","Euphemia","Jillian","Kithri","Lavinia","Lidda","Merla","Nedda","Paela","Portia","Seraphina","Shaena","Trym","Vani","Verna"};
	private static final String[] LAST = {"Brushgather","Goodbarrel","Greenbottle","High-hill","Hilltopple","Leagallow","Tealeaf","Thorngage","Tosscobble","Underbough"};
	

	@Override
	public String getName(Indexible obj) {
		int index = obj.reduceTempId(10);
		if(index==0) return NPCSpecies.HUMAN.getNameGen().getName(obj);
		else if(index==1) return NPCSpecies.ELF.getNameGen().getName(obj);
		return getElementFromArray(FIRST,obj)+" "+getElementFromArray(LAST,obj);
	}

}
