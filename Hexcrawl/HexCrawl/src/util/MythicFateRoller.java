package util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableRow;

import data.HexData;
import view.InfoPanel;
import view.MyTextPane;

public class MythicFateRoller {
	private static final Trinterval[] ranges = 
		{tri( 0, 1,81),tri( 1, 5,82),tri( 2,10,83),tri( 3,15,84),tri( 5,25,86),tri( 7,35,88),tri(10,50,91),tri(13,65,94),tri(15,75,96),tri(17,85,98),tri(18,90,99),tri(19,95,100),tri(20,99,101)};
	private static final String[] odds = {"Certain","Nearly Certain","Very Likely","Likely","50/50","Unlikely","Very Unlikely","Nearly Impossible","Impossible"};
	private static final String[] outcomes = {"Exceptional Yes","Yes","No","Exceptional No","Random Event"};
	transient Random rand;
	int chaosFactor;
	private InfoPanel info;
	
	private static Trinterval tri(int q1, int q2,int q3) {
		return new Trinterval(q1, q2, q3);
	}
	private static Trinterval getTriIndex(int index) {
		if(index<1) return ranges[0];
		else if(index>11) return ranges[12];
		else return ranges[index];
	}
	
	public MythicFateRoller(InfoPanel info) {
		chaosFactor = 5;
		this.info = info;
	}
	
	public MythicFateRollerDialog showDialog(JFrame parent) {
		MythicFateRollerDialog dialog = new MythicFateRollerDialog(parent);
		return dialog;
	}
	
	private static int isDoubles(int val) {
		if(val%10==val/10) return val%10;
		else return 10;
	}
	private String getOutcome(int roll, Trinterval values) {
		if(isDoubles(roll)<chaosFactor) return outcomes[4];
		else return outcomes[values.compare(roll)];
	}

	@SuppressWarnings("serial")
	public class MythicFateRollerDialog extends JDialog {
		private JSlider slider;
		private JTable table;
		private MyTextPane field;

		public MythicFateRollerDialog(JFrame parent) {
			super(parent);
			this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
			getRootPane().setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	        setResizable(false);
	        this.setTitle("Mythic GME2 Fate Question Roller");
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			Object[][] data = new Object[9][2];
			for(int i=0;i<data.length;i++) {
				data[i][0] = odds[i];
				data[i][1] = getTriIndex(5-i+5);
			}
			String[] headers = {"","1"};
			DefaultTableModel model = new DefaultTableModel(data,headers) {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false; 
	            }
	        };
			table = new JTable(model);
			table.setFont(table.getFont().deriveFont(20f));
			setColumnWidths(table);
			table.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int row = table.rowAtPoint(e.getPoint());
					int column = table.columnAtPoint(e.getPoint());
					if(column>0) {
						Trinterval obj = (Trinterval) table.getValueAt(row, column);
						int roll = getRand().nextInt(100)+1;
						String message = getOutcome(roll, obj);
						field.setText(message);
						field.setHighlight(true);
						field.flicker();
						//JOptionPane.showMessageDialog(MythicFateRollerDialog.this, message, "Fate Question Roll", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
			this.add(table);
			
			slider = new JSlider(JSlider.VERTICAL);
			slider.setInverted(true);
			slider.setMinimum(1);
			slider.setMaximum(9);
			slider.setValue(chaosFactor);
			slider.setMajorTickSpacing(1);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			slider.setPaintTrack(false);
			slider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					chaosFactor = slider.getValue();
					refreshTable();
					MythicFateRollerDialog.this.repaint();
				}
			});
			this.add(slider);
			
			this.field = new MyTextPane(info, -1, HexData.ENCOUNTER);
			field.setPreferredSize(new Dimension(270,270));
			this.add(field);
			
			this.pack();
	        setLocationRelativeTo(this.getOwner());
			this.setVisible(true);
		}
		
		private void setColumnWidths(JTable table) {
            table.setRowHeight(30);
            TableColumn column = table.getColumnModel().getColumn(0);
            column.setPreferredWidth(170 + table.getIntercellSpacing().width); // Add spacing
            TableColumn column1 = table.getColumnModel().getColumn(1);
            column1.setPreferredWidth(120 + table.getIntercellSpacing().width); // Add spacing
            table.setRowHeight(30);
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		}

		public void refreshTable(){
			Object[][] data = new Object[9][2];
			for(int i=0;i<data.length;i++) {
				data[i][0] = odds[i];
				data[i][1] = getTriIndex(slider.getValue()-i+5);
			}
			String[] headers = {"","1"};
			DefaultTableModel model = new DefaultTableModel(data,headers) {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false; 
	            }
	        };
			table.setModel(model);
			setColumnWidths(table);
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
			if(val<=q1) return 0;
			if(val<=q2) return 1;
			if(val<q3) return 2;
			else return 3;
		}
		public String toString() {
			String s1 = "x";
			if(q1>0) s1 = String.valueOf(q1);
			String s2 = String.valueOf(q2);
			String s3 = "x";
			if(q3<101) s3 = String.valueOf(q3);
			return padNumber(s1)+" "+padNumber(s2)+" "+padNumber(s3)+" ";
		}
		private String padNumber(String s) {
			if(s.length()>2) return s;
			else if(s.length()==2) return " "+s;
			else return "  "+s;
		}
	}
	
}
