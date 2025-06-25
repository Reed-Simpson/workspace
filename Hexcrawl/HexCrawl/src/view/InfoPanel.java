package view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JComboBox;
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
import data.magic.MagicModel;
import data.population.PopulationModel;
import data.population.SettlementSize;
import data.population.Species;
import data.precipitation.PrecipitationModel;
import names.LocationNameModel;
import names.wilderness.WildernessNameGenerator;
import util.Util;
import view.infopanels.DemographicsPanel;
import view.infopanels.HexPanelGeneralStatPanel;

@SuppressWarnings("serial")
public class InfoPanel extends JTabbedPane{
	private static final int TAB_TITLE_LENGTH = 34;
	private static final Color TEXTBACKGROUNDCOLOR = Color.WHITE;
	private static final Color TEXTHIGHLIGHTCOLOR = Color.getHSBColor(65f/360, 20f/100, 100f/100);
	public static final int INFOPANELWIDTH = 450;
	public static final int ENCOUNTERCOUNT = 20;
	public static final int NPCCOUNT = 20;
	public static final int POICOUNT = 20;
	public static final int DUNGEONCOUNT = 6;
	public static final int FACTIONCOUNT = 6;
	public static final int DISTRICTCOUNT = 6;
	
	private int FAITH_TAB_INDEX;
	private int FACTION_TAB_INDEX;
	private int CITY_TAB_INDEX;
	private int DUNGEON_ENCOUNTER_TAB_INDEX;
	private int DUNGEON_TAB_INDEX;
	private int LOCATION_TAB_INDEX;
	private int NPC_TAB_INDEX;
	private int ENCOUNTER_TAB_INDEX;
	private MapPanel panel;

	private JLabel biome;
	private JLabel magic;
	private JLabel cityName;
	//THREAT
	private MyTextPane threatText;
	private JScrollPane threatScrollPane;
	//ENCOUNTER
	private JScrollPane npcScrollPane;
	private JPanel hexPanel;
	private JPanel regionPanel;
	private JLabel locationName2;
	private MyTextPane city1;
	private JScrollPane cityScrollPane;
	private JScrollPane poiScrollPane;
//	private JScrollPane dungeonScrollPane;
	private MyTextPane hexNote1;
	private JScrollPane hexNoteScrollPane;
	public boolean expandHexNotePane;
	public boolean hideHexNotePane;
	private JTabbedPane detailsTabs;
	private int detailsSelectedIndex;
	private JTabbedPane regionTabs;
	private JTextField regionNameField;
	private JLabel citySizeLabel;
//	private ArrayList<MyTextPane> encounterTexts;
	private ArrayList<MyTextPane> npcTexts;
//	private ArrayList<MyTextPane> dungeonTexts;
	private ArrayList<MyTextPane> poiTexts;
	private ArrayList<MyTextPane> dEntranceTexts;
	private JScrollPane dEntranceScrollPane;
	private ArrayList<MyTextPane> factionTexts;
	private JScrollPane factionScrollPane;
	int selectedNPC;
	int selectedDungeon;
	int selectedPOI;
//	int selectedDEncounter;
	int selectedFaction;
//	int selectedEncounter;
	boolean changeSelected;

	private HexPanelGeneralStatPanel hexGeneralPanel;
	private DemographicsPanel demographicsPanel;
//	private JScrollPane encounterScrollPane;
	private MyTextPane charactersEmpty;
	private ArrayList<MyTextPane> charactersList;
	private ArrayList<MyTextPane> threadsList;
	private JPanel charactersPanel;
	private ArrayList<MyTextPane> faithsTexts;
	private JScrollPane faithsScrollPane;
	private int selectedFaith;

	public InfoPanel(MapPanel panel) {
		this.panel = panel;
		this.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
		this.setPreferredSize(new Dimension(INFOPANELWIDTH,800));
		this.setMaximumSize(new Dimension(INFOPANELWIDTH,99999));

		createHexTab(panel);
		
		createRegionTab(panel);
		
		createCampaignTab();
		

		resetSelection();
		changeSelected = true;

		ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
		toolTipManager.setDismissDelay(Integer.MAX_VALUE); 
		toolTipManager.setInitialDelay(0);
	}

	private void createHexTab(MapPanel panel) {
		this.hexPanel = new JPanel();
		hexPanel.setPreferredSize(new Dimension(INFOPANELWIDTH,INFOPANELWIDTH));
		hexPanel.setLayout(new BoxLayout(hexPanel, BoxLayout.Y_AXIS));
		

		hexGeneralPanel = new HexPanelGeneralStatPanel(this);
		hexPanel.add(hexGeneralPanel);
		
		demographicsPanel = new DemographicsPanel(this);
		hexPanel.add(demographicsPanel);

		detailsTabs = new JTabbedPane();

//		JPanel encounterPanel = new JPanel();
//		encounterPanel.setLayout(new BoxLayout(encounterPanel, BoxLayout.Y_AXIS));
//		encounterTexts = new ArrayList<MyTextPane>();
//		for(int i=0;i<ENCOUNTERCOUNT;i++) {
//			encounterPanel.add(new JLabel("~~~~~ Encounter #"+(i+1)+" ~~~~~"));
//			MyTextPane encounteri = new MyTextPane(this, i, HexData.ENCOUNTER);
//			encounteri.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
//			encounterPanel.add(encounteri);
//			encounterTexts.add(encounteri);
//		}
//		encounterScrollPane = new JScrollPane(encounterPanel);
//		encounterScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		encounterScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		detailsTabs.addTab("Encounters", encounterScrollPane);
//		this.ENCOUNTER_TAB_INDEX = detailsTabs.getTabCount();

		JPanel npcPanel = new JPanel();
		npcPanel.setLayout(new BoxLayout(npcPanel, BoxLayout.Y_AXIS));
		npcTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<NPCCOUNT;i++) {
			npcPanel.add(new JLabel("~~~~~ NPC #"+(i+1)+" ~~~~~"));
			MyTextPane npci = new MyTextPane(this, i, HexData.NPC);
			npci.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
			npcPanel.add(npci);
			npcTexts.add(npci);
		}
		npcScrollPane = new JScrollPane(npcPanel);
		npcScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		detailsTabs.addTab("NPCs", npcScrollPane);
		this.NPC_TAB_INDEX = detailsTabs.getTabCount()-1;
		System.out.println("NPC Tab index: "+NPC_TAB_INDEX);


		JPanel poiPanel = new JPanel();
		poiPanel.setLayout(new BoxLayout(poiPanel, BoxLayout.Y_AXIS));
		poiTexts = new ArrayList<MyTextPane>();
		poiPanel.add(new JLabel("~~~~~ Inn ~~~~~"));
		MyTextPane inn = new MyTextPane(this, 0, HexData.LOCATION);
		inn.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
		poiPanel.add(inn);
		poiTexts.add(inn);
		for(int i=1;i<POICOUNT;i++) {
			poiPanel.add(new JLabel("~~~~~ Point of Interest #"+(i)+" ~~~~~"));
			MyTextPane poii = new MyTextPane(this, i, HexData.LOCATION);
			poii.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
			poiPanel.add(poii);
			poiTexts.add(poii);
		}
		poiScrollPane = new JScrollPane(poiPanel);
		poiScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		detailsTabs.addTab("Locations", poiScrollPane);
		this.LOCATION_TAB_INDEX = detailsTabs.getTabCount()-1;
		System.out.println("locations Tab index: "+LOCATION_TAB_INDEX);

		JPanel dEntrancePanel = new JPanel();
		dEntrancePanel.setLayout(new BoxLayout(dEntrancePanel, BoxLayout.Y_AXIS));
		dEntranceTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<DUNGEONCOUNT;i++) {
			dEntrancePanel.add(new JLabel("~~~~~ Dungeon #"+(i+1)+" ~~~~~"));
			MyTextPane poii = new MyTextPane(this, i, HexData.DUNGEON);
			poii.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
			dEntrancePanel.add(poii);
			dEntranceTexts.add(poii);
		}
		dEntranceScrollPane = new JScrollPane(dEntrancePanel);
		dEntranceScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		detailsTabs.addTab("Dungeon", dEntranceScrollPane);
		this.DUNGEON_TAB_INDEX = detailsTabs.getTabCount()-1;
		System.out.println("dungeons Tab index: "+DUNGEON_TAB_INDEX);

//		JPanel dungeonPanel = new JPanel();
//		dungeonPanel.setLayout(new BoxLayout(dungeonPanel, BoxLayout.Y_AXIS));
//		dungeonTexts = new ArrayList<MyTextPane>();
//		for(int i=0;i<ENCOUNTERCOUNT;i++) {
//			dungeonPanel.add(new JLabel("~~~~~ Dungeon Encounter #"+(i+1)+" ~~~~~"));
//			MyTextPane encounteri = new MyTextPane(this, i, HexData.D_ENCOUNTER);
//			encounteri.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
//			dungeonPanel.add(encounteri);
//			dungeonTexts.add(encounteri);
//		}
//		dungeonScrollPane = new JScrollPane(dungeonPanel);
//		dungeonScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		detailsTabs.addTab("D.Encounters", dungeonScrollPane);
//		this.DUNGEON_ENCOUNTER_TAB_INDEX = detailsTabs.getTabCount();

		JPanel hexNotePanel = new JPanel();
		hexNotePanel.setLayout(new BorderLayout());
		JPanel highlightPanel = new JPanel();
		highlightPanel.add(new JLabel("highlight:"));
		JComboBox<HighlightColor> highlightMenu = new JComboBox<HighlightColor>(HighlightColor.values());
		highlightMenu.setMaximumSize(new Dimension(100,20));
		highlightMenu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.setHighlight(((HighlightColor) highlightMenu.getSelectedItem()).getColor());
			}
		});
		highlightPanel.add(highlightMenu);
		hexNotePanel.add(highlightPanel,BorderLayout.NORTH);
		hexNote1 = new MyTextPane(this, -1, HexData.NONE);
		hexNotePanel.add(hexNote1,BorderLayout.CENTER);
		hexNoteScrollPane = new JScrollPane(hexNotePanel);
		detailsTabs.addTab("Notes", hexNoteScrollPane);

		hexPanel.add(detailsTabs);

		this.addTab(Util.pad("Hex", TAB_TITLE_LENGTH), new JScrollPane(hexPanel));
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

		threatText = new MyTextPane(this, -1, HexData.THREAT);
		threatScrollPane = new JScrollPane(threatText);
		threatScrollPane.setMaximumSize(new Dimension(9999,125));
		threatScrollPane.setPreferredSize(new Dimension(9999,125));
		regionPanel.add(threatScrollPane);

		regionPanel.add(getSeparator());

		cityName = new JLabel("name");
		regionPanel.add(cityName);


		//City tab
		regionTabs = new JTabbedPane();
		city1 = new MyTextPane(this, -1, HexData.CITY);
		cityScrollPane = new JScrollPane(city1);
		regionTabs.addTab("Parent City", cityScrollPane);
		this.CITY_TAB_INDEX = regionTabs.getTabCount()-1;
		System.out.println("city Tab index: "+CITY_TAB_INDEX);

		//Faction tab
		JPanel factionPanel = new JPanel();
		factionPanel.setLayout(new BoxLayout(factionPanel, BoxLayout.Y_AXIS));
		factionTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<FACTIONCOUNT;i++) {
			factionPanel.add(new JLabel("~~~~~ Faction #"+(i+1)+" ~~~~~"));
			MyTextPane factioni = new MyTextPane(this, i, HexData.FACTION);
			factioni.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
			factionPanel.add(factioni);
			factionTexts.add(factioni);
		}
		factionScrollPane = new JScrollPane(factionPanel);
		factionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		regionTabs.addTab("Factions", factionScrollPane);
		this.FACTION_TAB_INDEX = regionTabs.getTabCount()-1;
		System.out.println("factions Tab index: "+FACTION_TAB_INDEX);
		
		//Faiths tab
		JPanel faithsPanel = new JPanel();
		faithsPanel.setLayout(new BoxLayout(faithsPanel, BoxLayout.Y_AXIS));
		faithsTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<FACTIONCOUNT;i++) {
			faithsPanel.add(new JLabel("~~~~~ Faith #"+(i+1)+" ~~~~~"));
			MyTextPane factioni = new MyTextPane(this, i, HexData.FAITH);
			factioni.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
			faithsPanel.add(factioni);
			faithsTexts.add(factioni);
		}
		faithsScrollPane = new JScrollPane(faithsPanel);
		faithsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		regionTabs.addTab("Faiths", faithsScrollPane);
		this.FAITH_TAB_INDEX = regionTabs.getTabCount()-1;
		System.out.println("faiths Tab index: "+FAITH_TAB_INDEX);

		
		regionPanel.add(regionTabs);

		this.addTab(Util.pad("Region", TAB_TITLE_LENGTH), new JScrollPane(regionPanel));
	}

	private void createCampaignTab() {
		JPanel campaignPanel = new JPanel();
		campaignPanel.setLayout(new BoxLayout(campaignPanel,BoxLayout.Y_AXIS));
		//TODO populate campaign tab


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
		charactersPanel.setMaximumSize(new Dimension(INFOPANELWIDTH-20,99999));
		//charactersPanel.setPreferredSize(new Dimension(INFOPANELWIDTH-20,999));
		ArrayList<Reference> chars = panel.getRecord().getCampaignCharacters();
		for(int i=0;i<chars.size();i++) {
			MyTextPane pane = new MyTextPane(this, i, HexData.CHARACTER);
			pane.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
			pane.setRef(chars.get(i));
			pane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,Color.gray));
			charactersPanel.add(pane);
			charactersList.add(pane);
		}
		charactersEmpty = new MyTextPane(this, chars.size(), HexData.CHARACTER);
		charactersEmpty.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
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
//		threadsPanel.setPreferredSize(new Dimension(INFOPANELWIDTH-20,999));
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
			pane.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
			pane.doPaint();
			threads.add(pane);
			threadsList.add(pane);
			threads.add(Box.createVerticalStrut(2));
		}
		addThreadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MyTextPane pane = new MyTextPane(InfoPanel.this, threadsList.size(), HexData.THREAD);
				pane.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
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
//		selectedEncounter = -1;
		selectedNPC = -1;
//		selectedDEncounter = -1;
		selectedDungeon = -1;
		selectedPOI = -1;
		selectedFaction = -1;
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		changeSelected = false;
		Point pos;
		if(panel.isShowDistance()) pos = panel.getMouseoverGridPoint();
		else pos = panel.getSelectedGridPoint();
		AltitudeModel grid = panel.getController().getGrid();
		PrecipitationModel precipitation = panel.getController().getPrecipitation();
		BiomeModel biomes = panel.getController().getBiomes();
		PopulationModel population = panel.getController().getPopulation();
		Point zero = panel.getRecord().getZero();
		
		hexGeneralPanel.dopaint();
		demographicsPanel.doPaint();

		Species species = population.getMajoritySpecies(pos.x, pos.y);
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

		String biome = getBiomeText(pos);
		this.biome.setText("Biome Type: "+biome);

		String magics = panel.getController().getMagic().getMagicType(pos).getName();
		this.magic.setText("Magic Type: "+magics);

		threatText.doPaint();
//		this.threatText.setText(getThreatText(pos));
		city1.doPaint();

		int transformedUniversalPopulation = population.getTransformedUniversalPopulation(pos);
		

		if(grid.isWater(pos)||precipitation.isLake(pos)) {
//			this.city1.setText("None");

			zeroPopComponents();
		}else {
			positivePopComponents();


//			for(int i = 0;i<this.encounterTexts.size();i++) {
//				MyTextPane pane = this.encounterTexts.get(i);
//				if(i==selectedEncounter) {
//					pane.setBackground(TEXTHIGHLIGHTCOLOR);
//				}else {
//					pane.setBackground(TEXTBACKGROUNDCOLOR);
//				}
//				pane.doPaint();
//				//pane.setText(getEncounterText(pos,i));
//
//			}
//			if(selectedEncounter>-1) this.encounterTexts.get(selectedEncounter).setCaretPosition(0);

			if(transformedUniversalPopulation>0) {
				detailsTabs.setEnabledAt(NPC_TAB_INDEX, true);
				for(int i = 0;i<this.npcTexts.size();i++) {
					MyTextPane pane = this.npcTexts.get(i);
					if(i==selectedNPC) {
						pane.setBackground(TEXTHIGHLIGHTCOLOR);
					}else {
						pane.setBackground(TEXTBACKGROUNDCOLOR);
					}
					pane.doPaint();
					//pane.setText(getNPCText(pos,i));
				}
				if(selectedNPC>-1) {
//					MyTextPane pane = npcTexts.get(selectedNPC);
//					pane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//					npcScrollPane.scrollRectToVisible(pane.getBounds());
//					SwingUtilities.invokeLater(() -> { encounterScrollPane.scrollRectToVisible(pane.getBounds()); });
					this.npcTexts.get(selectedNPC).setCaretPosition(0);
				}
			}else {
				detailsTabs.setEnabledAt(NPC_TAB_INDEX, false);
			}

			for(int i = 0;i<this.poiTexts.size();i++) {
				MyTextPane pane = this.poiTexts.get(i);
				if(i==selectedPOI) {
					pane.setBackground(TEXTHIGHLIGHTCOLOR);
				}else {
					pane.setBackground(TEXTBACKGROUNDCOLOR);
				}
				pane.doPaint();
				//pane.setText(getPOIText(pos,i,pos.equals(capital)));
			}
			if(selectedPOI>-1) this.poiTexts.get(selectedPOI).setCaretPosition(0);

			for(int i = 0;i<this.dEntranceTexts.size();i++) {
				MyTextPane pane = this.dEntranceTexts.get(i);
				if(i==selectedDungeon) {
					pane.setBackground(TEXTHIGHLIGHTCOLOR);
				}else {
					pane.setBackground(TEXTBACKGROUNDCOLOR);
				}
				pane.doPaint();
				//pane.setText(getDungeonText(pos,i));
			}
			if(selectedDungeon>-1) this.dEntranceTexts.get(selectedDungeon).setCaretPosition(0);

//			for(int i = 0;i<this.dungeonTexts.size();i++) {
//				MyTextPane pane = this.dungeonTexts.get(i);
//				if(i==selectedDEncounter) {
//					pane.setBackground(TEXTHIGHLIGHTCOLOR);
//				}else {
//					pane.setBackground(TEXTBACKGROUNDCOLOR);
//				}
//				pane.doPaint();
//				//pane.setText(getDungeonEncounterText(pos,i));
//			}
//			if(selectedDEncounter>-1) this.dungeonTexts.get(selectedDEncounter).setCaretPosition(0);

			for(int i = 0;i<this.faithsTexts.size();i++) {
				MyTextPane pane = this.faithsTexts.get(i);
				if(i==selectedFaith) {
					pane.setBackground(TEXTHIGHLIGHTCOLOR);
				}else {
					pane.setBackground(TEXTBACKGROUNDCOLOR);
				}
				pane.doPaint();
			}
			
			if(population.isCity(capital)) {
				for(int i = 0;i<this.factionTexts.size();i++) {
					MyTextPane pane = this.factionTexts.get(i);
					if(i==selectedFaction) {
						pane.setBackground(TEXTHIGHLIGHTCOLOR);
					}else {
						pane.setBackground(TEXTBACKGROUNDCOLOR);
					}
					pane.doPaint();
				}
				if(selectedFaction>-1) this.factionTexts.get(selectedFaction).setCaretPosition(0);
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

		hexNote1.doPaint();
		changeSelected = true;
	}

	private String getRegionNameText(Point pos,boolean isCity) {
		String regionNameText = panel.getRecord().getRegionName(pos);
		if(regionNameText==null) regionNameText = getDefaultRegionNameText(pos,isCity);
		return regionNameText;
	}

	private String getDefaultRegionNameText(Point pos,boolean isCity) {
		if(isCity) {
			Species species = panel.getController().getPopulation().getMajoritySpecies(pos.x, pos.y);
			LocationNameModel names = panel.getController().getNames();
			return names.getName(species.getCityNameGen(), pos);
		}else {
			BiomeModel biomes = panel.getController().getBiomes();
			Point region = biomes.getAbsoluteRegion(pos);
			return biomes.getRegionName(region)+" " + WildernessNameGenerator.getBiomeName(biomes.getBiome(pos));
		}
	}

	public String getBiomeText(Point pos) {
		BiomeModel biomes = panel.getController().getBiomes();
		MagicModel magic = panel.getController().getMagic();
		String biome = biomes.getBiome(pos).getName();
		if(magic.isWeird(pos)) {
			Point region = biomes.getAbsoluteRegion(pos);
			biome = magic.getWeirdness(region).toLowerCase() + " "+biome;
		}
		return biome;
	}

	private void positivePopComponents() {
		detailsTabs.setEnabledAt(ENCOUNTER_TAB_INDEX, true);
		detailsTabs.setEnabledAt(NPC_TAB_INDEX, true);
		detailsTabs.setEnabledAt(LOCATION_TAB_INDEX, true);
		detailsTabs.setEnabledAt(DUNGEON_TAB_INDEX, true);
		detailsTabs.setEnabledAt(DUNGEON_ENCOUNTER_TAB_INDEX, true);
		if(detailsSelectedIndex>-1) {
			detailsTabs.setSelectedIndex(detailsSelectedIndex);
			detailsSelectedIndex = -1;
		}

		enableCityTabs(true);
	}

	private void zeroPopComponents() {
		if(detailsSelectedIndex==-1) detailsSelectedIndex = detailsTabs.getSelectedIndex();
		detailsTabs.setEnabledAt(ENCOUNTER_TAB_INDEX, false);
		detailsTabs.setEnabledAt(NPC_TAB_INDEX, false);
		detailsTabs.setEnabledAt(LOCATION_TAB_INDEX, false);
		detailsTabs.setEnabledAt(DUNGEON_TAB_INDEX, false);
		detailsTabs.setEnabledAt(DUNGEON_ENCOUNTER_TAB_INDEX, false);
		detailsTabs.setSelectedIndex(5);

		enableCityTabs(false);
	}

	private void enableCityTabs(boolean enabled) {
		regionTabs.setEnabledAt(CITY_TAB_INDEX, enabled);
		regionTabs.setEnabledAt(FACTION_TAB_INDEX, enabled);
		city1.setEnabled(enabled);
		if(!enabled) {
			this.city1.setText("None");
			regionTabs.setSelectedIndex(CITY_TAB_INDEX);
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

	public void selectTabAndIndex(String tab, int x, int y, int index) {
		Point displayPos = new Point(x,y);
		Point actualPos = Util.denormalizePos(displayPos, panel.getRecord().getZero());
		panel.recenter(actualPos, true);
		switch(tab) {
		case "npc": selectTab(0,NPC_TAB_INDEX,index);break;
		case "location": selectTab(0,LOCATION_TAB_INDEX,index);break;
		case "dungeon": selectTab(0,DUNGEON_TAB_INDEX,index);break;
		case "faction": selectTab(1,FACTION_TAB_INDEX,index);break;
		case "district": selectTab(1,CITY_TAB_INDEX,index);break;
		case "faith": selectTab(1,FAITH_TAB_INDEX,index);break;
		default: throw new IllegalArgumentException("unrecognized tab name: "+tab);
		}
		panel.preprocessThenRepaint();
	}

	private void selectTab(int maintab, int subtab,int index) {
		this.setSelectedIndex(maintab);
		if(maintab==0) {
			detailsTabs.setSelectedIndex(subtab);
			if(ENCOUNTER_TAB_INDEX==subtab) {
//				selectedEncounter=index;
//				this.repaint();
			}else if(NPC_TAB_INDEX==subtab) {
				selectedNPC=index;
				this.repaint();
			}else if(LOCATION_TAB_INDEX==subtab) {
				selectedPOI=index;
				this.repaint();
			}else if(DUNGEON_TAB_INDEX==subtab) {
				selectedDungeon=index;
				this.repaint();
			}else if(DUNGEON_ENCOUNTER_TAB_INDEX==subtab) {
//				selectedDEncounter=index;
//				this.repaint();
			}else throw new IllegalArgumentException("unrecognized tab index: "+subtab);
		}else {
			regionTabs.setSelectedIndex(subtab);
			if(CITY_TAB_INDEX==subtab) {
			}else if(FACTION_TAB_INDEX==subtab) {
				selectedFaction=index;
				this.repaint();
			}else if(FAITH_TAB_INDEX==subtab) {
				selectedFaith=index;
				this.repaint();
			}else throw new IllegalArgumentException("unrecognized tab index: "+subtab);
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
		pane.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
		pane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,Color.gray));
		charactersPanel.add(pane);
		charactersList.add(pane);
		charactersEmpty.setVisible(false);
	}

}
