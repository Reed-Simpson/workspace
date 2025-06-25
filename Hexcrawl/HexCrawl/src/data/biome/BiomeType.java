package data.biome;

import java.awt.Color;

public enum BiomeType {
	VOID(Color.BLACK,null,"void",9999),
	WATER(Color.BLUE,'\u2652',"ocean",3), 
	SHALLOWS(Color.getHSBColor(220f/360f, 0.99f, 1f), '\u2652',"shallows",2), 
	GRASSLAND(Color.getHSBColor(100f/360f, 0.7f, 1f), '\u22ce',"grassland",3), 
	FOREST(Color.GREEN, '\u26b2',"forest",4), 
	HIGHLAND(Color.getHSBColor(100f/360f, 0.7f, 0.9f), '\u22ce',"highland",3), 
	HIGHLANDFOREST(Color.getHSBColor(100f/360f, 0.7f, 0.7f), '\u26b2',"highland forest",4), 
	SNOW(Color.WHITE, null,"snowy peaks",3),
	SALTMARSH(Color.MAGENTA, '\u22ce',"salt marsh",6),
	DESERT(Color.YELLOW, '\u2056',"desert",4),
	STEPPE(Color.ORANGE, '\u23dc',"steppe",3),
	FJORDS(Color.getHSBColor(220f/360f, 1f, 0.8f), '\u2E81',"fjords",4),
	CLIFFS(Color.GRAY, '\u2E81',"cliffs",4),
	BEACH(Color.YELLOW, '\u2056',"beach",3), 
	JUNGLE(Color.getHSBColor(100f/360f, 1f, 0.7f), '\u26b2',"jungle",4),
	SAVANNA(Color.getHSBColor(75f/360f, 0.7f, 1f), '\u22ce',"savanna",3),
	WOODYHILLS(Color.getHSBColor(100f/360f, 0.9f, 0.5f), '\u23dc',"wooded hills",4), 
	ROCKYHILLS(Color.getHSBColor(26f/360f, 0.9f, 0.5f), '\u23dc',"barren hills",4), 
	GLACIERS(Color.getHSBColor(220f/360f, 0.1f, 1f), null,"glacier",4), 
	SALTPAN(Color.getHSBColor(60f/360f, 0.2f, 1f), '\u2056',"salt pan",3), 
	TAIGA(Color.getHSBColor(100f/360f, 0.7f, 0.5f), '\u26b2',"taiga",6),
	MOUNTAINS(Color.DARK_GRAY, 'A',"mountains",6),
	BADLANDS(Color.ORANGE, '\u23dc',"badlands",4),
	VOLCANIC(Color.RED, 'A',"volcanic peaks",6),
	WETLAND(Color.getHSBColor(285f/360f, 0.95f, 0.5f), '\u22ce',"wetland",6) ,
	LAKE(Color.getHSBColor(220f/360f, 0.7f, 1f), '\u2652',"lake",2) ,
	FLOODPLAIN(Color.getHSBColor(165f/360f, 0.7f, 1f), '\u22ce',"floodplain",3) ,
	RIVER(Color.getHSBColor(220f/360f, 0.7f, 1f), '\u2652',"river",2), 
	CITY(Color.getHSBColor(75f/360f, 0.0f, 0.9f), '\u2605',"city",0) , 
	TOWN(Color.GRAY, '\u2B24',"town",0),
	DELTA(Color.getHSBColor(200f/360f, 0.7f, 1f), '\u2652',"delta",6) ;

	private Color color;
	private Character ch;
	private String trait;
	private String name;
	private int travel;
	
	private BiomeType(Color color,Character ch,String name,int travel) {
		this.color=color;
		this.ch=ch;
		this.name = name;
		this.travel = travel;
	}
	
	public Color getColor() {
		return this.color;
	}

	public Character getCh() {
		return ch;
	}

	public String getTrait() {
		return trait;
	}

	public void setTrait(String trait) {
		this.trait = trait;
	}

	public String getName() {
		return name;
	}

	public int getTravel() {
		return travel;
	}
	
}
