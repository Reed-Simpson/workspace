package population;

import java.awt.Color;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import biome.BiomeType;
import general.OpenSimplex2S;
import general.Util;
import general.WeightedTable;
import io.SaveRecord;
import map.AltitudeModel;
import precipitation.PrecipitationModel;

public class PopulationModel {
	public static final int SEED_OFFSET = 2*Util.getOffsetX();
	private static final int INFLUENCE_RADIUS = 8;
	private static final int INDEX_STEP = 2;
	private AltitudeModel grid;
	private PrecipitationModel precipitation;
	private HashMap<Point,LinkedHashMap<Species,Float>> cache;
	private DecimalFormat populationStringFormat;
	private DecimalFormat populationPercentStringFormat;
	private SaveRecord record;

	public float getPopulation(int x,int y, Species species) {
		Point p = new Point(x,y);
		if(grid.isWater(p)||precipitation.isLake(p)||species==null) {
			return 0f;
		}else {
			return getPopulationX(x,y,species.getIndex())*species.getWeight();
		}
	}

	public PopulationModel(SaveRecord record, AltitudeModel grid, PrecipitationModel precipitation) {
		this.record = record;
		this.grid = grid;
		this.precipitation = precipitation;
		this.cache = new HashMap<Point,LinkedHashMap<Species,Float>>();
		this.populationStringFormat = new DecimalFormat ("##,##0");
		this.populationPercentStringFormat = new DecimalFormat("#0.00%");
	}

	public LinkedHashMap<Species,Float> getDemographics(Point p) {
		if(grid.isWater(p)||precipitation.isLake(p)) {
			return new LinkedHashMap<Species, Float>();
		}else if(this.cache.get(p)!=null) {
			return this.cache.get(p);
		}else {
			float popFactor = getHarshnessFactor(p.x, p.y);
			LinkedHashMap<Species, Float> map = new LinkedHashMap<Species, Float>();
			for(Species s:Species.getAbeirSpecies()) {
				if(getPopulation(p.x, p.y, s)>0) {
					map.put(s, popTransform(getPopulation(p.x, p.y, s)));
				}
			}
			LinkedHashMap<Species, Float> sortedMap = new LinkedHashMap<Species, Float>();
			if(map.size()>0) {
				List<Map.Entry<Species, Float>> entries = new ArrayList<Map.Entry<Species, Float>>(map.entrySet());
				Collections.sort(entries, new Comparator<Map.Entry<Species, Float>>() {
					public int compare(Map.Entry<Species, Float> a, Map.Entry<Species, Float> b){
						return b.getValue().compareTo(a.getValue());
					}
				});
				Species majority = entries.get(0).getKey();
				for (Map.Entry<Species, Float> entry : entries) {
					float adjValue = entry.getValue();
					adjValue*=popFactor;
					Species key = entry.getKey();
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
				sortedMap.put(Species.OTHER, total*0.01f*getIsolationFactor(p, majority, Species.OTHER));
			}
			this.cache.put(p, sortedMap);
			return sortedMap;
		}
	}

	public float getIsolationFactor(Point p, Species majority, Species minority) {
		return Math.max(1-(getUniversalIsolationFactor(p.x, p.y)+minority.getIsolationFactor()+majority.getIsolationFactor()),0);
	}
	public LinkedHashMap<Species,Float> getDemographics(int x, int y) {
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
		return Math.max(1-Math.abs(grid.getHeight(x,y))*(1-precipitation.getPrecipitation(x, y)),0);
	}

	public Species getMajoritySpecies(int x,int y) {
		LinkedHashMap<Species,Float> pop = getDemographics(x,y);
		for(Species s:pop.keySet()) {
			return s;
		}
		return null;
	}

	public float getMajorityPop(int x,int y) {
		LinkedHashMap<Species,Float> pop = getDemographics(x,y);
		for(Species s:pop.keySet()) {
			return pop.get(s);
		}
		return 0;
	}

	public Point getLocalFealty(Point p) {
		float pop = getMajorityPop(p.x, p.y);
		Species s = getMajoritySpecies(p.x, p.y);
		Point result = p;
		for(Point p1:Util.getAdjacentPoints(p)) {
			float pop1=getMajorityPop(p1.x, p1.y);
			Species s1 = getMajoritySpecies(p1.x, p1.y);
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
		Species s = getMajoritySpecies(p.x, p.y);
		Point result = p;
		for(Point p1:Util.getNearbyPoints(p, INFLUENCE_RADIUS)) {
			float pop1=getMajorityPop(p1.x, p1.y);
			Species s1 = getMajoritySpecies(p1.x, p1.y);
			if(pop1>pop && s1!=null && s1.equals(s)) {
				pop=pop1;
				result = p1;
			}
		}
		return result;
	}

	public Color getColor(int x,int y) {
		float pop = Math.min(Math.max(getUniversalPopulation(x, y),0),1);
		Species species = getMajoritySpecies(x, y);
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
		else return pop;
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

	public WeightedTable<Species> getTransformedDemographics(Point p) {
		LinkedHashMap<Species,Float> demo = getDemographics(p);
		WeightedTable<Species> result = new WeightedTable<Species>();
		int popScale = getPopScale(p);
		for(Entry<Species, Float> e:demo.entrySet()) {
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


}
