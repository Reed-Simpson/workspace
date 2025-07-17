package data.npc;

import data.Indexible;
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
	public static final NPCJobType[] Hedge_jobs = new NPCJobType[] {Druid,Hermit,Farmer,Shepherd,Miller,Apiarist,Witch};
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
	public static final NPCJobType[] Haberdashery_jobs = new NPCJobType[] {Haberdasher};
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
	public static final NPCJobType[] Salon_jobs = new NPCJobType[] {Barber};
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
}
