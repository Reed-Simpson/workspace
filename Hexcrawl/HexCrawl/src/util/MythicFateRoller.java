package util;

import java.util.Random;

import javax.swing.JDialog;

public class MythicFateRoller {
	private static final Trinterval[] ranges = 
		{tri( 0, 1,81),tri( 1, 5,82),tri( 2,10,83),tri( 3,15,84),tri( 5,25,86),tri( 7,35,88),tri(10,50,91),tri(13,65,94),tri(15,75,96),tri(17,85,98),tri(18,90,99),tri(19,95,100),tri(20,99,101)};
	transient Random rand;
	int chaosFactor;
	
	private static Trinterval tri(int q1, int q2,int q3) {
		return new Trinterval(q1, q2, q3);
	}
	private static Trinterval getTriIndex(int index) {
		if(index<1) return ranges[0];
		else if(index>11) return ranges[12];
		else return ranges[index];
	}

	@SuppressWarnings("serial")
	public class MythicFateRollerDialog extends JDialog {
		
	}
	
	public Random getRand() {
		if(this.rand==null) rand = new Random(System.currentTimeMillis());
		return this.rand;
	}
	
	private static class Trinterval{
		int q1;
		int q2;
		int q3;
		public Trinterval(int q1, int q2,int q3) {
			this.q1=q1;
			this.q2=q2;
			this.q3=q3;
		}
		public int compare(int val) {
			if(val<=q1) return 1;
			if(val<=q2) return 2;
			if(val<q3) return 3;
			else return 4;
		}
	}
}
