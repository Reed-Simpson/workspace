package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.npc.Creature;
import names.FactionNameGenerator;

public class FeyNameGenerator extends ThreatNameGenerator {

	private static final String FACTION_ADJECTIVES = FactionNameGenerator.THEATERTROUPE_ADJECTIVES;
	private static final String FACTION_NOUNS = FactionNameGenerator.STREETMUSICIANS_NOUNS+","+FactionNameGenerator.THEATERTROUPE_NOUNS;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName(Creature threat) {
		// TODO Auto-generated method stub
		return null;
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



}
