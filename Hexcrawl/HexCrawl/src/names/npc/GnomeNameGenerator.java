package names.npc;

import data.Indexible;
import data.population.Species;
import names.IndexibleNameGenerator;
import util.Util;

public class GnomeNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Cricket","Daisy","Dimble","Ellywick","Erky","Fiddlestyx","Fonkin","Golly","Mimsy","Pumpkin","Quarrel","Sybilwick"};
	private static final String[] LAST = {"Borogrove","Goldjoy","Hoddypeak","Huddle","Jollywind","Oneshoe","Scramblewise","Sunnyhill","Tallgrass","Timbers","Underbough","Wimbly"};
	

	@Override
	public String getName(int... val) {
		if(val.length<3) throw new IllegalArgumentException("Expected 3 or more values");
		int[] remainder = Util.getRemainder(val, 1);
		if(val[0]%10==0) return Species.HUMAN.getNPCNameGen().getName(remainder);
		else if(val[0]%10==1) return Species.DWARF.getNPCNameGen().getName(remainder);
		return getElementFromArray(FIRST,val[1])+" "+getElementFromArray(LAST,val[2]);
	}


	@Override
	public String getName(Indexible obj) {
		int index = obj.reduceTempId(10);
		if(index==0) return Species.HUMAN.getNPCNameGen().getName(index);
		else if(index==1) return Species.DWARF.getNPCNameGen().getName(index);
		return getElementFromArray(FIRST,index)+" "+getElementFromArray(LAST,index);
	}

}
