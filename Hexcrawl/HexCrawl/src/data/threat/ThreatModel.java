package data.threat;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import data.DataModel;
import data.Indexible;
import data.OpenSimplex2S;
import data.npc.NPC;
import data.npc.NPCModel;
import io.SaveRecord;
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
			float popN = getThreatGroupIndex(i,p,Util.getNScale());
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
		NPC npc = npcs.getNPC(40, threatSource);
		if(npc==null) npc = npcs.getRandomNPC(40,threatSource);
		int[] indexes = new int[25];
		for(int i=0;i<indexes.length;i++) {
			indexes[i] = getThreatDetailIndex(p,i);
		}
		Threat result = new Threat(indexes);
		result.setType(getThreatCreatureType(threatSource,result));
		result.setSubtype(CreatureType.getSubtypeByWeight(result.getType(), result));
		result.setNPC(npc);
		result.setMotive(ThreatDetails.getMotivation(result));
		result.setFlaw(ThreatDetails.getFlaw(result));
		result.setPlan(ThreatDetails.getPlan(result));
		result.setName(getThreatName(result, threatSource ));
		threatCache.put(threatSource, result);
		return result;
	}

	public CreatureType getThreatCreatureType(Point p) {
		Threat threat = getThreat(p);
		return threat.getType();
	}
	public CreatureType getThreatCreatureType(Point p,Indexible obj) {
		return CreatureType.getByWeight(obj);
	}
	
	public String getThreatName(Threat threat,Point p) {
		int[] indexes = new int[20];
		for(int i=0;i<indexes.length;i++) {
			indexes[i] = getThreatDetailIndex(p,5+i);
		}
		return Util.toCamelCase(CreatureType.getName(threat));
	}

	@Override
	public Threat getDefaultValue(Point p, int i) {
		return getThreat(p);
	}

}
