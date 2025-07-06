package data.npc;

import data.Indexible;
import util.Util;

public enum NPCJobType {
	Acolyte,Actor,Apothacary,Baker,Barber,Blacksmith,Brewer,Bureaucrat,Butcher,Carpenter,Clockmaker,Courier,Courtier,Diplomat,Fishmonger,Guard,Haberdasher,Innkeeper,Item_Seller,Jeweler,Knight,
	Locksmith,Mason,Miller,Musician,Noble,Painter,Priest,Scholar,Scribe,Sculptor,Shipwright,Soldier,Tailor,Taxidermist,Wigmaker,Artificer,Bard,Cleric,Monk,Paladin,Wizard,
	
	Alchemist,Animal_Breeder,Assassin,Acrobat,Beggar,Burglar,Chimneysweep,Con_Man,Cultist,Cutpurse,Deserter,Ditchdigger,Fence,Forger,Fortuneseller,Gambler,Gladiator,Gravedigger,Headsman,
	Informant,Jailer,Laborer,Lamplighter,Mercenary,Poet,Poisoner,Privateer,Ratcatcher,Sailor,Servant,Smuggler,Spy,Urchin,Userer,Vagabond,Gutter_Mage,Rogue,Sorcerer,Warlock,
	
	Apiarist,Bandit,Caravan_Guard,Caravaneer,Druid,Exile,Explorer,Farmer,Fisher,Forager,Fugative,Hedge_Wizard,Hermit,Hunter,Messenger,Minstrel,Monster_Hunter,Outlander,
	Tinker,Pilgrim,Poacher,Raider,Ranger,Sage,Scavenger,Scout,Shepherd,Seer,Surveyor,Tomb_Raider,Trader,Trapper,Witch,Woodcutter,Barbarian;

	public static final NPCJobType[] civilized = new NPCJobType[] {Acolyte,Actor,Apothacary,Baker,Barber,Blacksmith,Brewer,Bureaucrat,Butcher,Carpenter,Clockmaker,Courier,Courtier,Diplomat,Fishmonger,
			Guard,Haberdasher,Innkeeper,Item_Seller,Jeweler,Knight,Locksmith,Mason,Miller,Musician,Noble,Painter,Priest,Scholar,Scribe,Sculptor,Shipwright,Soldier,Tailor,Taxidermist,Wigmaker,
			Artificer,Bard,Cleric,Monk,Paladin,Wizard};
	public static final NPCJobType[] underworld = new NPCJobType[] {Alchemist,Animal_Breeder,Assassin,Acrobat,Beggar,Burglar,Chimneysweep,Con_Man,Cultist,Cutpurse,Deserter,Ditchdigger,Fence,Forger,
			Fortuneseller,Gambler,Gladiator,Gravedigger,Headsman,Informant,Jailer,Laborer,Lamplighter,Mercenary,Poet,Poisoner,Privateer,Ratcatcher,Sailor,Servant,Smuggler,Spy,Urchin,Userer,
			Vagabond,Gutter_Mage,Rogue,Sorcerer,Warlock};
	public static final NPCJobType[] wilderness = new NPCJobType[] {Apiarist,Bandit,Caravan_Guard,Caravaneer,Druid,Exile,Explorer,Farmer,Fisher,Forager,Fugative,Hedge_Wizard,Hermit,Hunter,Messenger,
			Minstrel,Monk,Monster_Hunter,Outlander,Tinker,Pilgrim,Poacher,Raider,Ranger,Sage,Scavenger,Scout,Shepherd,Seer,Surveyor,Tomb_Raider,Trader,Trapper,Witch,Woodcutter,Barbarian};


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
