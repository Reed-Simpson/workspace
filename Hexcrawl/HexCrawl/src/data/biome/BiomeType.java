package data.biome;

import java.awt.Color;

import util.Pair;

public enum BiomeType {
	VOID(Color.BLACK,null,"void",9999),
	WATER(Color.BLUE,'\u2652',"ocean",3), 
	SHALLOWS(Color.getHSBColor(220f/360f, 0.99f, 1f), '\u2652',"shallows",2), 
	GRASSLAND(Color.getHSBColor(100f/360f, 0.7f, 1f), '\u22ce',"grassland",3), 
	FOREST(Color.getHSBColor(100f/360f, 1f, 0.7f), '\u26b2',"forest",4), 
	HIGHLAND(Color.getHSBColor(100f/360f, 0.7f, 0.9f), '\u22ce',"highland",3), 
	HIGHLANDFOREST(Color.getHSBColor(100f/360f, 0.7f, 0.7f), '\u26b2',"highland forest",4), 
	SNOW(Color.WHITE, null,"snowy peaks",3),
	SALTMARSH(Color.MAGENTA, '\u22ce',"salt marsh",6),
	DESERT(Color.YELLOW, '\u2056',"desert",4),
	STEPPE(Color.ORANGE, '\u23dc',"steppe",3),
	FJORDS(Color.getHSBColor(220f/360f, 1f, 0.8f), '\u2E81',"fjords",4),
	CLIFFS(Color.GRAY, '\u2E81',"cliffs",4),
	BEACH(Color.YELLOW, '\u2056',"beach",3), 
	JUNGLE(Color.GREEN, '\u26b2',"jungle",4),
	SAVANNA(Color.getHSBColor(75f/360f, 0.7f, 1f), '\u22ce',"savanna",3),
	WOODYHILLS(Color.getHSBColor(100f/360f, 0.9f, 0.5f), '\u23dc',"wooded hills",4), 
	ROCKYHILLS(Color.getHSBColor(26f/360f, 0.9f, 0.5f), '\u23dc',"barren hills",4), 
	GLACIERS(Color.getHSBColor(220f/360f, 0.1f, 1f), null,"glacier",4), 
	SALTPAN(Color.getHSBColor(60f/360f, 0.2f, 1f), '\u2056',"salt pan",3), 
	TAIGA(Color.getHSBColor(100f/360f, 0.7f, 0.5f), '\u26b2',"taiga",6),
	MOUNTAINS(Color.DARK_GRAY, '\u26F0',"mountains",6),
	BADLANDS(Color.ORANGE, '\u23dc',"badlands",4),
	VOLCANIC(Color.RED, '\u26F0',"volcanic peaks",6),
	WETLAND(Color.getHSBColor(285f/360f, 0.95f, 0.5f), '\u22ce',"wetland",6) ,
	LAKE(Color.getHSBColor(220f/360f, 0.7f, 1f), '\u2652',"lake",2) ,
	FLOODPLAIN(Color.getHSBColor(165f/360f, 0.7f, 1f), '\u22ce',"floodplain",3) ,
	RIVER(Color.getHSBColor(220f/360f, 0.7f, 1f), '\u2652',"river",2), 
	CITY(Color.getHSBColor(75f/360f, 0.0f, 0.9f), '\u2605',"city",0) , 
	TOWN(Color.getHSBColor(0.345f, 0.0f, 0.5f), '\u2B24',"town",0),
	DELTA(Color.getHSBColor(200f/360f, 0.7f, 1f), '\u2056',"delta",6) ;

	private Color color;
	private Character ch;
	private String trait;
	private String name;
	private int travel;  //hours to cross 1 hex
	
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
	
	public Pair<BiomeType,BiomeType> getHabitat() {
		switch(this) {
		case VOID: return new Pair<BiomeType,BiomeType>(VOID,VOID);
		case ROCKYHILLS:
		case STEPPE:
		case BADLANDS:return  new Pair<BiomeType,BiomeType>(ROCKYHILLS,ROCKYHILLS);
		case CLIFFS:
		case DELTA:
		case FJORDS:
		case BEACH: return  new Pair<BiomeType,BiomeType>(BEACH,BEACH);
		case TOWN:
		case CITY: return  new Pair<BiomeType,BiomeType>(CITY,CITY);
		case SALTPAN:
		case DESERT: return  new Pair<BiomeType,BiomeType>(DESERT,DESERT);
		case GRASSLAND:
		case HIGHLAND:
		case SAVANNA:
		case FLOODPLAIN: return  new Pair<BiomeType,BiomeType>(GRASSLAND,GRASSLAND);
		case HIGHLANDFOREST:
		case TAIGA:
		case WOODYHILLS:
		case FOREST: return  new Pair<BiomeType,BiomeType>(FOREST,FOREST);
		case SNOW:
		case GLACIERS: return  new Pair<BiomeType,BiomeType>(SNOW,SNOW);
		case JUNGLE: return  new Pair<BiomeType,BiomeType>(JUNGLE,JUNGLE);
		case RIVER:
		case LAKE: return  new Pair<BiomeType,BiomeType>(LAKE,LAKE);
		case SHALLOWS:
		case WATER: return  new Pair<BiomeType,BiomeType>(WATER,WATER);
		case VOLCANIC:
		case MOUNTAINS: return  new Pair<BiomeType,BiomeType>(MOUNTAINS,MOUNTAINS);
		case WETLAND:
		case SALTMARSH: return  new Pair<BiomeType,BiomeType>(WETLAND,WETLAND);
		default:
			throw new IllegalArgumentException("Unrecognized BiomeType:"+this.toString());
		}
	}
	
	public int getID() {
		for(int i=0;i<values().length;i++) {
			if(values()[i]==this) return i;
		}
		throw new IllegalStateException();
	}
	public int getID(int x) {
		return this.getID()+x*values().length;
	}
	
}
