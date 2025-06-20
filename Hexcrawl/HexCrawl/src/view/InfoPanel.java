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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import data.encounters.Encounter;
import data.magic.MagicModel;
import data.npc.NPC;
import data.population.PopulationModel;
import data.population.Settlement;
import data.population.SettlementModel;
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
	private static final int FACTION_TAB_INDEX = 1;
	private static final int CITY_TAB_INDEX = 0;
	private static final int DUNGEON_ENCOUNTER_TAB_INDEX = 4;
	private static final int DUNGEON_TAB_INDEX = 3;
	private static final int LOCATION_TAB_INDEX = 2;
	private static final int NPC_TAB_INDEX = 1;
	private static final int ENCOUNTER_TAB_INDEX = 0;
	private static final int INFOPANELWIDTH = 450;
	public static final int ENCOUNTERCOUNT = 20;
	public static final int NPCCOUNT = 20;
	public static final int POICOUNT = 20;
	public static final int DUNGEONCOUNT = 6;
	public static final int FACTIONCOUNT = 6;
	public static final int DISTRICTCOUNT = 6;
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
	private JScrollPane dungeonScrollPane;
	private MyTextPane hexNote1;
	private JScrollPane hexNoteScrollPane;
	public boolean expandHexNotePane;
	public boolean hideHexNotePane;
	private JTabbedPane detailsTabs;
	private int detailsSelectedIndex;
	private JTabbedPane regionTabs;
	private JTextField regionNameField;
	private JLabel citySizeLabel;
	private ArrayList<MyTextPane> encounterTexts;
	private ArrayList<MyTextPane> npcTexts;
	private ArrayList<MyTextPane> dungeonTexts;
	private ArrayList<MyTextPane> poiTexts;
	private ArrayList<MyTextPane> dEntranceTexts;
	private JScrollPane dEntranceScrollPane;
	private ArrayList<MyTextPane> factionTexts;
	private JScrollPane factionScrollPane;
	int selectedNPC;
	int selectedDungeon;
	int selectedPOI;
	int selectedDEncounter;
	int selectedFaction;
	int selectedEncounter;
	boolean changeSelected;

	private HexPanelGeneralStatPanel hexGeneralPanel;
	private DemographicsPanel demographicsPanel;
	private JScrollPane encounterScrollPane;
	private MyTextPane charactersEmpty;
	private ArrayList<MyTextPane> charactersList;
	private ArrayList<MyTextPane> threadsList;

	public InfoPanel(MapPanel panel) {
		this.panel = panel;
		this.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

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

		//encounterPanel = new EncountersPanel(this);
		JPanel encounterPanel = new JPanel();
		encounterPanel.setLayout(new BoxLayout(encounterPanel, BoxLayout.Y_AXIS));
		encounterTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<ENCOUNTERCOUNT;i++) {
			encounterPanel.add(new JLabel("~~~~~ Encounter #"+(i+1)+" ~~~~~"));
			MyTextPane encounteri = new MyTextPane(this, i, HexData.ENCOUNTER);
			encounteri.setMaximumSize(new Dimension(INFOPANELWIDTH,9999));
			encounterPanel.add(encounteri);
			encounterTexts.add(encounteri);
		}
		encounterScrollPane = new JScrollPane(encounterPanel);
		encounterScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		encounterScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		detailsTabs.addTab("Encounters", encounterScrollPane);

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

		JPanel dungeonPanel = new JPanel();
		dungeonPanel.setLayout(new BoxLayout(dungeonPanel, BoxLayout.Y_AXIS));
		dungeonTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<ENCOUNTERCOUNT;i++) {
			dungeonPanel.add(new JLabel("~~~~~ Dungeon Encounter #"+(i+1)+" ~~~~~"));
			MyTextPane encounteri = new MyTextPane(this, i, HexData.D_ENCOUNTER);
			encounteri.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
			dungeonPanel.add(encounteri);
			dungeonTexts.add(encounteri);
		}
		dungeonScrollPane = new JScrollPane(dungeonPanel);
		dungeonScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		detailsTabs.addTab("D.Encounters", dungeonScrollPane);

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


		regionTabs = new JTabbedPane();
		city1 = new MyTextPane(this, -1, HexData.CITY);
		cityScrollPane = new JScrollPane(city1);
		regionTabs.addTab("Parent City", cityScrollPane);

		JPanel factionPanel = new JPanel();
		factionPanel.setLayout(new BoxLayout(factionPanel, BoxLayout.Y_AXIS));
		factionTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<FACTIONCOUNT;i++) {
			factionPanel.add(new JLabel("~~~~~ Faction #"+(i+1)+" ~~~~~"));
			MyTextPane factioni = new MyTextPane(this, i, HexData.FACTION);;
			factioni.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
			factionPanel.add(factioni);
			factionTexts.add(factioni);
		}
		factionScrollPane = new JScrollPane(factionPanel);
		factionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		regionTabs.addTab("Factions", factionScrollPane);

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
		JPanel characters = new JPanel();
		JScrollPane charactersScrollPane = new JScrollPane(characters);
		JButton charactersHideButton = new JButton("Hide");
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
		charactersHeader.add(charactersHideButton,BorderLayout.EAST);
		charactersHeader.add(Box.createHorizontalStrut(140));
		campaignPanel.add(charactersHeader);
		this.charactersList = new ArrayList<MyTextPane>();
		characters.setLayout(new BoxLayout(characters, BoxLayout.Y_AXIS));
		ArrayList<Reference> chars = panel.getRecord().getCampaignCharacters();
		for(int i=0;i<chars.size()+30;i++) {
			MyTextPane pane = new MyTextPane(this, i, HexData.NPC);
			pane.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
			characters.add(pane);
			charactersList.add(pane);
			characters.add(Box.createVerticalStrut(2));
		}
		charactersEmpty = new MyTextPane(this, chars.size(), HexData.NPC);
		charactersEmpty.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
		charactersEmpty.setEnabled(false);
		characters.add(charactersEmpty);
		campaignPanel.add(charactersScrollPane);

		
		JPanel threadsPanel = new JPanel(new BorderLayout());
		JPanel threadsHeader = new JPanel();
		threadsHeader.setLayout(new BoxLayout(threadsHeader, BoxLayout.X_AXIS));
		threadsHeader.add(Box.createHorizontalStrut(40));
		threadsHeader.add(new JLabel("Threads:"),BorderLayout.WEST);
		threadsHeader.add(Box.createHorizontalGlue());
		JPanel threads = new JPanel();
		JScrollPane threadsScrollPane = new JScrollPane(threads);
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
		for(int i=0;i<threadText.size()+30;i++) {
			MyTextPane pane = new MyTextPane(this, i, HexData.THREAD);
			pane.setMaximumSize(new Dimension(INFOPANELWIDTH-20,9999));
			threads.add(pane);
			threadsList.add(pane);
			threads.add(Box.createVerticalStrut(2));
		}
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
	}

	@Override
	public void paint(Graphics g){
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


			for(int i = 0;i<this.encounterTexts.size();i++) {
				MyTextPane pane = this.encounterTexts.get(i);
				if(i==selectedEncounter) {
					pane.setBackground(TEXTHIGHLIGHTCOLOR);
				}else {
					pane.setBackground(TEXTBACKGROUNDCOLOR);
				}
				pane.doPaint();
				//pane.setText(getEncounterText(pos,i));

			}
			if(selectedEncounter>-1) this.encounterTexts.get(selectedEncounter).setCaretPosition(0);

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

			for(int i = 0;i<this.dungeonTexts.size();i++) {
				MyTextPane pane = this.dungeonTexts.get(i);
				if(i==selectedDEncounter) {
					pane.setBackground(TEXTHIGHLIGHTCOLOR);
				}else {
					pane.setBackground(TEXTBACKGROUNDCOLOR);
				}
				pane.doPaint();
				//pane.setText(getDungeonEncounterText(pos,i));
			}
			if(selectedDEncounter>-1) this.dungeonTexts.get(selectedDEncounter).setCaretPosition(0);

			if(population.isCity(capital)) {
//				this.city1.setText(getCityText(capital));
				//this.city1.setCaretPosition(0);

				for(int i = 0;i<this.factionTexts.size();i++) {
					MyTextPane pane = this.factionTexts.get(i);
					if(i==selectedFaction) {
						pane.setBackground(TEXTHIGHLIGHTCOLOR);
					}else {
						pane.setBackground(TEXTBACKGROUNDCOLOR);
					}
					pane.doPaint();
					//pane.setText(getFactionText(pos,i));
				}
				if(selectedFaction>-1) this.factionTexts.get(selectedFaction).setCaretPosition(0);
			}else {
				enableCityTabs(false);
			}
		}

		hexNote1.doPaint();
//		this.hexNote1.setText(getHexNoteText(pos));
		super.paint(g);
		changeSelected = true;
	}

	public String getLinkText(String link) {
		Matcher matcher = Pattern.compile("\\{(\\D+):(-?\\d+),(-?\\d+),(\\d+)\\}\\$").matcher(link);
		if(matcher.matches()) {
			link = getLinkText(
					matcher.group(1),
					Integer.valueOf(matcher.group(2)),
					Integer.valueOf(matcher.group(3)),
					Integer.valueOf(matcher.group(4))-1);
		}
		return link;
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

	String getDefaultEncounterText(Point pos,int index) {
		Encounter n = panel.getController().getEncounters().getEncounter(index, pos);
		return n.toString();
	}
	private String getNPCText(Point pos,int index) {
		String npcText = panel.getRecord().getNPC(pos,index);
		if(npcText==null) npcText = getDefaultNPCText(pos,index);
		return npcText;
	}
	private String getDefaultNPCText(Point pos,int index) {
		NPC n = panel.getController().getNpcs().getNPC(index, pos);
		return n.toString();
	}
	private String getNPCLinkText(Point pos,int index) {
		String npcText = getNPCText(pos, index);
		int firstLine = npcText.indexOf("\r\n");
		if(firstLine>-1&&firstLine<50) {
			return npcText.substring(0, firstLine);
		}else {
			return npcText.substring(0,30);
		}
	}

	private String getCityText(Point capital) {
		String cityText = panel.getRecord().getCity(capital);
		if(cityText==null) cityText = getDefaultCityText(capital);
		return cityText;
	}
	private String getDefaultCityText(Point capital) {
		SettlementModel cities = panel.getController().getSettlements();
		Settlement city = cities.getSettlement(capital);
		StringBuilder c1Text = new StringBuilder();
		c1Text.append(city.toString());
		String string = c1Text.toString();
		return string;
	}
	private String getDistrictLinkText(Point capital,int index) {
		SettlementModel cities = panel.getController().getSettlements();
		Settlement city = cities.getSettlement(capital);
		return city.getDistricts().get(index);
	}
	private String getFactionText(Point pos,int i) {
		String factionText = panel.getRecord().getFaction(pos,i);
		if(factionText==null) factionText = getDefaultFactionText(pos,i);
		return factionText;
	}
	private String getDefaultFactionText(Point capital,int i) {
		return panel.getController().getSettlements().getFaction(i, capital).toString();
	}
	private String getFactionLinkText(Point pos,int index) {
		String factionText = getFactionText(pos, index);
		int firstLine = factionText.indexOf("\r\n");
		if(firstLine>-1&&firstLine<50) {
			return factionText.substring(0, firstLine);
		}else {
			return factionText.substring(0,30);
		}
	}

	private String getPOIText(Point pos,int i, boolean isCity) {
		String poiText = panel.getRecord().getLocation(pos,i);
		if(poiText==null) {
			if(i==0) poiText = getDefaultInnText(pos);
			else poiText = getDefaultPOIText(pos,i,isCity);
		}
		return poiText;
	}
	private String getDefaultPOIText(Point pos,int i, boolean isCity) {
		return panel.getController().getPois().getPOI(i, pos,isCity);
	}
	private String getPOILinkText(Point pos,int index, boolean isCity) {
		String poiText = getPOIText(pos, index,isCity);
		int firstLine = poiText.indexOf("\r\n");
		if(firstLine>-1&&firstLine<50) {
			return poiText.substring(0, firstLine);
		}else {
			return poiText.substring(0,30);
		}
	}
	private String getDefaultInnText(Point pos) {
		AltitudeModel grid = panel.getController().getGrid();
		PrecipitationModel precipitation = panel.getController().getPrecipitation();
		if(grid.isWater(pos)||precipitation.isLake(pos)) {
			return "Inn: none";
		}else {
			LocationNameModel names = panel.getController().getNames();
			return names.getInnText(pos);
		}
	}
	private String getDungeonText(Point pos,int i) {
		String poiText = panel.getRecord().getDungeon(pos,i);
		if(poiText==null) poiText = getDefaultDungeonText(pos,i);
		return poiText;
	}
	private String getDefaultDungeonText(Point pos,int i) {
		return panel.getController().getDungeon().getDungeon(i, pos).toString();
	}
	private String getDungeonLinkText(Point pos,int index) {
		String dungeonText = getDungeonText(pos, index);
		int firstLine = dungeonText.indexOf("\r\n");
		if(firstLine>-1&&firstLine<50) {
			return dungeonText.substring(0, firstLine);
		}else {
			return dungeonText.substring(0,30);
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
		default: throw new IllegalArgumentException("unrecognized tab name: "+tab);
		}
	}

	private void selectTab(int maintab, int subtab,int index) {
		this.setSelectedIndex(maintab);
		if(maintab==0) {
			detailsTabs.setSelectedIndex(subtab);
			switch(subtab) {
			case ENCOUNTER_TAB_INDEX: {
				selectedEncounter=index;
				this.repaint();
//				MyTextPane pane = encounterTexts.get(index);
//				pane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//				encounterScrollPane.scrollRectToVisible(pane.getBounds());
//				SwingUtilities.invokeLater(() -> { encounterScrollPane.scrollRectToVisible(pane.getBounds()); });
				break;
			}
			case NPC_TAB_INDEX: {
				selectedNPC=index;
				this.repaint();
//				MyTextPane pane = npcTexts.get(index);
//				pane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//				npcScrollPane.scrollRectToVisible(pane.getBounds());
//				SwingUtilities.invokeLater(() -> { encounterScrollPane.scrollRectToVisible(pane.getBounds()); });
				break;
			}
			case LOCATION_TAB_INDEX: {
				selectedPOI=index;
				this.repaint();
//				MyTextPane pane = poiTexts.get(index);
//				pane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//				poiScrollPane.scrollRectToVisible(pane.getBounds());
//				SwingUtilities.invokeLater(() -> { encounterScrollPane.scrollRectToVisible(pane.getBounds()); });
				break;
			}
			case DUNGEON_TAB_INDEX: {
				selectedDungeon=index;
				this.repaint();
//				MyTextPane pane = dEntranceTexts.get(index);
//				pane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//				dEntranceScrollPane.scrollRectToVisible(pane.getBounds());
//				SwingUtilities.invokeLater(() -> { encounterScrollPane.scrollRectToVisible(pane.getBounds()); });
				break;
			}
			case DUNGEON_ENCOUNTER_TAB_INDEX: {
				selectedDEncounter=index;
				this.repaint();
//				MyTextPane pane = dungeonTexts.get(index);
//				pane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//				dungeonScrollPane.scrollRectToVisible(pane.getBounds());
//				SwingUtilities.invokeLater(() -> { encounterScrollPane.scrollRectToVisible(pane.getBounds()); });
				break;
			}
			default: throw new IllegalArgumentException("unrecognized tab index: "+subtab);
			}
		}else {
			regionTabs.setSelectedIndex(subtab);
			switch(subtab) {
			case CITY_TAB_INDEX: break;
			case FACTION_TAB_INDEX: {
				selectedFaction=index;
				//factionTexts.get(index).setCaretPosition(0);
				this.repaint();
				break;
			}
			default: throw new IllegalArgumentException("unrecognized tab index: "+subtab);
			}
		}
	}

	public String getToolTipText(String tab, int x, int y, int index) {
		Point displayPos = new Point(x,y);
		Point actualPos = Util.denormalizePos(displayPos, panel.getRecord().getZero());
		String result;
		switch(tab) {
		case "npc": result = getNPCText(actualPos, index);break;
		case "location": {
			PopulationModel population = panel.getController().getPopulation();
			boolean isCity = population.isCity(actualPos);
			result = getPOIText(actualPos, index, isCity);break;
		}
		case "dungeon": result = getDungeonText(actualPos, index);break;
		case "faction": {
			PopulationModel population = panel.getController().getPopulation();
			Point capital = population.getAbsoluteFealty(actualPos);
			result = getFactionText(capital, index);break;
		}
		case "district": {
			PopulationModel population = panel.getController().getPopulation();
			Point capital = population.getAbsoluteFealty(actualPos);
			result = getCityText(capital);break;
		}
		default: throw new IllegalArgumentException("unrecognized tab name: "+tab);
		}
		Matcher matcher;
		matcher = Pattern.compile("(\\{\\w+\\:\\d+,\\d+,\\d+\\})").matcher(result);
		while(matcher.find()) {
			result = Util.replace(result,matcher.group(1), getLinkText(matcher.group(1)));
		}
		return result;
	}

	private String getLinkText(String tab, int x, int y, int index) {
		Point p = new Point(x,y);
		String result;
		switch(tab) {
		case "npc": result = getNPCLinkText(p, index);break;
		case "location": {
			PopulationModel population = panel.getController().getPopulation();
			boolean isCity = population.isCity(p);
			result = getPOILinkText(p, index, isCity);break;
		}
		case "dungeon": result = getDungeonLinkText(p, index);break;
		case "faction": result = getFactionLinkText(p, index);break;
		case "district": {
			PopulationModel population = panel.getController().getPopulation();
			Point capital = population.getAbsoluteFealty(p);
			result = getDistrictLinkText(capital,index);break;
		}
		default: throw new IllegalArgumentException("unrecognized tab name: "+tab);
		}
		return result;
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

}
