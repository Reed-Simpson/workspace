package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
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
import javax.swing.KeyStroke;

import data.HexData;
import io.AppData;
import io.PrintHandler;
import io.SaveRecord;
import util.DiceRoller;
import util.MythicFateRoller;
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
	private MapFrame frame;
	private MythicFateRoller mythicroller;
	private JCheckBox iBox;
	private PrintHandler printer;

	public MenuBar(MapPanel panel,MapFrame frame,InfoPanel info) {
		this.frame = frame;
		this.panel=panel;
		this.printer = new PrintHandler();

		this.fileMenu = constructFileMenu(panel);
		this.add(fileMenu);
		repopulateRecentFiles(panel);

		JMenu utilityMenu = constructUtilityMenu();
		this.add(utilityMenu);

		JPanel dataFieldsPanel = constructDataFields(panel);

		this.add(dataFieldsPanel);
		this.distanceStringFormat = new DecimalFormat("#0 miles");
		mythicroller = new MythicFateRoller(info,panel.getRecord());
	}

	private JMenu constructFileMenu(MapPanel panel) {
		JMenu fileMenu = new JMenu("File");
		JMenuItem menuNew = new JMenuItem("New");
		fileMenu.add(menuNew);
		menuNew.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(frame.unsavedDataConfirmation()) {
					SaveRecord newRecord = new SaveRecord();
					frame.load(newRecord);
				}
			}
		});
		JMenuItem menuNewSeed = new JMenuItem("New From Seed");
		fileMenu.add(menuNewSeed);
		menuNewSeed.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(frame.unsavedDataConfirmation()) {
					newFromSeed();
				}
			}
		});
		JMenuItem menuOpen = new JMenuItem("Open");
		fileMenu.add(menuOpen);
		menuOpen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(frame.unsavedDataConfirmation()) {
					openFile();
				}
			}
		});
		menuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		JMenuItem menuSave = new JMenuItem("Save");
		fileMenu.add(menuSave);
		menuSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				panel.save();
				repopulateRecentFiles(panel);
			}
		});
		menuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		JMenuItem menuSaveAs = new JMenuItem("Save As");
		fileMenu.add(menuSaveAs);
		menuSaveAs.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.saveAs();
				repopulateRecentFiles(panel);
			}
		});
		menuSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		JMenuItem screenshot = new JMenuItem("Screenshot");
		fileMenu.add(screenshot);
		screenshot.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				printer.screenshot(panel);
			}
		});
		screenshot.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PRINTSCREEN,0 ));
		JMenuItem print = new JMenuItem("Print");
		//fileMenu.add(print);  //TODO finish implementation
		print.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				printer.print(panel);
			}
		});
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_DOWN_MASK ));
		fileMenu.addSeparator();
		fileMenu.add(new JLabel("recent"));
		return fileMenu;
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

		JMenuItem gme = new JMenuItem("GME2 Events and Scenes");
		gme.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mythicroller.showDialog(panel.getFrame());
			}
		});
		menu.add(gme);
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
				Point c = panel.getMiddleGridPoint();
				Point zero = panel.getRecord().getZero();
				Point displayPos = new Point(Integer.parseInt(xField.getText()),Integer.parseInt(yField.getText()));
				Point actualPos = Util.denormalizePos(displayPos, zero);
				double scale = Double.parseDouble(sField.getText());
				if(!c.equals(actualPos)||scale!=panel.getScale()) {
					panel.setScale(scale);
					panel.recenter(actualPos,true);
					panel.preprocessThenRepaint();
					sLabel.repaint();
				}
			}
		};
		xField.addActionListener(posActionListener);
		xField.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {}
			public void focusGained(FocusEvent e) {
				xField.select(0, xField.getText().length());
			}
		});
		dataFieldsPanel.add(xField);
		dataFieldsPanel.add(new JLabel("y:"));
		yField = new JTextField();
		yField.setPreferredSize(new Dimension(50,20));
		yField.setMaximumSize(new Dimension(50,50));
		yField.addActionListener(posActionListener);
		yField.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {}
			public void focusGained(FocusEvent e) {
				yField.select(0, yField.getText().length());
			}
		});
		dataFieldsPanel.add(yField);
		dataFieldsPanel.add(new JLabel("scale:"));
		sField = new JTextField();
		sField.setPreferredSize(new Dimension(30,20));
		sField.setMaximumSize(new Dimension(30,30));
		sField.addActionListener(posActionListener);
		sField.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {}
			public void focusGained(FocusEvent e) {
				sField.select(0, sField.getText().length());
			}
		});
		dataFieldsPanel.add(sField);
		sLabel = new JLabel("1 hex = 6 miles");
		dataFieldsPanel.add(sLabel);
		dataFieldsPanel.add(Box.createHorizontalStrut(10));
		dataFieldsPanel.add(new JLabel("view:"));

		dataMenu = new JComboBox<HexData>(HexData.getMapViews());
		dataMenu.setMaximumSize(new Dimension(100,100));
		dataMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!dataMenu.getSelectedItem().equals(selectedData)) {
					selectedData = (HexData) dataMenu.getSelectedItem();
					panel.setDisplayData(selectedData);
					panel.preprocessThenRepaint();
				}
			}
		});
		dataFieldsPanel.add(dataMenu);
		dataFieldsPanel.add(Box.createHorizontalStrut(10));

//		dataFieldsPanel.add(new JLabel("watersheds:"));
		wBox = new JCheckBox();
		wBox.setSelected(panel.isShowRivers());
		wBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				panel.setShowRivers(wBox.isSelected());
			}
		});
//		dataFieldsPanel.add(wBox);

		//		dataFieldsPanel.add(new JLabel("show towns:"));
		cBox = new JCheckBox();
		cBox.setSelected(panel.isShowCities());
		cBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				panel.setShowCities(cBox.isSelected());
			}
		});
		//		dataFieldsPanel.add(cBox);

//		dataFieldsPanel.add(new JLabel("show icons:"));
		iBox = new JCheckBox();
		iBox.setSelected(panel.isShowIcons());
		iBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				panel.setShowIcons(iBox.isSelected());
			}
		});
//		dataFieldsPanel.add(iBox);

		dataFieldsPanel.add(new JLabel("mouseover:"));
		dBox = new JCheckBox();
		dBox.setSelected(panel.isShowDistance());
		dBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				panel.setShowDistance(dBox.isSelected());
			}
		});
		dataFieldsPanel.add(dBox);

		dataFieldsPanel.add(new JLabel("highlight region:"));
		regionMenu = new JComboBox<HexData>(HexData.getRegionTypes());
		regionMenu.setMaximumSize(new Dimension(100,100));
		regionMenu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!regionMenu.getSelectedItem().equals(selectedRegion)) {
					selectedRegion = (HexData) regionMenu.getSelectedItem();
					panel.setDisplayRegion(selectedRegion);
					panel.preprocessThenRepaint();
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
			if(file.exists()) {
				JMenuItem recent = new JMenuItem(file.toString());
				recent.addActionListener(new ActionListener(){
					File thisFile = file;
					public void actionPerformed(ActionEvent e) {
						if(frame.unsavedDataConfirmation()) {
							SaveRecord newRecord = SaveRecord.load(thisFile,panel.getFrame().getAppData());
							repopulateRecentFiles(panel);
							frame.load(newRecord);
						}
					}
				});
				recentFileMenus.add(recent);
				fileMenu.add(recent);
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		SaveRecord record = panel.getRecord();
		if(panel.isShowDistance()) {
			distance.setText("Distance: "+distanceString());
		}
		distance.setVisible(panel.isShowDistance());
		Point adjustedPoint = Util.normalizePos(panel.getMiddleGridPoint(), record.getZero());
		xField.setText(String.valueOf(adjustedPoint.x));
		yField.setText(String.valueOf(adjustedPoint.y));
		sField.setText(String.valueOf(panel.getScale()));
		sLabel.setText(scaleString());
		dataMenu.setSelectedItem(panel.getDisplayData());
		nextButton.setEnabled(panel.hasNext());
		prevButton.setEnabled(panel.hasPrevious());
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
		frame.load(newRecord);
		//		panel.reloadFromSaveRecord(newRecord);
	}

	private void openFile() {
		JFileChooser fileChooser = new JFileChooser();
		AppData appData = panel.getFrame().getAppData();
		File recentfile = appData.getMostRecent();
		if(recentfile!=null) fileChooser.setCurrentDirectory(recentfile.getParentFile());
		else fileChooser.setCurrentDirectory(AppData.getDirectory());
		
		if (fileChooser.showOpenDialog(panel.getFrame()) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			SaveRecord newRecord = SaveRecord.load(file,panel.getFrame().getAppData());
			repopulateRecentFiles(panel);
			frame.load(newRecord);
			//			panel.reloadFromSaveRecord(newRecord);
		}
	}


}
