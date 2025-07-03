package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.npc.Creature;
import data.threat.Threat;
import names.FactionNameGenerator;
import util.Util;

public class FiendNameGenerator extends ThreatNameGenerator{
	private static final String[] PART1 = {"Bo","Tze","Stragi","Peli","Pati","Plena","Omni","Andro","Ispny","Hagi","Sha","Tha","Si","Simo","Sany","Marcho","Opse","Sito","Mu","Bu","Nie","Deca",
			"One","Germi","Mesni","Glo","Sarpi","Mari","Pele","Aspra","Pyro","Armo","Ouki","Asmo","Panto","Dra","Eni","Sili","Kinpha","Ply","Ba","Dea","Tetri","Gre","Oko","Aphi","Bedi","Mykhri"};
	private static final String[] PART2 = {"tis","pater","ton","el","nix","phages","rix","th","x","pnyx","rtor","piel","sias","rmur","ne","rier","rabia","ros","elel","khael","tas","die",
			"phonou","ki","toro","sem","deus","kym","don","raph","nkon","mory","kes","os","dam","pptos","stor","leel","biel","ziel"};
	private static final String[] TITLE = {"Whose ${form} is ${effect}","The ${monster trait} and ${monster personality}","Who ${insanity}","With Whose Passage ${omen}"};

	private static final String FACTION_ADJECTIVES = "Fiendish,"+FactionNameGenerator.CHURCH_ADJECTIVES;
	private static final String FACTION_NOUNS = FactionNameGenerator.ESOTERIC_NOUNS+","+FactionNameGenerator.CHURCH_NOUNS;
	private static WeightedTable<String> faction_adjectives;
	private static WeightedTable<String> faction_nouns;

	private static void populateAllTables() {
		faction_adjectives = new WeightedTable<String>();
		populate(faction_adjectives,FACTION_ADJECTIVES,",");
		faction_nouns = new WeightedTable<String>();
		populate(faction_nouns,FACTION_NOUNS,",");
	}
	@Override
	public String getName(Creature threat) {
		String partial = getPartialName(threat);
		String title = Util.formatTableResult(getElementFromArray(TITLE, threat),threat);
		if(title.contains("${subtype}")) title = Util.replace(title, "${subtype}", threat.getSpecies().toString());
		return Util.toCamelCase(partial+", "+title);
	}
	public static String getPartialName(Indexible threat) {
		String part1 = getElementFromArray(PART1, threat);
		String part2 = getElementFromArray(PART2, threat);
		return part1+part2;
	}
	@Override
	public String getTitle(Creature obj) {
		return "";
	}
	@Override
	public String getFactionAdjective(Indexible threat) {
		if(faction_adjectives==null) populateAllTables();
		return faction_adjectives.getByWeight(threat);
	}

	@Override
	public String getFactionNoun(Indexible threat) {
		if(faction_nouns==null) populateAllTables();
		return faction_nouns.getByWeight(threat);
	}
	@Override
	public String getDomain(Threat threat) {
		return super.getDomain(threat);
	}

}
