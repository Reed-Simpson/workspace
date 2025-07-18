package names;

import java.awt.Point;
import java.util.Random;

import data.Indexible;
import data.OpenSimplex2S;
import io.SaveRecord;
import util.Util;

public class LocationNameModel {
	public static final int SEED_OFFSET = 4*Util.getOffsetX();
	private SaveRecord record;
	
	public LocationNameModel(SaveRecord record) {
		this.record = record;
	}
	

	private int getIndexValue(Point p,int i) {
		float val = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+i), p.x, p.y);
		return Util.getIndexFromSimplex(val);
	}
	
	public String getName(IndexibleNameGenerator gen,Point p) {
		int[] indexes = new int[20];
		for(int i=0;i<indexes.length;i++) {
			indexes[i] = getIndexValue(p,3+i);
		}
		return gen.getName(new Indexible(indexes));
	}


	public String getName(IndexibleNameGenerator gen, Random random) {
		int[] indexes = new int[20];
		for(int i=0;i<indexes.length;i++) {
			indexes[i] = random.nextInt();
		}
		return gen.getName(new Indexible(indexes));
	}

}
