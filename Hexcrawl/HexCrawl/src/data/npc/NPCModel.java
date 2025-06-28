package data.npc;

import java.awt.Point;
import java.util.Random;

import data.DataModel;
import data.Indexible;
import data.OpenSimplex2S;
import data.WeightedTable;
import data.encounters.EncounterModel;
import data.population.PopulationModel;
import data.population.Species;
import io.SaveRecord;
import names.threat.HumanoidNameGenerator;
import util.Util;

public class NPCModel extends DataModel {
	//STATIC CONSTANTS
	private static final int SEED_OFFSET = 9*Util.getOffsetX();
	private static final int TABLECOUNT = 10;
	private static final String CIVILIZED = "Acolyte,Actor,Apothacary,Baker,Barber,Blacksmith,Brewer,Bureaucrat,Butcher,Carpenter,Clockmaker,Courier,"+
			"Courtier,Diplomat,Fishmonger,Guard,Haberdasher,Innkeeper,Item-seller,Jeweler,Knight,Locksmith,Mason,Miller,"+
			"Musician,Noble,Painter,Priest,Scholar,Scribe,Sculptor,Shipwright,Soldier,Tailor,Taxidermist,Wigmaker";
	private static WeightedTable<String> civilized;
	private static final String UNDERWORLD = "Alchemist,Animal-breeder,Assassin,Acrobat,Beggar,Burglar,Chimneysweep,Con Man,Cultist,Cutpurse,Deserter,Ditchdigger,"+
			"Fence,Forger,Fortuneseller,Gambler,Gladiator,Gravedigger,Headsman,Informant,Jailer,Laborer,Lamplighter,Mercenary,"+
			"Poet,Poisoner,Privateer,Rat-Catcher,Sailor,Servant,Smuggler,Spy,Urchin,Userer,Vagabond,Wizard";
	private static WeightedTable<String> underworld;
	private static final String WILDERNESS = "Apiarist,Bandit,Caravan Guard,Caravaneer,Druid,Exile,Explorer,Farmer,Fisher,Forager,Fugative,Hedge Wizard,"+
			"Hermit,Hunter,Messenger,Minstrel,Monk,Monster Hunter,Outlander,Tinker,Pilgrim,Poacher,Raider,Ranger,"+
			"Sage,Scavenger,Scout,Shepherd,Seer,Surveyor,Tinker,Tomb Raider,Trader,Trapper,Witch,Woodcutter";
	private static WeightedTable<String> wilderness;
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
	private static final String MISSION_VERBS = "Apprehend,Assassinate,Blackmail,Burgle,Chart,Convince,Deface,Defraud,Deliver,Destroy,Discredit,Escort,"+
			"Exfiltrate,Extort,Follow,Frame,Impersonate,Impress,Infiltrate,Interrogate,Investigate,Kidnap,Locate,Plant,"+
			"Protect,Raid,Replace,Retrieve,Rob,Ruin,Sabotage,Smuggle,Surveil,Control,Terrorize,Threaten";
	private static WeightedTable<String> missions;
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

	private static void populateAllTables() {
		civilized = new WeightedTable<String>();
		populate(civilized,CIVILIZED,",");
		underworld = new WeightedTable<String>();
		populate(underworld,UNDERWORLD,",");
		wilderness = new WeightedTable<String>();
		populate(wilderness,WILDERNESS,",");
		assets = new WeightedTable<String>();
		populate(assets,ASSETS,",");
		liabilities = new WeightedTable<String>();
		populate(liabilities,LIABILITIES,",");
		goals = new WeightedTable<String>();
		populate(goals,GOALS,",");
		misfortunes = new WeightedTable<String>();
		populate(misfortunes,MISFORTUNE_ADJECTIVES,",");
		missions = new WeightedTable<String>();
		populate(missions,MISSION_VERBS,",");
		methods = new WeightedTable<String>();
		populate(methods,METHOD_NOUNS,",");
		appearances = new WeightedTable<String>();
		populate(appearances,APPEARANCE_ADJECTIVES,",");
		details = new WeightedTable<String>();
		populate(details,APPEARANCE_NOUNS,",");
		costumes = new WeightedTable<String>();
		populate(costumes,COSTUME_ADJECTIVES,",");
		personalities = new WeightedTable<String>();
		populate(personalities,PERSONALITY_ADJECTIVES,",");
		mannerisms = new WeightedTable<String>();
		populate(mannerisms,MANNERISMS,",");
		secrets = new WeightedTable<String>();
		populate(secrets,SECRETS,",");
		reputations = new WeightedTable<String>();
		populate(reputations,REPUTATION_ADJECTIVES,",");
		hobbies = new WeightedTable<String>();
		populate(hobbies,HOBBY_NOUNS,",");
		relationships = new WeightedTable<String>();
		populate(relationships,RELATIONSHIP_NOUNS,",");
		domains = new WeightedTable<String>();
		populate(domains,DOMAIN_NOUNS,",");
	}


	public static String getCivilized(Indexible obj) {
		if(civilized==null) populateAllTables();
		return civilized.getByWeight(obj);
	}
	public static String getUnderworld(Indexible obj) {
		if(underworld==null) populateAllTables();
		return underworld.getByWeight(obj);
	}
	public static String getWilderness(Indexible obj) {
		if(wilderness==null) populateAllTables();
		return wilderness.getByWeight(obj);
	}
	public static String getJob(Indexible obj,boolean isCity,boolean isTown) {
		int id = obj.reduceTempId(10);
		if(isCity) {
			if(id<5) return getCivilized(obj);//50%
			else if(id<9) return getUnderworld(obj);//40%
			else return getWilderness(obj);//10%
		}else if(isTown) {
			if(id<4) return getCivilized(obj);//40%
			else if(id<5) return getUnderworld(obj);//10%
			else return getWilderness(obj);//50%
		}else {
			if(id<1) return getCivilized(obj);//10%
			else if(id<2) return getUnderworld(obj);//10%
			else return getWilderness(obj);//80%
		}
	}
	public static String getJob(Indexible obj) {
		int id = obj.reduceTempId(3);
		if(id==0) return getCivilized(obj);
		else if(id==1) return getUnderworld(obj);
		else return getWilderness(obj);
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
	public static String getMission(Indexible obj) {
		if(missions==null) populateAllTables();
		return missions.getByWeight(obj);
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

	//NON_STATIC CODE
	private PopulationModel population;

	public NPCModel(SaveRecord record,PopulationModel population) {
		super(record);
		this.population = population;
	}

	public NPC getNPC(int i,Point p) {
		float[] floats = new float[TABLECOUNT];
		for(int x=0;x<floats.length;x++) {
			floats[x] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+i*TABLECOUNT+x), p.x, p.y);
		}
		NPC result = new NPC(floats);

		populateNPCData(p, result);
		return result;
	}
	private void populateNPCData(Point p, NPC npc) {
		setSpecies(p, npc);
		setSubspecies(p, npc);
		setJob(p, npc);
		setAsset(p, npc);
		setLiability(p, npc);
		setGoal(p, npc);
		setMisfortune(p, npc);
		setMethod(p, npc);
		setAppearance(p, npc);
		setDetail(p, npc);
		setCostume(p, npc);
		setPersonality(p, npc);
		setMannerism(p, npc);
		setHobby(p, npc);
		setReputation(p, npc);
		setSecret(p, npc);
		setDomain(p, npc);
		setRelationship(p, npc);
		setName(p, npc);
		setFaction(p, npc);
		setDescriptors(p,npc);
	}


	private void setSpecies(Point p, NPC result) {
		if(result.getSpecies()==null) {
			WeightedTable<Species> demo = population.getTransformedDemographics(p);
			if(demo.getSumWeight()>0) {
				Species species = demo.getByWeight(result);
				result.setSpecies(species);
			}else {
				Species species = getRandomNPCSpecies(result);
				result.setSpecies(species);
			}
		}
	}
	private void setSubspecies(Point p, NPC result) {
		if(Species.GOBLINOID.equals(result.getSpecies())) {
			result.setSubspecies(HumanoidNameGenerator.getGoblinoid(result));
		}
	}
	private void setJob(Point p, NPC npc) {
		npc.setJob(getJob(npc,population.isCity(p), population.isTown(p)));
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
		if(npc.getSpecies()!=null&&npc.getSpecies().getNPCNameGen()!=null) {
			String name = npc.getSpecies().getNPCNameGen().getName(npc);
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
	public Species getRandomNPCSpecies(Indexible obj) {
		Species[] species = Species.getAbeirNPCSpecies();
		return (Species) Util.getElementFromArray(species, obj);
	}
	@Deprecated
	@Override
	public NPC getDefaultValue(Point p, int i) {
		return getNPC(i, p);
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

}
