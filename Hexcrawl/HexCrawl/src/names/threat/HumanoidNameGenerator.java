package names.threat;

import general.Indexible;
import general.Util;
import magic.MagicModel;
import npc.NPC;
import population.Species;
import threat.Threat;
import threat.subtype.HumanoidType;

public class HumanoidNameGenerator extends ThreatNameGenerator{
	private static final String[] GOBLINOIDS = {"Goblin","Hobgoblin","Bugbear"};
	public static String getGoblinoid(int val) {
		return getElementFromArray(GOBLINOIDS,val);
	}
	public static String getGoblinoid(NPC npc) {
		return getElementFromArray(GOBLINOIDS,npc.reduceTempId(GOBLINOIDS.length));
	}
	private static final String[] LYCANTHROPE = {"Tiger","Bear","Boar","Rat","Rat","Wolf","Wolf","${animal}"};
	public static String getLycanthrope(int val) {
		return getElementFromArray(LYCANTHROPE,val);
	}

	@Override
	public String getName(Threat threat,int... index) {
		HumanoidType subtype = (HumanoidType) threat.getSubtype();
		NPC npc = threat.getNPC();
		Species species = npc.getSpecies();
		if(npc.getName()==null) npc=null;
		return getNameBySubtype(subtype,npc,species,index);
	}
	
	@Override
	public String getName(int... val) {
		if(val.length<5) throw new IllegalArgumentException("Expected 5 or more values");
		HumanoidType subtype = HumanoidType.getFromID(val[0]);
		Species species = Species.getFromId(val[1]);
		int[] remainder = Util.getRemainder(val, 2);
		String result = getNameBySubtype(subtype,null,species,remainder);
		return result;
	}

	private String getNameBySubtype(HumanoidType subtype,NPC npc,Species species,int[] index) {
		switch(subtype) {
		case CULTIST: return getCultistName(npc,species,index);
		case WARLORD: return getWarlordName(npc,species,index);
		case SPELLCASTER: return getSpellcasterName(npc,species,index);
		case BANDIT: return getBanditName(npc,species,index);
		case LYCANTHROPE: return getLycanthropeName(npc,species,index);
		case GOBLINOID: return getGoblinoidName(index);
		case KOBOLD: return getKoboldName(index);
		case ORC: return getOrcName(index);
		case LIZARDFOLK: return getLizardfolkName(index);
		case DROW: return getDrowName(index);
		case DUREGAR: return getDuregarName(index);
		case GNOLL: return getGnollName(index);
		case TROGLODYTE: return getTrogName(index);
		default: throw new IllegalArgumentException(subtype.name());
		}
	}


	private String getCultistName(NPC npc,Species species, int... index) {
		String result;
		if(npc==null) result = species.getNPCNameGen().getName(index);
		else result = npc.getName();
		return result+", The "+species.name()+" Cult Leader";
	}
	private String getWarlordName(NPC npc,Species species, int... index) {
		String result;
		if(npc==null) result = species.getNPCNameGen().getName(index);
		else result = npc.getName();
		return result+", The "+species.name()+" Warlord";
	}
	private String getSpellcasterName(NPC npc,Species species, int... index) {
		String spellcaster = MagicModel.getSpellcaster(index[0]);
		int[] remainder = Util.getRemainder(index, 1);
		String result;
		if(npc==null) result = species.getNPCNameGen().getName(remainder);
		else result = npc.getName();
		return result+", The "+species.name()+" "+spellcaster;
	}
	private String getBanditName(NPC npc,Species species, int... index) {
		String result;
		if(npc==null) result = species.getNPCNameGen().getName(index);
		else result = npc.getName();
		return result+", The "+species.name()+" Bandit Lord";
	}
	private String getLycanthropeName(NPC npc,Species species, int... index) {
		String animal = Util.formatTableResult(getLycanthrope(index[0]),new Indexible(index[1]));
		int[] remainder = Util.getRemainder(index, 2);
		String result;
		if(npc==null) result = species.getNPCNameGen().getName(remainder);
		else result = npc.getName();
		return result+", The "+species.name()+" Were-"+animal;
	}
	private String getGoblinoidName(int... index) {
		String goblinoid = getGoblinoid(index[0]);
		int[] remainder = Util.getRemainder(index, 1);
		String result = Species.GOBLINOID.getNPCNameGen().getName(remainder);
		return result+", The "+goblinoid+" Warlord";
	}
	private String getKoboldName(int... index) {
		String result = Species.KOBOLD.getNPCNameGen().getName(index);
		return result+", The Kobold Denmaster";
	}
	private String getOrcName(int... index) {
		String result = Species.ORC.getNPCNameGen().getName(index);
		return result+", The Orc Warlord";
	}
	private String getLizardfolkName(int... index) {
		String result = Species.LIZARDFOLK.getNPCNameGen().getName(index);
		return result+", The Lizardfolk Chieftan";
	}
	private String getDrowName(int... index) {
		String result = Species.ELF.getNPCNameGen().getName(index);
		return result+", The Drow High Priestess";
	}
	private String getDuregarName(int... index) {
		String result = Species.DWARF.getNPCNameGen().getName(index);
		return result+", The Duregar General";
	}
	private String getGnollName(int... index) {
		String result = Species.GNOLL.getNPCNameGen().getName(index);
		return result+", The Gnoll Alpha";
	}
	private String getTrogName(int... index) {
		String result = Species.TROG.getNPCNameGen().getName(index);
		return result+", The Troglodyte Chieftan";
	}

}
