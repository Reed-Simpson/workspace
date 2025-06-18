package data.economy;

import java.awt.Color;

import data.Util;

public enum EconomicType {
	DESTITUTE(1, Color.BLACK), 
	STRUGGLING(1, Color.DARK_GRAY), 
	AVERAGE(1, Color.GRAY), 
	SUCCESSFUL(1, Color.LIGHT_GRAY), 
	LUXURIOUS(1, Color.WHITE);

	private static Float sumWeights = null;

	private float weight;
	private Color color;

	private EconomicType(float weight,Color color) {
		this.weight=weight;
		this.color=color;
	}

	public float getWeight() {
		return weight;
	}
	public Color getColor() {
		return color;
	}
	
	public static float getSumWeights(EconomicType type) {
		float sum = 0;
		for(EconomicType t:EconomicType.values()) {
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
	public static float getStrugglingThresh() {
		return Util.percentileToSimplex(100*DESTITUTE.getWeight()/getSumWeights());
	}
	public static float getAverageThresh() {
		return Util.percentileToSimplex(100*(DESTITUTE.getWeight()+STRUGGLING.getWeight())/getSumWeights());
	}
	public static float getSuccessfulThresh() {
		return Util.percentileToSimplex(100*(DESTITUTE.getWeight()+STRUGGLING.getWeight()+AVERAGE.getWeight())/getSumWeights());
	}
	public static float getLuxuriousThresh() {
		return Util.percentileToSimplex(100*(DESTITUTE.getWeight()+STRUGGLING.getWeight()+AVERAGE.getWeight()+SUCCESSFUL.getWeight())/getSumWeights());
	}
	
	public static EconomicType getEconomicType(float economicValue) {
		if(economicValue<getStrugglingThresh()) return DESTITUTE;
		else if(economicValue<getAverageThresh()) return STRUGGLING;
		else if(economicValue<getSuccessfulThresh()) return AVERAGE;
		else if(economicValue<getLuxuriousThresh()) return SUCCESSFUL;
		else return LUXURIOUS;
	}
}
