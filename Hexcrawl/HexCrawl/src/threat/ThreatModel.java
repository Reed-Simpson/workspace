package threat;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import general.OpenSimplex2S;
import general.Util;
import io.SaveRecord;
import npc.NPC;
import npc.NPCModel;

public class ThreatModel {
	private static final int SEED_OFFSET = 7*Util.getOffsetX();
	private static final int THREATGROUPCOUNT = 10;
	private HashMap<Point,Point> centerCache;
	private SaveRecord record;
	private NPCModel npcs;

	public ThreatModel(SaveRecord record,NPCModel npcs) {
		this.record = record;
		this.npcs = npcs;
		this.centerCache = new HashMap<Point,Point>();
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
		NPC npc = npcs.getNPC(40, threatSource);
		if(npc==null) npc = npcs.getRandomNPC(40,threatSource);
		Threat result = new Threat(0);
		result.setType(getThreatCreatureType(threatSource));
		result.setSubtype(CreatureType.getSubtypeByWeight(result.getType(), getThreatDetailIndex(threatSource,1)));
		result.setNPC(npc);
		result.setMotive(ThreatDetails.getMotivation(getThreatDetailIndex(threatSource,3)));
		result.setFlaw(ThreatDetails.getFlaw(getThreatDetailIndex(threatSource,4)));
		result.setPlan(ThreatDetails.getPlan(getThreatDetailIndex(threatSource,5)));
		result.setName(getThreatName(result, threatSource ));
		return result;
	}

	public CreatureType getThreatCreatureType(Point p) {
		return CreatureType.getByWeight(getThreatDetailIndex(p,0));
	}
	
	public String getThreatName(Threat threat,Point p) {
		int[] indexes = new int[20];
		for(int i=0;i<indexes.length;i++) {
			indexes[i] = getThreatDetailIndex(p,5+i);
		}
		return Util.toCamelCase(CreatureType.getName(threat, indexes));
	}

}
