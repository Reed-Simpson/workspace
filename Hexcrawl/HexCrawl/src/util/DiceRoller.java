package util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class DiceRoller {
	private static final int ARCHIVELIMIT = 10;
	transient Random rand;
	ArrayList<String> prev;
	ArrayList<String> results;

	public DiceRoller() {
		prev = new ArrayList<String>();
		results = new ArrayList<String>();
	}
	
	public ArrayList<Integer> roll(int number,int sides) {
		if(rand==null) rand = new Random();
		ArrayList<Integer> results = new ArrayList<Integer>();
		for(int i=0;i<number;i++) {
			results.add(rand.nextInt(sides)+1);
		}
		return results;
	}
	
	public int getNumber(String s) {
		if("".equals(s)) {
			return 1;
		}else {
			return Integer.valueOf(s);
		}
	}
	
	public int sum(Iterable<Integer> numbers) {
		int result = 0;
		for(int i:numbers) {
			result+=i;
		}
		return result;
	}

	private ArrayList<Integer> highest(ArrayList<Integer> roll, int highest) {
		while(highest<roll.size()) {
			int lowest = Integer.MAX_VALUE;
			int lowestIndex = -1;
			for(int i=0;i<roll.size();i++) {
				if(roll.get(i)<lowest) {
					lowest = roll.get(i);
					lowestIndex = i;
				}
			}
			roll.remove(lowestIndex);
		}
		return roll;
	}
	private ArrayList<Integer> lowest(ArrayList<Integer> roll, int lowest) {
		while(lowest<roll.size()) {
			int highest = Integer.MIN_VALUE;
			int highestIndex = -1;
			for(int i=0;i<roll.size();i++) {
				if(roll.get(i)>highest) {
					highest = roll.get(i);
					highestIndex = i;
				}
			}
			roll.remove(highestIndex);
		}
		return roll;
	}
	
	public String processString(String s) {
		String result = parseString(s);
		results.add(result);
		prev.add(s);
		if(prev.size()>ARCHIVELIMIT) prev.remove(0);
		if(results.size()>ARCHIVELIMIT) results.remove(0);
		return result;
	}
	
	public String parseString(String s) {
		if("".equals(s)) return "";
		Matcher matcher = Pattern.compile("(.*)\"(.+)\"(.*)").matcher(s);
		if(matcher.matches()) return parseString(matcher.group(1))+matcher.group(2)+parseString(matcher.group(3));
		matcher = Pattern.compile("(.*)([^\\ddlkF\\+\\-\\*\\/\\^]+)(.*)").matcher(s);
		if(matcher.matches()) return parseString(matcher.group(1))+matcher.group(2)+parseString(matcher.group(3));
		else return intify(roll(s));
	}

	public double roll(String s) {
		Matcher matcher = Pattern.compile("(.*)\\((.*)\\)(.*)").matcher(s);
		if(matcher.matches()) {
			return roll(matcher.group(1)+""+intify(roll(matcher.group(2)))+""+matcher.group(3));
		}
		matcher = Pattern.compile("-?\\d+(?:\\.\\d+)?").matcher(s);
		if(matcher.matches()) {
			return Double.valueOf(s);
		}
		matcher = Pattern.compile("(\\d*)dF").matcher(s);
		if(matcher.matches()) {
			int number = getNumber(matcher.group(1));
			return roll(number+"d3-2*"+number);
		}
		matcher = Pattern.compile("(\\d*)d(\\d+)((?:k\\d+)?)((?:kl\\d+)?)").matcher(s);
		if(matcher.matches()) {
			int number = getNumber(matcher.group(1));
			int sides = Integer.valueOf(matcher.group(2));
			ArrayList<Integer> results = roll(number,sides);
			if(!"".equals(matcher.group(3))) {
				int highest = Integer.valueOf(matcher.group(3).substring(1));
				highest(results, highest);
			}
			if(!"".equals(matcher.group(4))) {
				int lowest = Integer.valueOf(matcher.group(4).substring(2));
				lowest(results, lowest);
			}
			return sum(results);
		}
		matcher = Pattern.compile("(.+)([\\+\\-])(.+)").matcher(s);
		if(matcher.matches()) {
			if("+".equals(matcher.group(2))) {
				return roll(matcher.group(1))+roll(matcher.group(3));
			}else {
				return roll(matcher.group(1))-roll(matcher.group(3));
			}
		}
		matcher = Pattern.compile("(.+)([\\*\\/])(.+)").matcher(s);
		if(matcher.matches()) {
			if("*".equals(matcher.group(2))) {
				return roll(matcher.group(1))*roll(matcher.group(3));
			}else {
				return roll(matcher.group(1))/roll(matcher.group(3));
			}
		}
		matcher = Pattern.compile("(.+)\\^(.+)").matcher(s);
		if(matcher.matches()) {
			return roll(intify(Math.pow(roll(matcher.group(1)), roll(matcher.group(2)))));
		}
		throw new IllegalArgumentException("unparsable string: "+s);
	}

	public static void main(String[] args) throws IOException {
		System.out.println("Diceroller test");
		DiceRoller roller = new DiceRoller();
//		System.out.println("100 - "+roller.roll("100"));
//		System.out.println("d(50+50) - "+roller.roll("d(50+50)"));
//		System.out.println("d100 - "+roller.roll("d100"));
//		System.out.println("d100+100 - "+roller.roll("d100+100"));
//		System.out.println("d100-100 - "+roller.roll("d100-100"));
//		System.out.println("d100^2 - "+roller.roll("d100^2"));
//		System.out.println("d100*10 - "+roller.roll("d100*10"));
//		System.out.println("10d100 - "+roller.roll("10d100"));
//		System.out.println("d100/10 - "+roller.roll("d100/10"));
//		System.out.println("10dF - "+roller.roll("10dF"));

        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while(null!=(s=r.readLine())) {
        	System.out.println(" = "+roller.parseString(s));
        }

	}

	private static String intify(double d) {
		if(d%1==0) return String.valueOf((int)d);
		else return String.valueOf(d);
	}
	
	public DiceRollerDialog showDialog(JFrame parent) {
		DiceRollerDialog diceRollerDialog = new DiceRollerDialog(parent);
		diceRollerDialog.showDialog();
		return diceRollerDialog;
	}
	
	public class DiceRollerDialog extends JDialog {
		private static final long serialVersionUID = 969397943780677514L;
		private JTextArea archive;
		private int index = -1;
		private String temp;

		public DiceRollerDialog(JFrame parent) {
			super(parent,true);
			getRootPane().setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	        setResizable(false);
	        this.setTitle("Dice Roller");
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			archive = new JTextArea();
			archive.setEditable(false);
			archive.setPreferredSize(new Dimension(500,500));
			archive.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			this.setLayout(new BorderLayout());
			this.add(archive,BorderLayout.CENTER);
			JTextField entry = new JTextField();
			this.add(entry,BorderLayout.SOUTH);
			entry.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {
					if(e.getKeyChar() == '\n') {
						processString(entry.getText());
						entry.setText("");
						updateArchive();
						index = -1;
						temp = "";
					}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyPressed(KeyEvent e) {
		            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		                if(index>=0) index--;
		                if(index==-1) entry.setText(temp);
		                else entry.setText(prev.get(prev.size()-1-index));
		            }else if (e.getKeyCode() == KeyEvent.VK_UP) {
		                if(index==-1) temp = entry.getText();
		                if(index<prev.size()-1) index++;
		                entry.setText(prev.get(prev.size()-1-index));
		            }
				}
			});
			
		}

	    public void showDialog() {
	        this.pack();
	        setLocationRelativeTo(this.getOwner());
	        setVisible(true);
	        updateArchive();
	    }
	    
	    public void updateArchive() {
	    	StringBuilder text = new StringBuilder();
	    	for(int i=0;i<prev.size();i++) {
	    		text.append(prev.get(i)).append("\r\n");
	    		text.append(" → ").append(results.get(i)).append("\r\n");
	    	}
	    	this.archive.setText(text.toString());
	    }
		
	}


}
