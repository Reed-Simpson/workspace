package data.biome;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import data.Indexible;
import data.WeightedTable;
import util.Pair;
import view.Icon;

public enum BiomeType {
	VOID(Color.BLACK,null,"void",9999),
	WATER(Color.getHSBColor(240f/360f, 0.7f, 1f),BiomeModel.WAVES,"ocean",4), 
	SHALLOWS(Color.getHSBColor(220f/360f, 0.7f, 1f), BiomeModel.WAVES,"shallows",3), 
	GRASSLAND(Color.getHSBColor(100f/360f, 0.7f, 1f), BiomeModel.GRASS,"grassland",6), 
	FOREST(Color.getHSBColor(100f/360f, 1f, 0.7f), BiomeModel.TREE,"forest",8), 
	HIGHLAND(Color.getHSBColor(100f/360f, 0.7f, 0.9f), BiomeModel.GRASS,"highland",6), 
	HIGHLANDFOREST(Color.getHSBColor(100f/360f, 0.7f, 0.7f), BiomeModel.TREE,"highland forest",8), 
	SNOW(Color.WHITE, null,"snowy peaks",6),
	SALTMARSH(Color.getHSBColor(300f/360f, 0.7f, 0.5f), BiomeModel.GRASS,"salt marsh",12),
	DESERT(Color.YELLOW, BiomeModel.SAND,"desert",8),
	STEPPE(Color.ORANGE, BiomeModel.HILL,"steppe",6),
	FJORDS(Color.getHSBColor(220f/360f, 0.5f, 0.5f), BiomeModel.CLIFF,"fjords",8),
	CLIFFS(Color.GRAY, BiomeModel.CLIFF,"cliffs",8),
	BEACH(Color.YELLOW, BiomeModel.SAND,"beach",6), 
	JUNGLE(Color.GREEN, BiomeModel.TREE,"jungle",8),
	SAVANNA(Color.getHSBColor(75f/360f, 0.7f, 1f), BiomeModel.GRASS,"savanna",6),
	WOODYHILLS(Color.getHSBColor(100f/360f, 0.9f, 0.5f), BiomeModel.HILL,"wooded hills",8), 
	ROCKYHILLS(Color.getHSBColor(26f/360f, 0.9f, 0.5f), BiomeModel.HILL,"barren hills",8), 
	GLACIERS(Color.getHSBColor(220f/360f, 0.1f, 1f), null,"glacier",8), 
	SALTPAN(Color.getHSBColor(60f/360f, 0.2f, 1f), BiomeModel.SAND,"salt pan",6), 
	TAIGA(Color.getHSBColor(100f/360f, 0.7f, 0.5f), BiomeModel.TREE,"taiga",12),
	MOUNTAINS(Color.DARK_GRAY, BiomeModel.ROCK,"mountains",12),
	BADLANDS(Color.ORANGE, BiomeModel.HILL,"badlands",8),
	VOLCANIC(Color.getHSBColor(0f/360f, 1f, 0.1f), BiomeModel.ROCK,"volcanic peaks",12),
	WETLAND(Color.getHSBColor(285f/360f, 0.85f, 0.5f), BiomeModel.GRASS,"wetland",12) ,
	LAKE(Color.getHSBColor(215f/360f, 0.7f, 1f), BiomeModel.WAVES,"lake",3) ,
	FLOODPLAIN(Color.getHSBColor(165f/360f, 0.7f, 1f), BiomeModel.GRASS,"floodplain",6) ,
	RIVER(LAKE.getColor(), BiomeModel.WAVES,"river",3), 
	CITY(Color.getHSBColor(75f/360f, 0.0f, 0.9f), '\u2605',"city",1) , 
	TOWN(Color.getHSBColor(125f/360f, 0.0f, 0.5f), '\u2B24',"town",1),
	DELTA(Color.getHSBColor(200f/360f, 0.7f, 1f), BiomeModel.SAND,"delta",12) ;
	
	

	private static final String BIOMES = "Ash,Badlands,Bay,Beach,Delta,Desert,Dunes,Dustbowl,Fjords,Flood,"+
			"Forest,Glacier,Heath,Highland,Hills,Icefield,Jungle,Lowland,Mesas,Moor,Mountains,Plains"+
			"Rainforest,Riverlands,Saltpan,Savanna,Steppe,Taiga,Thickets,Tundra,Volcanic,Wetlands,Woods";
	private static WeightedTable<String> biomes;

	private static void populateAllTables() {
		biomes = new WeightedTable<String>().populate(BIOMES,",");
	}
	public static String getBiome(Indexible obj) {
		if(biomes==null) populateAllTables();
		return biomes.getByWeight(obj);
	}

	private Color color;
	private Character ch;
	private String trait;
	private String name;
	private int travel;  //days to cross 24 hexes
	
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
	
	public Pair<BiomeType,BiomeType> getHabitat(BiomeType townBiome) {
		if(townBiome!=null) {
			if(this.equals(CITY)) return new Pair<BiomeType,BiomeType>(CITY,townBiome.getHabitat(null).key1);
			else return new Pair<BiomeType,BiomeType>(townBiome.getHabitat(null).key1,CITY);
		}
		
		switch(this) {
		case VOID: return new Pair<BiomeType,BiomeType>(VOID,VOID);
		case ROCKYHILLS:return  new Pair<BiomeType,BiomeType>(ROCKYHILLS,MOUNTAINS);
		case STEPPE:return  new Pair<BiomeType,BiomeType>(ROCKYHILLS,DESERT);
		case BADLANDS:return  new Pair<BiomeType,BiomeType>(ROCKYHILLS,GRASSLAND);
		case CLIFFS:return  new Pair<BiomeType,BiomeType>(BEACH,MOUNTAINS);
		case DELTA:return  new Pair<BiomeType,BiomeType>(BEACH,LAKE);
		case FJORDS:return  new Pair<BiomeType,BiomeType>(BEACH,ROCKYHILLS);
		case BEACH: return  new Pair<BiomeType,BiomeType>(BEACH,WATER);
		case TOWN: return  new Pair<BiomeType,BiomeType>(CITY,CITY);
		case CITY: return  new Pair<BiomeType,BiomeType>(CITY,CITY);
		case SALTPAN: return  new Pair<BiomeType,BiomeType>(DESERT,null);
		case DESERT: return  new Pair<BiomeType,BiomeType>(DESERT,DESERT);
		case GRASSLAND:return  new Pair<BiomeType,BiomeType>(GRASSLAND,GRASSLAND);
		case HIGHLAND:return  new Pair<BiomeType,BiomeType>(GRASSLAND,ROCKYHILLS);
		case SAVANNA:return  new Pair<BiomeType,BiomeType>(GRASSLAND,DESERT);
		case FLOODPLAIN: return  new Pair<BiomeType,BiomeType>(GRASSLAND,WETLAND);
		case HIGHLANDFOREST:return  new Pair<BiomeType,BiomeType>(FOREST,ROCKYHILLS);
		case TAIGA:return  new Pair<BiomeType,BiomeType>(FOREST,SNOW);
		case WOODYHILLS:return  new Pair<BiomeType,BiomeType>(FOREST,ROCKYHILLS);
		case FOREST: return  new Pair<BiomeType,BiomeType>(FOREST,FOREST);
		case SNOW: return  new Pair<BiomeType,BiomeType>(SNOW,MOUNTAINS);
		case GLACIERS: return  new Pair<BiomeType,BiomeType>(SNOW,MOUNTAINS);
		case JUNGLE: return  new Pair<BiomeType,BiomeType>(JUNGLE,FOREST);
		case RIVER: return  new Pair<BiomeType,BiomeType>(LAKE,LAKE);
		case LAKE: return  new Pair<BiomeType,BiomeType>(LAKE,LAKE);
		case SHALLOWS: return  new Pair<BiomeType,BiomeType>(WATER,WATER);
		case WATER: return  new Pair<BiomeType,BiomeType>(WATER,WATER);
		case VOLCANIC: return  new Pair<BiomeType,BiomeType>(MOUNTAINS,MOUNTAINS);
		case MOUNTAINS: return  new Pair<BiomeType,BiomeType>(MOUNTAINS,SNOW);
		case WETLAND: return  new Pair<BiomeType,BiomeType>(WETLAND,LAKE);
		case SALTMARSH: return  new Pair<BiomeType,BiomeType>(WETLAND,WATER);
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
		return this.getID()+x*count();
	}
	public static int count() {
		return values().length;
	}
	

	public List<Icon> getIcons() {
		List<Icon> result = new ArrayList<Icon>();
		Character c = this.getCh();
		Point offset;
		if(BiomeType.CITY.getCh().equals(c)) {
			offset = new Point(-80,60);
			result.add( new Icon(c, offset, Color.black,2,1f,true));
		}else if(BiomeType.WATER.getCh().equals(c)) {
			offset = new Point(-80,10);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,true));
			offset = new Point(-30,60);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,true));
			offset = new Point(10,-10);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,true));
		}else if(BiomeType.ROCKYHILLS.getCh().equals(c)) {
			offset = new Point(-20,20);
			result.add( new Icon(c, offset, Color.darkGray,1,0.8f,false));
			offset = new Point(40,-20);
			result.add( new Icon(c, offset, Color.darkGray,1,0.8f,false));
		}else if(BiomeType.CLIFFS.getCh().equals(c)) {
			offset = new Point(-60,0);
			result.add( new Icon(c, offset, Color.darkGray,0.7,1f,false));
			offset = new Point(-20,40);
			result.add( new Icon(c, offset, Color.darkGray,0.7,1f,false));
			offset = new Point(20,0);
			result.add( new Icon(c, offset, Color.darkGray,0.7,1f,false));
		}else if(BiomeType.MOUNTAINS.getCh().equals(c)) {
			offset = new Point(-30,30);
			result.add( new Icon(c, offset, Color.black,1,0.5f,true));
			offset = new Point(-70,40);
			result.add( new Icon(c, offset, Color.black,0.8,0.5f,true));
		}else if(BiomeType.DESERT.getCh().equals(c)) {
			offset = new Point(-60,40);
			result.add( new Icon(c, offset, Color.darkGray,1,0.5f,false));
			offset = new Point(-20,60);
			result.add( new Icon(c, offset, Color.darkGray,1,0.5f,false));
			offset = new Point(20,20);
			result.add( new Icon(c, offset, Color.darkGray,1,0.5f,false));
		}else if(BiomeType.FOREST.getCh().equals(c))  {
			offset = new Point(-60,20);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,true));
			offset = new Point(-20,40);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,true));
			offset = new Point(20,0);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,true));
		}else if(BiomeType.SALTMARSH.equals(this)||BiomeType.WETLAND.equals(this))   {
			offset = new Point(-60,20);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.9f,false));
			offset = new Point(-20,40);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.9f,false));
			offset = new Point(20,0);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.9f,false));
		}else {
			offset = new Point(-60,20);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,false));
			offset = new Point(-20,40);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,false));
			offset = new Point(20,0);
			result.add( new Icon(c, offset, Color.darkGray,0.7,0.5f,false));
		}
		return result;
	}
	
}
