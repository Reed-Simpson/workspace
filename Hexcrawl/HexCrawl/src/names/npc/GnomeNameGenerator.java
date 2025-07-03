package names.npc;

import data.Indexible;
import data.population.NPCSpecies;
import names.IndexibleNameGenerator;

public class GnomeNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Cricket","Daisy","Dimble","Ellywick","Erky","Fiddlestyx","Fonkin","Golly","Mimsy","Pumpkin","Quarrel","Sybilwick"};
	private static final String[] LAST = {"Borogrove","Goldjoy","Hoddypeak","Huddle","Jollywind","Oneshoe","Scramblewise","Sunnyhill","Tallgrass","Timbers","Underbough","Wimbly"};
	


	@Override
	public String getName(Indexible obj) {
		int index = obj.reduceTempId(10);
		if(index==0) return NPCSpecies.HUMAN.getNameGen().getName(obj);
		else if(index==1) return NPCSpecies.DWARF.getNameGen().getName(obj);
		return getElementFromArray(FIRST,index)+" "+getElementFromArray(LAST,index);
	}

}
