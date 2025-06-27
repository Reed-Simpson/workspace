package data;

public enum HexData {
	NONE("none"),
	BIOME("biome"),
	ALTITUDE("altitude"),
	PRECIPITATION("precipitation"),
	POPULATION("population"),
	ECONOMY("economy"),
	TEMPERATURE("temperature"),
	MAGIC("magic"),
	THREAT("threat"),

	ENCOUNTER("encounter"),
	NPC("npc"),
	LOCATION("location"),
	DUNGEON("dungeon"),
	D_ENCOUNTER("d.encounter"),
	FACTION("faction"),
	FAITH("faith"),
	DISTRICT("district"),
	CITY("city"),
	TOWN("town"),
	CHARACTER("character"),
	THREAD("thread"), 
	MINION("minion");
	
	public static HexData[] getMapViews() {
		return new HexData[] {BIOME,ALTITUDE,PRECIPITATION,POPULATION,ECONOMY,MAGIC,THREAT};
	}
	public static HexData[] getRegionTypes() {
		return new HexData[] {NONE,BIOME,POPULATION,THREAT};
	}
	public static HexData get(String text) {
		for(HexData t:HexData.values()) {
			if(t.text.equals(text)) return t;
		}
		throw new IllegalArgumentException("DataType not found: "+text);
	}
	


	private String text;
	
	private HexData(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}

	
}
