package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.magic.MagicModel;
import data.monster.MonsterModel;
import data.npc.NPC;
import data.population.Species;
import data.threat.CreatureType;
import data.threat.Threat;
import data.threat.subtype.BeastType;
import data.threat.subtype.HumanoidType;
import util.Util;

public class BeastNameGenerator extends ThreatNameGenerator{
	private static final String BEASTNOUNS = "Beak,Claw,Eye,Fang,Fin,Fur,Feet,Leg,Horn,Slime,Nose,Scale,Shell,Spike,Spine,Sting,Teeth,Tail,Talon,Arm,Tusk,Wing,"+
			"Beast,Horror,Creature,Menace,Threat,Nuisance,Plague,Pest,Curse,Scourge,Thorn,Bristle,Shadow,Hunter,Killer,Raider,Invader";
	private static WeightedTable<String> beastnouns;
	private static final String BEASTADJS = "${monster personality},${basic color}";
	private static WeightedTable<String> beastadjs;
	public static String getBeastNoun(Indexible obj) {
		if(beastnouns==null) {
			beastnouns = new WeightedTable<String>();
			populate(beastnouns, BEASTNOUNS, ",");
		}
		return beastnouns.getByWeight(obj);
	}
	public static String getBeastAdj(Indexible obj) {
		if(beastadjs==null) {
			beastadjs = new WeightedTable<String>();
			populate(beastadjs, BEASTADJS, ",");
		}
		return Util.formatTableResult(beastadjs.getByWeight(obj),obj);
	}
	
	@Deprecated
	public static String getBeastAdj(int val) {
		return MonsterModel.getPersonality(val);
	}
	@Deprecated
	public static String getBeastName(int val) {
		return MonsterModel.getFeature(val);
	}

	@Deprecated
	@Override
	public String getName(Threat threat,int... index) {
		BeastType subtype = (BeastType) threat.getSubtype();
		String name = getNameBySubtype(subtype,threat,index);
		return name;
	}

	@Deprecated
	@Override
	public String getName(int... val) {
		if(val.length<5) throw new IllegalArgumentException("Expected 5 or more values");
		BeastType subtype = BeastType.getFromID(val[0]);
		Species species = Species.getFromId(val[1]);
		int[] remainder = Util.getRemainder(val, 2);
		Threat t = new Threat();
		t.setNPC(new NPC(species));
		String result = getNameBySubtype(subtype,t,remainder);
		return result;
	}

	@Deprecated
	private String getNameBySubtype(BeastType subtype,Threat threat,int[] index) {
		switch(subtype) {
		case AWAKENED: return getBeastName(index);
		case LYCANTHROPE: return getLycanthropeName(threat,index);
		case DRUID: return getDruidName(threat,index);
		default: throw new IllegalArgumentException(subtype.name());
		}
	}

	@Deprecated
	private String getBeastName(int[] index) {
		return "The "+getBeastAdj(index[0])+getBeastName(index[1]);
	}
	@Deprecated
	private String getLycanthropeName(Threat threat,int[] index) {
		threat.setSubtype(HumanoidType.LYCANTHROPE);
		return CreatureType.HUMANOID.getNameGenerator().getName(threat, index);
	}
	@Deprecated
	private String getDruidName(Threat threat,int[] index) {
		String druid = MagicModel.getDruid(index[0]);
		Species species = threat.getNPC().getSpecies();
		int[] remainder = Util.getRemainder(index, 1);
		String result;
		if(threat.getNPC().getName()==null) result = species.getNPCNameGen().getName(remainder);
		else result = threat.getNPC().getName();
		return result+", The "+species.name()+" "+druid;
	}
	@Override
	public String getName(Threat threat) {
		BeastType subtype = (BeastType) threat.getSubtype();
		switch(subtype) {
		case AWAKENED: return getBeastName(threat);
		case LYCANTHROPE: return getLycanthropeName(threat);
		case DRUID: return getDruidName(threat);
		default: throw new IllegalArgumentException(subtype.name());
		}
	}
	public static String getBeastName(Indexible threat) {
		return "The "+getBeastAdj(threat)+getBeastNoun(threat);
	}
	private String getLycanthropeName(Threat threat) {
		threat.setSubtype(HumanoidType.LYCANTHROPE);
		return CreatureType.HUMANOID.getNameGenerator().getName(threat);
	}
	private String getDruidName(Threat threat) {
		String druid = MagicModel.getDruid(threat);
		NPC npc = threat.getNPC();
		Species species = npc.getSpecies();
		String result = npc.getName();
		if(result==null) result = species.getNPCNameGen().getName(threat);
		return result+", The "+Species.getString(species, npc)+" "+druid;
	}

}
