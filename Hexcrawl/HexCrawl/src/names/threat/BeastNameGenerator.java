package names.threat;

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
	public static String getBeastAdj(int val) {
		return MonsterModel.getPersonality(val);
	}
	public static String getBeastName(int val) {
		return MonsterModel.getFeature(val);
	}

	@Override
	public String getName(Threat threat,int... index) {
		BeastType subtype = (BeastType) threat.getSubtype();
		String name = getNameBySubtype(subtype,threat,index);
		return name;
	}
	
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

	private String getNameBySubtype(BeastType subtype,Threat threat,int[] index) {
		switch(subtype) {
		case AWAKENED: return getBeastName(index);
		case LYCANTHROPE: return getLycanthropeName(threat,index);
		case DRUID: return getDruidName(threat,index);
		default: throw new IllegalArgumentException(subtype.name());
		}
	}

	private String getBeastName(int[] index) {
		return "The "+getBeastAdj(index[0])+getBeastName(index[1]);
	}
	private String getLycanthropeName(Threat threat,int[] index) {
		threat.setSubtype(HumanoidType.LYCANTHROPE);
		return CreatureType.HUMANOID.getNameGenerator().getName(threat, index);
	}
	private String getDruidName(Threat threat,int[] index) {
		String druid = MagicModel.getDruid(index[0]);
		Species species = threat.getNPC().getSpecies();
		int[] remainder = Util.getRemainder(index, 1);
		String result;
		if(threat.getNPC().getName()==null) result = species.getNPCNameGen().getName(remainder);
		else result = threat.getNPC().getName();
		return result+", The "+species.name()+" "+druid;
	}

}
