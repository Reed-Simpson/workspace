package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.npc.NPC;
import data.population.Species;
import data.threat.CreatureType;
import data.threat.Threat;
import data.threat.subtype.DragonType;
import data.threat.subtype.UndeadType;

public class UndeadNameGenerator extends ThreatNameGenerator{
	private static final String[] NIGHTWALKER = {"Death","End","Demise","Extinction","Ruin","Fatality","Mortality","Annihilation","Doom","Grave","Reaper",
			"Silence","Finality","Darkness","Oblivion","Terminus","Eradication","Obliteration","Extermination","Quiet","Desolation"};
	private static final String[] NIGHTWALKER_ADJ = {"Final","Last","Unavoidable","Inevitable","Inescapable","Inexorable","Destined","Certain","Fated",
			"Fatal","Unstoppable","Approaching","Impending","Oncoming","Awaited","Foretold","Prophesied"};
	
	private static final String FACTION_ADJECTIVES = "";
	private static final String FACTION_NOUNS = "";
	private static WeightedTable<String> faction_adjectives;
	private static WeightedTable<String> faction_nouns;

	private static void populateAllTables() {
		faction_adjectives = new WeightedTable<String>();
		populate(faction_adjectives,FACTION_ADJECTIVES,",");
		faction_nouns = new WeightedTable<String>();
		populate(faction_nouns,FACTION_NOUNS,",");
	}
	
	@Override
	public String getName(Threat threat) {
		UndeadType subtype = (UndeadType) threat.getSubtype();
		switch(subtype) {
		case LICH: return getGenericName(threat);
		case VAMPIRE: return getGenericName(threat);
		case MUMMYLORD: return getGenericName(threat);
		case NIGHTWALKER: return getNightwalkerName(threat);
		case SKULLLORD: return getGenericName(threat);
		case DEATHKNIGHT: return getGenericName(threat);
		case DEATHTYRANT: return getDeathtyrantName(threat);
		case DEMILICH: return getGenericName(threat);
		case DRACOLICH: return getDracolichName(threat);
		case GHOSTDRAGON: return getGhostDragonName(threat);
		case GHOST: return getGenericName(threat);
		case GHAST: return getGenericName(threat);
		case WRAITH: return getGenericName(threat);
		default: throw new IllegalArgumentException(subtype.name());
		}
	}
	private String getGenericName(Threat threat) {
		UndeadType subtype = (UndeadType) threat.getSubtype();
		NPC npc = threat.getNPC();
		Species species = npc.getSpecies();
		String speciesName = species.toString();
		if(npc.getSubspecies()!=null) speciesName = npc.getSubspecies();
		String result = npc.getName();
		if(result==null) result = species.getNameGen().getName(threat);
		return result+", The "+speciesName+" "+subtype;
	}
	private String getNightwalkerName(Threat threat) {
		return "The "+getElementFromArray(NIGHTWALKER_ADJ,threat)+" "+getElementFromArray(NIGHTWALKER,threat);
	}
	private String getDeathtyrantName(Threat threat) {
		return CreatureType.ABERRATION.getNameGen().getName(threat);
	}
	private String getDracolichName(Threat threat) {
		threat.setSubtype(DragonType.DRACOLICH);
		return CreatureType.DRAGON.getNameGen().getName(threat);
	}
	private String getGhostDragonName(Threat threat) {
		DragonType type = DragonType.getByWeight(threat);
		return DragonNameGenerator.getName(threat, type);
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
