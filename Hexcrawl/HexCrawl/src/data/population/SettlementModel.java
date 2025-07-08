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
import util.Util;
import view.InfoPanel;

public class SettlementModel extends DataModel{
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
	public static final String LEADER = "Respected,Feared,Easily manipulated,Illigitimate,Monsterous,Contested,Indecisive,Incompetent,Declining,Iron-willed,Puppet";
	private static WeightedTable<String> leader;
	public static final String GOVERNMENT = "Autocrat,Elected Official,Monarch,Plutocrats,Aristocracy,Theocracy,Tribes,Commune,Elected Council";
	private static WeightedTable<String> government;
	public static final String RELATIONSHIP = "${A} proposed a bad trade deal and was accepted.,"
			+ "${A} proposed a fair-trade deal and was accepted.,"
			+ "${A} proposed a great trade deal and was accepted.,"
			+ "${A} proposed a bad trade deal and was refused.,"
			+ "${A} proposed a fair-trade deal and was refused.,"
			+ "${A} proposed a great trade deal and was refused.,"
			+ "${A} requested an alliance and was accepted.,"
			+ "${A} requested an alliance and was refused.,"
			+ "${A} started a war.,"
			+ "${A} won a war.,"
			+ "${A} lost a war.,"
			+ "${A} joined an ally in war against ${B}.,"
			+ "${A} joined an ally in war and won.,"
			+ "${A} joined an ally in war and lost.,"
			+ "${A} discovered an affair between the ruling power’s spouses.,"
			+ "${A} proposed a political marriage and was denied.,"
			+ "${A} proposed a political marriage and was accepted.,"
			+ "${A} attempted to bribe officials in ${B},"
			+ "Higher up in ${A} produced Illegitmate Child from ${B} complicating the line of succession and power,"
			+ "${A} aided in rigging an election in their favor and was caught.,"
			+ "${A} aided in rigging an election in their favor and succeeded.,"
			+ "${A} aided in rigging an election in their favor and failed.,"
			+ "${A} abducted noble for blackmail.,"
			+ "${A} attempted an assassination and succeeded.,"
			+ "${A} attempted an assassination and failed.,"
			+ "${A} invaded ${B} in search of resources.,"
			+ "${A} invaded ${B} in search of treasure.,"
			+ "${A} invaded ${B} in religious belief.,"
			+ "${A} invaded ${B} in search of new land.,"
			+ "${A} hosted an assembly to open negotiations.,"
			+ "${A} attempted to start a spy ring in ${B} and succeeded.,"
			+ "${A} attempted to start a spy ring in ${B} and failed.,"
			+ "${A} discovered a spy ring run by ${B}.,"
			+ "${A} sent priests to ${B} to spread their religion and succeeded,"
			+ "${A} sent priests to ${B} to spread their religion and are persecuted,"
			+ "${A} tried to collect repayment for a loan and succeeded.,"
			+ "${A} tried to collect repayment for a loan and failed.,"
			+ "${A} provided aid after a natural disaster.,"
			+ "${A} took advantage of ${B} after they suffered a natural disaster.,"
			+ "${A} helped depose the previous ruler to install the current ruler.,"
			+ "${A} broke a non-aggression pact.,"
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
			+ "Kingdoms raced to achieve technological or social victory (sail around the world build the tallest structure etc),"
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
			+ "${A}'s new work of art swept through foreign land,"
			+ "Strange phenomena caused Kingdoms to work together to investigate its occurance,"
			+ "People went missing and multiple Kingdoms engaged in discovering why,"
			+ "${A} started a war with ${B} to force a marriage,"
			+ "${A} funded rebel group/barbarians/religious zealots within ${B} empowering them to attack,"
			+ "${A} sent an emissary to ${B} to learn of their customs and return,"
			+ "${A} sent thieves to steal religious artifact,"
			+ "${A} sent thieves to steal historical object / object of national pride,"
			+ "${A} stole significant object of ${B}'s nobility,"
			+ "${A} helped criminal escape into their country,"
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
		leader = new WeightedTable<String>().populate(LEADER,",");
		government = new WeightedTable<String>().populate(GOVERNMENT,",");
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
	public static String getLeadership(Indexible obj) {
		if(leader==null) populateAllTables();
		return leader.getByWeight(obj)+" "+government.getByWeight(obj).toLowerCase();
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
		float[] vals = new float[SETTLEMENTTABLES];
		for(int j=0;j<SETTLEMENTTABLES;j++) {
			vals[j] = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+j), p.x, p.y);
		}
		Settlement result = new Settlement(vals);
		result.setController(controller);
		populateSettlementDetails(result,p);
		populateDistricts(p, result);
		return result;
	}
	public Object getSettlement(Point p,Random random) {
		int[] vals = new int[SETTLEMENTTABLES];
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
		result.setLeadership(getLeadership(result));
		result.setEvent(getEvent(result));
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
	private void populateFactionDetails(Faction result, Point p) {
		boolean isFaith = result.getType().isFaith();
		if(isFaith) result.setDomain(NPCModel.getDomain(result));
		String name = FactionNameGenerator.getName(result.getType(),result);
		result.setName(name);
		result.setTrait(FactionNameGenerator.getTrait(result));
		String goal = formatGoal(FactionNameGenerator.getGoal(result),result,isFaith);
		result.setGoal(Util.formatTableResultPOS(goal,result,p,record.getZero()));
	}
	
	public Faction getFaction(int i,Point p) {
		Faction result = getIndexedFaction(i, p);
		FactionType type = FactionType.getFaction(result);
		result.setType(type);
		populateFactionDetails(result, p);
		return result;
	}
	public Faction getFaction(Random random,Point p) {
		Faction result = getRandomIndexedFaction(random);
		FactionType type = FactionType.getFaction(result);
		result.setType(type);
		populateFactionDetails(result, p);
		return result;
	}
	public Faction getFaith(int i,Point p) {
		Faction result = getIndexedFaction(i+InfoPanel.FACTIONCOUNT, p);
		FactionType type = FactionType.getFaith(result);
		result.setType(type);
		populateFactionDetails(result, p);
		return result;
	}
	public Faction getFaith(Random random,Point p) {
		Faction result = getRandomIndexedFaction(random);
		FactionType type = FactionType.getFaith(result);
		result.setType(type);
		populateFactionDetails(result, p);
		return result;
	}
	public Faction getFaction(int i,Point p,FactionType... types) {
		Faction result = getIndexedFaction(i, p);
		FactionType type = (FactionType) Util.getElementFromArray(types, result);
		result.setType(type);
		populateFactionDetails(result, p);
		return result;
	}
	public Faction getFaction(Random random,Point p,FactionType... types) {
		Faction result = getRandomIndexedFaction(random);
		FactionType type = (FactionType) Util.getElementFromArray(types, result);
		result.setType(type);
		populateFactionDetails(result, p);
		return result;
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
	public String getRelationship(Point p1,Point p2) {
		float f1 = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+SETTLEMENTTABLES), p1.x, p1.y);
		float f2 = OpenSimplex2S.noise2(record.getSeed(SEED_OFFSET+SETTLEMENTTABLES), p2.x, p2.y);
		Indexible obj = new Indexible(f1+f2);
		String relationship = getRelationship(obj);
		if(f1<f2) {
			Point p = p2;
			p2 = p1;
			p1 = p;
		}
		if(obj.reduceTempId(2)==0) {
			relationship = Util.replace(relationship, "${A}", new Reference(HexData.TOWN, record.normalizePOS(p1), 0).toString());
			relationship = Util.replace(relationship, "${B}", new Reference(HexData.TOWN, record.normalizePOS(p2), 0).toString());
		}else {
			relationship = Util.replace(relationship, "${A}", new Reference(HexData.TOWN, record.normalizePOS(p2), 0).toString());
			relationship = Util.replace(relationship, "${B}", new Reference(HexData.TOWN, record.normalizePOS(p1), 0).toString());
		}
		return relationship;
	}



}
