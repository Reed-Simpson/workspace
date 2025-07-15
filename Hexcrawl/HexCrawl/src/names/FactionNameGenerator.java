package names;

import java.util.LinkedHashMap;

import data.Indexible;
import data.WeightedTable;
import util.Util;

public class FactionNameGenerator extends IndexibleNameGenerator{
	private static LinkedHashMap<FactionType,FactionTypeNameGenerator> factionNameGenerators;
	//	private static final String FACTIONS = "art movement,beggar's guild,black market,brotherhood,city guard,conspiracy,craft guild,crime family,crime ring,dark cult,explorer's club,free company,"+
	//			"gourmand club,heist crew,heretical sect,high council,hired killers,local militia,national church,noble house,outlander clan,outlaw gang,political party,religious order,"+
	//			"religious sect,resistance,royal army,royal house,scholar's circle,secret society,spy network,street artists,street gang,street musicians,theater troupe,trade company";
	public static final String TRAITS = "bankrupt,bureaucratic,charitable,confused,connected,corrupt,decadent,decaying,delusional,divided,dwindling,efficient,"+
			"esoteric,expanding,hunted,incompetent,incorruptible,insane,insular,manipulative,martial,${personality},pious,popular,"+
			"righteous,ruthless,secret,subversive,suppressed,threatened,thriving,unpopular,up-and-coming,wealthy,well-prepared,xenophobic";
	private static WeightedTable<String> traits;
	public static final String GOALS = "advise leader,avoid detection,awaken being,collect artifacts,construct base,control ${faction index},control politics,create artifact,create monster,defeat ${faction index},defend borders,defend leader,"+
			"destroy artifacts,destroy being,destroy villain,enforce law,enrich members,entertain,exchange goods,hear rumors,indulge tastes,infiltrate ${faction index},map the wilds,overthrow order,"+
			"preserve lineage,preserve lore,produce goods,promote arts,promote craft,purge traitors,sell services,share knowledge,spread beliefs,summon evil,survive,transport goods";
	private static WeightedTable<String> goals;

	public static final String CHURCH_ADJECTIVES = "Eldritch,Infernal,Abyssal,Draconic,Elemental,Celestial,Humanist";
	public static final String CHURCH_NOUNS = "The Church of ${adj} ${placeholder domain},The Temple of ${adj} ${placeholder domain},The Servants of ${adj} ${placeholder domain},"
			+ "Order of the ${spell},Children of the ${spell},Cult of the ${spell}";
	public static final String CRIMINAL_ADJECTIVES= "${underworld npc},${color} Cloak,Hidden,Secret,Whispering,Masked,Shrouded,Midnight,Shadow,Veiled,Dusk,Twilight,Lurking,Hollow,Silent,Desperate";
	public static final String CRIMINAL_NOUNS= "Veil,Cartel,Defiance,Syndicate,Brotherhood,Shadows,Network,Ring,Hands,Gang,Clan";
	public static final String UNDERCLASS_ADJECTIVES= "Shattered,Ragged,Forgotten,Lost,Tattered,Hollow,Gutter,Shivering,Silent,Desperate,${misfortune}";
	public static final String UNDERCLASS_NOUNS= "Rags,Plea,Dregs,Lurks,Children,Beggars,Watchers";
	public static final String MERCHANT_ADJECTIVES= "${color} Lotus,${last name},${fancy color},Wandering,Exotic,Mirage,${material} Caravan";
	public static final String MERCHANT_NOUNS= "Guild,Consortium,Syndicate,Merchant Alliance,Brokers,Market,Auctionhouse,Traders,League";
	public static final String ESOTERIC_ADJECTIVES= "Occult,Celestial,Starlit,${weirdness},Mystic,Illumnated,Enigmatic,Arcane,Esoteric,Divine,${effect},Shadowed";
	public static final String ESOTERIC_NOUNS= "Brotherhood of the ${material} ${physical form},Sons of the ${structure},Covenant of ${dungeon room},Order of the ${color} ${element},"+
			"Daughters of the ${color} Veil,Keepers of the ${fancy color} Oath,Fellowship of the ${fancy color} ${form},Knights of the ${material} Heart,"+
			"Society of the ${monster trait} ${monster feature}";
	public static final String DEFENDER_ADJECTIVES= "Ironclad,${color} Shield,Watchful,King's,Resolute,Sentinel's,Queen's,Royal,Vigilant,Unbreakable,Unyielding,"+
			"Adamantine,${basic color} Steel,Iron ${landmark},${metal} Heart";
	public static final String DEFENDER_NOUNS= "Sentinels,Watchers,Garrison,Guard,Patrol,Wardens,Shields,Sentries,Bastion,Gatekeepers,Aegis,Protectors,"+
			"Company,Legion,Brigade,Companions,${animal}s,${weapon}s";
	public static final String ATTACKER_ADJECTIVES= "Lethal,${basic color} Steel,Iron ${landmark},${metal} Heart,Bloody,${basic color} ${animal},${color} ${weapon},${monster trait} ${weapon}";
	public static final String ATTACKER_NOUNS= "Vanguard,Enforcers,Company,Legion,Brigade,Raiders,Companions,Killers,Marauders,Slayers,Hunters";


	public static final String GENERIC_ADJECTIVES= "${material},${animal},Lost,Forgotten,Aetheric,Fateful,Illumnated,Shadowed,Esoteric,Gilded,${fancy color},${effect}";
	public static final String GENERIC_NOUNS= "Collective,Society,Circle,Enclave,Accord,Assembly,Covenant,Brotherhood,Group";
	private static WeightedTable<String> adjectives;
	private static WeightedTable<String> nouns;
	public static final String ARTMOVEMENT_ADJECTIVES= "Claybound,Marble,Quillbound,Brushbound,Prismatic,Chromatic,Canvas,Mosaic,Echoing,Inkbound,Theatric";
	public static final String ARTMOVEMENT_NOUNS= "Sculptors,Writers,Painters,Artisans,Architects,Actors,Musicians,Lyricists,Palette,Brush,Chorus,Visionaries";
	private static WeightedTable<String> art_adjectives;
	private static WeightedTable<String> art_nouns;
	public static final String BEGGAR_ADJECTIVES= UNDERCLASS_ADJECTIVES+",Mendicant,Lurking,Dead ${animal}";
	public static final String BEGGAR_NOUNS= CRIMINAL_NOUNS+","+UNDERCLASS_NOUNS;
	private static WeightedTable<String> beggar_adjectives;
	private static WeightedTable<String> beggar_nouns;
	public static final String BLACKMARKET_ADJECTIVES= CRIMINAL_ADJECTIVES+","+MERCHANT_ADJECTIVES+",Obsidian,Gloomweaver";
	public static final String BLACKMARKET_NOUNS= CRIMINAL_NOUNS+","+MERCHANT_NOUNS;
	private static WeightedTable<String> blackmarket_adjectives;
	private static WeightedTable<String> blackmarket_nouns;
	public static final String BROTHERHOOD_ADJECTIVES= ESOTERIC_ADJECTIVES;
	public static final String BROTHERHOOD_NOUNS= ESOTERIC_NOUNS;
	private static WeightedTable<String> brotherhood_adjectives;
	private static WeightedTable<String> brotherhood_nouns;
	public static final String GUARD_ADJECTIVES= DEFENDER_ADJECTIVES;
	public static final String GUARD_NOUNS= DEFENDER_NOUNS;
	private static WeightedTable<String> guard_adjectives;
	private static WeightedTable<String> guard_nouns;
	public static final String CONSPIRACY_ADJECTIVES = CRIMINAL_ADJECTIVES+","+ESOTERIC_ADJECTIVES+","+UNDERCLASS_ADJECTIVES;
	public static final String CONSPIRACY_NOUNS = "Veiled ${job}s,Circle of the ${city theme},Conclave of the ${district} District,Society of the ${building},Keepers of the ${city event}";
	private static WeightedTable<String> conspiracy_adjectives;
	private static WeightedTable<String> conspiracy_nouns;
	public static final String CRAFTGUILD_ADJECTIVES = "Forgemaster's,Alchemists',Stonecarvers',Illuminated,Weaver's,Tinker's,${basic color} Anvil,${basic color} Hammer,${basic color} Chisel";
	public static final String CRAFTGUILD_NOUNS = MERCHANT_NOUNS+",Fellowship,Makers,Workbench,Artificers";
	private static WeightedTable<String> craftguild_adjectives;
	private static WeightedTable<String> craftguild_nouns;
	public static final String CRIMERING_ADJECTIVES = CRIMINAL_ADJECTIVES+","+MERCHANT_ADJECTIVES;
	public static final String CRIMERING_NOUNS = CRIMINAL_NOUNS+","+MERCHANT_NOUNS+",Talons,Claws,Teeth,Pact,Family,Serpents";
	private static WeightedTable<String> crimering_adjectives;
	private static WeightedTable<String> crimering_nouns;
	public static final String CRIMEFAMILY_ADJECTIVES = CRIMINAL_ADJECTIVES+",${last name}";
	public static final String CRIMEFAMILY_NOUNS = CRIMINAL_NOUNS+",Guild,Family,Consortium";
	private static WeightedTable<String> crimefamily_adjectives;
	private static WeightedTable<String> crimefamily_nouns;
	public static final String DARKCULT_ADJECTIVES = CHURCH_ADJECTIVES+","+ESOTERIC_ADJECTIVES;
	public static final String DARKCULT_NOUNS = CHURCH_NOUNS+","+ESOTERIC_NOUNS+",Sons of the ${monster trait} ${monster feature},Daughters of ${dungeon ruination},"+
			"Order of the Dark ${dungeon trick},Faithful Who ${insanity},Children of the ${monster trait} ${animal},Acolytes of the ${weirdness} ${structure},Disciples of the ${hazard}";
	private static WeightedTable<String> darkcult_adjectives;
	private static WeightedTable<String> darkcult_nouns;
	public static final String EXPLORERCLUB_ADJECTIVES = "${color} Summit,${biome},Horizon and ${apparel},${landmark},${color} Horizon,${material} Compass";
	public static final String EXPLORERCLUB_NOUNS = "Wanderers,Wayfarers,Explorers,Club,Chasers,Pathfinders,Adventurers,Seekers,Navigators";
	private static WeightedTable<String> explorerclub_adjectives;
	private static WeightedTable<String> explorerclub_nouns;
	public static final String FREECOMPANY_ADJECTIVES = DEFENDER_ADJECTIVES+","+ATTACKER_ADJECTIVES;
	public static final String FREECOMPANY_NOUNS = DEFENDER_NOUNS+","+ATTACKER_NOUNS+",Mercenaries,Free Company";
	private static WeightedTable<String> freecompany_adjectives;
	private static WeightedTable<String> freecompany_nouns;
	public static final String GOURMANDCLUB_ADJECTIVES = "Culinary,Dinner,Flavor,Elegant,Gourmet,${edible plant},Spiced,Hungry";
	public static final String GOURMANDCLUB_NOUNS = "Gastronomes,Gourmands,Eaters,Gluttons,Pioneers,Gurus,Platter,Buffet";
	private static WeightedTable<String> gourmandclub_adjectives;
	private static WeightedTable<String> gourmandclub_nouns;
	public static final String HEISTCREW_ADJECTIVES = "${misfortune},${reputation},Second-story,Elite";
	public static final String HEISTCREW_NOUNS = CRIMINAL_NOUNS+",Phantoms,Ghosts,Spectres,Phantasms,Bogeymen";
	private static WeightedTable<String> heistcrew_adjectives;
	private static WeightedTable<String> heistcrew_nouns;
	public static final String HERETICALSECT_ADJECTIVES = CHURCH_ADJECTIVES;
	public static final String HERETICALSECT_NOUNS = CHURCH_NOUNS+",Prophets of ${placeholder domain},Seekers of the ${spell},Conclave of ${placeholder domain},Heralds of the ${spell},Reformists of ${placeholder domain}";
	private static WeightedTable<String> hereticalsect_adjectives;
	private static WeightedTable<String> hereticalsect_nouns;
	public static final String HIGHCOUNCIL_ADJECTIVES = "Summit,Prime,Supreme,${color} Robed,${rare material},${material}-Masked";
	public static final String HIGHCOUNCIL_NOUNS = "Circle,Assembly,Council,Tribunal,Court,Elders,Delegates,Representatives,Congress,Senate";
	private static WeightedTable<String> highcouncil_adjectives;
	private static WeightedTable<String> highcouncil_nouns;
	public static final String HIREDKILLERS_ADJECTIVES = CRIMINAL_ADJECTIVES+","+ATTACKER_ADJECTIVES+",${weirdness} Death,Vicious ${animal}";
	public static final String HIREDKILLERS_NOUNS = CRIMINAL_NOUNS+","+ATTACKER_NOUNS+",Vultures,Revenants,Assassins";
	private static WeightedTable<String> hiredkillers_adjectives;
	private static WeightedTable<String> hiredkillers_nouns;
	public static final String LOCALMILITIA_ADJECTIVES = DEFENDER_ADJECTIVES;
	public static final String LOCALMILITIA_NOUNS = DEFENDER_NOUNS+",Rabble,Irregulars,Militia";
	private static WeightedTable<String> localmilitia_adjectives;
	private static WeightedTable<String> localmilitia_nouns;
	public static final String NATIONALCHURCH_ADJECTIVES = CHURCH_ADJECTIVES;
	public static final String NATIONALCHURCH_NOUNS = CHURCH_NOUNS;
	private static WeightedTable<String> nationalchurch_adjectives;
	private static WeightedTable<String> nationalchurch_nouns;
	public static final String NOBLEHOUSE_ADJECTIVES = "${last name}";
	public static final String NOBLEHOUSE_NOUNS = "House,Lineage,Clan,Line,Lord ${adj}";
	private static WeightedTable<String> noblehouse_adjectives;
	private static WeightedTable<String> noblehouse_nouns;
	public static final String OUTLANDERCLAN_ADJECTIVES = "${city name},${last name},${material}blood,${landmark}warden,${elemental type}${landmark},${basic color}${form},${animal}-lord";
	public static final String OUTLANDERCLAN_NOUNS = "Clan,Tribe,Alliance,Folk,People,Nation,Horde";
	private static WeightedTable<String> outlanderclan_adjectives;
	private static WeightedTable<String> outlanderclan_nouns;
	public static final String OUTLAWGANG_ADJECTIVES = CRIMINAL_ADJECTIVES+","+ATTACKER_ADJECTIVES+",Cunning ${animal}";
	public static final String OUTLAWGANG_NOUNS = CRIMINAL_NOUNS+","+ATTACKER_NOUNS+",Mercenaries,Outlaws";
	private static WeightedTable<String> outlawgang_adjectives;
	private static WeightedTable<String> outlawgang_nouns;
	public static final String POLITICALPARTY_ADJECTIVES = "${city theme} Advocacy,${city event} Planning,${district} Management,Regional ${building},${job} Patronage";
	public static final String POLITICALPARTY_NOUNS = "Party,Alliance,Accord,Coalition,Association,Commission,Council,Institute,League,Union,Society,Group";
	private static WeightedTable<String> politicalparty_adjectives;
	private static WeightedTable<String> politicalparty_nouns;
	public static final String RELIGIOUSORDER_ADJECTIVES = CHURCH_ADJECTIVES;
	public static final String RELIGIOUSORDER_NOUNS = CHURCH_NOUNS;
	private static WeightedTable<String> religiousorder_adjectives;
	private static WeightedTable<String> religiousorder_nouns;
	public static final String RELIGIOUSSECT_ADJECTIVES = CHURCH_ADJECTIVES;
	public static final String RELIGIOUSSECT_NOUNS = CHURCH_NOUNS;
	private static WeightedTable<String> religioussect_adjectives;
	private static WeightedTable<String> religioussect_nouns;
	public static final String RESISTANCE_ADJECTIVES = CRIMINAL_ADJECTIVES+",Freedom";
	public static final String RESISTANCE_NOUNS = ATTACKER_NOUNS+",Rebellion,Insurgency,Alliance,Roots";
	private static WeightedTable<String> resistance_adjectives;
	private static WeightedTable<String> resistance_nouns;
	public static final String ROYALARMY_ADJECTIVES = DEFENDER_ADJECTIVES;
	public static final String ROYALARMY_NOUNS = DEFENDER_NOUNS;
	private static WeightedTable<String> royalarmy_adjectives;
	private static WeightedTable<String> royalarmy_nouns;
	public static final String ROYALHOUSE_ADJECTIVES = NOBLEHOUSE_ADJECTIVES;
	public static final String ROYALHOUSE_NOUNS = NOBLEHOUSE_NOUNS;
	private static WeightedTable<String> royalhouse_adjectives;
	private static WeightedTable<String> royalhouse_nouns;
	public static final String SCHOLARCIRCLE_ADJECTIVES = ESOTERIC_ADJECTIVES+",${color} Quill,${effect},${weirdness},${last name}";
	public static final String SCHOLARCIRCLE_NOUNS = "Scholarium,Academy,University,Library,Literary Society,Historians,Theorists,Scribes,Cartographers,Philosophers,Archivists,"+
			"Theologists,University of ${book},Library of ${hobby},Academy of ${domain}";
	private static WeightedTable<String> scholarcircle_adjectives;
	private static WeightedTable<String> scholarcircle_nouns;
	public static final String SECRETSOCIETY_ADJECTIVES = CRIMINAL_ADJECTIVES+","+ESOTERIC_ADJECTIVES;
	public static final String SECRETSOCIETY_NOUNS = ESOTERIC_NOUNS;
	private static WeightedTable<String> secretsociety_adjectives;
	private static WeightedTable<String> secretsociety_nouns;
	public static final String SPYNETWORK_ADJECTIVES = CRIMINAL_ADJECTIVES;
	public static final String SPYNETWORK_NOUNS = CRIMINAL_NOUNS;
	private static WeightedTable<String> spynetwork_adjectives;
	private static WeightedTable<String> spynetwork_nouns;
	public static final String STREETARTISTS_ADJECTIVES = UNDERCLASS_ADJECTIVES+",${color}";
	public static final String STREETARTISTS_NOUNS = "Muralists,Jugglers,Acrobats,Puppeteers";
	private static WeightedTable<String> streetartists_adjectives;
	private static WeightedTable<String> streetartists_nouns;
	public static final String STREETGANG_ADJECTIVES = OUTLAWGANG_ADJECTIVES;
	public static final String STREETGANG_NOUNS = OUTLAWGANG_NOUNS;
	private static WeightedTable<String> streetgang_adjectives;
	private static WeightedTable<String> streetgang_nouns;
	public static final String STREETMUSICIANS_ADJECTIVES = UNDERCLASS_ADJECTIVES;
	public static final String STREETMUSICIANS_NOUNS = "Lutists,Lyrists,Percussionists,Flutists,Bagpipers,Horn blowers,Violists,Fiddlers,Harpists,Recorderists,Hurdy-gurdyists";
	private static WeightedTable<String> streetmusicians_adjectives;
	private static WeightedTable<String> streetmusicians_nouns;
	public static final String THEATERTROUPE_ADJECTIVES = ESOTERIC_ADJECTIVES+",${fancy color} Stage,${personality} Mask";
	public static final String THEATERTROUPE_NOUNS = "Troupe,Actors' Guild,Theatre,Thespians,Playhouse,Productions";
	private static WeightedTable<String> theatertroupe_adjectives;
	private static WeightedTable<String> theatertroupe_nouns;
	public static final String TRADECOMPANY_ADJECTIVES = MERCHANT_ADJECTIVES;
	public static final String TRADECOMPANY_NOUNS = MERCHANT_NOUNS;
	private static WeightedTable<String> tradecompany_adjectives;
	private static WeightedTable<String> tradecompany_nouns;

	
	


	private static void populateAllTables() {
		traits = new WeightedTable<String>();
		populate(traits,TRAITS,",");
		goals = new WeightedTable<String>();
		populate(goals,GOALS,",");
		adjectives = new WeightedTable<String>();
		populate(adjectives,GENERIC_ADJECTIVES,",");
		nouns = new WeightedTable<String>();
		populate(nouns,GENERIC_NOUNS,",");
		art_adjectives = new WeightedTable<String>();
		populate(art_adjectives,ARTMOVEMENT_ADJECTIVES,",");
		art_nouns = new WeightedTable<String>();
		populate(art_nouns,ARTMOVEMENT_NOUNS,",");
		beggar_adjectives = new WeightedTable<String>();
		populate(beggar_adjectives,BEGGAR_ADJECTIVES,",");
		beggar_nouns = new WeightedTable<String>();
		populate(beggar_nouns,BEGGAR_NOUNS,",");
		blackmarket_adjectives = new WeightedTable<String>();
		populate(blackmarket_adjectives,BLACKMARKET_ADJECTIVES,",");
		blackmarket_nouns = new WeightedTable<String>();
		populate(blackmarket_nouns,BLACKMARKET_NOUNS,",");
		brotherhood_adjectives = new WeightedTable<String>();
		populate(brotherhood_adjectives,BROTHERHOOD_ADJECTIVES,",");
		brotherhood_nouns = new WeightedTable<String>();
		populate(brotherhood_nouns,BROTHERHOOD_NOUNS,",");
		guard_adjectives = new WeightedTable<String>();
		populate(guard_adjectives,GUARD_ADJECTIVES,",");
		guard_nouns = new WeightedTable<String>();
		populate(guard_nouns,GUARD_NOUNS,",");
		conspiracy_adjectives = new WeightedTable<String>();
		populate(conspiracy_adjectives,CONSPIRACY_ADJECTIVES,",");
		conspiracy_nouns = new WeightedTable<String>();
		populate(conspiracy_nouns,CONSPIRACY_NOUNS,",");
		craftguild_adjectives = new WeightedTable<String>();
		populate(craftguild_adjectives,CRAFTGUILD_ADJECTIVES,",");
		craftguild_nouns = new WeightedTable<String>();
		populate(craftguild_nouns,CRAFTGUILD_NOUNS,",");
		crimefamily_adjectives = new WeightedTable<String>();
		populate(crimefamily_adjectives,CRIMEFAMILY_ADJECTIVES,",");
		crimefamily_nouns = new WeightedTable<String>();
		populate(crimefamily_nouns,CRIMEFAMILY_NOUNS,",");
		crimering_adjectives = new WeightedTable<String>();
		populate(crimering_adjectives,CRIMERING_ADJECTIVES,",");
		crimering_nouns = new WeightedTable<String>();
		populate(crimering_nouns,CRIMERING_NOUNS,",");
		darkcult_adjectives = new WeightedTable<String>();
		populate(darkcult_adjectives,DARKCULT_ADJECTIVES,",");
		darkcult_nouns = new WeightedTable<String>();
		populate(darkcult_nouns,DARKCULT_NOUNS,",");
		explorerclub_adjectives = new WeightedTable<String>();
		populate(explorerclub_adjectives,EXPLORERCLUB_ADJECTIVES,",");
		explorerclub_nouns = new WeightedTable<String>();
		populate(explorerclub_nouns,EXPLORERCLUB_NOUNS,",");
		freecompany_adjectives = new WeightedTable<String>();
		populate(freecompany_adjectives,FREECOMPANY_ADJECTIVES,",");
		freecompany_nouns = new WeightedTable<String>();
		populate(freecompany_nouns,FREECOMPANY_NOUNS,",");
		gourmandclub_adjectives = new WeightedTable<String>();
		populate(gourmandclub_adjectives,GOURMANDCLUB_ADJECTIVES,",");
		gourmandclub_nouns = new WeightedTable<String>();
		populate(gourmandclub_nouns,GOURMANDCLUB_NOUNS,",");
		heistcrew_adjectives = new WeightedTable<String>();
		populate(heistcrew_adjectives,HEISTCREW_ADJECTIVES,",");
		heistcrew_nouns = new WeightedTable<String>();
		populate(heistcrew_nouns,HEISTCREW_NOUNS,",");
		hereticalsect_adjectives = new WeightedTable<String>();
		populate(hereticalsect_adjectives,HERETICALSECT_ADJECTIVES,",");
		hereticalsect_nouns = new WeightedTable<String>();
		populate(hereticalsect_nouns,HERETICALSECT_NOUNS,",");
		highcouncil_adjectives = new WeightedTable<String>();
		populate(highcouncil_adjectives,HIGHCOUNCIL_ADJECTIVES,",");
		highcouncil_nouns = new WeightedTable<String>();
		populate(highcouncil_nouns,HIGHCOUNCIL_NOUNS,",");
		hiredkillers_adjectives = new WeightedTable<String>();
		populate(hiredkillers_adjectives,HIREDKILLERS_ADJECTIVES,",");
		hiredkillers_nouns = new WeightedTable<String>();
		populate(hiredkillers_nouns,HIREDKILLERS_NOUNS,",");
		localmilitia_adjectives = new WeightedTable<String>();
		populate(localmilitia_adjectives,LOCALMILITIA_ADJECTIVES,",");
		localmilitia_nouns = new WeightedTable<String>();
		populate(localmilitia_nouns,LOCALMILITIA_NOUNS,",");
		nationalchurch_adjectives = new WeightedTable<String>();
		populate(nationalchurch_adjectives,NATIONALCHURCH_ADJECTIVES,",");
		nationalchurch_nouns = new WeightedTable<String>();
		populate(nationalchurch_nouns,NATIONALCHURCH_NOUNS,",");
		noblehouse_adjectives = new WeightedTable<String>();
		populate(noblehouse_adjectives,NOBLEHOUSE_ADJECTIVES,",");
		noblehouse_nouns = new WeightedTable<String>();
		populate(noblehouse_nouns,NOBLEHOUSE_NOUNS,",");
		outlanderclan_adjectives = new WeightedTable<String>();
		populate(outlanderclan_adjectives,OUTLANDERCLAN_ADJECTIVES,",");
		outlanderclan_nouns = new WeightedTable<String>();
		populate(outlanderclan_nouns,OUTLANDERCLAN_NOUNS,",");
		outlawgang_adjectives = new WeightedTable<String>();
		populate(outlawgang_adjectives,OUTLAWGANG_ADJECTIVES,",");
		outlawgang_nouns = new WeightedTable<String>();
		populate(outlawgang_nouns,OUTLAWGANG_NOUNS,",");
		politicalparty_adjectives = new WeightedTable<String>();
		populate(politicalparty_adjectives,POLITICALPARTY_ADJECTIVES,",");
		politicalparty_nouns = new WeightedTable<String>();
		populate(politicalparty_nouns,POLITICALPARTY_NOUNS,",");
		religiousorder_adjectives = new WeightedTable<String>();
		populate(religiousorder_adjectives,RELIGIOUSORDER_ADJECTIVES,",");
		religiousorder_nouns = new WeightedTable<String>();
		populate(religiousorder_nouns,RELIGIOUSORDER_NOUNS,",");
		religioussect_adjectives = new WeightedTable<String>();
		populate(religioussect_adjectives,RELIGIOUSSECT_ADJECTIVES,",");
		religioussect_nouns = new WeightedTable<String>();
		populate(religioussect_nouns,RELIGIOUSSECT_NOUNS,",");
		resistance_adjectives = new WeightedTable<String>();
		populate(resistance_adjectives,RESISTANCE_ADJECTIVES,",");
		resistance_nouns = new WeightedTable<String>();
		populate(resistance_nouns,RESISTANCE_NOUNS,",");
		royalarmy_adjectives = new WeightedTable<String>();
		populate(royalarmy_adjectives,ROYALARMY_ADJECTIVES,",");
		royalarmy_nouns = new WeightedTable<String>();
		populate(royalarmy_nouns,ROYALARMY_NOUNS,",");
		royalhouse_adjectives = new WeightedTable<String>();
		populate(royalhouse_adjectives,ROYALHOUSE_ADJECTIVES,",");
		royalhouse_nouns = new WeightedTable<String>();
		populate(royalhouse_nouns,ROYALHOUSE_NOUNS,",");
		scholarcircle_adjectives = new WeightedTable<String>();
		populate(scholarcircle_adjectives,SCHOLARCIRCLE_ADJECTIVES,",");
		scholarcircle_nouns = new WeightedTable<String>();
		populate(scholarcircle_nouns,SCHOLARCIRCLE_NOUNS,",");
		secretsociety_adjectives = new WeightedTable<String>();
		populate(secretsociety_adjectives,SECRETSOCIETY_ADJECTIVES,",");
		secretsociety_nouns = new WeightedTable<String>();
		populate(secretsociety_nouns,SECRETSOCIETY_NOUNS,",");
		spynetwork_adjectives = new WeightedTable<String>();
		populate(spynetwork_adjectives,SPYNETWORK_ADJECTIVES,",");
		spynetwork_nouns = new WeightedTable<String>();
		populate(spynetwork_nouns,SPYNETWORK_NOUNS,",");
		streetartists_adjectives = new WeightedTable<String>();
		populate(streetartists_adjectives,STREETARTISTS_ADJECTIVES,",");
		streetartists_nouns = new WeightedTable<String>();
		populate(streetartists_nouns,STREETARTISTS_NOUNS,",");
		streetgang_adjectives = new WeightedTable<String>();
		populate(streetgang_adjectives,STREETGANG_ADJECTIVES,",");
		streetgang_nouns = new WeightedTable<String>();
		populate(streetgang_nouns,STREETGANG_NOUNS,",");
		streetmusicians_adjectives = new WeightedTable<String>();
		populate(streetmusicians_adjectives,STREETMUSICIANS_ADJECTIVES,",");
		streetmusicians_nouns = new WeightedTable<String>();
		populate(streetmusicians_nouns,STREETMUSICIANS_NOUNS,",");
		theatertroupe_adjectives = new WeightedTable<String>();
		populate(theatertroupe_adjectives,THEATERTROUPE_ADJECTIVES,",");
		theatertroupe_nouns = new WeightedTable<String>();
		populate(theatertroupe_nouns,THEATERTROUPE_NOUNS,",");
		tradecompany_adjectives = new WeightedTable<String>();
		populate(tradecompany_adjectives,TRADECOMPANY_ADJECTIVES,",");
		tradecompany_nouns = new WeightedTable<String>();
		populate(tradecompany_nouns,TRADECOMPANY_NOUNS,",");
		populateFaction();
		populateFaiths();
	}
	private static void populateFaiths() {
	}
	private static void populateFaction() {
		factionNameGenerators = new LinkedHashMap<FactionType, FactionTypeNameGenerator>();
		factionNameGenerators.put(FactionType.DARK_CULT,new DarkCultNameGen());
		factionNameGenerators.put(FactionType.HERETICAL_SECT,new HereticalSectNameGen());
		factionNameGenerators.put(FactionType.NATIONAL_CHURCH,new NationalChurchNameGen());
		factionNameGenerators.put(FactionType.RELIGIOUS_ORDER,new ReligiousOrderNameGen());
		factionNameGenerators.put(FactionType.RELIGIOUS_SECT,new ReligiousSectNameGen());
		factionNameGenerators.put(FactionType.DRUID_CIRCLE,new ReligiousSectNameGen());
		factionNameGenerators.put(FactionType.MONASTIC_ORDER,new ReligiousSectNameGen());
		
		factionNameGenerators.put(FactionType.ART_MOVEMENT,new ArtMovementNameGen());
		factionNameGenerators.put(FactionType.BEGGAR_GUILD,new BeggarGuildNameGen());
		factionNameGenerators.put(FactionType.BLACK_MARKET,new BlackMarketNameGen());
		factionNameGenerators.put(FactionType.BROTHERHOOD,new BrotherhoodNameGen());
		factionNameGenerators.put(FactionType.CITY_GUARD,new CityGuardNameGen());
		factionNameGenerators.put(FactionType.CONSPIRACY,new ConspiracyNameGen());
		factionNameGenerators.put(FactionType.CRAFT_GUILD,new CraftGuildNameGen());
		factionNameGenerators.put(FactionType.CRIME_FAMILY,new CrimeFamilyNameGen());
		factionNameGenerators.put(FactionType.CRIME_RING,new CrimeRingNameGen());
		factionNameGenerators.put(FactionType.EXPLORER_CLUB,new ExplorerClubNameGen());
		factionNameGenerators.put(FactionType.FREE_COMPANY,new FreeCompanyNameGen());
		factionNameGenerators.put(FactionType.GOURMAND_CLUB,new GourmandClubNameGen());
		factionNameGenerators.put(FactionType.HEIST_CREW,new HeistCrewNameGen());
		factionNameGenerators.put(FactionType.HIGH_COUNCIL,new HighCouncilNameGen());
		factionNameGenerators.put(FactionType.HIRED_KILLERS,new HiredKillersNameGen());
		factionNameGenerators.put(FactionType.KNIGHTLY_ORDER,new RoyalArmyNameGen());
		factionNameGenerators.put(FactionType.LOCAL_MILITIA,new LocalMilitiaNameGen());
		factionNameGenerators.put(FactionType.NOBLE_HOUSE,new NobleHouseNameGen());
		factionNameGenerators.put(FactionType.OUTLANDER_CLAN,new OutlanderClanNameGen());
		factionNameGenerators.put(FactionType.OUTLAW_GANG,new OutlawGangNameGen());
		factionNameGenerators.put(FactionType.POLITICAL_PARTY,new PoliticalPartyNameGen());
		factionNameGenerators.put(FactionType.RESISTANCE,new ResistanceNameGen());
		factionNameGenerators.put(FactionType.ROYAL_ARMY,new RoyalArmyNameGen());
		factionNameGenerators.put(FactionType.ROYAL_HOUSE,new RoyalHouseNameGen());
		factionNameGenerators.put(FactionType.SCHOLAR_CIRCLE,new ScholarCircleNameGen());
		factionNameGenerators.put(FactionType.SECRET_SOCIETY,new SecretSocietyNameGen());
		factionNameGenerators.put(FactionType.SPY_NETWORK,new SpyNetworkNameGen());
		factionNameGenerators.put(FactionType.STREET_ARTISTS,new StreetArtistsNameGen());
		factionNameGenerators.put(FactionType.STREET_GANG,new StreetGangNameGen());
		factionNameGenerators.put(FactionType.BARD_COLLEGE,new StreetMusiciansNameGen());
		factionNameGenerators.put(FactionType.THEATER_TROUPE,new TheaterTroupeNameGen());
		factionNameGenerators.put(FactionType.TRADE_COMPANY,new TradeCompanyNameGen());
		factionNameGenerators.put(FactionType.WIZARD_CIRCLE,new ScholarCircleNameGen());
	}
	public static String getTrait(Indexible obj) {
		if(traits==null) populateAllTables();
		return Util.formatTableResult(traits.getByWeight(obj),obj);
	}
	public static String getGoal(Indexible obj) {
		if(goals==null) populateAllTables();
		return Util.formatTableResult(goals.getByWeight(obj),obj);
	}

	public static String getAdj(Indexible obj) {
		if(adjectives==null) populateAllTables();
		return adjectives.getByWeight(obj);
	}
	public static String getNoun(Indexible obj) {
		if(nouns==null) populateAllTables();
		return nouns.getByWeight(obj);
	}

	@Override
	public String getName(Indexible obj) {
		if(adjectives==null) populateAllTables();
		String result = "The "+adjectives.getByWeight(obj)+" "+nouns.getByWeight(obj);
		return result;
	}

	public static String getName(FactionType type,Indexible obj) {
		if(factionNameGenerators==null) populateAllTables();
		IndexibleNameGenerator gen = getNameGenerator(type);
		String name = gen.getName(obj);
		name = Util.formatTableResult(name,obj);
		return name;
	}
	public static FactionTypeNameGenerator getNameGenerator(FactionType type) {
		return factionNameGenerators.get(type);
	}
	
	private static class ArtMovementNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return art_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return art_nouns.getByWeight(obj);
		}
	}private static class BeggarGuildNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return beggar_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return beggar_nouns.getByWeight(obj);
		}
	}private static class BlackMarketNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return blackmarket_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return blackmarket_nouns.getByWeight(obj);
		}
	}private static class BrotherhoodNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return brotherhood_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return brotherhood_nouns.getByWeight(obj);
		}
	}private static class CityGuardNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return guard_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return guard_nouns.getByWeight(obj);
		}
	}private static class ConspiracyNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return conspiracy_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return conspiracy_nouns.getByWeight(obj);
		}
	}private static class CraftGuildNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return craftguild_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return craftguild_nouns.getByWeight(obj);
		}
	}private static class CrimeFamilyNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return crimefamily_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return crimefamily_nouns.getByWeight(obj);
		}
	}private static class CrimeRingNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return crimering_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return crimering_nouns.getByWeight(obj);
		}
	}private static class DarkCultNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return darkcult_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return darkcult_nouns.getByWeight(obj);
		}
	}private static class ExplorerClubNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return explorerclub_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return explorerclub_nouns.getByWeight(obj);
		}
	}private static class FreeCompanyNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return freecompany_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return freecompany_nouns.getByWeight(obj);
		}
	}private static class GourmandClubNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return gourmandclub_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return gourmandclub_nouns.getByWeight(obj);
		}
	}private static class HeistCrewNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return heistcrew_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return heistcrew_nouns.getByWeight(obj);
		}
	}private static class HereticalSectNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return hereticalsect_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return hereticalsect_nouns.getByWeight(obj);
		}
	}private static class HighCouncilNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return highcouncil_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return highcouncil_nouns.getByWeight(obj);
		}
	}private static class HiredKillersNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return hiredkillers_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return hiredkillers_nouns.getByWeight(obj);
		}
	}private static class LocalMilitiaNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return localmilitia_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return localmilitia_nouns.getByWeight(obj);
		}
	}private static class NationalChurchNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return nationalchurch_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return nationalchurch_nouns.getByWeight(obj);
		}
	}private static class NobleHouseNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return noblehouse_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return noblehouse_nouns.getByWeight(obj);
		}
	}private static class OutlanderClanNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return outlanderclan_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return outlanderclan_nouns.getByWeight(obj);
		}
	}private static class OutlawGangNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return outlawgang_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return outlawgang_nouns.getByWeight(obj);
		}
	}private static class PoliticalPartyNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return politicalparty_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return politicalparty_nouns.getByWeight(obj);
		}
	}private static class ReligiousOrderNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return religiousorder_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return religiousorder_nouns.getByWeight(obj);
		}
	}private static class ReligiousSectNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return religioussect_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return religioussect_nouns.getByWeight(obj);
		}
	}private static class ResistanceNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return resistance_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return resistance_nouns.getByWeight(obj);
		}
	}private static class RoyalArmyNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return royalarmy_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return royalarmy_nouns.getByWeight(obj);
		}
	}private static class RoyalHouseNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return royalhouse_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return royalhouse_nouns.getByWeight(obj);
		}
	}private static class ScholarCircleNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return scholarcircle_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return scholarcircle_nouns.getByWeight(obj);
		}
	}private static class SecretSocietyNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return secretsociety_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return secretsociety_nouns.getByWeight(obj);
		}
	}private static class SpyNetworkNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return spynetwork_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return spynetwork_nouns.getByWeight(obj);
		}
	}private static class StreetArtistsNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return streetartists_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return streetartists_nouns.getByWeight(obj);
		}
	}private static class StreetGangNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return streetgang_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return streetgang_nouns.getByWeight(obj);
		}
	}private static class StreetMusiciansNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return streetmusicians_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return streetmusicians_nouns.getByWeight(obj);
		}
	}private static class TheaterTroupeNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return theatertroupe_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return theatertroupe_nouns.getByWeight(obj);
		}
	}private static class TradeCompanyNameGen extends FactionTypeNameGenerator{
		@Override
		public String getAdj(Indexible obj) {
			return tradecompany_adjectives.getByWeight(obj);
		}
		@Override
		public String getNoun(Indexible obj) {
			return tradecompany_nouns.getByWeight(obj);
		}
	}


}
