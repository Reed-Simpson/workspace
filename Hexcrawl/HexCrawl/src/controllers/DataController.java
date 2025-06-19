package controllers;

import java.awt.Point;

import data.DataModel;
import data.HexData;
import data.altitude.AltitudeModel;
import data.biome.BiomeModel;
import data.dungeon.DungeonModel;
import data.economy.EconomicModel;
import data.encounters.EncounterModel;
import data.location.LocationModel;
import data.magic.MagicModel;
import data.npc.NPCModel;
import data.population.PopulationModel;
import data.population.SettlementModel;
import data.precipitation.PrecipitationModel;
import data.threat.ThreatModel;
import io.SaveRecord;
import names.LocationNameModel;

public class DataController {
	
	private AltitudeModel grid;
	private PrecipitationModel precipitation;
	private PopulationModel population;
	private MagicModel magic;
	private BiomeModel biomes;
	private EconomicModel economy;
	private LocationNameModel names;
	private NPCModel npcs;
	private ThreatModel threats;
	private SettlementModel settlements;
	private LocationModel pois;
	private DungeonModel dungeons;
	private EncounterModel encounters;
	private SaveRecord record;

	public DataController(SaveRecord record) {
		this.record = record;
		this.grid=new AltitudeModel(record);
		this.precipitation = new PrecipitationModel(record,grid);
		this.population = new PopulationModel(record, grid, precipitation);
		this.magic = new MagicModel(record);
		this.biomes = new BiomeModel(record, grid, precipitation,population);
		this.economy = new EconomicModel(record,population,biomes,precipitation,grid);
		this.names = new LocationNameModel(record);
		this.npcs = new NPCModel(record, population);
		this.threats = new ThreatModel(record,npcs);
		this.settlements = new SettlementModel(record);
		this.pois = new LocationModel(record);
		this.dungeons = new DungeonModel(record);
		this.encounters = new EncounterModel(record,population);
	}
	
	public DataModel getModel(HexData type) {
		switch(type) {
		case ALTITUDE: return grid;
		case PRECIPITATION: return precipitation;
		case BIOME: return biomes;
		case POPULATION: return population;
		case ECONOMY: return economy;
		//case TEMPERATURE: return temperature;
		case MAGIC: return magic;
		case THREAT: return threats;

		case ENCOUNTER: return encounters;
		case NPC: return npcs;
		case LOCATION: return pois;
		case DUNGEON: return dungeons;
		case D_ENCOUNTER: return encounters;
		case FACTION: return settlements;
		case CITY: return settlements;
		default: throw new IllegalArgumentException("Type not recognized: "+type.name());
		}
	}
	public String getText(HexData type,Point p,int i) {
		String recordText = getData(type, p, i);
		if(recordText==null) recordText = getDefaultText(type, p,i);
		return recordText;
	}
	public String getDefaultText(HexData type,Point p,int i) {
		String value;
		switch(type) {
		case THREAT: {
			Point center = threats.getCenter(p);
			value = threats.getThreat(center).toString();
			break;
		}
		case ENCOUNTER: value = encounters.getEncounter(i, p).toString();
		case NPC: value =  npcs.getNPC(i,p).toString();
		case LOCATION: {
			if(i==0) value = getDefaultInnText(p);
			else value = pois.getPOI(i, p,population.isCity(p));
			break;
		}
		case DUNGEON: value = dungeons.getDungeon(i, p).toString();break;
		case D_ENCOUNTER: value = encounters.getDungeonEncounter(i, p).toString();break;
		case FACTION: {
			Point capital = population.getAbsoluteFealty(p);
			value = settlements.getFaction(i, capital).toString(); break;
		}
		case DISTRICT: {
			Point capital = population.getAbsoluteFealty(p);
			value = settlements.getDistrict(i, capital); break;
		}
		case CITY: {
			Point capital = population.getAbsoluteFealty(p);
			if(population.isCity(capital)) {
				value = settlements.getSettlement(capital).toString();break;
			}else {
				value = "None";break;
			}
		}
		case BIOME: {
			Point region = biomes.getAbsoluteRegion(p);
			value =  biomes.getRegionName(region);break;
		}
		case NONE: value = "";break;
		default: value = getModel(type).getDefaultValue(p, i).toString();
		}
		return value.toString();
	}
	private String getDefaultInnText(Point pos) {
		if(grid.isWater(pos)||precipitation.isLake(pos)) {
			return "Inn: none";
		}else {
			return names.getInnText(pos);
		}
	}
	public String getData(HexData type,Point p, int i) {
		switch(type) {
		case THREAT: {
			Point center = threats.getCenter(p);
			return record.getThreat(center);
		}
		case ENCOUNTER: return record.getEncounter(p, i);
		case NPC: return record.getNPC(p, i);
		case LOCATION: return record.getLocation(p, i);
		case DUNGEON: return record.getDungeon(p, i);
		case D_ENCOUNTER: return record.getDungeonEncounter(p, i);
		case FACTION: {
			Point capital = population.getAbsoluteFealty(p);
			return record.getFaction(capital, i);
		}
		case CITY: {
			Point capital = population.getAbsoluteFealty(p);
			return record.getCity(capital);
		}
		case BIOME: {
			Point region = biomes.getAbsoluteRegion(p);
			return record.getRegionName(region);
		}
		case NONE: return record.getNote(p);
		default: throw new IllegalArgumentException("Type not recognized: "+type.name());
		}
	}
	public String removeData(HexData type,Point p, int i) {
		switch(type) {
		case THREAT: {
			Point center = threats.getCenter(p);
			return record.removeThreat(center);
		}
		case ENCOUNTER: return record.removeEncounter(p, i);
		case NPC: return record.removeNPC(p, i);
		case LOCATION: return record.removeLocation(p, i);
		case DUNGEON: return record.removeDungeon(p, i);
		case D_ENCOUNTER: return record.removeDungeonEncounter(p, i);
		case FACTION: {
			Point capital = population.getAbsoluteFealty(p);
			return record.removeFaction(capital, i);
		}
		case CITY: {
			Point capital = population.getAbsoluteFealty(p);
			return record.removeCity(capital);
		}
		case BIOME: {
			Point region = biomes.getAbsoluteRegion(p);
			return record.removeRegionName(region);
		}
		case NONE: return record.removeNote(p);
		default: throw new IllegalArgumentException("Type not recognized: "+type.name());
		}
	}
	public String putData(HexData type,Point p, int i,String s) {
		switch(type) {
		case THREAT: {
			Point center = threats.getCenter(p);
			return record.putThreat(center, s);
		}
		case ENCOUNTER: return record.putEncounter(p, i, s);
		case NPC: return record.putNPC(p, i, s);
		case LOCATION: return record.putLocation(p, i, s);
		case DUNGEON: return record.putDungeon(p, i, s);
		case D_ENCOUNTER: return record.putDungeonEncounter(p, i, s);
		case FACTION: {
			Point capital = population.getAbsoluteFealty(p);
			return record.putFaction(capital, i, s);
		}
		case CITY: {
			Point capital = population.getAbsoluteFealty(p);
			return record.putCity(capital, s);
		}
		case BIOME: {
			Point region = biomes.getAbsoluteRegion(p);
			return record.putRegionName(region, s);
		}
		case NONE: return record.putNote(p, s);
		default: throw new IllegalArgumentException("Type not recognized: "+type.name());
		}
	}

	public void updateData(HexData type, String text, Point p, int index) {
		String defaultText = this.getDefaultText(type, p,index);
		System.out.println(type.getText()+" text check: "+text.equals(defaultText));
		boolean isDefault = text==null||"".equals(text)||text.equals(defaultText);
		if(isDefault) this.removeData(type, p,index);
		else this.putData(type, p,index, text);
	}
	public String getLinkText(HexData type, Point pos,int index) {
		String fullText = getText(type, pos, index);
		int firstLine = fullText.indexOf("\r\n");
		if(firstLine>-1&&firstLine<50) {
			return fullText.substring(0, firstLine);
		}else {
			return fullText.substring(0,30);
		}
	}
	public String getLinkText(String tab, int x, int y, int index) {
		Point p = new Point(x,y);
		HexData type = HexData.get(tab);
		return getLinkText(type, p, index);
	}


	public AltitudeModel getGrid() {
		return this.grid;
	}
	public PrecipitationModel getPrecipitation() {
		return this.precipitation;
	}
	public BiomeModel getBiomes() {
		return this.biomes;
	}
	public PopulationModel getPopulation() {
		return this.population;
	}
	public MagicModel getMagic() {
		return this.magic;
	}
	public LocationNameModel getNames() {
		return this.names;
	}
	public ThreatModel getThreats() {
		return this.threats;
	}
	public EncounterModel getEncounters() {
		return this.encounters;
	}
	public NPCModel getNpcs() {
		return npcs;
	}
	public SettlementModel getSettlements() {
		return settlements;
	}
	public LocationModel getPois() {
		return pois;
	}
	public EconomicModel getEconomy() {
		return economy;
	}
	public DungeonModel getDungeon() {
		return dungeons;
	}

	public SaveRecord getRecord() {
		return record;
	}
}
