package io;

import java.awt.Color;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Random;

import data.Reference;
import data.altitude.AltitudeModel;
import data.population.PopulationModel;
import util.DiceRoller;
import util.Pair;
import util.Util;
import view.MapFrame;

public class SaveRecord implements Serializable {
	private static final long serialVersionUID = parseBits(MapFrame.VERSION);
	transient private Random random;
	transient private boolean hasUnsavedData;

	private long seed;
	private File saveLocation;
	private Point pos;
	private Point zero;
	private Point hero;
	private double scale;
	private boolean initialized;
	private int chaosFactor;
	private LinkedHashMap<Point,String> notes;
	private HashMap<Point,String> threats;
	private HashMap<Point,String> cities;
	private HashMap<Point,String> biomeNames;
	private HashMap<Point,String> regionNames;
	private HashMap<Point,ArrayList<String>> npcs2;
	private HashMap<Point,ArrayList<String>> factionNPCs;
	private HashMap<Point,ArrayList<String>> proprietors;
	private HashMap<Point,ArrayList<String>> encounters2;
	private HashMap<Point,ArrayList<String>> dungeonEncounters2;
	private HashMap<Point,ArrayList<String>> locations2;
	private HashMap<Point,ArrayList<String>> dungeons;
	private HashMap<Point,ArrayList<String>> factions;
	private HashMap<Point,ArrayList<String>> faiths;
	private HashMap<Point,Color> highlights;
	private DiceRoller roller;
	private ArrayList<Reference> campaignCharacters;
	private ArrayList<String> campaignThreads;
	private HashMap<Point,ArrayList<String>> minions;
	private HashSet<Point> explored;
	private HashMap<Point,ArrayList<String>> beasts;
	private HashMap<Point,ArrayList<String>> threatMonsters;
	private HashMap<Point,ArrayList<String>> missions;
	private HashMap<Pair<Point,Point>,String> cityHistory;
	private HashMap<Point,String> history;
	private HashMap<Point,String> events;
	private HashMap<Point,ArrayList<String>> districts;
	private HashMap<Point, ArrayList<String>> monsters;
	private HashMap<Point, String> oceanNames;

	public SaveRecord() {
		this.setRandomSeed();
		this.setSaveLocation(null);
		this.setScale(20);
		setDefaultPos();
		initialized=false;
		chaosFactor=5;
		this.notes = new LinkedHashMap<Point,String>();
		this.threats = new HashMap<Point,String>();
		this.cities = new HashMap<Point,String>();
		this.biomeNames = new HashMap<Point,String>();
		this.regionNames = new HashMap<Point,String>();
		this.oceanNames = new HashMap<Point,String>();


		this.npcs2 = new HashMap<Point,ArrayList<String>>();
		this.factionNPCs = new HashMap<Point,ArrayList<String>>();
		this.proprietors = new HashMap<Point,ArrayList<String>>();
		this.encounters2 = new HashMap<Point,ArrayList<String>>();
		this.dungeonEncounters2 = new HashMap<Point,ArrayList<String>>();
		this.locations2 = new HashMap<Point,ArrayList<String>>();
		this.dungeons = new HashMap<Point,ArrayList<String>>();
		this.factions = new HashMap<Point,ArrayList<String>>();
		this.faiths = new HashMap<Point,ArrayList<String>>();
		this.highlights = new HashMap<Point,Color>();
		this.minions = new HashMap<Point,ArrayList<String>>();
		this.beasts = new HashMap<Point,ArrayList<String>>();
		this.monsters = new HashMap<Point,ArrayList<String>>();
		this.threatMonsters = new HashMap<Point,ArrayList<String>>();
		this.missions = new HashMap<Point,ArrayList<String>>();
		this.cityHistory = new HashMap<Pair<Point,Point>,String>();
		this.history = new HashMap<Point,String>();
		this.events = new HashMap<Point,String>();
		this.districts = new HashMap<Point,ArrayList<String>>();

		this.campaignCharacters = new ArrayList<Reference>();
		this.campaignThreads = new ArrayList<String>();
		this.explored = new HashSet<Point>();
		
		this.hasUnsavedData = true;
	}

	private static long parseBits(String version) {
		long result = 0;
		char[] charArray = version.toCharArray();
		for(int i=0;i<charArray.length;i++) {
			result+= ((long)(charArray[i]))*Character.MAX_VALUE;
		}
		return result;
	}

	public void setDefaultPos() {
		Point pos = new Point((int)(this.seed%991-445)*10,(int)(this.seed%997-449)*10);
		this.setPos(pos);
		this.setZero(pos);
	}

	public void initialize(AltitudeModel grid, PopulationModel population) {
		boolean forceCity = false;
		if(forceCity) {
			this.setPos(population.findCity(this.getPos()));
		}else {
			this.setPos(grid.findLand(this.getPos()));
		}
		this.setZero(pos);
		this.hero = this.zero;
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
			if(loadedRecord.districts==null) loadedRecord.districts = new HashMap<Point,ArrayList<String>>();
			if(loadedRecord.monsters==null) loadedRecord.monsters = new HashMap<Point,ArrayList<String>>();
			if(loadedRecord.oceanNames==null) loadedRecord.oceanNames = new HashMap<Point,String>();
			if(loadedRecord.biomeNames==null) loadedRecord.biomeNames = new HashMap<Point,String>();
			//backwards compatibility field initialization
		}
		return loadedRecord;
	}
	public double getScale() {
		return scale;
	}
	public void setScale(double scale) {
		if(scale!=this.scale) {
			this.hasUnsavedData = true;
			this.scale = scale;
		}
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
		return new File(AppData.getDirectory(),"defaultSave_"+MapFrame.VERSION+".ser");
	}

	public String toString() {
		return seed+" - ("+(pos.x-zero.x)+","+(pos.y-zero.y)+") scale: "+scale;
	}
	public Point normalizePOS(Point p) {
		return Util.normalizePos(p, zero);
	}
	public Point denormalizePOS(Point p) {
		return Util.denormalizePos(p, zero);
	}

	public String putNote(Point p,String s) {
		String put = this.notes.put(p, s);
		if(put!=null&&!put.equals(s)) {
			this.hasUnsavedData = true;
		}
		return put;
	}
	public String getNote(Point p) {
		return this.notes.get(p);
	}
	public String removeNote(Point p) {
		String remove = this.notes.remove(p);
		if(remove!=null) {
			this.hasUnsavedData = true;
		}
		return remove;
	}

	public String putThreat(Point p,String s) {
		String put = this.threats.put(p, s);
		if(put!=null&&!put.equals(s)) {
			this.hasUnsavedData = true;
		}
		return put;
	}
	public String getThreat(Point p) {
		return this.threats.get(p);
	}
	public String removeThreat(Point p) {
		String remove = this.threats.remove(p);
		if(remove!=null) {
			this.hasUnsavedData = true;
		}
		return remove;
	}

	public String putCity(Point p,String s) {
		String put = this.cities.put(p, s);
		if(put!=null&&!put.equals(s)) {
			this.hasUnsavedData = true;
		}
		return put;
	}
	public String getCity(Point p) {
		return this.cities.get(p);
	}
	public String removeCity(Point p) {
		String remove = this.cities.remove(p);
		if(remove!=null) {
			this.hasUnsavedData = true;
		}
		return remove;
	}

	public String putEncounter(Point p, int i,String s) {
		if(!this.encounters2.containsKey(p)) this.encounters2.put(p, new ArrayList<String>());
		ArrayList<String> encounters = this.encounters2.get(p);
		while(encounters.size()<i+1) encounters.add(null);
		String set = encounters.set(i, s);
		if(set!=null&&!set.equals(s)) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	public String getEncounter(Point p, int i) {
		ArrayList<String> encounters = this.encounters2.get(p);
		if(encounters==null||encounters.size()<=i) return null;
		return encounters.get(i);
	}
	public ArrayList<String> getEncounters(Point p) {
		if(!this.encounters2.containsKey(p)) this.encounters2.put(p, new ArrayList<String>());
		return this.encounters2.get(p);
	}
	public String removeEncounter(Point p, int i) {
		ArrayList<String> encounters = this.encounters2.get(p);
		if(encounters==null||encounters.size()<=i) return null;
		String set = encounters.remove(i);
		if(set!=null) {
			this.hasUnsavedData = true;
		}
		return set;
	}

	public String putNPC(Point p,int i,String s) {
		if(!this.npcs2.containsKey(p)) this.npcs2.put(p, new ArrayList<String>());
		ArrayList<String> npcs = this.npcs2.get(p);
		while(npcs.size()<i+1) npcs.add(null);
		String set = npcs.set(i, s);
		if(set!=null&&!set.equals(s)) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	public String getNPC(Point p,int i) {
		ArrayList<String> npcs = this.npcs2.get(p);
		if(npcs==null||npcs.size()<=i) return null;
		return npcs.get(i);
	}
	public String removeNPC(Point p,int i) {
		ArrayList<String> npcs = this.npcs2.get(p);
		if(npcs==null||npcs.size()<=i) return null;
		String set = npcs.set(i, null);
		if(set!=null) {
			this.hasUnsavedData = true;
		}
		return set;
	}

	public String putFactionNPC(Point p,int i,String s) {
		if(!this.factionNPCs.containsKey(p)) this.factionNPCs.put(p, new ArrayList<String>());
		ArrayList<String> npcs = this.factionNPCs.get(p);
		while(npcs.size()<i+1) npcs.add(null);
		String set = npcs.set(i, s);
		if(set!=null&&!set.equals(s)) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	public String getFactionNPC(Point p,int i) {
		ArrayList<String> npcs = this.factionNPCs.get(p);
		if(npcs==null||npcs.size()<=i) return null;
		return npcs.get(i);
	}
	public String removeFactionNPC(Point p,int i) {
		ArrayList<String> npcs = this.factionNPCs.get(p);
		if(npcs==null||npcs.size()<=i) return null;
		String set = npcs.set(i, null);
		if(set!=null) {
			this.hasUnsavedData = true;
		}
		return set;
	}

	public String putProprietor(Point p,int i,String s) {
		if(!this.proprietors.containsKey(p)) this.proprietors.put(p, new ArrayList<String>());
		ArrayList<String> npcs = this.proprietors.get(p);
		while(npcs.size()<i+1) npcs.add(null);
		String set = npcs.set(i, s);
		if(set!=null&&!set.equals(s)) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	public String getProprietor(Point p,int i) {
		ArrayList<String> npcs = this.proprietors.get(p);
		if(npcs==null||npcs.size()<=i) return null;
		return npcs.get(i);
	}
	public String removeProprietor(Point p,int i) {
		ArrayList<String> npcs = this.proprietors.get(p);
		if(npcs==null||npcs.size()<=i) return null;
		String set = npcs.set(i, null);
		if(set!=null) {
			this.hasUnsavedData = true;
		}
		return set;
	}

	public String putLocation(Point p, int i,String s) {
		if(!this.locations2.containsKey(p)) this.locations2.put(p, new ArrayList<String>());
		ArrayList<String> location = this.locations2.get(p);
		while(location.size()<i+1) location.add(null);
		String set = location.set(i, s);
		if(set!=null&&!set.equals(s)) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	public String getLocation(Point p, int i) {
		ArrayList<String> location = this.locations2.get(p);
		if(location==null||location.size()<=i) return null;
		return location.get(i);
	}
	public String removeLocation(Point p, int i) {
		ArrayList<String> location = this.locations2.get(p);
		if(location==null||location.size()<=i) return null;
		String set = location.set(i, null);
		if(set!=null) {
			this.hasUnsavedData = true;
		}
		return set;
	}

	public String putDungeon(Point p, int i,String s) {
		if(!this.dungeons.containsKey(p)) this.dungeons.put(p, new ArrayList<String>());
		ArrayList<String> location = this.dungeons.get(p);
		while(location.size()<i+1) location.add(null);
		String set = location.set(i, s);
		if(set!=null&&!set.equals(s)) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	public String getDungeon(Point p, int i) {
		ArrayList<String> location = this.dungeons.get(p);
		if(location==null||location.size()<=i) return null;
		return location.get(i);
	}
	public String removeDungeon(Point p, int i) {
		ArrayList<String> location = this.dungeons.get(p);
		if(location==null||location.size()<=i) return null;
		String set = location.set(i, null);
		if(set!=null) {
			this.hasUnsavedData = true;
		}
		return set;
	}

	public String putFaction(Point p, int i,String s) {
		if(!this.factions.containsKey(p)) this.factions.put(p, new ArrayList<String>());
		ArrayList<String> faction = this.factions.get(p);
		while(faction.size()<i+1) faction.add(null);
		String set = faction.set(i, s);
		if(set!=null&&!set.equals(s)) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	public String getFaction(Point p, int i) {
		ArrayList<String> faction = this.factions.get(p);
		if(faction==null||faction.size()<=i) return null;
		return faction.get(i);
	}
	public String removeFaction(Point p, int i) {
		ArrayList<String> faction = this.factions.get(p);
		if(faction==null||faction.size()<=i) return null;
		String set = faction.set(i, null);
		if(set!=null) {
			this.hasUnsavedData = true;
		}
		return set;
	}

	public String putFaith(Point p, int i,String s) {
		if(!this.faiths.containsKey(p)) this.faiths.put(p, new ArrayList<String>());
		ArrayList<String> faction = this.faiths.get(p);
		while(faction.size()<i+1) faction.add(null);
		String set = faction.set(i, s);
		if(set!=null&&!set.equals(s)) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	public String getFaith(Point p, int i) {
		ArrayList<String> faction = this.faiths.get(p);
		if(faction==null||faction.size()<=i) return null;
		return faction.get(i);
	}
	public String removeFaith(Point p, int i) {
		ArrayList<String> faction = this.faiths.get(p);
		if(faction==null||faction.size()<=i) return null;
		String set = faction.set(i, null);
		if(set!=null) {
			this.hasUnsavedData = true;
		}
		return set;
	}

	public String putDungeonEncounter(Point p, int i,String s) {
		if(!this.dungeonEncounters2.containsKey(p)) this.dungeonEncounters2.put(p, new ArrayList<String>());
		ArrayList<String> encounters = this.dungeonEncounters2.get(p);
		while(encounters.size()<i+1) encounters.add(null);
		String set = encounters.set(i, s);
		if(set!=null&&!set.equals(s)) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	public String getDungeonEncounter(Point p, int i) {
		ArrayList<String> encounters = this.dungeonEncounters2.get(p);
		if(encounters==null||encounters.size()<=i) return null;
		return encounters.get(i);
	}

	public ArrayList<String> getDungeonEncounters(Point p) {
		if(!this.dungeonEncounters2.containsKey(p)) this.dungeonEncounters2.put(p, new ArrayList<String>());
		return this.dungeonEncounters2.get(p);
	}
	public String removeDungeonEncounter(Point p, int i) {
		ArrayList<String> encounters = this.dungeonEncounters2.get(p);
		if(encounters==null||encounters.size()<=i) return null;
		String set = encounters.remove(i);
		if(set!=null) {
			this.hasUnsavedData = true;
		}
		return set;
	}

	public String putBiomeName(Point p,String s) {
		String put = this.biomeNames.put(p, s);
		if(put!=null&&!put.equals(s)) {
			this.hasUnsavedData = true;
		}
		return put;
	}
	public String getBiomeName(Point p) {
		return this.biomeNames.get(p);
	}
	public String removeBiomeName(Point p) {
		String put = this.biomeNames.remove(p);
		if(put!=null) {
			this.hasUnsavedData = true;
		}
		return this.biomeNames.remove(p);
	}

	public String putRegionName(Point p,String s) {
		System.out.println("focusLost "+s+" - "+p.toString());
		String put = this.regionNames.put(p, s);
		if(put!=null&&!put.equals(s)) {
			this.hasUnsavedData = true;
		}
		return put;
	}
	public String getRegionName(Point p) {
		return this.regionNames.get(p);
	}
	public String removeRegionName(Point p) {
		String put = this.regionNames.remove(p);
		if(put!=null) {
			this.hasUnsavedData = true;
		}
		return this.regionNames.remove(p);
	}

	public String putOceanName(Point p,String s) {
		String put = this.oceanNames.put(p, s);
		if(put!=null&&!put.equals(s)) {
			this.hasUnsavedData = true;
		}
		return put;
	}
	public String getOceanName(Point p) {
		return this.oceanNames.get(p);
	}
	public String removeOceanName(Point p) {
		String put = this.oceanNames.remove(p);
		if(put!=null) {
			this.hasUnsavedData = true;
		}
		return this.oceanNames.remove(p);
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

	public void setHighlight(Point p, Color color) {
		if(color==null||Color.BLACK.equals(color)) highlights.remove(p);
		else highlights.put(p, color);
	}
	public Color getHighlight(Point p) {
		return highlights.get(p);
	}
	
	public ArrayList<Reference> getCampaignCharacters(){
		return this.campaignCharacters;
	}
	public Reference getCampaignCharacter(int i) {
		if(campaignCharacters.size()<=i) return null;
		else return this.campaignCharacters.get(i);
	}
	public String putCampaignCharacter(int i,String s) {
		Reference ref = new Reference(s);
		while(this.campaignCharacters.size()<i+1) this.campaignCharacters.add(null);
		Reference set = this.campaignCharacters.set(i, ref);
		if(set!=null&&!set.toString().equals(s)) {
			this.hasUnsavedData = true;
		}
		if(set==null) return null;
		else return set.toString();
	}
	public Reference removeCampaignCharacter(int index) {
		return this.campaignCharacters.remove(index);
	}
	
	public ArrayList<String> getCampaignThreads(){
		return this.campaignThreads;
	}
	public String getCampaignThread(int i) {
		if(campaignThreads.size()<=i) return null;
		else return this.campaignThreads.get(i);
	}
	public String putCampaignThread(int i,String s) {
		while(this.campaignThreads.size()<i+1) this.campaignThreads.add(null);
		String set = this.campaignThreads.set(i, s);
		if(set!=null&&!set.equals(s)) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	public String removeCampaignThread(int index) {
		if(this.campaignThreads.size()>index) return this.campaignThreads.remove(index);
		else return null;
	}

	

	public String putMinion(Point p,int i,String s) {
		if(!this.minions.containsKey(p)) this.minions.put(p, new ArrayList<String>());
		ArrayList<String> minion = this.minions.get(p);
		while(minion.size()<i+1) minion.add(null);
		String set = minion.set(i, s);
		if(set!=null&&!set.equals(s)) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	public String getMinion(Point p,int i) {
		ArrayList<String> minion = this.minions.get(p);
		if(minion==null||minion.size()<=i) return null;
		return minion.get(i);
	}
	public String removeMinion(Point p,int i) {
		ArrayList<String> minion = this.minions.get(p);
		if(minion==null||minion.size()<=i) return null;
		String set = minion.set(i, null);
		if(set!=null) {
			this.hasUnsavedData = true;
		}
		return set;
	}

	public int getChaosFactor() {
		return chaosFactor;
	}

	public void setChaosFactor(int chaosFactor) {
		if(this.chaosFactor!=chaosFactor) this.hasUnsavedData=true;
		this.chaosFactor = chaosFactor;
	}

	public boolean addExplored(Point p) {
		boolean result = this.explored.add(p);
		if(result) this.hasUnsavedData =true;
		return result;
	}
	public boolean addAllExplored(Collection<? extends Point> ps) {
		boolean result = this.explored.addAll(ps);
		if(result) this.hasUnsavedData =true;
		return result;
	}
	public boolean removeExplored(Point p) {
		boolean result = this.explored.remove(p);
		if(result) this.hasUnsavedData =true;
		return result;
	}
	public boolean isExplored(Point p) {
		return this.explored.contains(p);
	}
	public HashSet<Point> getExplored(){
		return this.explored;
	}

	public Point getHero() {
		return hero;
	}

	public void setHero(Point hero) {
		this.hero = hero;
	}

	public String putBeast(Point p,int i,String s) {
		if(!this.beasts.containsKey(p)) this.beasts.put(p, new ArrayList<String>());
		ArrayList<String> beast = this.beasts.get(p);
		while(beast.size()<i+1) beast.add(null);
		String set = beast.set(i, s);
		if(set!=null&&!set.equals(s)) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	public String getBeast(Point p,int i) {
		ArrayList<String> beast = this.beasts.get(p);
		if(beast==null||beast.size()<=i) return null;
		return beast.get(i);
	}
	public String removeBeast(Point p,int i) {
		ArrayList<String> beast = this.beasts.get(p);
		if(beast==null||beast.size()<=i) return null;
		String set = beast.set(i, null);
		if(set!=null) {
			this.hasUnsavedData = true;
		}
		return set;
	}

	public String putMonster(Point p,int i,String s) {
		if(!this.monsters.containsKey(p)) this.monsters.put(p, new ArrayList<String>());
		ArrayList<String> beast = this.monsters.get(p);
		while(beast.size()<i+1) beast.add(null);
		String set = beast.set(i, s);
		if(set!=null&&!set.equals(s)) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	public String getMonster(Point p,int i) {
		ArrayList<String> beast = this.monsters.get(p);
		if(beast==null||beast.size()<=i) return null;
		return beast.get(i);
	}
	public String removeMonster(Point p,int i) {
		ArrayList<String> beast = this.monsters.get(p);
		if(beast==null||beast.size()<=i) return null;
		String set = beast.set(i, null);
		if(set!=null) {
			this.hasUnsavedData = true;
		}
		return set;
	}

	public String putThreatMonster(Point p,int i,String s) {
		if(!this.threatMonsters.containsKey(p)) this.threatMonsters.put(p, new ArrayList<String>());
		ArrayList<String> beast = this.threatMonsters.get(p);
		while(beast.size()<i+1) beast.add(null);
		String set = beast.set(i, s);
		if(set!=null&&!set.equals(s)) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	public String getThreatMonster(Point p,int i) {
		ArrayList<String> beast = this.threatMonsters.get(p);
		if(beast==null||beast.size()<=i) return null;
		return beast.get(i);
	}
	public String removeThreatMonster(Point p,int i) {
		ArrayList<String> beast = this.threatMonsters.get(p);
		if(beast==null||beast.size()<=i) return null;
		String set = beast.set(i, null);
		if(set!=null) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	
	public String putMission(Point p, int i,String s) {
		if(!this.missions.containsKey(p)) this.missions.put(p, new ArrayList<String>());
		ArrayList<String> encounters = this.missions.get(p);
		while(encounters.size()<i+1) encounters.add(null);
		String set = encounters.set(i, s);
		if(set!=null&&!set.equals(s)) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	public String getMission(Point p, int i) {
		ArrayList<String> encounters = this.missions.get(p);
		if(encounters==null||encounters.size()<=i) return null;
		return encounters.get(i);
	}
	public ArrayList<String> getMissions(Point p) {
		if(!this.missions.containsKey(p)) this.missions.put(p, new ArrayList<String>());
		return this.missions.get(p);
	}
	public String removeMission(Point p, int i) {
		ArrayList<String> encounters = this.missions.get(p);
		if(encounters==null||encounters.size()<=i) return null;
		String set = encounters.remove(i);
		if(set!=null) {
			this.hasUnsavedData = true;
		}
		return set;
	}

	public String putCityHistory(Pair<Point,Point> pair,String s) {
		return putCityHistory(pair.key1, pair.key2,s);
	}
	public String putCityHistory(Point p1,Point p2,String s) {
		Pair<Point,Point> pair = new Pair<Point,Point>(p1,p2);
		Pair<Point,Point> pair2 = new Pair<Point,Point>(p2,p1);
		if(!cityHistory.containsKey(pair)&&cityHistory.containsKey(pair2)) {
			pair = pair2;
		}
		String put = cityHistory.put(pair, s);
		if(put!=null&&!put.equals(s)) {
			this.hasUnsavedData = true;
		}
		return put;
	}
	public String getCityHistory(Pair<Point,Point> pair) {
		if(pair==null) return null;
		return getCityHistory(pair.key1, pair.key2);
	}
	public String getCityHistory(Point p1,Point p2) {
		Pair<Point,Point> pair = new Pair<Point,Point>(p1,p2);
		Pair<Point,Point> pair2 = new Pair<Point,Point>(p2,p1);
		if(!cityHistory.containsKey(pair)&&cityHistory.containsKey(pair2)) {
			pair = pair2;
		}
		return cityHistory.get(pair);
	}
	public String removeCityHistory(Pair<Point,Point> pair) {
		return removeCityHistory(pair.key1, pair.key2);
	}
	public String removeCityHistory(Point p1,Point p2) {
		Pair<Point,Point> pair = new Pair<Point,Point>(p1,p2);
		Pair<Point,Point> pair2 = new Pair<Point,Point>(p2,p1);
		if(!cityHistory.containsKey(pair)&&cityHistory.containsKey(pair2)) {
			pair = pair2;
		}
		String remove = cityHistory.remove(pair);
		if(remove!=null) {
			this.hasUnsavedData = true;
		}
		return remove;
	}
	
	public String putHistory(Point p,String s) {
		String put = history.put(p, s);
		if(put!=null&&!put.equals(s)) {
			this.hasUnsavedData = true;
		}
		return put;
	}
	public String getHistory(Point p) {
		return history.get(p);
	}
	public String removeHistory(Point p) {
		String remove = history.remove(p);
		if(remove!=null) {
			this.hasUnsavedData = true;
		}
		return remove;
	}
	
	public String putEvent(Point p,String s) {
		String put = events.put(p, s);
		if(put!=null&&!put.equals(s)) {
			this.hasUnsavedData = true;
		}
		return put;
	}
	public String getEvent(Point p) {
		return events.get(p);
	}
	public String removeEvent(Point p) {
		String remove = events.remove(p);
		if(remove!=null) {
			this.hasUnsavedData = true;
		}
		return remove;
	}


	public String putDistrict(Point p,int i,String s) {
		if(!this.districts.containsKey(p)) this.districts.put(p, new ArrayList<String>());
		ArrayList<String> districts = this.districts.get(p);
		while(districts.size()<i+1) districts.add(null);
		String set = districts.set(i, s);
		if(set!=null&&!set.equals(s)) {
			this.hasUnsavedData = true;
		}
		return set;
	}
	public String getDistrict(Point p, int i) {
		ArrayList<String> districts = this.districts.get(p);
		if(districts==null||districts.size()<=i) return null;
		return districts.get(i);
	}
	public String removeDistrict(Point p,int i) {
		ArrayList<String> districts = this.districts.get(p);
		if(districts==null||districts.size()<=i) return null;
		String set = districts.set(i, null);
		if(set!=null) {
			this.hasUnsavedData = true;
		}
		return set;
	}
}
