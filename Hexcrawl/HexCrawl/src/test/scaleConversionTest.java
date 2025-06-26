package test;

public class scaleConversionTest {
	public static int getEffectiveScale(double scale) {
		int effectiveScale;
		if(scale<1) {
			int cardinality = (int) (-1*getCardinality(scale));
			int remainder = (int) getRemainder(scale,-1*cardinality);
			effectiveScale = remainder-9*cardinality-7;
		}else {
			effectiveScale = (int) scale;
		}
		return effectiveScale;
	}
	private static double getCardinality(double x) {
		return logX(x,9);
	}
	private static double getRemainder(double scale,int cardinality) {
		return (9*scale/Math.pow(9, cardinality))-1;
	}
	public static double getScale(int effectiveScale) {
		double scale;
		if(effectiveScale<1) {
			int cardinality = (int) ((effectiveScale-10)/-10);
			int remainder = (int) (effectiveScale-1+cardinality*10);
			scale = Math.pow(0.1, cardinality)*remainder;
		}else {
			scale = effectiveScale;
		}
		return scale;
	}
	public static double logX(double val, int x) {
		return Math.log(val)/Math.log(x);
	}
	
	public static void main(String[] args) {
//		int i=-8;
//		System.out.println(i+" - "+getScale(i)+" - "+getEffectiveScale(getScale(i)));
//		for(int i=10;i>-100;i--) {
//			System.out.println(i+" - "+getScale(i)+" - "+getEffectiveScale(getScale(i)));
//		}
		for(double i=1;i>0.15;i-=0.1) {
			System.out.println(i+" - "+getCardinality(i)+" - "+getRemainder(i, (int) getCardinality(i))+" - "+getEffectiveScale(i));
		}
		for(double i=0.1;i>0.005;i-=0.01) {
			System.out.println(i+" - "+getCardinality(i)+" - "+getRemainder(i, (int) getCardinality(i))+" - "+getEffectiveScale(i));
		}
	}

}
