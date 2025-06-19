package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.HexData;
import io.AppData;
import io.SaveRecord;
import util.DiceRoller;
import util.Util;

public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = -6668894616915293306L;
	private MapPanel panel;
	private JTextField xField;
	private JTextField yField;
	private JTextField sField;
	private JComboBox<HexData> dataMenu;
	private JLabel sLabel;
	private JCheckBox wBox;
	private JCheckBox cBox;
	private JCheckBox dBox;
	private JLabel distance;
	private DecimalFormat distanceStringFormat;
	private JComboBox<HexData> regionMenu;
	private HexData selectedData;
	private HexData selectedRegion;
	private JMenu fileMenu;
	private ArrayList<JMenuItem> recentFileMenus;
	private JButton prevButton;
	private JButton nextButton;

	public MenuBar(MapPanel panel) {
		this.panel=panel;

		this.fileMenu = constructFileMenu(panel);
		this.add(fileMenu);
		repopulateRecentFiles(panel);
		
		JMenu campaignMenu = constructCampaignMenu();
		this.add(campaignMenu);
		
		JMenu utilityMenu = constructUtilityMenu();
		this.add(utilityMenu);

		JPanel dataFieldsPanel = constructDataFields(panel);

		this.add(dataFieldsPanel);
		this.distanceStringFormat = new DecimalFormat("#0 miles");
	}

	private JMenu constructFileMenu(MapPanel panel) {
		JMenu fileMenu = new JMenu("File");
		JMenuItem menuNew = new JMenuItem("New");
		fileMenu.add(menuNew);
		menuNew.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				SaveRecord newRecord = new SaveRecord();
				panel.reloadFromSaveRecord(newRecord);
			}
		});
		JMenuItem menuNewSeed = new JMenuItem("New From Seed");
		fileMenu.add(menuNewSeed);
		menuNewSeed.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				newFromSeed();
			}
		});
		JMenuItem menuOpen = new JMenuItem("Open");
		fileMenu.add(menuOpen);
		menuOpen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
		JMenuItem menuSave = new JMenuItem("Save");
		fileMenu.add(menuSave);
		menuSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				panel.save();
				repopulateRecentFiles(panel);
			}
		});
		JMenuItem menuSaveAs = new JMenuItem("Save As");
		fileMenu.add(menuSaveAs);
		menuSaveAs.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.saveAs();
				repopulateRecentFiles(panel);
			}
		});
		fileMenu.addSeparator();
		fileMenu.add(new JLabel("recent"));
		return fileMenu;
	}

	private JMenu constructCampaignMenu() {
		JMenu menu = new JMenu("Campaign");
		JMenuItem listsItem = new JMenuItem("Lists");
		menu.add(listsItem);
		return menu;
	}

	private JMenu constructUtilityMenu() {
		JMenu menu = new JMenu("Utilities");
		JMenuItem roller = new JMenuItem("Dice Roller");
		roller.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DiceRoller roller = panel.getRecord().getRoller();
				roller.showDialog(panel.getFrame());
			}
		});
		menu.add(roller);
		return menu;
	}

	private JPanel constructDataFields(MapPanel panel) {
		JPanel dataFieldsPanel = new JPanel();
		
		dataFieldsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		distance = new JLabel("distance:");
		dataFieldsPanel.add(distance);
		dataFieldsPanel.add(Box.createHorizontalStrut(10));
		

		prevButton = new JButton("🢀");
		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.previous();
			}
		});
		dataFieldsPanel.add(prevButton);
		nextButton = new JButton("🢂");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.next();
			}
		});
		dataFieldsPanel.add(nextButton);
		dataFieldsPanel.add(Box.createHorizontalStrut(10));
		
		dataFieldsPanel.add(new JLabel("x:"));
		xField = new JTextField();
		xField.setPreferredSize(new Dimension(50,20));
		xField.setMaximumSize(new Dimension(50,50));
		ActionListener posActionListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Point c = panel.getSelectedGridPoint();
				Point zero = panel.getRecord().getZero();
				Point displayPos = new Point(Integer.parseInt(xField.getText()),Integer.parseInt(yField.getText()));
				Point actualPos = Util.denormalizePos(displayPos, zero);
				double scale = Double.parseDouble(sField.getText());
				if(!c.equals(actualPos)||scale!=panel.getScale()) {
					panel.setScale(scale);
					panel.recenter(actualPos,true);
					panel.repaint();
					sLabel.repaint();
				}
			}
		};
		xField.addActionListener(posActionListener);
		dataFieldsPanel.add(xField);
		dataFieldsPanel.add(new JLabel("y:"));
		yField = new JTextField();
		yField.setPreferredSize(new Dimension(50,20));
		yField.setMaximumSize(new Dimension(50,50));
		yField.addActionListener(posActionListener);
		dataFieldsPanel.add(yField);
		dataFieldsPanel.add(new JLabel("scale:"));
		sField = new JTextField();
		sField.setPreferredSize(new Dimension(30,20));
		sField.setMaximumSize(new Dimension(30,30));
		sField.addActionListener(posActionListener);
		dataFieldsPanel.add(sField);
		sLabel = new JLabel("1 hex = 6 miles");
		dataFieldsPanel.add(sLabel);
		dataFieldsPanel.add(Box.createHorizontalStrut(10));
		dataFieldsPanel.add(new JLabel("data:"));
		
		dataMenu = new JComboBox<HexData>(HexData.getMapViews());
		dataMenu.setMaximumSize(new Dimension(100,100));
		dataMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!dataMenu.getSelectedItem().equals(selectedData)) {
					selectedData = (HexData) dataMenu.getSelectedItem();
					panel.setDisplayData(selectedData);
					panel.repaint();
				}
			}
		});
		dataFieldsPanel.add(dataMenu);
		dataFieldsPanel.add(Box.createHorizontalStrut(10));
		
//		dataFieldsPanel.add(new JLabel("show rivers:"));
		wBox = new JCheckBox();
		wBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				panel.setShowRivers(wBox.isSelected());
			}
		});
//		dataFieldsPanel.add(wBox);
		wBox.setSelected(true);
		
//		dataFieldsPanel.add(new JLabel("show towns:"));
		cBox = new JCheckBox();
		cBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				panel.setShowCities(cBox.isSelected());
			}
		});
//		dataFieldsPanel.add(cBox);
		cBox.setSelected(true);
		
		dataFieldsPanel.add(new JLabel("mouseover:"));
		dBox = new JCheckBox();
		dBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				panel.setShowDistance(dBox.isSelected());
			}
		});
		dataFieldsPanel.add(dBox);
		dBox.setSelected(false);
		
		dataFieldsPanel.add(new JLabel("show region:"));
		regionMenu = new JComboBox<HexData>(HexData.getRegionTypes());
		regionMenu.setMaximumSize(new Dimension(100,100));
		regionMenu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!regionMenu.getSelectedItem().equals(selectedRegion)) {
					selectedRegion = (HexData) regionMenu.getSelectedItem();
					panel.setDisplayRegion(selectedRegion);
					panel.repaint();
				}
			}
		});
		dataFieldsPanel.add(regionMenu);

		selectedData = (HexData) dataMenu.getSelectedItem();
		selectedRegion = (HexData) regionMenu.getSelectedItem();
		panel.setDisplayData(selectedData);
		panel.setDisplayRegion(selectedRegion);
		return dataFieldsPanel;
	}

	private void repopulateRecentFiles(MapPanel panel) {
		if(recentFileMenus!=null) {
			for(JMenuItem item:recentFileMenus) {
				fileMenu.remove(item);
			}
		}
		recentFileMenus = new ArrayList<JMenuItem>();
		AppData appData = panel.getFrame().getAppData();
		ArrayList<File> recentFiles = appData.getRecent();
		for(int i=0;i<recentFiles.size();i++) {
			File file = recentFiles.get(recentFiles.size()-1-i);
			JMenuItem recent = new JMenuItem(file.toString());
			recent.addActionListener(new ActionListener(){
				File thisFile = file;
				public void actionPerformed(ActionEvent e) {
					SaveRecord newRecord = SaveRecord.load(thisFile,panel.getFrame().getAppData());
					repopulateRecentFiles(panel);
					panel.reloadFromSaveRecord(newRecord);
				}
			});
			recentFileMenus.add(recent);
			fileMenu.add(recent);
		}
	}

	@Override
	public void paint(Graphics g) {
		SaveRecord record = panel.getRecord();
		if(panel.isShowDistance()) {
			distance.setText("Distance: "+distanceString());
		}
		distance.setVisible(panel.isShowDistance());
		Point adjustedPoint = Util.normalizePos(panel.getSelectedGridPoint(), record.getZero());
		xField.setText(String.valueOf(adjustedPoint.x));
		yField.setText(String.valueOf(adjustedPoint.y));
		sField.setText(String.valueOf(panel.getScale()));
		sLabel.setText(scaleString());
		dataMenu.setSelectedItem(panel.getDisplayData());
		nextButton.setEnabled(panel.hasNext());
		prevButton.setEnabled(panel.hasPrevious());
		super.paint(g);
	}

	public String scaleString() {
		return "1 hex = "+hexScaleFactor()+" miles";
	}

	public int hexScaleFactor() {
		if(panel.getScale()<MapPanel.MIN_SCALE) {
			int miles = (int) (6*MapPanel.MIN_SCALE/panel.getScale());
			return miles;
		}else {
			return 6;
		}
	}

	public String distanceString() {
		return this.distanceStringFormat.format(panel.getDistance()*hexScaleFactor());
	}

	private void newFromSeed() {
		String text = "Seed must be a number between +/- 9 quintillion: ";
		String s = (String)JOptionPane.showInputDialog(MenuBar.this, text,"Enter Seed",JOptionPane.PLAIN_MESSAGE,null,null, panel.getRecord().getSeed());
		if(s==null)return;
		Long seed = null;
		try {
			seed = Long.parseLong(s);
		}catch (NumberFormatException ex) {}
		while(s.trim()==""||seed == null) {
			s = (String)JOptionPane.showInputDialog(MenuBar.this, text,"Enter Seed",JOptionPane.WARNING_MESSAGE,null,null, panel.getRecord().getSeed());
			if(s==null)return;
			seed = null;
			try {
				seed = Long.parseLong(s);
			}catch (NumberFormatException ex) {}
		}
		SaveRecord newRecord = new SaveRecord();
		newRecord.setSeed(seed);
		repopulateRecentFiles(panel);
		panel.reloadFromSaveRecord(newRecord);
	}

	private void openFile() {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showOpenDialog(panel.getFrame()) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			SaveRecord newRecord = SaveRecord.load(file,panel.getFrame().getAppData());
			repopulateRecentFiles(panel);
			panel.reloadFromSaveRecord(newRecord);
		}
	}


}
