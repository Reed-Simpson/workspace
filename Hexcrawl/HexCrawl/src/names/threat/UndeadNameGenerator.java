package names.threat;

import data.npc.NPC;
import data.population.Species;
import data.threat.CreatureType;
import data.threat.Threat;
import data.threat.subtype.AberrationType;
import data.threat.subtype.DragonType;
import data.threat.subtype.UndeadType;
import util.Util;

public class UndeadNameGenerator extends ThreatNameGenerator{
	private static final String[] NIGHTWALKER = {"Death","End","Demise","Extinction","Ruin","Fatality","Mortality","Annihilation","Doom","Grave","Reaper",
			"Silence","Finality","Darkness","Oblivion","Terminus","Eradication","Obliteration","Extermination","Quiet","Desolation"};
	private static final String[] NIGHTWALKER_ADJ = {"Final","Last","Unavoidable","Inevitable","Inescapable","Inexorable","Destined","Certain","Fated",
			"Fatal","Unstoppable","Approaching","Impending","Oncoming","Awaited","Foretold","Prophesied"};
	@Deprecated
	public static String getNightwalkerName(int val) {
		return getElementFromArray(NIGHTWALKER,val);
	}
	@Deprecated
	public static String getNightwalkerAdj(int val) {
		return getElementFromArray(NIGHTWALKER_ADJ,val);
	}

	@Deprecated
	@Override
	public String getName(Threat threat,int... index) {
		UndeadType subtype = (UndeadType) threat.getSubtype();
		NPC npc = threat.getNPC();
		String name = getNameBySubtype(subtype,npc,npc.getSpecies(),index);
		return name;
	}

	@Deprecated
	@Override
	public String getName(int... val) {
		if(val.length<5) throw new IllegalArgumentException("Expected 5 or more values");
		UndeadType subtype = UndeadType.getFromID(val[0]);
		Species species = Species.getFromId(val[1]);
		int[] remainder = Util.getRemainder(val, 2);
		String result = getNameBySubtype(subtype,null,species,remainder);
		return result;
	}

	@Deprecated
	private String getNameBySubtype(UndeadType subtype,NPC npc,Species species,int[] index) {
		switch(subtype) {
		case LICH: return getGenericName(npc,species,subtype,index);
		case VAMPIRE: return getGenericName(npc,species,subtype,index);
		case MUMMYLORD: return getGenericName(npc,species,subtype,index);
		case NIGHTWALKER: return getNightwalkerName(npc,species,index);
		case SKULLLORD: return getGenericName(npc,species,subtype,index);
		case DEATHKNIGHT: return getGenericName(npc,species,subtype,index);
		case DEATHTYRANT: return getDeathtyrantName(index);
		case DEMILICH: return getGenericName(npc,species,subtype,index);
		case DRACOLICH: return getDracolichName(index);
		case GHOSTDRAGON: return getGhostDragonName(index);
		case GHOST: return getGenericName(npc,species,subtype,index);
		case GHAST: return getGenericName(npc,species,subtype,index);
		case WRAITH: return getGenericName(npc,species,subtype,index);
		default: throw new IllegalArgumentException(subtype.name());
		}
	}

	@Deprecated
	private String getGenericName(NPC npc, Species species,UndeadType subtype, int[] index) {
		String speciesName = species.name();
		if(Species.GOBLINOID.equals(species)) speciesName = HumanoidNameGenerator.getGoblinoid(index[0]);
		int[] remainder = Util.getRemainder(index, 1);
		String result;
		if(npc==null) result = species.getNPCNameGen().getName(remainder);
		else result = npc.getName();
		return result+", The "+speciesName+" "+subtype;
	}

	@Deprecated
	private String getNightwalkerName(NPC npc, Species species, int[] index) {
		return "The "+getNightwalkerAdj(index[0])+" "+getNightwalkerName(index[1]);
	}
	@Deprecated
	private String getDeathtyrantName(int[] index) {
		Threat threat = new Threat();
		threat.setSubtype(AberrationType.BEHOLDER);
		return CreatureType.ABERRATION.getNameGenerator().getName(threat, index);
	}
	@Deprecated
	private String getDracolichName(int[] index) {
		Threat threat = new Threat();
		threat.setSubtype(DragonType.DRACOLICH);
		return CreatureType.DRAGON.getNameGenerator().getName(threat, index);
	}
	@Deprecated
	private String getGhostDragonName(int[] index) {
		Threat threat = new Threat();
		threat.setSubtype(DragonType.getFromID(index[0]));
		int[] remainder = Util.getRemainder(index, 1);
		return CreatureType.DRAGON.getNameGenerator().getName(threat, remainder);
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
		String speciesName = species.name();
		if(Species.GOBLINOID.equals(species)) speciesName = HumanoidNameGenerator.getGoblinoid(threat);
		String result = npc.getName();
		if(result==null) result = species.getNPCNameGen().getName(threat);
		return result+", The "+speciesName+" "+subtype;
	}
	private String getNightwalkerName(Threat threat) {
		return "The "+getElementFromArray(NIGHTWALKER_ADJ,threat)+" "+getElementFromArray(NIGHTWALKER,threat);
	}
	private String getDeathtyrantName(Threat threat) {
		return CreatureType.ABERRATION.getNameGenerator().getName(threat);
	}
	private String getDracolichName(Threat threat) {
		threat.setSubtype(DragonType.DRACOLICH);
		return CreatureType.DRAGON.getNameGenerator().getName(threat);
	}
	private String getGhostDragonName(Threat threat) {
		DragonType type = DragonType.getByWeight(threat);
		return DragonNameGenerator.getName(threat, type);
	}

}
