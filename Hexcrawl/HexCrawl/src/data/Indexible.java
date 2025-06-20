package data;

import java.math.BigInteger;

public class Indexible {
	private BigInteger id;
	private transient BigInteger tempId;


	
	public static BigInteger getIndexFromSimplex(float... floats) {
		String s = "";
		for(int i=0;i<floats.length;i++) {
			int val = Float.floatToRawIntBits(floats[i]);
			s+=Math.abs(val);
		}
		return new BigInteger(s);
	}
	public static BigInteger getIndex(int... ints) {
		String s = "";
		for(int i=0;i<ints.length;i++) {
			s+=ints[i];
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
		BigInteger val = BigInteger.valueOf(factor);
		if(this.tempId.compareTo(val)<0) throw new IllegalStateException("Ran out of ID data");
		int result = this.tempId.mod(val).intValue();
		this.tempId= this.tempId.divide(val);
		return result;
	}
}
