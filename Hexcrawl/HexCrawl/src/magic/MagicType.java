package magic;

import java.awt.Color;

import general.Util;

public enum MagicType {
	DEADMAGIC(0.1f,"Dead Magic",Color.BLACK),
	LOWMAGIC(1f,"Low Magic",Color.GRAY),
	NORMALMAGIC(3f,"Normal Magic",Color.YELLOW),
	HIGHMAGIC(1f,"High Magic",Color.GREEN),
	WILDMAGIC(0.15f,"Wild Magic",Color.PINK);
	
	private static Float sumWeights = null;

	private float weight;
	private String name;
	private Color color;
	
	private MagicType(float weight,String name,Color color) {
		this.weight=weight;
		this.name=name;
		this.color=color;
	}
	
	public float getWeight() {
		return this.weight;
	}
	public String getName() {
		return this.name;
	}
	public Color getColor() {
		return color;
	}
	
	public static float getSumWeights(MagicType type) {
		float sum = 0;
		for(MagicType t:MagicType.values()) {
			sum+= t.getWeight();
			if(t.equals(type)) break;
		}
		return sum;
	}
	public static float getSumWeights() {
		if(sumWeights==null) {
			sumWeights = getSumWeights(null);
		}
		return sumWeights;
	}
	public static float getLowMagicThresh() {
		return Util.percentileToSimplex(100*DEADMAGIC.getWeight()/getSumWeights());
	}
	public static float getNormalMagicThresh() {
		return Util.percentileToSimplex(100*(DEADMAGIC.getWeight()+LOWMAGIC.getWeight())/getSumWeights());
	}
	public static float getHighMagicThresh() {
		return Util.percentileToSimplex(100*(DEADMAGIC.getWeight()+LOWMAGIC.getWeight()+NORMALMAGIC.getWeight())/getSumWeights());
	}
	public static float getWildMagicThresh() {
		return Util.percentileToSimplex(100*(DEADMAGIC.getWeight()+LOWMAGIC.getWeight()+NORMALMAGIC.getWeight()+HIGHMAGIC.getWeight())/getSumWeights());
	}
	
	public static MagicType getMagicType(float magicValue) {
		if(magicValue<getLowMagicThresh()) return DEADMAGIC;
		else if(magicValue<getNormalMagicThresh()) return LOWMAGIC;
		else if(magicValue<getHighMagicThresh()) return NORMALMAGIC;
		else if(magicValue<getWildMagicThresh()) return HIGHMAGIC;
		else return WILDMAGIC;
	}
}
