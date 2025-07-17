package data.npc;

import java.awt.Point;
import java.util.Random;

import data.DataModel;
import data.Indexible;
import data.OpenSimplex2S;
import data.WeightedTable;
import data.encounters.EncounterModel;
import data.location.Location;
import data.location.LocationModel;
import data.magic.MagicModel;
import data.population.NPCSpecies;
import data.population.PopulationModel;
import data.population.SettlementModel;
import data.population.Species;
import data.threat.CreatureType;
import data.threat.Threat;
import data.threat.subtype.BeastType;
import data.threat.subtype.HumanoidType;
import data.threat.subtype.OozeType;
import data.threat.subtype.PlantType;
import data.threat.subtype.UndeadType;
import io.SaveRecord;
import names.threat.HumanoidNameGenerator;
import util.Util;
import view.InfoPanel;

public class NPCModel extends DataModel {
	//STATIC CONSTANTS
	private static final int SEED_OFFSET = 9*Util.getOffsetX();
	private static final int TABLECOUNT = 10;
	public static final int THREAT_NPC_INDEX = InfoPanel.NPCCOUNT*2+InfoPanel.POICOUNT+InfoPanel.FACTIONCOUNT+InfoPanel.FAITHCOUNT+1;
	private static final String ASSETS = "Has authority,Avoids detection,Calls in favors,Is charming,Cooks the books,Erases the evidence,Excellent liar,Extremely Rich,Leader of ${faction index},Member of ${faction index},Feared,Has a fortified base,"+
			"Gorgeous,Hears rumors,Huge family,Huge library,Impersonator,Interrogator,Knows a guy,Knows a way in,Launders money,Learned,Local celebrity,Posesses local knowledge,"+
			"Has loyal henchmen,Middling Oracle,Has nothing to lose,Owns the guards,Has a powerful spouse,Procures gear,Pulls the strings,Has a secret lab,Sells contraband,Smuggles goods,Has a spy network,War hero";
	private static WeightedTable<String> assets;
	private static final String LIABILITIES = "has an addiction,is an alchoholic,has a corrupt ally,is a coward,is decadent,has a forbidden love,is a gambler,is a glutton,is greedy,is a heretic,has huge debts,is an imposter,"+
			"${insanity},is jealous,leaves evidence,has many enemies,is misinformed,left a money trail,is a narcissist,needs medicine,is neurotic,is paranoid,is a partyer,has poor equipment,"+
			"is overprotective,is scandalous,is softhearted,follows strict routines,is superstitous,is overly suspicious,has a temper,is overly trusting,has a vulnerable base,is wanted,is weak-willed,is widely despised";
	private static WeightedTable<String> liabilities;
	private static final String GOALS = "a better life,acceptance,to acquire ${item},to craft ${item},to destroy ${faction index},to destroy ${item},enlightenment,fame,to found ${faction},freedom,glory,to impress ${relationship},"+
			"infamy,to infiltrate ${faction index},justice,to kidnap ${relationship},to lead ${faction index},learning,to locate ${relationship},love,mastery,power,to reach location,to rescue ${relationship},"+
			"to resolve a dispute,to restore ${faction},to reveal a secret,revenge,to sabotage ${faction index},to serve a deity,to serve evil,to serve ${faction index},to serve ideology,to serve leader,to help the Needy,wealth";
	private static WeightedTable<String> goals;
	private static final String MISFORTUNE_ADJECTIVES = "abandoned,addicted,arrested,blackmailed,burgled,challenged,condemned,crippled,cursed,defrauded,demoted,depressed,"+
			"discredited,dismissed,disowned,exiled,famished,forgotten,framed,haunted,humiliated,impoverished,kidnapped,lost,"+
			"mobbed,mutilated,overworked,poisoned,pursued,rejected,replaced,robbed,sick,sued,suspected,transformed";
	private static WeightedTable<String> misfortunes;
	private static final String METHOD_NOUNS = "alchemy,blackmail,bluster,bribery,bullying,bureaucracy,charm,commerce,cronies,debate,deceit,deduction,"+
			"eloquence,espionage,fast-talking,favors,hard work,humor,investigation,legal maneuvers,manipulation,misdirection,money,nagging,"+
			"negotiation,persistence,piety,preparation,quick wit,research,rumors,sabotage,teamwork,theft,threats,violence";
	private static WeightedTable<String> methods;
	private static final String APPEARANCE_ADJECTIVES = "aquiline,athletic,barrel-chested,boney,brawny,brutish,bullnecked,chiseled,coltish,corpulent,craggy,delicate,"+
			"furrowed,gaunt,gorgeous,grizzled,haggard,handsome,hideous,lanky,pudgy,ripped,rosy,scrawny,"+
			"sinewy,slender,slumped,solid,square-jawed,statuesque,towering,trim,weathered,willowy,wiry,wrinkled";
	private static WeightedTable<String> appearances;
	private static final String APPEARANCE_NOUNS = "acid scars,battle scars,a birthmark,braided hair,a brand mark,a broken nose,bronze skin,burn scars,bushy eyebrows,curly hair,dark skin,dreadlocks,"+
			"an exotic accent,flogging scars,freckles,a gold tooth,a hoarse voice,a huge beard,long hair,a melodious voice,a missing ear,missing teeth,a mustache,muttonchops,"+
			"nine fingers,oiled hair,one eye,pale Skin,piercings,ritual scars,sallow skin,a shaved head,a sunburn,tangled hair,tattoos,a topknot";
	private static WeightedTable<String> details;
	private static final String COSTUME_ADJECTIVES = "antique,battle-torn,bedraggled,blood-stained,ceremonial,dated,decaying,eccentric,elegant,embroidered,exotic,fashionable,"+
			"flamboyant,flood-stained,formal,frayed,frumpy,garish,grimy,haute couture,lacey,livery,mud-stained,ostentatious,"+
			"oversized,patched,patterned,perfumed,practical,rumpled,sigils,singed,tasteless,undersized,wine-stained,worn out";
	private static WeightedTable<String> costumes;
	private static final String PERSONALITY_ADJECTIVES = "bitter,brave,cautious,chipper,contrary,cowardly,cunning,driven,entitled,gregarious,grumpy,heartless,"+
			"honor-bound,hotheaded,inquisitive,irascible,jolly,pretentious,lazy,loyal,menacing,mopey,nervous,protective,"+
			"righteous,rude,sarcastic,savage,scheming,serene,spacey,stoic,stubborn,stuck-up,suspicous,jovial";
	private static WeightedTable<String> personalities;
	private static final String MANNERISMS = "fond of anecdotes,often breathy,often chuckling,often curt,often cryptic,deep voiced,speaking with a drawl,often enunciating,often using flowery speech,gravelly voiced,highly formal,hypnotic,"+
			"often interrupting,laconic,often laughing,often taking long pauses,melodious,monotone,often mumbling,often narrating,overly casual,fond of quaint sayings,often rambling,fond of random facts"+
			"often rapid-fire,fond of rhyming,robotic,often speaking slowly,often speechifying,squeaky,fond of street slang,often stuttering,often talking to self,often trailing off,very loud,often whispering";
	private static WeightedTable<String> mannerisms;
	private static final String SECRETS = "addicted,artificial,an assassin,bankrupt,beholden,a counterspy,a cultist,a demigod,of evil lineage,an exile,a fence,a fugitive,"+
			"a ghost,a parent,a heretic,high born,hiding a huge fortune,a shapeshifter,an insurrectionist,low born,married,mind-controlled,frequently ${misfortune},a monster hunter,"+
			"a non-human,hiding a ${relationship},an adulterer,protecting a relic,hiding a scandalous birth,a secret police,a serial killer,a smuggler,a spy,a planar traveler,transformed,a war criminal";
	private static WeightedTable<String> secrets;
	private static final String REPUTATION_ADJECTIVES = "ambitious,authoritative,boorish,indebted,famous,charitable,deceitful,dangerous,entertaining,gossipy,hardworking,holy,"+
			"honest,neurotic,idiotic,influential,lazy,leaderly,misanthropic,miserly,neighborly,nutty,obnoxious,overeducated,"+
			"revelrous,pious,proper,gloomy,repulsive,respected,riffraff,scandalous,slimey,terrifying,weird,wise";
	private static WeightedTable<String> reputations;
	private static final String HOBBY_NOUNS = "archeology,art collecting,bad fiction,calligraphy,card games,clockwork,cats,cuisine,dark lore,dog breeding,embroidery,exercise,"+
			"falconry,fashion,fishing,foreign cultures,gardening,history,horseracing,hunting,music,knitting,lawn games,mountaineering,"+
			"opera,painting,poetry,puzzle-solving,riddling,science,sculpture,sketching,smoking,theater,weaving,whiskey";
	private static WeightedTable<String> hobbies;
	private static final String RELATIONSHIP_NOUNS = "adviser,blackmailer,business partner,business rival,buyer,captor,client,confidant,debtor,disciple,guardian,henchman,"+
			"idol,informant,master,mentor,nemesis,offspring,parent,patron,political rival,prisoner,protege,quarry,"+
			"sidekick,romantic rival,servant,sibling,social rival,spouse,stalker,suitor,supplicant,supplier,sweetheart,unrequited love";
	private static WeightedTable<String> relationships;
	private static final String DOMAIN_NOUNS = "${animal}s,balance,betrayal,chance,chaos,conquest,cycles,death,destiny,dreams,${element},gateways,"+
			"judgement,love,memory,monsters,moon,motherhood,${job}s,oaths,order,plague,purification,reason,"+
			"schemes,secrets,storms,summer,sun,forge,sea,wild,time,underworld,wealth,winter";
	private static WeightedTable<String> domains;
	public static final String BODYPART = "Hair,Horn,Skull,Face,Eye,Ear,Nose,Tongue,Jaw,Fang,Tooth,Chin,Beard,Neck,Spine,Shoulder,Heart,Blood,Arm,Hand,Fist,Claw,Finger,Thumb,Back,Leg,Foot,Toe";
	private static WeightedTable<String> bodypart;

	private static void populateAllTables() {
		assets = new WeightedTable<String>().populate(ASSETS,",");
		liabilities = new WeightedTable<String>().populate(LIABILITIES,",");
		goals = new WeightedTable<String>().populate(GOALS,",");
		misfortunes = new WeightedTable<String>().populate(MISFORTUNE_ADJECTIVES,",");
		methods = new WeightedTable<String>().populate(METHOD_NOUNS,",");
		appearances = new WeightedTable<String>().populate(APPEARANCE_ADJECTIVES,",");
		details = new WeightedTable<String>().populate(APPEARANCE_NOUNS,",");
		costumes = new WeightedTable<String>().populate(COSTUME_ADJECTIVES,",");
		personalities = new WeightedTable<String>().populate(PERSONALITY_ADJECTIVES,",");
		mannerisms = new WeightedTable<String>().populate(MANNERISMS,",");
		secrets = new WeightedTable<String>().populate(SECRETS,",");
		reputations = new WeightedTable<String>().populate(REPUTATION_ADJECTIVES,",");
		hobbies = new WeightedTable<String>().populate(HOBBY_NOUNS,",");
		relationships = new WeightedTable<String>().populate(RELATIONSHIP_NOUNS,",");
		domains = new WeightedTable<String>().populate(DOMAIN_NOUNS,",");
		bodypart = new WeightedTable<String>().populate(BODYPART,",");
	}


	public static String getAsset(Indexible obj) {
		if(assets==null) populateAllTables();
		return Util.formatTableResult(assets.getByWeight(obj),obj);
	}
	public static String getLiability(Indexible obj) {
		if(liabilities==null) populateAllTables();
		return Util.formatTableResult(liabilities.getByWeight(obj),obj);
	}
	public static String getGoal(Indexible obj) {
		if(goals==null) populateAllTables();
		return Util.formatTableResult(goals.getByWeight(obj),obj);
	}
	public static String getMisfortune(Indexible obj) {
		if(misfortunes==null) populateAllTables();
		return misfortunes.getByWeight(obj);
	}
	public static String getMethod(Indexible obj) {
		if(methods==null) populateAllTables();
		return methods.getByWeight(obj);
	}
	public static String getAppearance(Indexible obj) {
		if(appearances==null) populateAllTables();
		return appearances.getByWeight(obj);
	}
	public static String getDetail(Indexible obj) {
		if(details==null) populateAllTables();
		return details.getByWeight(obj);
	}
	public static String getCostume(Indexible obj) {
		if(costumes==null) populateAllTables();
		return costumes.getByWeight(obj);
	}
	public static String getPersonality(Indexible obj) {
		if(personalities==null) populateAllTables();
		return personalities.getByWeight(obj);
	}
	public static String getMannerism(Indexible obj) {
		if(mannerisms==null) populateAllTables();
		return mannerisms.getByWeight(obj);
	}
	public static String getSecret(Indexible obj) {
		if(secrets==null) populateAllTables();
		return Util.formatTableResult(secrets.getByWeight(obj),obj);
	}
	public static String getReputation(Indexible obj) {
		if(reputations==null) populateAllTables();
		return reputations.getByWeight(obj);
	}
	public static String getHobby(Indexible obj) {
		if(hobbies==null) populateAllTables();
		return hobbies.getByWeight(obj);
	}
	public static String getRelationship(Indexible obj) {
		if(relationships==null) populateAllTables();
		return relationships.getByWeight(obj);
	}
	public static String getDomain(Indexible obj) {
		if(domains==null) populateAllTables();
		return Util.formatTableResult(domains.getByWeight(obj),obj);
	}
	public static String getBodypart(Indexible obj) {
		if(bodypart==null) populateAllTables();
		return Util.formatTableResult(bodypart.getByWeight(obj),obj);
	}

	//NON_STATIC CODE
	private PopulationModel population;
	private LocationModel location;
	private SettlementModel cities;

	public NPCModel(SaveRecord record,PopulationModel population,LocationModel location,SettlementModel cities) {
		super(record);
		this.population = population;
		this.location = location;
		this.cities = cities;
	}

	public NPC getNPC(int i,Point p) {
		if(i==THREAT_NPC_INDEX)  ;
		else if(i>=InfoPanel.NPCCOUNT*2+InfoPanel.POICOUNT) return getFactionNPC(i-(InfoPanel.NPCCOUNT*2+InfoPanel.POICOUNT), p);
		else if(i>=InfoPanel.NPCCOUNT*2) return getProprietor(i-InfoPanel.NPCCOUNT*2, p);
		NPC result = getIndexedNPC(i, p);
		populateNPCData(p, result);
		return result;
	}


	private NPC getIndexedNPC(int i, Point p) {
		float[] floats = new float[TABLECOUNT];
		for(int x=0;x<floats.length;x++) {
			floats[x] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+i*TABLECOUNT+x), p.x, p.y);
		}
		NPC result = new NPC(floats);
		return result;
	}
	public NPC getProprietor(int i, Point p) {
		NPC result = getIndexedNPC(i+InfoPanel.NPCCOUNT*2, p);
		populateNPCData(p, result);
		if(setProprietorJob(p, i, result)) return result;
		else return null;
	}
	public NPC getFactionNPC(int i, Point p) {
		Point capital = population.getAbsoluteFealty(p);
		NPC result = getIndexedNPC(i+InfoPanel.NPCCOUNT*2+InfoPanel.POICOUNT, capital);
		populateNPCData(capital, result);
		if(setFactionJob(capital, i/2, result)) return result;
		else return null;
	}


	private void populateNPCData(Point p, NPC npc) {
		setSpecies(p, npc);
		setSubspecies(p, npc);
		setJob(p, npc);
		
		setAppearance(p, npc);
		setDetail(p, npc);
		setCostume(p, npc);
		setMannerism(p, npc);
		
		int index = npc.reduceTempId(5);
		if(index==0) setPersonality(p, npc);
		else if(index==1) setReputation(p, npc);
		else if(index==2) setRelationship(p, npc);
		else if(index==3) setFaction(p, npc);
		else if(index==4) {
			index = npc.reduceTempId(6);
			if(index==0||index==1||index==2) setPersonality(p, npc);
			if(index==0||index==3||index==4) setReputation(p, npc);
			if(index==1||index==3||index==5) setRelationship(p, npc);
			if(index==2||index==4||index==5) setFaction(p, npc);
		}
		
		setGoal(p, npc);
		index = npc.reduceTempId(5);
		if(index==0) setAsset(p, npc);
		else if(index==1) setMethod(p, npc);
		else if(index==2) setHobby(p, npc);
		else if(index==3) setDomain(p, npc);
		else if(index==4) {
			index = npc.reduceTempId(6);
			if(index==0||index==1||index==2) setAsset(p, npc);
			if(index==0||index==3||index==4) setMethod(p, npc);
			if(index==1||index==3||index==5) setHobby(p, npc);
			if(index==2||index==4||index==5) setDomain(p, npc);
		}

		index = npc.reduceTempId(7);
		if(index<2) setLiability(p, npc);
		else if(index<4) setMisfortune(p, npc);
		else if(index<6) setSecret(p, npc);
		else {
			index = npc.reduceTempId(3);
			if(index==0||index==1) setLiability(p, npc);
			else if(index==0||index==2) setMisfortune(p, npc);
			else if(index==1||index==2) setSecret(p, npc);
		}
		setName(p, npc);
		setDescriptors(p,npc);
	}


	private void setSpecies(Point p, NPC result) {
		if(result.getSpecies()==null) {
			WeightedTable<NPCSpecies> demo = population.getTransformedDemographics(p);
			if(demo!=null&&demo.getSumWeight()>0) {
				NPCSpecies species = demo.getByWeight(result);
				result.setSpecies(species);
			}else {
				NPCSpecies species = getRandomNPCSpecies(result);
				result.setSpecies(species);
			}
		}
	}
	private void setSubspecies(Point p, NPC result) {
		if(NPCSpecies.GOBLINOID.equals(result.getSpecies())) {
			result.setSubspecies(HumanoidNameGenerator.getGoblinoid(result));
		}
	}
	private void setJob(Point p, NPC npc) {
		if(npc.getJob()==null) {
			npc.setJob(NPCJobType.getJob(npc,population.isCity(p), population.isTown(p)).toString());
		}
	}
	private void setAsset(Point p, NPC npc) {
		npc.setAsset(Util.formatTableResultPOS(getAsset(npc),npc,p,record.getZero()));
	}
	private void setLiability(Point p, NPC npc) {
		npc.setLiability(getLiability(npc));
	}
	private void setGoal(Point p, NPC npc) {
		npc.setGoal(Util.formatTableResultPOS(getGoal(npc),npc,p,record.getZero()));
	}
	private void setMisfortune(Point p, NPC npc) {
		npc.setMisfortune(getMisfortune(npc));
	}
	private void setMethod(Point p, NPC npc) {
		npc.setMethod(getMethod(npc));
	}
	private void setAppearance(Point p, NPC npc) {
		npc.setAppearance(getAppearance(npc));
	}
	private void setDetail(Point p, NPC npc) {
		npc.setDetail(getDetail(npc));
	}
	private void setCostume(Point p, NPC npc) {
		npc.setCostume(getCostume(npc));
	}
	private void setPersonality(Point p, NPC npc) {
		npc.setPersonality(getPersonality(npc));
	}
	private void setMannerism(Point p, NPC npc) {
		npc.setMannerism(getMannerism(npc));
	}
	private void setHobby(Point p, NPC npc) {
		npc.setHobby(getHobby(npc));
	}
	private void setReputation(Point p, NPC npc) {
		npc.setReputation(getReputation(npc));
	}
	private void setSecret(Point p, NPC npc) {
		npc.setSecret(getSecret(npc));
	}
	private void setDomain(Point p, NPC npc) {
		String formatTableResultPOS = Util.formatTableResultPOS("${faith index}",npc,p,record.getZero());
		npc.setDomain(formatTableResultPOS);
	}
	private void setRelationship(Point p, NPC npc) {
		npc.setRelationship(getRelationship(npc));
	}
	private void setName(Point p, NPC npc) {
		if(npc.getSpecies()!=null&&npc.getSpecies().getNameGen()!=null) {
			String name = npc.getSpecies().getNameGen().getName(npc);
			name = Util.formatTableResultPOS(name, npc, p, record.getZero());
			npc.setName(name);
		}
	}
	private void setFaction(Point p, NPC npc) {
		npc.setFaction(Util.formatTableResultPOS("${faction index}", npc, p, record.getZero()));
	}
	private void setDescriptors(Point p, NPC npc) {
		String desc1 = EncounterModel.getChar(npc);
		String desc2 = EncounterModel.getChar(npc);
		npc.setDescriptors(new String[]{desc1,desc2});
	}
	public NPCSpecies getRandomNPCSpecies(Indexible obj) {
		NPCSpecies[] species = NPCSpecies.getAbeirNPCSpecies();
		return (NPCSpecies) Util.getElementFromArray(species, obj);
	}
	@Deprecated
	@Override
	public NPC getDefaultValue(Point p, int i) {
		return getNPC(i, p);
	}




	public NPC getProprietor(Point p, Random random, int i) {
		NPC result = getNPC(p, random);
		if(setProprietorJob(p, i, result)) return result;
		else return null;
	}
	public NPC getFactionNPC(Point p, Random random, int i) {
		Point capital = population.getAbsoluteFealty(p);
		NPC result = getNPC(capital, random);
		if(setFactionJob(capital, i/2, result)) return result;
		else return null;
	}


	private boolean setProprietorJob(Point p, int i, NPC result) {
		Location l = location.getPOI(i, p);
		NPCJobType jobType = l.getProprietorJob();
		if(jobType!=null) {
			result.setJob(jobType.toString());
			return true;
		}
		else return false;
	}
	private boolean setFactionJob(Point p, int i, NPC result) {
		Faction f = cities.getDisplayedFaction(i, p);
		if(f==null) return false;
		NPCJobType[] jobs = f.getType().getFactionJobs();
		result.setJob(Util.getElementFromArray(jobs, result).toString());
		return true;
	}
	public NPC getNPC(Point p,Random random) {
		int[] ints = new int[TABLECOUNT];
		for(int x=0;x<ints.length;x++) {
			ints[x] = random.nextInt();
		}
		NPC result = new NPC(ints);

		populateNPCData(p, result);
		return result;
	}
	public NPC getMinion(int i,Point p,Threat threat) {
		NPC result = getIndexedNPC(i+InfoPanel.NPCCOUNT, p);//minion offset
		setMinionSpecies(getMinionSpecies(threat,result),result,threat);

		populateNPCData(p, result);
		if(i<2)	setFactionJob(p, SettlementModel.THREAT_FACTION_INDEX, result);
		return result;
	}


	public Species getMinionSpecies(Threat threat,Indexible obj) {
		int index = obj.reduceTempId(3);
		if(index==0) {
			return threat.getMinionSpecies();
		}
		else if(index == 1) return null;
		else {
			Species minionSpecies = (Species) Util.getElementFromArray(threat.getSubtype().getMinionSpeciesList(), obj);
			if(minionSpecies==null) return threat.getNPC().getSpecies();
			else return minionSpecies;
		}
	}
	private void setMinionSpecies(Species preliminary,NPC npc,Threat threat) {
		Species minionSpecies = preliminary;
		if(CreatureType.HUMANOID.equals(minionSpecies)) minionSpecies = null;
		else if(HumanoidType.CULTIST.equals(minionSpecies)) {
			minionSpecies = null;
			npc.setJob("Cultist");
		}else if(HumanoidType.WARLORD.equals(minionSpecies)) {
			minionSpecies = null;
			npc.setJob("Thug");
		}else if(HumanoidType.SPELLCASTER.equals(minionSpecies)) {
			minionSpecies = null;
			npc.setJob(MagicModel.getSpellcaster(npc));
		}else if(HumanoidType.BANDIT.equals(minionSpecies)) {
			minionSpecies = null;
			npc.setJob("Bandit");
		}else if(HumanoidType.LYCANTHROPE.equals(minionSpecies)||BeastType.LYCANTHROPE.equals(minionSpecies)) {
			minionSpecies = null;
			npc.setSubspecies(HumanoidNameGenerator.getLycanthrope(npc));
		}else if(UndeadType.LICH.equals(minionSpecies)) {
			minionSpecies = null;
			npc.setJob("Lich");
		}else if(UndeadType.VAMPIRE.equals(minionSpecies)) {
			minionSpecies = null;
			npc.setSubspecies("Vampire");
		}else if(UndeadType.MUMMY_LORD.equals(minionSpecies)) {
			minionSpecies = threat.getNPC().getSpecies();
			npc.setJob("Mummy");
		}else if(UndeadType.SKULL_LORD.equals(minionSpecies)) {
			minionSpecies = null;
			npc.setJob("Skull Lord");
		}else if(UndeadType.DEATH_KNIGHT.equals(minionSpecies)) {
			minionSpecies = null;
			npc.setJob("Death Knight");
		}else if(UndeadType.DEMILICH.equals(minionSpecies)) {
			minionSpecies = null;
			npc.setJob("Demilich");
		}else if(UndeadType.GHOST.equals(minionSpecies)) {
			minionSpecies = null;
			npc.setSubspecies("Ghost");
		}else if(UndeadType.WIGHT.equals(minionSpecies)) {
			minionSpecies = null;
			npc.setJob("Wight");
		}else if(UndeadType.WRAITH.equals(minionSpecies)) {
			minionSpecies = null;
			npc.setJob("Wraith");
		}else if(UndeadType.DEATHLOCK.equals(minionSpecies)) {
			minionSpecies = null;
			npc.setJob("Deathlock");
		}else if(BeastType.DRUID.equals(minionSpecies)||OozeType.DRUID.equals(minionSpecies)||PlantType.DRUID.equals(minionSpecies)) {
			minionSpecies = null;
			npc.setDomain(threat.getDomain());
			npc.setJob("Druid");
		}

		npc.setSpecies(minionSpecies);
	}
	

	public String getFactionNPCLastName(int i, Point p) {
		Point capital = population.getAbsoluteFealty(p);
		NPC result = getIndexedNPC(i+InfoPanel.NPCCOUNT*2+InfoPanel.POICOUNT, capital);
		populateNPCData(capital, result);
		int split = result.getName().lastIndexOf(' ');
		return Util.toCamelCase(result.getName().substring(split+1));
	}

}
