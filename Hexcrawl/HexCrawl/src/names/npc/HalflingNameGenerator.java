package names.npc;

import data.Indexible;
import data.population.Species;
import names.IndexibleNameGenerator;
import util.Util;

public class HalflingNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Alton","Ander","Cade","Corrin","Eldon","Errich","Finnan","Garret","Lindal","Lyle","Merric","Milo","Osborn","Perrin","Reed","Roscoe","Wellby",
		"Andry","Bree","Callie","Cora","Euphemia","Jillian","Kithri","Lavinia","Lidda","Merla","Nedda","Paela","Portia","Seraphina","Shaena","Trym","Vani","Verna"};
	private static final String[] LAST = {"Brushgather","Goodbarrel","Greenbottle","High-hill","Hilltopple","Leagallow","Tealeaf","Thorngage","Tosscobble","Underbough"};
	
	@Deprecated
	@Override
	public String getName(int... val) {
		if(val.length<3) throw new IllegalArgumentException("Expected 3 or more values");
		int[] remainder = Util.getRemainder(val, 1);
		if(val[0]%10==0) return Species.HUMAN.getNPCNameGen().getName(remainder);
		else if(val[0]%10==1) return Species.ELF.getNPCNameGen().getName(remainder);
		return getElementFromArray(FIRST,val[1])+" "+getElementFromArray(LAST,val[2]);
	}


	@Override
	public String getName(Indexible obj) {
		int index = obj.reduceTempId(10);
		if(index==0) return Species.HUMAN.getNPCNameGen().getName(obj);
		else if(index==1) return Species.ELF.getNPCNameGen().getName(obj);
		return getElementFromArray(FIRST,obj)+" "+getElementFromArray(LAST,obj);
	}

}
