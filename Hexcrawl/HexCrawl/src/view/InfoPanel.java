package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ToolTipManager;

import data.HexData;
import data.Reference;
import util.Util;
import view.infopanels.HexPanel;
import view.infopanels.RegionPanel;

@SuppressWarnings("serial")
public class InfoPanel extends JTabbedPane{
	public static final int RECENT_HISTORY_COUNT = 20;
	public static final int TAB_TITLE_LENGTH = 34;
	public static final int INFOPANELWIDTH = 450;
	public static final int ENCOUNTERCOUNT = 20;
	public static final int NPCCOUNT = 20;
	public static final int POICOUNT = 20;
	public static final int DUNGEONCOUNT = 6;
	public static final int FACTIONCOUNT = 6;
	public static final int FAITHCOUNT = 5;
	public static final int DISTRICTCOUNT = 6;
	public static final int MONSTERCOUNT = 8;


	private MapPanel panel;

	//ENCOUNTER
	private HexPanel hexPanel;
	private RegionPanel regionPanel;
	public boolean expandHexNotePane;
	public boolean hideHexNotePane;
	boolean changeSelected;

	private JTextArea charactersEmpty;
	private ArrayList<MyTextPane> charactersList;
	private ArrayList<MyTextPane> threadsList;
	private JPanel charactersPanel;

	public InfoPanel(MapPanel panel) {
		this.panel = panel;
		this.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
		this.setPreferredSize(new Dimension(INFOPANELWIDTH,800));
		this.setMaximumSize(new Dimension(INFOPANELWIDTH,99999));
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		hexPanel = new HexPanel(this);
		this.addTab(Util.pad("Hex", InfoPanel.TAB_TITLE_LENGTH), hexPanel);

		this.regionPanel = new RegionPanel(this);
		this.addTab(Util.pad("Region", TAB_TITLE_LENGTH), regionPanel);

		createCampaignTab();

		changeSelected = true;

		ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
		toolTipManager.setDismissDelay(Integer.MAX_VALUE); 
		toolTipManager.setInitialDelay(0);
	}

	public MyTextPane createDungeonEncounter() {
		return hexPanel.createDungeonEncounter();
	}

	public MyTextPane createEncounter() {
		return hexPanel.createEncounter();
	}

	public MyTextPane createMission() {
		return hexPanel.createMission();
	}

	public void removeDungeonEncounter(int index) {
		hexPanel.removeDungeonEncounter(index);
	}

	public void removeEncounter(int index) {
		hexPanel.removeEncounter(index);
	}

	private void createCampaignTab() {
		JPanel campaignPanel = new JPanel();
		campaignPanel.setLayout(new BoxLayout(campaignPanel,BoxLayout.Y_AXIS));


		JPanel charactersHeader = new JPanel();
		charactersHeader.setLayout(new BoxLayout(charactersHeader, BoxLayout.X_AXIS));
		charactersHeader.add(Box.createHorizontalStrut(40));
		charactersHeader.add(new JLabel("Characters:"),BorderLayout.WEST);
		charactersHeader.add(Box.createHorizontalGlue());
		charactersPanel = new JPanel();
		JButton charactersHideButton = new JButton("Hide");
		charactersHeader.add(charactersHideButton,BorderLayout.EAST);
		charactersHeader.add(Box.createHorizontalStrut(140));
		campaignPanel.add(charactersHeader);
		this.charactersList = new ArrayList<MyTextPane>();
		charactersPanel.setLayout(new BoxLayout(charactersPanel, BoxLayout.Y_AXIS));
		charactersPanel.setMaximumSize(new Dimension(INFOPANELWIDTH-30,99999));
		//charactersPanel.setPreferredSize(new Dimension(INFOPANELWIDTH-30,999));
		ArrayList<Reference> chars = panel.getRecord().getCampaignCharacters();
		for(int i=0;i<chars.size();i++) {
			MyTextPane pane = new MyTextPane(this, i, HexData.CHARACTER);
			pane.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
			pane.setRef(chars.get(i));
			pane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,Color.gray));
			charactersPanel.add(pane);
			charactersList.add(pane);
		}
		charactersEmpty = new JTextArea();
		charactersEmpty.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
		charactersEmpty.setEnabled(false);
		charactersEmpty.setText("None");
		charactersPanel.add(charactersEmpty);
		if(chars.size()==0) {
			charactersEmpty.setVisible(true);
		}else {
			charactersEmpty.setVisible(false);
		}
		JScrollPane charactersScrollPane = new JScrollPane(charactersPanel);
		charactersScrollPane.setPreferredSize(new Dimension(INFOPANELWIDTH,999));
		charactersScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		charactersScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		charactersHideButton.addActionListener(new ActionListener() {
			boolean hidden = false;
			public void actionPerformed(ActionEvent e) {
				if(hidden) {
					charactersHideButton.setText("Hide");
					hidden = false;
					charactersScrollPane.setVisible(true);
				}else {
					charactersHideButton.setText("Show");
					hidden = true;
					charactersScrollPane.setVisible(false);
				}
			}
		});
		campaignPanel.add(charactersScrollPane);
		campaignPanel.setMaximumSize(new Dimension(INFOPANELWIDTH,99999));


		JPanel threadsPanel = new JPanel(new BorderLayout());
		JPanel threadsHeader = new JPanel();
		threadsHeader.setLayout(new BoxLayout(threadsHeader, BoxLayout.X_AXIS));
		threadsHeader.add(Box.createHorizontalStrut(40));
		threadsHeader.add(new JLabel("Threads:"),BorderLayout.WEST);
		threadsHeader.add(Box.createHorizontalGlue());
		JPanel threads = new JPanel();
		JScrollPane threadsScrollPane = new JScrollPane(threads);
		threadsScrollPane.setPreferredSize(new Dimension(INFOPANELWIDTH,999));
		JButton addThreadButton = new JButton("➕ Add Thread");
		JButton threadsHideButton = new JButton("Hide");
		threadsHideButton.addActionListener(new ActionListener() {
			boolean hidden = false;
			public void actionPerformed(ActionEvent e) {
				if(hidden) {
					threadsHideButton.setText("Hide");
					hidden = false;
					threadsScrollPane.setVisible(true);
					addThreadButton.setVisible(true);
				}else {
					threadsHideButton.setText("Show");
					hidden = true;
					threadsScrollPane.setVisible(false);
					addThreadButton.setVisible(false);
				}
			}
		});
		threadsHeader.add(threadsHideButton,BorderLayout.EAST);
		threadsHeader.add(Box.createHorizontalStrut(140));
		threadsPanel.add(threadsHeader, BorderLayout.NORTH);
		this.threadsList = new ArrayList<MyTextPane>();
		threads.setLayout(new BoxLayout(threads, BoxLayout.Y_AXIS));
		ArrayList<String> threadText = panel.getRecord().getCampaignThreads();
		for(int i=0;i<threadText.size();i++) {
			MyTextPane pane = new MyTextPane(this, i, HexData.THREAD);
			pane.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
			pane.doPaint();
			threads.add(pane);
			threadsList.add(pane);
			threads.add(Box.createVerticalStrut(2));
		}
		addThreadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MyTextPane pane = new MyTextPane(InfoPanel.this, threadsList.size(), HexData.THREAD);
				pane.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
				threads.add(pane);
				threadsList.add(pane);
				threads.add(Box.createVerticalStrut(2));
				InfoPanel.this.repaint();
			}
		});
		addThreadButton.setMaximumSize(new Dimension(999, 30));
		threadsPanel.add(addThreadButton,BorderLayout.SOUTH);
		threadsPanel.add(threadsScrollPane,BorderLayout.CENTER);
		campaignPanel.add(threadsPanel);

		this.addTab(Util.pad("Campaign", TAB_TITLE_LENGTH), (campaignPanel));
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		changeSelected = false;

		for(int i=0;i<threadsList.size();i++) {
			this.threadsList.get(i).doPaint();
		}
		for(int i=0;i<charactersList.size();i++) {
			this.charactersList.get(i).doPaint();
		}
		changeSelected = true;
	}



	public void setPanel(MapPanel panel) {
		this.panel = panel;
	}
	public void selectTabAndIndex(Reference ref) {
		selectTabAndIndex(ref.getType().getText(), ref.getPoint().x, ref.getPoint().y, ref.getIndex());
	}

	public void selectTabAndIndex(String tab, int x, int y, int index) {
		HexData type = HexData.get(tab);
		Point displayPos = new Point(x,y);
		Point actualPos = Util.denormalizePos(displayPos, panel.getRecord().getZero());
		panel.recenter(actualPos, true);
		switch(type) {
		case NPC: {
			hexPanel.setSelectedNPC(index);
			selectTab(0,hexPanel.NPC_TAB_INDEX,index);break;
		}
		case LOCATION: {
			hexPanel.setSelectedPOI(index);
			selectTab(0,hexPanel.LOCATION_TAB_INDEX,index);break;
		}
		case DUNGEON: {
			hexPanel.setSelectedDungeon(index);
			selectTab(0,hexPanel.LOCATION_TAB_INDEX,index);break;
		}
		case ENCOUNTER: {
			hexPanel.setSelectedEncounter(index);
			selectTab(0,hexPanel.ENCOUNTER_TAB_INDEX,index);break;
		}
		case D_ENCOUNTER: {
			hexPanel.setSelectedEncounter(index);
			selectTab(0,hexPanel.ENCOUNTER_TAB_INDEX,index);break;
		}
		case MISSION: {
			hexPanel.setSelectedMission(index);
			selectTab(0,hexPanel.MISSIONS_TAB_INDEX,index);break;
		}
		case DISTRICT: selectTab(1,regionPanel.CITY_TAB_INDEX,index);break;
		case FACTION: {
			regionPanel.setSelectedFaction(index);
			selectTab(1,regionPanel.FACTION_TAB_INDEX,index);break;
		}
		case FAITH: {
			regionPanel.setSelectedFaith(index);
			selectTab(1,regionPanel.FAITH_TAB_INDEX,index);break;
		}
		case FACTION_NPC: {
			regionPanel.setSelectedFactionNPC(index);
			selectTab(1,regionPanel.FACTION_NPC_TAB_INDEX,index);break;
		}
		case MINION: {
			regionPanel.setSelectedMinion(index);
			selectTab(1,regionPanel.MINIONS_TAB_INDEX,index);break;
		}
		case THREATMONSTER:{
			regionPanel.setSelectedMonster(index);
			selectTab(1,regionPanel.BEASTS_TAB_INDEX,index);break;
		}
		case MONSTER: {
			regionPanel.setSelectedBeast(index);
			selectTab(1,regionPanel.BEASTS_TAB_INDEX,index);break;
		}
		case CHARACTER: selectTab(2,0,index);break;
		case TOWN: selectTab(1,regionPanel.CITY_TAB_INDEX,index);break;
		default: throw new IllegalArgumentException("unrecognized tab name: "+tab);
		}
		panel.preprocessThenRepaint();
	}

	private void selectTab(int maintab, int subtab,int index) {
		this.setSelectedIndex(maintab);
		if(maintab==0) {
			hexPanel.setDetailsTab(subtab);
		}else {
			regionPanel.setRegionTabs(subtab);
		}
	}
	public MapPanel getPanel() {
		return panel;
	}
	public boolean isChangeSelected() {
		return changeSelected;
	}

	@Override
	public void repaint() {
		super.repaint();
	}

	public void removeCharacter(int index) {
		panel.getController().removeData(HexData.CHARACTER, null, index);
		charactersList.remove(index).setVisible(false);
		if(charactersList.size()==0) charactersEmpty.setVisible(true);
	}

	public void addCharacter(HexData type, Point point, int index) {
		Point displayPoint = Util.normalizePos(point, panel.getRecord().getZero());
		Reference ref = new Reference(type,displayPoint,index);
		panel.getController().putData(HexData.CHARACTER, null, charactersList.size(), ref.toString());
		MyTextPane pane = new MyTextPane(this, charactersList.size(), HexData.CHARACTER);
		pane.setRef(ref);
		pane.doPaint();
		pane.setAlignmentX(LEFT_ALIGNMENT);
		pane.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
		pane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,Color.gray));
		charactersPanel.add(pane);
		charactersList.add(pane);
		charactersEmpty.setVisible(false);
	}

}
