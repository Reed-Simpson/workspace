package data.monster;

import java.awt.Point;
import java.util.Random;

import controllers.DataController;
import data.Indexible;
import data.OpenSimplex2S;
import data.WeightedTable;
import data.biome.BiomeType;
import data.monster.subtype.BeastType;
import data.population.Species;
import io.SaveRecord;
import util.Util;

public class MonsterModel {
	private static final int SEED_OFFSET = 13*Util.getOffsetX();
	private static final int TABLECOUNT = 3;
	private static final String ANIMALS = "${aerial},${terrestrial},${aquatic}";
	private static WeightedTable<String> animals;
	private static final String AERIAL = "Albatross,Bat,Beetle,Bird of paradise,Butterfly,Condor,Crane,Crow,Dragonfly,Eagle,Falcon,Firefly,"+
			"Flamingo,Fly,Flying squirrel,Goose,Gull,Hummingbird,Kingfisher,Locust,Magpie,Mantis,Mockingbird,Mosquito,"+
			"Moth,Owl,Parrot,Peacock,Pelican,Pteranodon,Rooster,Sparrow,Swan,Vulture,Wasp,Woodpecker";
	private static WeightedTable<String> aerial;
	private static final String TERRESTRIAL = "Ant,Ape,Armadillo,Badger,Bear,Boar,Caterpillar,Centipede,Chameleon,Cockroach,Deer,Elephant,"+
			"Ferret,Fox,Giraffe,Goat,Horse,Human,Mole,Ostrich,Ox,Porcupine,Rabbit,Raccoon,"+
			"Rat,Rhinoceros,Scorpion,Sheep,Slug,Snail,Snake,Spider,Squirrel,Tiger,Wolf,Wolverine";
	private static WeightedTable<String> terrestrial;
	private static final String AQUATIC = "Alligator,Amoeba,Anglerfish,Beaver,Clam,Crab,Dolphin,Eel,Frog,Hippopotamus,Jellyfish,Leech,"+
			"Lobster,Manatee,Manta ray,Muskrat,Narwhal,Newt,Octopus,Otter,Penguin,Platypus,Pufferfish,Salamander,"+
			"Sea anemone,Sea urchin,Seahorse,Seal,Shark,Shrimp,Squid,Swordfish,Tadpole,Turtle,Walrus,Whale";
	private static WeightedTable<String> aquatic;
	public static final String FEATURES = "Antlers,Beak,Carapace,Claws,Compound eyes,Eye stalks,Fangs,Fins,Fur,Gills,Hooves,Horns,"+
			"Legless,Long tongue,Many-eyed,Many-limbed,Mucus,Pincers,Plates,Plumage,Probiscus,Scales,Segments,Shaggy hair,"+
			"Shell,Spikes,Spinnerets,Spines,Stinger,Suction cups,Tail,Talons,Tentacles,Trunk,Tusks,Wings";
	private static WeightedTable<String> features;
	private static final String TRAITS = "Amphibious,Bloated,Brittle,Cannibal,Clay-like,Colossal,Crystalline,Decaying,${ethereal element},Ethereal,Ever-young,Eyeless,"+
			"Fearless,Fluffy,Fungal,Gelatinous,Geometric,Hardened,Illusory,Intelligent,Iridescent,Luminous,Many-headed,Mechanical,"+
			"${physical element},Planar,Reflective,Rubbery,Shadowy,Sharp,Skeletal,Slimy,Sticky,Stinking,Tiny,Translucent";
	private static WeightedTable<String> traits;
	private static final String ABILITIES = "Absorbing,Acid blood,Anti-magic,Blinding,Breath weapon,Camoflaging,Duplicating,Electric,Entangling,${ethereal effect},Exploding,Flying,"+
			"Gaze weapon,Hypnotizing,Impervious,Invisible,Life-draining,Magnetic,Mimicking,Mind-reading,Paralyzing,Phasing,${physical effect},Venomous,"+
			"Radioactive,Reflective,Regenerating,Shapeshifting,Spell-casting,Stealthy,Strangling,Super-strength,Telekinetic,Teleporting,Vampiric,Wall-crawling";
	private static WeightedTable<String> abilities;
	private static final String TACTICS = "Ambush,Call for support,Capture,Charge,Climb foes,Compel worship,Create barrier,Decieve,Demand duel,Disorient,Encircle,Evade,"+
			"Gang up,Gather strength,Go berserk,Harry,Hurl foes,Immobilize,Manipulate,Mock,Monologue,Order minion,Protect leader,Protect self,"+
			"Scatter foes,Stalk,Steal from,Swarm,Target insolent,Target leader,Target nearest,Target riches,Target strongest,Target weakest,Toy with,Use terrain";
	private static WeightedTable<String> tactics;
	private static final String PERSONALITIES = "Alien,Aloof,Bored,Cautious,Cowardly,Curious,Devious,Distractable,Educated,Embittered,Envious,Erudite,"+
			"Fanatical,Forgetful,Generous,Hateful,Honorable,Humble,Jaded,Jovial,Legalistic,Manipulative,Megalomaniacal,Melancholy,"+
			"Meticulous,Mystical,Obsessive,Out of touch,Paranoid,Polite,Psychopathic,Sophisticated,Touchy,Unimpressed,Vain,Xenophobic";
	private static WeightedTable<String> personalities;
	private static final String WEAKNESSES = "Bells,Birdsong,Children,Cold,Cold iron,Competition,Conversation,Deformity,Flattery,Flowers,Gifts,Gold,"+
			"Heat,Holy icon,Water,Home cooking,${insanity},Mirrors,Mistletoe,Moonlight,Music,${method},Phylactery,${physical element},"+
			"Puzzles,Riddles,Rituals,Silver,Sunlight,Tears,True name,${rare material},Weak spot,${weapon},Wine,Wormwood";
	private static WeightedTable<String> weaknesses;

	private static void populate(WeightedTable<String> table,String values,String regex) {
		for(String s:values.split(regex)) {
			table.put(s);
		}
	}
	private static void populateAllTables() {
		animals = new WeightedTable<String>();
		populate(animals,ANIMALS,",");
		aerial = new WeightedTable<String>();
		populate(aerial,AERIAL,",");
		terrestrial = new WeightedTable<String>();
		populate(terrestrial,TERRESTRIAL,",");
		aquatic = new WeightedTable<String>();
		populate(aquatic,AQUATIC,",");
		features = new WeightedTable<String>();
		populate(features,FEATURES,",");
		traits = new WeightedTable<String>();
		populate(traits,TRAITS,",");
		abilities = new WeightedTable<String>();
		populate(abilities,ABILITIES,",");
		tactics = new WeightedTable<String>();
		populate(tactics,TACTICS,",");
		personalities = new WeightedTable<String>();
		populate(personalities,PERSONALITIES,",");
		weaknesses = new WeightedTable<String>();
		populate(weaknesses,WEAKNESSES,",");
	}

	public static String getAnimal(Indexible obj) {
		if(animals==null) populateAllTables();
		return Util.formatTableResult(animals.getByWeight(obj),obj);
	}
	public static String getAerial(Indexible obj) {
		if(aerial==null) populateAllTables();
		return aerial.getByWeight(obj);
	}
	public static String getTerrestrial(Indexible obj) {
		if(terrestrial==null) populateAllTables();
		return terrestrial.getByWeight(obj);
	}
	public static String getAquatic(Indexible obj) {
		if(aquatic==null) populateAllTables();
		return aquatic.getByWeight(obj);
	}
	public static String getFeature(Indexible obj) {
		if(features==null) populateAllTables();
		return features.getByWeight(obj);
	}
	public static String getTrait(Indexible obj) {
		if(traits==null) populateAllTables();
		return Util.formatTableResult(traits.getByWeight(obj),obj);
	}
	public static String getAbility(Indexible obj) {
		if(abilities==null) populateAllTables();
		return Util.formatTableResult(abilities.getByWeight(obj),obj);
	}
	public static String getTactic(Indexible obj) {
		if(tactics==null) populateAllTables();
		return tactics.getByWeight(obj);
	}
	public static String getPersonality(Indexible obj) {
		if(personalities==null) populateAllTables();
		return personalities.getByWeight(obj);
	}
	public static String getWeakness(Indexible obj) {
		if(weaknesses==null) populateAllTables();
		return Util.formatTableResult(weaknesses.getByWeight(obj),obj);
	}
	
	public static String getMonster(Indexible obj) {
		String animal = getAnimal(obj);
		String feature = getFeature(obj);
		String trait = getTrait(obj);
		String ability = getAbility(obj);
		String tactic = getTactic(obj);
		String personality = getPersonality(obj);
		String weakness = getWeakness(obj);
		return trait+", "+(personality+" "+animal+"(s) with "+ability+" "+feature).toLowerCase()+". Uses "+(tactic+" tactics and has a weakness to "+weakness+".").toLowerCase();
	}
	
	public Species getWanderingMonster(Point territoryRef,int i,BiomeType biome) {
		WeightedTable<Species> species = BeastType.getSpecies(biome.getHabitat());
		Indexible obj = getIndexible(territoryRef, biome.getID());
		return species.getByWeight(obj);
	}
	
	public Species getWanderingMonster(Random rand,BiomeType biome) {
		WeightedTable<Species> species = BeastType.getSpecies(biome.getHabitat());
		Indexible obj = getIndexible(rand);
		return species.getByWeight(obj);
	}
	
	public Point getTerritoryRef(Point p,int i) {
		int x = p.x/50;
		int y = p.y/50;
		x = x + (x+i/2)%2;
		y = y + (y+i)%2;
		return new Point(x*2,y*2);
	}

	private SaveRecord record;
	public MonsterModel(DataController controller) {
		this.record = controller.getRecord();
	}

	public Indexible getIndexible(Point p,int index) {
		float[] floats = new float[TABLECOUNT];
		for(int n=0;n<floats.length;n++) {
			floats[n] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+n+index*TABLECOUNT), p.x, p.y);
		}
		return new Indexible(floats);
	}
	public Indexible getIndexible(Random rand) {
		float[] floats = new float[TABLECOUNT];
		for(int n=0;n<floats.length;n++) {
			floats[n] = rand.nextFloat();
		}
		return new Indexible(floats);
	}
}
