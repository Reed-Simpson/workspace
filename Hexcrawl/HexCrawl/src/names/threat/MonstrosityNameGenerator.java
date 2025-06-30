package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.item.EquipmentModel;
import data.npc.Creature;
import data.npc.NPC;
import data.threat.Threat;
import data.threat.subtype.MonstrosityType;
import util.Util;

public class MonstrosityNameGenerator extends ThreatNameGenerator {
	private static final String GREEKNAMES = "rambris,madrantos,chraispetus,xenthonos,cheudaon,vuthaenon,zurrerion,risetus,kosoeis,kusnates,"
			+ "xume,cheza,chrala,sthoste,thispe,sthodryne,kolosy,masnama,phaspare,dyldates,chusnanes,vosous,thuzon,phirraemon,chronos,dyrdoteus,chredis,phanous,huderion";
	private static WeightedTable<String> greeknames;
	private static final String EGYPTIANNAMES = "Api,Besa,Sa-pthah,Ra-s-hotep,Seti,Khatiuer,Rhampsinitus,Atefaamen,Eate,Horirem,Is-artais,Kartek,Pet-amen,Nen-sala,Katesch,Manet-ankh,"
			+ "Tikar,Mer-ankhes,Ta-kha,Bakat";
	private static WeightedTable<String> egyptiannames;
	private static final String SNAKENAMES = "chizsal,shashir,crelsaakkass,ircecshal,ilsoree,ahas,jyama,otralu,ushnihas,ayanshtripas,"
			+ "nulshih,shahu,zsuszuis,eszi,zhultlies,ssehtli,sheztahlie,sshussehlu,sotsosi,ssaltloyu";
	private static WeightedTable<String> snakenames;

	private static final String FACTION_ADJECTIVES = "";
	private static final String FACTION_NOUNS = "";
	private static WeightedTable<String> faction_adjectives;
	private static WeightedTable<String> faction_nouns;

	private static void populateAllTables() {
		faction_adjectives = new WeightedTable<String>();
		populate(faction_adjectives,FACTION_ADJECTIVES,",");
		faction_nouns = new WeightedTable<String>();
		populate(faction_nouns,FACTION_NOUNS,",");
	}
	public static void populateAll() {
		greeknames = new WeightedTable<String>();
		populate(greeknames, GREEKNAMES, ",");
		egyptiannames = new WeightedTable<String>();
		populate(egyptiannames, EGYPTIANNAMES, ",");
		snakenames = new WeightedTable<String>();
		populate(snakenames, SNAKENAMES, ",");
	}
	public static String getGreekName(Indexible obj) {
		if(greeknames==null) populateAll();
		return greeknames.getByWeight(obj);
	}
	public static String getEgyptianName(Indexible obj) {
		if(egyptiannames==null) populateAll();
		return egyptiannames.getByWeight(obj);
	}
	public static String getSnakeName(Indexible obj) {
		if(snakenames==null) populateAll();
		return snakenames.getByWeight(obj);
	}

	@Deprecated
	@Override
	public String getName(int... val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName(Creature threat) {
		NPC npc;
		if(threat instanceof Threat) {
			npc = ((Threat)threat).getNPC();
		}else {
			npc = (NPC)threat;
		}
		MonstrosityType type = (MonstrosityType) threat.getSpecies();
		switch (type) {
		case BASILISK: return BeastNameGenerator.getBeastName(threat);
		case BULETTE:return BeastNameGenerator.getBeastName(threat);
		case CHIMERA:return BeastNameGenerator.getBeastName(threat);
		case COCKATRICE:return BeastNameGenerator.getBeastName(threat);
		case DISPLACERBEAST:return BeastNameGenerator.getBeastName(threat);
		case DOPPELGANGER: return npc.getName();
		case GRIFFON:return BeastNameGenerator.getBeastName(threat);
		case HARPY:return BeastNameGenerator.getBeastName(threat);
		case HYDRA:return BeastNameGenerator.getBeastName(threat);
		case LAMIA:return getEgyptianName(threat);
		case MANTICORE:return BeastNameGenerator.getBeastName(threat);
		case MEDUSA:return getGreekName(threat);
		case MIMIC:return getMimicName(threat);
		case MINOTAUR:return getGreekName(threat);
		case NAGA:return getSnakeName(threat);
		case OWLBEAR:return BeastNameGenerator.getBeastName(threat);
		case PHASESPIDER:return BeastNameGenerator.getBeastName(threat);
		case PURPLEWORM:return BeastNameGenerator.getBeastName(threat);
		case REMORHAZ:return BeastNameGenerator.getBeastName(threat);
		case RUSTMONSTER:return BeastNameGenerator.getBeastName(threat);
		case SPHINX:return getEgyptianName(threat);
		case TERRASQUE:return "The Terrasque";
		case YUANTI:return getSnakeName(threat);
		default: throw new IllegalArgumentException("Unrecognized subtype: "+type);
		}
	}
	private String getMimicName(Indexible threat) {
		return Util.formatTableResult("${object element}", threat)+" "+EquipmentModel.getMisc(threat);
	}
	@Override
	public String getFactionAdjective(Indexible threat) {
		if(faction_adjectives==null) populateAllTables();
		return faction_adjectives.getByWeight(threat);
	}

	@Override
	public String getFactionNoun(Indexible threat) {
		if(faction_nouns==null) populateAllTables();
		return faction_nouns.getByWeight(threat);
	}
}

