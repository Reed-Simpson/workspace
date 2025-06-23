package names.threat;

import data.threat.Threat;

public class AberrationNameGenerator extends ThreatNameGenerator{
	private static final String[] PART1 = {"Xylo","Vori","Glyn","Dai","Grujh'","Kthegr'","Cxo","C'thor","Oc'thel","oz'ioz","Yz'","Oda","Yox"};
	private static final String[] PART2 = {"gnath","thul","tar","odhe","xerc","ri","ggu","ved","posz","tog","irrax","olki","ulpe"};
	private static final String[] VERB = {"Siphoner of","Whisperer of","Festerer of","Distorter of","Devourer of","Contorted","Fractured","Spectre of","Maddening","Corruptor of"};
	private static final String[] NOUN = {"Dreams","Secrets","Dread","Truth","Light","Nightmare","Reality","Echoes","Beauty","Mind"};

	@Deprecated
	@Override
	public String getName(int... val) {
		if(val.length<5) throw new IllegalArgumentException("Expected 5 or more values");
		//AberrationType subtype = AberrationType.getFromID(val[0]);
		String part1 = getElementFromArray(PART1, val[2]);
		String part2 = getElementFromArray(PART2, val[3]);
		String verb = getElementFromArray(VERB, val[4]);
		String noun = getElementFromArray(NOUN, val[5]);
		return part1+part2+", The "+verb+" "+noun;
	}

	@Override
	public String getName(Threat threat) {
		String part1 = getElementFromArray(PART1, threat);
		String part2 = getElementFromArray(PART2, threat);
		String verb = getElementFromArray(VERB, threat);
		String noun = getElementFromArray(NOUN, threat);
		return part1+part2+", The "+verb+" "+noun;
	}

}
