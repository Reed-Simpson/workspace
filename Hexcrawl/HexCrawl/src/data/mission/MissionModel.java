package data.mission;

import java.awt.Point;

import data.DataModel;
import data.Indexible;
import data.OpenSimplex2S;
import data.WeightedTable;
import data.encounters.EncounterModel;
import data.item.EquipmentModel;
import io.SaveRecord;
import util.Util;
import view.InfoPanel;

public class MissionModel  extends DataModel{
	public static final int SEED_OFFSET = 15*Util.getOffsetX();
	private static final int TABLECOUNT = 1;
	private static final String MISSION_VERBS = "Apprehend,Assassinate,Blackmail,Burgle,Chart,Convince,Deface,Defraud,Deliver,Destroy,Discredit,Escort,"+
			"Exfiltrate,Extort,Follow,Frame,Impersonate,Impress,Infiltrate,Interrogate,Investigate,Kidnap,Locate,Plant,"+
			"Protect,Raid,Replace,Retrieve,Rob,Ruin,Sabotage,Smuggle,Surveil,Control,Terrorize,Threaten";
	private static WeightedTable<String> missions;
	

	private static void populateAllTables() {
		missions = new WeightedTable<String>().populate(MISSION_VERBS,",");
	}
	public static String getMission(Indexible obj) {
		if(missions==null) populateAllTables();
		return missions.getByWeight(obj);
	}

	public MissionModel(SaveRecord record) {
		super(record);
	}
	
	public Mission getMission(Point p, int i) {
		float[] floats = new float[TABLECOUNT];
		for(int x=0;x<floats.length;x++) {
			floats[x] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+i*TABLECOUNT+x), p.x, p.y);
		}
		Mission mission = new Mission(floats);
		mission.setVerb(getMission(mission));
		mission.setObject(new String[]{EquipmentModel.getTrait(mission),EncounterModel.getObj(mission)});
		mission.setQuestgiver(Util.getRandomReference(mission, "npc", InfoPanel.NPCCOUNT+InfoPanel.POICOUNT, p));
		return mission;
	}

	@Override
	public Object getDefaultValue(Point p, int i) {
		return getMission(p, i).toString();
	}

}
