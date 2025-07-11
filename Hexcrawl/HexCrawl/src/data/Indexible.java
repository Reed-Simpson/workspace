package data;

import java.math.BigInteger;
import util.Util;

public class Indexible {
	private BigInteger id;
	private transient BigInteger tempId;


	
	public static BigInteger getIndexFromSimplex(float... floats) {
		String s = "";
		for(int i=0;i<floats.length;i++) {
			int val = Util.getIndexFromSimplex(floats[i]);
			s+=Math.abs(val);
		}
		return new BigInteger(s);
	}
	public static BigInteger getIndex(int... ints) {
		String s = "";
		for(int i=0;i<ints.length;i++) {
			s+=Math.abs(ints[i]);
		}
		return new BigInteger(s);
	}
	
	public Indexible(float... floats) {
		this.setId(floats);
	}
	public Indexible(int... index) {
		this.setId(getIndex(index));
	}


	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
		this.tempId = id;
	}

	public void setId(float... floats) {
		setId(getIndexFromSimplex(floats));
	}

	public BigInteger getTempId() {
		return tempId;
	}

	public void setTempId(BigInteger tempId) {
		this.tempId = tempId;
	}
	public int reduceTempId(int factor) {
		if(factor<1) throw new IllegalArgumentException("Factor must be greater than 0");
		BigInteger val = BigInteger.valueOf(factor);
		if(this.tempId.compareTo(val)<0) throw new IllegalStateException("Ran out of ID data");
		int result = this.tempId.mod(val).intValue();
		this.tempId= this.tempId.divide(val);
		return result;
	}
	public double getDouble(int granularity) {
		int random = this.reduceTempId(granularity-1)+1;
		return Util.percentileToZ(random*1.0/granularity)/2;
	}
}
