package controllers;

import java.awt.Point;
import java.util.regex.Matcher;

import data.DataModel;
import data.HexData;
import data.Indexible;
import data.Reference;
import data.altitude.AltitudeModel;
import data.biome.BiomeModel;
import data.biome.BiomeType;
import data.dungeon.DungeonModel;
import data.economy.EconomicModel;
import data.encounters.EncounterModel;
import data.location.DistrictType;
import data.location.LocationModel;
import data.magic.MagicModel;
import data.mission.MissionModel;
import data.monster.Monster;
import data.monster.MonsterModel;
import data.npc.NPCModel;
import data.population.NPCSpecies;
import data.population.PopulationModel;
import data.population.SettlementModel;
import data.precipitation.PrecipitationModel;
import data.threat.Threat;
import data.threat.ThreatModel;
import io.SaveRecord;
import names.LocationNameModel;
import names.wilderness.WildernessNameGenerator;
import util.Pair;
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
	private MonsterModel monsters;
	private MissionModel missions;

	public DataController(SaveRecord record) {
		this.record = record;
		this.grid=new AltitudeModel(record,this);
		this.precipitation = new PrecipitationModel(record,grid);
		this.population = new PopulationModel(record, this);
		this.magic = new MagicModel(record);
		this.biomes = new BiomeModel(record, grid, precipitation,population);
		this.economy = new EconomicModel(record,population,biomes,precipitation,grid);
		this.names = new LocationNameModel(record);
		this.pois = new LocationModel(record,this);
		this.settlements = new SettlementModel(record,this);
		this.npcs = new NPCModel(record, population,pois,settlements);
		this.threats = new ThreatModel(record,npcs);
		this.dungeons = new DungeonModel(record);
		this.encounters = new EncounterModel(record,population,this);
		this.monsters = new MonsterModel(this);
		this.missions = new MissionModel(record, population);
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
		case FACTION_NPC: return npcs;
		case PROPRIETOR: return npcs;
		case LOCATION: return pois;
		case DUNGEON: return dungeons;
		case D_ENCOUNTER: return encounters;
		case FACTION: return settlements;
		case FAITH: return settlements;
		case CITY: return settlements;
		case TOWN: return settlements;
		case MISSION: return missions;
		default: throw new IllegalArgumentException("Type not recognized: "+type.name());
		}
	}
	public String getText(HexData type,Point p,int i) {
		String recordText = getData(type, p, i);
		if(recordText==null) recordText = getDefaultText(type, p,i);
		return recordText;
	}
	public String getDefaultText(HexData type,Point p,int i) {
		Object value;
		switch(type) {
		case THREAT: {
			value = threats.getThreat(p);break;
		}
		case D_ENCOUNTER: value = "";break;
		case ENCOUNTER: value = "";break;
		case NPC:{
			value = npcs.getNPC(i,p);
			if(value==null) value="None";
			break;
		}
		case FACTION_NPC: {
			value =  npcs.getFactionNPC(i,p); 
			if(value==null) value="None";
			break;
		}
		case PROPRIETOR: {
			value = npcs.getProprietor(i,p);
			if(value==null) value="None";
			break;
		}
		case LOCATION: {
			value = pois.getPOI(i, p);
			if(value==null) value = "None";
			break;
		}
		case DUNGEON: value = dungeons.getDungeon(i, p);break;
		case FACTION: {
			Point capital = population.getAbsoluteFealty(p);
			value = settlements.getFaction(i, capital); 
			if(value==null) value="None";
			break;
		}
		case FAITH: {
			Point capital = population.getAbsoluteFealty(p);
			value = settlements.getFaith(i, capital); break;
		}
		case MINION: {
			Point center = threats.getCenter(p);
			if(i==0) value = threats.getFaction(this, center,null);
			else value = threats.getMinion(this, center, i-1,null);break;
		}
		case TOWN: {
			Point capital = population.getTown(p);
			NPCSpecies species = population.getMajoritySpecies(capital.x,capital.y);
			if(species==null) {
				System.err.println("getDefaultText null species "+record.normalizePOS(capital));
				value = null;break;
			}else {
				value =  names.getName(species.getCityNameGen(), capital);break;
			}
		}
		case CITY: {
			Point capital = population.getAbsoluteFealty(p);
			if(population.isCity(capital)) {
				value = settlements.getSettlement(capital);break;
			}else {
				value = "None";break;
			}
		}
		case DISTRICT: {
			Point capital = population.getAbsoluteFealty(p);
			if(population.isCity(capital)) {
				value = settlements.getDistrict(i, capital); break;
			}else {
				value = "None";break;
			}
		}
		case BIOME: {
			if(i==1) {
				Point region = biomes.getOcean(p);
				value = biomes.getRegionName(region)+" Land";
			}else if(population.isTown(p)) {
				NPCSpecies species = population.getMajoritySpecies(p.x, p.y);
				value = names.getName(species.getCityNameGen(), p);
			}else {
				Point region = biomes.getAbsoluteRegion(p);
				value = biomes.getRegionName(region)+" " + WildernessNameGenerator.getBiomeName(biomes.getBiome(p));
			}
			break;
		}
		case NOTE: value = "";break;
		case NONE: value = "";break;
		case THREAD: value = "";break;
		case CHARACTER: value = "";break;
		case BEAST: {
			Point region = monsters.getTerritoryRef(p,i);
			Pair<BiomeType,BiomeType> habitats = this.biomes.getHabitatBiomes(p);
			Monster monster = monsters.getWanderingBeast(region, i,habitats);
			if(monster!=null) value = monster;
			else value = "None";
			break;
		}
		case MONSTER: {
			Point region = monsters.getTerritoryRef(p,i);
			Pair<BiomeType,BiomeType> habitats = this.biomes.getHabitatBiomes(p);
			Monster monster = monsters.getWanderingMonster(region, i,habitats);
			if(monster!=null) value = monster;
			else value = "None";
			break;
		}
		case THREATMONSTER: {
			Pair<BiomeType,BiomeType> habitats = this.biomes.getHabitatBiomes(p);
			Monster monster = monsters.getThreatMonster(p, i,habitats);
			if(monster!=null) value = monster;
			else value = "None";
			break;
		}
		case MISSION: value = missions.getMission(p, i); break;
		case CITYHISTORY: {
			Point capital = population.getAbsoluteFealty(p);
			value = settlements.getRelationship(capital, i); break;
		}
		case HISTORY: {
			Point region = monsters.getTerritoryRef(p,i);
			value = monsters.getRuination(region); break;
		}
		case EVENT: {
			Point capital = population.getAbsoluteFealty(p);
			value = settlements.getEvent(capital); break;
		}
		default: value = getModel(type).getDefaultValue(p, i);
		}
		if(value==null) return null;
		else return value.toString();
	}
	public String getData(HexData type,Point p, int i) {
		switch(type) {
		case THREAT: {
			Point center = threats.getCenter(p);
			return record.getThreat(center);
		}
		case D_ENCOUNTER: //return record.getDungeonEncounter(p, i);
		case ENCOUNTER: return record.getEncounter(p, i);
		case NPC: return record.getNPC(p, i);
		case FACTION_NPC: {
			Point capital = population.getAbsoluteFealty(p);
			return record.getFactionNPC(capital, i);
		}
		case PROPRIETOR: return record.getProprietor(p, i);
		case LOCATION: return record.getLocation(p, i);
		case DUNGEON: return record.getDungeon(p, i);
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
		case DISTRICT: {
			Point capital = population.getAbsoluteFealty(p);
			return record.getDistrict(capital,i);
		}
		case TOWN: {
			Point capital = population.getTown(p);
			return record.getRegionName(capital);
		}
		case BIOME: {
			if(i==1) {
				Point region = biomes.getOcean(p);
				return record.getRegionName(region);
			}
			Point region = biomes.getAbsoluteRegion(p);
			if(grid.isOcean(p)) {
				return record.getOceanName(region);
			}else {
				return record.getBiomeName(region);
			}
		}
		case NOTE: return record.getNote(p);
		case NONE: return null;
		case THREAD: return record.getCampaignThread(i);
		case CHARACTER: return record.getCampaignCharacter(i).toString();
		case BEAST: {
			Point region = monsters.getTerritoryRef(p,i);
			return record.getBeast(region, i/4);
		}
		case MONSTER: {
			Point region = monsters.getTerritoryRef(p,i);
			return record.getMonster(region, i/4);
		}
		case THREATMONSTER: {
			return record.getThreatMonster(p, i);
		}
		case MISSION: return record.getMission(p,i);
		case CITYHISTORY: {
			Point capital = population.getAbsoluteFealty(p);
			Pair<Point, Point> cityPair = settlements.getCityPair(capital, i);
			if(cityPair!=null) return record.getCityHistory(cityPair);
			else return null;
		}
		case HISTORY: {
			Point region = monsters.getTerritoryRef(p,i);
			return record.getHistory(region);
		}
		case EVENT: {
			Point capital = population.getAbsoluteFealty(p);
			return record.getEvent(capital);
		}
		default: throw new IllegalArgumentException("Type not recognized: "+type.name());
		}
	}
	public String removeData(HexData type,Point p, int i) {
		switch(type) {
		case THREAT: {
			Point center = threats.getCenter(p);
			return record.removeThreat(center);
		}
		case D_ENCOUNTER: //return record.removeDungeonEncounter(p, i);
		case ENCOUNTER: return record.removeEncounter(p, i);
		case NPC: return record.removeNPC(p, i);
		case FACTION_NPC: {
			Point capital = population.getAbsoluteFealty(p);
			return record.removeFactionNPC(capital, i);
		}
		case PROPRIETOR: return record.removeProprietor(p, i);
		case LOCATION: return record.removeLocation(p, i);
		case DUNGEON: return record.removeDungeon(p, i);
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
		case DISTRICT: {
			Point capital = population.getAbsoluteFealty(p);
			return record.removeDistrict(capital,i);
		}
		case BIOME: {
			if(i==1) {
				Point region = biomes.getOcean(p);
				return record.removeRegionName(region);
			}
			Point region = biomes.getAbsoluteRegion(p);
			if(grid.isOcean(p)) {
				return record.removeOceanName(region);
			}else {
				return record.removeBiomeName(region);
			}
		}
		case NOTE: return record.removeNote(p);
		case NONE: return null;
		case THREAD: return record.removeCampaignThread(i);
		case CHARACTER: return record.removeCampaignCharacter(i).toString();
		case BEAST: {
			Point region = monsters.getTerritoryRef(p,i);
			return record.removeBeast(region,i/4);
		}
		case MONSTER: {
			Point region = monsters.getTerritoryRef(p,i);
			return record.removeMonster(region,i/4);
		}
		case THREATMONSTER: {
			return record.removeThreatMonster(p,i);
		}
		case MISSION: return record.removeMission(p, i);
		case CITYHISTORY: {
			Point capital = population.getAbsoluteFealty(p);
			Pair<Point, Point> cityPair = settlements.getCityPair(capital, i);
			if(cityPair!=null) return record.removeCityHistory(cityPair);
			else return null;
		}
		case HISTORY: {
			Point region = monsters.getTerritoryRef(p,i);
			return record.removeHistory(region);
		}
		case EVENT: {
			Point capital = population.getAbsoluteFealty(p);
			return record.removeEvent(capital);
		}
		default: throw new IllegalArgumentException("Type not recognized: "+type.name());
		}
	}
	public String putData(HexData type,Point p, int i,String s) {
		switch(type) {
		case THREAT: {
			Point center = threats.getCenter(p);
			return record.putThreat(center, s);
		}
		case D_ENCOUNTER: //return record.putDungeonEncounter(p, i, s);
		case ENCOUNTER: return record.putEncounter(p, i, s);
		case NPC: return record.putNPC(p, i, s);
		case FACTION_NPC:{
			Point capital = population.getAbsoluteFealty(p);
			return record.putFactionNPC(capital, i, s);
		}
		case PROPRIETOR: return record.putProprietor(p, i, s);
		case LOCATION: return record.putLocation(p, i, s);
		case DUNGEON: return record.putDungeon(p, i, s);
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
			//check if neighbor list is loaded before saving
			Pair<Point, Point> cityPair = settlements.getCityPair(capital, 0);
			if(cityPair!=null) return record.putCity(capital, s);
			else return null;
		}
		case DISTRICT: {
			Point capital = population.getAbsoluteFealty(p);
			return record.getDistrict(capital,i);
		}
		case BIOME: {
			if(i==1) {
				Point region = biomes.getOcean(p);
				return record.putRegionName(region,s);
			}
			Point region = biomes.getAbsoluteRegion(p);
			if(grid.isOcean(p)) {
				return record.putOceanName(region, s);
			}else {
				return record.putBiomeName(region, s);
			}
		}
		case NOTE: return record.putNote(p, s);
		case NONE: return null;
		case THREAD: return record.putCampaignThread(i,s);
		case CHARACTER: return record.putCampaignCharacter(i,s);
		case BEAST: {
			Point region = monsters.getTerritoryRef(p,i);
			return record.putBeast(region, i/4, s);
		}
		case MONSTER: {
			Point region = monsters.getTerritoryRef(p,i);
			return record.putMonster(region, i/4, s);
		}
		case THREATMONSTER: {
			return record.putThreatMonster(p, i, s);
		}
		case MISSION: return record.putMission(p, i, s);
		case CITYHISTORY: {
			Point capital = population.getAbsoluteFealty(p);
			Pair<Point, Point> cityPair = settlements.getCityPair(capital, i);
			if(cityPair!=null) return record.putCityHistory(cityPair,s);
			else return null;
		}
		case HISTORY: {
			Point region = monsters.getTerritoryRef(p,i);
			return record.putHistory(region,s);
		}
		case EVENT: {
			Point capital = population.getAbsoluteFealty(p);
			return record.putEvent(capital,s);
		}
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
		case D_ENCOUNTER: return encounters.getDungeonEncounter(record.getRandom()).toString();
		case ENCOUNTER: return encounters.getEncounter(p,record.getRandom(),ref).toString();
		case NPC: return npcs.getNPC(p,record.getRandom()).toString();
		case FACTION_NPC: return npcs.getFactionNPC(p,record.getRandom(),i).toString();
		case PROPRIETOR: return npcs.getProprietor(p,record.getRandom(),i).toString();
		case LOCATION: return pois.getPOI(record.getRandom(), p,population.isCity(p),i).toString();
		case DUNGEON: return dungeons.getDungeon(record.getRandom(), p).toString();
		case FACTION: return settlements.getFaction(record.getRandom(),p,i).toString(); 
		case FAITH: return settlements.getFaith(record.getRandom(),p,i).toString(); 
		case MINION:{
			if(i==0) return threats.getFaction(this, record.getRandom(), p, null).toString();
			else return threats.getMinion(this,record.getRandom(),p,null).toString(); 
		}
		case TOWN: {
			NPCSpecies species = population.getMajoritySpecies(p.x,p.y);
			return names.getName(species.getCityNameGen(), record.getRandom());
		}
		case CITY: return settlements.getSettlement(p,record.getRandom()).toString();
		case DISTRICT: return DistrictType.getDistrict(new Indexible(record.getRandom().nextInt())).toString(); 
		case BIOME: {
			if(i==1) {
				return biomes.getRegionName(p,record.getRandom())+" Land";
			}else if(population.isTown(p)) {
				NPCSpecies species = population.getMajoritySpecies(p.x, p.y);
				return names.getName(species.getCityNameGen(), record.getRandom());
			}else {
				return biomes.getRegionName(p,record.getRandom())+" " + WildernessNameGenerator.getBiomeName(biomes.getBiome(p));
			}
		}
		case NOTE: return "";
		case NONE: return "";
		case THREAD: return "";
		case CHARACTER: return "";
		case BEAST:{
			Point region = monsters.getTerritoryRef(p,i);
			Pair<BiomeType,BiomeType> biomes = this.biomes.getHabitatBiomes(p);
			Threat threat = this.getThreats().getThreat(p);
			Monster monster = monsters.getWanderingBeast(region,record.getRandom(),i,biomes,threat);
			return monster.toString(); 
		}
		case MONSTER:{
			Point region = monsters.getTerritoryRef(p,i);
			Pair<BiomeType,BiomeType> biomes = this.biomes.getHabitatBiomes(p);
			Threat threat = this.getThreats().getThreat(p);
			Monster monster = monsters.getWanderingMonster(region,record.getRandom(),i,biomes,threat);
			return monster.toString(); 
		}
		case THREATMONSTER:{
			Pair<BiomeType,BiomeType> biomes = this.biomes.getHabitatBiomes(p);
			Threat threat = this.getThreats().getThreat(p);
			Monster monster = monsters.getThreatMonster(record.getRandom(),i,biomes,threat);
			return monster.toString(); 
		}
		case MISSION: return missions.getMission(p, record.getRandom()).toString();
		case CITYHISTORY: {
			Point capital = population.getAbsoluteFealty(p);
			return settlements.getRelationship(capital, i, record.getRandom());
		}
		case HISTORY: return monsters.getRuination(record.getRandom()); 
		case EVENT: {
			Point capital = population.getAbsoluteFealty(p);
			return settlements.getEvent(capital);
		}
		default: throw new IllegalArgumentException("Type not recognized: "+type.name());
		}
	}

	public String getLinkText(String link) {
		Reference ref = new Reference(link);
		link = getLinkText(ref);
		return link;
	}
	public String getRawLinkText(HexData type, Point pos,int index) {
		String fullText = getText(type, pos, index);
		if(fullText==null) {
			//System.err.println("null link text: {"+type+":"+record.normalizePOS(pos)+","+index+"}");
			return null;
		}
		int firstLine = fullText.indexOf("\n");
		if(firstLine>-1&&firstLine<100) {
			return fullText.substring(0, firstLine);
		}else if(fullText.length()>=50) {
			return fullText.substring(0,50);
		}else return fullText;
	}

	public String getLinkText(Reference link) {
		if(link.getText()!=null) return link.getText();
		Point displayPos = link.getPoint();
		Point actualPos = Util.denormalizePos(displayPos, record.getZero());
		HexData type = HexData.get(link.getType().getText());
		String linkText = getRawLinkText(type, actualPos, link.getIndex());
		if(linkText==null) {
			//System.err.println("null link text: {"+type+":"+displayPos+","+index+"}");
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
	public String getLinkText(String tab, int x, int y, int index) {
		Point displayPos = new Point(x,y);
		Point actualPos = Util.denormalizePos(displayPos, record.getZero());
		HexData type = HexData.get(tab);
		String linkText = getRawLinkText(type, actualPos, index);
		if(linkText==null) {
			//System.err.println("null link text: {"+type+":"+displayPos+","+index+"}");
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

	public String getToolTipText(HexData type, Point displayPos, int index, Point source) {
		Point actualPos = Util.denormalizePos(displayPos, record.getZero());
		if(HexData.TOWN.equals(type)) return getTownInfotext(source,actualPos);
		String result = this.getText(type, actualPos, index);
		if(result!=null) {
			Matcher matcher;
			matcher = Reference.LINKDETECT.matcher(result);
			while(matcher.find()) {
				result = Util.replace(result,matcher.group(1), getLinkText(matcher.group(1)));
			}
		}
		return result;
	}

	public Point getOriginPoint(HexData type,Point p,int i) {
		switch(type) {
		case THREAT: 
		case MINION: return threats.getCenter(p);
		case CITYHISTORY:
		case EVENT:
		case FACTION_NPC: 
		case FACTION: 
		case FAITH: 
		case DISTRICT:
		case CITY: return population.getAbsoluteFealty(p);
		case TOWN: return population.getTown(p);
		case BIOME: return biomes.getAbsoluteRegion(p);
		case NONE: 
		case THREAD: 
		case CHARACTER: return null;
		case HISTORY:
		case BEAST:
		case MONSTER: return monsters.getTerritoryRef(p,i);
		case THREATMONSTER: 
		case D_ENCOUNTER: 
		case ENCOUNTER: 
		case NPC: 
		case PROPRIETOR: 
		case LOCATION: 
		case DUNGEON: 
		case NOTE: 
		case MISSION: return p;
		default: throw new IllegalArgumentException("Type not recognized: "+type.name());
		}
	}

	public String getTownInfotext(Point pos,Point town) {
		Reference ref = new Reference(HexData.TOWN, record.normalizePOS(town), 0);
		char ch = 'x';
		NPCSpecies species = population.getMajoritySpecies(town.x, town.y);
		if(species!=null && species.getIcons()!=null) {
			ch = species.getIcons().get(0).getCh();
		}
		int dist = economy.getTravelTime(pos, town)*20/24;
		String str = "("+Util.posString(town,record.getZero())+") "+ref.toString()+" "+ch+" distance:"+dist/20.0+" days";
		return str;
	}

	public String getBiomeText(Point pos) {
		String biome = biomes.getBiome(pos).getName();
		if(magic.isWeird(pos)) {
			Point region = biomes.getAbsoluteRegion(pos);
			biome = magic.getWeirdness(region).toLowerCase() + " "+biome;
		}
		return biome;
	}

	public String removeLinks(String string) {
		StringBuilder sb = new StringBuilder();
		Matcher matcher = Reference.PATTERN.matcher(string);
		int closebrace = -1;
		while(matcher.find()) {
			sb.append(string.substring(closebrace+1,matcher.start()));
			closebrace = matcher.end()-1;
			String link = string.substring(matcher.start(), matcher.end());
			sb.append(this.getLinkText(link));
		}
		sb.append(string.substring(closebrace+1));
		return sb.toString();
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

	public MonsterModel getMonsters() {
		return monsters;
	}

}
