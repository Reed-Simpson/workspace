package names.threat;

import data.Indexible;
import data.magic.MagicModel;
import data.npc.NPC;
import data.population.Species;
import data.threat.Threat;
import data.threat.subtype.HumanoidType;
import util.Util;

public class HumanoidNameGenerator extends ThreatNameGenerator{
	private static final String[] GOBLINOIDS = {"Goblin","Hobgoblin","Bugbear"};
	private static final String[] LYCANTHROPE = {"Tiger","Bear","Boar","Rat","Rat","Wolf","Wolf","${animal}"};

	@Deprecated
	public static String getGoblinoid(int val) {
		return getElementFromArray(GOBLINOIDS,val);
	}
	public static String getGoblinoid(Indexible obj) {
		return getElementFromArray(GOBLINOIDS,obj);
	}
	@Deprecated
	public static String getLycanthrope(int val) {
		return getElementFromArray(LYCANTHROPE,val);
	}
	public static String getLycanthrope(Indexible obj) {
		return "Were-"+Util.formatTableResult(getElementFromArray(LYCANTHROPE,obj),obj);
	}

	@Deprecated
	@Override
	public String getName(Threat threat,int... index) {
		HumanoidType subtype = (HumanoidType) threat.getSubtype();
		NPC npc = threat.getNPC();
		Species species = npc.getSpecies();
		if(npc.getName()==null) npc=null;
		return getNameBySubtype(subtype,npc,species,index);
	}

	@Deprecated
	@Override
	public String getName(int... val) {
		if(val.length<5) throw new IllegalArgumentException("Expected 5 or more values");
		HumanoidType subtype = HumanoidType.getFromID(val[0]);
		Species species = Species.getFromId(val[1]);
		int[] remainder = Util.getRemainder(val, 2);
		String result = getNameBySubtype(subtype,null,species,remainder);
		return result;
	}

	@Deprecated
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

	@Deprecated
	private String getCultistName(NPC npc,Species species, int... index) {
		String result;
		if(npc==null) result = species.getNPCNameGen().getName(index);
		else result = npc.getName();
		return result+", The "+species.name()+" Cult Leader";
	}
	@Deprecated
	private String getWarlordName(NPC npc,Species species, int... index) {
		String result;
		if(npc==null) result = species.getNPCNameGen().getName(index);
		else result = npc.getName();
		return result+", The "+species.name()+" Warlord";
	}
	@Deprecated
	private String getSpellcasterName(NPC npc,Species species, int... index) {
		String spellcaster = MagicModel.getSpellcaster(index[0]);
		int[] remainder = Util.getRemainder(index, 1);
		String result;
		if(npc==null) result = species.getNPCNameGen().getName(remainder);
		else result = npc.getName();
		return result+", The "+species.name()+" "+spellcaster;
	}
	@Deprecated
	private String getBanditName(NPC npc,Species species, int... index) {
		String result;
		if(npc==null) result = species.getNPCNameGen().getName(index);
		else result = npc.getName();
		return result+", The "+species.name()+" Bandit Lord";
	}
	@Deprecated
	private String getLycanthropeName(NPC npc,Species species, int... index) {
		String animal = Util.formatTableResult(getLycanthrope(index[0]),new Indexible(index[1]));
		int[] remainder = Util.getRemainder(index, 2);
		String result;
		if(npc==null) result = species.getNPCNameGen().getName(remainder);
		else result = npc.getName();
		return result+", The "+species.name()+" Were-"+animal;
	}
	@Deprecated
	private String getGoblinoidName(int... index) {
		String goblinoid = getGoblinoid(index[0]);
		int[] remainder = Util.getRemainder(index, 1);
		String result = Species.GOBLINOID.getNPCNameGen().getName(remainder);
		return result+", The "+goblinoid+" Warlord";
	}
	@Deprecated
	private String getKoboldName(int... index) {
		String result = Species.KOBOLD.getNPCNameGen().getName(index);
		return result+", The Kobold Denmaster";
	}
	@Deprecated
	private String getOrcName(int... index) {
		String result = Species.ORC.getNPCNameGen().getName(index);
		return result+", The Orc Warlord";
	}
	@Deprecated
	private String getLizardfolkName(int... index) {
		String result = Species.LIZARDFOLK.getNPCNameGen().getName(index);
		return result+", The Lizardfolk Chieftan";
	}
	@Deprecated
	private String getDrowName(int... index) {
		String result = Species.ELF.getNPCNameGen().getName(index);
		return result+", The Drow High Priestess";
	}
	@Deprecated
	private String getDuregarName(int... index) {
		String result = Species.DWARF.getNPCNameGen().getName(index);
		return result+", The Duregar General";
	}
	@Deprecated
	private String getGnollName(int... index) {
		String result = Species.GNOLL.getNPCNameGen().getName(index);
		return result+", The Gnoll Alpha";
	}
	@Deprecated
	private String getTrogName(int... index) {
		String result = Species.TROG.getNPCNameGen().getName(index);
		return result+", The Troglodyte Chieftan";
	}
	
	
	@Override
	public String getName(Threat threat) {
		HumanoidType subtype = (HumanoidType) threat.getSubtype();
		switch(subtype) {
		case CULTIST: return getHumanoidName(threat,"Cult Leader");
		case WARLORD: return getHumanoidName(threat,"Warlord");
		case SPELLCASTER: return getHumanoidName(threat,MagicModel.getSpellcaster(threat));
		case BANDIT: return getHumanoidName(threat,"Bandit Lord");
		case LYCANTHROPE: return getHumanoidName(threat,getLycanthrope(threat));
		case GOBLINOID: return getGoblinoidName(threat);
		case KOBOLD: return getKoboldName(threat);
		case ORC: return getOrcName(threat);
		case LIZARDFOLK: return getLizardfolkName(threat);
		case DROW: return getDrowName(threat);
		case DUREGAR: return getDuregarName(threat);
		case GNOLL: return getGnollName(threat);
		case TROGLODYTE: return getTrogName(threat);
		default: throw new IllegalArgumentException(subtype.name());
		}
	}

	private String getHumanoidName(Threat threat,String profession) {
		NPC npc = threat.getNPC();
		Species species = npc.getSpecies();
		String result = npc.getName();
		if(result==null) result = species.getNPCNameGen().getName(threat);
		return result+", The "+species.name()+" "+profession;
	}
	private String getGoblinoidName(Threat threat) {
		String goblinoid = getGoblinoid(threat);
		NPC npc = threat.getNPC();
		npc.setSubspecies(goblinoid);
		String result = Species.GOBLINOID.getNPCNameGen().getName(threat);
		return result+", The "+goblinoid+" Warlord";
	}
	private String getKoboldName(Threat threat) {
		String result = Species.KOBOLD.getNPCNameGen().getName(threat);
		return result+", The Kobold Denmaster";
	}
	private String getOrcName(Threat threat) {
		String result = Species.ORC.getNPCNameGen().getName(threat);
		return result+", The Orc Warlord";
	}
	private String getLizardfolkName(Threat threat) {
		String result = Species.LIZARDFOLK.getNPCNameGen().getName(threat);
		return result+", The Lizardfolk Chieftan";
	}
	private String getDrowName(Threat threat) {
		String result = Species.ELF.getNPCNameGen().getName(threat);
		return result+", The Drow High Priestess";
	}
	private String getDuregarName(Threat threat) {
		String result = Species.DWARF.getNPCNameGen().getName(threat);
		return result+", The Duregar General";
	}
	private String getGnollName(Threat threat) {
		String result = Species.GNOLL.getNPCNameGen().getName(threat);
		return result+", The Gnoll Alpha";
	}
	private String getTrogName(Threat threat) {
		String result = Species.TROG.getNPCNameGen().getName(threat);
		return result+", The Troglodyte Chieftan";
	}

}
