package names;

import java.awt.Point;

import general.OpenSimplex2S;
import general.Util;
import io.SaveRecord;

public class LocationNameModel {
	public static final int SEED_OFFSET = 4*Util.getOffsetX();
	private SaveRecord record;
	private InnNameGenerator innNames;
	
	public LocationNameModel(SaveRecord record) {
		this.record = record;
		this.innNames = new InnNameGenerator();
	}
	

	private int getIndexValue(Point p,int i) {
		float val = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+i), p.x, p.y);
		return Util.getIndexFromSimplex(val);
	}
	
	public String getInnName(Point p) {
		return innNames.getName(getIndexValue(p,0),getIndexValue(p,1));
	}
	public String getInnQuirk(Point p) {
		return innNames.getQuirk(getIndexValue(p,2));
	}
	
	public String getName(NameGenerator gen,Point p) {
		int[] indexes = new int[20];
		for(int i=0;i<indexes.length;i++) {
			indexes[i] = getIndexValue(p,3+i);
		}
		return gen.getName(indexes);
	}

}
