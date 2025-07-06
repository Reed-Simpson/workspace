package data.economy;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import data.AStarGraph;
import data.DataModel;
import data.OpenSimplex2S;
import data.altitude.AltitudeModel;
import data.biome.BiomeModel;
import data.population.PopulationModel;
import data.precipitation.PrecipitationModel;
import io.SaveRecord;
import util.Util;

public class EconomicModel extends DataModel{
	public static final int LOCAL_WEIGHT_1 = 10;
	public static final int LOCAL_WEIGHT_2 = 1;
	public static final int SEED_OFFSET = 5*Util.getOffsetX();
	private static final int ROAD_DISTANCE = 16;
	private PopulationModel population;
	private BiomeModel biomes;
	private PrecipitationModel precipitation;
	private AltitudeModel grid;
	private AStarGraph travel;
	private AStarGraph roads;
	private HashSet<Point> roadsCache;

	public EconomicModel(SaveRecord record, PopulationModel population,BiomeModel biomes,PrecipitationModel precipitation,AltitudeModel grid) {
		super(record);
		this.population = population;
		this.biomes = biomes;
		this.precipitation = precipitation;
		this.grid = grid;
		resetCache();
	}
	private void resetCache() {
		this.travel = new AStarGraph(1);
		this.roads = new AStarGraph(1);
		this.roadsCache = new HashSet<Point>();
	}

	private float getLocalFactor(Point p) {
		float local1 = (OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET), Util.getNScale()*p.x, Util.getNScale()*p.y));
		float local2 = (OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+1), Util.getLScale()*p.x, Util.getLScale()*p.y));
		return (LOCAL_WEIGHT_1*local1+LOCAL_WEIGHT_2*local2)/(LOCAL_WEIGHT_1+LOCAL_WEIGHT_2);
	}
	public EconomicType getEconomicType(Point p) {
		float economicFactor = getLocalFactor(p);
		return EconomicType.getEconomicType(economicFactor);
	}

	private void populateEdges(Point p) {
		boolean pIsWater = grid.isWater(p)||precipitation.isLake(p);
		for(Point p1:Util.getAdjacentPoints(p)) {
			int weight = biomes.getBiome(p1).getTravel()*2;
			boolean p1IsWater = grid.isWater(p1)||precipitation.isLake(p1);
			if(p1IsWater!=pIsWater&&!population.isTown(p)&&!population.isTown(p1)) weight = 10*2;
			if(travel.getEdgeWeight(p, p1)==-1) travel.addEdge(p, p1, weight);
		}
		Point p1 = precipitation.getFlow(p);
		if(p1!=null&&!p1.equals(p)) {
			int distance = Util.getDist(p, p1);
			float volume = precipitation.getFlowVolume(p);
			int weight;
			if(volume>81) weight = 3;
			else if(volume>36) weight = 4;
			else if(volume>9) weight = 5;
			else if(volume>7) weight = 6;
			else if(volume>4) weight = 7;
			else if(volume>1) weight = 8;
			else weight = 12;
			if(!travel.contains(p1)) travel.add(p1);
			if(travel.getEdgeWeight(p, p1)>weight*distance||travel.getEdgeWeight(p, p1)==-1) travel.addEdge(p, p1, weight*distance);
			if(travel.getEdgeWeight(p1, p)>weight*distance||travel.getEdgeWeight(p, p1)==-1) travel.addEdge(p1, p, weight*distance);
		}
	}

	public Color getColor(int i, int j) {
		return Color.getHSBColor(60f/360f, 0.2f, getSteppedLocalFactor(new Point(i,j)));
	}

	public float getAdjustedLocalFactor(Point p) {
		return Util.adjustSimplex(getLocalFactor(p),0,1);
	}
	public float getSteppedLocalFactor(Point p) {
		int steps = 10;
		float result = getAdjustedLocalFactor(p);
		return (float) Math.ceil(result*steps)/steps;
	}

	public String getRoadDescription(Point p) {
		int roadId = calcFinalRoadId(p);
		return getRoadType(roadId);
	}
	private int calcFinalRoadId(Point p) {
		int roadId = getRoadPopId(p);
		int mainRoad = -1;
		for(Point p1:roads.getAdjacencyList(p)) {
			if(roads.getEdgeWeight(p, p1)>mainRoad) mainRoad = roads.getEdgeWeight(p, p1);
			if(roads.getEdgeWeight(p1, p)>mainRoad) mainRoad = roads.getEdgeWeight(p1, p);
		}
		if(mainRoad>-1) {
			roadId = Math.max(roadId, getRoadWealthId(p))+mainRoad-1;
		}
		return roadId;
	}
	public String getCrossingDescription(Point p) {
		int roadId = calcFinalRoadId(p);
		Float volume = precipitation.getFlowVolume(p);
		if(roadId>=7) {
			if(volume>20) return "Ferry";
			else if(volume>7) return "Toll Bridge";
			else return "Bridge";
		}else if(roadId==6) {
			if(volume>15) return "Ferry";
			else if(volume>5) return "Toll Bridge";
			else return "Bridge";
		}else if(roadId==5) {
			if(volume>14) return "Ferry";
			else if(volume>11) return "Ford";
			else if(volume>3) return "Toll Bridge";
			else return "Bridge";
		}else if(roadId==4) {
			if(volume>12) return "None";
			else if(volume>7) return "Ferry";
			else return "Ford";
		}else if(roadId==3) {
			if(volume>4) return "None";
			else return "Ford";
		}else {
			return "None";
		}
		
	}

	private String getRoadType(int roadId) {
		String result;
		if(roadId>=7) result="high road";
		else if(roadId==6) result="low road";
		else if(roadId==5) result="cobbled road";
		else if(roadId==4) result="dirt road";
		else if(roadId==3) result="cart track";
		else if(roadId==2) result="smooth path";
		else if(roadId==1) result="uneven path";
		else result="Game trail";
		return result;
	}

	public int getRoadWealthId(Point p) {
		float wealth = getAdjustedLocalFactor(p);
		if(wealth>0.8) return 7;
		else if(wealth>0.7) return 6;
		else if(wealth>0.6) return 5;
		else if(wealth>0.5) return 4;
		else if(wealth>0.4) return 3;
		else if(wealth>0.3) return 2;
		else if(wealth>0.2) return 1;
		else return 0;
	}

	public int getRoadPopId(Point p) {
		float pop = population.getUniversalPopulation(p);
		if(pop>2.2f) return 5;
		else if(pop>1.2f) return 4;
		else if(pop>0.3f) return 3;
		else if(pop>0.03f) return 2;
		else if(pop>0.003f) return 1;
		else return 0;
	}

	public void findTradeRoads(Point p) {
		if(roadsCache.contains(p)) return;
		ArrayList<Point> nearbyTowns = new ArrayList<Point>();
		travel.add(p);
		for(Point p1:Util.getNearbyPoints(p, ROAD_DISTANCE*2+1)) {
			travel.add(p1);
		}
		populateEdges(p);
		for(int radius=1;radius<=ROAD_DISTANCE;radius++) {
			for(Point p1:Util.getRing(p, radius)) {
				populateEdges(p1);
				if(population.isTown(p1)) nearbyTowns.add(p1);
			}
		}
		boolean distant = false;
		if(nearbyTowns.isEmpty()) {
			distant = true;
			for(int radius=ROAD_DISTANCE+1;radius<=ROAD_DISTANCE*2;radius++) {
				for(Point p1:Util.getRing(p, radius)) {
					populateEdges(p1);
					if(population.isTown(p1)) {
						nearbyTowns.add(p1);
						break;
					}
				}
			}
		}
		if(!roads.contains(p)) roads.add(p);
		boolean isCity = population.isCity(p);
		for(Point town:nearbyTowns) {
			int weight = 1;
			int maxdistance = 36;
			LinkedList<Point> path = new LinkedList<Point>();
			int distance = travel.shortestPath(p, town,path);
			if(isCity&&population.isCity(town)) {
				if(distance<maxdistance) weight = 2;
				else {
					maxdistance*=2;
				}
			}
			if(distant) maxdistance*=2;
			Iterator<Point> iterator = path.iterator();
			if(distance<=maxdistance) {
				if(path.size()==0) {
					System.err.println("no path found");break;
				}
				Point p1 = iterator.next();
				while(iterator.hasNext()) {
					Point next = iterator.next();
					if(!roads.contains(next)) roads.add(next);
					int edgeWeight = roads.getEdgeWeight(p1, next);
					if(edgeWeight==-1||edgeWeight<weight) {
						roads.addEdge(p1, next,weight);
					}
					int roadweight = 3;
					if(travel.getEdgeWeight(p1, next)>roadweight) travel.addEdge(p1, next, roadweight);
					if(travel.getEdgeWeight(next, p1)>roadweight) travel.addEdge(next, p1, roadweight);
					p1=next;
				}
			}
		}
		roadsCache.add(p);
	}

	public AStarGraph getRoads(){
		return this.roads;
	}

	@Override
	public Float getDefaultValue(Point p, int i) {
		return getLocalFactor(p);
	}
}
