package data;

import java.util.LinkedHashMap;

public class WeightedTable<T> extends LinkedHashMap<T,Integer> {
	private static final long serialVersionUID = 8902680161211841046L;
	private int sumweight = -1;

	public Integer getSumWeight() {
		if(sumweight<0) {
			recalculateSumWeight();
		}
		return sumweight;
	}

	public Integer recalculateSumWeight() {
		Integer result = 0;
		for(Integer f:this.values()) {
			result+=f;
		}
		return sumweight = result;
	}

	public T getByWeight(int index) {
		Integer sum = getSumWeight();
		index = index % sum;
		for(java.util.Map.Entry<T, Integer> e:this.entrySet()) {
			if(index<e.getValue()) return e.getKey();
			else index-=e.getValue();
		}
		return null;
	}

	public T getByWeight(Indexible obj) {
		int index = obj.reduceTempId(getSumWeight());
		for(java.util.Map.Entry<T, Integer> e:this.entrySet()) {
			if(index<e.getValue()) return e.getKey();
			else index-=e.getValue();
		}
		return null;
	}

	public Integer put(T key) {
		return this.put(key, 1);
	}
}
