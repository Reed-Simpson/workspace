package data.location;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import controllers.DataController;
import data.DataModel;
import data.HexData;
import data.Indexible;
import data.OpenSimplex2S;
import data.Reference;
import data.WeightedTable;
import io.SaveRecord;
import names.InnNameGenerator;
import util.Util;
import view.InfoPanel;

public class LocationModel extends DataModel{
	private static final int SEED_OFFSET = 11*Util.getOffsetX();
	private static final int TABLECOUNT = 5;
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
		discoveries = new WeightedTable<String>().populate(DISCOVERIES,",");
		wildactivities = new WeightedTable<String>().populate(WILDACTIVITIES,",");
		hazards = new WeightedTable<String>().populate(HAZARDS,",");
		edibles = new WeightedTable<String>().populate(EDIBLE,",");
		poisons = new WeightedTable<String>().populate(POISON,",");
		descriptors = new WeightedTable<String>().populate(DESCRIPTORS,",");
	}
	public static String getDescriptor(Indexible e) {
		if(descriptors==null) populateAllTables();
		return descriptors.getByWeight(e);
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

	private ArrayList<Reference> getDungeons(Point p, int i) {
		ArrayList<Reference> result = new ArrayList<Reference>();
		int[] dungeonPositions = controller.getDungeon().getDungeonPositions(p);
		if(dungeonPositions[i]>0) {
			int index = 0;
			for(int n=0;n<i;n++) index+=dungeonPositions[n];
			for(int n=index;n<index+dungeonPositions[i];n++) {
				//Dungeon d = controller.getDungeon().getDefaultValue(p, n);
				result.add(new Reference(HexData.DUNGEON, record.normalizePOS(p), n));
			}
		}
		return result;
	}
	public Location getPOI(int i,Point p) {
		if(i==0) return getInnText(p);
		boolean isCity = controller.getPopulation().isCity(p);
		Location location = new Location(getLocationDetailIndex(3+i*TABLECOUNT, p));
		populateLocationFields(isCity, location,i,p);
		return location;
	}
	public Location getPOI(Random random,Point p,boolean isCity,int i) {
		if(i==0) return getInnText(random, p);
		Location location = new Location(random.nextInt());
		populateLocationFields(isCity, location,i,p);
		return location;
	}
	private Location populateLocationFields(boolean isCity, Location location,int i,Point p) {
		LocationType type;
		int landmarkCount;
		if(isCity) {
			if(i-1<InfoPanel.DISTRICTCOUNT) {
				District d = controller.getSettlements().getDistrict(i-1, p);
				type = d.getBuilding();
				if(type == null) type = LocationType.getBuilding(location);
			}else {
				type = LocationType.getBuilding(location);
			}
			landmarkCount = 7;
		}
		else if(controller.getPopulation().isTown(p)){
			if(i==1) type = LocationType.Village;
			else if(i==2||i==4) type = LocationType.getBuilding(location);
			else {
				if(location.reduceTempId(10)==0) type = LocationType.getBuilding(location);
				else type = LocationType.getStructure(location);
			}
			landmarkCount = 4;
		}else {
			if(location.reduceTempId(10)==0) type = LocationType.getBuilding(location);
			else type = LocationType.getStructure(location);
			landmarkCount = 2;
		}
		location.setType(type);
		location.setDescriptors(new String[] {getDescriptor(location),getDescriptor(location)});
		location.setProprietorJob(type.getRandomJobType(location));
		if(location.getProprietorJob()!=null) location.setProprietor(new Reference(HexData.PROPRIETOR, record.normalizePOS(p), i));
//		if(isCity) {
//			location.setProprietor(Util.getRandomReference(location, "npc", InfoPanel.NPCCOUNT, record.normalizePOS(p)));
//		}
		location.setVisibility(getVisibility(i,landmarkCount));
		location.setDungeons(getDungeons(p, i));
		return location;
	}
	private String getVisibility(int i,int landmarkCount) {
		if(i<landmarkCount) return VISIBILITY[0];
		else if(i<landmarkCount*2) return VISIBILITY[1];
		else return VISIBILITY[2];
	}
	@Override
	public String getDefaultValue(Point p, int i) {
		return getPOI(i, p).toString();
	}


	public Location getInnText(Point p) {
		Location obj = new Location(getLocationDetailIndex(0, p),getLocationDetailIndex(1, p),getLocationDetailIndex(2, p),getLocationDetailIndex(3, p));
		if(obj.reduceTempId(100)>getInnProbability(controller.getPopulation().getTransformedUniversalPopulation(p))) {
			return null;
		}
		populateInnFields(obj,p);
		return obj;
	}
	public Location getInnText(Random random,Point p) {
		Location obj = new Location(random.nextInt(),random.nextInt(),random.nextInt(),random.nextInt());
		populateInnFields(obj,p);
		return obj;
	}
	private String populateInnFields(Location location,Point p) {
		location.setType(LocationType.Inn);
		location.setDescriptors(new String[] {getDescriptor(location),getDescriptor(location)});
		location.setName(innNames.getName((Indexible) location));
		location.setProprietorJob(LocationType.Inn.getRandomJobType(location));
		if(location.getProprietorJob()!=null) location.setProprietor(new Reference(HexData.PROPRIETOR, record.normalizePOS(p), 0));
		//location.setProprietor(Util.getRandomReference(location, "npc", InfoPanel.NPCCOUNT, record.normalizePOS(p)));
		location.setVisibility(VISIBILITY[0]);
		location.setQuirk(innNames.getQuirk(location));
		location.setDungeons(getDungeons(p, 0));
		return location.toString();
	}
	private int getInnProbability(int pop) {
		float ratio = pop/2000f;
		if(ratio<=0) return 0;
		float percent = (float) (1-Math.pow(100, ratio*-1)*1.0001f)+0.0001f;
		return (int) (percent*100);
	}

}
