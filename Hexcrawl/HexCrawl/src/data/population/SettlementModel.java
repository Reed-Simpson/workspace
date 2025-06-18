package data.population;

import java.awt.Point;

import data.DataModel;
import data.Indexible;
import data.OpenSimplex2S;
import data.Util;
import data.WeightedTable;
import data.npc.Faction;
import io.SaveRecord;
import names.FactionNameGenerator;
import view.InfoPanel;

public class SettlementModel extends DataModel{
	private static final int SEED_OFFSET = 10*Util.getOffsetX();
	private static final int FACTIONTABLES = 20;
	private static final int SETTLEMENTTABLES = 5;
	//private static final int TABLECOUNT = FACTIONTABLES+5;
	private static final String THEMES = "${animal},Aristocracy,Art,Bureaucracy,Castes,Catacombs,${city activity},${city event},Crime Families,Cruelty,${district index},${domain},"+
			"${faction index},Festivals,Feuds,Intrigue,${lower class building},Martial Law,Meritocracy,${job},Opulence,${physical element},Pilgrimages,Piracy,"+
			"Plutocracy,Poverty,Rituals,Slavery,Spices,Theocracy,Thievery,Trade,Tyranny,${upper class building},Wizardry,Xenophobia";
	private static WeightedTable<String> themes;
	private static final String EVENTS = "Assassination,Carnival,Conscription,Coronation,Coup,Cult Activity,Curfew,Discovery,Earthquake,${faction index} War,Fashion Trend,Fire,"+
			"Flood,Heavy Fog,Heavy Taxes,Holy Day,Hysteria,Inquisition,Insurrection,Invasion,Jailbreak,Mass Eviction,Mass Pardon,Negotiations,"+
			"Plague,Proclamation,Prohibition,Public Games,Refugees,Rioting,Roundup,Scandal,Serial Killer,Shortage,Tournament,Trial";
	private static WeightedTable<String> events;
	private static final String DISTRICTS = "Catacombs,${civilized npc},Construction,Crafts,Criminality,Culture,Dining,Education,Entertainment,Finance,Foreigners,Ghettoes,"+
			"Government,Graveyards,Green Space,Industrialization,Judgement,Livestock,Marketplace,Memorials,Military,Opulence,Pollution,Poverty,"+
			"Punishment,Religion,Science,Trade,Trash,${underworld npc},${upper class building},${lower class building},Vices,${wilderness npc},Wizardry,Wonders";
	private static WeightedTable<String> districts;
	private static final String UCBUILDINGS = "Academy,Alchemist,Archive,Art Dealer,Barber,Bookbinder,Bookseller,Castle,Clockmaker,Clothier,Courthouse,Furrier,"+
			"Gallery,Garden,Haberdashery,Jeweler,Law Office,Locksmith,Lounge,Manor,Museum,Observatory,Opera House,Park,"+
			"Physician,Printer,Public Baths,Restaurant,Salon,Stables,Taxidermist,Temple,Tobacconist,Townhouse,Winery,Zoo";
	private static WeightedTable<String> ucBuildings;
	private static final String LCBUILDINGS = "Apothacary,Asylum,Baker,Brewery,Butcher,Candlemaker,Catacombs,Cheesemaker,Criminal Den,Curiosity Shop,Dock,Fighting Pit,"+
			"Forge,Fortuneteller,Gambling Hall,Leatherworks,Marketplace,Mason,Mill,Moneylender,Orphanage,Outfitter,Prison,Sewers,"+
			"Shipyards,Shrine,Stockyard,Stonecarver,Tattooist,Tavern,Theater,Veterinarian,Warehouse,Watchtower,Weaver,Workshop";
	private static WeightedTable<String> lcBuildings;
	private static final String ACTIVITIES = "Abduct,Beg,Brawl,Burgle,Celebrate,Chase,Construct,Cook,Dance,Duel,${dungeon activity},Execute,"+
			"Extinguish,Extort,Follow,Gamble,Interrogate,Marry,${mission},Mourn,Party,Patrol,Perform,"+
			"Play,Preach,Process,Proclaim,Protest,Release,Repair,Riot,Rob,Search,Sell,${wilderness activity}";
	private static WeightedTable<String> activities;
	private static final String ROOMS = "arboretum,atrium,attic,aviary,ballroom,baths,bed chamber,cabinet,chapel,cloakroom,dining room,dressing room,"+
			"${dungeon room},garden,garret,greenhouse,junk room,kitchen,larder,library,map room,menagerie,mews,nursery,"+
			"pantry,parlor,privey,root cellar,saucery,scullery,smoking room,spicery,still room,study,trophy room,wardrobe";
	private static WeightedTable<String> rooms;
	private static final String STREETS = "arcade,awnings,balconies,barricades,bridge,canal,carriages,catwalks,${city activity},climbable walls,clotheslines,crowd,"+
			"dead end,dense fog,downpour,${dungeon activity},flooding,food stalls,fountain,gates,ladders,livestock,muddy,overgrown,"+
			"roof access,roof gardens,sewer access,sinkhole,slick,steep roofs,steep streets,steps,torn up street,vermin swarms,well,${wilderness activity}";
	private static WeightedTable<String> streets;
	private static final String FEATURES = "${animal} nests,balconies,basement access,brightly lit,broken furniture,broken glass,cabinets,carpeted floors,chandeliers,crawlspaces,drain pipes,dumbwaiters,"+
			"echoing marble,hanging chains,huge fireplace,narrow ledges,open windows,ornate weapons,overgrown,patrols,piles of trash,pillars,rotting ceiling,rotting floors,"+
			"rotting walls,screens,servant passages,sewer access,shadowy alcoves,skylights,spyholes,staircases,tall bookshelves,unlit,watchdogs,window drapes";
	private static WeightedTable<String> features;
	private static final String LEADER = "Respected,Feared,Easily manipulated,Illigitimate,Monsterous,Contested,Indecisive,Incompetent,Declining,Iron-willed,Puppet";
	private static WeightedTable<String> leader;
	private static final String GOVERNMENT = "Autocrat,Elected Official,Monarch,Plutocrats,Aristocracy,Theocracy,Tribes,Commune,Elected Council";
	private static WeightedTable<String> government;



	private static void populateAllTables() {
		themes = new WeightedTable<String>();
		populate(themes,THEMES,",");
		events = new WeightedTable<String>();
		populate(events,EVENTS,",");
		districts = new WeightedTable<String>();
		populate(districts,DISTRICTS,",");
		ucBuildings = new WeightedTable<String>();
		populate(ucBuildings,UCBUILDINGS,",");
		lcBuildings = new WeightedTable<String>();
		populate(lcBuildings,LCBUILDINGS,",");
		activities = new WeightedTable<String>();
		populate(activities,ACTIVITIES,",");
		rooms = new WeightedTable<String>();
		populate(rooms,ROOMS,",");
		streets = new WeightedTable<String>();
		populate(streets,STREETS,",");
		features = new WeightedTable<String>();
		populate(features,FEATURES,",");
		leader = new WeightedTable<String>();
		populate(leader,LEADER,",");
		government = new WeightedTable<String>();
		populate(government,GOVERNMENT,",");
	}
	

	public static String getTheme(Indexible obj) {
		if(themes==null) populateAllTables();
		return Util.formatTableResult(themes.getByWeight(obj),obj);
	}
	public static String getEvent(Indexible obj) {
		if(events==null) populateAllTables();
		return Util.formatTableResult(events.getByWeight(obj),obj);
	}
	public static String getDistrict(Indexible obj) {
		if(districts==null) populateAllTables();
		return Util.formatTableResult(districts.getByWeight(obj),obj);
	}
	public static String getBuilding(Indexible obj) {
		if(ucBuildings==null) populateAllTables();
		if(obj.reduceTempId(2)==0) return ucBuildings.getByWeight(obj);
		else return lcBuildings.getByWeight(obj);
	}
	public static String getUCBuilding(Indexible obj) {
		if(ucBuildings==null) populateAllTables();
		return ucBuildings.getByWeight(obj);
	}
	public static String getLCBuilding(Indexible obj) {
		if(lcBuildings==null) populateAllTables();
		return lcBuildings.getByWeight(obj);
	}
	public static String getActivity(Indexible obj) {
		if(activities==null) populateAllTables();
		return Util.formatTableResult(activities.getByWeight(obj),obj);
	}
	public static String getRoom(Indexible obj) {
		if(rooms==null) populateAllTables();
		return Util.formatTableResult(rooms.getByWeight(obj),obj);
	}
	public static String getStreet(Indexible obj) {
		if(streets==null) populateAllTables();
		return Util.formatTableResult(streets.getByWeight(obj),obj);
	}
	public static String getFeature(Indexible obj) {
		if(features==null) populateAllTables();
		return Util.formatTableResult(features.getByWeight(obj),obj);
	}
	public static String getDiscovery(Indexible obj) {
		if(features==null) populateAllTables();
		return getFeature(obj);
	}
	public static String getLeadership(Indexible obj) {
		if(leader==null) populateAllTables();
		return leader.getByWeight(obj)+" "+government.getByWeight(obj).toLowerCase();
	}


	//NON_STATIC CODE

	public SettlementModel(SaveRecord record) {
		super(record);
	}
	
	public Settlement getSettlement(Point p) {
		float[] vals = new float[SETTLEMENTTABLES];
		for(int j=0;j<SETTLEMENTTABLES;j++) {
			vals[j] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+j), p.x, p.y);
		}
		Settlement result = new Settlement(vals);
		result.setTheme(getTheme(result));
		result.setLeadership(getLeadership(result));
		result.setEvent(getEvent(result));
		for(int k=0;k<InfoPanel.DISTRICTCOUNT;k++) {
			result.putDistrict(getDistrict(result));
		}
		return result;
	}
	public Faction getFaction(int i,Point p) {
		float[] vals = new float[FACTIONTABLES];
		for(int j=0;j<FACTIONTABLES;j++) {
			vals[j] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+SETTLEMENTTABLES+j+i*FACTIONTABLES), p.x, p.y);
		}
		Faction result = new Faction(vals);
		String type = FactionNameGenerator.getFaction(result);
		String name = FactionNameGenerator.getName(type,result);
		String trait = FactionNameGenerator.getTrait(result);
		String goal = FactionNameGenerator.getGoal(result);
		result.setType(type);
		result.setName(name);
		result.setTrait(trait);
		result.setGoal(goal);
		return result;
	}
	@Override
	public Settlement getDefaultValue(Point p, int i) {
		return getSettlement(p);
	}

}
