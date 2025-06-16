package threat;

import java.awt.Color;

import general.WeightedTable;
import names.threat.AberrationNameGenerator;
import names.threat.BeastNameGenerator;
import names.threat.CelestialNameGenerator;
import names.threat.ConstructNameGenerator;
import names.threat.DragonNameGenerator;
import names.threat.ElementalNameGenerator;
import names.threat.FeyNameGenerator;
import names.threat.FiendNameGenerator;
import names.threat.GiantNameGenerator;
import names.threat.HumanoidNameGenerator;
import names.threat.MonstrosityNameGenerator;
import names.threat.OozeNameGenerator;
import names.threat.PlantNameGenerator;
import names.threat.ThreatNameGenerator;
import names.threat.UndeadNameGenerator;
import threat.subtype.AberrationType;
import threat.subtype.BeastType;
import threat.subtype.CelestialType;
import threat.subtype.ConstructType;
import threat.subtype.DragonType;
import threat.subtype.ElementalType;
import threat.subtype.FeyType;
import threat.subtype.FiendType;
import threat.subtype.GiantType;
import threat.subtype.HumanoidType;
import threat.subtype.MonstrosityType;
import threat.subtype.OozeType;
import threat.subtype.PlantType;
import threat.subtype.UndeadType;

public enum CreatureType {
	DRAGON(1,new DragonNameGenerator(),Color.getHSBColor(25f/360f, 1.0f, 1.0f)), 
	ELEMENTAL(2,new ElementalNameGenerator(),Color.getHSBColor(50f/360f, 1.0f, 1.0f)),
	HUMANOID(3,new HumanoidNameGenerator(),Color.getHSBColor(75f/360f, 1.0f, 1.0f)),
	UNDEAD(4,new UndeadNameGenerator(),Color.getHSBColor(280f/360f, 1.0f, 0.5f)),
	ABERRATION(5,new AberrationNameGenerator(),Color.getHSBColor(300f/360f, 1.0f, 1.0f)),
	FIEND(6,new FiendNameGenerator(),Color.getHSBColor(330f/360f, 1.0f, 0.5f)),
	CELESTIAL(7,new CelestialNameGenerator(),Color.getHSBColor(175f/360f, 0.4f, 1.0f)),
	BEAST(8,new BeastNameGenerator(),Color.getHSBColor(125f/360f, 1.0f, 0.6f)),
	MONSTROSITY(9,new MonstrosityNameGenerator(),Color.getHSBColor(180f/360f, 1.0f, 0.8f)),
	OOZE(10,new OozeNameGenerator(),Color.getHSBColor(85f/360f, 1.0f, 1.0f)),
	PLANT(11,new PlantNameGenerator(),Color.getHSBColor(275f/360f, 0.8f, 0.8f)),
	CONSTRUCT(12,new ConstructNameGenerator(),Color.getHSBColor(37f/360f, 0.0f, 0.4f)),
	FEY(13,new FeyNameGenerator(),Color.getHSBColor(100f/360f, 1.0f, 1.0f)),
	GIANT(14,new GiantNameGenerator(),Color.getHSBColor(210f/360f, 1.0f, 1.0f));

	private static WeightedTable<CreatureType> weights;
	private int id;
	private ThreatNameGenerator names;
	private Color color;

	private CreatureType(int id,ThreatNameGenerator names,Color color) {
		this.id = id;
		this.names = names;
		this.color = color;
	}
	
	private static void populateWeights() {
		weights = new WeightedTable<CreatureType>();
		weights.put(DRAGON, 100);
		weights.put(ELEMENTAL, 100);
		weights.put(HUMANOID, 20);
		weights.put(UNDEAD, 20);
		weights.put(ABERRATION, 20);
		weights.put(FIEND, 20);
		weights.put(MONSTROSITY, 4);
		weights.put(CELESTIAL, 1);
		weights.put(BEAST, 1);
		weights.put(OOZE, 1);
		weights.put(PLANT, 1);
		weights.put(CONSTRUCT, 1);
		weights.put(FEY, 0);
		weights.put(GIANT, 1);
	}
	public static CreatureType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public static CreatureSubtype getSubtypeByWeight(CreatureType type,int index) {
		CreatureSubtype result = null;
		switch(type) {
		case DRAGON: result=DragonType.getByWeight(index); break;
		case ELEMENTAL: result=ElementalType.getByWeight(index); break;
		case HUMANOID: result=HumanoidType.getByWeight(index); break;
		case UNDEAD: result=UndeadType.getByWeight(index); break;
		case ABERRATION: result=AberrationType.getByWeight(index); break;
		case FIEND: result=FiendType.getByWeight(index); break;
		case CELESTIAL: result=CelestialType.getByWeight(index); break;
		case BEAST: result=BeastType.getByWeight(index); break;
		case MONSTROSITY: result=MonstrosityType.getByWeight(index); break;
		case OOZE: result=OozeType.getByWeight(index); break;
		case PLANT: result=PlantType.getByWeight(index); break;
		case CONSTRUCT: result=ConstructType.getByWeight(index); break;
		case FEY: result=FeyType.getByWeight(index); break;
		case GIANT: result=GiantType.getByWeight(index); break;
		}
		return result;
	}


	public ThreatNameGenerator getNameGenerator() {
		return names;
	}
	public int getId() {
		return this.id;
	}
	public static String getName(Threat threat,int... index) {
		ThreatNameGenerator gen = threat.getType().getNameGenerator();
		if(gen==null) return null;
		return gen.getName(threat,index);
	}
	public static CreatureType getFromID(int id) {
		id = id%CreatureType.values().length;
		if(id<0) id+=CreatureType.values().length;
		CreatureType result = null;
		for(CreatureType type:CreatureType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}

}
