package data;

import java.awt.Point;

import io.SaveRecord;

public abstract class DataModel {
	protected SaveRecord record;

	public DataModel(SaveRecord record) {
		this.record = record;
	}
	
	public abstract Object getDefaultValue(Point p,int i) ;
}
