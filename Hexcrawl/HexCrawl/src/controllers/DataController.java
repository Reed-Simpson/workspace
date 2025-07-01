package controllers;

import java.awt.Point;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.DataModel;
import data.HexData;
import data.Indexible;
import data.Reference;
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
import data.population.NPCSpecies;
import data.precipitation.PrecipitationModel;
import data.threat.ThreatModel;
import io.SaveRecord;
import names.LocationNameModel;
import util.Util;

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
		this.grid=new AltitudeModel(record,this);
		this.precipitation = new PrecipitationModel(record,grid);
		this.population = new PopulationModel(record, this);
		this.magic = new MagicModel(record);
		this.biomes = new BiomeModel(record, grid, precipitation,population);
		this.economy = new EconomicModel(record,population,biomes,precipitation,grid);
		this.names = new LocationNameModel(record);
		this.npcs = new NPCModel(record, population);
		this.threats = new ThreatModel(record,npcs);
		this.settlements = new SettlementModel(record,this);
		this.pois = new LocationModel(record,this);
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
		case FAITH: return settlements;
		case CITY: return settlements;
		case TOWN: return settlements;
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
			value = threats.getThreat(center).toString();break;
		}
		case ENCOUNTER: value = "";break;
		case NPC: {
			value =  npcs.getNPC(i,p).toString(); break;
		}
		case LOCATION: {
			if(i==0) value = getDefaultInnText(p);
			else value = pois.getPOI(i, p,population.isCity(p));break;
		}
		case DUNGEON: value = dungeons.getDungeon(i, p).toString();break;
		case D_ENCOUNTER: value = "";break;
		case FACTION: {
			Point capital = population.getAbsoluteFealty(p);
			value = settlements.getFaction(i, capital).toString(); break;
		}
		case FAITH: {
			Point capital = population.getAbsoluteFealty(p);
			value = settlements.getFaith(i, capital).toString(); break;
		}
		case MINION: {
			Point center = threats.getCenter(p);
			if(i==0) value = threats.getFaction(this, center,null).toString();
			else value = threats.getMinion(this, center, i-1,null).toString();break;
		}
		case DISTRICT: {
			Point capital = population.getAbsoluteFealty(p);
			value = settlements.getDistrict(i, capital); break;
		}
		case TOWN: {
			Point capital = population.getLocalFealty(p);
			NPCSpecies species = population.getMajoritySpecies(capital.x,capital.y);
			if(species==null) {
				System.err.println("null species "+record.normalizePOS(capital));
				value = null;break;
			}else {
				value =  names.getName(species.getCityNameGen(), capital);break;
			}
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
		case THREAD: value = "";break;
		case CHARACTER: value = "";break;
		default: value = getModel(type).getDefaultValue(p, i).toString();
		}
		if(value==null) return null;
		else return value.toString();
	}
	private String getDefaultInnText(Point pos) {
		if(grid.isWater(pos)||precipitation.isLake(pos)) {
			return "Inn: none";
		}else {
			return pois.getInnText(pos);
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
		case FAITH: {
			Point capital = population.getAbsoluteFealty(p);
			return record.getFaith(capital, i);
		}
		case MINION: {
			Point center = threats.getCenter(p);
			return record.getMinion(center,i);
		}
		case CITY: {
			Point capital = population.getAbsoluteFealty(p);
			return record.getCity(capital);
		}
		case DISTRICT: return null;
		case TOWN: {
			Point capital = population.getLocalFealty(p);
			return record.getRegionName(capital);
		}
		case BIOME: {
			Point region = biomes.getAbsoluteRegion(p);
			return record.getRegionName(region);
		}
		case NONE: return record.getNote(p);
		case THREAD: return record.getCampaignThread(i);
		case CHARACTER: return record.getCampaignCharacter(i).toString();
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
		case FAITH: {
			Point capital = population.getAbsoluteFealty(p);
			return record.removeFaith(capital, i);
		}
		case MINION: {
			Point center = threats.getCenter(p);
			return record.removeMinion(center,i);
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
		case THREAD: return record.removeCampaignThread(i);
		case CHARACTER: return record.removeCampaignCharacter(i).toString();
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
		case FAITH: {
			Point capital = population.getAbsoluteFealty(p);
			return record.putFaith(capital, i, s);
		}
		case MINION: {
			Point center = threats.getCenter(p);
			return record.putMinion(center, i, s);
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
		case THREAD: return record.putCampaignThread(i,s);
		case CHARACTER: return record.putCampaignCharacter(i,s);
		default: throw new IllegalArgumentException("Type not recognized: "+type.name());
		}
	}

	public void updateData(HexData type, String text, Point p, int index) {
		String defaultText = this.getDefaultText(type, p,index);
		boolean isDefault = text==null||"".equals(text)||text.equals(defaultText);
		if(isDefault) this.removeData(type, p,index);
		else this.putData(type, p,index, text);
	}
	public String genNewData(HexData type, Point p, int i,Reference ref) {
		switch(type) {
		case THREAT: return threats.getThreat(p,record.getRandom()).toString();
		case ENCOUNTER: return encounters.getEncounter(p,record.getRandom(),ref).toString();
		case NPC: return npcs.getNPC(p,record.getRandom()).toString();
		case LOCATION: {
			if(i==0) return pois.getInnText(record.getRandom(),p);
			else return pois.getPOI(record.getRandom(), p,population.isCity(p),i);
		}
		case DUNGEON: return dungeons.getDungeon(record.getRandom(), p).toString();
		case D_ENCOUNTER: return encounters.getDungeonEncounter(record.getRandom()).toString();
		case FACTION: return settlements.getFaction(record.getRandom(),p).toString(); 
		case FAITH: return settlements.getFaith(record.getRandom(),p).toString(); 
		case MINION:{
			if(i==0) return threats.getFaction(this, record.getRandom(), p, null).toString();
			else return threats.getMinion(this,record.getRandom(),p,null).toString(); 
		}
		case DISTRICT: return SettlementModel.getDistrict(new Indexible(record.getRandom().nextInt())); 
		case TOWN: {
			NPCSpecies species = population.getMajoritySpecies(p.x,p.y);
			return names.getName(species.getCityNameGen(), record.getRandom());
		}
		case CITY: return settlements.getSettlement(p,record.getRandom()).toString();
		case BIOME: return biomes.getRegionName(record.getRandom());
		case NONE: return "";
		case THREAD: return "";
		case CHARACTER: return "";
		default: throw new IllegalArgumentException("Type not recognized: "+type.name());
		}
	}

	public String getLinkText(String link) {
		Matcher matcher = Reference.PATTERN.matcher(link);
		if(matcher.matches()) {
			link = getLinkText(
					matcher.group(1),
					Integer.valueOf(matcher.group(2)),
					Integer.valueOf(matcher.group(3)),
					Integer.valueOf(matcher.group(4))-1);
		}
		return link;
	}
	public String getLinkText(HexData type, Point pos,int index) {
		String fullText = getText(type, pos, index);
		if(fullText==null) {
			System.err.println("null link text: {"+type+":"+record.normalizePOS(pos)+","+index+"}");
			return null;
		}
		int firstLine = fullText.indexOf("\n");
		if(firstLine>-1&&firstLine<100) {
			return fullText.substring(0, firstLine);
		}else if(fullText.length()>=50) {
			return fullText.substring(0,50);
		}else return fullText;
	}
	public String getLinkText(String tab, int x, int y, int index) {
		Point displayPos = new Point(x,y);
		Point actualPos = Util.denormalizePos(displayPos, record.getZero());
		HexData type = HexData.get(tab);
		String linkText = getLinkText(type, actualPos, index);
		if(linkText==null) {
			System.err.println("null link text: {"+type+":"+displayPos+","+index+"}");
			return null;
		}
		Matcher matcher = Reference.PATTERN.matcher(linkText);
		if(matcher.find()) {
			linkText = linkText.substring(0,matcher.start())+
					getLinkText(linkText.substring(matcher.start(), matcher.end()))+
					linkText.substring(matcher.end());
		}
		return linkText;
	}

	public String getToolTipText(HexData type, int x, int y, int index) {
		Point displayPos = new Point(x,y);
		Point actualPos = Util.denormalizePos(displayPos, record.getZero());
		String result = this.getText(type, actualPos, index);
		
		Matcher matcher;
		matcher = Pattern.compile("(\\{\\w+\\:\\d+,\\d+,\\d+\\})").matcher(result);
		while(matcher.find()) {
			result = Util.replace(result,matcher.group(1), getLinkText(matcher.group(1)));
		}
		return result;
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
