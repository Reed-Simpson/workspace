package names;

import data.Indexible;
import data.WeightedTable;

public abstract class IndexibleNameGenerator {

	protected static String getElementFromArray(String[] array,int val) {
		int index = val%array.length;
		if(index<0) index+=array.length;
		return array[index];
	}

	protected static void populate(WeightedTable<String> table,String values,String regex) {
		for(String s:values.split(regex)) {
			table.put(s);
		}
	}
	public abstract String getName(Indexible obj);

	protected static String getElementFromArray(String[] array,Indexible obj) {
		return array[obj.reduceTempId(array.length)];
	}
	

	@Deprecated
	public String getName(int... val) {
		return getName(new Indexible(val));
	};
}
