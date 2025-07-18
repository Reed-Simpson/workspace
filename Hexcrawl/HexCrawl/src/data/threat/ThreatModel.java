package data.threat;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import controllers.DataController;
import data.DataModel;
import data.Indexible;
import data.OpenSimplex2S;
import data.npc.Faction;
import data.npc.NPC;
import data.npc.NPCJobType;
import data.npc.NPCModel;
import data.population.SettlementModel;
import data.population.Species;
import io.SaveRecord;
import names.FactionNameGenerator;
import names.FactionType;
import names.FactionTypeNameGenerator;
import names.threat.ThreatNameGenerator;
import util.Util;

public class ThreatModel extends DataModel{
	private static final int SEED_OFFSET = 7*Util.getOffsetX();
	private static final int THREATGROUPCOUNT = 10;
	private HashMap<Point,Point> centerCache;
	private HashMap<Point,Threat> threatCache;
	private NPCModel npcs;

	public ThreatModel(SaveRecord record,NPCModel npcs) {
		super(record);
		this.npcs = npcs;
		this.centerCache = new HashMap<Point,Point>();
		this.threatCache = new HashMap<Point,Threat>();
	}

	public int getThreatGroup(Point p) {
		int result = -1;
		float val = 0f;
		for(int i=0;i<THREATGROUPCOUNT;i++) {
			float popN = getThreatGroupIndex(i,p,Util.getNScale()*1.4);
			if(popN>val) {
				result = i;
				val=popN;
			}
		}
		return result;
	}
	
	private float getThreatGroupIndex(int i,Point p,double scale) {
		return OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+i), scale*p.x, scale*p.y);
	}

	public Point getCenter(Point p) {
		if(!centerCache.containsKey(p)) {
			HashSet<Point> group = findGroup(p);
			long x = 0;
			long y = 0;
			int count = 0;
			for(Point p1:group) {
				x+=p1.x;
				y+=p1.y;
				count++;
			}
			Point center = new Point((int)(x/count),(int)(y/count));
			for(Point p1:group) {
				centerCache.put(p1, center);
			}
		}
		return centerCache.get(p);
	}

	private HashSet<Point> findGroup(Point p){
		HashSet<Point> group = new HashSet<Point>();
		ArrayList<Point> unchecked = new ArrayList<Point>();
		unchecked.add(p);
		int id = getThreatGroup(p);
		//findGroup(new ArrayList<Point>(),new HashSet<Point>(), group, p, getThreatGroup(p));
		while(!unchecked.isEmpty()) {
			Point next = unchecked.remove(unchecked.size()-1);
			if(getThreatGroup(next)==id&&group.add(next)) {
				for(Point p1:Util.getAdjacentPoints(next)) {
					if(!group.contains(p1)) unchecked.add(p1);
				}
			}
		}
		return group;
	}

	public Set<Point> getRegion(Point p) {
		return findGroup(p);
	}

	private int getThreatDetailIndex(Point p, int i) {
		return Util.getIndexFromSimplex(getThreatGroupIndex(THREATGROUPCOUNT+i, p, 1));
	}
	
	public Threat getThreat(Point p) {
		Point threatSource = getCenter(p);
		if(threatCache.get(threatSource)!=null) return threatCache.get(threatSource);
		NPC npc = npcs.getNPC(NPCModel.THREAT_NPC_INDEX, threatSource);
		int[] indexes = new int[25];
		for(int i=0;i<indexes.length;i++) {
			indexes[i] = getThreatDetailIndex(threatSource,i);
		}
		Threat result = new Threat(indexes);
		result.setNPC(npc);
		populateThreatDetails( result);
		threatCache.put(threatSource, result);
		return result;
	}

	private Threat populateThreatDetails( Threat result) {
		result.setType(getThreatCreatureType(result));
		result.setSubtype(CreatureType.getSubtypeByWeight(result.getType(), result));
		result.setMotive(ThreatDetails.getMotivation(result));
		result.setFlaw(ThreatDetails.getFlaw(result));
		result.setPlan(ThreatDetails.getPlan(result));
		result.setName(getThreatName(result ));
		if(result.getName().contains("${job placeholder}")) {
			result.setDomain(NPCJobType.getJob(result).toString()+"s");
		}else {
			result.setDomain(CreatureType.getDomain(result));
		}
		Species minionSpecies = (Species) Util.getElementFromArray(result.getSubtype().getMinionSpeciesList(), result);
		if(minionSpecies==null) minionSpecies = result.getNPC().getSpecies();
		result.setMinionSpecies(minionSpecies);
		return result;
	}

	public CreatureType getThreatCreatureType(Point p) {
		Threat threat = getThreat(p);
		return threat.getType();
	}
	public CreatureType getThreatCreatureType(Indexible obj) {
		return CreatureType.getByWeight(obj);
	}
	
	public String getThreatName(Threat threat) {
		return CreatureType.getName(threat);
	}

	@Override
	public Threat getDefaultValue(Point p, int i) {
		return getThreat(p);
	}

	public Threat getThreat(Point p,Random random) {
		NPC npc = npcs.getNPC(p,random);
		int[] indexes = new int[25];
		for(int i=0;i<indexes.length;i++) {
			indexes[i] = random.nextInt();
		}
		Threat result = new Threat(indexes);
		result.setNPC(npc);
		populateThreatDetails( result);
		return result;
	}


	private void populateFactionDetails(Threat threat, Faction faction) {
		FactionTypeNameGenerator factionGen = FactionNameGenerator.getNameGenerator(faction.getType());
		ThreatNameGenerator threatGen = threat.getType().getNameGen();
		String name = factionGen.getThreatFactionName(faction,threatGen);
		if(name.contains("${subtype}")) name = Util.replace(name, "${subtype}", threat.getSubtype().getSpeciesName());
		faction.setName(Util.formatTableResult(name,faction));
		String domain = threat.getDomain();
		if(domain!=null) faction.setDomain(domain);
	}
	public Faction getFaction(DataController controller,Point p,Threat threat) {
		Point center = getCenter(p);
		if(threat==null) threat = getThreat(center);
		FactionType[] factionTypes = threat.getType().getFactionList();
		Faction faction = controller.getSettlements().getFaction(SettlementModel.THREAT_FACTION_INDEX, center,factionTypes);
		populateFactionDetails(threat, faction);
		return faction;
	}
	public Faction getFaction(DataController controller, Random random,Point p,Threat threat) {
		if(threat==null) threat = getThreat(p);
		FactionType[] factionTypes = threat.getType().getFactionList();
		Faction faction = controller.getSettlements().getFaction(SettlementModel.THREAT_FACTION_INDEX,random, p,factionTypes);
		populateFactionDetails(threat, faction);
		return faction;
	}
	
	public NPC getMinion(DataController controller,Point p,int i,Threat threat) {
		if(threat==null) threat = getThreat(p);
		NPC npc = controller.getNpcs().getMinion(i, p,threat);
		return npc;
	}
	public NPC getMinion(DataController controller, Random random, Point p,Threat threat) {
		if(threat==null) threat = getThreat(p);
		NPC npc = controller.getNpcs().getNPC(p,random);
		return npc;
	}

}
