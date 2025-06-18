package data;

public class GenericTables {
	private static final String FANCYCOLORS = "Golden,Crimson,Ember,Emerald,Amber,Scarlet,Ruby,Vermillion,Rose,Viridian,Azure,Cerulean,Sapphire,Cobalt,Indigo,Violet,Amethyst,Silver,"+
			"Brass,Bronze,Copper,Lavender,Coral,Ebony,Jade,Ashen,Grey,Pearl,Ivory,Pale,Topaz,Crystal,Obsidian,Black,Dusken,Midnight,Cinder,Sable,Shadow";
	private static WeightedTable<String> fancycolors;
	private static final String BASICCOLORS = "Red,Orange,Yellow,Green,Blue,White,Black,Grey,Brown";
	private static WeightedTable<String> basiccolors;
	

	private static void populate(WeightedTable<String> table,String values,String regex) {
		for(String s:values.split(regex)) {
			table.put(s);
		}
	}
	

	public static String getFancyColor(Indexible obj) {
		if(fancycolors==null) {
			fancycolors = new WeightedTable<String>();
			populate(fancycolors,FANCYCOLORS,",");
		}
		return fancycolors.getByWeight(obj);
	}
	public static String getBasicColor(Indexible obj) {
		if(basiccolors==null) {
			basiccolors = new WeightedTable<String>();
			populate(basiccolors,BASICCOLORS,",");
		}
		return basiccolors.getByWeight(obj);
	}
	public static String getColor(Indexible obj) {
		if(obj.reduceTempId(2)==0) return getBasicColor(obj);
		else return getFancyColor(obj);
	}

}
