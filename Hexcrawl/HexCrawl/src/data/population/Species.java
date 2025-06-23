package data.population;

import data.Indexible;
import names.IndexibleNameGenerator;
import names.NameGenerator;
import names.city.DragonbornCityNameGenerator;
import names.city.DwarfCityNameGenerator;
import names.city.ElfCityNameGenerator;
import names.city.GenasiCityNameGenerator;
import names.city.GnomeCityNameGenerator;
import names.city.GoliathCityNameGenerator;
import names.city.HalflingCityNameGenerator;
import names.city.HumanCityNameGenerator;
import names.city.OrcCityNameGenerator;
import names.npc.DragonbornNameGenerator;
import names.npc.DwarfNameGenerator;
import names.npc.ElfNameGenerator;
import names.npc.GenasiNameGenerator;
import names.npc.GnomeNameGenerator;
import names.npc.GoliathNameGenerator;
import names.npc.HalflingNameGenerator;
import names.npc.HumanNameGenerator;
import names.npc.OrcNameGenerator;
import util.Util;

public enum Species {
	DRAGONBORN(	6,0.5f,0f/360f,	new DragonbornCityNameGenerator(),	new DragonbornNameGenerator(),	1.0f),
	DWARF(		5,0.5f,30f/360f,new DwarfCityNameGenerator(),		new DwarfNameGenerator(),		1.0f),
	HUMAN(		1,0.0f,60f/360f,new HumanCityNameGenerator(),		new HumanNameGenerator(),		1.0f),
	HALFLING(	2,0.0f,90f/360f,new HalflingCityNameGenerator(),	new HalflingNameGenerator(),	1.0f),
	ELF(		4,0.5f,120f/360f,new ElfCityNameGenerator(),		new ElfNameGenerator(),			1.0f),
	GENASI(		9,0.0f,180f/360f,new GenasiCityNameGenerator(),		new GenasiNameGenerator(),		1.0f),
	GNOME(		3,0.2f,270f/360f,new GnomeCityNameGenerator(),		new GnomeNameGenerator(),		0.8f),
	ORC(		8,0.8f,330f/360f,new OrcCityNameGenerator(),		new OrcNameGenerator(),			1.0f),
	GOLIATH(	7,0.8f,300f/360f,new GoliathCityNameGenerator(),	new GoliathNameGenerator(),		0.8f),
	GOBLINOID(	11,1.0f,240f/360f,ORC.getCityNameGen(),				ORC.getNPCNameGen(),			1.0f),
	KOBOLD(		12,1.0f,180f/360f,ORC.getCityNameGen(),				ORC.getNPCNameGen(),			0.5f),
	LIZARDFOLK(	13,1.0f,150f/360f,ORC.getCityNameGen(),				ORC.getNPCNameGen(),			0.5f),
	GNOLL(		14,1.0f,150f/360f,ORC.getCityNameGen(),				ORC.getNPCNameGen(),			0.5f),
	TROG(		15,1.0f,150f/360f,ORC.getCityNameGen(),				ORC.getNPCNameGen(),			0.1f),
	AASIMAR(	16,0.0f,60f/360f,HUMAN.getCityNameGen(),			HUMAN.getNPCNameGen(),			0.01f),
	TIEFLING(	17,0.0f,60f/360f,HUMAN.getCityNameGen(),			HUMAN.getNPCNameGen(),			0.05f),
	OTHER(		10,0.0f,60f/360f,HUMAN.getCityNameGen(),			HUMAN.getNPCNameGen(),			1.0f);

	private static final String[] GOBLINOIDS = {"Goblin","Hobgoblin","Bugbear"};
	public static String getString(Species species, Indexible obj) {
		if(GOBLINOID.equals(species)) return GOBLINOIDS[obj.reduceTempId(GOBLINOIDS.length)];
		else return Util.toCamelCase(species.name());
	}
	
	private float isolationFactor;
	private float hue;
	private NameGenerator cityNames;
	private int index;
	private IndexibleNameGenerator npcNames;
	private float weight;
	
	private Species(int index,float isolationFactor,float hue,NameGenerator cityNames,IndexibleNameGenerator npcNames,float weight) {
		this.index = index;
		this.isolationFactor=isolationFactor;
		this.hue = hue;
		this.cityNames = cityNames;
		this.npcNames = npcNames;
		this.weight = weight;
	}

	public float getIsolationFactor() {
		return this.isolationFactor;
	}
	public float getHue() {
		return this.hue;
	}
	public NameGenerator getCityNameGen() {
		return cityNames;
	}
	public IndexibleNameGenerator getNPCNameGen() {
		return npcNames;
	}
	public int getIndex() {
		return index;
	}
	
	public static Species[] getFaerunSpecies() {
		return new Species[]{HUMAN,HALFLING,DWARF,DRAGONBORN,ORC,GOBLINOID,AASIMAR,TIEFLING,ELF,GNOME};
	}
	public static Species[] getAbeirSpecies() {
		return new Species[]{HUMAN,HALFLING,DWARF,DRAGONBORN,ORC,GOBLINOID,AASIMAR,TIEFLING,GENASI,GOLIATH};
	}
	public static Species[] getAbeirNPCSpecies() {
		return new Species[]{HUMAN,HALFLING,DWARF,DRAGONBORN,ORC,GOBLINOID,AASIMAR,TIEFLING,GENASI,GOLIATH,GOBLINOID,KOBOLD,LIZARDFOLK,GNOLL,TROG,OTHER};
	}

	public static Species getFromId(int id) {
		Species result = null;
		for(Species type:Species.values()) {
			if(type.getIndex()==id) result=type;
		}
		return result;
	}

	public float getWeight() {
		return weight;
	}
	
}
