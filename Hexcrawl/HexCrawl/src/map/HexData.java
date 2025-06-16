package map;

public enum HexData {
	NONE,BIOME,ALTITUDE,PRECIPITATION,POPULATION,ECONOMY,TEMPERATURE,MAGIC,THREAT;
	
	public static HexData[] getValues() {
		return new HexData[] {BIOME,ALTITUDE,PRECIPITATION,POPULATION,ECONOMY,MAGIC,THREAT};
	}
	
	public static HexData[] getRegionTypes() {
		return new HexData[] {NONE,BIOME,POPULATION,THREAT};
	}
}
