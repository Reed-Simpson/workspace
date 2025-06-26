package util;

import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class MythicFateRoller {
	private static final Trinterval[] ranges = 
		{tri( 0, 1,81),tri( 1, 5,82),tri( 2,10,83),tri( 3,15,84),tri( 5,25,86),tri( 7,35,88),tri(10,50,91),tri(13,65,94),tri(15,75,96),tri(17,85,98),tri(18,90,99),tri(19,95,100),tri(20,99,101)};
	private static final String[] odds = {"Certain","Nearly Certain","Very Likely","Likely","50/50","Unlikely","Very Unlikely","Nearly Impossible","Impossible"};
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

	
	public MythicFateRollerDialog showDialog(JFrame parent) {
		MythicFateRollerDialog dialog = new MythicFateRollerDialog(parent);
		return dialog;
	}

	@SuppressWarnings("serial")
	public class MythicFateRollerDialog extends JDialog {
		public MythicFateRollerDialog(JFrame parent) {
			super(parent);
			this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
			getRootPane().setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	        setResizable(false);
	        this.setTitle("Mythic GME2 Fate Question Roller");
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			Object[][] data = new Object[9][10];
			for(int i=0;i<data.length;i++) {
				data[i][0] = odds[i];
				for(int j=1;j<data[0].length;j++) {
					data[i][j] = getTriIndex(i+j-3);
				}
			}
			String[] headers = {"","1","2","3","4","5","6","7","8","9"};
			JTable table = new JTable(data,headers);
			this.add(table);
			
			JSlider slider = new JSlider(JSlider.HORIZONTAL);
			slider.setMinimum(1);
			slider.setMaximum(9);
			slider.setMajorTickSpacing(1);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			this.add(slider);
			this.pack();
	        setLocationRelativeTo(this.getOwner());
			this.setVisible(true);
		}
		
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
		public String toString() {
			String s1 = "x";
			if(q1>0) s1 = String.valueOf(q1);
			String s2 = String.valueOf(q2);
			String s3 = "x";
			if(q3<101) s3 = String.valueOf(q3);
			return padNumber(s1)+" "+padNumber(s2)+" "+padNumber(s3);
		}
		private String padNumber(String s) {
			if(s.length()>2) return s;
			else if(s.length()==2) return " "+s;
			else return "  "+s;
		}
	}
}
