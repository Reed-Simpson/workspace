package names.npc;

import data.Indexible;
import data.Util;
import names.IndexibleNameGenerator;

public class HumanNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Adrik","Alvyn","Aurora","Eldeth","Eldon","Farris","Kathra","Kellen","Lily","Nissa","Xinli","Zorra"};
	private static final String[] LAST = {"${color}${element}","${material}fist","${color}castle","Good${item}","${color}beard","${material}beard",
			"${ethereal form}${landmark}","Brightsun","Dundragon","Garrick","Jaerin","Merryweather","Wren"};

	public static String getLastName(int... val) {
		if(val.length<3) throw new IllegalArgumentException("Expected 3 or more values");
		int[] remainder = Util.getRemainder(val, 1);
		return Util.formatTableResult(getElementFromArray(LAST,val[0]),new Indexible(remainder));
	}
	public static String getLastName(Indexible obj) {
		return Util.formatTableResult(getElementFromArray(LAST,obj),obj);
	}

	@Override
	public String getName(int... val) {
		if(val.length<4) throw new IllegalArgumentException("Expected 4 or more values");
		int[] remainder = Util.getRemainder(val, 1);
		return getElementFromArray(FIRST,val[0])+" "+getLastName(remainder);
	}

	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(FIRST,obj)+" "+getLastName(obj);
	}



}
