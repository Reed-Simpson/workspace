package data.population;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import controllers.DataController;
import data.DataModel;
import data.HexData;
import data.Indexible;
import data.OpenSimplex2S;
import data.Reference;
import data.WeightedTable;
import data.magic.MagicModel;
import data.magic.MagicType;
import data.npc.Faction;
import data.npc.NPCModel;
import io.SaveRecord;
import names.FactionNameGenerator;
import names.FactionType;
import util.Pair;
import util.Util;
import view.InfoPanel;

public class SettlementModel extends DataModel{
	public static final int THREAT_FACTION_INDEX = InfoPanel.FACTIONCOUNT+InfoPanel.FAITHCOUNT+1;
	public static final int LEADERSHIP_FACTION_INDEX = 0;
	private static final int MEDIAN_RECENT_EVENT_YEARS = 5;
	private static final int SEED_OFFSET = 10*Util.getOffsetX();
	private static final int FACTIONTABLES = 20;
	private static final int SETTLEMENTTABLES = 5;
	//private static final int TABLECOUNT = FACTIONTABLES+5;
	public static final String THEMES = "${animal},Aristocracy,Art,Bureaucracy,Castes,Catacombs,${city activity},${city event},Crime Families,Cruelty,${district index},${domain},"+
			"${faction index},Festivals,Feuds,Intrigue,${lower class building},Martial Law,Meritocracy,${job},Opulence,${physical element},Pilgrimages,Piracy,"+
			"Plutocracy,Poverty,Rituals,Slavery,Spices,Theocracy,Thievery,Trade,Tyranny,${upper class building},Wizardry,Xenophobia";
	private static WeightedTable<String> themes;
	public static final String EVENTS = "Assassination,Carnival,Conscription,Coronation,Coup,Cult Activity,Curfew,Discovery,Earthquake,${faction index} War,Fashion Trend,Fire,"+
			"Flood,Heavy Fog,Heavy Taxes,Holy Day,Hysteria,Inquisition,Insurrection,Invasion,Jailbreak,Mass Eviction,Mass Pardon,Negotiations,"+
			"Plague,Proclamation,Prohibition,Public Games,Refugees,Rioting,Roundup,Scandal,Serial Killer,Shortage,Tournament,Trial";
	private static WeightedTable<String> events;
	public static final String DISTRICTS = "Catacombs,${civilized npc},Construction,Crafts,Criminality,Culture,Dining,Education,Entertainment,Finance,Foreigners,Ghettoes,"+
			"Government,Graveyards,Green Space,Industrialization,Judgement,Livestock,Marketplace,Memorials,Military,Opulence,Pollution,Poverty,"+
			"Punishment,Religion,Science,Trade,Trash,${underworld npc},${upper class building},${lower class building},Vices,${wilderness npc},Wizardry,Wonders";
	private static WeightedTable<String> districts;
	public static final String ACTIVITIES = "Abduct,Beg,Brawl,Burgle,Celebrate,Chase,Construct,Cook,Dance,Duel,${dungeon activity},Execute,"+
			"Extinguish,Extort,Follow,Gamble,Interrogate,Marry,${mission},Mourn,Party,Patrol,Perform,"+
			"Play,Preach,Process,Proclaim,Protest,Release,Repair,Riot,Rob,Search,Sell,${wilderness activity}";
	private static WeightedTable<String> activities;
	public static final String ROOMS = "arboretum,atrium,attic,aviary,ballroom,baths,bed chamber,cabinet,chapel,cloakroom,dining room,dressing room,"+
			"${dungeon room},garden,garret,greenhouse,junk room,kitchen,larder,library,map room,menagerie,mews,nursery,"+
			"pantry,parlor,privey,root cellar,saucery,scullery,smoking room,spicery,still room,study,trophy room,wardrobe";
	private static WeightedTable<String> rooms;
	public static final String STREETS = "arcade,awnings,balconies,barricades,bridge,canal,carriages,catwalks,${city activity},climbable walls,clotheslines,crowd,"+
			"dead end,dense fog,downpour,${dungeon activity},flooding,food stalls,fountain,gates,ladders,livestock,muddy,overgrown,"+
			"roof access,roof gardens,sewer access,sinkhole,slick,steep roofs,steep streets,steps,torn up street,vermin swarms,well,${wilderness activity}";
	private static WeightedTable<String> streets;
	public static final String FEATURES = "${animal} nests,balconies,basement access,brightly lit,broken furniture,broken glass,cabinets,carpeted floors,chandeliers,crawlspaces,drain pipes,dumbwaiters,"+
			"echoing marble,hanging chains,huge fireplace,narrow ledges,open windows,ornate weapons,overgrown,patrols,piles of trash,pillars,rotting ceiling,rotting floors,"+
			"rotting walls,screens,servant passages,sewer access,shadowy alcoves,skylights,spyholes,staircases,tall bookshelves,unlit,watchdogs,window drapes";
	private static WeightedTable<String> features;
	public static final String LEADER_REP = "Respected,Feared,Easily manipulated,Illigitimate,Monsterous,Contested,Indecisive,Incompetent,Declining,Iron-willed,Puppet";
	private static WeightedTable<String> leader_rep;
	public static final String RELATIONSHIP = "${A} proposed a bad trade deal and was accepted.,"
			+ "${A} proposed a fair-trade deal with ${B} and was accepted.,"
			+ "${A} proposed a great trade deal with ${B} and was accepted.,"
			+ "${A} proposed a bad trade deal with ${B} and was refused.,"
			+ "${A} proposed a fair-trade deal with ${B} and was refused.,"
			+ "${A} proposed a great trade deal with ${B} and was refused.,"
			+ "${A} requested an alliance with ${B} and was accepted.,"
			+ "${A} requested an alliance with ${B} and was refused.,"
			+ "${A} started a war with ${B}.,"
			+ "${A} won a war with ${B}.,"
			+ "${A} lost a war with ${B}.,"
			+ "${A} joined an ally in war against ${B}.,"
			+ "${A} joined an ally in war against ${B} and won.,"
			+ "${A} joined an ally in war against ${B} and lost.,"
			+ "${A} and ${B} discovered an affair between the ruling power’s spouses.,"
			+ "${A} proposed a political marriage with ${B} and was denied.,"
			+ "${A} proposed a political marriage with ${B} and was accepted.,"
			+ "${A} attempted to bribe officials in ${B},"
			+ "Higher up in ${A} produced Illegitmate Child from ${B} complicating the line of succession and power,"
			+ "${A} manipulated the power structure of ${B} in their favor and was caught.,"
			+ "${A} manipulated the power structure of ${B} in their favor and succeeded.,"
			+ "${A} manipulated the power structure of ${B} in their favor and failed.,"
			+ "${A} abducted noble from ${B} for blackmail.,"
			+ "${A} attempted an assassination in ${B} and succeeded.,"
			+ "${A} attempted an assassination in ${B} and failed.,"
			+ "${A} invaded ${B} in search of resources.,"
			+ "${A} invaded ${B} in search of treasure.,"
			+ "${A} invaded ${B} out of religious belief.,"
			+ "${A} invaded ${B} in search of new land.,"
			+ "${A} hosted a diplomatic assembly with ${B}.,"
			+ "${A} attempted to start a spy ring in ${B} and succeeded.,"
			+ "${A} attempted to start a spy ring in ${B} and failed.,"
			+ "${A} discovered a spy ring run by ${B}.,"
			+ "${A} sent priests to ${B} to spread their religion and succeeded,"
			+ "${A} sent priests to ${B} to spread their religion and are persecuted,"
			+ "${A} tried to collect repayment for a loan from ${B} and succeeded.,"
			+ "${A} tried to collect repayment for a loan from ${B} and failed.,"
			+ "${A} provided aid to ${B} after a natural disaster.,"
			+ "${A} took advantage of ${B} after they suffered a natural disaster.,"
			+ "${A} helped depose the previous ruler of ${B} to install the current ruler.,"
			+ "${A} broke a non-aggression pact with ${B}.,"
			+ "${A} secured a treaty that humilated ${B}.,"
			+ "${A} aided ${B} in defeating a rebellion.,"
			+ "${A} refused to aid ${B} in defeating a rebellion.,"
			+ "${A} sent Refugees to ${B} and the economy tanked from supporting them.,"
			+ "${A} sent Refugees to ${B} and the economy grew with new workers and trade.,"
			+ "${A} hosted a world faire to boost economy trade and renown.,"
			+ "${A} discovered new resource and tried to sell it to ${B},"
			+ "${A} learns of ${B}'s resource (which they lack) and tried to trade for it,"
			+ "${A} lead religious crusade on ${B},"
			+ "${A} attempted to establish colony on ${B},"
			+ "${A} inspired fashion or lifestyle trend on ${B},"
			+ "${A} took a high ranking noble from ${B} prisoner,"
			+ "${A} wrote influential literature that reached ${B},"
			+ "${A} ruler visited ${B} and implemented some of its laws,"
			+ "${A} uncovered ${B}’s plans before they were acted upon: technology war trade etc.,"
			+ "${A} allied with ${B} to uncover magic secrets or mysteries,"
			+ "${A} allied with ${B} to create magical / technological wonder,"
			+ "${A} inspired artistic reform or trend in ${B},"
			+ "${A} sabotaged ${B}'s plans/tech/economy (famine blight etc),"
			+ "${A} used magic to scry on ${B} and was caught,"
			+ "${A} used magic to scry on ${B} and succeeded,"
			+ "${A} used magic to scry on ${B} and failed,"
			+ "${A} and ${B} raced to achieve technological or social victory (sail around the world build the tallest structure etc),"
			+ "${A} sent urgent aid to ${B} who was struggling with disease and sickness,"
			+ "${A} sent urgent aid to ${B} who was struggling with monster threat,"
			+ "${A} requested urgent aid from ${B} struggling with disease and sickness,"
			+ "${A} requested urgent aid from ${B} struggling with monster threat,"
			+ "${A} requested to explore a planar rift with ${B},"
			+ "${A} raced with ${B} to find lost object artifact or location,"
			+ "${A} worked with ${B} to find lost object artifact or location,"
			+ "${A} impeded ${B}'s work to find lost object artifact or location,"
			+ "${A} stopped ${B} from finding lost object artifact or location,"
			+ "${A} allied with ${B} to explore uncharted territory,"
			+ "${A} raced against ${B} to explore uncharted territory,"
			+ "${A} warred with ${B} to stop them from exploring uncharted territory,"
			+ "${A} began teaching ${B}'s military tactics advancing / changing their strategies,"
			+ "${A} hired mercenaries from ${B} to drill and train their soldiers in tactics,"
			+ "${A}'s new work of art swept through ${B},"
			+ "${A} and ${B} cooperate to investigate a strange phenomena,"
			+ "${B} and ${B} cooperate to investigate the cause of missing people,"
			+ "${A} started a war with ${B} to force a marriage,"
			+ "${A} funded rebel group/barbarians/religious zealots within ${B} empowering them to attack,"
			+ "${A} sent an emissary to ${B} to learn of their customs and return,"
			+ "${A} sent thieves to steal religious artifact from ${B},"
			+ "${A} sent thieves to steal cultural artifact from ${B},"
			+ "${A} stole significant object of ${B}'s nobility,"
			+ "${A} sheltered a wanted criminal from ${B},"
			+ "${A} banished signifcant figure to ${B},"
			+ "${A} established an embassy in ${B},"
			+ "${A} rebels seized embassy of ${B} and demand ransom in exchange for hostages,"
			+ "${A} captured wanted criminal of ${B} in their land both sides fight to press charges,"
			+ "${A} discovered secret of higher up in ${B} and uses it to damage reputation / stir up rebellion,"
			+ "${A} discovered one of their spies/ ambassadors in ${B} was leaking info / betraying them,"
			+ "Large guild in ${A} moved their base of operations to ${B},"
			+ "Large guild established base of operations in ${B},"
			+ "${A} took in defectors from ${B},"
			+ "Drug from ${A} is sold in ${B}";
	private static WeightedTable<String> relationship;



	private static void populateAllTables() {
		themes = new WeightedTable<String>().populate(THEMES,",");
		events = new WeightedTable<String>().populate(EVENTS,",");
		districts = new WeightedTable<String>().populate(DISTRICTS,",");
		activities = new WeightedTable<String>().populate(ACTIVITIES,",");
		rooms = new WeightedTable<String>().populate(ROOMS,",");
		streets = new WeightedTable<String>().populate(STREETS,",");
		features = new WeightedTable<String>().populate(FEATURES,",");
		leader_rep = new WeightedTable<String>().populate(LEADER_REP,",");
		relationship = new WeightedTable<String>().populate(RELATIONSHIP,",");
	}


	public static String getTheme(Indexible obj) {
		if(themes==null) populateAllTables();
		return Util.formatTableResult(themes.getByWeight(obj),obj);
	}
	public static String getEvent(Indexible obj) {
		if(events==null) populateAllTables();
		return Util.formatTableResult(events.getByWeight(obj),obj);
	}
	public static String getDistrict(Indexible obj) {
		if(districts==null) populateAllTables();
		return Util.formatTableResult(districts.getByWeight(obj),obj);
	}
	public static String getActivity(Indexible obj) {
		if(activities==null) populateAllTables();
		return Util.formatTableResult(activities.getByWeight(obj),obj);
	}
	public static String getRoom(Indexible obj) {
		if(rooms==null) populateAllTables();
		return Util.formatTableResult(rooms.getByWeight(obj),obj);
	}
	public static String getStreet(Indexible obj) {
		if(streets==null) populateAllTables();
		return Util.formatTableResult(streets.getByWeight(obj),obj);
	}
	public static String getFeature(Indexible obj) {
		if(features==null) populateAllTables();
		return Util.formatTableResult(features.getByWeight(obj),obj);
	}
	public static String getDiscovery(Indexible obj) {
		if(features==null) populateAllTables();
		return getFeature(obj);
	}
	public static String getReputation(Indexible obj) {
		if(leader_rep==null) populateAllTables();
		return leader_rep.getByWeight(obj);
	}
	public static FactionType getLeadership(Indexible obj) {
		return (FactionType) Util.getElementFromArray(FactionType.CITY_LEADERSHIP, obj);
	}
	public static String getRelationship(Indexible obj) {
		if(relationship==null) populateAllTables();
		return relationship.getByWeight(obj);
	}


	private DataController controller;


	//NON_STATIC CODE

	public SettlementModel(SaveRecord record,DataController controller) {
		super(record);
		this.controller = controller;
	}

	public Settlement getSettlement(Point p) {
		float[] vals = new float[SETTLEMENTTABLES-1];
		for(int j=0;j<vals.length;j++) {
			vals[j] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+j), p.x, p.y);
		}
		Settlement result = new Settlement(vals);
		result.setController(controller);
		populateSettlementDetails(result,p);
		populateDistricts(p, result);
		return result;
	}
	public String getEvent(Point p) {
		Indexible obj = new Indexible(OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+SETTLEMENTTABLES-1), p.x, p.y));
		return getEvent(obj);
	}
	public Settlement getSettlement(Point p,Random random) {
		int[] vals = new int[SETTLEMENTTABLES-1];
		for(int j=0;j<vals.length;j++) {
			vals[j] = random.nextInt();
		}
		Settlement result = new Settlement(vals);
		result.setController(controller);
		populateSettlementDetails(result,p);
		populateDistricts(p,random, result);
		return result;
	}
	private void populateSettlementDetails(Settlement result, Point p) {
		result.setPos(p);
		result.setTheme(Util.formatTableResultPOS(getTheme(result), result, p, record.getZero()));
		result.setReputation(getReputation(result));
		result.setLeadership(getLeadership(result));
		result.setEvent(new Reference(HexData.EVENT, record.normalizePOS(p), 0).toString());
		result.setNeighbors(controller.getEconomy().getNeighboringCities(p));
	}
	private void populateDistricts(Point p, Settlement result) {
		MagicModel magic = controller.getMagic();
		for(int k=0;k<InfoPanel.DISTRICTCOUNT;k++) {
			String district = getDistrict(result);
			if(magic.isWeird(p,k)) district = magic.getAdjective(p, k)+" "+district;
			result.putDistrict(district);
		}
	}
	private void populateDistricts(Point p, Random random, Settlement result) {
		MagicModel magic = controller.getMagic();
		MagicType type = magic.getMagicType(p);
		for(int k=0;k<InfoPanel.DISTRICTCOUNT;k++) {
			String district = getDistrict(result);
			if(magic.isWeird(type,random.nextInt())) district = MagicModel.getAdjective(new Indexible(random.nextInt()))+" "+district;
			result.putDistrict(district);
		}
	}
	
	//FACTION
	private Faction getIndexedFaction(int i, Point p) {
		float[] vals = new float[FACTIONTABLES];
		for(int j=0;j<vals.length;j++) {
			vals[j] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+SETTLEMENTTABLES+j+i*FACTIONTABLES), p.x, p.y);
		}
		Faction result = new Faction(vals);
		return result;
	}
	private Faction getRandomIndexedFaction(Random random) {
		int[] ints = new int[FACTIONTABLES];
		for(int n=0;n<ints.length;n++) {
			ints[n] = random.nextInt();
		}
		Faction result = new Faction(ints);
		return result;
	}
	private void populateFactionDetails(Faction result, Point p,int i) {
		boolean isFaith = result.getType().isFaith();
		if(isFaith) result.setDomain(NPCModel.getDomain(result));
		String name = FactionNameGenerator.getName(result.getType(),result);
		if(name.contains("${last name}")) name = Util.replace(name,"${last name}",controller.getNpcs().getFactionNPCLastName(i, p));
		result.setName(name);
		result.setTrait(FactionNameGenerator.getTrait(result));
		String goal = formatGoal(FactionNameGenerator.getGoal(result),result,isFaith);
		if(i==THREAT_FACTION_INDEX) {
			populateFactionNPCs(result, p, 1,HexData.MINION);
		} else {
			populateFactionNPCs(result, p, i*2,HexData.FACTION_NPC);
		}
		Point capital = controller.getPopulation().getAbsoluteFealty(p);
		result.setGoal(Util.formatTableResultPOS(goal,result,capital,record.getZero()));
	}


	private void populateFactionNPCs(Faction result, Point p, int i,HexData type) {
		result.setLeader(new Reference(type, record.normalizePOS(p), i));
		result.setMember(new Reference(type, record.normalizePOS(p), i+1));
	}

	public Faction getFaction(int i,Point p,FactionType... types) {
		Faction result = getIndexedFaction(i, p);
		FactionType type = (FactionType) Util.getElementFromArray(types, result);
		result.setType(type);
		populateFactionDetails(result, p,i);
		return result;
	}
	public Faction getFaction(int i,Random random,Point p,FactionType... types) {
		Faction result = getRandomIndexedFaction(random);
		FactionType type = (FactionType) Util.getElementFromArray(types, result);
		result.setType(type);
		populateFactionDetails(result, p,i);
		return result;
	}
	public Faction getFaction(int i,Point p) {
		if(i==0) return getLeadershipFaction(p);
		else return getFaction(i,p,FactionType.FACTIONS);
	}
	public Faction getFaction(Random random,Point p,int i) {
		if(i==0) return getLeadershipFaction(random,p);
		else return getFaction(i,random,p,FactionType.FACTIONS);
	}
	public Faction getFaith(int i,Point p) {
		return getFaction(i+InfoPanel.FACTIONCOUNT,p,FactionType.FAITHS);
	}
	public Faction getFaith(Random random,Point p,int i) {
		return getFaction(i+InfoPanel.FACTIONCOUNT,random,p,FactionType.FAITHS);
	}
	public Faction getLeadershipFaction(Point p) {
		Settlement s = getSettlement(p);
		FactionType[] factionTypes = s.getLeadership().getGovernmentFactions();
		Faction faction = controller.getSettlements().getFaction(LEADERSHIP_FACTION_INDEX, p,factionTypes);
		return faction;
	}
	public Faction getLeadershipFaction(Random random,Point p) {
		Settlement s = getSettlement(p);
		FactionType[] factionTypes = s.getLeadership().getGovernmentFactions();
		Faction faction = controller.getSettlements().getFaction(LEADERSHIP_FACTION_INDEX,random, p,factionTypes);
		return faction;
	}

	private String formatGoal(String goal, Faction result,boolean faith) {
		boolean edge = result.reduceTempId(10)<2;
		if(faith!=edge) {
			goal = Util.replace(goal, "${faction index}", "${faith index}");
		}
		return goal;
	}

	@Override
	public Settlement getDefaultValue(Point p, int i) {
		return getSettlement(p);
	}


	public String getDistrict(int i, Point capital) {
		ArrayList<String> districts = getSettlement(capital).getDistricts();
		if(i<districts.size()&&i>-1) return districts.get(i);
		return null;
	}
	public String getRelationship(Point p1,Point p2,boolean links) {
		if(p2==null) return null;
		float f1 = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+SETTLEMENTTABLES), p1.x, p1.y);
		float f2 = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+SETTLEMENTTABLES), p2.x, p2.y);
		Indexible obj = new Indexible(f1+f2);
		if(f1<f2) {
			Point p = p2;
			p2 = p1;
			p1 = p;
		}
		return populateRelationship(p1, p2, links, obj);
	}


	private String populateRelationship(Point p1, Point p2, boolean links, Indexible obj) {
		String relationship = getRelationship(obj);
		String s1;
		String s2;
		if(links) {
			s1 = new Reference(HexData.TOWN, controller.getRecord().normalizePOS(p1), 0).toString();
			s2 = new Reference(HexData.TOWN, controller.getRecord().normalizePOS(p2), 0).toString();
		}else {
			s1 = controller.getText(HexData.TOWN, p1, 0);
			s2 = controller.getText(HexData.TOWN, p2, 0);
		}
		if(obj.reduceTempId(2)==0) {
			relationship = Util.replace(relationship, "${A}", s1);
			relationship = Util.replace(relationship, "${B}", s2);
		}else {
			relationship = Util.replace(relationship, "${A}", s2);
			relationship = Util.replace(relationship, "${B}", s1);
		}
		double age = obj.getDouble(100);
		double years = Math.pow(10, age)*MEDIAN_RECENT_EVENT_YEARS;
		//System.out.println(MEDIAN_RECENT_EVENT_YEARS+"*10^"+age+"="+years);
		String timeString;
		if(years<1) {
			timeString = " ("+(int)Math.floor(years*12)+" months ago)";
		}else {
			timeString = " ("+(int)Math.floor(years)+" years ago)";
		}
		return relationship+timeString;
	}
	public Pair<Point,Point> getCityPair(Point p,int i) {
		ArrayList<Point> nearby = controller.getEconomy().getNeighboringCities(p);
		if(nearby==null) return null;
		else if(i>=nearby.size()) return new Pair<Point,Point>(p,null);
		else return new Pair<Point,Point>(p,nearby.get(i));
	}
	public String getRelationship(Point p,int i) {
		Pair<Point,Point> pair = getCityPair(p, i);
		if(pair==null) {
			if(i==0) return "Loading...";
			else return null;
		}
		return getRelationship(pair.key1, pair.key2, true);
	}
	public String getRelationship(Point p,int i,Random rand) {
		Pair<Point,Point> pair = getCityPair(p, i);
		return getRelationship(pair.key1, pair.key2, true,rand);
	}
	public String getRelationship(Point p1,Point p2,boolean links,Random rand) {
		Indexible obj = new Indexible(rand.nextInt());
		return populateRelationship(p1, p2, links, obj);
	}


	public Faction getDisplayedFaction(int i, Point p) {
		if(i==LEADERSHIP_FACTION_INDEX) return getLeadershipFaction(p);
		else if(i==THREAT_FACTION_INDEX) return controller.getThreats().getFaction(controller, p, null);
		else if(i<InfoPanel.FACTIONCOUNT) return getFaction(i, p);
		else return getFaith((i-InfoPanel.FACTIONCOUNT), p);
	}



}
