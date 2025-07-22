package data.biome;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import data.AStarGraph;
import data.DataModel;
import data.Indexible;
import data.OpenSimplex2S;
import data.AStarGraph.EdgeWeightComparator;
import data.altitude.AltitudeModel;
import data.population.PopulationModel;
import data.precipitation.PrecipitationModel;
import io.SaveRecord;
import names.wilderness.WildernessNameGenerator;
import util.Pair;
import util.Util;

public class BiomeModel extends DataModel {
	private static final int TABLECOUNT = 2;
	private static final int NAMEINDEXES = 3;
	//Altitude Breakpoints
	public static final float WATER_HEIGHT = 0.10f;
	public static final float SHALLOWS_HEIGHT = WATER_HEIGHT-0.1f;
	public static final float COAST_HEIGHT = WATER_HEIGHT+0.01f;
	public static final float HIGHLAND_HEIGHT = 0.5f;
	public static final float SNOW_HEIGHT = 0.8f;
	//Humidity Breakpoints
	public static final float DESERT_HUMIDITY = 0.2f;
	public static final float TREE_HUMIDITY = 0.40f;
	public static final float TROPICS_HUMIDITY = 0.65f;
	//chars
	public static final char WAVES = '\u2652';
	public static final char GRASS = '\u22ce';
	public static final char TREE = '\u26b2';
	public static final char SAND = '\u2056';
	public static final char CLIFF = '\u2E81';
	public static final char HILL = '\u23dc';
	public static final char ROCK = '\u26F0';


	public static final int SEED_OFFSET = 6*Util.getOffsetX();
	private static final int LOCAL_WEIGHT_1 = 8;
	private static final int LOCAL_WEIGHT_2 = 10;
	private static final int INFLUENCE_RADIUS = 8;
	private AltitudeModel grid;
	private PrecipitationModel precipitation;
	private PopulationModel population;

	public BiomeModel(SaveRecord record, AltitudeModel grid, PrecipitationModel precipitation,PopulationModel population) {
		super(record);
		this.grid = grid;
		this.precipitation = precipitation;
		this.population=population;
	}
	private Pair<Integer, Float> getBiomeIndexPair(int x, int y, int numTypes) {
		if(numTypes<1||numTypes>49) throw new IllegalArgumentException();
		int index = -1;
		float maxVal = -100;
		for(int i=0;i<numTypes;i++) {
			float biomeValue = getBiomeValuei(x, y, i);
			if(biomeValue>maxVal) {
				maxVal=biomeValue;
				index=i;
			}
		}
		Pair<Integer, Float> result = new Pair<Integer, Float>(index, maxVal);
		return result;
	}

	private float getBiomeValuei(int x, int y, int i) {
		float biomeTypeiN = (OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+i*TABLECOUNT), (Util.getNScale())*x, (Util.getNScale())*y));
		float biomeTypeiL = (OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+i*TABLECOUNT+1), (Util.getLScale())*x, (Util.getLScale())*y));
		float result = (LOCAL_WEIGHT_1*biomeTypeiN+LOCAL_WEIGHT_2*biomeTypeiL)/(LOCAL_WEIGHT_1+LOCAL_WEIGHT_2);
		return result;
	}

	public BiomeType getBiome(int x, int y) {
		Pair<BiomeType, Float> pair = getBiomePair(x, y);
		return pair.key1;
	}
	public BiomeType getBaseBiome(Point p) {
		Pair<BiomeType, Float> pair = getBaseBiomePair(p);
		return pair.key1;
	}
	
	public String getRegionName(Point p) {
		int offset = 1;
		if(grid.isOcean(p)) offset = 2;
		Point region = this.getAbsoluteRegion(p);
		int[] vals = new int[NAMEINDEXES];
		for(int i=0;i<vals.length;i++) {
			vals[i] = Util.getIndexFromSimplex((OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+Util.getOffsetX()-offset-i), region.x, region.y)));
		}
		Indexible obj = new Indexible(vals);
		String regionName = WildernessNameGenerator.getRegionName(obj);
		regionName = Util.formatTableResultPOS(regionName, obj, p, record.getZero());
		return Util.toCamelCase(regionName);
	}

	public String getRegionName(Point p,Random random) {
		int[] vals = new int[NAMEINDEXES];
		for(int i=0;i<vals.length;i++) {
			vals[i] = random.nextInt();
		}
		Indexible obj = new Indexible(vals);
		String regionName = WildernessNameGenerator.getRegionName(obj);
		regionName = Util.formatTableResultPOS(regionName, obj, p, record.getZero());
		return Util.toCamelCase(regionName);
	}

	private Pair<BiomeType, Float> getBiomePair(int x, int y) {
		Point p = new Point(x,y);
		if(population.isCity(p)) return new Pair<BiomeType, Float>(BiomeType.CITY, 0f);
		return getBaseBiomePair(p);
	}
	private Pair<BiomeType, Float> getBaseBiomePair(Point p) {
		float height = grid.getHeight(p);
		float humidity = precipitation.getPrecipitation(p);
		ArrayList<BiomeType> biomes;
		if(precipitation.isLake(p)) biomes = getLakeBiomes(p, height, humidity);
		else biomes = getBiomes(p.x, p.y, height, humidity);
		Pair<Integer, Float> pair = getBiomeIndexPair(p.x, p.y, biomes.size());
		return new Pair<BiomeType, Float>(biomes.get(pair.key1),pair.key2);
	}

	private ArrayList<BiomeType> getLakeBiomes(Point p, float height, float humidity) {
		ArrayList<BiomeType> biomes = new ArrayList<BiomeType>();
		Point outlet = precipitation.getOutlet(p);
		float depth = grid.getHeight(outlet)-height;
		if(precipitation.isOutlet(p)) {
			biomes.add(BiomeType.FLOODPLAIN);
			if(precipitation.getLakeFlow(p)>7) biomes.add(BiomeType.LAKE);
			if(precipitation.getLakeFlow(p)<14) biomes.add(BiomeType.WETLAND);
			if(precipitation.getLakeFlow(p)>21) biomes.add(BiomeType.LAKE);
			if(precipitation.getLakeFlow(p)<28) biomes.add(BiomeType.WETLAND);
		}
		else if(depth*humidity<0.003) biomes.add(BiomeType.FLOODPLAIN);
		else if(depth*precipitation.getLakeFlow(p)<0.02) biomes.add(BiomeType.WETLAND);
		else biomes.add(BiomeType.LAKE);
		return biomes;
	}

	private ArrayList<BiomeType> getBiomes(int x, int y, float height, float humidity) {
		ArrayList<BiomeType> biomes;
		if(height>SNOW_HEIGHT) {
			biomes = getSnowBiomes(x,y,humidity);
		}
		else if(height>HIGHLAND_HEIGHT) {
			biomes = getHighlandBiomes(x,y,humidity);
		}
		else if(height>COAST_HEIGHT) {
			biomes = getLowlandBiomes(x,y,humidity);
		}
		else if(height>WATER_HEIGHT) {
			biomes = getCoastBiomes(x,y,humidity);
		}
		else if(height>SHALLOWS_HEIGHT) {
			biomes = getShallowsBiomes();
		}
		else biomes = getWaterBiomes();
		return biomes;
	}

	private ArrayList<BiomeType> getWaterBiomes() {
		ArrayList<BiomeType> biomes = new ArrayList<BiomeType>();
		biomes.add(BiomeType.WATER);
		return biomes;
	}

	private ArrayList<BiomeType> getShallowsBiomes() {
		ArrayList<BiomeType> biomes = new ArrayList<BiomeType>();
		biomes.add(BiomeType.SHALLOWS);
		return biomes;
	}

	private ArrayList<BiomeType> getCoastBiomes(int x,int y,float humidity) {
		boolean cliffs = grid.getHeight(x, y)>(COAST_HEIGHT-WATER_HEIGHT)/2+WATER_HEIGHT;
		ArrayList<BiomeType> biomes = new ArrayList<BiomeType>();
		float flowVolume = precipitation.getFlowVolume(new Point(x,y));
		if(flowVolume>25) biomes.add( BiomeType.DELTA);
		if(flowVolume>64) biomes.add( BiomeType.DELTA);
		if(flowVolume>100) biomes.add( BiomeType.DELTA);
		if(flowVolume>144) return biomes;
		if(cliffs) biomes.add(BiomeType.CLIFFS);
		biomes.add(BiomeType.BEACH);
		if(humidity>TROPICS_HUMIDITY) {
			biomes.add( BiomeType.SALTMARSH);
		}else if(humidity>TREE_HUMIDITY) {
			if(cliffs) biomes.add(BiomeType.FJORDS);
			biomes.add( BiomeType.SALTMARSH);
		}else if(humidity>DESERT_HUMIDITY) {
			if(cliffs) biomes.add(BiomeType.FJORDS);
			biomes.add( BiomeType.GRASSLAND);
		}else {
			biomes.add( BiomeType.DESERT);
		}
		return biomes;
	}

	private ArrayList<BiomeType> getLowlandBiomes(int x,int y,float humidity) {
		ArrayList<BiomeType> biomes = new ArrayList<BiomeType>();
		if(humidity>TROPICS_HUMIDITY) {
			biomes.add( BiomeType.GRASSLAND);
			biomes.add( BiomeType.JUNGLE);
			biomes.add( BiomeType.WETLAND);
		}else if(humidity>TREE_HUMIDITY) {
			biomes.add( BiomeType.GRASSLAND);
			biomes.add( BiomeType.FOREST);
			biomes.add( BiomeType.WOODYHILLS);
		}else if(humidity>DESERT_HUMIDITY) {
			biomes.add( BiomeType.GRASSLAND);
			biomes.add( BiomeType.SAVANNA);
			biomes.add( BiomeType.ROCKYHILLS);
		}else {
			biomes.add( BiomeType.DESERT);
			biomes.add( BiomeType.SAVANNA);
			biomes.add( BiomeType.SALTPAN);
		}
		return biomes;
	}

	private ArrayList<BiomeType> getHighlandBiomes(int x,int y,float humidity) {
		ArrayList<BiomeType> biomes = new ArrayList<BiomeType>();
		if(humidity>TROPICS_HUMIDITY) {
			biomes.add( BiomeType.HIGHLANDFOREST);
			biomes.add( BiomeType.HIGHLAND);
			biomes.add( BiomeType.WOODYHILLS);
		}else if(humidity>TREE_HUMIDITY) {
			biomes.add( BiomeType.HIGHLANDFOREST);
			biomes.add( BiomeType.HIGHLAND);
			biomes.add( BiomeType.WOODYHILLS);
			biomes.add( BiomeType.TAIGA);
			biomes.add( BiomeType.MOUNTAINS);
		}else if(humidity>DESERT_HUMIDITY) {
			biomes.add( BiomeType.HIGHLAND);
			biomes.add( BiomeType.ROCKYHILLS);
			biomes.add( BiomeType.MOUNTAINS);
			biomes.add( BiomeType.VOLCANIC);
			biomes.add( BiomeType.BADLANDS);
		}else {
			biomes.add( BiomeType.STEPPE);
			biomes.add( BiomeType.BADLANDS);
			biomes.add( BiomeType.VOLCANIC);
		}
		return biomes;
	}

	private ArrayList<BiomeType> getSnowBiomes(int x,int y,float humidity) {
		ArrayList<BiomeType> biomes = new ArrayList<BiomeType>();
		if(humidity>TROPICS_HUMIDITY) {
			biomes.add( BiomeType.TAIGA);
			biomes.add( BiomeType.SNOW);
			biomes.add( BiomeType.GLACIERS);
		}else if(humidity>TREE_HUMIDITY) {
			biomes.add( BiomeType.TAIGA);
			biomes.add( BiomeType.SNOW);
			biomes.add( BiomeType.GLACIERS);
			biomes.add( BiomeType.MOUNTAINS);
			biomes.add( BiomeType.VOLCANIC);
		}else if(humidity>DESERT_HUMIDITY) {
			biomes.add( BiomeType.SNOW);
			biomes.add( BiomeType.GLACIERS);
			biomes.add( BiomeType.MOUNTAINS);
			biomes.add( BiomeType.VOLCANIC);
		}else {
			biomes.add( BiomeType.MOUNTAINS);
			biomes.add( BiomeType.VOLCANIC);
		}
		return biomes;
	}

	public BiomeType getBiome(Point p) {
		return getBiome(p.x,p.y);
	}

	public Color getColor(BiomeType type) {
		return type.getColor();
	}

	public Point getParentRegion(Point p) {
		Pair<BiomeType, Float> pair = getBiomePair(p.x,p.y);
		float pop = pair.key2;
		BiomeType b =  pair.key1;
		Point result = p;
		for(Point p1:Util.getNearbyPoints(p, INFLUENCE_RADIUS)) {
			Pair<BiomeType, Float> pair1 = getBiomePair(p.x,p.y);
			float pop1 = pair1.key2;
			BiomeType b1 =  pair1.key1;
			if(pop1>pop && b1!=null && b1.equals(b)) {
				pop=pop1;
				result = p1;
			}
		}
		return result;
	}

	public Point getAbsoluteRegion(Point p) {
		if(grid.isOcean(p)) {
			return getOcean(p);
		}
		Point p0 = p;
		short count = 0;
		Point p1 = p;
		do {
			p0=p1;
			p1 = getLocalRegion(p0);
			if(p0.equals(p1)) p1 = getParentRegion(p1);
			count++;
		} while(!p0.equals(p1) && count<100);
		return p0;
	}

	private Point getOcean(Point p) {
		int oceanSize = 300;
		return new Point((p.x/oceanSize)*oceanSize,(p.y/oceanSize)*oceanSize);
	}
	public Point getLocalRegion(Point p) {
		Pair<BiomeType, Float> pair = getBiomePair(p.x,p.y);
		float pop = pair.key2;
		BiomeType b =  pair.key1;
		Point result = p;
		for(Point p1:Util.getAdjacentPoints(p)) {
			Pair<BiomeType, Float> pair1 = getBiomePair(p1.x,p1.y);
			float pop1 = pair1.key2;
			BiomeType b1 =  pair1.key1;
			if(pop1>pop && b1!=null && b1.equals(b)) {
				pop=pop1;
				result = p1;
			}
		}
		return result;
	}

	public HashSet<Point> getRegion(Point p) {
		HashSet<Point> group = new HashSet<Point>();
		if(precipitation.isLake(p)) {
			AStarGraph lake = new AStarGraph(3);
			lake.addEdgeWeightcomparator(new EdgeWeightComparator() {
				@Override
				public Iterable<Point> getAdjacentVertices(Point p) {
					HashSet<Point> result = new HashSet<Point>();
					for(Point p1:Util.getAdjacentPoints(p)) {
						if(lake.contains(p1)) result.add(p1);
					}
					return result;
				}
				@Override
				public int getEdgeComparatorWeight(Point p1, Point p2) {
					return precipitation.getEdgeWeight(p1, p2);
				}
			});
			precipitation.findLakeBorders(new HashSet<Point>(), lake, p);
			group.addAll(lake);
		}else {
			ArrayList<Point> unchecked = new ArrayList<Point>();
			unchecked.add(p);
			Point capital = getAbsoluteRegion(p);
			if(capital==null) return group;
			while(!unchecked.isEmpty()) {
				Point next = unchecked.remove(unchecked.size()-1);
				if(capital.equals(getAbsoluteRegion(next))&&group.add(next)) {
					for(Point p1:Util.getAdjacentPoints(next)) {
						if(!group.contains(p1)) unchecked.add(p1);
					}
				}
			}
		}
		return group;
	}
	
	public Pair<BiomeType,BiomeType> getHabitatBiomes(Point p){
		BiomeType townBiome = null;
		if(population.isTown(p)) townBiome = this.getBaseBiome(p);
		return this.getBiome(p).getHabitat(townBiome);
	}
	
	@Override
	public BiomeType getDefaultValue(Point p, int i) {
		return getBiome(p);
	}

}
