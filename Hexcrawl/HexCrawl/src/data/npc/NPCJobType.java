package data.npc;

import data.Indexible;
import data.location.LocationType;
import util.Util;

public enum NPCJobType {
	Acolyte,Actor,Apothacary,Baker,Barber,Blacksmith,Brewer,Bureaucrat,Butcher,Carpenter,Clockmaker,Courier,Courtier,Diplomat,Fishmonger,Guard,Haberdasher,Innkeeper,Item_Seller,Jeweler,Knight,
	Locksmith,Mason,Miller,Musician,Noble,Painter,Priest,Scholar,Scribe,Sculptor,Shipwright,Soldier,Tailor,Taxidermist,Wigmaker,Artificer,Bard,Cleric,Monk,Paladin,Wizard,
	
	Alchemist,Animal_Breeder,Assassin,Acrobat,Beggar,Burglar,Chimneysweep,Charlatan,Cultist,Cutpurse,Deserter,Ditchdigger,Fence,Forger,Fortuneseller,Gambler,Gladiator,Gravedigger,Headsman,
	Informant,Jailer,Laborer,Lamplighter,Mercenary,Poet,Poisoner,Privateer,Ratcatcher,Sailor,Servant,Smuggler,Spy,Urchin,Loan_Shark,Vagabond,Gutter_Mage,Rogue,Sorcerer,Warlock,
	
	Apiarist,Bandit,Caravan_Guard,Caravaneer,Druid,Exile,Explorer,Farmer,Fisher,Forager,Fugative,Hedge_Wizard,Hermit,Hunter,Messenger,Minstrel,Monster_Hunter,Outlander,
	Tinker,Pilgrim,Poacher,Raider,Ranger,Sage,Scavenger,Scout,Shepherd,Seer,Surveyor,Tomb_Raider,Trader,Trapper,Witch,Woodcutter,Barbarian,
	
	Lawyer,Ruffian,Chef,Artisan,Abjurer,Swordmage,Conjurer,Diviner,Enchanter,Evoker,Illusionist,Necromancer,Scrivener,Transmuter,Warmage;

	public static final NPCJobType[] civilized = new NPCJobType[] {Acolyte,Actor,Apothacary,Baker,Barber,Blacksmith,Brewer,Bureaucrat,Butcher,Carpenter,Clockmaker,Courier,Courtier,Diplomat,Fishmonger,
			Guard,Haberdasher,Innkeeper,Item_Seller,Jeweler,Knight,Locksmith,Mason,Miller,Musician,Noble,Painter,Priest,Scholar,Scribe,Sculptor,Shipwright,Soldier,Tailor,Taxidermist,Wigmaker,
			Artificer,Bard,Cleric,Monk,Paladin,Wizard,Lawyer,Chef};
	public static final NPCJobType[] underworld = new NPCJobType[] {Alchemist,Animal_Breeder,Assassin,Acrobat,Beggar,Burglar,Chimneysweep,Charlatan,Cultist,Cutpurse,Deserter,Ditchdigger,Fence,Forger,
			Fortuneseller,Gambler,Gladiator,Gravedigger,Headsman,Informant,Jailer,Laborer,Lamplighter,Mercenary,Poet,Poisoner,Privateer,Ratcatcher,Sailor,Servant,Smuggler,Spy,Urchin,Loan_Shark,
			Vagabond,Gutter_Mage,Rogue,Sorcerer,Warlock,Ruffian};
	public static final NPCJobType[] wilderness = new NPCJobType[] {Apiarist,Bandit,Caravan_Guard,Caravaneer,Druid,Exile,Explorer,Farmer,Fisher,Forager,Fugative,Hedge_Wizard,Hermit,Hunter,Messenger,
			Minstrel,Monk,Monster_Hunter,Outlander,Tinker,Pilgrim,Poacher,Raider,Ranger,Sage,Scavenger,Scout,Shepherd,Seer,Surveyor,Tomb_Raider,Trader,Trapper,Witch,Woodcutter,Barbarian};
	public static final NPCJobType[] wizard = new NPCJobType[] {Abjurer,Swordmage,Conjurer,Diviner,Enchanter,Evoker,Illusionist,Necromancer,Scrivener,Transmuter,Warmage};

	//Location Job Lists
	public static final NPCJobType[] Bandit_Camp_jobs = new NPCJobType[] {Bandit};
	public static final NPCJobType[] Bonfire_jobs = new NPCJobType[] {Bandit,Caravan_Guard,Caravaneer,Exile,Explorer,Fisher,Forager,Fugative,Hedge_Wizard,Hunter,Messenger,Minstrel,
			Monster_Hunter,Outlander,Tinker,Pilgrim,Poacher,Ranger,Sage,Scavenger,Scout,Seer,Surveyor,Tomb_Raider,Trader,Trapper,Witch,Woodcutter,Barbarian,null};
	public static final NPCJobType[] Bridge_jobs = new NPCJobType[] {Bandit,Bureaucrat,null};
	public static final NPCJobType[] Crossroads_jobs = Bonfire_jobs;
	public static final NPCJobType[] Crypt_jobs = new NPCJobType[] {Acolyte,Priest,Cleric,Paladin,Gravedigger,Necromancer,Cultist,null};
	public static final NPCJobType[] Dungeon_jobs = new NPCJobType[] {Jailer};
	public static final NPCJobType[] Farm_jobs = new NPCJobType[] {Farmer,Apiarist};
	public static final NPCJobType[] Ford_jobs = new NPCJobType[] {Fisher,null};
	public static final NPCJobType[] Fortress_jobs = new NPCJobType[] {Knight,Noble,Guard,Soldier,Jailer};
	public static final NPCJobType[] Gallows_jobs = new NPCJobType[] {Headsman};
	public static final NPCJobType[] Graveyard_jobs = new NPCJobType[] {Acolyte,Priest,Cleric,Paladin,Gravedigger,Necromancer,Cultist,null};
	public static final NPCJobType[] Hedge_jobs = new NPCJobType[] {Druid,Hermit,Farmer,Shepherd,Apiarist,Witch};
	public static final NPCJobType[] Hunter_Camp_jobs = new NPCJobType[] {Fisher,Forager,Hunter,Poacher,Ranger,Scavenger,Scout,Surveyor,Trapper};
	public static final NPCJobType[] Inn_jobs = new NPCJobType[] {Innkeeper};
	public static final NPCJobType[] Lumber_Camp_jobs = new NPCJobType[] {Woodcutter};
	public static final NPCJobType[] Mine_jobs = new NPCJobType[] {Laborer};
	public static final NPCJobType[] Monastery_jobs = new NPCJobType[] {Monk};
	public static final NPCJobType[] Orchard_jobs = new NPCJobType[] {Farmer};
	public static final NPCJobType[] Outpost_jobs = new NPCJobType[] {Outlander,Trader,Soldier};
	public static final NPCJobType[] Pasture_jobs = new NPCJobType[] {Shepherd};
	public static final NPCJobType[] Seclusion_jobs = new NPCJobType[] {Monk,Hermit,Fugative,Exile,Druid,Hedge_Wizard,Ranger,Seer,Sage,Witch};
	public static final NPCJobType[] Shack_jobs = new NPCJobType[] {Hermit,Fugative,Exile,Druid,Hedge_Wizard,Ranger,Witch};
	public static final NPCJobType[] Shrine_jobs = new NPCJobType[] {Acolyte,Druid,Priest,Cultist,null};
	public static final NPCJobType[] Temple_jobs = new NPCJobType[] {Acolyte,Priest,Cleric,Paladin,Cultist};
	public static final NPCJobType[] Village_jobs = new NPCJobType[] {Noble,Priest,Bureaucrat,Innkeeper};
	public static final NPCJobType[] Wall_jobs = new NPCJobType[] {Knight,Guard,Soldier,null};
	public static final NPCJobType[] Watchtower_jobs = new NPCJobType[] {Knight,Guard,Soldier,Scout};
	public static final NPCJobType[] Academy_jobs = new NPCJobType[] {Noble,Scholar,Scribe,Wizard};
	public static final NPCJobType[] Alchemist_jobs = new NPCJobType[] {Alchemist,Forager};
	public static final NPCJobType[] Archive_jobs = new NPCJobType[] {Noble,Scholar,Scribe,Wizard};
	public static final NPCJobType[] Art_Dealer_jobs = new NPCJobType[] {Painter,Sculptor};
	public static final NPCJobType[] Barber_jobs = new NPCJobType[] {Barber};
	public static final NPCJobType[] Bookbinder_jobs = new NPCJobType[] {Scholar,Scribe};
	public static final NPCJobType[] Bookseller_jobs = new NPCJobType[] {Scholar,Scribe,Item_Seller};
	public static final NPCJobType[] Castle_jobs = new NPCJobType[] {Noble,Knight};
	public static final NPCJobType[] Clockmaker_jobs = new NPCJobType[] {Clockmaker};
	public static final NPCJobType[] Clothier_jobs = new NPCJobType[] {Haberdasher,Tailor,Item_Seller};
	public static final NPCJobType[] Courthouse_jobs = new NPCJobType[] {Bureaucrat,Noble,Jailer};
	public static final NPCJobType[] Furrier_jobs = new NPCJobType[] {Hunter,Trapper,Poacher,Ranger,Shepherd};
	public static final NPCJobType[] Gallery_jobs = new NPCJobType[] {Painter,Sculptor,Item_Seller};
	public static final NPCJobType[] Garden_jobs = new NPCJobType[] {Apothacary,Farmer,Druid,Monk};
	public static final NPCJobType[] Haberdashery_jobs = new NPCJobType[] {Haberdasher,Tailor};
	public static final NPCJobType[] Jeweler_jobs = new NPCJobType[] {Jeweler};
	public static final NPCJobType[] Law_Office_jobs = new NPCJobType[] {Lawyer};
	public static final NPCJobType[] Locksmith_jobs = new NPCJobType[] {Locksmith,Rogue,Burglar};
	public static final NPCJobType[] Lounge_jobs = new NPCJobType[] {Innkeeper,Smuggler};
	public static final NPCJobType[] Manor_jobs = new NPCJobType[] {Noble,Item_Seller,Ruffian};
	public static final NPCJobType[] Museum_jobs = new NPCJobType[] {Scholar,Explorer,Tomb_Raider};
	public static final NPCJobType[] Observatory_jobs = new NPCJobType[] {Scholar,Wizard,Warlock,Seer};
	public static final NPCJobType[] Opera_House_jobs = new NPCJobType[] {Musician,Poet,Acrobat,Actor,Minstrel,Bard};
	public static final NPCJobType[] Park_jobs = new NPCJobType[] {Noble,Druid,Ranger,Hermit,Outlander};
	public static final NPCJobType[] Physician_jobs = new NPCJobType[] {Apothacary,Barber,Priest,Scholar};
	public static final NPCJobType[] Printer_jobs = new NPCJobType[] {Scribe};
	public static final NPCJobType[] Restaurant_jobs = new NPCJobType[] {Chef,Innkeeper,Fishmonger};
	public static final NPCJobType[] Salon_jobs = new NPCJobType[] {Barber,Wigmaker};
	public static final NPCJobType[] Stables_jobs = new NPCJobType[] {Ranger,Servant,Animal_Breeder};
	public static final NPCJobType[] Taxidermist_jobs = new NPCJobType[] {Taxidermist};
	public static final NPCJobType[] Tobacconist_jobs = new NPCJobType[] {Item_Seller,Smuggler};
	public static final NPCJobType[] Townhouse_jobs = civilized;
	public static final NPCJobType[] Winery_jobs = new NPCJobType[] {Brewer};
	public static final NPCJobType[] Zoo_jobs = new NPCJobType[] {Animal_Breeder,Ranger,Druid,Outlander,Noble};
	public static final NPCJobType[] Apothacary_jobs = new NPCJobType[] {Apothacary,Druid,Hedge_Wizard,Witch,Sage};
	public static final NPCJobType[] Asylum_jobs = new NPCJobType[] {Priest,Bureaucrat,Scholar,Jailer};
	public static final NPCJobType[] Baker_jobs = new NPCJobType[] {Baker};
	public static final NPCJobType[] Brewery_jobs = new NPCJobType[] {Brewer};
	public static final NPCJobType[] Butcher_jobs = new NPCJobType[] {Butcher};
	public static final NPCJobType[] Candlemaker_jobs = new NPCJobType[] {Artisan,Apiarist};
	public static final NPCJobType[] Catacombs_jobs = new NPCJobType[] {Acolyte,Priest,Cleric,Paladin,Gravedigger,Necromancer,Cultist,null};
	public static final NPCJobType[] Cheesemaker_jobs = new NPCJobType[] {Artisan,Shepherd};
	public static final NPCJobType[] Criminal_Den_jobs = new NPCJobType[] {Assassin,Burglar,Charlatan,Cultist,Cutpurse,Deserter,Fence,Forger,Gambler,Poisoner,Privateer,Smuggler,Spy,Gutter_Mage,
			Rogue,Warlock,Ruffian};
	public static final NPCJobType[] Curiosity_Shop_jobs = new NPCJobType[] {Item_Seller,Artificer,Fortuneseller,Smuggler,Seer};
	public static final NPCJobType[] Dock_jobs = new NPCJobType[] {Sailor,Laborer,Privateer,Smuggler,Shipwright};
	public static final NPCJobType[] Fighting_Pit_jobs = new NPCJobType[] {Gambler,Gladiator};
	public static final NPCJobType[] Forge_jobs = new NPCJobType[] {Blacksmith};
	public static final NPCJobType[] Fortuneteller_jobs = new NPCJobType[] {Fortuneseller,Charlatan,Seer};
	public static final NPCJobType[] Gambling_Hall_jobs = new NPCJobType[] {Gambler,Loan_Shark,Ruffian};
	public static final NPCJobType[] Leatherworks_jobs = new NPCJobType[] {Hunter,Outlander,Poacher,Shepherd,Trapper,Butcher};
	public static final NPCJobType[] Marketplace_jobs = new NPCJobType[] {Item_Seller,Smuggler,Fence,Caravaneer,Farmer,Tinker};
	public static final NPCJobType[] Mason_jobs = new NPCJobType[] {Mason};
	public static final NPCJobType[] Mill_jobs = new NPCJobType[] {Miller};
	public static final NPCJobType[] Moneylender_jobs = new NPCJobType[] {Loan_Shark,Noble,Trader};
	public static final NPCJobType[] Orphanage_jobs = new NPCJobType[] {Noble,Priest,Servant};
	public static final NPCJobType[] Outfitter_jobs = new NPCJobType[] {Caravaneer,Explorer,Fisher,Forager,Hunter,Monster_Hunter,Surveyor,Tomb_Raider};
	public static final NPCJobType[] Prison_jobs = new NPCJobType[] {Jailer};
	public static final NPCJobType[] Sewers_jobs = new NPCJobType[] {null,Beggar,Bandit,Fugative,Servant};
	public static final NPCJobType[] Shipyards_jobs = new NPCJobType[] {Sailor,Laborer,Privateer,Smuggler,Shipwright};
	public static final NPCJobType[] Stockyard_jobs = new NPCJobType[] {Smuggler,Laborer,Item_Seller};
	public static final NPCJobType[] Stonecarver_jobs = new NPCJobType[] {Mason};
	public static final NPCJobType[] Tattooist_jobs = new NPCJobType[] {Barber,Outlander,Ruffian};
	public static final NPCJobType[] Tavern_jobs = new NPCJobType[] {Innkeeper};
	public static final NPCJobType[] Theater_jobs = new NPCJobType[] {Musician,Poet,Acrobat,Actor,Minstrel,Bard};
	public static final NPCJobType[] Veterinarian_jobs = new NPCJobType[] {Animal_Breeder,Ranger,Druid,Hedge_Wizard,Witch};
	public static final NPCJobType[] Warehouse_jobs = new NPCJobType[] {Smuggler,Laborer,Item_Seller};
	public static final NPCJobType[] Weaver_jobs = new NPCJobType[] {Artisan};
	public static final NPCJobType[] Workshop_jobs = new NPCJobType[] {Carpenter,Artificer,Tinker};
	public static final NPCJobType[] Slum_jobs = new NPCJobType[] {null,Loan_Shark,Carpenter,Ruffian,Charlatan,Acolyte,Servant};
	public static final NPCJobType[] Auction_House_jobs = new NPCJobType[] {Item_Seller,Smuggler,Fence,Caravaneer};
	
	//Faction Job Lists
	public static final NPCJobType[] DARK_CULT_JOBS = new NPCJobType[] {Acolyte,Priest,Cleric,Cultist,Warlock,Witch,Charlatan};
	public static final NPCJobType[] HERETICAL_SECT_JOBS = new NPCJobType[] {Acolyte,Priest,Cleric,Cultist,Warlock,Charlatan};
	public static final NPCJobType[] NATIONAL_CHURCH_JOBS = new NPCJobType[] {Acolyte,Priest,Cleric,Cultist,Warlock,Sorcerer};
	public static final NPCJobType[] RELIGIOUS_ORDER_JOBS = new NPCJobType[] {Acolyte,Priest,Cleric,Cultist,Warlock,Sorcerer};
	public static final NPCJobType[] RELIGIOUS_SECT_JOBS = new NPCJobType[] {Acolyte,Priest,Cleric,Cultist,Warlock,Sorcerer};
	public static final NPCJobType[] DRUID_CIRCLE_JOBS = new NPCJobType[] {Acolyte,Priest,Druid,Ranger,Hedge_Wizard,Witch};
	public static final NPCJobType[] MONASTIC_ORDER_JOBS = new NPCJobType[] {Acolyte,Priest,Cleric,Monk,Sage,Scholar};
	
	public static final NPCJobType[] ART_MOVEMENT_JOBS = new NPCJobType[] {Painter,Sculptor};
	public static final NPCJobType[] BARD_COLLEGE_JOBS = new NPCJobType[] {Musician,Poet,Acrobat,Actor,Minstrel,Bard};
	public static final NPCJobType[] BEGGAR_GUILD_JOBS = new NPCJobType[] {Beggar,Burglar,Chimneysweep,Charlatan,Cutpurse,Fence,Gambler,Informant,Laborer,Lamplighter,Ratcatcher,Servant,
			Spy,Urchin,Loan_Shark,Vagabond,Gutter_Mage,Ruffian};
	public static final NPCJobType[] BLACK_MARKET_JOBS = new NPCJobType[] {Smuggler,Fence,Caravaneer,Farmer,Tinker};
	public static final NPCJobType[] BROTHERHOOD_JOBS = new NPCJobType[] {Actor,Apothacary,Baker,Barber,Blacksmith,Brewer,Bureaucrat,Butcher,Carpenter,Clockmaker,Courier,Courtier,Guard,Haberdasher,
			Innkeeper,Item_Seller,Jeweler,Knight,Locksmith,Mason,Miller,Musician,Noble,Scholar,Scribe,Shipwright,Tailor,Artificer,Bard,Wizard,Alchemist,Animal_Breeder,Assassin,Acrobat,
			Chimneysweep,Ditchdigger,Fortuneseller,Gladiator,Gravedigger,Jailer,Laborer,Lamplighter,Mercenary,Privateer,Ratcatcher,Sailor,Servant,Loan_Shark,Gutter_Mage,Rogue,Sorcerer,Warlock,
			Apiarist,Caravan_Guard,Caravaneer,Druid,Explorer,Farmer,Fisher,Forager,Hedge_Wizard,Hermit,Hunter,Messenger,Minstrel,Monster_Hunter,Outlander,
			Tinker,Pilgrim,Ranger,Sage,Scout,Shepherd,Seer,Surveyor,Tomb_Raider,Trader,Trapper,Witch,Woodcutter,Lawyer,Chef,Artisan};
	public static final NPCJobType[] CITY_GUARD_JOBS = new NPCJobType[] {Guard};
	public static final NPCJobType[] CONSPIRACY_JOBS = NPCJobType.values();
	public static final NPCJobType[] CRAFT_GUILD_JOBS = new NPCJobType[] {Apothacary,Baker,Blacksmith,Brewer,Carpenter,Clockmaker,Haberdasher,Jeweler,Locksmith,Mason,Miller,Scribe,Shipwright,
			Tailor,Artificer,Alchemist,Tinker,Chef,Artisan};
	public static final NPCJobType[] CRIME_FAMILY_JOBS = new NPCJobType[] {Assassin,Burglar,Charlatan,Cutpurse,Fence,Forger,Gambler,Poisoner,Privateer,Smuggler,Spy,Loan_Shark,Ruffian};
	public static final NPCJobType[] CRIME_RING_JOBS = new NPCJobType[] {Assassin,Burglar,Charlatan,Cutpurse,Fence,Forger,Gambler,Poisoner,Privateer,Smuggler,Spy,Loan_Shark,Ruffian};
	public static final NPCJobType[] EXPLORER_CLUB_JOBS = new NPCJobType[] {Explorer,Monster_Hunter,Outlander,Ranger,Scout,Surveyor,Tomb_Raider,Trader};
	public static final NPCJobType[] FREE_COMPANY_JOBS = new NPCJobType[] {Knight,Mercenary,Privateer};
	public static final NPCJobType[] GOURMAND_CLUB_JOBS = new NPCJobType[] {Baker,Brewer,Chef,Noble,Trader};
	public static final NPCJobType[] HEIST_CREW_JOBS = new NPCJobType[] {Burglar,Charlatan,Ruffian};
	public static final NPCJobType[] HIGH_COUNCIL_JOBS = new NPCJobType[] {Acolyte,Bureaucrat,Courtier,Diplomat,Knight,Noble,Priest,Scholar,Scribe,Soldier,Lawyer};
	public static final NPCJobType[] HIRED_KILLERS_JOBS = new NPCJobType[] {Assassin,Poisoner,Ruffian};
	public static final NPCJobType[] LOCAL_MILITIA_JOBS = new NPCJobType[] {Knight,Soldier,Bureaucrat};
	public static final NPCJobType[] NOBLE_HOUSE_JOBS = new NPCJobType[] {Noble,Knight,Diplomat};
	public static final NPCJobType[] OUTLANDER_CLAN_JOBS = new NPCJobType[] {Outlander,Priest,Noble,Raider,Scavenger,Shepherd};
	public static final NPCJobType[] OUTLAW_GANG_JOBS = new NPCJobType[] {Privateer,Smuggler,Bandit,Raider,Scavenger};
	public static final NPCJobType[] POLITICAL_PARTY_JOBS = new NPCJobType[] {Acolyte,Bureaucrat,Courtier,Diplomat,Knight,Noble,Priest,Scholar,Scribe,Soldier,Lawyer,Laborer,Item_Seller};
	public static final NPCJobType[] KNIGHTLY_ORDER_JOBS = new NPCJobType[] {Knight,Noble,Monster_Hunter,Paladin};
	public static final NPCJobType[] RESISTANCE_JOBS = new NPCJobType[] {Charlatan,Cultist,Noble,Vagabond,Exile,Fugative,Urchin,Laborer};
	public static final NPCJobType[] ROYAL_ARMY_JOBS = new NPCJobType[] {Knight,Noble,Soldier,Paladin};
	public static final NPCJobType[] ROYAL_HOUSE_JOBS = new NPCJobType[] {Noble,Knight,Diplomat};
	public static final NPCJobType[] SCHOLAR_CIRCLE_JOBS = new NPCJobType[] {Bureaucrat,Noble,Priest,Scholar,Scribe,Bard,Wizard,Lawyer,Sage,Seer,Poet,Actor};
	public static final NPCJobType[] SECRET_SOCIETY_JOBS = BROTHERHOOD_JOBS;
	public static final NPCJobType[] SPY_NETWORK_JOBS = new NPCJobType[] {Spy,Diplomat,Noble,Cultist,Exile};
	public static final NPCJobType[] STREET_ARTISTS_JOBS = new NPCJobType[] {Actor,Musician,Painter,Acrobat,Charlatan,Fortuneseller,Minstrel};
	public static final NPCJobType[] STREET_GANG_JOBS = new NPCJobType[] {Burglar,Charlatan,Cutpurse,Fence,Gambler,Smuggler,Spy,Ruffian};
	public static final NPCJobType[] THEATER_TROUPE_JOBS = new NPCJobType[] {Musician,Poet,Acrobat,Actor,Minstrel,Bard};
	public static final NPCJobType[] TRADE_COMPANY_JOBS = new NPCJobType[] {Artificer,Item_Seller,Fence,Sailor,Smuggler,Caravaneer,Tinker,Tomb_Raider,Trader};
	public static final NPCJobType[] WIZARD_CIRCLE_JOBS = new NPCJobType[] {Abjurer,Swordmage,Conjurer,Diviner,Enchanter,Evoker,Illusionist,Necromancer,Scrivener,Transmuter,Warmage,
			Wizard,Gutter_Mage,Witch,Hedge_Wizard};

	public static final NPCJobType[] AUTOCRACY_JOBS = ROYAL_HOUSE_JOBS;
	public static final NPCJobType[] ELECTED_OFFICIAL_JOBS = HIGH_COUNCIL_JOBS;
	public static final NPCJobType[] MONARCHY_JOBS = ROYAL_HOUSE_JOBS;
	public static final NPCJobType[] PLUTOCRACY_JOBS = TRADE_COMPANY_JOBS;
	public static final NPCJobType[] ARISTOCRACY_JOBS = NOBLE_HOUSE_JOBS;
	public static final NPCJobType[] THEOCRACY_JOBS = NATIONAL_CHURCH_JOBS;
	public static final NPCJobType[] CLANS_JOBS = HIGH_COUNCIL_JOBS;
	public static final NPCJobType[] COMMUNE_JOBS = HIGH_COUNCIL_JOBS;
	public static final NPCJobType[] ELECTED_COUNCIL_JOBS = HIGH_COUNCIL_JOBS;

	public static NPCJobType getCivilized(Indexible obj) {
		return (NPCJobType) Util.getElementFromArray(civilized, obj);
	}
	public static NPCJobType getUnderworld(Indexible obj) {
		return (NPCJobType) Util.getElementFromArray(underworld, obj);
	}
	public static NPCJobType getWilderness(Indexible obj) {
		return (NPCJobType) Util.getElementFromArray(wilderness, obj);
	}
	public static NPCJobType getJob(Indexible obj,boolean isCity,boolean isTown) {
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
	public static NPCJobType getJob(Indexible obj) {
		int id = obj.reduceTempId(3);
		if(id==0) return getCivilized(obj);
		else if(id==1) return getUnderworld(obj);
		else return getWilderness(obj);
	}
	
	public String toString() {
		return Util.replace(this.name(), "_", " ");
	}
	
	public LocationType[] getLocationTypes() {
		switch(this) {
		case Acolyte: return LocationType.Acolyte_locations;
		case Actor: return LocationType.Actor_locations;
		case Apothacary: return LocationType.Apothacary_locations;
		case Baker: return LocationType.Baker_locations;
		case Barber: return LocationType.Barber_locations;
		case Blacksmith: return LocationType.Blacksmith_locations;
		case Brewer: return LocationType.Brewer_locations;
		case Bureaucrat: return LocationType.Bureaucrat_locations;
		case Butcher: return LocationType.Butcher_locations;
		case Carpenter: return LocationType.Carpenter_locations;
		case Clockmaker: return LocationType.Clockmaker_locations;
		case Courier: return LocationType.Courier_locations;
		case Courtier: return LocationType.Courtier_locations;
		case Diplomat: return LocationType.Diplomat_locations;
		case Fishmonger: return LocationType.Fishmonger_locations;
		case Guard: return LocationType.Guard_locations;
		case Haberdasher: return LocationType.Haberdasher_locations;
		case Innkeeper: return LocationType.Innkeeper_locations;
		case Item_Seller: return LocationType.Item_Seller_locations;
		case Jeweler: return LocationType.Jeweler_locations;
		case Knight: return LocationType.Knight_locations;
		case Locksmith: return LocationType.Locksmith_locations;
		case Mason: return LocationType.Mason_locations;
		case Miller: return LocationType.Miller_locations;
		case Musician: return LocationType.Musician_locations;
		case Noble: return LocationType.Noble_locations;
		case Painter: return LocationType.Painter_locations;
		case Priest: return LocationType.Priest_locations;
		case Scholar: return LocationType.Scholar_locations;
		case Scribe: return LocationType.Scribe_locations;
		case Sculptor: return LocationType.Sculptor_locations;
		case Shipwright: return LocationType.Shipwright_locations;
		case Soldier: return LocationType.Soldier_locations;
		case Tailor: return LocationType.Tailor_locations;
		case Taxidermist: return LocationType.Taxidermist_locations;
		case Wigmaker: return LocationType.Wigmaker_locations;
		case Artificer: return LocationType.Artificer_locations;
		case Bard: return LocationType.Bard_locations;
		case Cleric: return LocationType.Cleric_locations;
		case Monk: return LocationType.Monk_locations;
		case Paladin: return LocationType.Paladin_locations;
		case Wizard: return LocationType.Wizard_locations;
		case Alchemist: return LocationType.Alchemist_locations;
		case Animal_Breeder: return LocationType.Animal_Breeder_locations;
		case Assassin: return LocationType.Assassin_locations;
		case Acrobat: return LocationType.Acrobat_locations;
		case Beggar: return LocationType.Beggar_locations;
		case Burglar: return LocationType.Burglar_locations;
		case Chimneysweep: return LocationType.Chimneysweep_locations;
		case Charlatan: return LocationType.Charlatan_locations;
		case Cultist: return LocationType.Cultist_locations;
		case Cutpurse: return LocationType.Cutpurse_locations;
		case Deserter: return LocationType.Deserter_locations;
		case Ditchdigger: return LocationType.Ditchdigger_locations;
		case Fence: return LocationType.Fence_locations;
		case Forger: return LocationType.Forger_locations;
		case Fortuneseller: return LocationType.Fortuneseller_locations;
		case Gambler: return LocationType.Gambler_locations;
		case Gladiator: return LocationType.Gladiator_locations;
		case Gravedigger: return LocationType.Gravedigger_locations;
		case Headsman: return LocationType.Headsman_locations;
		case Informant: return LocationType.Informant_locations;
		case Jailer: return LocationType.Jailer_locations;
		case Laborer: return LocationType.Laborer_locations;
		case Lamplighter: return LocationType.Lamplighter_locations;
		case Mercenary: return LocationType.Mercenary_locations;
		case Poet: return LocationType.Poet_locations;
		case Poisoner: return LocationType.Poisoner_locations;
		case Privateer: return LocationType.Privateer_locations;
		case Ratcatcher: return LocationType.Ratcatcher_locations;
		case Sailor: return LocationType.Sailor_locations;
		case Servant: return LocationType.Servant_locations;
		case Smuggler: return LocationType.Smuggler_locations;
		case Spy: return LocationType.Spy_locations;
		case Urchin: return LocationType.Urchin_locations;
		case Loan_Shark: return LocationType.Loan_Shark_locations;
		case Vagabond: return LocationType.Vagabond_locations;
		case Gutter_Mage: return LocationType.Gutter_Mage_locations;
		case Rogue: return LocationType.Rogue_locations;
		case Sorcerer: return LocationType.Sorcerer_locations;
		case Warlock: return LocationType.Warlock_locations;
		case Apiarist: return LocationType.Apiarist_locations;
		case Bandit: return LocationType.Bandit_locations;
		case Caravan_Guard: return LocationType.Caravan_Guard_locations;
		case Caravaneer: return LocationType.Caravaneer_locations;
		case Druid: return LocationType.Druid_locations;
		case Exile: return LocationType.Exile_locations;
		case Explorer: return LocationType.Explorer_locations;
		case Farmer: return LocationType.Farmer_locations;
		case Fisher: return LocationType.Fisher_locations;
		case Forager: return LocationType.Forager_locations;
		case Fugative: return LocationType.Fugative_locations;
		case Hedge_Wizard: return LocationType.Hedge_Wizard_locations;
		case Hermit: return LocationType.Hermit_locations;
		case Hunter: return LocationType.Hunter_locations;
		case Messenger: return LocationType.Messenger_locations;
		case Minstrel: return LocationType.Minstrel_locations;
		case Monster_Hunter: return LocationType.Monster_Hunter_locations;
		case Outlander: return LocationType.Outlander_locations;
		case Tinker: return LocationType.Tinker_locations;
		case Pilgrim: return LocationType.Pilgrim_locations;
		case Poacher: return LocationType.Poacher_locations;
		case Raider: return LocationType.Raider_locations;
		case Ranger: return LocationType.Ranger_locations;
		case Sage: return LocationType.Sage_locations;
		case Scavenger: return LocationType.Scavenger_locations;
		case Scout: return LocationType.Scout_locations;
		case Shepherd: return LocationType.Shepherd_locations;
		case Seer: return LocationType.Seer_locations;
		case Surveyor: return LocationType.Surveyor_locations;
		case Tomb_Raider: return LocationType.Tomb_Raider_locations;
		case Trader: return LocationType.Trader_locations;
		case Trapper: return LocationType.Trapper_locations;
		case Witch: return LocationType.Witch_locations;
		case Woodcutter: return LocationType.Woodcutter_locations;
		case Barbarian: return LocationType.Barbarian_locations;
		case Lawyer: return LocationType.Lawyer_locations;
		case Ruffian: return LocationType.Ruffian_locations;
		case Chef: return LocationType.Chef_locations;
		case Artisan: return LocationType.Artisan_locations;
		case Abjurer: return LocationType.Abjurer_locations;
		case Swordmage: return LocationType.Swordmage_locations;
		case Conjurer: return LocationType.Conjurer_locations;
		case Diviner: return LocationType.Diviner_locations;
		case Enchanter: return LocationType.Enchanter_locations;
		case Evoker: return LocationType.Evoker_locations;
		case Illusionist: return LocationType.Illusionist_locations;
		case Necromancer: return LocationType.Necromancer_locations;
		case Scrivener: return LocationType.Scrivener_locations;
		case Transmuter: return LocationType.Transmuter_locations;
		case Warmage: return LocationType.Warmage_locations;
		default: return null;
		}
	}
}
