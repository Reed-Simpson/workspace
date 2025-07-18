package data.item;

import data.Indexible;
import data.WeightedTable;
import util.Util;

public class EquipmentModel {
	//private static final int SEED_OFFSET = 14*Util.getOffsetX();
	//private static final int TABLECOUNT = 12;
	private static final String MISC = "Barrel,Bowl,Bell,Brooch,Figurine,Cup,Deck,Drawing,Coin,Pawn,Eye,Jar,Comb,"+
			"Handkerchief,Box,Hourglass,Tooth,Horn,Dice,Fork,Key,Lamp,Doll,Paint,Pencil,"+
			"Purse,Quill,Slave,Scissors,Scroll,Letter,Needle,Razor,Button,Skull,Pipe,Wine";
	private static WeightedTable<String> misc;
	private static final String APPAREL = "Belt,Blouse,Boots,Bracelet,Breastplate,Brigandine,Cincture,Cloak,Coat,Dress,Earing,Eyepatch,"+
			"Gauntlets,Glove,Gown,Hat,Helmet,Hose,Leather armor,Locket,Mail shirt,Mask,Necklace,Padded armor,"+
			"Plate mail,Ring,Robe,Sandals,Scarf,Shirt,Shoes,Skirt,Slippers,Socks,Trousers,Veil";
	private static WeightedTable<String> apparel;
	private static final String WEAPONS = "Sword,Backsword,Battleaxe,Blowpipe,Claymore,Club,Crossbow,Cutlass,Dagger,Flail,Glaive,"+
			"Halberd,Hammer,Hatchet,Horsebow,Knife,Lance,Longbow,Longsword,Mace,Maul,Morningstar,Pike,"+
			"Scimitar,Shortbow,Sickle,Sling,Spear,Staff,Stake,Stiletto,Axe,Warhammer,Warpick,Whip";
	private static WeightedTable<String> weapons;
	private static final String BOOKS = "Alchemy,Art,Astrology,Blackmail,Charts and maps,Conspiracies,Cookbook,Criminals,Divination,Etiquette,Fashion,Genealogy,"+
			"Hagiography,History,Journal,Language,Laws,Letters,Lost empires,Lost places,Love poems,Monsters,Mythology,Odd customs,"+
			"Oratory,Propaganda,Prophecies,Siegecraft,Songs,State secrets,Sword fighting,Theology,Treasures,War chronicle,Who's who,Witch-hunting";
	private static WeightedTable<String> books;
	private static final String TOOLS = "Acid,Beartrap,Bellows,Boltcutters,Chain,Chisel,Crowbar,Ram,Eartrumpet,Fireoil,Fishhook,Goggles,"+
			"Grapplinghook,Grease,Hacksaw,Hammer,Drill,Lantern,Lens,Lock,Lockpick,Manacles,File,Mortar,"+
			"Needle,Pickaxe,Pitchfork,Pliers,Pole,Pulleys,Rope,Scissors,Shovel,Spikes,Wire,Tongs";
	private static WeightedTable<String> tools;
	private static final String POTIONS = "${animal}-form,Body swap,Camoflage,Control animals,Control ${element},Cure affliction,Detect evil,Detect gold,Detect hidden,Direction sense,${element}-form,${element}-skin,"+
			"Extra arm,Flight,Ghost-speech,Heat vision,${insanity},Invulnerable,${item}-form,Magic immunity,Mirror image,${monster ability},${monster feature},${monster trait},"+
			"${mutation},Night vision,${spell},Restore health,Speed,Stretchy,Super-jump,Super-strength,Telekinesis,Tongues,Water-breathing,Water-walking";
	private static WeightedTable<String> potions;
	private static final String INGREDIENTS = "Ancient Liquor,${animal},Blind eye,Boiled cat,${book} page,Bottled frog,Coffin nail,Corpse hair,Crossroad dust,Cultist entrails,${edible plant},Exotic spice,"+
			"Killer's hand,King's tooth,Last breath,Liar's tongue,Lightning bolt,Lodestone,Monk's vow,${monster feature},Newborn's cry,Oil portrait,${physical element},${poisonous plant},"+
			"${potion},Pyre ember,Queen bee,Queen's blood,Ship's barnacle,Starmetal,Thief's finger,Tomb flower,${rare material},Wedding ring,Widow's tears,Wizard skull";
	private static WeightedTable<String> ingredients;
	private static final String TREASURES = "Alchemy recipe,Amulet,Astrolabe,Blueprints,Calligraphy,Carpet,Compass,Contract,Crown,Crystal,Deed,Embroidery,"+
			"Fine china,Fine liquor,Instrument,Magical book,Microscope,Music box,Orrery,Painting,Perfume,Prayer book,Printing block,Rare textile,"+
			"Royal robes,Saint's relic,Scrimshaw,Sextant,Sheet music,Signet ring,Silverware,Spices,Spyglass,Tapestry,Telescope,Treasure map";
	private static WeightedTable<String> treasures;
	private static final String TRAITS = "Altered,Ancient,Blessed,Bulky,Compact,Consumable,Culturally valuable,Cursed,Damaged,Disguised,Draws enemies,${effect},"+
			"${element},Embellished,Encoded,Exotic,Extra-planar,Famous,Forbidden,Fragile,Heavy,Immovable,Impractical,Indestructible,"+
			"Intelligent,Masterwork,Militarily valuable,Non-human,Owned,Partial,Politically valuable,Religiously valuable,Repaired,Royal,Toxic,Vile";
	private static WeightedTable<String> traits;
	private static final String METALS = "Iron,Bronze,Copper,Brass,Steel,Lead";
	private static WeightedTable<String> metals;
	private static final String WOODS = "Oak,Bamboo,Ironwood,Ash,Rosewood,Ebony,Pine,Birch";
	private static WeightedTable<String> woods;
	private static final String MINERALS = "Granite,Sandstone,Flint,Obsidian,Basalt,Quartz,Shale,Marble,Limestone,Brick,Bone,Clay";
	private static WeightedTable<String> minerals;
	private static final String FABRICS = "Silk,Leather,Hide,Linen,Wool,Hemp,Cotton";
	private static WeightedTable<String> fabrics;
	private static final String OTHER = "Loam,Ice,Water,Moss,Chitin,Ichor,Blood,Flesh";
	private static WeightedTable<String> other;
	private static final String RARE = "Alabaster,Amber,Aquamarine,Azurite,Beryl,Black pearl,Bloodstone,China,Chalcedony,Cinnebar,Coral,Diamond,"+
			"Dragonbone,Ebony,Emerald,Agate,Garnet,Gold,Ivory,Jade,Jasper,Jet,Lapis lazuli,Malachite,Moonstone,"+
			"Onyx,Opal,Pearl,Platinum,Porcelain,Ruby,Sapphire,Serpentine,Silver,Spidersilk,Starmetal,Topaz,Turquoise";
	private static WeightedTable<String> rare;
	
	private static void populate(WeightedTable<String> table,String values,String regex) {
		for(String s:values.split(regex)) {
			table.put(s);
		}
	}
	private static void populateAllTables() {
		misc = new WeightedTable<String>();
		populate(misc,MISC,",");
		apparel = new WeightedTable<String>();
		populate(apparel,APPAREL,",");
		weapons = new WeightedTable<String>();
		populate(weapons,WEAPONS,",");
		books = new WeightedTable<String>();
		populate(books,BOOKS,",");
		tools = new WeightedTable<String>();
		populate(tools,TOOLS,",");
		potions = new WeightedTable<String>();
		populate(potions,POTIONS,",");
		ingredients = new WeightedTable<String>();
		populate(ingredients,INGREDIENTS,",");
		treasures = new WeightedTable<String>();
		populate(treasures,TREASURES,",");
		traits = new WeightedTable<String>();
		populate(traits,TRAITS,",");
		rare = new WeightedTable<String>();
		populate(rare,RARE,",");
		metals = new WeightedTable<String>();
		populate(metals,METALS,",");
		woods = new WeightedTable<String>();
		populate(woods,WOODS,",");
		minerals = new WeightedTable<String>();
		populate(minerals,MINERALS,",");
		fabrics = new WeightedTable<String>();
		populate(fabrics,FABRICS,",");
		other = new WeightedTable<String>();
		populate(other,OTHER,",");
	}

	

	public static String getMisc(Indexible obj) {
		if(misc==null) populateAllTables();
		return misc.getByWeight(obj);
	}
	public static String getApparel(Indexible obj) {
		if(apparel==null) populateAllTables();
		return apparel.getByWeight(obj);
	}
	public static String getWeapon(Indexible obj) {
		if(weapons==null) populateAllTables();
		return weapons.getByWeight(obj);
	}
	public static String getItem(Indexible obj) {
		if(misc==null) populateAllTables();
		int index = obj.reduceTempId(5);
		if(index==0) return getMisc(obj);
		else if(index==1) return getApparel(obj);
		else if(index==2) return getWeapon(obj);
		else if(index==3) return getTreasure(obj);
		else return getTool(obj);
	}
	public static String getBook(Indexible obj) {
		if(books==null) populateAllTables();
		return books.getByWeight(obj);
	}
	public static String getTool(Indexible obj) {
		if(tools==null) populateAllTables();
		return tools.getByWeight(obj);
	}
	public static String getPotion(Indexible obj) {
		if(potions==null) populateAllTables();
		return Util.formatTableResult(potions.getByWeight(obj),obj);
	}
	public static String getIngredient(Indexible obj) {
		if(ingredients==null) populateAllTables();
		return Util.formatTableResult(ingredients.getByWeight(obj),obj);
	}
	public static String getTreasure(Indexible obj) {
		if(treasures==null) populateAllTables();
		return treasures.getByWeight(obj);
	}
	public static String getTrait(Indexible obj) {
		if(traits==null) populateAllTables();
		return Util.formatTableResult(traits.getByWeight(obj),obj);
	}
	public static String getRareMaterial(Indexible obj) {
		if(rare==null) populateAllTables();
		return rare.getByWeight(obj);
	}
	public static String getCommonMaterial(Indexible obj) {
		if(metals==null) populateAllTables();
		int index = obj.reduceTempId(4);
		if(index==0) return getMetal(obj);
		else if(index==1) return getWood(obj);
		else if(index==2) return getStone(obj);
		else return getFabric(obj);
	}
	public static String getMetal(Indexible obj) {
		if(metals==null) populateAllTables();
		return metals.getByWeight(obj);
	}
	public static String getWood(Indexible obj) {
		if(woods==null) populateAllTables();
		return woods.getByWeight(obj);
	}
	public static String getStone(Indexible obj) {
		if(minerals==null) populateAllTables();
		return minerals.getByWeight(obj);
	}
	public static String getFabric(Indexible obj) {
		if(fabrics==null) populateAllTables();
		return fabrics.getByWeight(obj);
	}
	public static String getMaterial(Indexible obj) {
		if(metals==null) populateAllTables();
		int ratio = 5;
		if(obj.reduceTempId(ratio)==0) return getRareMaterial(obj);
		else return getCommonMaterial(obj);
	}

}
