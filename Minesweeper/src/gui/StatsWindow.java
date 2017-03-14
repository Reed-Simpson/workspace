package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import data.RecordList;


public class StatsWindow extends JDialog{
	private static final long serialVersionUID = 1L;
	private static final String[] columns = {"Time","Clicks","3BV"};
	
	private RecordList records;
	private int sortBy;
	private JPanel[] panels = new JPanel[4];
	private JTabbedPane pane;

	public StatsWindow(JFrame frame, RecordList records, int currentDifficulty) {
		super(frame,"Statistics",true);
		pane=new JTabbedPane();
		sortBy=0;
		this.records=records;

		addTab(pane,"Easy",0);
		addTab(pane,"Medium",1);
		addTab(pane,"Hard",2);
		addTab(pane,"Custom",3);
		
		pane.setSelectedIndex(currentDifficulty);

		this.add(pane);
		this.setSize(300, 105+16*RecordList.NUMENTRIESLOGGED);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void addTab(JTabbedPane pane,String title,int difficulty){
		JPanel panel = makePanel(difficulty);
		panels[difficulty]=panel;
		pane.add(title,panel);
	}
	
	private JPanel makePanel(int difficulty){
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());

		result.add(makeLabel(difficulty),BorderLayout.NORTH);
		result.add(new JScrollPane(makeTable(difficulty)),BorderLayout.CENTER);
		
		return result;
	}
	
	private JLabel makeLabel(int difficulty){
		int wins = records.getWinCount(difficulty);
		int losses = records.getLossCount(difficulty);
		int winRate = (int) Math.round(((double) wins/(double)(wins+losses)*100));
		String labelText = "Wins: "+wins+"   Losses: "+losses+"   Win Rate: "+winRate+"%";
		return new JLabel(labelText);
	}
	
	private JTable makeTable(int difficulty){
		JTable result = new JTable(records.getData(difficulty, sortBy),columns);
		JButton timeButton = new JButton("time");
		timeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setSort(0);
				
			}
		});
		JButton clicksButton = new JButton("clicks");
		clicksButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setSort(1);
			}
		});
		JButton BBBVButton = new JButton("3BV");
		BBBVButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setSort(2);
			}
		});
		JTableHeader header = result.getTableHeader();
		header.setLayout(new BorderLayout());
//		int x = header.getWidth()/3;
//		int y = timeButton.getPreferredSize().height;
//		timeButton.setPreferredSize(new Dimension(x, y));
//		clicksButton.setPreferredSize(new Dimension(x, y));
//		BBBVButton.setPreferredSize(new Dimension(x, y));
		header.add(timeButton,BorderLayout.WEST);
		header.add(clicksButton,BorderLayout.CENTER);
		header.add(BBBVButton,BorderLayout.EAST);
		
		result.setEnabled(false);
		return result;
	}
	
	private void setSort(int sortBy){
		this.sortBy=sortBy;
		for(int difficulty=0;difficulty<4;difficulty++){
			panels[difficulty].removeAll();
			panels[difficulty].add(makeLabel(difficulty),BorderLayout.NORTH);
			panels[difficulty].add(new JScrollPane(makeTable(difficulty)),BorderLayout.CENTER);
		}
		panels[pane.getSelectedIndex()].validate();
	}

	
}
