package data.threat.subtype;

import java.util.ArrayList;

import data.Indexible;
import data.WeightedTable;
import data.population.NPCSpecies;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.CreatureType;
import names.threat.ThreatNameGenerator;

public enum AberrationType implements CreatureSubtype{
	ABOLETH(1,"ABOLETH"),
	BEHOLDER(2,"BEHOLDER"),
	SLAAD(3,"SLAAD"),
	MINDFLAYER(4,"MIND FLAYER"),
	ELDERBRAIN(5,"ELDER BRAIN"),
	ELDERBRAINDRAGON(6,"ELDER BRAIN DRAGON"),
	FEYR(7,"FEYR"),
	GRIMLOCK(8,"GRIMLOCK"),
	STARSPAWN(9,"STAR SPAWN"),
	BEHOLDERKIN(10,"Beholderkin"),
	CHUUL(11,"Chuul"),
	NOTHIC(12,"Nothic");


	private static WeightedTable<AberrationType> weights;

	private static void populateWeights() {
		weights = new WeightedTable<AberrationType>();
		weights.put(ABOLETH, 100);
		weights.put(BEHOLDER, 100);
		weights.put(SLAAD, 100);
		weights.put(ELDERBRAIN, 100);
		weights.put(ELDERBRAINDRAGON, 10);
		weights.put(GRIMLOCK, 10);
		weights.put(STARSPAWN, 10);
	}
	@Deprecated
	public static AberrationType getByWeight(int index) {
		if(weights==null) populateWeights();
		return weights.getByWeight(index);
	}
	public static AberrationType getByWeight(Indexible obj) {
		if(weights==null) populateWeights();
		return weights.getByWeight(obj);
	}
	public static AberrationType getFromID(int id) {
		id = id%AberrationType.values().length;
		if(id<0) id+=AberrationType.values().length;
		AberrationType result = null;
		for(AberrationType type:AberrationType.values()) {
			if(type.getId()==id) result=type;
		}
		return result;
	}

	private int id;
	private String name;
	private AberrationType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	@Override
	public ThreatNameGenerator getNameGen() {
		return CreatureType.ABERRATION.getNameGen();
	}
	@Override
	public Species[] getMinionSpeciesList() {
		ArrayList<Species> list = new ArrayList<Species>();
		switch(this) {
		case ELDERBRAIN:
		case ELDERBRAINDRAGON:
			list.add(MINDFLAYER);
			list.add(HumanoidType.DUREGAR);
			break;
		case GRIMLOCK:
			list.add(GRIMLOCK);
			break;
		case SLAAD:
			list.add(SLAAD);
		case STARSPAWN:
		case ABOLETH:
		case BEHOLDER:
		default:
			list.add(STARSPAWN);
			list.add(BEHOLDERKIN);
			list.add(HumanoidType.DUREGAR);
			list.add(HumanoidType.CULTIST);
			list.add(HumanoidType.SPELLCASTER);
			list.add(CHUUL);
			list.add(NPCSpecies.FISHPEOPLE);
			list.add(NOTHIC);
		}
		return list.toArray(new Species[] {});
	}

}
