package data.npc;

import data.Indexible;
import util.Util;

public enum NPCJobType {
	Acolyte,Actor,Apothacary,Baker,Barber,Blacksmith,Brewer,Bureaucrat,Butcher,Carpenter,Clockmaker,Courier,Courtier,Diplomat,Fishmonger,Guard,Haberdasher,Innkeeper,Item_Seller,Jeweler,Knight,
	Locksmith,Mason,Miller,Musician,Noble,Painter,Priest,Scholar,Scribe,Sculptor,Shipwright,Soldier,Tailor,Taxidermist,Wigmaker,Artificer,Bard,Cleric,Monk,Paladin,Wizard,
	
	Alchemist,Animal_Breeder,Assassin,Acrobat,Beggar,Burglar,Chimneysweep,Charlatan,Cultist,Cutpurse,Deserter,Ditchdigger,Fence,Forger,Fortuneseller,Gambler,Gladiator,Gravedigger,Headsman,
	Informant,Jailer,Laborer,Lamplighter,Mercenary,Poet,Poisoner,Privateer,Ratcatcher,Sailor,Servant,Smuggler,Spy,Urchin,Userer,Vagabond,Gutter_Mage,Rogue,Sorcerer,Warlock,
	
	Apiarist,Bandit,Caravan_Guard,Caravaneer,Druid,Exile,Explorer,Farmer,Fisher,Forager,Fugative,Hedge_Wizard,Hermit,Hunter,Messenger,Minstrel,Monster_Hunter,Outlander,
	Tinker,Pilgrim,Poacher,Raider,Ranger,Sage,Scavenger,Scout,Shepherd,Seer,Surveyor,Tomb_Raider,Trader,Trapper,Witch,Woodcutter,Barbarian,
	
	Toll_Collector,Lawyer,Gangster,Thespian,Chef,Artisan,Abjurer,Swordmage,Chronomancer,Conjurer,Diviner,Enchanter,Evoker,Gravimancer,Illusionist,Necromancer,Scrivener,Transmuter,Warmage;

	public static final NPCJobType[] civilized = new NPCJobType[] {Acolyte,Actor,Apothacary,Baker,Barber,Blacksmith,Brewer,Bureaucrat,Butcher,Carpenter,Clockmaker,Courier,Courtier,Diplomat,Fishmonger,
			Guard,Haberdasher,Innkeeper,Item_Seller,Jeweler,Knight,Locksmith,Mason,Miller,Musician,Noble,Painter,Priest,Scholar,Scribe,Sculptor,Shipwright,Soldier,Tailor,Taxidermist,Wigmaker,
			Artificer,Bard,Cleric,Monk,Paladin,Wizard,Lawyer,Chef};
	public static final NPCJobType[] underworld = new NPCJobType[] {Alchemist,Animal_Breeder,Assassin,Acrobat,Beggar,Burglar,Chimneysweep,Charlatan,Cultist,Cutpurse,Deserter,Ditchdigger,Fence,Forger,
			Fortuneseller,Gambler,Gladiator,Gravedigger,Headsman,Informant,Jailer,Laborer,Lamplighter,Mercenary,Poet,Poisoner,Privateer,Ratcatcher,Sailor,Servant,Smuggler,Spy,Urchin,Userer,
			Vagabond,Gutter_Mage,Rogue,Sorcerer,Warlock,Gangster,Thespian};
	public static final NPCJobType[] wilderness = new NPCJobType[] {Apiarist,Bandit,Caravan_Guard,Caravaneer,Druid,Exile,Explorer,Farmer,Fisher,Forager,Fugative,Hedge_Wizard,Hermit,Hunter,Messenger,
			Minstrel,Monk,Monster_Hunter,Outlander,Tinker,Pilgrim,Poacher,Raider,Ranger,Sage,Scavenger,Scout,Shepherd,Seer,Surveyor,Tomb_Raider,Trader,Trapper,Witch,Woodcutter,Barbarian};
	public static final NPCJobType[] wizard = new NPCJobType[] {Abjurer,Swordmage,Chronomancer,Conjurer,Diviner,Enchanter,Evoker,Gravimancer,Illusionist,Necromancer,Scrivener,Transmuter,Warmage};


	public static final NPCJobType[] Bandit_Camp_jobs = new NPCJobType[] {Bandit};
	public static final NPCJobType[] Bonfire_jobs = new NPCJobType[] {Bandit,Caravan_Guard,Caravaneer,Exile,Explorer,Fisher,Forager,Fugative,Hedge_Wizard,Hunter,Messenger,Minstrel,
			Monster_Hunter,Outlander,Tinker,Pilgrim,Poacher,Ranger,Sage,Scavenger,Scout,Seer,Surveyor,Tomb_Raider,Trader,Trapper,Witch,Woodcutter,Barbarian,null};
	public static final NPCJobType[] Bridge_jobs = new NPCJobType[] {Bandit,Toll_Collector,null};
	public static final NPCJobType[] Crossroads_jobs = Bonfire_jobs;
	public static final NPCJobType[] Crypt_jobs = new NPCJobType[] {Acolyte,Priest,Cleric,Paladin,Gravedigger,Necromancer,null};
	public static final NPCJobType[] Dungeon_jobs = new NPCJobType[] {Jailer};
	public static final NPCJobType[] Farm_jobs = new NPCJobType[] {Farmer,Apiarist};
	public static final NPCJobType[] Ford_jobs = new NPCJobType[] {Fisher,null};
	public static final NPCJobType[] Fortress_jobs = new NPCJobType[] {Knight,Noble,Guard,Soldier,Jailer};
	public static final NPCJobType[] Gallows_jobs = new NPCJobType[] {Headsman};
	public static final NPCJobType[] Graveyard_jobs = new NPCJobType[] {Acolyte,Priest,Cleric,Paladin,Gravedigger,Necromancer,null};
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
	public static final NPCJobType[] Shrine_jobs = new NPCJobType[] {Acolyte,Druid,Priest,null};
	public static final NPCJobType[] Temple_jobs = new NPCJobType[] {Acolyte,Priest,Cleric,Paladin};
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
	public static final NPCJobType[] Manor_jobs = new NPCJobType[] {Noble,Item_Seller,Gangster};
	public static final NPCJobType[] Museum_jobs = new NPCJobType[] {Scholar,Explorer,Tomb_Raider};
	public static final NPCJobType[] Observatory_jobs = new NPCJobType[] {Scholar,Wizard,Warlock,Seer};
	public static final NPCJobType[] Opera_House_jobs = new NPCJobType[] {Musician,Poet,Acrobat,Thespian,Minstrel,Bard};
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
	public static final NPCJobType[] Catacombs_jobs = new NPCJobType[] {Acolyte,Priest,Cleric,Paladin,Gravedigger,Necromancer,null};
	public static final NPCJobType[] Cheesemaker_jobs = new NPCJobType[] {Artisan,Shepherd};
	public static final NPCJobType[] Criminal_Den_jobs = new NPCJobType[] {Assassin,Burglar,Charlatan,Cultist,Cutpurse,Deserter,Fence,Forger,Gambler,Poisoner,Privateer,Smuggler,Spy,Gutter_Mage,
			Rogue,Warlock,Gangster};
	public static final NPCJobType[] Curiosity_Shop_jobs = new NPCJobType[] {Item_Seller,Artificer,Fortuneseller,Smuggler,Seer};
	public static final NPCJobType[] Dock_jobs = new NPCJobType[] {Sailor,Laborer,Privateer,Smuggler,Shipwright};
	public static final NPCJobType[] Fighting_Pit_jobs = new NPCJobType[] {Gambler,Gladiator};
	public static final NPCJobType[] Forge_jobs = new NPCJobType[] {Blacksmith};
	public static final NPCJobType[] Fortuneteller_jobs = new NPCJobType[] {Fortuneseller,Charlatan,Seer};
	public static final NPCJobType[] Gambling_Hall_jobs = new NPCJobType[] {Gambler,Userer,Gangster};
	public static final NPCJobType[] Leatherworks_jobs = new NPCJobType[] {Hunter,Outlander,Poacher,Shepherd,Trapper,Butcher};
	public static final NPCJobType[] Marketplace_jobs = new NPCJobType[] {Item_Seller,Smuggler,Fence,Caravaneer,Farmer,Tinker};
	public static final NPCJobType[] Mason_jobs = new NPCJobType[] {Mason};
	public static final NPCJobType[] Mill_jobs = new NPCJobType[] {Miller};
	public static final NPCJobType[] Moneylender_jobs = new NPCJobType[] {Userer,Noble,Trader};
	public static final NPCJobType[] Orphanage_jobs = new NPCJobType[] {Noble,Priest,Servant};
	public static final NPCJobType[] Outfitter_jobs = new NPCJobType[] {Caravaneer,Explorer,Fisher,Forager,Hunter,Monster_Hunter,Surveyor,Tomb_Raider};
	public static final NPCJobType[] Prison_jobs = new NPCJobType[] {Jailer};
	public static final NPCJobType[] Sewers_jobs = new NPCJobType[] {null,Beggar,Bandit,Fugative,Servant};
	public static final NPCJobType[] Shipyards_jobs = new NPCJobType[] {Sailor,Laborer,Privateer,Smuggler,Shipwright};
	public static final NPCJobType[] Stockyard_jobs = new NPCJobType[] {Smuggler,Laborer,Item_Seller};
	public static final NPCJobType[] Stonecarver_jobs = new NPCJobType[] {Mason};
	public static final NPCJobType[] Tattooist_jobs = new NPCJobType[] {Barber,Outlander,Gangster};
	public static final NPCJobType[] Tavern_jobs = new NPCJobType[] {Innkeeper};
	public static final NPCJobType[] Theater_jobs = new NPCJobType[] {Musician,Poet,Acrobat,Thespian,Minstrel,Bard};
	public static final NPCJobType[] Veterinarian_jobs = new NPCJobType[] {Animal_Breeder,Ranger,Druid,Hedge_Wizard,Witch};
	public static final NPCJobType[] Warehouse_jobs = new NPCJobType[] {Smuggler,Laborer,Item_Seller};
	public static final NPCJobType[] Weaver_jobs = new NPCJobType[] {Artisan};
	public static final NPCJobType[] Workshop_jobs = new NPCJobType[] {Carpenter,Artificer,Tinker};

	public static String getCivilized(Indexible obj) {
		return Util.getElementFromArray(civilized, obj).toString();
	}
	public static String getUnderworld(Indexible obj) {
		return Util.getElementFromArray(underworld, obj).toString();
	}
	public static String getWilderness(Indexible obj) {
		return Util.getElementFromArray(wilderness, obj).toString();
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
	
	public String toString() {
		return Util.replace(this.name(), "_", " ");
	}

}
