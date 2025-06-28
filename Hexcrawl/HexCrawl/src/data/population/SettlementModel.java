package data.population;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import controllers.DataController;
import data.DataModel;
import data.Indexible;
import data.OpenSimplex2S;
import data.WeightedTable;
import data.magic.MagicModel;
import data.magic.MagicType;
import data.npc.Faction;
import data.npc.NPCModel;
import io.SaveRecord;
import names.FactionNameGenerator;
import names.FactionType;
import util.Util;
import view.InfoPanel;

public class SettlementModel extends DataModel{
	private static final int SEED_OFFSET = 10*Util.getOffsetX();
	private static final int FACTIONTABLES = 20;
	private static final int SETTLEMENTTABLES = 5;
	//private static final int TABLECOUNT = FACTIONTABLES+5;
	public static final String THEMES = "${animal},Aristocracy,Art,Bureaucracy,Castes,Catacombs,${city activity},${city event},Crime Families,Cruelty,${district index},${domain},"+
			"${faction index},Festivals,Feuds,Intrigue,${lower class building},Martial Law,Meritocracy,${job},Opulence,${physical element},Pilgrimages,Piracy,"+
			"Plutocracy,Poverty,Rituals,Slavery,Spices,Theocracy,Thievery,Trade,Tyranny,${upper class building},Wizardry,Xenophobia";
	private static WeightedTable<String> themes;
	public static final String EVENTS = "Assassination,Carnival,Conscription,Coronation,Coup,Cult Activity,Curfew,Discovery,Earthquake,${faction index} War,Fashion Trend,Fire,"+
			"Flood,Heavy Fog,Heavy Taxes,Holy Day,Hysteria,Inquisition,Insurrection,Invasion,Jailbreak,Mass Eviction,Mass Pardon,Negotiations,"+
			"Plague,Proclamation,Prohibition,Public Games,Refugees,Rioting,Roundup,Scandal,Serial Killer,Shortage,Tournament,Trial";
	private static WeightedTable<String> events;
	public static final String DISTRICTS = "Catacombs,${civilized npc},Construction,Crafts,Criminality,Culture,Dining,Education,Entertainment,Finance,Foreigners,Ghettoes,"+
			"Government,Graveyards,Green Space,Industrialization,Judgement,Livestock,Marketplace,Memorials,Military,Opulence,Pollution,Poverty,"+
			"Punishment,Religion,Science,Trade,Trash,${underworld npc},${upper class building},${lower class building},Vices,${wilderness npc},Wizardry,Wonders";
	private static WeightedTable<String> districts;
	public static final String UCBUILDINGS = "Academy,Alchemist,Archive,Art Dealer,Barber,Bookbinder,Bookseller,Castle,Clockmaker,Clothier,Courthouse,Furrier,"+
			"Gallery,Garden,Haberdashery,Jeweler,Law Office,Locksmith,Lounge,Manor,Museum,Observatory,Opera House,Park,"+
			"Physician,Printer,Public Baths,Restaurant,Salon,Stables,Taxidermist,Temple,Tobacconist,Townhouse,Winery,Zoo";
	private static WeightedTable<String> ucBuildings;
	public static final String LCBUILDINGS = "Apothacary,Asylum,Baker,Brewery,Butcher,Candlemaker,Catacombs,Cheesemaker,Criminal Den,Curiosity Shop,Dock,Fighting Pit,"+
			"Forge,Fortuneteller,Gambling Hall,Leatherworks,Marketplace,Mason,Mill,Moneylender,Orphanage,Outfitter,Prison,Sewers,"+
			"Shipyards,Shrine,Stockyard,Stonecarver,Tattooist,Tavern,Theater,Veterinarian,Warehouse,Watchtower,Weaver,Workshop";
	private static WeightedTable<String> lcBuildings;
	public static final String ACTIVITIES = "Abduct,Beg,Brawl,Burgle,Celebrate,Chase,Construct,Cook,Dance,Duel,${dungeon activity},Execute,"+
			"Extinguish,Extort,Follow,Gamble,Interrogate,Marry,${mission},Mourn,Party,Patrol,Perform,"+
			"Play,Preach,Process,Proclaim,Protest,Release,Repair,Riot,Rob,Search,Sell,${wilderness activity}";
	private static WeightedTable<String> activities;
	public static final String ROOMS = "arboretum,atrium,attic,aviary,ballroom,baths,bed chamber,cabinet,chapel,cloakroom,dining room,dressing room,"+
			"${dungeon room},garden,garret,greenhouse,junk room,kitchen,larder,library,map room,menagerie,mews,nursery,"+
			"pantry,parlor,privey,root cellar,saucery,scullery,smoking room,spicery,still room,study,trophy room,wardrobe";
	private static WeightedTable<String> rooms;
	public static final String STREETS = "arcade,awnings,balconies,barricades,bridge,canal,carriages,catwalks,${city activity},climbable walls,clotheslines,crowd,"+
			"dead end,dense fog,downpour,${dungeon activity},flooding,food stalls,fountain,gates,ladders,livestock,muddy,overgrown,"+
			"roof access,roof gardens,sewer access,sinkhole,slick,steep roofs,steep streets,steps,torn up street,vermin swarms,well,${wilderness activity}";
	private static WeightedTable<String> streets;
	public static final String FEATURES = "${animal} nests,balconies,basement access,brightly lit,broken furniture,broken glass,cabinets,carpeted floors,chandeliers,crawlspaces,drain pipes,dumbwaiters,"+
			"echoing marble,hanging chains,huge fireplace,narrow ledges,open windows,ornate weapons,overgrown,patrols,piles of trash,pillars,rotting ceiling,rotting floors,"+
			"rotting walls,screens,servant passages,sewer access,shadowy alcoves,skylights,spyholes,staircases,tall bookshelves,unlit,watchdogs,window drapes";
	private static WeightedTable<String> features;
	public static final String LEADER = "Respected,Feared,Easily manipulated,Illigitimate,Monsterous,Contested,Indecisive,Incompetent,Declining,Iron-willed,Puppet";
	private static WeightedTable<String> leader;
	public static final String GOVERNMENT = "Autocrat,Elected Official,Monarch,Plutocrats,Aristocracy,Theocracy,Tribes,Commune,Elected Council";
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


	private DataController controller;


	//NON_STATIC CODE

	public SettlementModel(SaveRecord record,DataController controller) {
		super(record);
		this.controller = controller;
	}

	public Settlement getSettlement(Point p) {
		float[] vals = new float[SETTLEMENTTABLES];
		for(int j=0;j<SETTLEMENTTABLES;j++) {
			vals[j] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+j), p.x, p.y);
		}
		Settlement result = new Settlement(vals);
		populateSettlementDetails(result);
		populateDistricts(p, result);
		return result;
	}
	public Object getSettlement(Point p,Random random) {
		int[] vals = new int[SETTLEMENTTABLES];
		for(int j=0;j<vals.length;j++) {
			vals[j] = random.nextInt();
		}
		Settlement result = new Settlement(vals);
		populateSettlementDetails(result);
		populateDistricts(p,random, result);
		return result;
	}
	private void populateSettlementDetails(Settlement result) {
		result.setTheme(getTheme(result));
		result.setLeadership(getLeadership(result));
		result.setEvent(getEvent(result));
	}
	private void populateDistricts(Point p, Settlement result) {
		MagicModel magic = controller.getMagic();
		for(int k=0;k<InfoPanel.DISTRICTCOUNT;k++) {
			String district = getDistrict(result);
			if(magic.isWeird(p,k)) district = magic.getAdjective(p, k)+" "+district;
			result.putDistrict(district);
		}
	}
	private void populateDistricts(Point p, Random random, Settlement result) {
		MagicModel magic = controller.getMagic();
		MagicType type = magic.getMagicType(p);
		for(int k=0;k<InfoPanel.DISTRICTCOUNT;k++) {
			String district = getDistrict(result);
			if(magic.isWeird(type,random.nextInt())) district = MagicModel.getAdjective(new Indexible(random.nextInt()))+" "+district;
			result.putDistrict(district);
		}
	}
	
	//FACTION
	private Faction getIndexedFaction(int i, Point p) {
		float[] vals = new float[FACTIONTABLES];
		for(int j=0;j<vals.length;j++) {
			vals[j] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+SETTLEMENTTABLES+j+i*FACTIONTABLES), p.x, p.y);
		}
		Faction result = new Faction(vals);
		return result;
	}
	private Faction getRandomIndexedFaction(Random random) {
		int[] ints = new int[FACTIONTABLES];
		for(int n=0;n<ints.length;n++) {
			ints[n] = random.nextInt();
		}
		Faction result = new Faction(ints);
		return result;
	}
	private void populateFactionDetails(Faction result, Point p) {
		boolean isFaith = result.getType().isFaith();
		if(isFaith) result.setDomain(NPCModel.getDomain(result));
		String name = FactionNameGenerator.getName(result.getType(),result);
		result.setName(name);
		result.setTrait(FactionNameGenerator.getTrait(result));
		String goal = formatGoal(FactionNameGenerator.getGoal(result),result,isFaith);
		result.setGoal(Util.formatTableResultPOS(goal,result,p,record.getZero()));
	}
	
	public Faction getFaction(int i,Point p) {
		Faction result = getIndexedFaction(i, p);
		FactionType type = FactionNameGenerator.getFaction(result);
		result.setType(type);
		populateFactionDetails(result, p);
		return result;
	}
	public Faction getFaction(Random random,Point p) {
		Faction result = getRandomIndexedFaction(random);
		FactionType type = FactionNameGenerator.getFaction(result);
		result.setType(type);
		populateFactionDetails(result, p);
		return result;
	}
	public Faction getFaith(int i,Point p) {
		Faction result = getIndexedFaction(i+InfoPanel.FACTIONCOUNT, p);
		FactionType type = FactionNameGenerator.getFaith(result);
		result.setType(type);
		populateFactionDetails(result, p);
		return result;
	}
	public Faction getFaith(Random random,Point p) {
		Faction result = getRandomIndexedFaction(random);
		FactionType type = FactionNameGenerator.getFaith(result);
		result.setType(type);
		populateFactionDetails(result, p);
		return result;
	}
	public Faction getFaction(int i,Point p,FactionType... types) {
		Faction result = getIndexedFaction(i, p);
		FactionType type = (FactionType) Util.getElementFromArray(types, result);
		result.setType(type);
		populateFactionDetails(result, p);
		return result;
	}
	public Faction getFaction(Random random,Point p,FactionType... types) {
		Faction result = getRandomIndexedFaction(random);
		FactionType type = (FactionType) Util.getElementFromArray(types, result);
		result.setType(type);
		populateFactionDetails(result, p);
		return result;
	}

	private String formatGoal(String goal, Faction result,boolean faith) {
		boolean edge = result.reduceTempId(10)<2;
		if(faith!=edge) {
			goal = Util.replace(goal, "${faction index}", "${faith index}");
		}
		return goal;
	}

	@Override
	public Settlement getDefaultValue(Point p, int i) {
		return getSettlement(p);
	}


	public String getDistrict(int i, Point capital) {
		ArrayList<String> districts = getSettlement(capital).getDistricts();
		if(i<districts.size()&&i>-1) return districts.get(i);
		return null;
	}



}
