package controllers;

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

	public DataController(SaveRecord record) {
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

}
