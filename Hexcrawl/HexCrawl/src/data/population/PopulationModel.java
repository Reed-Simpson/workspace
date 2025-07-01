package data.population;

import java.awt.Color;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import controllers.DataController;
import data.DataModel;
import data.OpenSimplex2S;
import data.WeightedTable;
import data.altitude.AltitudeModel;
import data.biome.BiomeType;
import data.precipitation.PrecipitationModel;
import io.SaveRecord;
import util.Util;

public class PopulationModel extends DataModel{
	public static final int SEED_OFFSET = 2*Util.getOffsetX();
	private static final int INFLUENCE_RADIUS = 8;
	private static final int INDEX_STEP = 2;
	private AltitudeModel grid;
	private PrecipitationModel precipitation;
	private HashMap<Point,LinkedHashMap<NPCSpecies,Float>> cache;
	private DecimalFormat populationStringFormat = new DecimalFormat ("##,##0");
	private DecimalFormat populationPercentStringFormat = new DecimalFormat("#0.00%");
	private DataController controller;

	public float getPopulation(int x,int y, NPCSpecies species) {
		Point p = new Point(x,y);
		if(grid.isWater(p)||precipitation.isLake(p)||species==null) {
			return 0f;
		}else {
			return getPopulationX(x,y,species.getIndex())*species.getWeight();
		}
	}

	public PopulationModel(SaveRecord record, DataController controller) {
		super(record);
		this.controller = controller;
		this.grid = controller.getGrid();
		this.precipitation = controller.getPrecipitation();
		resetCache();
	}
	private void resetCache() {
		this.cache = new HashMap<Point,LinkedHashMap<NPCSpecies,Float>>();
	}

	public LinkedHashMap<NPCSpecies,Float> getDemographics(Point p) {
		if(grid.isWater(p)||precipitation.isLake(p)) {
			return new LinkedHashMap<NPCSpecies, Float>();
		}else if(this.cache.get(p)!=null) {
			return this.cache.get(p);
		}else {
			float popFactor = getHarshnessFactor(p.x, p.y);
			LinkedHashMap<NPCSpecies, Float> map = new LinkedHashMap<NPCSpecies, Float>();
			for(NPCSpecies s:NPCSpecies.getAbeirSpecies()) {
				if(getPopulation(p.x, p.y, s)>0) {
					map.put(s, popTransform(getPopulation(p.x, p.y, s)));
				}
			}
			LinkedHashMap<NPCSpecies, Float> sortedMap = new LinkedHashMap<NPCSpecies, Float>();
			if(map.size()>0) {
				List<Map.Entry<NPCSpecies, Float>> entries = new ArrayList<Map.Entry<NPCSpecies, Float>>(map.entrySet());
				Collections.sort(entries, new Comparator<Map.Entry<NPCSpecies, Float>>() {
					public int compare(Map.Entry<NPCSpecies, Float> a, Map.Entry<NPCSpecies, Float> b){
						return b.getValue().compareTo(a.getValue());
					}
				});
				NPCSpecies majority = entries.get(0).getKey();
				for (Map.Entry<NPCSpecies, Float> entry : entries) {
					float adjValue = entry.getValue();
					adjValue*=popFactor;
					NPCSpecies key = entry.getKey();
					if(!key.equals(majority)) {
						adjValue*=Math.min(getIsolationFactor(p, majority, key),1);
					}
					if(adjValue>0) {
						sortedMap.put(key, adjValue);
					}
				}
				float total = 0f;
				for(float entry : sortedMap.values()) {
					total+=entry;
				}
				sortedMap.put(NPCSpecies.OTHER, total*0.01f*getIsolationFactor(p, majority, NPCSpecies.OTHER));
			}
			this.cache.put(p, sortedMap);
			return sortedMap;
		}
	}

	public float getIsolationFactor(Point p, NPCSpecies majority, NPCSpecies minority) {
		return Math.max(1-(getUniversalIsolationFactor(p.x, p.y)+minority.getIsolationFactor()+majority.getIsolationFactor()),0);
	}
	public LinkedHashMap<NPCSpecies,Float> getDemographics(int x, int y) {
		return getDemographics(new Point(x,y));
	}

	private float getPopulationX(int x,int y,int index) {
		return getWeightedPopulationX(x,y,index, 1);
	}
	private float getWeightedPopulationX(int x,int y,int index, float weight) {
		int variance = 10;
		float varianceRegionalMaxWeight = 0.8f;
		float popWeight = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+1), Util.getNScale()*x, Util.getNScale()*y)/variance+(varianceRegionalMaxWeight-1.0f/variance);
		float popN = weight*OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+2+index*INDEX_STEP), Util.getNScale()*x, Util.getNScale()*y);
		float popL = weight*OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+3+index*INDEX_STEP), Util.getLScale()*x, Util.getLScale()*y);
		return popWeight*popN+(1-popWeight)*popL;
	}

	public float getUniversalPopulation(int x,int y) {
		float result = 0;
		for(float f:getDemographics(x, y).values()) {
			if(f>0) {
				result+=f;
			}
		}
		return result;
	}

	public float getUniversalPopulation(Point pos) {
		return getUniversalPopulation(pos.x,pos.y);
	}
	public int getTransformedUniversalPopulation(Point pos) {
		int result = 0;
		for(int f:getTransformedDemographics(pos).values()) {
			if(f>0) {
				result+=f;
			}
		}
		return result;
	}
	private float getUniversalIsolationFactor(int x,int y) {
		return OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET), Util.getNScale()*x, Util.getNScale()*y)/2;
	}
	private float getHarshnessFactor(int x,int y) {
		Point p = new Point(x,y);
		return Math.max(1-Math.abs(grid.getHeight(x,y))*(1-precipitation.getPrecipitation(x, y)),0)*Util.getLogisticalCurve(controller.getEconomy().getAdjustedLocalFactor(p));
	}

	public NPCSpecies getMajoritySpecies(int x,int y) {
		return getMajorityPopAndSpecies(x, y).getKey();
	}

	public float getMajorityPop(int x,int y) {
		return getMajorityPopAndSpecies(x, y).getValue();
	}
	public Entry<NPCSpecies, Float> getMajorityPopAndSpecies(int x,int y) {
		LinkedHashMap<NPCSpecies,Float> pop = getDemographics(x,y);
		Iterator<Entry<NPCSpecies, Float>> iterator = pop.entrySet().iterator();
		if(iterator.hasNext()) return iterator.next();
		else return new AbstractMap.SimpleEntry<NPCSpecies, Float>(null, 0f);
	}

	public Point getLocalFealty(Point p) {
		Entry<NPCSpecies,Float> e = getMajorityPopAndSpecies(p.x, p.y);
		float pop = e.getValue();
		NPCSpecies s = e.getKey();
		Point result = p;
		for(Point p1:Util.getAdjacentPoints(p)) {
			Entry<NPCSpecies,Float> e1 = getMajorityPopAndSpecies(p1.x, p1.y);
			float pop1 = e1.getValue();
			NPCSpecies s1 = e1.getKey();
			if(pop1>pop && s1!=null && s1.equals(s)) {
				pop=pop1;
				result = p1;
			}
		}
		return result;
	}

	public boolean isCity(Point p) {
		if(p==null||getUniversalPopulation(p.x,p.y)<0.16f) return false;
		else {
			return p.equals(getLocalFealty(p))&&p.equals(getParentCity(p));
		}
	}
	public boolean isTown(Point p) {
		if(p==null||getUniversalPopulation(p.x,p.y)<0.08f) return false;
		else {
			return p.equals(getLocalFealty(p));
		}
	}
	public BiomeType getSettlementType(Point p) {
		float pop = getUniversalPopulation(p.x,p.y);
		if(pop>=0.08f) {
			boolean isTown = p.equals(getLocalFealty(p));
			if(pop>0.16f&&isTown&&p.equals(getParentCity(p))) {
				return BiomeType.CITY;
			}else if(isTown) {
				return BiomeType.TOWN;
			}
		}
		return null;
	}

	public Point getAbsoluteFealty(Point p) {
		Point p0 = p;
		short count = 0;
		Point p1 = p;
		do {
			p0=p1;
			p1 = getLocalFealty(p0);
			if(p0.equals(p1)) p1 = getParentCity(p1);
			count++;
		} while(!p0.equals(p1) && count<100);
		return p0;
	}

	public Point getParentCity(Point p) {
		float pop = getMajorityPop(p.x, p.y);
		NPCSpecies s = getMajoritySpecies(p.x, p.y);
		Point result = p;
		for(Point p1:Util.getNearbyPoints(p, INFLUENCE_RADIUS)) {
			float pop1=getMajorityPop(p1.x, p1.y);
			NPCSpecies s1 = getMajoritySpecies(p1.x, p1.y);
			if(pop1>pop && s1!=null && s1.equals(s)) {
				pop=pop1;
				result = p1;
			}
		}
		return result;
	}

	public Color getColor(int x,int y) {
		float pop = Math.min(Math.max(getUniversalPopulation(x, y),0),1);
		NPCSpecies species = getMajoritySpecies(x, y);
		int gradiants = 10;
		pop = (float) Math.ceil(pop*gradiants)/gradiants;
		if(species==null) return BiomeType.VOID.getColor();
		else return Color.getHSBColor(species.getHue(), pop, pop);

	}
	public int demoTransformInt(Float pop, int popScale){
		return (int) (pop*popScale);
	}

	public String demoTransformString(Float pop, float total,int popScale) {
		int number = demoTransformInt(pop,popScale);
		return populationStringFormat.format(number);
	}
	public String demoTransformString(Integer pop, int total) {
		if(pop<1) {
			pop = 1;
		}
		return populationStringFormat.format(pop)+" ("+populationPercentStringFormat.format(((float)pop)/total)+")";
	}

	private float popTransform(float pop) {
		if(pop>0) return pop*pop*2;
		else return 0;
	}

	public HashSet<Point> getRegion(Point p) {
		HashSet<Point> group = new HashSet<Point>();
		ArrayList<Point> unchecked = new ArrayList<Point>();
		unchecked.add(p);
		Point capital = getAbsoluteFealty(p);
		if(capital==null) return group;
		while(!unchecked.isEmpty()) {
			Point next = unchecked.remove(unchecked.size()-1);
			if(capital.equals(getAbsoluteFealty(next))&&group.add(next)) {
				for(Point p1:Util.getAdjacentPoints(next)) {
					if(!group.contains(p1)) unchecked.add(p1);
				}
			}
		}
		return group;
	}

	public static int getPopScale(boolean isCity,boolean isTown) {
		int popScale = 3000;//rural pop scale
		if(isCity) {
			popScale = 25000;//city pop scale
		}else if(isTown) {
			popScale = 10000;//town pop scale
		}
		return popScale;
	}
	public int getPopScale(Point p) {
		return getPopScale(isCity(p), isTown(p));
	}

	public WeightedTable<NPCSpecies> getTransformedDemographics(Point p) {
		LinkedHashMap<NPCSpecies,Float> demo = getDemographics(p);
		WeightedTable<NPCSpecies> result = new WeightedTable<NPCSpecies>();
		int popScale = getPopScale(p);
		for(Entry<NPCSpecies, Float> e:demo.entrySet()) {
			int demoTransformInt = demoTransformInt(e.getValue(), popScale);
			if(demoTransformInt>0) result.put(e.getKey(), demoTransformInt);
		}
		return result;
	}

	public Point findCity(Point pos) {
		Point p=null;
		int radius = 0;
		while(p==null) {
			for(Point p1:Util.getRing(pos, radius)) {
				if(isCity(p1)) p=p1;
			}
			radius++;
		}
		return p;
	}

	@Override
	public Integer getDefaultValue(Point p, int i) {
		return getTransformedUniversalPopulation(p);
	}


}
