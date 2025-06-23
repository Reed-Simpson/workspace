package names.threat;

import data.Indexible;
import data.WeightedTable;
import data.item.EquipmentModel;
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
	public String getName(Threat threat) {
		MonstrosityType type = (MonstrosityType) threat.getSubtype();
		switch (type) {
		case BASILISK: return BeastNameGenerator.getBeastName(threat);
		case BULETTE:return BeastNameGenerator.getBeastName(threat);
		case CHIMERA:return BeastNameGenerator.getBeastName(threat);
		case COCKATRICE:return BeastNameGenerator.getBeastName(threat);
		case DISPLACERBEAST:return BeastNameGenerator.getBeastName(threat);
		case DOPPELGANGER: return threat.getNPC().getName();
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
	private String getMimicName(Threat threat) {
		return Util.formatTableResult("${object element}", threat)+" "+EquipmentModel.getMisc(threat);
	}
}

