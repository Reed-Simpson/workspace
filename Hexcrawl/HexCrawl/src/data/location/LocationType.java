package data.location;

import data.Indexible;
import util.Util;

public enum LocationType {
	Bog,Boulder,Butte,Cave,Cliff,Crag,Crater,Creek,Crossing,Ditch,Field,Forest,Grove,Hill,Hollow,Hotspring,Lair,Lake,Lakebed,Marsh,Mesa,Moor,Pass,Peak,Pit,Pond,
	Rapids,Ravine,Ridge,Rise,River,Rockslide,Spring,Swamp,Thicket,Valley,Fall,
	Altar,Aqueduct,Bandit_Camp,Battlefield,Bonfire,Bridge,Cairn,Crossroads,Crypt,Dam,Dungeon,Farm,Ford,Fortress,Gallows,Graveyard,Hedge,Hunter_Camp,Inn,Lumber_Camp,
	Mine,Monastery,Monument,Orchard,Outpost,Pasture,Ruin,Seclusion,Shack,Shrine,Standing_Stone,Temple,Village,Wall,Watchtower,Waystone,
	
	Academy,Alchemist,Archive,Art_Dealer,Barber,Bookbinder,Bookseller,Castle,Clockmaker,Clothier,Courthouse,Furrier,Gallery,Garden,Haberdashery,Jeweler,Law_Office,Locksmith,Lounge,Manor,
	Museum,Observatory,Opera_House,Park,Physician,Printer,Public_Baths,Restaurant,Salon,Stables,Taxidermist,Tobacconist,Townhouse,Winery,Zoo,
	Apothacary,Asylum,Baker,Brewery,Butcher,Candlemaker,Catacombs,Cheesemaker,Criminal_Den,Curiosity_Shop,Dock,Fighting_Pit,Forge,Fortuneteller,Gambling_Hall,Leatherworks,Marketplace,Mason,Mill,
	Moneylender,Orphanage,Outfitter,Prison,Sewers,Shipyards,Stockyard,Stonecarver,Tattooist,Tavern,Theater,Veterinarian,Warehouse,Weaver,Workshop;

	public static final LocationType[] landmarks = new LocationType[] {Bog,Boulder,Butte,Cave,Cliff,Crag,Crater,Creek,Crossing,Ditch,Field,Forest,Grove,Hill,Hollow,Hotspring,Lair,Lake,Lakebed,
			Marsh,Mesa,Moor,Pass,Peak,Pit,Pond,Rapids,Ravine,Ridge,Rise,River,Rockslide,Spring,Swamp,Thicket,Valley,Fall};
	public static final LocationType[] structures = new LocationType[] {Altar,Aqueduct,Bandit_Camp,Battlefield,Bonfire,Bridge,Cairn,Crossroads,Crypt,Dam,Dungeon,Farm,Ford,Fortress,Gallows,
			Graveyard,Hedge,Hunter_Camp,Inn,Lumber_Camp,Mine,Monastery,Monument,Orchard,Outpost,Pasture,Ruin,Seclusion,Shack,Shrine,Standing_Stone,Temple,Village,Wall,Watchtower,Waystone};
	public static final LocationType[] ucBuildings = new LocationType[] {Academy,Alchemist,Archive,Art_Dealer,Barber,Bookbinder,Bookseller,Castle,Clockmaker,Clothier,Courthouse,Furrier,Gallery,
			Garden,Haberdashery,Jeweler,Law_Office,Locksmith,Lounge,Manor,Museum,Observatory,Opera_House,Park,Physician,Printer,Public_Baths,Restaurant,Salon,Stables,Taxidermist,Temple,
			Tobacconist,Townhouse,Winery,Zoo};
	public static final LocationType[] lcBuildings = new LocationType[] {Apothacary,Asylum,Baker,Brewery,Butcher,Candlemaker,Catacombs,Cheesemaker,Criminal_Den,Curiosity_Shop,Dock,Fighting_Pit,
			Forge,Fortuneteller,Gambling_Hall,Leatherworks,Marketplace,Mason,Mill,Moneylender,Orphanage,Outfitter,Prison,Sewers,Shipyards,Shrine,Stockyard,Stonecarver,Tattooist,Tavern,
			Theater,Veterinarian,Warehouse,Watchtower,Weaver,Workshop};
	

	public static String getBuilding(Indexible obj) {
		if(obj.reduceTempId(2)==0) return getUCBuilding(obj);
		else return getLCBuilding(obj);
	}
	public static String getUCBuilding(Indexible obj) {
		return Util.getElementFromArray(ucBuildings, obj).toString();
	}
	public static String getLCBuilding(Indexible obj) {
		return Util.getElementFromArray(lcBuildings, obj).toString();
	}

	public static String getLandmark(Indexible obj) {
		return Util.getElementFromArray(landmarks, obj).toString();
	}
	public static String getStructure(Indexible obj) {
		return Util.getElementFromArray(structures, obj).toString();
	}
	public static String getStructureOrLandmark(Indexible obj) {
		int i = obj.reduceTempId(2);
		if(i==0) return getStructure(obj);
		else return getLandmark(obj);
	}
	
	public String toString() {
		return Util.replace(this.name(), "_", " ");
	}

}
