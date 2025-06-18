package data;

import java.awt.Point;

import io.SaveRecord;

public abstract class DataModel {
	protected SaveRecord record;

	public DataModel(SaveRecord record) {
		this.record = record;
	}

	protected static void populate(WeightedTable<String> table,String values,String regex) {
		for(String s:values.split(regex)) {
			table.put(s);
		}
	}
	
	public abstract Object getDefaultValue(Point p,int i) ;
}
