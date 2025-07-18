package data.location;

import data.Indexible;
import util.Util;

public enum DistrictType {
	Catacombs,civilized_npc,Construction,Crafts,Criminality,Culture,Dining,Education,Entertainment,Finance,Foreigners,Ghettoes,
	Government,Graveyards,Green_Space,Industrialization,Judgement,Livestock,Marketplace,Memorials,Military,Opulence,Pollution,Poverty,
	Punishment,Religion,Science,Trade,Trash,underworld_npc,upper_class_building,lower_class_building,Vices,wilderness_npc,Wizardry,Wonders;


	public static DistrictType getDistrict(Indexible obj) {
		DistrictType result = (DistrictType) Util.getElementFromArray(DistrictType.values(), obj);
		return result;
	}


	public String toString() {
		return Util.replace(this.name(), "_", " ");
	}
	
	public LocationType[] getLocationTypes() {
		switch(this) {
		case Catacombs: return LocationType.Catacombs_locations;
		case Construction: return LocationType.Construction_locations;
		case Crafts: return LocationType.Crafts_locations;
		case Criminality: return LocationType.Criminality_locations;
		case Culture: return LocationType.Culture_locations;
		case Dining: return LocationType.Dining_locations;
		case Education: return LocationType.Education_locations;
		case Entertainment: return LocationType.Entertainment_locations;
		case Finance: return LocationType.Finance_locations;
		case Foreigners: return LocationType.Foreigners_locations;
		case Ghettoes: return LocationType.Ghettoes_locations;
		case Government: return LocationType.Government_locations;
		case Graveyards: return LocationType.Graveyards_locations;
		case Green_Space: return LocationType.Green_Space_locations;
		case Industrialization: return LocationType.Industrialization_locations;
		case Judgement: return LocationType.Judgement_locations;
		case Livestock: return LocationType.Livestock_locations;
		case Marketplace: return LocationType.Marketplace_locations;
		case Memorials: return LocationType.Memorials_locations;
		case Military: return LocationType.Military_locations;
		case Opulence: return LocationType.Opulence_locations;
		case Pollution: return LocationType.Pollution_locations;
		case Poverty: return LocationType.Poverty_locations;
		case Punishment: return LocationType.Punishment_locations;
		case Religion: return LocationType.Religion_locations;
		case Science: return LocationType.Science_locations;
		case Trade: return LocationType.Trade_locations;
		case Trash: return LocationType.Trash_locations;
		case Vices: return LocationType.Vices_locations;
		case Wizardry: return LocationType.Wizardry_locations;
		case Wonders: return LocationType.Wonders_locations;
		case civilized_npc: 
		case lower_class_building: 
		case underworld_npc: 
		case upper_class_building: 
		case wilderness_npc: 
		default: return null;
		}
	}


}
