package data.monster;

import java.awt.Point;
import java.util.Random;

import controllers.DataController;
import data.Indexible;
import data.OpenSimplex2S;
import data.WeightedTable;
import data.biome.BiomeType;
import data.dungeon.DungeonModel;
import data.monster.subtype.BeastMonsterType;
import data.population.Species;
import data.threat.CreatureType;
import data.threat.Threat;
import io.SaveRecord;
import util.Pair;
import util.Util;

public class MonsterModel {
	public static final int BEASTCOUNT = 4;
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
			"No legs,Long tongue,Many eyes,Many limbs,Mucus,Pincers,Plates,Plumage,Probiscus,Scales,Segmented body,Shaggy hair,"+
			"Shell,Spikes,Spinnerets,Spines,Stinger,Suction cups,Tail,Talons,Tentacles,Trunk,Tusks,Wings";
	private static WeightedTable<String> features;
	private static final String TRAITS = "Amphibious,Bloated,Brittle,Cannibal,Clay-like,Colossal,Crystalline,Emaciated,${ethereal element}y,Ethereal,Immortal,Eyeless,"+
			"Fearless,Fluffy,Fungal,Gelatinous,Geometric,Hardened,Illusory,Intelligent,Iridescent,Luminous,Multiheaded,Mechanical,"+
			"${physical element}y,Cosmic,Reflective,Rubbery,Shadowy,Sharp,Skeletal,Slimy,Sticky,Stinking,Tiny,Translucent";
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
			"Meticulous,Mystical,Obsessive,Out of touch,Paranoid,Polite,Psychopathic,Sophisticated,Touchy,Unimpressed,Vain,Territorial";
	private static WeightedTable<String> personalities;
	private static final String WEAKNESSES = "Bells,Birdsong,Children,Cold,Cold iron,Competition,Conversation,Deformity,Flattery,Flowers,Gifts,Gold,"+
			"Heat,Holy icon,Water,Home cooking,${insanity},Mirrors,Mistletoe,Moonlight,Music,${method},Phylactery,${physical element},"+
			"Puzzles,Riddles,Rituals,Silver,Sunlight,Tears,True name,${rare material},Weak spot,${weapon},Wine,Wormwood";
	private static final double MEDIAN_HISTORY_YEARS = 1000;
	private static final double HISTORY_YEARS_RANGE = 2;
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
		Monster monster = new Monster(animal);

		int index = obj.reduceTempId(2);
		if(index==0) monster.setPersonality(getPersonality(obj));
		else monster.setTrait(getTrait(obj));

		index = obj.reduceTempId(2);
		if(index==0) monster.setFeature(getFeature(obj));
		else monster.setAbility(getAbility(obj));

		index = obj.reduceTempId(2);
		if(index==0) monster.setTactic(getTactic(obj));
		else monster.setWeakness(getWeakness(obj));
		return monster.toString();
	}

	private SaveRecord record;
	private DataController controller;
	public MonsterModel(DataController controller) {
		this.record = controller.getRecord();
		this.controller = controller;
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

	public Monster getWanderingMonster(Point territoryRef,int i,Pair<BiomeType,BiomeType> habitats) {
		WeightedTable<Species> species;
		int index;
		if(i<BEASTCOUNT) {
			species = BeastMonsterType.getSpecies(habitats.key1);
			index = habitats.key1.getID(0);
		}else {
			if(habitats.key2!=null) {
				species = BeastMonsterType.getSpecies(habitats.key2);
				if(habitats.key1==habitats.key2) index = habitats.key2.getID(1);
				else index = habitats.key2.getID(0);
			} else {
				return null;
			}
		}
		Indexible obj = getIndexible(territoryRef, index);
		Monster result = new Monster(species.getByWeight(obj));
		if(result.getSpecies()==null) return null;
		populateMonsterTrait(obj, result);
		return result;
	}

	public Monster getThreatMonster(Point p,int i,Pair<BiomeType,BiomeType> habitats) {
		WeightedTable<Species> species;
		if(i<BEASTCOUNT) {
			Threat threat = controller.getThreats().getThreat(p);
			species = CreatureType.getMonsterByWeight(threat.getType(), habitats.key1, threat);
		}else {
			if(habitats.key2!=null) {
				Threat threat = controller.getThreats().getThreat(p);
				species = CreatureType.getMonsterByWeight(threat.getType(), habitats.key2, threat);
			} else {
				return null;
			}
		}
		Indexible obj = getIndexible(p, i+BiomeType.count()*2);
		Monster result = new Monster(species.getByWeight(obj));
		if(result.getSpecies()==null) return null;
		result.setPersonality(getPersonality(obj));
		return result;
	}
	private void populateMonsterTrait(Indexible obj, Monster result) {
		int branch = obj.reduceTempId(3);
		if(branch==0) result.setTrait(getTrait(obj));
		else if(branch==1) result.setFeature(getFeature(obj));
		else result.setAbility(getAbility(obj));
	}

	public Monster getWanderingMonster(Random rand,int i,Pair<BiomeType,BiomeType> habitats,Threat threat) {
		WeightedTable<Species> species;
		if(i<BEASTCOUNT) {
			species = BeastMonsterType.getSpecies(habitats.key1);
		}else {
			species = BeastMonsterType.getSpecies(habitats.key2);
		}
		Indexible obj = getIndexible(rand);
		Monster result = new Monster(species.getByWeight(obj));
		populateMonsterTrait(obj, result);
		return result;
	}

	public Monster getThreatMonster(Random rand,int i,Pair<BiomeType,BiomeType> habitats,Threat threat) {
		WeightedTable<Species> species;
		if(i<BEASTCOUNT) {
			species = CreatureType.getMonsterByWeight(threat.getType(), habitats.key1, threat);
		}else {
			if(habitats.key2!=null) {
				species = CreatureType.getMonsterByWeight(threat.getType(), habitats.key2, threat);
			} else {
				return null;
			}
		}
		Indexible obj = getIndexible(rand);
		Monster result = new Monster(species.getByWeight(obj));
		populateMonsterTrait(obj, result);
		return result;
	}
	public Point getTerritoryRef(Point p,int i) {
		int y = p.y/50;
		int x = (p.x+p.y/2)/50;
		x = x + (x+i/2)%2;
		y = y + (y+i)%2;
		return new Point(x*2,y*2);
	}
	public String getRuination(Point territory) {
		Indexible obj = new Indexible(OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+BiomeType.count()*2+BEASTCOUNT*2), territory.x, territory.y));
		return DungeonModel.getRuination(obj)+getTimePeriod(obj);
	}
	public String getRuination(Random rand) {
		Indexible obj = new Indexible(rand.nextInt());
		return DungeonModel.getRuination(obj)+getTimePeriod(obj);
	}
	private String getTimePeriod(Indexible obj) {
		double age = obj.getDouble(100);
		double years = Math.pow(10, age*HISTORY_YEARS_RANGE)*MEDIAN_HISTORY_YEARS;
		//System.out.println(MEDIAN_HISTORY_YEARS+"*10^"+age+"="+years);
		String timeString;
		if(years<1) {
			timeString = " ("+(int)Math.floor(years*12)+" months ago)";
		}else {
			timeString = " ("+(int)Math.floor(years)+" years ago)";
		}
		return timeString;
	}
}
