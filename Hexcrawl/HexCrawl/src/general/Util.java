package general;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import dungeon.DungeonModel;
import item.EquipmentModel;
import location.LocationModel;
import magic.MagicModel;
import monster.MonsterModel;
import names.FactionNameGenerator;
import names.InnNameGenerator;
import names.npc.HumanNameGenerator;
import npc.NPCModel;
import population.Species;
import settlement.SettlementModel;
import threat.CreatureSubtype;
import view.InfoPanel;

public class Util {
	private static final String RANDOMSTRING = "8juO5fJag55Ti6zIWB7y6CaMoQaLeF";
	//scale is roughly 3 miles divided by scalar
	private static double SCALAR_0 = 0.0001; // supercontinent scale
	private static double SCALAR_1 = 0.001; // continent scale
	private static double SCALAR_2 = 0.01; // national scale
	private static double SCALAR_3 = 0.1; // city scale
	private static final int SEED_OFFSET_FACTOR = 1000;

	public static double getSScale() {
		return SCALAR_0;
	}
	public static double getCScale() {
		return SCALAR_1;
	}
	public static double getNScale() {
		return SCALAR_2;
	}
	public static double getLScale() {
		return SCALAR_3;
	}
	public static int getOffsetX() {
		return SEED_OFFSET_FACTOR;
	}

	public static List<Point> getAdjacentPoints(Point p){
		List<Point> result = new ArrayList<Point>();
		result.add(new Point(p.x+1,p.y));
		result.add(new Point(p.x+1,p.y-1));
		result.add(new Point(p.x,p.y-1));
		result.add(new Point(p.x-1,p.y));
		result.add(new Point(p.x-1,p.y+1));
		result.add(new Point(p.x,p.y+1));
		return result;
	}

	public static List<Point> getNearbyPoints(Point p, int radius){
		List<Point> result = new ArrayList<Point>();
		for(int i=-radius;i<=radius;i++) {
			int yStart = -radius-(i<0?i:0);
			int yEnd = radius-(i>0?i:0);
			for(int j=yStart;j<=yEnd;j++) {
				result.add(new Point(p.x+i, p.y+j));
			}
		}
		result.remove(p);
		return result;
	}

	public static List<Point> getRing(Point p, int radius){
		List<Point> result = new ArrayList<Point>();
		for(int i=0;i<radius;i++) {
			result.add(new Point(p.x+radius-i,p.y+i));
			result.add(new Point(p.x-i,p.y+radius));
			result.add(new Point(p.x-radius,p.y+radius-i));
			result.add(new Point(p.x-radius+i,p.y-i));
			result.add(new Point(p.x+i,p.y-radius));
			result.add(new Point(p.x+radius,p.y-radius+i));
		}
		return result;
	}

	public static int getDist(Point p1,Point p2) {
		int dx=p1.x-p2.x;
		int dy=p1.y-p2.y;
		if(dx*dy<0) return Math.max(Math.abs(dx), Math.abs(dy));
		else return Math.abs(dx)+Math.abs(dy);
	}

	public static double symplexToPercentile(float simplex) {
		return 103.3/(1+Math.exp(-4.311*(simplex-0.01169)))-1.85;
	}
	public static float percentileToSimplex(double percentile) {
		return (float) (Math.log(103.3/(percentile+1.85)-1)/(-4.311)+0.01169);
	}

	public static Point normalizePos(Point pos,Point zero) {
		if(zero==null) zero = new Point(0,0);
		return new Point(pos.x-zero.x,zero.y-pos.y);
	}
	public static String posString(Point pos,Point zero) {
		Point p = normalizePos(pos,zero);
		return posString(p);
	}
	public static String posString(Point p) {
		return p.x+","+p.y;
	}
	public static String replace(String string,String match,String replacement) {
		if(string==null||match==null) return string;
		int i = string.indexOf(match);
		if(i>-1) return string.substring(0,i)+replacement+string.substring(i+match.length());
		else return string;
	}
	public static String formatTableResultPOS(String result,Indexible obj) {
		return formatTableResultPOS(result, obj, null, null);
	}
	public static String formatTableResultPOS(String result,Indexible obj,Point p,Point capital) {
		if(p!=null) {
			if(result.contains("${location index}")) result = Util.replace(result,"${location index}",getIndexString(obj, "location", InfoPanel.POICOUNT, p));
			if(result.contains("${npc index}")) result = Util.replace(result,"${npc index}",getIndexString(obj, "npc", InfoPanel.NPCCOUNT, p));
		}else {
			if(result.contains("${location index}")) result = Util.replace(result,"${location index}",LocationModel.getStructure(obj));
			if(result.contains("${npc index}")) result = Util.replace(result,"${npc index}",NPCModel.getJob(obj));
		}
		if(capital!=null) {
			if(result.contains("${faction index}")) result = Util.replace(result,"${faction index}",getIndexString(obj, "faction", InfoPanel.FACTIONCOUNT, p));
			if(result.contains("${district index}")) result = Util.replace(result,"${district index}",getIndexString(obj, "district", InfoPanel.DISTRICTCOUNT, p));
		}else {
			if(result.contains("${faction index}")) result = Util.replace(result,"${faction index}",FactionNameGenerator.getFaction(obj));
			if(result.contains("${district index}")) result = Util.replace(result,"${district index}",SettlementModel.getDistrict(obj));
		}
		return result;
	}

	public static String formatTableResult(String result,Indexible obj) {
		if(result.contains("${fancy color}")) result = Util.replace(result,"${fancy color}",GenericTables.getFancyColor(obj));
		if(result.contains("${basic color}")) result = Util.replace(result,"${basic color}",GenericTables.getBasicColor(obj));
		if(result.contains("${color}")) result = Util.replace(result,"${color}",GenericTables.getColor(obj));

		if(result.contains("${city theme}")) result = Util.replace(result,"${city theme}",SettlementModel.getTheme(obj));
		if(result.contains("${city activity}")) result = Util.replace(result,"${city activity}",SettlementModel.getActivity(obj));
		if(result.contains("${city event}")) result = Util.replace(result,"${city event}",SettlementModel.getEvent(obj));
		if(result.contains("${district}")) result = Util.replace(result,"${district}",SettlementModel.getDistrict(obj));
		if(result.contains("${lower class building}")) result = Util.replace(result,"${lower class building}",SettlementModel.getLCBuilding(obj));
		if(result.contains("${upper class building}")) result = Util.replace(result,"${upper class building}",SettlementModel.getUCBuilding(obj));
		if(result.contains("${building}")) result = Util.replace(result,"${building}",SettlementModel.getBuilding(obj));
		if(result.contains("${building room}")) result = Util.replace(result,"${building room}",SettlementModel.getRoom(obj));

		if(result.contains("${faction}")) result = Util.replace(result,"${faction}",FactionNameGenerator.getFaction(obj));
		if(result.contains("${faction trait}")) result = Util.replace(result,"${faction trait}",FactionNameGenerator.getTrait(obj));

		if(result.contains("${job}")) result = Util.replace(result,"${job}",NPCModel.getJob(obj));
		if(result.contains("${civilized npc}")) result = Util.replace(result,"${civilized npc}",NPCModel.getCivilized(obj));
		if(result.contains("${underworld npc}")) result = Util.replace(result,"${underworld npc}",NPCModel.getUnderworld(obj));
		if(result.contains("${wilderness npc}")) result = Util.replace(result,"${wilderness npc}",NPCModel.getWilderness(obj));
		if(result.contains("${mission}")) result = Util.replace(result,"${mission}",NPCModel.getMission(obj));
		if(result.contains("${domain}")) result = Util.replace(result,"${domain}",NPCModel.getDomain(obj));
		if(result.contains("${personality}")) result = Util.replace(result,"${personality}",NPCModel.getPersonality(obj));
		if(result.contains("${reputation}")) result = Util.replace(result,"${reputation}",NPCModel.getReputation(obj));
		if(result.contains("${method}")) result = Util.replace(result,"${method}",NPCModel.getMethod(obj));
		if(result.contains("${relationship}")) result = Util.replace(result,"${relationship}",NPCModel.getRelationship(obj));
		if(result.contains("${misfortune}")) result = Util.replace(result,"${misfortune}",NPCModel.getMisfortune(obj));
		if(result.contains("${hobby}")) result = Util.replace(result,"${hobby}",NPCModel.getHobby(obj));

		if(result.contains("${last name}")) result = Util.replace(result,"${last name}",HumanNameGenerator.getLastName(obj));
		if(result.contains("${city name}")) result = Util.replace(result,"${city name}",Species.HUMAN.getCityNameGen().getName(obj.reduceTempId(100)));

		if(result.contains("${dungeon activity}")) result = Util.replace(result,"${dungeon activity}",DungeonModel.getActivity(obj));
		if(result.contains("${dungeon room}")) result = Util.replace(result,"${dungeon room}",DungeonModel.getRoom(obj));
		if(result.contains("${dungeon form}")) result = Util.replace(result,"${dungeon form}",DungeonModel.getForm(obj));
		if(result.contains("${dungeon ruination}")) result = Util.replace(result,"${dungeon ruination}",DungeonModel.getRuination(obj));
		if(result.contains("${dungeon trick}")) result = Util.replace(result,"${dungeon trick}",DungeonModel.getTrick(obj));

		if(result.contains("${animal}")) result = Util.replace(result,"${animal}",MonsterModel.getAnimal(obj));
		if(result.contains("${aerial}")) result = Util.replace(result,"${aerial}",MonsterModel.getAerial(obj));
		if(result.contains("${terrestrial}")) result = Util.replace(result,"${terrestrial}",MonsterModel.getTerrestrial(obj));
		if(result.contains("${aquatic}")) result = Util.replace(result,"${aquatic}",MonsterModel.getAquatic(obj));
		if(result.contains("${monster feature}")) result = Util.replace(result,"${monster feature}",MonsterModel.getFeature(obj));
		if(result.contains("${monster trait}")) result = Util.replace(result,"${monster trait}",MonsterModel.getTrait(obj));
		if(result.contains("${monster ability}")) result = Util.replace(result,"${monster ability}",MonsterModel.getAbility(obj));
		if(result.contains("${monster tactic}")) result = Util.replace(result,"${monster tactic}",MonsterModel.getTactic(obj));
		if(result.contains("${monster personality}")) result = Util.replace(result,"${monster personality}",MonsterModel.getPersonality(obj));

		if(result.contains("${item}")) result = Util.replace(result,"${item}",EquipmentModel.getItem(obj));
		if(result.contains("${weapon}")) result = Util.replace(result,"${weapon}",EquipmentModel.getWeapon(obj));
		if(result.contains("${apparel}")) result = Util.replace(result,"${apparel}",EquipmentModel.getApparel(obj));
		if(result.contains("${treasure item}")) result = Util.replace(result,"${treasure item}",EquipmentModel.getTreasure(obj));
		if(result.contains("${book}")) result = Util.replace(result,"${book}",EquipmentModel.getBook(obj));
		if(result.contains("${potion}")) result = Util.replace(result,"${potion}",EquipmentModel.getPotion(obj));
		if(result.contains("${rare material}")) result = Util.replace(result,"${rare material}",EquipmentModel.getRareMaterial(obj));
		if(result.contains("${common material}")) result = Util.replace(result,"${common material}",EquipmentModel.getCommonMaterial(obj));
		if(result.contains("${material}")) result = Util.replace(result,"${material}",EquipmentModel.getMaterial(obj));
		if(result.contains("${metal}")) result = Util.replace(result,"${metal}",EquipmentModel.getMetal(obj));
		if(result.contains("${wood}")) result = Util.replace(result,"${wood}",EquipmentModel.getWood(obj));
		if(result.contains("${stone}")) result = Util.replace(result,"${stone}",EquipmentModel.getStone(obj));
		if(result.contains("${fabric}")) result = Util.replace(result,"${fabric}",EquipmentModel.getFabric(obj));

		if(result.contains("${spell}")) result = Util.replace(result,"${spell}",MagicModel.getSpell(obj));
		if(result.contains("${physical element}")) result = Util.replace(result,"${physical element}",MagicModel.getPhysicalElement(obj));
		if(result.contains("${ethereal element}")) result = Util.replace(result,"${ethereal element}",MagicModel.getEtherealElement(obj));
		if(result.contains("${element}")) result = Util.replace(result,"${element}",MagicModel.getElement(obj));
		if(result.contains("${physical effect}")) result = Util.replace(result,"${physical effect}",MagicModel.getPhysicalEffect(obj));
		if(result.contains("${ethereal effect}")) result = Util.replace(result,"${ethereal effect}",MagicModel.getEtherealEffect(obj));
		if(result.contains("${effect}")) result = Util.replace(result,"${effect}",MagicModel.getEffect(obj));
		if(result.contains("${physical form}")) result = Util.replace(result,"${physical form}",MagicModel.getPhysicalForm(obj));
		if(result.contains("${ethereal form}")) result = Util.replace(result,"${ethereal form}",MagicModel.getEtherealForm(obj));
		if(result.contains("${form}")) result = Util.replace(result,"${form}",MagicModel.getForm(obj));
		if(result.contains("${mutation}")) result = Util.replace(result,"${mutation}",MagicModel.getMutation(obj));
		if(result.contains("${insanity}")) result = Util.replace(result,"${insanity}",MagicModel.getInsanity(obj));
		if(result.contains("${omen}")) result = Util.replace(result,"${omen}",MagicModel.getOmen(obj));
		if(result.contains("${spellcaster}")) result = Util.replace(result,"${spellcaster}",MagicModel.getSpellcaster(obj));
		if(result.contains("${wizard}")) result = Util.replace(result,"${wizard}",MagicModel.getWizard(obj));
		if(result.contains("${warlock}")) result = Util.replace(result,"${warlock}",MagicModel.getWarlock(obj));
		if(result.contains("${sorcerer}")) result = Util.replace(result,"${sorcerer}",MagicModel.getSorcerer(obj));
		if(result.contains("${cleric}")) result = Util.replace(result,"${cleric}",MagicModel.getCleric(obj));
		if(result.contains("${druid}")) result = Util.replace(result,"${druid}",MagicModel.getDruid(obj));
		if(result.contains("${bard}")) result = Util.replace(result,"${bard}",MagicModel.getBard(obj));
		if(result.contains("${weirdness}")) result = Util.replace(result,"${weirdness}",MagicModel.getWeirdness(obj));

		if(result.contains("${biome}")) result = Util.replace(result,"${biome}",LocationModel.getBiome(obj));
		if(result.contains("${wilderness activity}")) result = Util.replace(result,"${wilderness activity}",LocationModel.getActivity(obj));
		if(result.contains("${edible plant}")) result = Util.replace(result,"${edible plant}",LocationModel.getEdiblePlant(obj));
		if(result.contains("${poisonous plant}")) result = Util.replace(result,"${poisonous plant}",LocationModel.getPoisonousPlant(obj));
		if(result.contains("${landmark}")) result = Util.replace(result,"${landmark}",LocationModel.getLandmark(obj));
		if(result.contains("${structure}")) result = Util.replace(result,"${structure}",LocationModel.getStructure(obj));
		if(result.contains("${hazard}")) result = Util.replace(result,"${hazard}",LocationModel.getHazard(obj));

		if(result.contains("${inn prefix}")) result = Util.replace(result,"${inn prefix}",InnNameGenerator.getPrefix(obj));
		if(result.contains("${inn suffix}")) result = Util.replace(result,"${inn suffix}",InnNameGenerator.getSuffix(obj));

		String[] encode = {"location index","npc index","faction index","district index","subtype"};
		for(String s:encode) {if(result.contains("${"+s+"}")) result = Util.replace(result,"${"+s+"}",RANDOMSTRING+s);}//encode
		if(result.contains("${")) {
			throw new IllegalStateException("Unable to process tag: "+result);
		}
		for(String s:encode) {if(result.contains(RANDOMSTRING+s)) result = Util.replace(result,RANDOMSTRING+s,"${"+s+"}");}//decode
		return result;
	}

	public static String getIndexString(Indexible obj,String type,int count,Point p) {
		int index = obj.reduceTempId(count);
		return "{"+type+":"+p.x+","+p.y+","+(index+1)+"}";
	}

	public static String formatSubtype(String string,CreatureSubtype type) {
		return Util.replace(string,"${subtype}",type.getName());
	}
	public static int[] getRemainder(int[] vals,int reduction) {
		int[] remainder = new int[vals.length-reduction];
		for(int i=0;i<remainder.length;i++) remainder[i] = vals[i+reduction];
		return remainder;
	}

	public static String toCamelCase(String st) {
		if(st==null) return st;
		StringBuilder result = new StringBuilder();
		result.append(Character.toUpperCase(st.charAt(0)));
		int start = 1;
		int space = st.indexOf(" ", start);
		while(space!=-1&&st.length()>space+1) {
			result.append(st.substring(start, space+1).toLowerCase());
			result.append(Character.toUpperCase(st.charAt(space+1)));
			start = space+2;
			space = st.indexOf(" ", start);
		}
		result.append(st.substring(start).toLowerCase());
		return result.toString();
	}

	public static int getIndexFromSimplex(float simplex) {
		return Math.abs(Float.floatToRawIntBits(simplex));
	}

	public static Object getElementFromArray(Object[] array,int val) {
		int index = val%array.length;
		if(index<0) index+=array.length;
		return array[index];
	}

	public static Object getElementFromArray(Object[] array,Indexible obj) {
		int index = obj.reduceTempId(array.length);
		return array[index];
	}
}
