package names;

import data.Indexible;
import data.WeightedTable;
import util.Util;

public class InnNameGenerator extends NameGenerator{
	private static final String PREFIXES = "Bellowing,Blazing,Bleak,Blessed,Bloody,Crimson,Cunning,Copper,Dancing,Dead,Drunken,Flying,"+//Maze rats
			"Ghastly,Golden,Helpful,Hideous,Howling,Hungry,Moldy,Muttering,Nimble,Oozing,Petrified,Prancing,"+
			"Romantic,Salty,Singing,Shivering,Shrieking,Silver,Smoking,Thirsty,Wicked,Tipsy,Whistling,Wanton,"+
			"Beardless,Laughing,Gilded,Stumbling,Wolf and,Fallen,Leering,Wine and,Roaring,Frowning,Barrel and,Wandering,Barking,Happy,Witch and,"+//DMG
			"Lively,Scratchy,Knobby,Ruddy,Merry,Sticky,Abandoned,Courageous,Itchy,Aching,Handsome,Ornery,Bountiful,Filthy,Quaint,Drafty,Lanky,Nasty,Shiny,Crowded,"+//TBMBo Random Encounters
			"Reckless,Gleaming,Winged,Jagged,Cozy,Harmless,Nifty,Eager,Pitiful,Deserted,Frosty,Soggy,Magnificent,Regal,Rough,Meager,Embellished,Proud,Intrepid,Shoddy,"+
			"Fair,Loud,Bare,Mysterious,Jaunty,Muddy,Hospitable,Livid,Golden,Salty,Knowing,Lavish,Silver,Charming,Hairy,Bleak,Feisty,Smoggy,Opulent,Peaceful,"+
			"Immaculate,Sleepy,Limping,Everlasting,Wild,Ironclad,Growling,Stale,Jovial,Wise,Gullible,Ample,Rich,Cautious,Wonderful,Hungry,Wooden,Yawning,Heavenly,Rosy,"+
			"Bewitched,Spirited,Buzzing,Tasty,Snarling,Enchanted,Clever,Pleasant,Deadly,Tricky,Treasured,Antique,Poor,Glorious,Blushing,Ultimate,Vacant,Dull,Weary,Fickle";
	private static WeightedTable<String> prefixes;
	private static final String SUFFIXES = "Axe,Barrel,Bear,Bell,Boot,Bowl,Bucket,Candle,Cock,Cow,Dragon,Egg,"+//Maze rats
			"Elephant,Flea,Fork,Giant,Griffin,Hart,Hog,Hound,Lamb,Lion,Mackerel,Maid,"+
			"Monk,Moon,Pipe,Prince,Rat,Skull,Spoon,Star,Swan,Sword,Whale,Wife,"+
			"Lyre,Dolphin,Dwarf,Pegasus,Hut,Rose,Stag,Duck,Demon,Goat,Spirit,Horde,Jester,Crow,Satyr,Dog,Spider,"+//DMG
			"Flagon,Rogue,Dragon,Hag,Pony,Troll,Drunk,Pint,Basin,Traveler,Giant,Boar,Flask,Drow,Fairy,Sow,Dretch,Boot,Serpent,Lion,"+//TBMBo Random Encounters
			"Balor,King,Tiefling,Cauldron,Goat,Bard,Bottle,Pub,Shot,Knight,Miner,Inn,Earl,Duck,Mug,Crevice,Dagger,Horse,Sailor,Elf,"+
			"Chameleon,Nymph,Hall,Wench,Dump,Castle,Helm,Fox,Throne,Dwarf,Wyvern,Haven,Talon,Bar,Wand,Den,House,Bear,Lantern,Demon,"+
			"Blade,Eunuch,Axe,Wagon,Palace,Brewery,Minotaur,Wanderer,Lord,Hideaway,Cudgel,Tower,Minstrel,Barrel,Tankard,Bow,Hearth,Toad,Lodge,Room,"+
			"Bucket,Damsel,Willow,Unicorn,Head,Crow,Mead House,Cat,Arrow,Parlor,Swamp,Boil,Fool,Louse,Hat,Cavern,Winery,Place,Griffin,Sword";
	private static WeightedTable<String> suffixes;
	private static final String QUIRKS = "100 years in the past,always night,animal fights,bard duels,bigger inside,black market,"+//Maze rats
			"brand new,cannibals,${city activity},constant party,dancing contest,dead drop,"+
			"${dungeon form},expensive,${faction} hangout,${faction trait},famous chef,non-humanoid patrons,"+
			"fight club,five floors,ghost staff,haunted,hideout,${building},"+
			"magic sword,magically moves,mercs for hire,${job} hangout,preaching,secure storage,"+
			"staff are kids,talking painting,underground,VIP lounge,voice in well,women only,"+
			"sawdust on the floor and dust in the rafters,complex drinks that require a wait time of 20 minutes,hosting a sing-a-long night with one of the region's least popular bards,"+//TBMBo Random Encounters
			"a brand ambassador for an experimental mead,an illegal gambling hall under the floorboards,velvet-lined walls with a dress code and an undeserved air of importance,"+
			"ornamental trophies from beasts and monsters hanging on the wall,its floor stocked with dozens of pillows instead of tables and chairs,"+
			"a dance hall with music so loud it's tough to hear anything else,a back patio that's never open,"+
			"an epic collection of classic board games though most sets are missing a piece or two,a large crater-like hole in the center of the floor that has gone unremarked upon for months,"+
			"is utterly pleasing to the eye but dripping with illusion magic,clearly has an arrangement with a nearby brothel,"+
			"a tropical theme and feels like walking through a humid rainforest,walls lined with portraits of famed adventurers enjoying a pint there,"+
			"servers wear kitchy buttons and enthusiastically advertise the bar's other locations,only serves pitchers,"+
			"has been named best pub in town several years in a row by the nobles in the region,features great food at incredible prices but discriminates against certain customers";
	private static WeightedTable<String> quirks;

	private static void populate(WeightedTable<String> table,String values,String regex) {
		for(String s:values.split(regex)) {
			table.put(s);
		}
	}
	public static String getPrefix(int val) {
		if(prefixes==null) {
			prefixes = new WeightedTable<String>();
			populate(prefixes, PREFIXES, ",");
		}
		return prefixes.getByWeight(val);
	}
	public static String getSuffix(int val) {
		if(suffixes==null) {
			suffixes = new WeightedTable<String>();
			populate(suffixes, SUFFIXES, ",");
		}
		return suffixes.getByWeight(val);
	}
	public static String getPrefix(Indexible obj) {
		if(prefixes==null) {
			prefixes = new WeightedTable<String>();
			populate(prefixes, PREFIXES, ",");
		}
		return prefixes.getByWeight(obj);
	}
	public static String getSuffix(Indexible obj) {
		if(suffixes==null) {
			suffixes = new WeightedTable<String>();
			populate(suffixes, SUFFIXES, ",");
		}
		return suffixes.getByWeight(obj);
	}

	public String getName(Indexible obj) {
		return "The "+getPrefix(obj)+" "+getSuffix(obj);
	}
	public String getQuirk(Indexible obj) {
		if(quirks==null) {
			quirks = new WeightedTable<String>();
			populate(quirks, QUIRKS, ",");
		}
		return Util.formatTableResult(quirks.getByWeight(obj),obj);
	}
	@Override
	public String getName(int... val) {
		return getName(new Indexible(val));
	}
	

}
