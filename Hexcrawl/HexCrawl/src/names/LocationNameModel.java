package names;

import java.awt.Point;

import data.Indexible;
import data.OpenSimplex2S;
import data.location.LocationModel;
import io.SaveRecord;
import util.Util;

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
	public String getInnText(Point p) {
		Indexible obj = new Indexible(getIndexValue(p,0),getIndexValue(p,1),getIndexValue(p,2),getIndexValue(p,3));
		String innname = "Inn: "+getInnName(obj);
		String innquirk = "\r\nQuirk: "+getInnQuirk(obj);
		String inndescriptors = "\r\nDescriptors: "+getInnDescriptor(obj)+" and "+getInnDescriptor(obj);
		return innname+innquirk+inndescriptors;
	}
	
	public String getInnName(Indexible obj) {
		return innNames.getName(obj);
	}
	public String getInnQuirk(Indexible obj) {
		return innNames.getQuirk(obj);
	}
	public String getInnDescriptor(Indexible obj) {
		return LocationModel.getDescriptor(obj);
	}
	
	public String getName(NameGenerator gen,Point p) {
		int[] indexes = new int[20];
		for(int i=0;i<indexes.length;i++) {
			indexes[i] = getIndexValue(p,3+i);
		}
		return gen.getName(new Indexible(indexes));
	}

}
