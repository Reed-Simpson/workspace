package data.mission;

import java.awt.Point;
import java.util.Random;

import data.DataModel;
import data.HexData;
import data.Indexible;
import data.OpenSimplex2S;
import data.Reference;
import data.WeightedTable;
import data.encounters.EncounterModel;
import data.item.EquipmentModel;
import data.population.PopulationModel;
import io.SaveRecord;
import util.Util;
import view.InfoPanel;

public class MissionModel  extends DataModel{
	public static final int SEED_OFFSET = 15*Util.getOffsetX();
	private static final int TABLECOUNT = 10;
	private static final String MISSION_VERBS = "Apprehend,Assassinate,Blackmail,Burgle,Chart,Convince,Deface,Defraud,Deliver,Destroy,Discredit,Escort,"+
			"Exfiltrate,Extort,Follow,Frame,Impersonate,Impress,Infiltrate,Interrogate,Investigate,Kidnap,Locate,Plant,"+
			"Protect,Raid,Replace,Retrieve,Rob,Ruin,Sabotage,Smuggle,Surveil,Control,Terrorize,Threaten";
	private static WeightedTable<String> missions;


	private static void populateAllTables() {
		missions = new WeightedTable<String>().populate(MISSION_VERBS,",");
	}
	public static String getMissionVerb(Indexible obj) {
		if(missions==null) populateAllTables();
		return missions.getByWeight(obj);
	}

	private PopulationModel pop;

	public MissionModel(SaveRecord record,PopulationModel pop) {
		super(record);
		this.pop = pop;
	}

	public Mission getMission(Point p, int i) {
		float[] floats = new float[TABLECOUNT];
		for(int x=0;x<floats.length;x++) {
			floats[x] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+i*TABLECOUNT+x), p.x, p.y);
		}
		Mission mission = new Mission(floats);
		populateMission(p, mission);
		return mission;
	}
	private void populateMission(Point p, Mission mission) {
		Point p1 = pop.getWeightedNearbyPointsTable(p).getByWeight(mission);
		mission.setVerb(getMissionVerb(mission));
		mission.setObject(new String[]{EquipmentModel.getTrait(mission),EncounterModel.getObj(mission)});
		Point n = record.normalizePOS(p);
		Point n1 = record.normalizePOS(p1);
		if(mission.reduceTempId(2)==0) {
			int index = mission.reduceTempId(InfoPanel.NPCCOUNT);
			mission.setLocal(new Reference(HexData.NPC,n,(index+1)));
			int index1 = mission.reduceTempId(InfoPanel.POICOUNT);
			mission.setRemote(new Reference(HexData.NPC,n1,(InfoPanel.NPCCOUNT*2+index1+1)));
			mission.setRemoteRef(new Reference(HexData.LOCATION,n1,(index1+1)));
		}else {
			int index = mission.reduceTempId(InfoPanel.POICOUNT);
			mission.setLocal(new Reference(HexData.NPC,n,(InfoPanel.NPCCOUNT*2+index+1)));
			mission.setLocalRef(new Reference(HexData.LOCATION,n,(index+1)));
			int index1 = mission.reduceTempId(InfoPanel.NPCCOUNT);
			mission.setRemote(new Reference(HexData.NPC,n1,(index1+1)));
		}
		Point f;
		if(mission.reduceTempId(2)==0) {
			f=n;
		}else {
			f=n1;
		}
		int index = mission.reduceTempId((InfoPanel.FACTIONCOUNT*2+1)*2);
		if(index<InfoPanel.FACTIONCOUNT*2) {
			mission.setRegional(new Reference(HexData.FACTION_NPC,f,(index)));
			mission.setRegionalRef(new Reference(HexData.FACTION,f,(index/2)));
		}else if(index<InfoPanel.FACTIONCOUNT*4) {
			mission.setRegional(new Reference(HexData.FACTION_NPC,f,(index)));
			mission.setRegionalRef(new Reference(HexData.FAITH,f,((index-InfoPanel.FACTIONCOUNT*2)/2)));
		}else {
			mission.setRegional(new Reference(HexData.MINION,f,(index%2+1)));
			mission.setRegionalRef(new Reference(HexData.MINION,f,(0)));
		}
	}

	public Mission getMission(Point p, Random rand) {
		float[] floats = new float[TABLECOUNT];
		for(int x=0;x<floats.length;x++) {
			floats[x] = rand.nextFloat();
		}
		Mission mission = new Mission(floats);
		populateMission(p, mission);
		return mission;
	}

	@Override
	public Object getDefaultValue(Point p, int i) {
		return getMission(p, i).toString();
	}

}
