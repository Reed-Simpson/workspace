package data.location;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import controllers.DataController;
import data.DataModel;
import data.Indexible;
import data.OpenSimplex2S;
import data.WeightedTable;
import data.dungeon.Dungeon;
import data.population.SettlementModel;
import io.SaveRecord;
import names.InnNameGenerator;
import util.Util;

public class LocationModel extends DataModel{
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
	private static final String[] VISIBILITY = {"Landmark","Standard","Hidden"};

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
	private DataController controller;
	private InnNameGenerator innNames;



	public LocationModel(SaveRecord record,DataController controller) {
		super(record);
		this.controller = controller;
		this.innNames = new InnNameGenerator();
	}
	private int getLocationDetailIndex(int i,Point p) {
		float val = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+i), p.x, p.y);
		return Util.getIndexFromSimplex(val);
	}

	private ArrayList<String> getDungeons(Point p, int i) {
		ArrayList<String> result = new ArrayList<String>();
		int[] dungeonPositions = controller.getDungeon().getDungeonPositions(p);
		if(dungeonPositions[i]>0) {
			int index = 0;
			for(int n=0;n<i;n++) index+=dungeonPositions[n];
			for(int n=index;n<index+dungeonPositions[i];n++) {
				Dungeon d = controller.getDungeon().getDefaultValue(p, n);
				result.add(d.toString());
//				if(d.getLocation().getIndex()==i) {
//					String s = d.getEntrance()+" leading to "+new Reference(HexData.DUNGEON, record.normalizePOS(p), n);
//					result.add(s);
//				}
			}
		}
		return result;
	}
	public String getPOI(int i,Point p,boolean isCity) {
		Indexible obj = new Indexible(getLocationDetailIndex(3+i*TABLECOUNT, p));
		ArrayList<String> dungeons = getDungeons(p,i);
		String poi = getPOI(isCity, obj,i,dungeons);
		return Util.formatTableResultPOS(poi, obj, p,record.getZero());
	}
	public String getPOI(Random random,Point p,boolean isCity,int i) {
		Indexible obj = new Indexible(random.nextInt());
		ArrayList<String> dungeons = getDungeons(p,i);
		String poi = getPOI(isCity, obj,i,dungeons);
		return Util.formatTableResultPOS(poi, obj, p,record.getZero());
	}
	private String getPOI(boolean isCity, Indexible obj,int i,ArrayList<String> dungeons) {
		String location;
		if(isCity) location = SettlementModel.getBuilding(obj);
		else location = getStructure(obj);
		String descriptor1 = getDescriptor(obj);
		String descriptor2 = getDescriptor(obj);
		String proprietor = "";
		if(isCity) {
			proprietor = "\r\nProprietor: ${npc index}";
		}
		String visibility = "\r\nVisibility: "+getVisibility(i);
		String dungeon = "";
		if(dungeons.size()>0) {
			dungeon = "\r\nDungeon Entrances: ";
			for(String s:dungeons) {
				dungeon+="\r\n   "+s;
			}
		}
		return descriptor1+" and "+descriptor2+" "+location+proprietor+visibility+dungeon;
	}
	private String getVisibility(int i) {
		if(i<2) return VISIBILITY[0];
		else if(i<6) return VISIBILITY[1];
		else return VISIBILITY[2];
	}
	@Override
	public String getDefaultValue(Point p, int i) {
		return getPOI(i, p, false);
	}


	public String getInnText(Point p) {
		Indexible obj = new Indexible(getLocationDetailIndex(0, p),getLocationDetailIndex(1, p),getLocationDetailIndex(2, p),getLocationDetailIndex(3, p));
		return Util.formatTableResultPOS(getInnText(obj,p),obj,p,record.getZero());
	}
	public String getInnText(Random random,Point p) {
		Indexible obj = new Indexible(random.nextInt(),random.nextInt(),random.nextInt(),random.nextInt());
		return getInnText(obj,p);
	}
	private String getInnText(Indexible obj,Point p) {
		String innname = "Inn: "+getInnName(obj);
		String innquirk = "\r\nQuirk: "+getInnQuirk(obj);
		String inndescriptors = "\r\nDescriptors: "+getInnDescriptor(obj)+" and "+getInnDescriptor(obj);
		String proprietor = "\r\nProprietor: ${npc index}";
		String dungeon = "";
		ArrayList<String> dungeons = getDungeons(p,0);
		if(dungeons.size()>0) {
			dungeon = "\r\nDungeon Entrances: ";
			for(String s:dungeons) {
				dungeon+="\r\n    "+s;
			}
		}
		return innname+innquirk+inndescriptors+proprietor+dungeon;
	}

	public String getInnName(Indexible obj) {
		return innNames.getName(obj);
	}
	public String getInnQuirk(Indexible obj) {
		return innNames.getQuirk(obj);
	}
	public String getInnDescriptor(Indexible obj) {
		return LocationModel.getDescriptor(obj);
	}

}
