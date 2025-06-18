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
		case DISTRICT: return settlements;
		default: throw new IllegalArgumentException("Type not recognized: "+type.name());
		}
	}
	public String getDefaultText(HexData type,Point p,int i) {
		String value;
		switch(type) {
		case LOCATION: value = pois.getPOI(i, p,population.isCity(p)); break;
		case FACTION: {
			Point capital = population.getAbsoluteFealty(p);
			value = settlements.getFaction(i, capital).toString(); break;
		}
		case DISTRICT: {
			Point capital = population.getAbsoluteFealty(p);
			value = settlements.getDistrict(i, capital); break;
		}
		default: value = getModel(type).getDefaultValue(p, i).toString();
		}
		System.out.println(value);
		return value.toString();
	}
	public boolean isDefaultText(HexData type,String text,Point p,int index) {
		String defaultText = this.getDefaultText(type, p,index);
		System.out.println(type.getText()+" text check: "+text.equals(defaultText));
		return text==null||"".equals(text)||text.equals(defaultText);
	}
	public String removeData(HexData type,Point p, int i) {
		switch(type) {
		case THREAT: return record.removeThreat(p);
		case ENCOUNTER: return record.removeEncounter(p, i);
		case NPC: return record.removeNPC(p, i);
		case LOCATION: return record.removeLocation(p, i);
		case DUNGEON: return record.removeDungeon(p, i);
		case D_ENCOUNTER: return record.removeDungeonEncounter(p, i);
		case FACTION: return record.removeFaction(p, i);
		case DISTRICT: return record.removeCity(p);
		case BIOME: return record.removeRegionName(p);
		case NONE: return record.removeNote(p);
		default: throw new IllegalArgumentException("Type not recognized: "+type.name());
		}
	}
	public String putData(HexData type,Point p, int i,String s) {
		switch(type) {
		case THREAT: return record.putThreat(p, s);
		case ENCOUNTER: return record.putEncounter(p, i, s);
		case NPC: return record.putNPC(p, i, s);
		case LOCATION: return record.putLocation(p, i, s);
		case DUNGEON: return record.putDungeon(p, i, s);
		case D_ENCOUNTER: return record.putDungeonEncounter(p, i, s);
		case FACTION: return record.putFaction(p, i, s);
		case DISTRICT: return record.putCity(p, s);
		case BIOME: return record.putRegionName(p, s);
		case NONE: return record.putNote(p, s);
		default: throw new IllegalArgumentException("Type not recognized: "+type.name());
		}
	}

	public void updateData(HexData type, String text, Point p, int index) {
		if(this.isDefaultText(type, text, p, index)) this.removeData(type, p,index);
		else this.putData(type, p,index, text);
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
