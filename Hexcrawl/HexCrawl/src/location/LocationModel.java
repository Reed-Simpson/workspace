package location;

import java.awt.Point;

import general.Indexible;
import general.OpenSimplex2S;
import general.Util;
import general.WeightedTable;
import io.SaveRecord;
import settlement.SettlementModel;

public class LocationModel {
	private static final int SEED_OFFSET = 11*Util.getOffsetX();
	private static final int TABLECOUNT = 5;
	private static final String BIOMES = "Ash,Badlands,Bay,Beach,Delta,Desert,Dunes,Dustbowl,Fjords,Flood,"+
			"Forest,Glacier,Heath,Highland,Hills,Icefield,Jungle,Lowland,Mesas,Moor,Mountains,Plains"+
			"Rainforest,Riverlands,Saltpan,Savanna,Steppe,Taiga,Thickets,Tundra,Volcanic,Wetlands,Woods";
	private static WeightedTable<String> biomes;
	private static final String LANDMARKS = "Bog,Boulder,Butte,Cave,Cliff,Crag,Crater,Creek,Crossing,Ditch,Field,Forest,"+
			"Grove,Hill,Hollow,Hotspring,Lair,Lake,Lakebed,Marsh,Mesa,Moor,Pass,Peak,Pit,"+
			"Pond,Rapids,Ravine,Ridge,Rise,River,Rockslide,Spring,Swamp,Thicket,Valley,Fall";
	private static WeightedTable<String> landmarks;
	private static final String STRUCTURES = "Altar,Aqueduct,Bandit Camp,Battlefield,Bonfire,Bridge,Cairn,Crossroads,Crypt,Dam,Dungeon,Farm,"+
			"Ford,Fortress,Gallows,Graveyard,Hedge,Hunter Camp,Inn,Lumber Camp,Mine,Monastery,Monument,Orchard,"+
			"Outpost,Pasture,Ruin,Seclusion,Shack,Shrine,Standing Stone,Temple,Village,Wall,Watchtower,Waystone";
	private static WeightedTable<String> structures;
	private static final String DISCOVERIES = "Blood Stains,Bones,Broken Weapons,Burrow,${city activity},${civilized npc},Cut Ropes,Dead Animal,${dungeon activity},Food Scraps,Grave Marker,Human Corpse,"+
			"${item},Lost ${job},${spell},Map,Message,Migration,${mutation},Nest,Portal,Resources,Rift,Strange Plant,"+
			"Stunned ${job},Supplies,Torn Flag,Tracks,Trap,Treasure Cache,${underworld npc},${wilderness activity},${landmark},${structure},${wilderness npc},Wizard Duel";
	private static WeightedTable<String> discoveries;
	private static final String WILDACTIVITIES = "Ambush,Argue,Birth,Build,Bury,Capture,${city activity},Convene,Demolish,Die,Duel,${dungeon activity},"+
			"Eat,Excavate,Feast,Felling,Fish,Flee,Forage,Hunt,March,Raid,Rescue,Rest,"+
			"Sacrifice,Scout,Sing,Skin,Skirmish,Slay,Sleep,Swim,Track,Trap,Wander,Worship";
	private static WeightedTable<String> wildactivities;
	private static final String HAZARDS = "Avalanche,Blizzard,Brushfire,Cloudburst,Cyclone,Dense Fog,Downpour,Drizzle,Dust Storm,Earthquake,Eruption,Flooding,"+
			"Forest Fire,Hail,Heat Wave,Hurricane,Ice Storm,Light Mist,Locust Swarm,Magma Flow,Meteor Strike,Monsoon,Mudflow,Mudslide,"+
			"Predator,Quicksand,Rain of Frogs,Rockslide,Sandstorm,Sleet,Snow,Stampede,Thunderstorm,Tsunami,Whirlpool,Windstorm";
	private static WeightedTable<String> hazards;
	private static final String EDIBLE = "Acorns,Apples,Asparagus,Blackberries,Blueberries,Carrots,Cattail,Cherries,Chickweed,Chicory,Dandelion,"+
			"Dead-nettle,Elderberries,Fireweed,Gooseberries,Hazelnuts,Henbit,Hickory Nuts,Honeysuckle,Leeks,Milk Thistle,Mint,Mulberries,"+
			"Mushrooms,Mustard,Onion,Pecans,Persimmons,Raspberries,Strawberries,Walnuts,Watercress,Wild Garlic,Wild Grapes,Wood Sorrel";
	private static WeightedTable<String> edibles;
	private static final String POISON = "Angel's Trumpet,Baneberry,Belladona,Black Truffle,Bleeding Heart,Celandine,Cocklebur,Columbine,Crowncup,Death Cap,Dumbcane,Foxglove,"+
			"Hemlock,Hogweed,Holly,Horse Chestnut,Hyacinth,Ivy,Jessamine,Kudu,Larkspur,Mandrake,Mangrove,Mistletoe,"+
			"Moonflower,Nightshade,Oleander,Ragwort,Reindeer Lichen,Snakeweed,Spindle,Stinkhorn,Waxcap,Wine-Cap,Wolfsbane,Wormwood";
	private static WeightedTable<String> poisons;
	private static final String DESCRIPTORS = "Abandoned,Active,Artistic,Atmospheric,Beautiful,Bleak,Bright,Businesslike,Calm,Charming,Clean,Cluttered,Cold,Colorful,Colorless,Confusing,Cramped,Creepy,Crude,Cute,Damaged,Dangrous,Dark,Delightful,Dirty,"+
			"Domestic,Empty,Enclosed,Enormous,Threshold,Exclusive,Exposed,Extravagant,Familiar,Fancy,Festive,Foreboding,Fortunate,Fragrant,Frantic,Frightening,Full,Harmful,Helpful,Horrible,Important,Impressive,Inactive,Intense,Intriguing,"+
			"Liminal,Lively,Lonely,Long,Loud,Meaningful,Messy,Mobile,Modern,Mundane,Mysterious,Natural,New,Occupied,Odd,Official,Old,Open,Peaceful,Personal,Plain,Magically Connected,Protected,Protective,Purposeful,Quiet,Reassuring,"+
			"Remote,Resourceful,Ruined,Rustic,Safe,Multifunctional,Simple,Small,Spacious,Storage,Strange,Stylish,Suspicious,Tall,Threatening,Tranquil,Unexpected,Unpleasant,Unusual,Useful,Warm,Warning,Watery,Welcoming";
	private static WeightedTable<String> descriptors;
	
	private static void populate(WeightedTable<String> table,String values,String regex) {
		for(String s:values.split(regex)) {
			table.put(s);
		}
	}
	private static void populateAllTables() {
		biomes = new WeightedTable<String>();
		populate(biomes,BIOMES,",");
		landmarks = new WeightedTable<String>();
		populate(landmarks,LANDMARKS,",");
		structures = new WeightedTable<String>();
		populate(structures,STRUCTURES,",");
		discoveries = new WeightedTable<String>();
		populate(discoveries,DISCOVERIES,",");
		wildactivities = new WeightedTable<String>();
		populate(wildactivities,WILDACTIVITIES,",");
		hazards = new WeightedTable<String>();
		populate(hazards,HAZARDS,",");
		edibles = new WeightedTable<String>();
		populate(edibles,EDIBLE,",");
		poisons = new WeightedTable<String>();
		populate(poisons,POISON,",");
		descriptors = new WeightedTable<String>();
		populate(descriptors,DESCRIPTORS,",");
	}
	public static String getBiome(int i) {
		if(biomes==null) populateAllTables();
		return biomes.getByWeight(i);
	}
	public static String getLandmark(int i) {
		if(landmarks==null) populateAllTables();
		return landmarks.getByWeight(i);
	}
	public static String getStructure(int i) {
		if(structures==null) populateAllTables();
		return structures.getByWeight(i);
	}
	public static String getStructureOrLandmark(int i) {
		if(structures==null) populateAllTables();
		if(i%2==0) return getStructure(i/2);
		else return getLandmark(i/2);
	}
	public static String getActivity(int i) {
		if(wildactivities==null) populateAllTables();
		return Util.formatTableResult(wildactivities.getByWeight(i),new Indexible(i/wildactivities.size()));
	}
	public static String getDiscovery(int i) {
		if(discoveries==null) populateAllTables();
		return Util.formatTableResult(discoveries.getByWeight(i),new Indexible(i/discoveries.size()));
	}
	public static String getHazard(int i) {
		if(hazards==null) populateAllTables();
		return hazards.getByWeight(i);
	}
	public static String getEdiblePlant(int i) {
		if(edibles==null) populateAllTables();
		return edibles.getByWeight(i);
	}
	public static String getPoisonousPlant(int i) {
		if(poisons==null) populateAllTables();
		return poisons.getByWeight(i);
	}
	public static String getDescriptor(Indexible e) {
		if(descriptors==null) populateAllTables();
		return descriptors.getByWeight(e);
	}
	
	

	public static String getBiome(Indexible obj) {
		if(biomes==null) populateAllTables();
		return biomes.getByWeight(obj);
	}
	public static String getLandmark(Indexible obj) {
		if(landmarks==null) populateAllTables();
		return landmarks.getByWeight(obj);
	}
	public static String getStructure(Indexible obj) {
		if(structures==null) populateAllTables();
		return structures.getByWeight(obj);
	}
	public static String getStructureOrLandmark(Indexible obj) {
		if(structures==null) populateAllTables();
		int i = obj.reduceTempId(2);
		if(i==0) return getStructure(obj);
		else return getLandmark(obj);
	}
	public static String getActivity(Indexible obj) {
		if(wildactivities==null) populateAllTables();
		return Util.formatTableResult(wildactivities.getByWeight(obj),obj);
	}
	public static String getDiscovery(Indexible obj) {
		if(discoveries==null) populateAllTables();
		return Util.formatTableResult(discoveries.getByWeight(obj),obj);
	}
	public static String getHazard(Indexible obj) {
		if(hazards==null) populateAllTables();
		return hazards.getByWeight(obj);
	}
	public static String getEdiblePlant(Indexible obj) {
		if(edibles==null) populateAllTables();
		return edibles.getByWeight(obj);
	}
	public static String getPoisonousPlant(Indexible obj) {
		if(poisons==null) populateAllTables();
		return poisons.getByWeight(obj);
	}
	
	
	private SaveRecord record;
	
	public LocationModel(SaveRecord record) {
		this.record = record;
	}
	private int getLocationDetailIndex(int i,Point p) {
		float val = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+i), p.x, p.y);
		return Util.getIndexFromSimplex(val);
	}

	public String getStructure(int i,Point p) {
		return getStructure(getLocationDetailIndex(i*TABLECOUNT, p));
	}
	public String getPOI(int i,Point p,boolean isCity) {
		Indexible obj = new Indexible(getLocationDetailIndex(i*TABLECOUNT, p));
		String location;
		if(isCity) location = SettlementModel.getBuilding(obj);
		else location = getStructure(obj);
		String descriptor1 = getDescriptor(obj);
		String descriptor2 = getDescriptor(obj);
		String proprietor = "\r\nProprietor: "+Util.formatTableResultPOS("${npc index}", obj, p, null);
		return descriptor1+" and "+descriptor2+" "+location+proprietor;
	}
	public String getEdible(int i,Point p) {
		if(edibles==null) populateAllTables();
		return edibles.getByWeight(getLocationDetailIndex(i*TABLECOUNT+5, p));
	}
	public String getPoison(int i,Point p) {
		if(poisons==null) populateAllTables();
		return poisons.getByWeight(getLocationDetailIndex(i*TABLECOUNT+6, p));
	}

}
