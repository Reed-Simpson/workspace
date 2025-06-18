package data.threat;

import data.WeightedTable;

public class ThreatDetails {

	private static WeightedTable<String> motivations;
	private static WeightedTable<String> flaws;
	private static WeightedTable<String> plans;
	
	private static void populateMotivations() {
		motivations = new WeightedTable<String>();
		motivations.put("Chaos Agent");
		motivations.put("Immortality");
		motivations.put("Obsession");
		motivations.put("Power Hungry");
		motivations.put("Revenge");
		motivations.put("Twisted Justice");
	}
	
	public static String getMotivation(int index) {
		if(motivations==null) populateMotivations();
		return motivations.getByWeight(index);
	}
	
	private static void populateFlaws() {
		flaws = new WeightedTable<String>();
		flaws.put("Envy/Jealousy");
		flaws.put("Existential Crisis");
		flaws.put("Fanaticism");
		flaws.put("Fear/Insecurity");
	}
	
	public static String getFlaw(int index) {
		if(flaws==null) populateFlaws();
		return flaws.getByWeight(index);
	}
	
	private static void populatePlans() {
		plans = new WeightedTable<String>();
		plans.put("Concealment");
		plans.put("Conversion");
		plans.put("Desecration");
		plans.put("Destroy Community");
		plans.put("Destroy Good-Aligned Group");
		plans.put("Economic Power");
		plans.put("Evoke Catastrophic Event");
		plans.put("Food");
		plans.put("Gain Favor of Another Villain");
		plans.put("Increase Personal Capability");
		plans.put("Political Power");
		plans.put("Random Acts");
		plans.put("Reputation");
		plans.put("Subversion to the Villain's Purposes");
		plans.put("Support Evil Groups Secretly");
	}
	
	public static String getPlan(int index) {
		if(plans==null) populatePlans();
		return plans.getByWeight(index);
	}

}
