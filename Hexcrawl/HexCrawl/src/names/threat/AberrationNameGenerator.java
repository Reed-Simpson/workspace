package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.npc.Creature;
import names.FactionNameGenerator;

public class AberrationNameGenerator extends ThreatNameGenerator{
	private static final String[] PART1 = {"Xylo","Vori","Glyn","Dai","Grujh'","Kthegr'","Cxo","C'thor","Oc'thel","oz'ioz","Yz'","Oda","Yox"};
	private static final String[] PART2 = {"gnath","thul","tar","odhe","xerc","ri","ggu","ved","posz","tog","irrax","olki","ulpe"};
	private static final String[] VERB = {"Siphoner of","Whisperer of","Festerer of","Distorter of","Devourer of","Contorted","Fractured","Spectre of","Maddening","Corruptor of"};
	private static final String[] NOUN = {"Dreams","Secrets","Dread","Truth","Light","Nightmare","Reality","Echoes","Beauty","Mind"};

	private static final String FACTION_ADJECTIVES = FactionNameGenerator.ESOTERIC_ADJECTIVES;
	private static final String FACTION_NOUNS = FactionNameGenerator.ESOTERIC_NOUNS;
	private static WeightedTable<String> faction_adjectives;
	private static WeightedTable<String> faction_nouns;

	private static void populateAllTables() {
		faction_adjectives = new WeightedTable<String>();
		populate(faction_adjectives,FACTION_ADJECTIVES,",");
		faction_nouns = new WeightedTable<String>();
		populate(faction_nouns,FACTION_NOUNS,",");
	}
	
	@Deprecated
	@Override
	public String getName(int... val) {
		if(val.length<5) throw new IllegalArgumentException("Expected 5 or more values");
		//AberrationType subtype = AberrationType.getFromID(val[0]);
		String part1 = getElementFromArray(PART1, val[2]);
		String part2 = getElementFromArray(PART2, val[3]);
		String verb = getElementFromArray(VERB, val[4]);
		String noun = getElementFromArray(NOUN, val[5]);
		return part1+part2+", The "+verb+" "+noun;
	}

	@Override
	public String getName(Creature obj) {
		String part1 = getElementFromArray(PART1, obj);
		String part2 = getElementFromArray(PART2, obj);
		String verb = getElementFromArray(VERB, obj);
		String noun = getElementFromArray(NOUN, obj);
		return part1+part2+", The "+verb+" "+noun;
	}
	@Override
	public String getTitle(Creature obj) {
		return "";
	}

	@Override
	public String getFactionAdjective(Indexible obj) {
		if(faction_adjectives==null) populateAllTables();
		return faction_adjectives.getByWeight(obj);
	}

	@Override
	public String getFactionNoun(Indexible obj) {
		if(faction_nouns==null) populateAllTables();
		return faction_nouns.getByWeight(obj);
	}

}
