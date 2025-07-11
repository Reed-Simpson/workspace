package test;

import util.Util;

public class ZscoreTest {

	public static void main(String[] args) {
		for(int i=1;i<1000;i++) {
			System.out.println((i*1.0/1000)+" "+Util.percentileToZ(i*1.0/1000));
		}
	}



}
