package names.threat;

import general.Indexible;
import general.Util;
import threat.CreatureSubtype;
import threat.subtype.FiendType;

public class FiendNameGenerator extends ThreatNameGenerator{
	private static final String[] PART1 = {"Bo","Tze","Stragi","Peli","Pati","Plena","Omni","Andro","Ispny","Hagi","Sha","Tha","Si","Simo","Sany","Marcho","Opse","Sito","Mu","Bu","Nie","Deca",
			"One","Germi","Mesni","Glo","Sarpi","Mari","Pele","Aspra","Pyro","Armo","Ouki","Asmo","Panto","Dra","Eni","Sili","Kinpha","Ply","Ba","Dea","Tetri","Gre","Oko","Aphi","Bedi","Mykhri"};
	private static final String[] PART2 = {"tis","pater","ton","el","nix","phages","rix","th","x","pnyx","rtor","piel","sias","rmur","ne","rier","rabia","ros","elel","khael","tas","die",
			"phonou","ki","toro","sem","deus","kym","don","raph","nkon","mory","kes","os","dam","pptos","stor","leel","biel","ziel"};
	private static final String[] TITLE = {"Whose ${form} is ${effect}","The ${monster trait} ${monster personality} ${subtype}","Who ${insanity}","With Whose Passage ${omen}"};
	

	@Override
	public String getName(int... val) {
		if(val.length<5) throw new IllegalArgumentException("Expected 5 or more values");
		CreatureSubtype type = FiendType.getFromID(val[0]);
		String partial = getPartialName(val[1],val[2]);
		int[] remainder = Util.getRemainder(val, 3);
		String title = getTitle(type, remainder);
		return partial+", "+title;
	}
	
	public static String getPartialName(int val1,int val2) {
		String part1 = getElementFromArray(PART1, val1);
		String part2 = getElementFromArray(PART2, val2);
		return part1+part2;
	}

	private String getTitle(CreatureSubtype type, int... val) {
		int[] remainder = Util.getRemainder(val, 1);
		String title = Util.formatTableResult(getElementFromArray(TITLE, val[0]),new Indexible(remainder));
		return title;
	}
}
