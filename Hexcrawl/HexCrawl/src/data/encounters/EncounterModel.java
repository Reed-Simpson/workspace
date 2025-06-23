package data.encounters;

import java.awt.Point;
import java.util.Random;

import data.DataModel;
import data.Indexible;
import data.OpenSimplex2S;
import data.WeightedTable;
import data.dungeon.DungeonModel;
import data.location.LocationModel;
import data.population.PopulationModel;
import data.population.SettlementModel;
import io.SaveRecord;
import util.Util;

public class EncounterModel extends DataModel{
	//STATIC CONSTANTS
	private static final int SEED_OFFSET = 8*Util.getOffsetX();
	public static final String VERBS = "Abandon,Accompany,Activate,Agree,Ambush,Arrive,Assist,Attack,Attain,Bargain,Befriend,Bestow,Betray,Block,Break,Carry,Celebrate,Change,Close,Combine,"+
			"Communicate,Conceal,Continue,Control,Create,Deceive,Decrease,Defend,Delay,Deny,Depart,Deposit,Destroy,Dispute,Disrupt,Distrust,Divide,Drop,Easy,Energize,"+
			"Escape,Expose,Fail,Fight,Flee,Free,Guide,Harm,Heal,Hinder,Imitate,Imprison,Increase,Indulge,Inform,Inquire,Inspect,Invade,Leave,Lure,"+
			"Misuse,Move,Neglect,Observe,Open,Oppose,Overthrow,Praise,Proceed,Protect,Punish,Pursue,Recruit,Refuse,Release,Relinquish,Repair,Repulse,Return,Reward,"+
			"Ruin,Separate,Start,Stop,Strange,Struggle,Succeed,Support,Suppress,Take,Threaten,Transform,Trap,Travel,Triumph,Truce,Trust,Use,Usurp,Waste";
	public static final String NOUNS = "Advantage,Adversity,Agreement,Animal,Attention,Balance,Battle,Benefits,Building,Burden,Bureaucracy,Business,Chaos,Comfort,Completion,Conflict,Cooperation,Danger,Defense,Depletion,"+
			"Disadvantage,Distraction,Elements,Emotion,Enemy,Energy,Environment,Expectation,Exterior,Extravagance,Failure,Fame,Fear,Freedom,Friend,Goal,Group,Health,Hinderance,Home,"+
			"Hope,Idea,Illness,Illusion,Individual,Information,Innocent,Intellect,Interior,Investment,Leadership,Legal,Location,Military,Misfortune,Mundane,Nature,Needs,News,Normal,"+
			"Object,Obscurity,Official,Opposition,Ouside,Pain,Path,Peace,People,Personal,Physical,Plot,Portal,Possessions,Poverty,Power,Prison,Project,Protection,Reassurance,"+
			"Representative,Riches,Safety,Strength,Success,Suffering,Surprise,Tactic,Technology,Tension,Time,Trial,Value,Vehicle,Victory,Vulnerability,Weapon,Weather,Work,Wound";
	public static final String ADVERB = "Adventurously,Aggressively,Anxiously,Awkwardly,Beautifully,Bleakly,Boldly,Bravely,Busily,Calmly,Carefully,Carelessly,Cautiously,Ceaselessly,Cheerfully,Combatively,Coolly,Crazily,Curiously,Dangerously,"+
			"Defiantly,Deliberately,Delicately,Delightfully,Dimly,Efficiently,Emotionally,Energetically,Enormously,Enthusiastically,Excitedly,Fearfully,Ferociously,Fiercly,Foolishly,Fortunately,Frantically,Freely,Frighteningly,Fully,"+
			"Generously,Gently,Gladly,Gracefully,Gratefully,Happily,Hastily,Healthily,Helpfully,Helplessly,Hopelessly,Innocently,Intensly,Interestingly,Irritatingly,Joyfully,Kindly,Lazily,Lightly,Loosely,"+
			"Loudly,Lovingly,Loyally,Majestically,Meaningfully,Mechanically,Mildly,Miserably,Mockingly,Mysteriously,Naturally,Neatly,Nicely,Oddly,Offensively,Officially,Partially,Passively,Peacefully,Perfectly,"+
			"Playfully,Politely,Positively,Powerfully,Quaintly,Quarrelsomely,Quietly,Roughly,Rudely,Ruthlessly,Slowly,Softly,Strangely,Swiftly,Threateningly,Timidly,Very,Violently,Wildly,Yieldingly";
	public static final String ADJECTIVE = "Abnormal,Amusing,Artificial,Average,Beautiful,Bizarre,Boring,Bright,Broken,Clean,Cold,Colorful,Colorless,Comforting,Creepy,Cute,Damaged,Dark,Defeated,Dirty,"+
			"Disagreeable,Dry,Dull,Empty,Enormous,Extraordinary,Extravagant,Faded,Familiar,Fancy,Feeble,Festive,Flawless,Forlorn,Fragile,Fragrant,Fresh,Full,Glorious,Graceful,"+
			"Hard,Harsh,Healthy,Heavy,Historical,Horrible,Important,Interesting,Juvenile,Lacking,Large,Lavish,Lean,Less,Lethal,Lively,Lonely,Lovely,Magnificent,Mature,"+
			"Messy,Mighty,Military,Modern,Mundane,Mysterious,Natural,Normal,Odd,Old,Pale,Peaceful,Petite,Plain,Poor,Powerful,Protective,Quaint,Rare,Reassuring,"+
			"Remarkable,Rotten,Rough,Ruined,Rustic,Scary,Shocking,Simple,Small,Smooth,Soft,Strong,Stylish,Unpleasant,Valuable,Vibrant,Warm,Watery,Weak,Young";
	public static final String CHARACTER_ADJECTIVES = "Accompanied,Active,Aggressive,Ambush,Animal,Anxious,Armed,Beautiful,Bold,Busy,Calm,Careless,Casual,Cautious,Classy,Colorful,Combative,Crazy,Creepy,Curious,Dangerous,Deceitful,Defeated,"+
			"Defiant,Delightful,Emotional,Energetic,Equipped,Excited,Expected,Familiar,Fast,Feeble,Feminine,Ferocious,Foe,Foolish,Fortunate,Fragrant,Frantic,Friend,Frightened,Frightening,Generous,Glad,Happy,Harmful,Helpful,Helpless,Hurt,"+
			"Important,Inactive,Influential,Innocent,Intense,Knowledgeable,Large,Lonely,Loud,Loyal,Masculine,Mighty,Miserable,Multiple,Mundane,Mysterious,Natural,Odd,Official,Old,Passive,Peaceful,Playful,Powerful,Professional,"+
			"Protected,Protecting,Questioning,Quiet,Reassuring,Resourceful,Seeking,Skilled,Slow,Small,Stealthy,Strange,Strong,Tall,Thieving,Threatening,Triumphant,Unexpected,Unnatural,Unusual,Violent,Vocal,Weak,Wild,Young";
	public static final String OBJECT_ADJECTIVES = "Active,Artistic,Average,Beautiful,Bizarre,Bright,Clothing,Clue,Cold,Colorful,Communication,Complicated,Confusing,Consumable,Container,Creepy,Crude,Cute,Damaged,Dangerous,Deactivated,Deliberate,Delightful,"+
			"Desired,Domestic,Empty,Energy,Enormous,Equipment,Expected,Expended,Extravagant,Faded,Familiar,Fancy,Flora,Fortunate,Fragile,Fragrant,Frightening,Garbage,Guidance,Hard,Harmful,Healing,Heavy,Helpful,Horrible,Important,Inactive,"+
			"Information,Intriguing,Large,Lethal,Light,Liquid,Loud,Majestic,Meaningful,Mechanical,Modern,Moving,Multiple,Mundane,Mysterious,Natural,New,Odd,Official,Old,Ornate,Personal,Powerful,Prized,"+
			"Protection,Rare,Ready,Reassuring,Resource,Ruined,Small,Soft,Solitary,Stolen,Strange,Stylish,Threatening,Tool,Travel,Unexpected,Unpleasant,Unusual,Useful,Useless,Valuable,Warm,Weapon,Wet,Worn";
	private static final int TABLECOUNT = 14;
	private static WeightedTable<String> encounterChar;
	private static WeightedTable<String> encounterObj;
	private static WeightedTable<String> encounterAdverb;
	private static WeightedTable<String> encounterAdj;
	private static WeightedTable<String> encounterFocus;
	private static WeightedTable<String> encounterVerb;
	private static WeightedTable<String> encounterNoun;
	
	private static void populateEncounterFocus() {
		encounterFocus = new WeightedTable<String>();
		encounterFocus.put("Remote Event",5);
		encounterFocus.put("Ambiguous Event",5);
		encounterFocus.put("New NPC",10);
		encounterFocus.put("NPC Action",20);
		encounterFocus.put("NPC Negative",5);
		encounterFocus.put("NPC Positive",5);
		encounterFocus.put("Move Toward A Thread",5);
		encounterFocus.put("Move Away From A Thread",10);
		encounterFocus.put("Close A Thread",5);
		encounterFocus.put("PC Negative",10);
		encounterFocus.put("PC Positive",5);
		encounterFocus.put("Current Context",15);
	}
	public static String getFocus(Indexible e) {
		if(encounterFocus==null) populateAllTables();
		return encounterFocus.getByWeight(e);
	}
	private static void populateAllTables() {
		populateEncounterFocus();
		encounterVerb = new WeightedTable<String>();
		populate(encounterVerb,VERBS,",");
		encounterNoun = new WeightedTable<String>();
		populate(encounterNoun,NOUNS,",");
		encounterAdverb = new WeightedTable<String>();
		populate(encounterAdverb,ADVERB,",");
		encounterAdj = new WeightedTable<String>();
		populate(encounterAdj,ADJECTIVE,",");
		encounterChar = new WeightedTable<String>();
		populate(encounterChar,CHARACTER_ADJECTIVES,",");
		encounterObj = new WeightedTable<String>();
		populate(encounterObj,OBJECT_ADJECTIVES,",");
	}
	public static String getVerb(Indexible e) {
		if(encounterVerb==null) populateAllTables();
		return encounterVerb.getByWeight(e);
	}
	public static String getNoun(Indexible e) {
		if(encounterNoun==null) populateAllTables();
		return encounterNoun.getByWeight(e);
	}
	public static String getAdverb(Indexible e) {
		if(encounterAdverb==null) populateAllTables();
		return encounterAdverb.getByWeight(e);
	}
	public static String getAdj(Indexible e) {
		if(encounterAdj==null) populateAllTables();
		return encounterAdj.getByWeight(e);
	}
	public static String getChar(Indexible e) {
		if(encounterChar==null) populateAllTables();
		return encounterChar.getByWeight(e);
	}
	public static String getObj(Indexible e) {
		if(encounterObj==null) populateAllTables();
		return encounterObj.getByWeight(e);
	}
	
	//NON_STATIC CODE
	private PopulationModel pop;
	
	public EncounterModel(SaveRecord record,PopulationModel pop) {
		super(record);
		this.pop = pop;
	}

	public String getLocation(Indexible e,boolean isCity) {
		if(isCity) return SettlementModel.getBuilding(e);
		else return LocationModel.getStructureOrLandmark(e);
	}
	public String getLocationReference(Indexible e,Point p) {
		return Util.formatTableResultPOS("${location index}", e,p,record.getZero());
	}
	public String getNPCReference(Indexible e,Point p) {
		return Util.formatTableResultPOS("${npc index}", e,p,record.getZero());
	}
	public String getFactionReference(Indexible e,Point p) {
		return Util.formatTableResultPOS("${faction index}", e,p,record.getZero());
	}
	private String getCityRoom( Indexible e) {
		return SettlementModel.getRoom(e);
	}
	private String getDungeonRoom( Indexible e) {
		return DungeonModel.getRoom(e);
	}
	public String getActivity(Indexible e,boolean isCity) {
		if(isCity) return SettlementModel.getActivity(e);
		else return LocationModel.getActivity(e);
	}
	private String getDungeonActivity( Indexible e) {
		return DungeonModel.getActivity(e);
	}
	public String getDiscovery(Indexible e,boolean isCity) {
		if(isCity) return SettlementModel.getDiscovery(e);
		else return LocationModel.getDiscovery(e);
	}
	private String getStreet(Encounter e) {
		return SettlementModel.getStreet(e);
	}
	private String getDungeonDetail( Indexible e) {
		return DungeonModel.getDetail(e);
	}
	public String getWildernessHazard( Indexible e) {
		return LocationModel.getHazard(e);
	}
	public String getCityEvent( Indexible e) {
		return SettlementModel.getEvent(e);
	}
	public String getDungeonHazard( Indexible e) {
		return DungeonModel.getHazard(e);
	}
	public String getTrap(Indexible e) {
		return DungeonModel.getTrap(e);
	}
	public Encounter getEncounter(int i,Point p) {
		boolean isCity = pop.isCity(p);
		float[] floats = new float[TABLECOUNT];
		for(int n=0;n<floats.length;n++) {
			floats[n] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+n+i*TABLECOUNT), p.x, p.y);
		}
		Encounter e = new Encounter(floats);
		populateEncounterDetail(p, isCity, e);
		return e;
	}
	public Object getEncounter(Point p, Random random) {
		boolean isCity = pop.isCity(p);
		int[] ints = new int[TABLECOUNT];
		for(int n=0;n<ints.length;n++) {
			ints[n] = random.nextInt();
		}
		Encounter e = new Encounter(ints);
		populateEncounterDetail(p, isCity, e);
		return e;
	}
	private void populateEncounterDetail(Point p, boolean isCity, Encounter e) {
		if(isCity) e.setType("City");
		else e.setType("Wilderness");
		e.setFocus(getFocus(e));
		e.setAction(new String[] {'"'+getVerb(e)+" "+getNoun(e)+'"','"'+getActivity(e,isCity)+'"'});
		e.setDescriptor(new String[] {'"'+getAdverb(e)+" "+getAdj(e)+'"'});
		e.setCharacter(new String[] {getNPCReference(e, p),getFactionReference(e, p)});
		e.setObject(new String[] {getObj(e),getObj(e)});
		if(isCity) {
			e.setLocation(new String[] {getLocationReference(e, p),getCityRoom(e),getStreet(e),SettlementModel.getDiscovery(e)});
		}
		else {
			e.setLocation(new String[] {getLocationReference(e, p),LocationModel.getDiscovery(e)});
		}
		e.setHazard(new String[] {getWildernessHazard(e)});
	}
	public String getDefaultValue(Point p,int i) {
		return getEncounter(i,p).toString();
	}
	public Encounter getDungeonEncounter(int i,Point p) {
		float[] floats = new float[TABLECOUNT];
		for(int n=0;n<floats.length;n++) {
			floats[n] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+n+i*TABLECOUNT), p.x, p.y);
		}
		Encounter e = new Encounter(floats);
		populateDungeonEncounterDetail(e);
		return e;
	}
	public Encounter getDungeonEncounter(Random random) {
		int[] ints = new int[TABLECOUNT];
		for(int n=0;n<ints.length;n++) {
			ints[n] = random.nextInt();
		}
		Encounter e = new Encounter(ints);
		populateDungeonEncounterDetail(e);
		return e;
	}
	private void populateDungeonEncounterDetail(Encounter e) {
		e.setType("Dungeon");
		e.setFocus(getFocus(e));
		e.setAction(new String[] {'"'+getVerb(e)+" "+getNoun(e)+'"','"'+getDungeonActivity(e)+'"'});
		e.setDescriptor(new String[] {'"'+getAdverb(e)+" "+getAdj(e)+'"'});
		e.setLocation(new String[] {LocationModel.getDescriptor(e)+" and "+LocationModel.getDescriptor(e)+" "+getDungeonRoom(e)+" with "+getDungeonDetail(e)});
		e.setCharacter(new String[] {getChar(e),getChar(e)});
		e.setObject(new String[] {getObj(e),getObj(e)});
		e.setHazard(new String[] {getDungeonHazard(e),getTrap(e)});
	}
}
