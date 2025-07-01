package data.altitude;

import java.awt.Color;
import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;

import data.DataModel;
import data.OpenSimplex2S;
import data.biome.BiomeModel;
import data.biome.BiomeType;
import io.SaveRecord;
import util.Util;

public class AltitudeModel extends DataModel{
	private static float SCALAR_VARIABILITY_1 = 0.5f;
	private static double WEIGHT_0 = 1;
	private static double WEIGHT_1 = 10;
	private static double WEIGHT_2 = 6;
	private static double WEIGHT_3 = 1;
	private static double WEIGHT_4 = 0.1;
	private static float WEIGHT_VARIABILITY_1 = 0.90f;
	private static float WEIGHT_VARIABILITY_2 = 0.90f;
	private static float WEIGHT_VARIABILITY_3 = 0.90f;
	private static float WEIGHT_VARIABILITY_4 = 0.90f;
	private static int ALTITUDE_SCALE = 15000; // max altitude in feet

	public AltitudeModel(SaveRecord record) {
		super(record);
	}

	public float getHeight(int x,int y) {
		float weight0 = (float) WEIGHT_0;
		float megaHeight = weight0*OpenSimplex2S.noise2(record.getSeed(0), Util.getSScale()*x, Util.getSScale()*y);
		megaHeight=-megaHeight*megaHeight*2+1;

		float scalarX1 = OpenSimplex2S.noise2(record.getSeed(1), Util.getCScale()*x, Util.getCScale()*y)*SCALAR_VARIABILITY_1;
		float scalarY1 = OpenSimplex2S.noise2(record.getSeed(2), Util.getCScale()*x, Util.getCScale()*y)*SCALAR_VARIABILITY_1;
		float weightVariability1 = OpenSimplex2S.noise2(record.getSeed(3), Util.getCScale()*x, Util.getCScale()*y)*WEIGHT_VARIABILITY_1;
		float weight1 = (float) (WEIGHT_1+WEIGHT_1*weightVariability1);
		float macroHeight = weight1*OpenSimplex2S.noise2(record.getSeed(4), Util.getCScale()*x+scalarX1, Util.getCScale()*y+scalarY1);

		float scalarX2 = OpenSimplex2S.noise2(record.getSeed(5), Util.getNScale()*x, Util.getNScale()*y)*SCALAR_VARIABILITY_1;
		float scalarY2 = OpenSimplex2S.noise2(record.getSeed(6), Util.getNScale()*x, Util.getNScale()*y)*SCALAR_VARIABILITY_1;
		float weightVariability2 = OpenSimplex2S.noise2(record.getSeed(7), Util.getNScale()*x, Util.getNScale()*y)*WEIGHT_VARIABILITY_2;
		float weight2 = (float) (WEIGHT_2+WEIGHT_2*weightVariability2);
		float midiHeight = weight2*OpenSimplex2S.noise2(record.getSeed(8), Util.getNScale()*x+scalarX2, Util.getNScale()*y+scalarY2);

		float weightVariability3 = OpenSimplex2S.noise2(record.getSeed(9), Util.getLScale()*x, Util.getLScale()*y)*WEIGHT_VARIABILITY_3;
		float weight3 = (float) (WEIGHT_3+WEIGHT_3*weightVariability3);
		float localHeight = weight3*OpenSimplex2S.noise2(record.getSeed(10), Util.getLScale()*x, Util.getLScale()*y);

		float weightVariability4 = OpenSimplex2S.noise2(record.getSeed(11), Util.getMScale()*x, Util.getMScale()*y)*WEIGHT_VARIABILITY_4;
		float weight4 = (float) (WEIGHT_4+WEIGHT_4*weightVariability4);
		float microHeight = weight4*OpenSimplex2S.noise2(record.getSeed(12), Util.getMScale()*x, Util.getMScale()*y);

		float weightSum = weight0+weight1+weight2+weight3+weight4;
		return (megaHeight+macroHeight+midiHeight+localHeight+microHeight)/weightSum;
	}
	public float getHeight(Point p) {
		return getHeight(p.x,p.y);
	}
	public boolean isWater(Point p) {
		return getHeight(p)<BiomeModel.WATER_HEIGHT;
	}
	public boolean isWater(int i, int j) {
		return getHeight(i,j)<BiomeModel.WATER_HEIGHT;
	}
	public boolean isOcean(Point p) {
		return getHeight(p)<BiomeModel.SHALLOWS_HEIGHT;
	}

	public Color getColor(float height) {
		Color c = Color.GREEN;
		float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
		float adjheight = height - BiomeModel.WATER_HEIGHT;
		float maxheight = 1 - BiomeModel.WATER_HEIGHT;
		if(adjheight>maxheight) {
			return Color.BLACK;
		}else if(adjheight>0) {
			int gradiants = 20;
			float b = (float) Math.ceil((1-adjheight/maxheight)*gradiants)/gradiants;
			return Color.getHSBColor(hsb[0], b, b);
		}else {
			return c;
		}
	}

	public static float altitudeTransformation(float altitude) {
		altitude = (altitude - BiomeModel.WATER_HEIGHT)/(1-BiomeModel.WATER_HEIGHT);
		float f = altitude*Math.abs(altitude)*ALTITUDE_SCALE;
		if(Math.abs(altitude)>1) f*=Math.abs(altitude);
		float adj = Math.min(f,0.2f)*300;
		return f+adj;
	}

	public Point findLand(Point p) {
		Point result = p;
		int dx = p.x/Math.abs(p.x);
		int dy = p.y/Math.abs(p.y);
		while(isWater(result)) {
			result.translate(dx, dy);
		}
		return result;
	}

	@Override
	public Float getDefaultValue(Point p,int i) {
		return getHeight(p);
	}

	public BiomeType getAltitudeBiome(Point p) {
		float height = getHeight(p);
		BiomeType type;
		if(height>BiomeModel.SNOW_HEIGHT) {
			type = BiomeType.MOUNTAINS;
		}
		else if(height>BiomeModel.HIGHLAND_HEIGHT) {
			type = BiomeType.ROCKYHILLS;
		}
		else if(height>BiomeModel.COAST_HEIGHT) {
			type = BiomeType.GRASSLAND;
		}
		else if(height>BiomeModel.WATER_HEIGHT) {
			type = BiomeType.BEACH;
		}
		else if(height>BiomeModel.SHALLOWS_HEIGHT) {
			type = BiomeType.SHALLOWS;
		}
		else type = BiomeType.WATER;
		return type;
	}

	public HashSet<Point> getLineOfSight(Point p){
		HashSet<Point> result = new HashSet<Point>();
		result.add(p);
		for(Point px:Util.getRing(p, 10)) {
			result.addAll(getLineOfSight(p,px));
		}
		return result;
	}

	private HashSet<Point> getLineOfSight(Point p, Point px) {
		HashSet<Point> result = new HashSet<Point>();
		int dy = px.y-p.y;
		int dx = px.x-p.x;
		int yrange = Math.abs(dy);
		int xrange = Math.abs(dx);
		int range;
		if(dx*dy>=0) {
			range = yrange+xrange;
		}else if(yrange>xrange) {
			range = yrange;
		}else {
			range = xrange;
		}
		float height = altitudeTransformation(getHeight(p))+6;
		double maxtheta = 0;
		for(int i=1;i<=range;i++) {
			double x = p.x + dx*((double)i)/((double)range);
			double y = p.y + dy*((double)i)/((double)range);
			Point p_i = new Point((int) Math.round(x),(int) Math.round(y));
			float newobstacle = altitudeTransformation(getHeight(p_i))- i*i*2f/3f;
			double theta;
			if(height == newobstacle) theta = Math.PI/2;
			else theta = Math.atan(i/(height-newobstacle));
			if(theta<0) theta = Math.PI+theta;
			if(theta>maxtheta) {
				result.add(p_i);
				maxtheta = theta;
			}
		}
		return result;
	}




}
