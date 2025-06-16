package io;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import map.AltitudeModel;
import population.PopulationModel;
import util.DiceRoller;

public class SaveRecord implements Serializable {
	private static final long serialVersionUID = 6999712431803441390L;
	transient private Random random;
	transient private boolean hasUnsavedData;

	private long seed;
	private File saveLocation;
	private Point pos;
	private Point zero;
	private double scale;
	private boolean initialized;
	private HashMap<Point,String> notes;
	private HashMap<Point,String> threats;
	private HashMap<Point,String> cities;
	private HashMap<Point,String> locations;
	private HashMap<Point,String> dungeonEncounters;
	private HashMap<Point,String> regionNames;
	private HashMap<Point,ArrayList<String>> npcs2;
	private HashMap<Point,ArrayList<String>> encounters2;
	private HashMap<Point,ArrayList<String>> dungeonEncounters2;
	private HashMap<Point,ArrayList<String>> locations2;
	private HashMap<Point,ArrayList<String>> dungeons;
	private DiceRoller roller;

	public SaveRecord() {
		this.setRandomSeed();
		this.setSaveLocation(null);
		this.setScale(20);
		setDefaultPos();
		initialized=false;
		this.notes = new HashMap<Point,String>();
		this.threats = new HashMap<Point,String>();
		this.cities = new HashMap<Point,String>();
		this.locations = new HashMap<Point,String>();
		this.dungeonEncounters = new HashMap<Point,String>();
		this.regionNames = new HashMap<Point,String>();


		this.npcs2 = new HashMap<Point,ArrayList<String>>();
		this.encounters2 = new HashMap<Point,ArrayList<String>>();
		this.dungeonEncounters2 = new HashMap<Point,ArrayList<String>>();
		this.locations2 = new HashMap<Point,ArrayList<String>>();
		this.dungeons = new HashMap<Point,ArrayList<String>>();
		
		this.hasUnsavedData = true;
	}

	public void setDefaultPos() {
		Point pos = new Point((int)(this.seed%991-445)*10,(int)(this.seed%997-449)*10);
		this.setPos(pos);
		this.setZero(pos);
	}

	public void initialize(AltitudeModel grid, PopulationModel population) {
		boolean forceCity = true;
		if(forceCity) {
			this.setPos(population.findCity(this.getPos()));
		}else {
			this.setPos(grid.findLand(this.getPos()));
		}
		this.setZero(pos);
		initialized=true;
		this.hasUnsavedData = true;
	}

	public long getSeed() {
		return seed;
	}
	public void setSeed(long seed) {
		this.seed = seed;
		this.hasUnsavedData = true;
		setDefaultPos();
	}
	public void setRandomSeed() {
		this.setSeed(this.getRandom().nextLong());
	}
	public File getSaveLocation() {
		return saveLocation;
	}
	public void setSaveLocation(File saveLocation) {
		this.hasUnsavedData = true;
		this.saveLocation = saveLocation;
	}
	public Point getPos() {
		return pos;
	}
	public void setPos(Point pos) {
		this.hasUnsavedData = true;
		this.pos = pos;
	}

	public long getSeed(int offset) {
		long diff = Long.MAX_VALUE;
		if(this.getSeed()>0) diff -= this.getSeed();
		if(offset>diff) {
			return Long.MIN_VALUE+offset-diff-1;
		}else {
			return this.getSeed()+offset;
		}
	}

	public boolean save(AppData data) {
		boolean result = false;
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(this.saveLocation);
			ObjectOutputStream objectOutputStream = null;
			try {
				objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(this);
				result = true;
				System.out.println("File saved to "+this.saveLocation.toString()+" ["+this.toString()+"]");
				data.addRecent(this.saveLocation);
				this.hasUnsavedData = false;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					objectOutputStream.flush();
					objectOutputStream.close();
				} catch (Exception ex) {}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Location not found: "+this.saveLocation.toString());
		}
		return result;
	}

	public static SaveRecord load(File file,AppData data) {
		if(file==null||!file.canRead()) return null;
		FileInputStream fileInputStream;
		SaveRecord loadedRecord = null;
		ObjectInputStream objectInputStream = null;
		try {
			file.getAbsoluteFile().getParentFile().mkdirs();
			file.createNewFile();
			fileInputStream = new FileInputStream(file);
			objectInputStream = new ObjectInputStream(fileInputStream);
			loadedRecord = (SaveRecord) objectInputStream.readObject();
			System.out.println("File loaded from "+file.toString()+" ["+loadedRecord.toString()+"]");
			data.addRecent(file);
			loadedRecord.hasUnsavedData = false;
		} catch (FileNotFoundException e) {
			System.out.println("File not found: "+file.toString());
		} catch (IOException|ClassNotFoundException e) {
			System.out.println("Failed to load: "+file.toString());
			e.printStackTrace();
		} finally {
			try {
				objectInputStream.close();
			} catch (Exception ex) {}
		}
		if(loadedRecord!=null) {
			if(loadedRecord.notes==null) loadedRecord.notes=new HashMap<Point,String>();
			if(loadedRecord.threats==null) loadedRecord.threats=new HashMap<Point,String>();
			if(loadedRecord.cities==null) loadedRecord.cities=new HashMap<Point,String>();
			if(loadedRecord.locations==null) loadedRecord.locations=new HashMap<Point,String>();
			if(loadedRecord.dungeonEncounters==null) loadedRecord.dungeonEncounters=new HashMap<Point,String>();
			if(loadedRecord.regionNames==null) loadedRecord.regionNames=new HashMap<Point,String>();


			if(loadedRecord.npcs2==null) loadedRecord.npcs2 = new HashMap<Point,ArrayList<String>>();
			if(loadedRecord.encounters2==null) loadedRecord.encounters2 = new HashMap<Point,ArrayList<String>>();
			if(loadedRecord.dungeonEncounters2==null) loadedRecord.dungeonEncounters2 = new HashMap<Point,ArrayList<String>>();
			if(loadedRecord.locations2==null) loadedRecord.locations2 = new HashMap<Point,ArrayList<String>>();
			if(loadedRecord.dungeons==null) loadedRecord.dungeons = new HashMap<Point,ArrayList<String>>();
		}
		return loadedRecord;
	}
	public double getScale() {
		return scale;
	}
	public void setScale(double scale) {
		this.hasUnsavedData = true;
		this.scale = scale;
	}

	public Point getZero() {
		return zero;
	}

	public void setZero(Point zero) {
		this.hasUnsavedData = true;
		this.zero = zero;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public File getDefaultSaveFile() {
		return new File(AppData.getDirectory(),"defaultSave.ser");
	}

	public String toString() {
		return seed+" - ("+(pos.x-zero.x)+","+(pos.y-zero.y)+") scale: "+scale;
	}

	public String putNote(Point p,String s) {
		this.hasUnsavedData = true;
		return this.notes.put(p, s);
	}
	public String getNote(Point p) {
		return this.notes.get(p);
	}
	public String removeNote(Point p) {
		this.hasUnsavedData = true;
		return this.notes.remove(p);
	}

	public String putThreat(Point p,String s) {
		this.hasUnsavedData = true;
		return this.threats.put(p, s);
	}
	public String getThreat(Point p) {
		return this.threats.get(p);
	}
	public String removeThreat(Point p) {
		this.hasUnsavedData = true;
		return this.threats.remove(p);
	}

	public String putCity(Point p,String s) {
		this.hasUnsavedData = true;
		return this.cities.put(p, s);
	}
	public String getCity(Point p) {
		return this.cities.get(p);
	}
	public String removeCity(Point p) {
		this.hasUnsavedData = true;
		return this.cities.remove(p);
	}

	public String putEncounter(Point p, int i,String s) {
		this.hasUnsavedData = true;
		if(!this.encounters2.containsKey(p)) this.encounters2.put(p, new ArrayList<String>());
		ArrayList<String> encounters = this.encounters2.get(p);
		while(encounters.size()<i+1) encounters.add(null);
		return encounters.set(i, s);
	}
	public String getEncounter(Point p, int i) {
		ArrayList<String> encounters = this.encounters2.get(p);
		if(encounters==null||encounters.size()>=i) return null;
		return encounters.get(i);
	}
	public String removeEncounter(Point p, int i) {
		this.hasUnsavedData = true;
		ArrayList<String> encounters = this.encounters2.get(p);
		if(encounters==null||encounters.size()>=i) return null;
		return encounters.set(i,null);
	}

	public String putNPC(Point p,int i,String s) {
		this.hasUnsavedData = true;
		if(!this.npcs2.containsKey(p)) this.npcs2.put(p, new ArrayList<String>());
		ArrayList<String> npcs = this.npcs2.get(p);
		while(npcs.size()<i+1) npcs.add(null);
		return npcs.set(i, s);
	}
	public String getNPC(Point p,int i) {
		ArrayList<String> npcs = this.npcs2.get(p);
		if(npcs==null||npcs.size()>=i) return null;
		return npcs.get(i);
	}
	public String removeNPC(Point p,int i) {
		this.hasUnsavedData = true;
		ArrayList<String> npcs = this.npcs2.get(p);
		if(npcs==null||npcs.size()>=i) return null;
		return npcs.set(i,null);
	}

	public String putLocation(Point p, int i,String s) {
		this.hasUnsavedData = true;
		if(!this.locations2.containsKey(p)) this.locations2.put(p, new ArrayList<String>());
		ArrayList<String> location = this.locations2.get(p);
		while(location.size()<i+1) location.add(null);
		return location.set(i, s);
	}
	public String getLocation(Point p, int i) {
		ArrayList<String> location = this.locations2.get(p);
		if(location==null||location.size()>=i) return null;
		return location.get(i);
	}
	public String removeLocation(Point p, int i) {
		this.hasUnsavedData = true;
		ArrayList<String> location = this.locations2.get(p);
		if(location==null||location.size()>=i) return null;
		return location.set(i,null);
	}

	public String putDungeon(Point p, int i,String s) {
		this.hasUnsavedData = true;
		if(!this.dungeons.containsKey(p)) this.dungeons.put(p, new ArrayList<String>());
		ArrayList<String> location = this.dungeons.get(p);
		while(location.size()<i+1) location.add(null);
		return location.set(i, s);
	}
	public String getDungeon(Point p, int i) {
		ArrayList<String> location = this.dungeons.get(p);
		if(location==null||location.size()>=i) return null;
		return location.get(i);
	}
	public String removeDungeon(Point p, int i) {
		this.hasUnsavedData = true;
		ArrayList<String> location = this.dungeons.get(p);
		if(location==null||location.size()>=i) return null;
		return location.set(i,null);
	}

	public String putDungeonEncounter(Point p, int i,String s) {
		this.hasUnsavedData = true;
		if(!this.dungeonEncounters2.containsKey(p)) this.dungeonEncounters2.put(p, new ArrayList<String>());
		ArrayList<String> encounters = this.dungeonEncounters2.get(p);
		while(encounters.size()<i+1) encounters.add(null);
		return encounters.set(i, s);
	}
	public String getDungeonEncounter(Point p, int i) {
		ArrayList<String> encounters = this.dungeonEncounters2.get(p);
		if(encounters==null||encounters.size()>=i) return null;
		return encounters.get(i);
	}
	public String removeDungeonEncounter(Point p, int i) {
		this.hasUnsavedData = true;
		ArrayList<String> encounters = this.dungeonEncounters2.get(p);
		if(encounters==null||encounters.size()>=i) return null;
		return encounters.set(i,null);
	}

	public String putRegionName(Point p,String s) {
		this.hasUnsavedData = true;
		return this.regionNames.put(p, s);
	}
	public String getRegionName(Point p) {
		return this.regionNames.get(p);
	}
	public String removeRegionName(Point p) {
		this.hasUnsavedData = true;
		return this.regionNames.remove(p);
	}

	public DiceRoller getRoller() {
		if(roller==null) roller = new DiceRoller();
		return roller;
	}
	public Random getRandom() {
		if(random==null) random  = new Random(System.currentTimeMillis());
		return random;
	}
	public boolean hasUnsavedData() {
		return this.hasUnsavedData;
	}
	public boolean setHasUnsavedData(boolean unsavedData) {
		return this.hasUnsavedData = unsavedData;
	}
}
