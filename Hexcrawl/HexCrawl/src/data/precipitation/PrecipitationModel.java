package data.precipitation;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import data.AStarGraph;
import data.DataModel;
import data.OpenSimplex2S;
import data.altitude.AltitudeModel;
import data.biome.BiomeModel;
import io.SaveRecord;
import util.Util;
import view.MapPanel;

public class PrecipitationModel extends DataModel{
	public static final int LOCAL_WEIGHT_1 = 10;
	public static final int LOCAL_WEIGHT_2 = 3;
	public static final int EVAPORATION_WEIGHT = 3;
	public static final int EVAPORATION_RANGE = 10;
	public static final float DISTANCE_FACTOR = 3.0f;
	public static final float CLOUD_HEIGHT = 0.8f;
	public static final float GREEN_THRESH = 0.7f;
	public static final float YELLOW_THRESH = 0.35f;
	private static final int PRECIPITATION_SCALE = 12000; // max annual precipitation in mm
	public static final int SEED_OFFSET = 1*Util.getOffsetX();
	public static final int RIVERFLOWDISTANCE = 1;
	public static final int RIVERRENDERDISTANCE = 7;
	public static final int LAKEFLOWDISTANCE = 3;
	private static final int MAXDEPTH = 40;

	private AltitudeModel grid;
	private HashMap<Point,Float> evapCache;
	private ConcurrentHashMap<Point,Point> flowCache;
	private ConcurrentHashMap<Point,Point> riverCache;
	private ConcurrentHashMap<Point,Float> volumeCache;
	private ConcurrentHashMap<Point,Integer> lakes;
	private ConcurrentHashMap<Point,Point> outletCache;
	public long time;
	private long interval = 1000;

	public PrecipitationModel(SaveRecord record, AltitudeModel grid) {
		super(record);
		this.grid = grid;
		resetCache();
	}
	private void resetCache() {
		this.evapCache = new HashMap<Point,Float>();
		this.flowCache = new ConcurrentHashMap<Point,Point>();
		this.riverCache = new ConcurrentHashMap<Point,Point>();
		this.volumeCache = new ConcurrentHashMap<Point,Float>();
		this.lakes = new ConcurrentHashMap<Point,Integer>();
		this.outletCache = new ConcurrentHashMap<Point,Point>();
	}

	public ConcurrentHashMap<Point,Integer> getLakes() {
		return lakes;
	}

	public boolean isLake(Point p) {
		return lakes.containsKey(p);
	}

	public float getPrecipitation(Point p) {
		float evaporation = getEvaporationFactor(p);
		float local = getLocalFactor(p);
		return (LOCAL_WEIGHT_1+LOCAL_WEIGHT_2)*local/sumWeights()+(EVAPORATION_WEIGHT)*evaporation/sumWeights();
	}
	public float getPrecipitation(int x, int y) {
		return getPrecipitation(new Point(x,y));
	}


	private float getEvaporationFactor(Point p) {
		Float result = evapCache.get(p);
		if(result == null) {
			float sum = 0;
			float count = 0;
			for(int i=-EVAPORATION_RANGE;i<=EVAPORATION_RANGE;i++) {
				int yStart = -EVAPORATION_RANGE-(i<0?i:0);
				int yEnd = EVAPORATION_RANGE-(i>0?i:0);
				for(int j=yStart;j<=yEnd;j++) {
					int d = Util.getDist(new Point(0,0), new Point(i,j));
					if(d==0)d=1;
					double hexWeight = Math.pow(d,DISTANCE_FACTOR);
					count+=hexWeight;
					if(grid.getHeight(p.x+i, p.y+j)<=BiomeModel.WATER_HEIGHT) {
						sum+=hexWeight;
					}
				}
			}
			result = sum/count;
			evapCache.put(p, result);
		}
		return result;
	}

	private float getLocalFactor(Point p) {
		float local1 = (OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET), Util.getNScale()*p.x, Util.getNScale()*p.y)+1)/2;
		float local2 = (OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+1), Util.getLScale()*p.x, Util.getLScale()*p.y)+1)/2;
		return (LOCAL_WEIGHT_1*local1+LOCAL_WEIGHT_2*local2)/(LOCAL_WEIGHT_1+LOCAL_WEIGHT_2);
	}

	private int sumWeights() {
		return LOCAL_WEIGHT_1+LOCAL_WEIGHT_2+EVAPORATION_WEIGHT;
	}

	public static float precipitationTransformation(float precipitation) {
		return precipitation*PRECIPITATION_SCALE;
	}

	public Color getColor(float precipitation) {
		int gradiants = 10;
		int r=255; int g=255;
		if(precipitation>GREEN_THRESH) {
			r=0;
		}else if(precipitation>YELLOW_THRESH){
			r= (int)(255*Math.ceil(gradiants*(GREEN_THRESH-precipitation)/(GREEN_THRESH-YELLOW_THRESH))/gradiants);
		}else {
			g= (int)(255*Math.ceil(gradiants*precipitation/YELLOW_THRESH)/gradiants);
		}
		return new Color(r, g, 0);
	}

	public Point getFlow(Point p) {
		if(grid.isWater(p)) return p;
		if(flowCache.get(p)==null) {
			int index = Util.getIndexFromSimplex(OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+2), p.x, p.y));
			float alt = grid.getHeight(p.x, p.y);
			Point result = p;
			Point result2 = p;
			for(Point p1:Util.getNearbyPoints(p,RIVERFLOWDISTANCE)) {
				float alt1=grid.getHeight(p1.x, p1.y);
				if(alt1<alt ) {
					result2=result;
					result = p1;
					alt=alt1;
				}
			}
			if(index%4==0&&!isFlowingInto(result2,p)) {//chance of fudge is 1 in 2
				result = result2;
			}
			flowCache.put(p, result);
		}
		return flowCache.get(p);
	}
	
	public Point getWiggleFactor(Point p) {
		int range = MapPanel.WIGGLERADIUS*5/6;
		int x = Util.getIndexFromSimplex(OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+3), p.x, p.y));
		int y = Util.getIndexFromSimplex(OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+4), p.x, p.y));
		return new Point(x%(range*2-1)-(range-1),y%(range*2-1)-(range-1));
	}

	public Point getRiver(Point p) {
		if(grid.isWater(p)) return p;
		else return riverCache.get(p);
	}

	public void updateFlowVolume(Point p) {
		this.time = System.currentTimeMillis();
		updateFlowVolume(p,0,0);
	}
	public void updateFlowVolume(Point p, float volume,int depth) {
		if(grid.isWater(p)) return;
		float precipitation = 0;
		Float prevVal;
		if((prevVal=volumeCache.putIfAbsent(p, getPrecipitation(p)))==null) {
			precipitation=getPrecipitation(p);
			prevVal = 0f;
		}
		if(volume+precipitation>0.000001f){
			volumeCache.replace(p, volume+volumeCache.get(p));
			Point p1 = getFlow(p);
			if(depth<MAXDEPTH) {
				Point prevP = riverCache.put(p, p1);
				if(prevP==null) {
					precipitation+=prevVal;
				}
				if(p1!=p) updateFlowVolume(p1,volume+precipitation,depth+1);
				else {
					if(!lakes.contains(p)) {
						Point outlet = generateLake(p);
						updateFlowVolume(outlet, getFlowVolume(p),depth+1);
					}
				}
			}else {
				//System.out.println("Max river length reached: "+Util.posString(p, record.getZero()));
			}
		}
	}

	public Point generateLake(Point p) {
		getFlow(p);
		AStarGraph lake = new AStarGraph(10);
		HashSet<Point> lakeBorder = new HashSet<Point>();
		Point outlet = p;
		Point drain = p;
		float altitude = Float.MAX_VALUE;
		while(isFlowingInto(drain, p)) {
			lake.add(drain);
			if(Util.getDist(outlet, drain)<=LAKEFLOWDISTANCE) {
				addEdge(lake, outlet, drain);
			}
			outlet = drain;
			lakes.put(outlet,0);
			addEdges(lake,outlet);
			drain = checkDrainage(outlet, lake);
			if(drain==null) {
				lakeBorder.remove(outlet);
				findLakeBorders(lakeBorder, lake, outlet);
				for(Point p1:lakeBorder) {
					if(grid.getHeight(p1)<altitude) {
						drain = p1;
						altitude = grid.getHeight(p1);
					}
				}
			}
			altitude = Float.MAX_VALUE;
		}
		for(Point l:lake) {
			HashSet<Point> cache = new HashSet<Point>();
			updateFlowPath(l,outlet,lake,cache);
			outletCache.put(l, outlet);
			lakes.put(l,lake.size());
		}
		if(!isFlowingInto(drain, outlet)) {
			flowCache.put(outlet, drain);
		}else {
			flowCache.put(outlet, outlet);
		}
		return outlet;
	}
	private void addEdges(AStarGraph lake, Point outlet) {
		for(Point p:Util.getAdjacentPoints(outlet)) {
			if(lake.contains(p)) {
				addEdge(lake, outlet, p);
			}
		}
	}
	private void addEdge(AStarGraph lake, Point p1,Point p2) {
		lake.addEdge(p1, p2, getEdgeWeight(p1,p2));
		lake.addEdge(p2, p1, getEdgeWeight(p2,p1));
	}
	private int getEdgeWeight(Point p1,Point p2) {
		float alt = grid.getHeight(p2)-grid.getHeight(p1);
		if(alt>0) return 40+(int)(alt*20);
		else return 20+(int)(alt*10);
	}

	private void updateFlowPath(Point l, Point outlet,AStarGraph lake,HashSet<Point> cache) {
		if(!cache.contains(l)) {
			LinkedList<Point> path = new LinkedList<Point>();
			lake.shortestPath(l, outlet,path);
			Iterator<Point> iterator = path.iterator();
			Point p = iterator.next();
			Point next;
			while(iterator.hasNext()) {
				next = iterator.next();
				flowCache.put(p, next);
				riverCache.put(p, next);
				if(!cache.add(p)) break;
				p = next;
			}
			if(System.currentTimeMillis()>time+interval) {
				System.out.println("updateFlowPath "+(System.currentTimeMillis()-time));
				time=System.currentTimeMillis();
			}
		}
	}

	public Point findLakeBorders(Set<Point> lakeBorder,AStarGraph lake,Point newLake) {
		Point result = null;
		for(Point p1:Util.getNearbyPoints(newLake,1)) {
			if(!lakeBorder.contains(p1)&&!lake.contains(p1)) {
				if(lakes.containsKey(p1)) {
					if(result==null) result = p1;
					lake.add(p1);
					addEdges(lake,p1);
					findLakeBorders(lakeBorder, lake, p1);
				}else {
					lakeBorder.add(p1);
				}
			}
		}
		return result;
	}
	private Point checkDrainage(Point p,AStarGraph lake) {
		Point result = p;
		for(Point p1:Util.getNearbyPoints(p,LAKEFLOWDISTANCE)) {
			if(!lake.contains(p1)&&grid.getHeight(p1)<grid.getHeight(result)&&!isFlowingInto(p1, p)) {
				result=p1;
			}
		}
		if(result.equals(p)) return null;
		else return result;
	}

	private boolean isFlowingInto(Point p1,Point p2) {
		while(p1!=p2&&getFlow(p1)!=p1) {
			p1=getFlow(p1);
		}
		return p1.equals(p2);
	}

	public float getFlowVolume(Point p) {
		return (volumeCache.get(p)==null ? 0 : volumeCache.get(p));
	}

	public float getLakeFlow(Point p) {
		int lakeSize = lakes.get(p);
		Point outlet = getOutlet(p);
		Float flow = volumeCache.get(outlet);
		if(flow==null) {
			System.err.println("cache not populated: "+Util.posString(outlet, record.getZero()));
			flow = getFlowVolume(outlet);
		}
		return flow/lakeSize;
	}
	public boolean isOutlet(Point p) {
		return isLake(p)&&!isLake(getFlow(p));
	}
	public Point getOutlet(Point p) {
		if(isLake(p)) return outletCache.get(p);
		else return getFlow(p);
	}

	@Deprecated
	@Override
	public Float getDefaultValue(Point p, int i) {
		return getPrecipitation(p);
	}


}
