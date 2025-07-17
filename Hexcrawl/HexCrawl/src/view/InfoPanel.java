package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;

import data.HexData;
import data.Reference;
import data.altitude.AltitudeModel;
import data.biome.BiomeModel;
import data.population.NPCSpecies;
import data.population.PopulationModel;
import data.population.SettlementSize;
import data.precipitation.PrecipitationModel;
import names.LocationNameModel;
import names.wilderness.WildernessNameGenerator;
import util.Util;
import view.infopanels.HexPanel;

@SuppressWarnings("serial")
public class InfoPanel extends JTabbedPane{
	private static final int RECENT_HISTORY_COUNT = 20;
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

	private int FAITH_TAB_INDEX;
	private int FACTION_TAB_INDEX;
	private int CITY_TAB_INDEX;
	private int MINIONS_TAB_INDEX;
	private int BEASTS_TAB_INDEX;
	private MapPanel panel;

	private JLabel biome;
	private JLabel magic;
	private JLabel cityName;
	//THREAT
	private MyTextPane threatText;
	private JScrollPane threatScrollPane;
	//ENCOUNTER
	private HexPanel hexPanel;
	private JPanel regionPanel;
	private JLabel locationName2;
	private MyTextPane city1;
	public boolean expandHexNotePane;
	public boolean hideHexNotePane;
	private JTabbedPane regionTabs;
	private JTextField regionNameField;
	private JLabel citySizeLabel;
	private ArrayList<MyTextPane> encounterTexts;
	private ArrayList<MyTextPane> factionTexts;
	int selectedNPC;
	int selectedDungeon;
	int selectedPOI;
	int selectedDEncounter;
	int selectedFaction;
	int selectedEncounter;
	private int selectedFaith;
	private int selectedMinion;
	private int selectedBeast;
	private int selectedFactionNPC;
	private int selectedMonster;
	private int selectedHistory;
	private int selectedCityHistory;
	boolean changeSelected;

	private MyTextPane charactersEmpty;
	private ArrayList<MyTextPane> charactersList;
	private ArrayList<MyTextPane> threadsList;
	private JPanel charactersPanel;
	private ArrayList<MyTextPane> faithsTexts;
	private JPanel encounterPanel;
	private ArrayList<MyTextPane> minionsTexts;
	private ArrayList<MyTextPane> beastsTexts;
	private ArrayList<MyTextPane> factionNPCTexts;
	private int FACTION_NPC_TAB_INDEX;
	private Container missionsPanel;
	private ArrayList<MyTextPane> missionsTexts;
	private int MISSIONS_TAB_INDEX;
	private ArrayList<MyTextPane> threatMonsterTexts;
	private ArrayList<MyTextPane> historyTexts;
	private ArrayList<MyTextPane> cityHistoryTexts;
	private MyTextPane event;

	public InfoPanel(MapPanel panel) {
		this.panel = panel;
		this.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
		this.setPreferredSize(new Dimension(INFOPANELWIDTH,800));
		this.setMaximumSize(new Dimension(INFOPANELWIDTH,99999));
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		//createHexTab(panel);
		hexPanel = new HexPanel(this);
		this.addTab(Util.pad("Hex", InfoPanel.TAB_TITLE_LENGTH), hexPanel);

		createRegionTab(panel);

		createCampaignTab();


		resetSelection();
		changeSelected = true;

		ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
		toolTipManager.setDismissDelay(Integer.MAX_VALUE); 
		toolTipManager.setInitialDelay(0);
	}

//	private void createHexTab(MapPanel panel) {
//		this.hexPanel = new JPanel();
//		hexPanel.setPreferredSize(new Dimension(INFOPANELWIDTH,INFOPANELWIDTH));
//		hexPanel.setLayout(new BoxLayout(hexPanel, BoxLayout.Y_AXIS));
//
//
//		hexGeneralPanel = new HexPanelGeneralStatPanel(panel.getController(),panel);
//		hexPanel.add(hexGeneralPanel);
//
//		demographicsPanel = new DemographicsPanel(panel);
//		hexPanel.add(demographicsPanel);
//
//		detailsTabs = new JTabbedPane();
//		getDetailsTab().setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
//
//		JPanel npcPanel = new JPanel();
//		npcPanel.setLayout(new BoxLayout(npcPanel, BoxLayout.Y_AXIS));
//		npcTexts = new ArrayList<MyTextPane>();
//		for(int i=0;i<NPCCOUNT;i++) {
//			npcPanel.add(new JLabel("~~~~~ NPC #"+(i+1)+" ~~~~~"));
//			MyTextPane npci = new MyTextPane(this, i, HexData.NPC);
//			npci.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
//			npcPanel.add(npci);
//			npcTexts.add(npci);
//		}
//		proprietorTexts = new ArrayList<MyTextPane>();
//		for(int i=0;i<POICOUNT;i++) {
//			npcPanel.add(new JLabel("~~~~~ Proprietor #"+(i+1)+" ~~~~~"));
//			MyTextPane npci = new MyTextPane(this, i, HexData.PROPRIETOR);
//			npci.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
//			npcPanel.add(npci);
//			proprietorTexts.add(npci);
//		}
//		JScrollPane npcScrollPane = new JScrollPane(npcPanel);
//		npcScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		getDetailsTab().addTab("NPCs", npcScrollPane);
//		this.NPC_TAB_INDEX = getDetailsTab().getTabCount()-1;
//
//
//		JPanel poiPanel = new JPanel();
//		poiPanel.setLayout(new BoxLayout(poiPanel, BoxLayout.Y_AXIS));
//		poiTexts = new ArrayList<MyTextPane>();
//		poiPanel.add(new JLabel("~~~~~ Inn ~~~~~"));
//		MyTextPane inn = new MyTextPane(this, 0, HexData.LOCATION);
//		inn.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
//		poiPanel.add(inn);
//		poiTexts.add(inn);
//		for(int i=1;i<POICOUNT;i++) {
//			poiPanel.add(new JLabel("~~~~~ Point of Interest #"+(i)+" ~~~~~"));
//			MyTextPane poii = new MyTextPane(this, i, HexData.LOCATION);
//			poii.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
//			poiPanel.add(poii);
//			poiTexts.add(poii);
//		}
//		JScrollPane poiScrollPane = new JScrollPane(poiPanel);
//		poiScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		getDetailsTab().addTab("Locations", poiScrollPane);
//		this.LOCATION_TAB_INDEX = getDetailsTab().getTabCount()-1;
//
//		//JPanel dEntrancePanel = new JPanel();
//		//dEntrancePanel.setLayout(new BoxLayout(dEntrancePanel, BoxLayout.Y_AXIS));
//		dEntranceTexts = new ArrayList<MyTextPane>();
//		for(int i=0;i<DUNGEONCOUNT;i++) {
//			poiPanel.add(new JLabel("~~~~~ Dungeon #"+(i+1)+" ~~~~~"));
//			MyTextPane poii = new MyTextPane(this, i, HexData.DUNGEON);
//			poii.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
//			poiPanel.add(poii);
//			dEntranceTexts.add(poii);
//		}
//		//JScrollPane dEntranceScrollPane = new JScrollPane(dEntrancePanel);
//		//dEntranceScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		//detailsTabs.addTab("Dungeon", dEntranceScrollPane);
//		this.DUNGEON_TAB_INDEX = getDetailsTab().getTabCount()-1;
//
//		JPanel hexNotePanel = new JPanel();
//		hexNotePanel.setLayout(new BorderLayout());
//		JPanel highlightPanel = new JPanel();
//		highlightPanel.add(new JLabel("highlight:"));
//		JComboBox<HighlightColor> highlightMenu = new JComboBox<HighlightColor>(HighlightColor.values());
//		highlightMenu.setMaximumSize(new Dimension(100,20));
//		highlightMenu.addActionListener(new ActionListener(){
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				panel.setHighlight(((HighlightColor) highlightMenu.getSelectedItem()).getColor());
//			}
//		});
//		highlightPanel.add(highlightMenu);
//		hexNotePanel.add(highlightPanel,BorderLayout.NORTH);
//		hexNote1 = new MyTextPane(this, -1, HexData.NONE);
//		hexNotePanel.add(hexNote1,BorderLayout.CENTER);
//		JScrollPane hexNoteScrollPane = new JScrollPane(hexNotePanel);
//		getDetailsTab().addTab("Notes", hexNoteScrollPane);
//
//
//		encounterPanel = new JPanel();
//		encounterPanel.setLayout(new BoxLayout(encounterPanel, BoxLayout.Y_AXIS));
//		encounterTexts = new ArrayList<MyTextPane>();
//		for(int i=0;i<panel.getRecord().getEncounters(panel.getSelectedGridPoint()).size();i++) {
//			MyTextPane pane = createEncounter();
//			pane.doPaint();
//		}
//		JScrollPane encounterScrollPane = new JScrollPane(encounterPanel);
//		encounterScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		encounterScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		getDetailsTab().addTab("Encounters", encounterScrollPane);
//		this.ENCOUNTER_TAB_INDEX = getDetailsTab().getTabCount()-1;
//
//		dungeonPanel = new JPanel();
//		dungeonPanel.setLayout(new BoxLayout(dungeonPanel, BoxLayout.Y_AXIS));
////		dungeonTexts = new ArrayList<MyTextPane>();
////		for(int i=0;i<panel.getRecord().getDungeonEncounters(panel.getSelectedGridPoint()).size();i++) {
////			MyTextPane pane = createDungeonEncounter();
////			pane.doPaint();
////		}
////		JScrollPane dungeonScrollPane = new JScrollPane(dungeonPanel);
////		dungeonScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		//detailsTabs.addTab("D.Encounters", dungeonScrollPane);
//		this.DUNGEON_ENCOUNTER_TAB_INDEX = getDetailsTab().getTabCount()-1;
//		
//
//		missionsPanel = new JPanel();
//		missionsPanel.setLayout(new BoxLayout(missionsPanel, BoxLayout.Y_AXIS));
//		missionsTexts = new ArrayList<MyTextPane>();
//		for(int i=0;i<panel.getRecord().getMissions(panel.getSelectedGridPoint()).size()||i<3;i++) {
//			MyTextPane pane = createMission();
//			pane.doPaint();
//		}
//		JScrollPane missionsScrollPane = new JScrollPane(missionsPanel);
//		missionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		missionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		getDetailsTab().addTab("Missions", missionsScrollPane);
//		this.MISSIONS_TAB_INDEX = getDetailsTab().getTabCount()-1;
//
//		hexPanel.add(getDetailsTab());
//
//		this.addTab(Util.pad("Hex", TAB_TITLE_LENGTH), hexPanel);
//	}

	public MyTextPane createDungeonEncounter() {
		int i = encounterTexts.size();
		MyTextPane encounteri = new MyTextPane(this, i, HexData.D_ENCOUNTER);
		encounteri.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
		encounteri.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		encounterPanel.add(encounteri,0);
		encounterTexts.add(encounteri);
		return encounteri;
	}

	public MyTextPane createEncounter() {
		int i = encounterTexts.size();
		MyTextPane encounteri = new MyTextPane(this, i, HexData.ENCOUNTER);
		encounteri.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
		encounteri.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		encounterPanel.add(encounteri,0);
		encounterTexts.add(encounteri);
		return encounteri;
	}

	public MyTextPane createMission() {
		int i = missionsTexts.size();
		MyTextPane encounteri = new MyTextPane(this, i, HexData.MISSION);
		encounteri.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
		encounteri.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		missionsPanel.add(encounteri,0);
		missionsTexts.add(encounteri);
		return encounteri;
	}

	public void removeDungeonEncounter(int index) {
		panel.getController().removeData(HexData.D_ENCOUNTER, panel.getSelectedGridPoint(), index);
		encounterTexts.remove(encounterTexts.size()-1).setVisible(false);
		repaint();
	}

	public void removeEncounter(int index) {
		panel.getController().removeData(HexData.ENCOUNTER, panel.getSelectedGridPoint(), index);
		encounterTexts.remove(encounterTexts.size()-1).setVisible(false);
		repaint();
	}

	private void createRegionTab(MapPanel panel) {
		this.regionPanel = new JPanel();
		regionPanel.setPreferredSize(new Dimension(300,300));
		regionPanel.setLayout(new BoxLayout(regionPanel, BoxLayout.Y_AXIS));

		JPanel dummy4 = new JPanel(new BorderLayout());
		JPanel regionNamePanel = new JPanel();
		regionNamePanel.setLayout(new BoxLayout(regionNamePanel, BoxLayout.X_AXIS));
		locationName2 = new JLabel("name");
		regionNamePanel.add(locationName2);
		regionNameField = new JTextField();
		regionNameField.setMaximumSize(new Dimension(300,20));
		regionNamePanel.add(regionNameField);
		regionNameField.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				String text = regionNameField.getText();
				Point pos = panel.getController().getBiomes().getAbsoluteRegion(panel.getSelectedGridPoint());
				PopulationModel pop = panel.getController().getPopulation();
				String defaultText = getDefaultRegionNameText(pos,pop.isTown(pos));
				if(text==null||"".equals(text)||text.equals(defaultText)) panel.getRecord().removeRegionName(pos);
				else panel.getRecord().putRegionName(pos, text);
			}
			public void focusGained(FocusEvent e) {}
		});
		citySizeLabel = new JLabel();
		regionNamePanel.add(citySizeLabel);
		dummy4.add(regionNamePanel,BorderLayout.NORTH);

		biome = new JLabel("Grassland");
		biome.setMaximumSize(new Dimension(400,20));
		dummy4.add(biome,BorderLayout.CENTER);

		magic = new JLabel("Normal Magic");
		dummy4.add(magic,BorderLayout.SOUTH);

		dummy4.setMaximumSize(new Dimension(400,65));
		regionPanel.add(dummy4);
		regionPanel.add(getSeparator());

		threatText = new MyTextPane(this, 0, HexData.THREAT);
		threatScrollPane = new JScrollPane(threatText);
		threatScrollPane.setMaximumSize(new Dimension(9999,375));
		threatScrollPane.setPreferredSize(new Dimension(9999,375));
		regionPanel.add(threatScrollPane);

		regionPanel.add(getSeparator());

		cityName = new JLabel("name");
		regionPanel.add(cityName);


		//City tab
		regionTabs = new JTabbedPane();
		city1 = new MyTextPane(this, 0, HexData.CITY);
		JScrollPane cityScrollPane = new JScrollPane(city1);
		regionTabs.addTab("Parent City", cityScrollPane);
		this.CITY_TAB_INDEX = regionTabs.getTabCount()-1;

		//Faction tab
		JPanel factionPanel = new JPanel();
		factionPanel.setLayout(new BoxLayout(factionPanel, BoxLayout.Y_AXIS));
		factionTexts = new ArrayList<MyTextPane>();
		factionPanel.add(new JLabel("~~~~~ City Leadership ~~~~~"));
		MyTextPane leadership = new MyTextPane(this, 0, HexData.FACTION);
		leadership.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
		factionPanel.add(leadership);
		factionTexts.add(leadership);
		for(int i=1;i<FACTIONCOUNT;i++) {
			factionPanel.add(new JLabel("~~~~~ Faction #"+(i)+" ~~~~~"));
			MyTextPane factioni = new MyTextPane(this, i, HexData.FACTION);
			factioni.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
			factionPanel.add(factioni);
			factionTexts.add(factioni);
		}
		JScrollPane factionScrollPane = new JScrollPane(factionPanel);
		factionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		regionTabs.addTab("Factions", factionScrollPane);
		this.FACTION_TAB_INDEX = regionTabs.getTabCount()-1;

		//Faiths tab
		//JPanel faithsPanel = new JPanel();
		//faithsPanel.setLayout(new BoxLayout(faithsPanel, BoxLayout.Y_AXIS));
		faithsTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<FAITHCOUNT;i++) {
			factionPanel.add(new JLabel("~~~~~ Faith #"+(i+1)+" ~~~~~"));
			MyTextPane factioni = new MyTextPane(this, i, HexData.FAITH);
			factioni.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
			factionPanel.add(factioni);
			faithsTexts.add(factioni);
		}
		//JScrollPane faithsScrollPane = new JScrollPane(faithsPanel);
		//faithsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//regionTabs.addTab("Faiths", faithsScrollPane);
		this.FAITH_TAB_INDEX = regionTabs.getTabCount()-1;


		//Faction NPCs tab
		JPanel factionNPCPanel = new JPanel();
		factionNPCPanel.setLayout(new BoxLayout(factionNPCPanel, BoxLayout.Y_AXIS));
		factionNPCTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<(FACTIONCOUNT+FAITHCOUNT)*2;i++) {
			factionNPCPanel.add(new JLabel("~~~~~ NPC #"+(i+1)+" ~~~~~"));
			MyTextPane minion = new MyTextPane(this, i, HexData.FACTION_NPC);
			minion.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
			factionNPCPanel.add(minion);
			factionNPCTexts.add(minion);
		}
		JScrollPane factionNPCScrollPane = new JScrollPane(factionNPCPanel);
		factionNPCScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		regionTabs.addTab("NPCs", factionNPCScrollPane);
		this.FACTION_NPC_TAB_INDEX = regionTabs.getTabCount()-1;


		//Minions tab
		JPanel minionsPanel = new JPanel();
		minionsPanel.setLayout(new BoxLayout(minionsPanel, BoxLayout.Y_AXIS));
		minionsTexts = new ArrayList<MyTextPane>();
		minionsPanel.add(new JLabel("~~~~~ Faction ~~~~~"));
		MyTextPane threatFaction = new MyTextPane(this, 0, HexData.MINION);
		threatFaction.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
		minionsPanel.add(threatFaction);
		minionsTexts.add(threatFaction);
		for(int i=1;i<NPCCOUNT;i++) {
			minionsPanel.add(new JLabel("~~~~~ Minion #"+(i)+" ~~~~~"));
			MyTextPane minion = new MyTextPane(this, i, HexData.MINION);
			minion.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
			minionsPanel.add(minion);
			minionsTexts.add(minion);
		}
		JScrollPane minionsScrollPane = new JScrollPane(minionsPanel);
		minionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		regionTabs.addTab("Minions", minionsScrollPane);
		this.MINIONS_TAB_INDEX = regionTabs.getTabCount()-1;


		//Wandering Monsters tab
		JPanel beastsPanel = new JPanel();
		beastsPanel.setLayout(new BoxLayout(beastsPanel, BoxLayout.Y_AXIS));
		beastsTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<MONSTERCOUNT;i++) {
			beastsPanel.add(new JLabel("~~~~~ Regional Monster #"+(i+1)+" ~~~~~"));
			MyTextPane beast = new MyTextPane(this, i, HexData.MONSTER);
			beast.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
			beastsPanel.add(beast);
			beastsTexts.add(beast);
		}
		threatMonsterTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<MONSTERCOUNT;i++) {
			beastsPanel.add(new JLabel("~~~~~ Threat Monster #"+(i+1)+" ~~~~~"));
			MyTextPane beast = new MyTextPane(this, i, HexData.THREATMONSTER);
			beast.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
			beastsPanel.add(beast);
			threatMonsterTexts.add(beast);
		}
		JScrollPane beastsScrollPane = new JScrollPane(beastsPanel);
		beastsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		regionTabs.addTab("Monsters", beastsScrollPane);
		this.BEASTS_TAB_INDEX = regionTabs.getTabCount()-1;
		
		//History tab
		JPanel historyPanel = new JPanel();
		historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
		historyPanel.add(new JLabel("~~~~~ Current Events ~~~~~"));
		event = new MyTextPane(this, 0, HexData.EVENT);
		event.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
		event.setPreferredSize(new Dimension(INFOPANELWIDTH-30,5));
		event.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		historyPanel.add(event);
		
		cityHistoryTexts = new ArrayList<MyTextPane>();
		historyPanel.add(new JLabel("~~~~~ Recent History ~~~~~"));
		for(int i=0;i<RECENT_HISTORY_COUNT;i++) {
			MyTextPane history = new MyTextPane(this, i, HexData.CITYHISTORY);
			history.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
			history.setPreferredSize(new Dimension(INFOPANELWIDTH-30,5));
			history.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
			historyPanel.add(history);
			cityHistoryTexts.add(history);
		}
		historyTexts = new ArrayList<MyTextPane>();
		historyPanel.add(new JLabel("~~~~~ Ancient History ~~~~~"));
		for(int i=0;i<4;i++) {
			MyTextPane history = new MyTextPane(this, i, HexData.HISTORY);
			history.setMaximumSize(new Dimension(INFOPANELWIDTH-30,9999));
			history.setPreferredSize(new Dimension(INFOPANELWIDTH-30,5));
			history.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
			historyPanel.add(history);
			historyTexts.add(history);
		}
		historyPanel.add(Box.createVerticalGlue());
		JScrollPane historyScrollPane = new JScrollPane(historyPanel);
		historyScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		regionTabs.addTab("History", historyScrollPane);
		

		regionPanel.add(regionTabs);

		this.addTab(Util.pad("Region", TAB_TITLE_LENGTH), new JScrollPane(regionPanel));
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
		charactersEmpty = new MyTextPane(this, chars.size(), HexData.CHARACTER);
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
		//		threadsPanel.setPreferredSize(new Dimension(INFOPANELWIDTH-30,999));
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


	public void resetSelection() {
		selectedEncounter = -1;
		selectedNPC = -1;
		selectedDEncounter = -1;
		selectedDungeon = -1;
		selectedPOI = -1;
		selectedFaction = -1;
		selectedFaith = -1;
		selectedMinion = -1;
		selectedBeast = -1;
		selectedFactionNPC = -1;
		selectedMonster = -1;
		selectedHistory = -1;
		selectedCityHistory = -1;
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		changeSelected = false;
		Point pos = panel.getSelectedGridPoint();
		AltitudeModel grid = panel.getController().getGrid();
		PrecipitationModel precipitation = panel.getController().getPrecipitation();
		BiomeModel biomes = panel.getController().getBiomes();
		PopulationModel population = panel.getController().getPopulation();
		Point zero = panel.getRecord().getZero();

		NPCSpecies species = population.getMajoritySpecies(pos.x, pos.y);
		float pop = population.getUniversalPopulation(pos);
		int popScale = population.getPopScale(pos) ;
		int popValue = population.demoTransformInt(pop, popScale);
		Point capital = population.getAbsoluteFealty(pos);
		Point region = biomes.getAbsoluteRegion(pos);
		if(species!=null) {
			if(population.isCity(pos)) {
				String cityname = getRegionNameText(capital,true);
				SettlementSize size = SettlementSize.getSettlementSize(popValue);
				String cityText = "Parent City: here!";
				this.locationName2.setText("CITY NAME: ★ ");
				this.regionNameField.setText(cityname);
				this.citySizeLabel.setText(" ("+size.getName()+")");
				this.cityName.setText(cityText);
			}else if(population.isTown(pos)){
				String cityname = getRegionNameText(capital,true)+" ("+Util.posString(capital,zero)+")";
				if(!population.isCity(capital))cityname = "None";
				SettlementSize size = SettlementSize.getSettlementSize(popValue);
				String townname = getRegionNameText(pos,true);
				String cityText = "Parent City: "+cityname;
				this.locationName2.setText("Town Name: ⬤ ");
				this.regionNameField.setText(townname);
				this.citySizeLabel.setText(" ("+size.getName()+")");
				this.cityName.setText(cityText);
			}else {
				String cityname = getRegionNameText(capital,true)+" ("+Util.posString(capital,zero)+")";
				if(!population.isCity(capital))cityname = "None";
				String locationname = getRegionNameText(region,false);
				String cityText = "Parent City: "+cityname;
				this.locationName2.setText("Region Name: ");
				this.regionNameField.setText(locationname);
				this.citySizeLabel.setText(null);
				this.cityName.setText(cityText);
			}
		}else {
			String locationname = getRegionNameText(region,false);
			String cityText = "Parent City: none";
			this.locationName2.setText("Region Name: ");
			this.regionNameField.setText(locationname);
			this.citySizeLabel.setText(null);
			this.cityName.setText(cityText);
		}

		String biome = panel.getController().getBiomeText(pos);
		this.biome.setText("Biome Type: "+biome);

		String magics = panel.getController().getMagic().getMagicType(pos).getName();
		this.magic.setText("Magic Type: "+magics);

		threatText.doPaint();
		city1.doPaint();

		if(grid.isWater(pos)||precipitation.isLake(pos)) {
			enableCityTabs(false);
		}else {
			if(population.isCity(capital)) {
				enableCityTabs(true);
			}else {
				enableCityTabs(false);
			}
		}
		for(int i=0;i<threadsList.size();i++) {
			this.threadsList.get(i).doPaint();
		}
		for(int i=0;i<charactersList.size();i++) {
			this.charactersList.get(i).doPaint();
		}
		for(int i = 0;i<this.factionTexts.size();i++) {
			MyTextPane pane = this.factionTexts.get(i);
			pane.setHighlight(i==selectedFaction);
			pane.doPaint();
		}

		for(int i = 0;i<this.faithsTexts.size();i++) {
			MyTextPane pane = this.faithsTexts.get(i);
			pane.setHighlight(i==selectedFaith);
			pane.doPaint();
		}

		for(int i = 0;i<this.factionNPCTexts.size();i++) {
			MyTextPane pane = this.factionNPCTexts.get(i);
			pane.setHighlight(i==selectedFactionNPC);
			pane.doPaint();
		}

		for(int i = 0;i<this.minionsTexts.size();i++) {
			MyTextPane pane = this.minionsTexts.get(i);
			pane.setHighlight(i==selectedMinion);
			pane.doPaint();
		}

		for(int i = 0;i<this.beastsTexts.size();i++) {
			MyTextPane pane = this.beastsTexts.get(i);
			pane.setHighlight(i==selectedBeast);
			pane.doPaint();
		}

		for(int i = 0;i<this.threatMonsterTexts.size();i++) {
			MyTextPane pane = this.threatMonsterTexts.get(i);
			pane.setHighlight(i==selectedMonster);
			pane.doPaint();
		}
		
		event.doPaint();

		for(int i = 0;i<this.cityHistoryTexts.size();i++) {
			MyTextPane pane = this.cityHistoryTexts.get(i);
			pane.setHighlight(i==selectedCityHistory);
			pane.doPaint();
			if(pane.getRawText()==null) {
				pane.setVisible(false);
			}else {
				pane.setVisible(true);
			}
		}

		for(int i = 0;i<this.historyTexts.size();i++) {
			MyTextPane pane = this.historyTexts.get(i);
			pane.setHighlight(i==selectedHistory);
			pane.doPaint();
		}
		changeSelected = true;
	}

	private String getRegionNameText(Point pos,boolean isCity) {
		String regionNameText = panel.getRecord().getRegionName(pos);
		if(regionNameText==null) regionNameText = getDefaultRegionNameText(pos,isCity);
		return regionNameText;
	}

	private String getDefaultRegionNameText(Point pos,boolean isCity) {
		if(isCity) {
			NPCSpecies species = panel.getController().getPopulation().getMajoritySpecies(pos.x, pos.y);
			try {
				LocationNameModel names = panel.getController().getNames();
				return names.getName(species.getCityNameGen(), pos);
			}catch (NullPointerException e) {
				System.err.println("population not found:"+panel.getRecord().normalizePOS(pos));
				return "None";
			}
		}else {
			BiomeModel biomes = panel.getController().getBiomes();
			Point region = biomes.getAbsoluteRegion(pos);
			return biomes.getRegionName(region)+" " + WildernessNameGenerator.getBiomeName(biomes.getBiome(pos));
		}
	}

	private void enableCityTabs(boolean enabled) {
		//regionTabs.setEnabledAt(CITY_TAB_INDEX, enabled);
		//regionTabs.setEnabledAt(FACTION_TAB_INDEX, enabled);
		city1.setEnabled(enabled);
		if(!enabled) {
			this.city1.setText("None");
		}
	}



	public void setPanel(MapPanel panel) {
		this.panel = panel;
	}

	private JSeparator getSeparator() {
		JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
		separator.setMaximumSize(new Dimension(9999,1));
		return separator;
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
			selectTab(0,MISSIONS_TAB_INDEX,index);break;
		}
		case DISTRICT: selectTab(1,CITY_TAB_INDEX,index);break;
		case FACTION: {
			selectedFaction=index;
			selectTab(1,FACTION_TAB_INDEX,index);break;
		}
		case FAITH: {
			selectedFaith=index;
			selectTab(1,FAITH_TAB_INDEX,index);break;
		}
		case FACTION_NPC: {
			selectedFactionNPC=index;
			selectTab(1,FACTION_NPC_TAB_INDEX,index);break;
		}
		case MINION: {
			selectedMinion=index;
			selectTab(1,MINIONS_TAB_INDEX,index);break;
		}
		case THREATMONSTER:{
			selectedMonster=index;
			selectTab(1,BEASTS_TAB_INDEX,index);break;
		}
		case MONSTER: {
			selectedBeast=index;
			selectTab(1,BEASTS_TAB_INDEX,index);break;
		}
		case CHARACTER: selectTab(2,0,index);break;
		case TOWN: selectTab(1,CITY_TAB_INDEX,index);break;
		default: throw new IllegalArgumentException("unrecognized tab name: "+tab);
		}
		panel.preprocessThenRepaint();
	}

	private void selectTab(int maintab, int subtab,int index) {
		this.setSelectedIndex(maintab);
		if(maintab==0) {
			hexPanel.setDetailsTab(subtab);
		}else {
			regionTabs.setSelectedIndex(subtab);
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
