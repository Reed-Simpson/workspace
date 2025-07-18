package data.location;

import data.Indexible;
import data.npc.NPCJobType;
import util.Util;

public enum LocationType {
	Bog,Boulder,Butte,Cave,Cliff,Crag,Crater,Creek,Crossing,Ditch,Field,Forest,Grove,Hill,Hollow,Hotspring,Lair,Lake,Lakebed,Marsh,Mesa,Moor,Pass,Peak,Pit,Pond,
	Rapids,Ravine,Ridge,Rise,River,Rockslide,Spring,Swamp,Thicket,Valley,Fall,
	Altar,Aqueduct,Bandit_Camp,Battlefield,Bonfire,Bridge,Cairn,Crossroads,Crypt,Dam,Dungeon,Farm,Ford,Fortress,Gallows,Graveyard,Hedge,Hunter_Camp,Inn,Lumber_Camp,
	Mine,Monastery,Monument,Orchard,Outpost,Pasture,Ruin,Seclusion,Shack,Shrine,Standing_Stone,Temple,Village,Wall,Watchtower,Waystone,
	
	Academy,Alchemist,Archive,Art_Dealer,Barber,Bookbinder,Bookseller,Castle,Clockmaker,Clothier,Courthouse,Furrier,Gallery,Garden,Haberdashery,Jeweler,Law_Office,Locksmith,Lounge,Manor,
	Museum,Observatory,Opera_House,Park,Physician,Printer,Public_Baths,Restaurant,Salon,Stables,Taxidermist,Tobacconist,Townhouse,Winery,Zoo,
	Apothacary,Asylum,Bakery,Brewery,Butcher,Candlemaker,Catacombs,Cheesemaker,Criminal_Den,Curiosity_Shop,Dock,Fighting_Pit,Forge,Fortuneteller,Gambling_Hall,Leatherworks,Marketplace,Mason,Mill,
	Moneylender,Orphanage,Outfitter,Prison,Sewers,Shipyards,Stockyard,Stonecarver,Tattooist,Tavern,Theater,Veterinarian,Warehouse,Weaver,Workshop,
	
	Slum,Auction_House;

	public static final LocationType[] landmarks = new LocationType[] {Bog,Boulder,Butte,Cave,Cliff,Crag,Crater,Creek,Crossing,Ditch,Field,Forest,Grove,Hill,Hollow,Hotspring,Lair,Lake,Lakebed,
			Marsh,Mesa,Moor,Pass,Peak,Pit,Pond,Rapids,Ravine,Ridge,Rise,River,Rockslide,Spring,Swamp,Thicket,Valley,Fall};
	public static final LocationType[] structures = new LocationType[] {Altar,Aqueduct,Bandit_Camp,Battlefield,Bonfire,Bridge,Cairn,Crossroads,Crypt,Dam,Dungeon,Farm,Ford,Fortress,Gallows,
			Graveyard,Hedge,Hunter_Camp,Inn,Lumber_Camp,Mine,Monastery,Monument,Orchard,Outpost,Pasture,Ruin,Seclusion,Shack,Shrine,Standing_Stone,Temple,Village,Wall,Watchtower,Waystone};
	public static final LocationType[] ucBuildings = new LocationType[] {Academy,Alchemist,Archive,Art_Dealer,Barber,Bookbinder,Bookseller,Castle,Clockmaker,Clothier,Courthouse,Furrier,Gallery,
			Garden,Haberdashery,Jeweler,Law_Office,Locksmith,Lounge,Manor,Museum,Observatory,Opera_House,Park,Physician,Printer,Public_Baths,Restaurant,Salon,Stables,Taxidermist,Temple,
			Tobacconist,Townhouse,Winery,Zoo,Auction_House};
	public static final LocationType[] lcBuildings = new LocationType[] {Apothacary,Asylum,Bakery,Brewery,Butcher,Candlemaker,Catacombs,Cheesemaker,Criminal_Den,Curiosity_Shop,Dock,Fighting_Pit,
			Forge,Fortuneteller,Gambling_Hall,Leatherworks,Marketplace,Mason,Mill,Moneylender,Orphanage,Outfitter,Prison,Sewers,Shipyards,Shrine,Stockyard,Stonecarver,Tattooist,Tavern,
			Theater,Veterinarian,Warehouse,Watchtower,Weaver,Workshop,Slum};
	
	public static final LocationType[] Catacombs_locations = new LocationType[] {Catacombs};
	public static final LocationType[] Construction_locations = new LocationType[] {Mason,Shipyards,Stockyard,Workshop};
	public static final LocationType[] Crafts_locations = new LocationType[] {Alchemist,Bookbinder,Clockmaker,Jeweler,Candlemaker,Forge,Leatherworks,Mason,Shipyards,Weaver};
	public static final LocationType[] Criminality_locations = new LocationType[] {Courthouse,Law_Office,Tobacconist,Criminal_Den,Fighting_Pit,Gambling_Hall,Prison};
	public static final LocationType[] Culture_locations = new LocationType[] {Archive,Art_Dealer,Bookseller,Clothier,Furrier,Gallery,Haberdashery,Museum,Opera_House,Salon,Tattooist,Theater};
	public static final LocationType[] Dining_locations = new LocationType[] {Lounge,Restaurant,Winery,Bakery,Brewery,Cheesemaker,Tavern};
	public static final LocationType[] Education_locations = new LocationType[] {Academy,Archive,Bookseller,Museum,Observatory};
	public static final LocationType[] Entertainment_locations = new LocationType[] {Gallery,Lounge,Opera_House,Zoo,Fortuneteller,Gambling_Hall,Theater};
	public static final LocationType[] Finance_locations = new LocationType[] {Marketplace,Moneylender,Auction_House};
	public static final LocationType[] Foreigners_locations = new LocationType[] {Academy,Alchemist,Archive,Art_Dealer,Barber,Bookbinder,Bookseller,Castle,Clockmaker,Clothier,Courthouse,Furrier,Gallery,
			Garden,Haberdashery,Jeweler,Law_Office,Locksmith,Lounge,Manor,Museum,Observatory,Opera_House,Park,Physician,Printer,Public_Baths,Restaurant,Salon,Stables,Taxidermist,Temple,
			Tobacconist,Townhouse,Winery,Zoo,Apothacary,Asylum,Bakery,Brewery,Butcher,Candlemaker,Catacombs,Cheesemaker,Criminal_Den,Curiosity_Shop,Dock,Fighting_Pit,
			Forge,Fortuneteller,Gambling_Hall,Leatherworks,Marketplace,Mason,Mill,Moneylender,Orphanage,Outfitter,Prison,Sewers,Shipyards,Shrine,Stockyard,Stonecarver,Tattooist,Tavern,
			Theater,Veterinarian,Warehouse,Watchtower,Weaver,Workshop,Slum,Auction_House};
	public static final LocationType[] Ghettoes_locations = new LocationType[] {Criminal_Den,Dock,Moneylender,Orphanage,Prison,Sewers,Shrine,Stockyard,Tattooist,Tavern,
			Warehouse,Watchtower,Workshop,Slum};
	public static final LocationType[] Government_locations = new LocationType[] {Castle,Courthouse,Law_Office,Manor};
	public static final LocationType[] Graveyards_locations = new LocationType[] {Temple,Catacombs,Shrine};
	public static final LocationType[] Green_Space_locations = new LocationType[] {Garden,Park,Manor,Zoo,Criminal_Den};
	public static final LocationType[] Industrialization_locations = new LocationType[] {Forge,Shipyards,Workshop};
	public static final LocationType[] Judgement_locations = new LocationType[] {Courthouse,Law_Office,Asylum,Prison};
	public static final LocationType[] Livestock_locations = new LocationType[] {Furrier,Stables,Zoo,Butcher,Leatherworks,Veterinarian};
	public static final LocationType[] Marketplace_locations = new LocationType[] {Alchemist,Art_Dealer,Bookseller,Clockmaker,Clothier,Furrier,Haberdashery,Jeweler,Tobacconist,Winery,Apothacary,
			Bakery,Brewery,Butcher,Candlemaker,Cheesemaker,Forge,Fortuneteller,Leatherworks,Marketplace,Moneylender,Outfitter,Tavern,Warehouse,Auction_House};
	public static final LocationType[] Memorials_locations = new LocationType[] {Museum,Park,Shrine};
	public static final LocationType[] Military_locations = new LocationType[] {Academy,Fighting_Pit,Forge,Watchtower};
	public static final LocationType[] Opulence_locations = new LocationType[] {Art_Dealer,Castle,Clothier,Furrier,Garden,Haberdashery,Jeweler,Manor,Opera_House,Public_Baths,Restaurant,Salon,
			Tobacconist,Winery,Zoo,Gambling_Hall,Auction_House};
	public static final LocationType[] Pollution_locations = new LocationType[] {Alchemist,Stables,Forge,Sewers,Slum};
	public static final LocationType[] Poverty_locations = new LocationType[] {Criminal_Den,Moneylender,Orphanage,Prison,Slum};
	public static final LocationType[] Punishment_locations = new LocationType[] {Courthouse,Asylum,Prison};
	public static final LocationType[] Religion_locations = new LocationType[] {Temple};
	public static final LocationType[] Science_locations = new LocationType[] {Academy,Alchemist,Archive,Observatory,Workshop};
	public static final LocationType[] Trade_locations = new LocationType[] {Alchemist,Barber,Bookbinder,Clockmaker,Clothier,Jeweler,Locksmith,Printer,Stables,Taxidermist,
			Apothacary,Bakery,Brewery,Butcher,Candlemaker,Cheesemaker,Criminal_Den,Dock,Forge,Leatherworks,Mason,Mill,Shipyards,Stockyard,Stonecarver,Warehouse,Weaver,Workshop};
	public static final LocationType[] Trash_locations = new LocationType[] {Criminal_Den,Sewers,Orphanage,Prison,Slum};
	public static final LocationType[] Vices_locations = new LocationType[] {Tobacconist,Gambling_Hall,Lounge,Restaurant,Brewery};
	public static final LocationType[] Wizardry_locations = new LocationType[] {Academy,Archive,Observatory,Workshop,Fortuneteller,Curiosity_Shop};
	public static final LocationType[] Wonders_locations = new LocationType[] {Castle,Clockmaker,Gallery,Garden,Museum,Observatory,Opera_House,Park,Restaurant,Temple,Zoo,
			Catacombs,Curiosity_Shop,Fortuneteller,Gambling_Hall,Marketplace,Theater,Auction_House};
	
	//Job locations
	public static final LocationType[] Acolyte_locations = new LocationType[] {Crypt,Graveyard,Shrine,Temple,Catacombs};
	public static final LocationType[] Actor_locations = new LocationType[] {Opera_House,Theater};
	public static final LocationType[] Apothacary_locations = new LocationType[] {Garden,Physician,Apothacary};
	public static final LocationType[] Baker_locations = new LocationType[] {Bakery};
	public static final LocationType[] Barber_locations = new LocationType[] {Barber,Physician,Salon};
	public static final LocationType[] Blacksmith_locations = new LocationType[] {Forge};
	public static final LocationType[] Brewer_locations = new LocationType[] {Winery,Brewery};
	public static final LocationType[] Bureaucrat_locations = new LocationType[] {Courthouse};
	public static final LocationType[] Butcher_locations = new LocationType[] {Butcher,Leatherworks};
	public static final LocationType[] Carpenter_locations = new LocationType[] {Workshop};
	public static final LocationType[] Clockmaker_locations = new LocationType[] {Clockmaker};
	public static final LocationType[] Courier_locations = new LocationType[] {null};
	public static final LocationType[] Courtier_locations = new LocationType[] {Castle,Manor};
	public static final LocationType[] Diplomat_locations = new LocationType[] {Castle,Courthouse,Law_Office,Manor};
	public static final LocationType[] Fishmonger_locations = new LocationType[] {Restaurant};
	public static final LocationType[] Guard_locations = new LocationType[] {Wall,Watchtower,Castle};
	public static final LocationType[] Haberdasher_locations = new LocationType[] {Clothier,Haberdashery};
	public static final LocationType[] Innkeeper_locations = new LocationType[] {Inn,Lounge,Restaurant,Tavern};
	public static final LocationType[] Item_Seller_locations = new LocationType[] {Bookseller,Clothier,Gallery,Manor,Tobacconist,Curiosity_Shop,Marketplace,Stockyard,Warehouse,Auction_House};
	public static final LocationType[] Jeweler_locations = new LocationType[] {Jeweler};
	public static final LocationType[] Knight_locations = new LocationType[] {Wall,Watchtower,Castle};
	public static final LocationType[] Locksmith_locations = new LocationType[] {Locksmith};
	public static final LocationType[] Mason_locations = new LocationType[] {Mason,Stonecarver};
	public static final LocationType[] Miller_locations = new LocationType[] {Mill};
	public static final LocationType[] Musician_locations = new LocationType[] {Opera_House,Theater};
	public static final LocationType[] Noble_locations = new LocationType[] {Academy,Archive,Castle,Courthouse,Manor,Park};
	public static final LocationType[] Painter_locations = new LocationType[] {Art_Dealer,Gallery};
	public static final LocationType[] Priest_locations = new LocationType[] {Graveyard,Shrine,Temple,Village,Physician,Asylum,Catacombs,Orphanage};
	public static final LocationType[] Scholar_locations = new LocationType[] {Academy,Archive,Bookbinder,Bookseller,Museum,Observatory,Physician};
	public static final LocationType[] Scribe_locations = new LocationType[] {Academy,Archive,Bookbinder,Bookseller,Printer};
	public static final LocationType[] Sculptor_locations = new LocationType[] {Art_Dealer,Gallery};
	public static final LocationType[] Shipwright_locations = new LocationType[] {Dock,Shipyards};
	public static final LocationType[] Soldier_locations = new LocationType[] {Castle,Wall,Watchtower};
	public static final LocationType[] Tailor_locations = new LocationType[] {Clothier,Haberdashery};
	public static final LocationType[] Taxidermist_locations = new LocationType[] {Taxidermist};
	public static final LocationType[] Wigmaker_locations = new LocationType[] {Salon};
	public static final LocationType[] Artificer_locations = new LocationType[] {Curiosity_Shop,Workshop};
	public static final LocationType[] Bard_locations = new LocationType[] {Opera_House,Theater};
	public static final LocationType[] Cleric_locations = new LocationType[] {Graveyard,Temple,Catacombs};
	public static final LocationType[] Monk_locations = new LocationType[] {Monastery,Seclusion,Garden};
	public static final LocationType[] Paladin_locations = new LocationType[] {Graveyard,Temple,Catacombs};
	public static final LocationType[] Wizard_locations = new LocationType[] {Seclusion,Shack,Academy,Archive,Observatory,Apothacary,Veterinarian};
	public static final LocationType[] Alchemist_locations = new LocationType[] {Alchemist};
	public static final LocationType[] Animal_Breeder_locations = new LocationType[] {Stables,Zoo,Veterinarian};
	public static final LocationType[] Assassin_locations = new LocationType[] {Criminal_Den};
	public static final LocationType[] Acrobat_locations = new LocationType[] {Opera_House,Theater};
	public static final LocationType[] Beggar_locations = new LocationType[] {Sewers,Slum};
	public static final LocationType[] Burglar_locations = new LocationType[] {Locksmith,Criminal_Den};
	public static final LocationType[] Chimneysweep_locations = new LocationType[] {Slum};
	public static final LocationType[] Charlatan_locations = new LocationType[] {Criminal_Den,Fortuneteller,Slum};
	public static final LocationType[] Cultist_locations = new LocationType[] {Graveyard,Shrine,Temple,Catacombs,Criminal_Den};
	public static final LocationType[] Cutpurse_locations = new LocationType[] {Criminal_Den};
	public static final LocationType[] Deserter_locations = new LocationType[] {Criminal_Den};
	public static final LocationType[] Ditchdigger_locations = new LocationType[] {Mine,Slum};
	public static final LocationType[] Fence_locations = new LocationType[] {Criminal_Den,Marketplace,Auction_House};
	public static final LocationType[] Forger_locations = new LocationType[] {Criminal_Den};
	public static final LocationType[] Fortuneseller_locations = new LocationType[] {Curiosity_Shop,Fortuneteller};
	public static final LocationType[] Gambler_locations = new LocationType[] {Criminal_Den,Fighting_Pit,Gambling_Hall};
	public static final LocationType[] Gladiator_locations = new LocationType[] {Fighting_Pit};
	public static final LocationType[] Gravedigger_locations = new LocationType[] {Graveyard,Catacombs};
	public static final LocationType[] Headsman_locations = new LocationType[] {Gallows};
	public static final LocationType[] Informant_locations = new LocationType[] {Criminal_Den,Prison};
	public static final LocationType[] Jailer_locations = new LocationType[] {Dungeon,Castle,Courthouse,Asylum,Prison};
	public static final LocationType[] Laborer_locations = new LocationType[] {Mine,Dock,Shipyards,Stockyard,Warehouse};
	public static final LocationType[] Lamplighter_locations = new LocationType[] {Sewers,Slum};
	public static final LocationType[] Mercenary_locations = new LocationType[] {Criminal_Den,Tavern,Watchtower};
	public static final LocationType[] Poet_locations = new LocationType[] {Opera_House,Theater};
	public static final LocationType[] Poisoner_locations = new LocationType[] {Criminal_Den};
	public static final LocationType[] Privateer_locations = new LocationType[] {Criminal_Den,Dock,Shipyards};
	public static final LocationType[] Ratcatcher_locations = new LocationType[] {Zoo,Veterinarian,Butcher};
	public static final LocationType[] Sailor_locations = new LocationType[] {Dock,Shipyards};
	public static final LocationType[] Servant_locations = new LocationType[] {Stables,Orphanage,Sewers,Slum};
	public static final LocationType[] Smuggler_locations = new LocationType[] {Lounge,Tobacconist,Criminal_Den,Curiosity_Shop,Dock,Marketplace,Shipyards,Stockyard,Warehouse,Auction_House};
	public static final LocationType[] Spy_locations = new LocationType[] {Criminal_Den};
	public static final LocationType[] Urchin_locations = new LocationType[] {Slum};
	public static final LocationType[] Loan_Shark_locations = new LocationType[] {Gambling_Hall,Moneylender,Slum};
	public static final LocationType[] Vagabond_locations = new LocationType[] {Slum};
	public static final LocationType[] Gutter_Mage_locations = new LocationType[] {Criminal_Den};
	public static final LocationType[] Rogue_locations = new LocationType[] {Locksmith,Criminal_Den,Gambling_Hall};
	public static final LocationType[] Sorcerer_locations = new LocationType[] {null};
	public static final LocationType[] Warlock_locations = new LocationType[] {Observatory,Criminal_Den};
	public static final LocationType[] Apiarist_locations = new LocationType[] {Farm,Hedge,Candlemaker};
	public static final LocationType[] Bandit_locations = new LocationType[] {Bandit_Camp,Bridge,Sewers};
	public static final LocationType[] Caravan_Guard_locations = new LocationType[] {Marketplace,Outfitter,Watchtower};
	public static final LocationType[] Caravaneer_locations = new LocationType[] {Marketplace,Outfitter,Auction_House};
	public static final LocationType[] Druid_locations = new LocationType[] {Garden,Park,Zoo,Apothacary,Veterinarian};
	public static final LocationType[] Exile_locations = new LocationType[] {Seclusion,Shack};
	public static final LocationType[] Explorer_locations = new LocationType[] {Museum,Outfitter};
	public static final LocationType[] Farmer_locations = new LocationType[] {Farm,Hedge,Orchard,Garden,Marketplace};
	public static final LocationType[] Fisher_locations = new LocationType[] {Ford,Hunter_Camp,Outfitter};
	public static final LocationType[] Forager_locations = new LocationType[] {Hunter_Camp,Alchemist,Outfitter};
	public static final LocationType[] Fugative_locations = new LocationType[] {Seclusion,Shack,Sewers};
	public static final LocationType[] Hedge_Wizard_locations = new LocationType[] {Seclusion,Shack,Apothacary,Veterinarian};
	public static final LocationType[] Hermit_locations = new LocationType[] {Hedge,Seclusion,Shack,Park};
	public static final LocationType[] Hunter_locations = new LocationType[] {Hunter_Camp,Furrier,Leatherworks,Outfitter};
	public static final LocationType[] Messenger_locations = new LocationType[] {null};
	public static final LocationType[] Minstrel_locations = new LocationType[] {Opera_House,Theater};
	public static final LocationType[] Monster_Hunter_locations = new LocationType[] {Outfitter};
	public static final LocationType[] Outlander_locations = new LocationType[] {Outpost,Park,Zoo,Leatherworks,Tattooist};
	public static final LocationType[] Tinker_locations = new LocationType[] {Marketplace,Workshop};
	public static final LocationType[] Pilgrim_locations = new LocationType[] {Shrine,Temple};
	public static final LocationType[] Poacher_locations = new LocationType[] {Hunter_Camp,Furrier,Leatherworks};
	public static final LocationType[] Raider_locations = new LocationType[] {Museum,Outfitter};
	public static final LocationType[] Ranger_locations = new LocationType[] {Hunter_Camp,Seclusion,Shack,Furrier,Park,Stables,Zoo,Veterinarian};
	public static final LocationType[] Sage_locations = new LocationType[] {Seclusion,Apothacary};
	public static final LocationType[] Scavenger_locations = new LocationType[] {Hunter_Camp};
	public static final LocationType[] Scout_locations = new LocationType[] {Hunter_Camp,Watchtower};
	public static final LocationType[] Shepherd_locations = new LocationType[] {Hedge,Pasture,Furrier,Cheesemaker,Leatherworks};
	public static final LocationType[] Seer_locations = new LocationType[] {Seclusion,Observatory,Curiosity_Shop,Fortuneteller};
	public static final LocationType[] Surveyor_locations = new LocationType[] {Hunter_Camp,Outfitter};
	public static final LocationType[] Tomb_Raider_locations = new LocationType[] {Museum,Outfitter};
	public static final LocationType[] Trader_locations = new LocationType[] {Outpost,Moneylender};
	public static final LocationType[] Trapper_locations = new LocationType[] {Hunter_Camp,Furrier,Leatherworks};
	public static final LocationType[] Witch_locations = new LocationType[] {Hedge,Seclusion,Shack,Apothacary,Veterinarian};
	public static final LocationType[] Woodcutter_locations = new LocationType[] {Lumber_Camp,Workshop};
	public static final LocationType[] Barbarian_locations = new LocationType[] {Outpost,Park,Zoo,Leatherworks,Tattooist,Fighting_Pit};
	public static final LocationType[] Lawyer_locations = new LocationType[] {Law_Office};
	public static final LocationType[] Ruffian_locations = new LocationType[] {Manor,Criminal_Den,Gambling_Hall,Tattooist,Slum};
	public static final LocationType[] Chef_locations = new LocationType[] {Restaurant};
	public static final LocationType[] Artisan_locations = new LocationType[] {Candlemaker,Cheesemaker,Weaver};
	public static final LocationType[] Abjurer_locations = new LocationType[] {Academy,Archive,Workshop,Watchtower};
	public static final LocationType[] Swordmage_locations = new LocationType[] {Academy,Archive,Workshop,Castle};
	public static final LocationType[] Conjurer_locations = new LocationType[] {Academy,Archive,Observatory,Workshop,Zoo};
	public static final LocationType[] Diviner_locations = new LocationType[] {Academy,Archive,Observatory,Workshop,Fortuneteller};
	public static final LocationType[] Enchanter_locations = new LocationType[] {Academy,Archive,Workshop,Criminal_Den};
	public static final LocationType[] Evoker_locations = new LocationType[] {Academy,Archive,Workshop,Castle};
	public static final LocationType[] Illusionist_locations = new LocationType[] {Academy,Archive,Workshop,Criminal_Den,Theater};
	public static final LocationType[] Necromancer_locations = new LocationType[] {Crypt,Graveyard,Catacombs};
	public static final LocationType[] Scrivener_locations = new LocationType[] {Academy,Archive,Observatory,Workshop,Bookbinder,Bookseller};
	public static final LocationType[] Transmuter_locations = new LocationType[] {Academy,Archive,Observatory,Workshop,Jeweler};
	public static final LocationType[] Warmage_locations = new LocationType[] {Academy,Archive,Observatory,Workshop,Castle,Watchtower};
	
	public static LocationType getBuilding(Indexible obj) {
		if(obj.reduceTempId(2)==0) return getUCBuilding(obj);
		else return getLCBuilding(obj);
	}
	public static LocationType getUCBuilding(Indexible obj) {
		return (LocationType) Util.getElementFromArray(ucBuildings, obj);
	}
	public static LocationType getLCBuilding(Indexible obj) {
		return (LocationType) Util.getElementFromArray(lcBuildings, obj);
	}

	public static LocationType getLandmark(Indexible obj) {
		return (LocationType) Util.getElementFromArray(landmarks, obj);
	}
	public static LocationType getStructure(Indexible obj) {
		return (LocationType) Util.getElementFromArray(structures, obj);
	}
	public static LocationType getStructureOrLandmark(Indexible obj) {
		int i = obj.reduceTempId(2);
		if(i==0) return getStructure(obj);
		else return getLandmark(obj);
	}
	public NPCJobType getRandomJobType(Indexible obj) {
		return (NPCJobType) Util.getElementFromArray(getJobTypes(), obj);
	}
	public NPCJobType[] getJobTypes() {
		switch(this) {
		case Bandit_Camp: return NPCJobType.Bandit_Camp_jobs;
		case Bonfire: return NPCJobType.Bonfire_jobs;
		case Bridge: return NPCJobType.Bridge_jobs;
		case Crossroads: return NPCJobType.Crossroads_jobs;
		case Crypt: return NPCJobType.Crypt_jobs;
		case Dungeon: return NPCJobType.Dungeon_jobs;
		case Farm: return NPCJobType.Farm_jobs;
		case Ford: return NPCJobType.Ford_jobs;
		case Fortress: return NPCJobType.Fortress_jobs;
		case Gallows: return NPCJobType.Gallows_jobs;
		case Graveyard: return NPCJobType.Graveyard_jobs;
		case Hedge: return NPCJobType.Hedge_jobs;
		case Hunter_Camp: return NPCJobType.Hunter_Camp_jobs;
		case Inn: return NPCJobType.Inn_jobs;
		case Lumber_Camp: return NPCJobType.Lumber_Camp_jobs;
		case Mine: return NPCJobType.Mine_jobs;
		case Monastery: return NPCJobType.Monastery_jobs;
		case Orchard: return NPCJobType.Orchard_jobs;
		case Outpost: return NPCJobType.Outpost_jobs;
		case Pasture: return NPCJobType.Pasture_jobs;
		case Seclusion: return NPCJobType.Seclusion_jobs;
		case Shack: return NPCJobType.Shack_jobs;
		case Shrine: return NPCJobType.Shrine_jobs;
		case Temple: return NPCJobType.Temple_jobs;
		case Village: return NPCJobType.Village_jobs;
		case Wall: return NPCJobType.Wall_jobs;
		case Watchtower: return NPCJobType.Watchtower_jobs;
		case Academy: return NPCJobType.Academy_jobs;
		case Alchemist: return NPCJobType.Alchemist_jobs;
		case Archive: return NPCJobType.Archive_jobs;
		case Art_Dealer: return NPCJobType.Art_Dealer_jobs;
		case Barber: return NPCJobType.Barber_jobs;
		case Bookbinder: return NPCJobType.Bookbinder_jobs;
		case Bookseller: return NPCJobType.Bookseller_jobs;
		case Castle: return NPCJobType.Castle_jobs;
		case Clockmaker: return NPCJobType.Clockmaker_jobs;
		case Clothier: return NPCJobType.Clothier_jobs;
		case Courthouse: return NPCJobType.Courthouse_jobs;
		case Furrier: return NPCJobType.Furrier_jobs;
		case Gallery: return NPCJobType.Gallery_jobs;
		case Garden: return NPCJobType.Garden_jobs;
		case Haberdashery: return NPCJobType.Haberdashery_jobs;
		case Jeweler: return NPCJobType.Jeweler_jobs;
		case Law_Office: return NPCJobType.Law_Office_jobs;
		case Locksmith: return NPCJobType.Locksmith_jobs;
		case Lounge: return NPCJobType.Lounge_jobs;
		case Manor: return NPCJobType.Manor_jobs;
		case Museum: return NPCJobType.Museum_jobs;
		case Observatory: return NPCJobType.Observatory_jobs;
		case Opera_House: return NPCJobType.Opera_House_jobs;
		case Park: return NPCJobType.Park_jobs;
		case Physician: return NPCJobType.Physician_jobs;
		case Printer: return NPCJobType.Printer_jobs;
		case Restaurant: return NPCJobType.Restaurant_jobs;
		case Salon: return NPCJobType.Salon_jobs;
		case Stables: return NPCJobType.Stables_jobs;
		case Taxidermist: return NPCJobType.Taxidermist_jobs;
		case Tobacconist: return NPCJobType.Tobacconist_jobs;
		case Townhouse: return NPCJobType.Townhouse_jobs;
		case Winery: return NPCJobType.Winery_jobs;
		case Zoo: return NPCJobType.Zoo_jobs;
		case Apothacary: return NPCJobType.Apothacary_jobs;
		case Asylum: return NPCJobType.Asylum_jobs;
		case Bakery: return NPCJobType.Baker_jobs;
		case Brewery: return NPCJobType.Brewery_jobs;
		case Butcher: return NPCJobType.Butcher_jobs;
		case Candlemaker: return NPCJobType.Candlemaker_jobs;
		case Catacombs: return NPCJobType.Catacombs_jobs;
		case Cheesemaker: return NPCJobType.Cheesemaker_jobs;
		case Criminal_Den: return NPCJobType.Criminal_Den_jobs;
		case Curiosity_Shop: return NPCJobType.Curiosity_Shop_jobs;
		case Dock: return NPCJobType.Dock_jobs;
		case Fighting_Pit: return NPCJobType.Fighting_Pit_jobs;
		case Forge: return NPCJobType.Forge_jobs;
		case Fortuneteller: return NPCJobType.Fortuneteller_jobs;
		case Gambling_Hall: return NPCJobType.Gambling_Hall_jobs;
		case Leatherworks: return NPCJobType.Leatherworks_jobs;
		case Marketplace: return NPCJobType.Marketplace_jobs;
		case Mason: return NPCJobType.Mason_jobs;
		case Mill: return NPCJobType.Mill_jobs;
		case Moneylender: return NPCJobType.Moneylender_jobs;
		case Orphanage: return NPCJobType.Orphanage_jobs;
		case Outfitter: return NPCJobType.Outfitter_jobs;
		case Prison: return NPCJobType.Prison_jobs;
		case Sewers: return NPCJobType.Sewers_jobs;
		case Shipyards: return NPCJobType.Shipyards_jobs;
		case Stockyard: return NPCJobType.Stockyard_jobs;
		case Stonecarver: return NPCJobType.Stonecarver_jobs;
		case Tattooist: return NPCJobType.Tattooist_jobs;
		case Tavern: return NPCJobType.Tavern_jobs;
		case Theater: return NPCJobType.Theater_jobs;
		case Veterinarian: return NPCJobType.Veterinarian_jobs;
		case Warehouse: return NPCJobType.Warehouse_jobs;
		case Weaver: return NPCJobType.Weaver_jobs;
		case Workshop: return NPCJobType.Workshop_jobs;
		case Slum: return NPCJobType.Slum_jobs;
		case Auction_House: return NPCJobType.Auction_House_jobs;
		default: return new NPCJobType[] {null};
		}
	}
	
	public String toString() {
		return Util.replace(this.name(), "_", " ");
	}

}
