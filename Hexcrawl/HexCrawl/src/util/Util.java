package util;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import data.GenericTables;
import data.Indexible;
import data.Reference;
import data.biome.BiomeType;
import data.dungeon.DungeonModel;
import data.encounters.EncounterModel;
import data.item.EquipmentModel;
import data.location.LocationModel;
import data.location.LocationType;
import data.magic.MagicModel;
import data.mission.MissionModel;
import data.monster.MonsterModel;
import data.npc.NPCJobType;
import data.npc.NPCModel;
import data.population.NPCSpecies;
import data.population.SettlementModel;
import data.population.Species;
import data.threat.CreatureSubtype;
import data.threat.subtype.DragonType;
import data.threat.subtype.ElementalType;
import names.FactionNameGenerator;
import names.FactionType;
import names.InnNameGenerator;
import view.InfoPanel;

public class Util {
	private static final String RANDOMSTRING = "8juO5fJag55Ti6zIWB7y6CaMoQaLeF";
	//scale is roughly 3 miles divided by scalar
	private static double SCALAR_0 = 0.0001; // supercontinent scale
	private static double SCALAR_1 = 0.001; // continent scale
	private static double SCALAR_2 = 0.01; // national scale
	private static double SCALAR_3 = 0.1; // city scale
	private static double SCALAR_4 = 1; // micro scale
	private static final int SEED_OFFSET_FACTOR = 10000;

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
	public static double getMScale() {
		return SCALAR_4;
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
	public static Point denormalizePos(Point pos,Point zero) {
		if(zero==null) zero = new Point(0,0);
		return new Point(pos.x+zero.x,zero.y-pos.y);
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
		while(i>-1) {
			string = string.substring(0,i)+replacement+string.substring(i+match.length());
			i = string.indexOf(match);
		}
		return string;
	}

	public static String getTownIndex(Indexible obj,Point p,Point zero) {
		Point displayPos = normalizePos(p, zero);
		return getRandomReference(obj, "town", 1, displayPos,true).toString();
	}

	public static String formatTableResultPOS(String result,Indexible obj) {
		return formatTableResultPOS(result, obj, null,null);
	}
	public static String formatTableResultPOS(String result,Indexible obj,Point p,Point zero) {
		if(p!=null) {
			Point displayPos = normalizePos(p, zero);
			if(result.contains("${location index}")) result = Util.replace(result,"${location index}",getRandomReference(obj, "location", InfoPanel.POICOUNT, displayPos,true).toString());
			if(result.contains("${npc index}")) result = Util.replace(result,"${npc index}",getRandomReference(obj, "npc", InfoPanel.NPCCOUNT, displayPos,true).toString());
			if(result.contains("${faction index}")) result = Util.replace(result,"${faction index}",getRandomReference(obj, "faction", InfoPanel.FACTIONCOUNT, displayPos,true).toString());
			if(result.contains("${faith index}")) result = Util.replace(result,"${faith index}",getRandomReference(obj, "faith", InfoPanel.FAITHCOUNT, displayPos,true).toString());
			if(result.contains("${district index}")) result = Util.replace(result,"${district index}",getRandomReference(obj, "district", InfoPanel.DISTRICTCOUNT, displayPos,true).toString());
			if(result.contains("${character index}")) {
				if(obj.reduceTempId(2)%2==0) result = Util.replace(result,"${character index}",getRandomReference(obj, "npc", InfoPanel.NPCCOUNT, displayPos,true).toString());
				else result = Util.replace(result,"${character index}",getRandomReference(obj, "faction", InfoPanel.FACTIONCOUNT, displayPos,true).toString());
			}
			if(result.contains("${town index}")) result = Util.replace(result,"${town index}",getRandomReference(obj, "town", 1, displayPos,false).toString());
		}else {
			if(result.contains("${location index}")) result = Util.replace(result,"${location index}",LocationType.getStructure(obj).toString());
			if(result.contains("${npc index}")) result = Util.replace(result,"${npc index}",NPCJobType.getJob(obj).toString());
			if(result.contains("${faction index}")) result = Util.replace(result,"${faction index}",FactionType.getFaction(obj).toString());
			if(result.contains("${faith index}")) result = Util.replace(result,"${faith index}",NPCModel.getDomain(obj));
			if(result.contains("${district index}")) result = Util.replace(result,"${district index}",SettlementModel.getDistrict(obj));
			if(result.contains("${character index}")) {
				if(obj.reduceTempId(2)%2==0) result = Util.replace(result,"${character index}",NPCJobType.getJob(obj).toString());
				else result = Util.replace(result,"${character index}",FactionType.getFaction(obj).toString());
			}
			if(result.contains("${town index}")) result = Util.replace(result,"${town index}",NPCSpecies.GOLIATH.getCityNameGen().getName(obj));
		}
		return result;
	}
	public static Reference getReferenceForIndex(String string,Indexible obj,Point p,Point zero) {
		Reference result = null;
		Point displayPos = normalizePos(p, zero);
		if(string.contains("${location index}")) result = getRandomReference(obj, "location", InfoPanel.POICOUNT, displayPos,true);
		if(string.contains("${npc index}")) result = getRandomReference(obj, "npc", InfoPanel.NPCCOUNT, displayPos,true);
		if(string.contains("${faction index}")) result = getRandomReference(obj, "faction", InfoPanel.FACTIONCOUNT, displayPos,true);
		if(string.contains("${faith index}")) result = getRandomReference(obj, "faith", InfoPanel.FAITHCOUNT, displayPos,true);
		if(string.contains("${district index}")) result = getRandomReference(obj, "district", InfoPanel.DISTRICTCOUNT, displayPos,true);
		if(string.contains("${character index}")) {
			if(obj.reduceTempId(2)%2==0) result = getRandomReference(obj, "npc", InfoPanel.NPCCOUNT, displayPos,true);
			else result = getRandomReference(obj, "faction", InfoPanel.FACTIONCOUNT, displayPos,true);
		}
		if(string.contains("${town index}")) result = getRandomReference(obj, "town", 1, displayPos,true);
		return result;
	}

	public static String formatTableResult(String string,Indexible obj) {
		String result = string;
		if(result.contains("${fancy color}")) result = Util.replace(result,"${fancy color}",GenericTables.getFancyColor(obj));
		if(result.contains("${basic color}")) result = Util.replace(result,"${basic color}",GenericTables.getBasicColor(obj));
		if(result.contains("${color}")) result = Util.replace(result,"${color}",GenericTables.getColor(obj));
		if(result.contains("${basic material}")) result = Util.replace(result,"${basic material}",GenericTables.getBasicMaterial(obj));

		if(result.contains("${city theme}")) result = Util.replace(result,"${city theme}",SettlementModel.getTheme(obj));
		if(result.contains("${city activity}")) result = Util.replace(result,"${city activity}",SettlementModel.getActivity(obj));
		if(result.contains("${city event}")) result = Util.replace(result,"${city event}",SettlementModel.getEvent(obj));
		if(result.contains("${district}")) result = Util.replace(result,"${district}",SettlementModel.getDistrict(obj));
		if(result.contains("${building room}")) result = Util.replace(result,"${building room}",SettlementModel.getRoom(obj));
		
		if(result.contains("${lower class building}")) result = Util.replace(result,"${lower class building}",LocationType.getLCBuilding(obj).toString());
		if(result.contains("${upper class building}")) result = Util.replace(result,"${upper class building}",LocationType.getUCBuilding(obj).toString());
		if(result.contains("${building}")) result = Util.replace(result,"${building}",LocationType.getBuilding(obj).toString());
		if(result.contains("${landmark}")) result = Util.replace(result,"${landmark}",LocationType.getLandmark(obj).toString());
		if(result.contains("${structure}")) result = Util.replace(result,"${structure}",LocationType.getStructure(obj).toString());
		if(result.contains("${location}")) result = Util.replace(result,"${location}",LocationType.getStructureOrLandmark(obj).toString());

		if(result.contains("${faction}")) result = Util.replace(result,"${faction}",FactionType.getFaction(obj).toString());
		if(result.contains("${faction trait}")) result = Util.replace(result,"${faction trait}",FactionNameGenerator.getTrait(obj));

		if(result.contains("${job}")) result = Util.replace(result,"${job}",NPCJobType.getJob(obj).toString());
		if(result.contains("${civilized npc}")) result = Util.replace(result,"${civilized npc}",NPCJobType.getCivilized(obj).toString());
		if(result.contains("${underworld npc}")) result = Util.replace(result,"${underworld npc}",NPCJobType.getUnderworld(obj).toString());
		if(result.contains("${wilderness npc}")) result = Util.replace(result,"${wilderness npc}",NPCJobType.getWilderness(obj).toString());
		if(result.contains("${domain}")) result = Util.replace(result,"${domain}",NPCModel.getDomain(obj));
		if(result.contains("${personality}")) result = Util.replace(result,"${personality}",NPCModel.getPersonality(obj));
		if(result.contains("${reputation}")) result = Util.replace(result,"${reputation}",NPCModel.getReputation(obj));
		if(result.contains("${method}")) result = Util.replace(result,"${method}",NPCModel.getMethod(obj));
		if(result.contains("${relationship}")) result = Util.replace(result,"${relationship}",NPCModel.getRelationship(obj));
		if(result.contains("${misfortune}")) result = Util.replace(result,"${misfortune}",NPCModel.getMisfortune(obj));
		if(result.contains("${hobby}")) result = Util.replace(result,"${hobby}",NPCModel.getHobby(obj));
		if(result.contains("${bodypart}")) result = Util.replace(result,"${bodypart}",NPCModel.getBodypart(obj));

		if(result.contains("${mission}")) result = Util.replace(result,"${mission}",MissionModel.getMissionVerb(obj));

		//if(result.contains("${last name}")) result = Util.replace(result,"${last name}",HumanNameGenerator.getLastName(obj));
		if(result.contains("${city name}")) result = Util.replace(result,"${city name}",NPCSpecies.HUMAN.getCityNameGen().getName(obj));

		if(result.contains("${dungeon activity}")) result = Util.replace(result,"${dungeon activity}",DungeonModel.getActivity(obj));
		if(result.contains("${dungeon room}")) result = Util.replace(result,"${dungeon room}",DungeonModel.getRoom(obj));
		if(result.contains("${dungeon form}")) result = Util.replace(result,"${dungeon form}",DungeonModel.getForm(obj));
		if(result.contains("${dungeon layout}")) result = Util.replace(result,"${dungeon layout}",DungeonModel.getLayout(obj));
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
		if(result.contains("${item trait}")) result = Util.replace(result,"${item trait}",EquipmentModel.getTrait(obj));

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
		if(result.contains("${artificer}")) result = Util.replace(result,"${artificer}",MagicModel.getArtificer(obj));
		if(result.contains("${weirdness}")) result = Util.replace(result,"${weirdness}",MagicModel.getWeirdness(obj));

		if(result.contains("${biome}")) result = Util.replace(result,"${biome}",BiomeType.getBiome(obj));
		
		if(result.contains("${wilderness activity}")) result = Util.replace(result,"${wilderness activity}",LocationModel.getActivity(obj));
		if(result.contains("${edible plant}")) result = Util.replace(result,"${edible plant}",LocationModel.getEdiblePlant(obj));
		if(result.contains("${poisonous plant}")) result = Util.replace(result,"${poisonous plant}",LocationModel.getPoisonousPlant(obj));
		if(result.contains("${hazard}")) result = Util.replace(result,"${hazard}",LocationModel.getHazard(obj));

		if(result.contains("${inn prefix}")) result = Util.replace(result,"${inn prefix}",InnNameGenerator.getPrefix(obj));
		if(result.contains("${inn suffix}")) result = Util.replace(result,"${inn suffix}",InnNameGenerator.getSuffix(obj));

		if(result.contains("${object element}")) result = Util.replace(result,"${object element}",EncounterModel.getObj(obj));
		if(result.contains("${character element}")) result = Util.replace(result,"${character element}",EncounterModel.getChar(obj));

		if(result.contains("${elemental type}")) result = Util.replace(result,"${elemental type}",ElementalType.getByWeight(obj).toString());
		if(result.contains("${dragon type}")) result = Util.replace(result,"${dragon type}",DragonType.getByWeight(obj).toString());

		if(!result.equals(string)) {
			result = formatTableResult(result, obj);
		}else {
			String[] encode = {"location index","npc index","faction index","district index","subtype","town index","placeholder domain","faith index","job placeholder","last name"};
			for(String s:encode) {if(result.contains("${"+s+"}")) result = Util.replace(result,"${"+s+"}",RANDOMSTRING+s);}//encode
			if(result.contains("${")) {
				throw new IllegalStateException("Unable to process tag: "+result);
			}
			for(String s:encode) {if(result.contains(RANDOMSTRING+s)) result = Util.replace(result,RANDOMSTRING+s,"${"+s+"}");}//decode
		}
		return result;
	}

	public static Reference getRandomReference(Indexible obj,String type,int count,Point p,boolean active) {
		int index = obj.reduceTempId(count);
		Reference reference = new Reference("{"+type+":"+p.x+","+p.y+","+(index+1)+"}$");
		reference.setActive(active);
		return reference;
	}

	public static String formatSubtype(String string,CreatureSubtype type) {
		return Util.replace(string,"${subtype}",type.getSpeciesName());
	}
	public static String formatSpecies(String string, Species species) {
		return Util.replace(string,"${species}",species.toString());
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

	public static String parseArray(String[] arr) {
		return parseArray(arr,"and");
	}
	public static String parseArray(String[] arr,String conjunction) {
		if(arr.length==0) return "";
		else if(arr.length==1) return arr[0];
		else if(arr.length==2) return arr[0]+" "+conjunction+" "+arr[1];
		else {
			String result = "";
			for(int i=0;i<arr.length-1;i++) {
				result+=arr[i]+", ";
			}
			result+="and "+arr[arr.length-1];
			return result;
		}
	}

	public static String pad(String s,int width) {
		if(width<s.length()) return s;
		if(s.length()%2!=width%2) s+=" ";
		String padding = String.format("%" + ((width-s.length())/2) + "s", "");
		return padding+s+padding;
	}

	//converts a float between 0 and 1 to a curve that tends toward 0 below 0.5 and 1 above 0.5
	public static float getLogisticalCurve(float f) {
		float value = (f-1f/2)/4;
		double pow;
		if(value<0) pow = -1*Math.pow(-1*value, 1.0/3.0);
		else pow = Math.pow(value, 1.0/3.0);
		return (float) pow+1f/2;
	}
	public static float adjustSimplex(float f,float min,float max) {
		return (f+1)/2*(max-min)+min;
	}

	public static double percentileToZ(double percentile) {
		if(percentile<=0||percentile>=100) throw new IllegalArgumentException("Expected value between (0,100) exclusive");
		double $p = Math.abs(percentile - 0.5);

		double $z;
		if($p > 0.42) {
			double $r=Math.sqrt(-Math.log(0.5 - $p));
			$z=(((2.3212128 * $r + 4.8501413) * $r + (-2.2979648)) * $r + (-2.7871893)) / ((1.6370678 * $r + 3.5438892) * $r + 1);
		}
		else {
			double $r = $p * $p;
			$z = $p * (((-25.4410605 * $r + 41.3911977) * $r + (-18.6150006)) * $r + 2.5066282) / ((((3.1308291 * $r + (-21.0622410)) * $r + 23.0833674) * $r + (-8.4735109)) * $r + 1);
		}
		if(percentile < 0.5) $z = -$z;
		return $z;
	}
}
