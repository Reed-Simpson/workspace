package data.encounters;

import java.awt.Point;
import java.util.Random;

import controllers.DataController;
import data.DataModel;
import data.HexData;
import data.Indexible;
import data.OpenSimplex2S;
import data.Reference;
import data.WeightedTable;
import data.dungeon.DungeonModel;
import data.item.EquipmentModel;
import data.location.LocationModel;
import data.location.LocationType;
import data.npc.NPCModel;
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
	private static final int TABLECOUNT = 14;
	private static WeightedTable<String> encounterAdverb;
	private static WeightedTable<String> encounterAdj;
	private static WeightedTable<String> encounterVerb;
	private static WeightedTable<String> encounterNoun;

	private static void populateAllTables() {
		encounterVerb = new WeightedTable<String>().populate(VERBS,",");
		encounterNoun = new WeightedTable<String>().populate(NOUNS,",");
		encounterAdverb = new WeightedTable<String>().populate(ADVERB,",");
		encounterAdj = new WeightedTable<String>().populate(ADJECTIVE,",");
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

	//NON_STATIC CODE
	private PopulationModel pop;
	private DataController controller;

	public EncounterModel(SaveRecord record,PopulationModel pop,DataController controller) {
		super(record);
		this.pop = pop;
		this.controller = controller;
	}

	public String getLocation(Indexible e,boolean isCity) {
		if(isCity) return LocationType.getBuilding(e).toString();
		else return LocationType.getStructureOrLandmark(e).toString();
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
	public String getActivity(Indexible e,boolean isCity) {
		if(isCity) return SettlementModel.getActivity(e);
		else return LocationModel.getActivity(e);
	}
	public Encounter getEncounter(int i,Point p) {
		boolean isCity = pop.isCity(p);
		float[] floats = new float[TABLECOUNT];
		for(int n=0;n<floats.length;n++) {
			floats[n] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+n+i*TABLECOUNT), p.x, p.y);
		}
		Encounter e = new Encounter(floats);
		populateEncounterDetail(p, isCity, e,null);
		return e;
	}
	public Encounter getEncounter(Point p, Random random,Reference ref) {
		boolean isCity = pop.isCity(p);
		int[] ints = new int[TABLECOUNT];
		for(int n=0;n<ints.length;n++) {
			ints[n] = random.nextInt();
		}
		Encounter e = new Encounter(ints);
		populateEncounterDetail(p, isCity, e,ref);
		return e;
	}
	private void populateEncounterDetail(Point p, boolean isCity, Encounter e,Reference ref) {
		if(ref==null) ref = getRandomEncounterRef(p,isCity,e);

		e.setFocus(EncounterFocus.getFocus(e));
		HexData linkType = e.getFocus().getLinkType();
		if(linkType!=null) {
			if(HexData.NPC.equals(linkType)) {
				linkType = (HexData) Util.getElementFromArray(HexData.getCharacterTypes(), e);
			}
			Reference focusRef = new Reference(linkType, p, e, record);
			e.setFocusRef(focusRef);
		}
		if(e.reduceTempId(2)==0) {
			e.setAction(new String[] {'"'+getVerb(e)+" "+getNoun(e)+'"'});
		}else {
			e.setAction(new String[] {'"'+getActivity(e,isCity)+'"'});
		}
		e.setDescriptor(new String[] {'"'+getAdverb(e)+" "+getAdj(e)+'"'});

		populateEncounterCharacterAndLocation(e, ref);
		if(e.getCharacter()==null) e.setCharacter(new String[] {new Reference(HexData.NPC, ref.getPoint(), e, null).toString()});
		if(e.getLocation()==null) {
			if(isCity||e.reduceTempId(2)==0) {
				e.setLocation(new String[] {new Reference(HexData.LOCATION, ref.getPoint(), e, null).toString()});
			}else {
				String landmark = Util.getElementFromArray(LocationType.landmarks, e).toString();
				e.setLocation(new String[] {landmark});
			}
		}

		e.setObject(new String[] {EquipmentModel.getObj(e),EquipmentModel.getObj(e)});
		if(isCity) {
			e.setSpice(new String[] {SettlementModel.getRoom(e),SettlementModel.getStreet(e),SettlementModel.getDiscovery(e)});
		}else {
			e.setSpice(new String[] {LocationModel.getDiscovery(e)});
		}
		//e.setHazard(new String[] {getWildernessHazard(e)});
	}
	private void populateEncounterCharacterAndLocation(Encounter e, Reference ref) {
		switch(ref.getType()) {
		case LOCATION:{
			String[] character = null;
			Reference proprietor = new Reference(HexData.PROPRIETOR, ref.getPoint(), ref.getIndex());
			if("None".equals(proprietor.getLinkText(controller))) {
				character = new String[] {new Reference(HexData.NPC, ref.getPoint(), e, null).toString()};
			}else {
				character = new String[] {proprietor.toString()};
			}
			e.setLocation(new String[] {ref.toString()});
			e.setCharacter(character);
			break;
		}case DUNGEON:{
			Reference location = new Reference(HexData.LOCATION, ref.getPoint(), controller.getDungeon().getLocationOfDungeon(record.denormalizePOS(ref.getPoint()), ref.getIndex()));
			e.setLocation(new String[] {location.toString(),ref.toString()});
			HexData beast = (HexData) Util.getElementFromArray(new HexData[]{HexData.BEAST,HexData.MONSTER,HexData.THREATMONSTER}, e);
			e.setCharacter(new String[] {new Reference(beast, ref.getPoint(), e, null).toString()});
			break;
		}case DISTRICT: {
			String[] character = null;
			int i = e.reduceTempId(2);
			Reference loc = new Reference(HexData.LOCATION, ref.getPoint(), ref.getIndex()+i*HexData.DISTRICT.getCount());
			Reference proprietor = new Reference(HexData.PROPRIETOR, loc.getPoint(), loc.getIndex());
			if("None".equals(proprietor.getLinkText(controller))) {
				character = new String[] {new Reference(HexData.NPC, ref.getPoint(), e, null).toString()};
			}else {
				character = new String[] {proprietor.toString()};
			}
			e.setLocation(new String[] {ref.toString(),loc.toString()});
			e.setCharacter(character);
			break;
		}case FACTION:{
			if("None".equals(ref.getLinkText(controller))) {
				e.setCharacter(new String[] {new Reference(HexData.NPC, ref.getPoint(), e, null).toString()});
			}else {
				int i = (e.reduceTempId(4)==0?0:1);
				Reference proprietor = new Reference(HexData.FACTION_NPC, ref.getPoint(), ref.getIndex()*2+i);
				e.setCharacter(new String[] {ref.toString(),proprietor.toString()});
			}
			break;
		}case FAITH:{
			int i = (e.reduceTempId(4)==0?0:1);
			Reference proprietor = new Reference(HexData.FACTION_NPC, ref.getPoint(), (ref.getIndex()+HexData.FACTION.getCount())*2+i);
			e.setCharacter(new String[] {ref.toString(),proprietor.toString()});
			break;
		}case MINION:{
			Reference faction = new Reference(HexData.MINION, ref.getPoint(), 0);
			Reference proprietor;
			if(ref.getIndex()==0) {
				proprietor = new Reference(HexData.MINION, ref.getPoint(), 2);
			}else {
				proprietor = ref;
			}
			e.setCharacter(new String[] {faction.toString(),proprietor.toString()});
			break;
		}case THREAT:{
			HexData secondary = getRandomEncounterType(e);
			populateEncounterCharacterAndLocation(e, new Reference(secondary, ref.getPoint(), e,null));
			String[] chars = new String[e.getCharacter().length+1];
			chars[0] = ref.toString();
			for(int i=0;i<e.getCharacter().length;i++) {
				chars[i+1] = e.getCharacter()[i];
			}
			e.setCharacter(chars);
			break;
		}case NPC:case BEAST:case MONSTER:case THREATMONSTER:{
			e.setCharacter(new String[] {ref.toString()});
			break;
		}default: break;
		}
	}
	private Reference getRandomEncounterRef(Point p, boolean isCity, Indexible obj) {
		HexData refType = getRandomEncounterType(obj);
		if(isCity&&HexData.LOCATION.equals(refType)) refType = HexData.DISTRICT;
		return new Reference(refType,p,obj,record);
	}
	private HexData getRandomEncounterType(Indexible obj) {
		if(obj.reduceTempId(2)==0) {
			HexData result = (HexData) Util.getElementFromArray(HexData.getRPEncounterTypes(), obj);
			return result;
		}else {
			HexData result = (HexData) Util.getElementFromArray(HexData.getCombatEncounterTypes(), obj);
			return result;
		}
	}
	@SuppressWarnings("deprecation")
	public Point getRumorLocation(Point p,Random rand) {
		WeightedTable<Point> points = new WeightedTable<Point>();
		points.put(p, 60);
		for(int r=1;r<6;r++) {
			for(Point p1:Util.getRing(p, r)) {
				if(!controller.getGrid().isWater(p1)) {
					points.put(p1, 60/r);
				}
			}
		}
		return points.getByWeight(rand.nextInt());
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
		e.setFocus(EncounterFocus.getFocus(e));
		e.setAction(new String[] {'"'+getVerb(e)+" "+getNoun(e)+'"','"'+DungeonModel.getActivity(e)+'"'});
		e.setDescriptor(new String[] {'"'+getAdverb(e)+" "+getAdj(e)+'"'});
		e.setLocation(new String[] {LocationModel.getDescriptor(e)+" and "+LocationModel.getDescriptor(e)+" "+DungeonModel.getRoom(e)+" with "+DungeonModel.getDetail(e)});
		e.setCharacter(new String[] {NPCModel.getChar(e),NPCModel.getChar(e)});
		e.setObject(new String[] {EquipmentModel.getObj(e),EquipmentModel.getObj(e)});
		e.setHazard(new String[] {DungeonModel.getHazard(e),DungeonModel.getTrap(e)});
	}
}
