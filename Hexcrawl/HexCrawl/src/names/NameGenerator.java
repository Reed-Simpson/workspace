package names;

import data.Indexible;
import data.WeightedTable;

public abstract class NameGenerator {

	@Deprecated
	public abstract String getName(int... val);
	public abstract String getName(Indexible val);

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
}
