package data.magic;

import java.awt.Color;
import java.awt.Point;

import data.DataModel;
import data.Indexible;
import data.OpenSimplex2S;
import data.WeightedTable;
import io.SaveRecord;
import util.Util;

public class MagicModel extends DataModel{
	public static final int LOCAL_WEIGHT_1 = 10;
	public static final int LOCAL_WEIGHT_2 = 1;
	public static final int SEED_OFFSET = 3*Util.getOffsetX();
	private static final String WEIRDNESS = "Ashen,Blasted,Blighted,Broken,Consuming,Corrupted,Creeping,Desolate,${dungeon ruination},Eternal,${ethereal effect},Forsaken,"+
			"Frozen,Haunted,Howling,Jagged,Lonely,Misty,Perilous,Petrified,Phantasmal,Ravenous,Savage,Shadowy,"+
			"Shifting,Shivering,Sinister,Sinking,Smoldering,Sweltering,Thorny,Thundering,Torrential,${physical effect},Wandering,Withered";
	private static WeightedTable<String> weirdness;
	private static final String SPELLS = "${physical effect} ${physical form},${physical effect} ${ethereal form},${ethereal effect} ${physical form},${ethereal effect} ${ethereal form},"+
			"${physical element} ${physical form},${physical element} ${ethereal form},${ethereal element} ${physical form},${ethereal element} ${ethereal form},"+
			"${physical effect} ${physical element},${physical effect} ${ethereal element},${ethereal effect} ${physical element},${ethereal effect} ${ethereal element}";
	private static WeightedTable<String> spells;
	private static final String PHYSICALEFFECTS = "Animating,Attracting,Binding,Blossoming,Consuming,Creeping,Crushing,Diminishing,Dividing,Duplicating,Enveloping,Expanding,"+
			"Fusing,Grasping,Hastening,Hindering,Illuminating,Imprisoning,Levitating,Opening,Petrifying,Phasing,Piercing,Pursuing,"+
			"Reflecting,Regenerating,Rending,Repelling,Resurrecting,Screaming,Sealing,Shapeshifting,Shielding,Spawning,Transmuting,Transporting";
	private static WeightedTable<String> physicaleffects;
	private static final String ETHEREALEFFECTS = "Avenging,Banishing,Bewildering,Blinding,Charming,Communicating,Compelling,Concealing,Deafening,Deceiving,Deciphering,Disgusting,"+
			"Dispelling,Emboldening,Encoding,Energizing,Enlightening,Enraging,Excrutiating,Foreseeing,Intoxicating,Maddening,Mesmerizing,Mindreading,"+
			"Nullifying,Paralyzing,Revealing,Revolting,Scrying,Silencing,Soothing,Summoning,Terrifying,Warding,Wearying,Withering";
	private static WeightedTable<String> etherealeffects;
	private static final String PHYSICALELEMENTS = "Acid,Amber,Bark,Blood,Bone,Brine,Clay,Crow,Crystal,Ember,Flesh,Fungus,Glass,Honey,Ice,Insect,Wood,Lava,"+
			"Moss,Obsidian,Oil,Poison,Rat,Salt,Sand,Sap,Serpent,Slime,Stone,Tar,Thorn,Vine,Water,Wine,Wood,Worm";
	private static WeightedTable<String> physicalelements;
	private static final String ETHEREALELEMENTS = "Ash,Chaos,Distortion,Dream,Dust,Echo,Ectoplasm,Fire,Fog,Ghost,Harmony,Heat,Light,Lightning,Memory,Mind,Mutation,Negation"+
			"Plague,Plasma,Probability,Rain,Rot,Shadow,Smoke,Snow,Soul,Star,Stasis,Steam,Thunder,Time,Void,Warp,Whisper,Wind";
	private static WeightedTable<String> etherealelements;
	private static final String PHYSICALFORMS = "Altar,Armor,Arrow,Beast,Blade,Cauldron,Chain,Chariot,Claw,Cloak,Colossus,Crown,Elemental,Eye,Fountain,Gate,Golem,Hammer,"+
			"Horn,Key,Mask,Monolith,Pit,Prison,Sentinel,Servant,Shield,Spear,Steed,Swarm,Tentacle,Throne,Torch,Trap,Wall,Web";
	private static WeightedTable<String> physicalforms;
	private static final String ETHEREALFORMS = "Aura,Beacon,Beam,Blast,Blob,Bolt,Bubble,Call,Cascade,Circle,Cloud,Coil,Cone,Cube,Dance,Disk,Field,Form,"+
			"Gaze,Loop,Moment,Nexus,Portal,Pulse,Pyramid,Ray,Shard,Sphere,Spray,Storm,Swarm,Torrent,Touch,Vortex,Wave,Word";
	private static WeightedTable<String> etherealforms;
	private static final String MUTATIONS = "Ages,Attracts birds,Child-form,Corpulence,Covered in hair,${animal} arms,${animal} eyes,${animal} head,${animal} legs,${animal} mouth,${animal} skin,${animal}-form,"+
			"Cyclops,Extra arms,Extra eyes,Extra legs,Forked tongue,Gender swap,Hunchback,${item}-form,Long arms,Lose all hair,Lose teeth,${monster feature},"+
			"${monster trait},No eyes,No mouth,${physical element}-skin,Second face,Sheds skin,Shrinks,Shrivels,Skin boils,Slime trail,Translucent skin,Weeps blood";
	private static WeightedTable<String> mutations;
	private static final String INSANITIES = "Always lies,Is always polite,Imagines ${animal}-form,Cannot count,Cannot lie,Can't see faces,Fears birds,Fears blood,Fears books,Fears darkness,Fears fire,Fears gold,"+
			"Fears horses,Fears iron,Fears music,Fears own hand,Fears allies,Fears rain,Fears rivers,Fears silence,Fears Sleep,Fears sunlight,Fears the moon,Fears trees,"+
			"Imagines genius,Imagines beauty,Hates violence,Imagines invisibility,Imagines invulnerability,Imagines \"${monster ability}\",Imagines \"${monster feature}\",Imagines \"${monster trait}\",Must sing,is ${personality},Says thoughts,Sees dead people";
	private static WeightedTable<String> insanities;
	private static final String OMENS = "All iron rusts,Animals die,Animals mutate (${mutation}),Birds attack,Cities appear,Fog is deadly,Dreams plague all,Night is endless,Rain is endless,Storms are endless,Twilight is endless,Winter is endless,"+
			"Monsters appear,Forests appear,Forgetfulness plagues all,Graves open,Lamentations renew,Maggots thrive,Insanity plagues all: ${insanity},Mutations plague all: ${mutation},Slumber plagues all,Meteors strike,Mirrors speak,Stars disappear,"+
			"Strangers appear,People shrink,People vanish,Plants wither,Portals open,Rifts open,Shadows speak,Space distorts,Stones speak,Silence is unbreakable,Towers appear,Water turns to blood";
	private static WeightedTable<String> omens;
	private static final String SPELLCASTERS = "${wizard},${warlock},${sorcerer},${cleric},${druid},${bard}";
	private static WeightedTable<String> spellcasters;
	private static final String WIZARDS = "Abjurer,Swordmage,Chronomancer,Conjurer,Diviner,Enchanter,Evoker,Gravimancer,Illusionist,Necromancer,Archivist,Transmuter,Warmage";
	private static WeightedTable<String> wizards;
	private static final String WARLOCKS = "Fey-touched,Divine messenger,Deep one,Diabolist,Wish-master,Occultist,Hexblade,Immortal servant";
	private static WeightedTable<String> warlocks;
	private static final String SORCERERS = "Aberrant sorcerer,Clockwork sorcerer,Draconic sorcerer,Divine sorcerer,Lunar sorcerer,Shadow sorcerer,Storm sorcerer,Wild magic sorcerer";
	private static WeightedTable<String> sorcerers;
	private static final String CLERICS = "Cleric of ${domain}";
	private static WeightedTable<String> clerics;
	private static final String DRUIDS = "Druid of Dreams,Druid of the Land,Druid of the Moon,Druid of the Shepard,Druid of Spores,Druid of the Stars,Druid of Wildfire";
	private static WeightedTable<String> druids;
	private static final String BARDS = "Bard of Creation,Bard of Eloquence,Bard of Glamour,Bard of Lore,Bard of Spirits,Bard of Swords,Bard of Valour,Bard of Whispers";
	private static WeightedTable<String> bards;
	private static final String ARTIFICER = "Armorer,Artillerist,Battlesmith,Alchemist";
	private static WeightedTable<String> artificer;
	private static final String MAGIC_ADJECTIVE = "${weirdness},${dungeon layout},${dungeon ruination},${dungeon trick},${effect}";
	private static WeightedTable<String> adjective;
	


	private static void populateAllTables() {
		weirdness = new WeightedTable<String>();
		populate(weirdness, WEIRDNESS, ",");
		spells = new WeightedTable<String>();
		populate(spells, SPELLS, ",");
		physicaleffects = new WeightedTable<String>();
		populate(physicaleffects, PHYSICALEFFECTS, ",");
		etherealeffects = new WeightedTable<String>();
		populate(etherealeffects, ETHEREALEFFECTS, ",");
		physicalelements = new WeightedTable<String>();
		populate(physicalelements, PHYSICALELEMENTS, ",");
		etherealelements = new WeightedTable<String>();
		populate(etherealelements, ETHEREALELEMENTS, ",");
		physicalforms = new WeightedTable<String>();
		populate(physicalforms, PHYSICALFORMS, ",");
		etherealforms = new WeightedTable<String>();
		populate(etherealforms, ETHEREALFORMS, ",");
		mutations = new WeightedTable<String>();
		populate(mutations, MUTATIONS, ",");
		insanities = new WeightedTable<String>();
		populate(insanities, INSANITIES, ",");
		omens = new WeightedTable<String>();
		populate(omens, OMENS, ",");
		spellcasters = new WeightedTable<String>();
		populate(spellcasters, SPELLCASTERS, ",");
		wizards = new WeightedTable<String>();
		populate(wizards, WIZARDS, ",");
		warlocks = new WeightedTable<String>();
		populate(warlocks, WARLOCKS, ",");
		sorcerers = new WeightedTable<String>();
		populate(sorcerers, SORCERERS, ",");
		clerics = new WeightedTable<String>();
		populate(clerics, CLERICS, ",");
		druids = new WeightedTable<String>();
		populate(druids, DRUIDS, ",");
		bards = new WeightedTable<String>();
		populate(bards, BARDS, ",");
		artificer = new WeightedTable<String>();
		populate(artificer, ARTIFICER, ",");
		adjective = new WeightedTable<String>();
		populate(adjective, MAGIC_ADJECTIVE, ",");
	}
	@Deprecated
	public static String getWeirdness(int index) {
		if(weirdness==null) populateAllTables();
		return Util.formatTableResult(weirdness.getByWeight(index),new Indexible(index/weirdness.size()));
	}
	@Deprecated
	public static String getSpell(int index) {
		if(spells==null) populateAllTables();
		return Util.formatTableResult(spells.getByWeight(index),new Indexible(index/spells.size()));
	}
	@Deprecated
	public static String getPhysicalEffect(int index) {
		if(physicaleffects==null) populateAllTables();
		return physicaleffects.getByWeight(index);
	}
	@Deprecated
	public static String getEtherealEffect(int index) {
		if(etherealeffects==null) populateAllTables();
		return etherealeffects.getByWeight(index);
	}
	@Deprecated
	public static String getEffect(int index) {
		if(physicaleffects==null) populateAllTables();
		if(index%2==0) return getPhysicalEffect(index/2);
		else return getEtherealEffect(index/2);
	}
	@Deprecated
	public static String getPhysicalElement(int index) {
		if(physicalelements==null) populateAllTables();
		return physicalelements.getByWeight(index);
	}
	@Deprecated
	public static String getEtherealElement(int index) {
		if(etherealelements==null) populateAllTables();
		return etherealelements.getByWeight(index);
	}
	@Deprecated
	public static String getElement(int index) {
		if(physicalelements==null) populateAllTables();
		if(index%2==0) return getPhysicalElement(index/2);
		else return getEtherealElement(index/2);
	}
	@Deprecated
	public static String getPhysicalForm(int index) {
		if(physicalforms==null) populateAllTables();
		return physicalforms.getByWeight(index);
	}
	@Deprecated
	public static String getEtherealForm(int index) {
		if(etherealforms==null) populateAllTables();
		return etherealforms.getByWeight(index);
	}
	@Deprecated
	public static String getForm(int index) {
		if(physicalforms==null) populateAllTables();
		if(index%2==0) return getPhysicalForm(index/2);
		else return getEtherealForm(index/2);
	}
	@Deprecated
	public static String getMutation(int index) {
		if(mutations==null) populateAllTables();
		return Util.formatTableResult(mutations.getByWeight(index),new Indexible(index/mutations.size()));
	}
	@Deprecated
	public static String getInsanity(int index) {
		if(insanities==null) populateAllTables();
		return Util.formatTableResult(insanities.getByWeight(index),new Indexible(index/insanities.size()));
	}
	@Deprecated
	public static String getOmen(int index) {
		if(omens==null) populateAllTables();
		return Util.formatTableResult(omens.getByWeight(index),new Indexible(index/omens.size()));
	}
	@Deprecated
	public static String getSpellcaster(int index) {
		if(spellcasters==null) populateAllTables();
		return Util.formatTableResult(spellcasters.getByWeight(index),new Indexible(index/spellcasters.size()));
	}
	@Deprecated
	public static String getWizard(int index) {
		if(wizards==null) populateAllTables();
		return wizards.getByWeight(index);
	}
	@Deprecated
	public static String getWarlock(int index) {
		if(warlocks==null) populateAllTables();
		return warlocks.getByWeight(index);
	}
	@Deprecated
	public static String getSorcerer(int index) {
		if(sorcerers==null) populateAllTables();
		return sorcerers.getByWeight(index);
	}
	@Deprecated
	public static String getCleric(int index) {
		if(clerics==null) populateAllTables();
		return Util.formatTableResult(clerics.getByWeight(index),new Indexible(index/clerics.size()));
	}
	@Deprecated
	public static String getDruid(int index) {
		if(druids==null) populateAllTables();
		return Util.formatTableResult(druids.getByWeight(index),new Indexible(index/druids.size()));
	}
	@Deprecated
	public static String getBard(int index) {
		if(bards==null) populateAllTables();
		return Util.formatTableResult(bards.getByWeight(index),new Indexible(index/bards.size()));
	}
	public static String getAdjective(Indexible obj) {
		if(adjective==null) populateAllTables();
		return Util.formatTableResult(adjective.getByWeight(obj),obj);
	}
	
	

	public static String getWeirdness(Indexible obj) {
		if(weirdness==null) populateAllTables();
		return Util.formatTableResult(weirdness.getByWeight(obj),obj);
	}
	public static String getSpell(Indexible obj) {
		if(spells==null) populateAllTables();
		return Util.formatTableResult(spells.getByWeight(obj),obj);
	}
	public static String getPhysicalEffect(Indexible obj) {
		if(physicaleffects==null) populateAllTables();
		return physicaleffects.getByWeight(obj);
	}
	public static String getEtherealEffect(Indexible obj) {
		if(etherealeffects==null) populateAllTables();
		return etherealeffects.getByWeight(obj);
	}
	public static String getEffect(Indexible obj) {
		if(physicaleffects==null) populateAllTables();
		int i = obj.reduceTempId(2);
		if(i==0) return getPhysicalEffect(obj);
		else return getEtherealEffect(obj);
	}
	public static String getPhysicalElement(Indexible obj) {
		if(physicalelements==null) populateAllTables();
		return physicalelements.getByWeight(obj);
	}
	public static String getEtherealElement(Indexible obj) {
		if(etherealelements==null) populateAllTables();
		return etherealelements.getByWeight(obj);
	}
	public static String getElement(Indexible obj) {
		if(physicalelements==null) populateAllTables();
		int i = obj.reduceTempId(2);
		if(i==0) return getPhysicalElement(obj);
		else return getEtherealElement(obj);
	}
	public static String getPhysicalForm(Indexible obj) {
		if(physicalforms==null) populateAllTables();
		return physicalforms.getByWeight(obj);
	}
	public static String getEtherealForm(Indexible obj) {
		if(etherealforms==null) populateAllTables();
		return etherealforms.getByWeight(obj);
	}
	public static String getForm(Indexible obj) {
		if(physicalforms==null) populateAllTables();
		int i = obj.reduceTempId(2);
		if(i==0) return getPhysicalForm(obj);
		else return getEtherealForm(obj);
	}
	public static String getMutation(Indexible obj) {
		if(mutations==null) populateAllTables();
		return Util.formatTableResult(mutations.getByWeight(obj),obj);
	}
	public static String getInsanity(Indexible obj) {
		if(insanities==null) populateAllTables();
		return Util.formatTableResult(insanities.getByWeight(obj),obj);
	}
	public static String getOmen(Indexible obj) {
		if(omens==null) populateAllTables();
		return Util.formatTableResult(omens.getByWeight(obj),obj);
	}
	public static String getSpellcaster(Indexible obj) {
		if(spellcasters==null) populateAllTables();
		return Util.formatTableResult(spellcasters.getByWeight(obj),obj);
	}
	public static String getWizard(Indexible obj) {
		if(wizards==null) populateAllTables();
		return wizards.getByWeight(obj);
	}
	public static String getWarlock(Indexible obj) {
		if(warlocks==null) populateAllTables();
		return warlocks.getByWeight(obj);
	}
	public static String getSorcerer(Indexible obj) {
		if(sorcerers==null) populateAllTables();
		return sorcerers.getByWeight(obj);
	}
	public static String getCleric(Indexible obj) {
		if(clerics==null) populateAllTables();
		return Util.formatTableResult(clerics.getByWeight(obj),obj);
	}
	public static String getDruid(Indexible obj) {
		if(druids==null) populateAllTables();
		return Util.formatTableResult(druids.getByWeight(obj),obj);
	}
	public static String getBard(Indexible obj) {
		if(bards==null) populateAllTables();
		return Util.formatTableResult(bards.getByWeight(obj),obj);
	}
	public static String getArtificer(Indexible obj) {
		if(artificer==null) populateAllTables();
		return Util.formatTableResult(artificer.getByWeight(obj),obj);
	}
	
	
	
	public MagicModel(SaveRecord record) {
		super(record);
	}
	
	private float getMagicValue(Point p) {
		float local1 = (OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET), Util.getNScale()*p.x, Util.getNScale()*p.y));
		float local2 = (OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+1), Util.getLScale()*p.x, Util.getLScale()*p.y));
		return (LOCAL_WEIGHT_1*local1+LOCAL_WEIGHT_2*local2)/(LOCAL_WEIGHT_1+LOCAL_WEIGHT_2);
	}
	private float getWeirdnessValue(Point p) {
		return (OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+2), p.x, p.y));
	}
	private int getWeirdnessIndex(int i,Point p) {
		float val = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+4+i), p.x, p.y);
		return Util.getIndexFromSimplex(val);
	}
	
	public MagicType getMagicType(Point p) {
		float magicFactor = getMagicValue(p);
		return MagicType.getMagicType(magicFactor);
	}

	public Color getColor(int i, int j) {
		MagicType type = getMagicType(new Point(i,j));
		Color color = type.getColor();
		if(isWeird(new Point(i,j))) {
	        float[] hsb = new float[3];
	        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
			float h = hsb[0];
			float s = hsb[1]/2;
			float b = hsb[2]+(1-hsb[2])/2;
			color = Color.getHSBColor(h, s, b);
		}
		return color;
	}

	public boolean isWeird(Point p) {
		return isWeird(p,0);
	}

	public boolean isWeird(Point p,int i) {
		Indexible obj = new Indexible(getWeirdnessValue(p));
		MagicType type = getMagicType(p);
		int roll = obj.reduceTempId(20);
		for(int count = 0;count<i;count++) {
			roll = obj.reduceTempId(20);
		}
		if(MagicType.WILDMAGIC.equals(type)) {
			return roll>1; //90%
		}else if(MagicType.HIGHMAGIC.equals(type)) {
			return roll>9; //50%
		}else if(MagicType.NORMALMAGIC.equals(type)) {
			return roll>16; //20%
		}else if(MagicType.LOWMAGIC.equals(type)) {
			return roll>19; //5%
		}else {
			return false;
		}
	}
	public String getWeirdness(Point p) {
		return getWeirdness(p,0);
	}
	public String getWeirdness(Point p,int i) {
		return getWeirdness(getWeirdnessIndex(i, p));
	}
	public String getAdjective(Point p,int i) {
		return getAdjective(new Indexible(getWeirdnessIndex(i, p)));
	}
	@Override
	public Float getDefaultValue(Point p, int i) {
		return getMagicValue(p);
	}
}
