package data.dungeon;

import java.awt.Point;
import java.util.Random;

import data.DataModel;
import data.HexData;
import data.Indexible;
import data.OpenSimplex2S;
import data.Reference;
import data.WeightedTable;
import data.monster.MonsterModel;
import io.SaveRecord;
import util.Util;
import view.InfoPanel;

public class DungeonModel extends DataModel {
	private static final int SEED_OFFSET = 12*Util.getOffsetX();
	private static final int TABLECOUNT = 12;
	private static final String ENTRANCES = "Library,Beaver Dam,Behind Waterfall,Chalk Rectangle,Chest Bottom,Chimney,Cupboard,Dolmen Shadow,Down a Well,Firey Pit,Fog Road,Forest Spring,"+
			"Giant Book,Nomad Wagon,Hollow Tree,Huge Keyhole,Iron Maiden,Living Tattoo,Magic Painting,Man-Shaped Hole,Maze Potion,Mirror,Monster Mouth,Monster Wound,"+
			"Narrow Alley,Rain Door,Sewer Grate,Sudden Rift,Tidal Cave,Tower Top,Tree Roots,Under the Bed,Unfolded Map,Up a Tree,Whirlpool,Wine Barrel";
	private static WeightedTable<String> entrances;
	private static final String FORMS = "Arena,Asylum,Aviary,Bank,Baths,Body,${building room},Casino,Catacombs,Cave,Court,${dungeon room},"+
			"Forge,Garden,Hideout,Hotel,${lower class building},Laboratory,Library,Market,Mine,Monastery,Museum,Nursery,"+
			"Orphanage,Palace,Prison,Sewer,Ship,Slave Pit,Temple,Theater,${upper class building},University,Vault,Zoo";
	private static WeightedTable<String> forms;
	private static final String LAYOUTS = "Ant Colony,Central Hub,Claustrophobic,Crisscrossing,Curved,Disorienting,Galleria,Geometric,Gonzo,Haphazard,Highly Irregular,Honeycomb,"+
			"Intertwined,Isolated Wings,Layered,Linear,Loops,Many Corridors,Mazes,Mix of Layouts,Multiple Hubs,No Corridors,Open Plan,Open Voids,"+
			"Organic,Oversized,Recursive,Repetitive,Sprawling,Suspended,Symbol Shape,Tall and Narrow,Themed Zones,Vertical,Winding,Ziggurat";
	private static WeightedTable<String> layouts;
	private static final String RUINATIONS = "Arcane Disaster,Army Invasion,Cannibalism,Civil War,Collapse,Crystal Growth,Curse,Degeneration,Earthquake,Eruption,Evil Unearthed,Experiments,"+
			"Explosion,Famine,Fire,Flooding,Fungus,Haunting,Ice,Insanity:${insanity},Lava Flow,Magical Sleep,Melted,Monster Attack,"+
			"Mutation:${mutation},Outsider Attack,Overgrowth,Petrification,Plague,Planar Overlay,Poison Gas,Resources Gone,Revolt,Risen Dead,Too Many Traps,War";
	private static WeightedTable<String> ruinations;
	private static final String REWARDS = "Ancient ${book} Lore,${animal} Ally,Army,Blessing,Blueprints,Cultural Artifact,Enemy Weakness,${faction index} Ally,Forewarning,Guide,Holy Relic of ${domain},Influential Ally ${npc index},"+
			"Instructions,Jewels,Key,Lost Formula,Machine,Magic ${item},Magical Ally ${npc index},Map,Martial Ally ${npc index},Masterpiece,Monsterous ${animal} Ally,Oracle,"+
			"Piles of Loot,Planar Portal,Prophecy,Renown,Spell ${spell},Transformation,Transport,${treasure item},Uncovered Plot,${rare material},Vision,Magic ${weapon}";
	private static WeightedTable<String> rewards;
	private static final String ACTIVITIES = "Besiege,Capture,${city activity},Collect,Construct,Control,Deliver,Demolish,Escape,Feed,Fortify,Guard,"+
			"Hide,Hunt,Loot,Map,Mine,${monster tactic},Negotiate,Patrol,Perform Ritual,Purge,Question,Raid,"+
			"Repair,Rescue,Research,Revive,Riddle,Scavenge,Seize,Tunnel,Unearth,Vandalize,${wilderness activity},Worship";
	private static WeightedTable<String> activities;
	private static final String ROOMS = "Armory,Banquet Hall,Barracks,${building room},Catacombs,Cavern,Chasm,Courtyard,Crypt,Dormitory,Fighting Pit,Forge,"+
			"Fountain,Gate House,Guard Room,Kennel,${lower class building},Laboratory,Mess Hall,Mine Shaft,Museum,Oubilette,Pool,Prison,"+
			"Record Room,Shrine,Slaughterhouse,Stables,Storeroom,Throne Room,Torture Room,Treasury,${upper class building},Vault,Well,Workshop";
	private static WeightedTable<String> rooms;
	private static final String DETAILS = "Bas-Relief,Blood Trail,Bones,Chains,Chalk Marks,Corpses,Cracked Beams,Crumbling Walls,Decaying Food,Decaying Nest,Dripping Water,"+
			"Fading Murals,Faint Breeze,Faint Footsteps,Fallen Pillars,Fungus,Furniture,Graffiti,Mosaics,Recent Repairs,Rotting Books,Rubble,Shed Skin,"+
			"Slime Trails,Spider Webs,Stalactites,Stench,Smoke Stains,Thick Dust,Torn Clothes,Tree Roots,Unusual Smell,Vibrations,Vines,Whispers";
	private static WeightedTable<String> details;
	private static final String TRICKS = "Absorbtion,Activation,Animation,Blessings,Communication,Confusion,Consumption,Creation,Curses,Deception,Duplication,${ethereal effect},"+
			"Exchange,Imprisonment,Instructions,Interrogation,Mind-Control,${mission},Mood-Alteration,Nullification,${physical effect},Planeshift,Protection,Rejuvenation,"+
			"Release,Reversal,Rotation,Scrying,Size-Alteration,Summoning,Theft,Time-Alteration,Transformation,Transmutation,Transportation,Wonder";
	private static WeightedTable<String> tricks;
	private static final String HAZARDS = "Acid Drip,Bloodsuckers,Cave-in,Choking Dust,Crude Oil,Crystal Shards,Deafening Noise,Dense Fog,Ensnaring Vines,Fallen Floor,Flooding,Freezing,"+
			"Geysers,Magma,Magnetic Field,Mud Flow,Narrow Ledge,Narrow Passage,Poison Goo,Poison Plants,Precipice,Quicksand,Radiation,Rockslide,"+
			"Rotten Ceiling,Rotten Floor,Sinkhole,Slippery Slope,Spider Webs,Spores,Steam Vents,Strong Winds,Tar Pit,Tight Passage,Toppling Object,Toxic Fumes";
	private static WeightedTable<String> hazards;
	private static final String TRAPEFFECTS = "Acid Pool,Adhesive,Alarm,Armor Melts,Bear Trap,Blinding Spray,Blunt Pendulum,Boiling Tar,Collapsing Floor,Crocodile Pit,Crushing Walls,Deep Pit,"+
			"Falling Cage,Falling Ceiling,Fills with Sand,Flooding,Giant Magnet,Hard Vacuum,Lava Flow,Lightning,Living Statues,Missile Fire,Monster Freed,Net Trap,"+
			"Pendulum Blade,Poison Gas,Poison Needle,Quicksand,Rage Gas,Rolling Boulder,Room Freezes,Room on Fire,Sleeping Gas,Spiked Pit,Tombs Open,Wall Spikes";
	private static WeightedTable<String> trapeffects;
	private static final String TRAPTRIGGERS = "Blow,Break,Burn,Choice,Countdown,Darkness,Drain,Eat,Insert,Kill,Knock,Light,"+
			"Magic,Melody,Noise,Open,Phrase,Pour,Press,Proximity,Pull,Read,Reflect,Release,"+
			"Remove,Retrieve,Rudeness,Shut,Sit,Sleep,Slide,Touch,Turn,Unbalance,Unearth,Write";
	private static WeightedTable<String> traptriggers;

	private static void populateAllTables() {
		entrances = new WeightedTable<String>().populate(ENTRANCES,",");
		forms = new WeightedTable<String>().populate(FORMS,",");
		layouts = new WeightedTable<String>().populate(LAYOUTS,",");
		ruinations = new WeightedTable<String>().populate(RUINATIONS,",");
		rewards = new WeightedTable<String>().populate(REWARDS,",");
		activities = new WeightedTable<String>().populate(ACTIVITIES,",");
		rooms = new WeightedTable<String>().populate(ROOMS,",");
		details = new WeightedTable<String>().populate(DETAILS,",");
		tricks = new WeightedTable<String>().populate(TRICKS,",");
		hazards = new WeightedTable<String>().populate(HAZARDS,",");
		trapeffects = new WeightedTable<String>().populate(TRAPEFFECTS,",");
		traptriggers = new WeightedTable<String>().populate(TRAPTRIGGERS,",");
	}
	

	public static String getEntrance(Indexible obj) {
		if(entrances==null) populateAllTables();
		return entrances.getByWeight(obj);
	}
	public static String getForm(Indexible obj) {
		if(forms==null) populateAllTables();
		return Util.formatTableResult(forms.getByWeight(obj),obj);
	}
	public static String getLayout(Indexible obj) {
		if(layouts==null) populateAllTables();
		return layouts.getByWeight(obj);
	}
	public static String getRuination(Indexible obj) {
		if(ruinations==null) populateAllTables();
		return Util.formatTableResult(ruinations.getByWeight(obj),obj);
	}
	public static String getReward(Indexible obj) {
		if(rewards==null) populateAllTables();
		return Util.formatTableResult(rewards.getByWeight(obj),obj);
	}
	public static String getActivity(Indexible obj) {
		if(activities==null) populateAllTables();
		return Util.formatTableResult(activities.getByWeight(obj),obj);
	}
	public static String getRoom(Indexible obj) {
		if(rooms==null) populateAllTables();
		return Util.formatTableResult(rooms.getByWeight(obj),obj);
	}
	public static String getDetail(Indexible obj) {
		if(details==null) populateAllTables();
		return details.getByWeight(obj);
	}
	public static String getTrick(Indexible obj) {
		if(tricks==null) populateAllTables();
		return Util.formatTableResult(tricks.getByWeight(obj),obj);
	}
	public static String getHazard(Indexible obj) {
		if(hazards==null) populateAllTables();
		return hazards.getByWeight(obj);
	}
	public static String getTrapEffect(Indexible obj) {
		if(trapeffects==null) populateAllTables();
		return trapeffects.getByWeight(obj);
	}
	public static String getTrapTrigger(Indexible obj) {
		if(traptriggers==null) populateAllTables();
		return traptriggers.getByWeight(obj);
	}
	public static String getTrap(Indexible obj) {
		return getTrapEffect(obj)+" triggered by "+getTrapTrigger(obj);
	}



	public DungeonModel(SaveRecord record) {
		super(record);
	}
	
	public int[] getDungeonPositions(Point p) {
		float[] floats = new float[1];
		for(int n=0;n<floats.length;n++) {
			floats[n] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+n), p.x, p.y);
		}
		Indexible obj = new Indexible(floats);
		int[] result = new int[InfoPanel.POICOUNT];
		for(int n=0;n<InfoPanel.DUNGEONCOUNT;n++) {
			int index=obj.reduceTempId(InfoPanel.POICOUNT);
			result[index]++;
		}
		return result;
	}

	public Dungeon getDungeon(int i,Point p) {
		float[] floats = new float[TABLECOUNT];
		for(int n=0;n<floats.length;n++) {
			floats[n] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+n+i*TABLECOUNT), p.x, p.y);
		}
		Dungeon result = new Dungeon(floats);
		populateDungeonDetail(p, result);
		return result;
	}
	public Dungeon getDungeon(Random random,Point p) {
		int[] ints = new int[TABLECOUNT];
		for(int n=0;n<ints.length;n++) {
			ints[n] = random.nextInt();
		}
		Dungeon result = new Dungeon(ints);
		populateDungeonDetail(p, result);
		return result;
	}
	private void populateDungeonDetail(Point p, Dungeon result) {
		result.setEntrance(getEntrance(result));
		result.setLocation(new Reference(Util.formatTableResultPOS("${location index}", result, p,record.getZero())));
		result.setForm(getForm(result));
		result.setLayout(getLayout(result));
		result.setRuination(new Reference(HexData.HISTORY, record.normalizePOS(p), result.reduceTempId(4)).toString());
		result.setReward(Util.formatTableResultPOS(getReward(result),result,p,record.getZero()));
		result.setTrick(getTrick(result));
		result.setHazard(getHazard(result));
		result.setTrap(getTrap(result));
		result.setMonster(MonsterModel.getMonster(result));
	}
	@Override
	public Dungeon getDefaultValue(Point p, int i) {
		return getDungeon(i, p);
	}
}
