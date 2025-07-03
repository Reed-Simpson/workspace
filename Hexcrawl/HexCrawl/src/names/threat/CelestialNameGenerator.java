package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.npc.Creature;
import data.threat.Threat;
import names.FactionNameGenerator;
import util.Util;

public class CelestialNameGenerator extends ThreatNameGenerator{
	private static final String[] TITLE = {"Whose ${form} is ${effect}","The ${personality} ${subtype} of ${placeholder domain}","The ${personality} Patron of ${job placeholder}",
			"The ${personality} Patron of ${hobby}","The ${personality} Patron of ${relationship}s","The ${personality} Champion of the ${misfortune}"};

	private static final String FACTION_ADJECTIVES = "Celestial,"+FactionNameGenerator.CHURCH_ADJECTIVES;
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
		String partial = FiendNameGenerator.getPartialName(threat);
		String title = Util.formatTableResult(getElementFromArray(TITLE, threat),threat);
		return partial+", "+title;
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
