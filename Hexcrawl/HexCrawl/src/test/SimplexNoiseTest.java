package test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import data.OpenSimplex2S;
import util.Util;

public class SimplexNoiseTest {
	private static final int SIZE = 5000;
	private static final int BUCKET_COUNT = 10;
	
	public static void main(String[] args) {
		HashMap<Double,Integer> counter = new HashMap<Double,Integer>();
		long sum = 0;
		for(int x = 0;x<SIZE;x++) {
			for(int y = 0;y<SIZE;y++) {
				float f = OpenSimplex2S.noise2(897807, x, y);
				double bucket = Math.ceil(f*BUCKET_COUNT)/BUCKET_COUNT;
				sum++;
				if(counter.get(bucket)==null) {
					counter.put(bucket, 1);
				}else {
					counter.put(bucket, counter.get(bucket)+1);
				}
			}
		}
		
		ArrayList<Double> keyList = new ArrayList<Double>(counter.keySet());
		Collections.sort(keyList);
		DecimalFormat df = new DecimalFormat(" #,##0.00 %");
		long cumulative = 0;
		for(Double d:keyList) {
			cumulative+=counter.get(d);
			System.out.println(d+" | "+counter.get(d)+" | "+df.format(((double)cumulative)/sum)+" | "+Util.symplexToPercentile((float)(d+0.0))+" | "+Util.percentileToSimplex((100*(double)cumulative)/sum));
		}
	}

}
