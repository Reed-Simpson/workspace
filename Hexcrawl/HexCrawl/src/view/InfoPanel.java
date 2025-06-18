package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ToolTipManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import biome.BiomeModel;
import encounters.Encounter;
import general.Util;
import magic.MagicModel;
import map.AltitudeModel;
import names.LocationNameModel;
import names.wilderness.WildernessNameGenerator;
import npc.NPC;
import population.PopulationModel;
import population.SettlementSize;
import population.Species;
import precipitation.PrecipitationModel;
import settlement.Settlement;
import settlement.SettlementModel;
import threat.Threat;
import threat.ThreatModel;
import view.infopanels.ChatLinkAction;
import view.infopanels.ChatLinkMouseoverAction;
import view.infopanels.DemographicsPanel;
import view.infopanels.EncountersPanel;
import view.infopanels.HexPanelGeneralStatPanel;
import view.infopanels.TextLinkMouseListener;

@SuppressWarnings("serial")
public class InfoPanel extends JTabbedPane{
	private static final Color TEXTBACKGROUNDCOLOR = Color.WHITE;
	private static final Color TEXTHIGHLIGHTCOLOR = Color.getHSBColor(65f/360, 20f/100, 100f/100);
	private static final int FACTION_TAB_INDEX = 1;
	private static final int CITY_TAB_INDEX = 0;
	private static final int DUNGEON_ENCOUNTER_TAB_INDEX = 4;
	private static final int DUNGEON_TAB_INDEX = 3;
	private static final int LOCATION_TAB_INDEX = 2;
	private static final int NPC_TAB_INDEX = 1;
	private static final int ENCOUNTER_TAB_INDEX = 0;
	private static final int WIDTH = 450;
	public static final int NPCCOUNT = 20;
	public static final int POICOUNT = 20;
	public static final int DUNGEONCOUNT = 6;
	public static final int FACTIONCOUNT = 6;
	public static final int DISTRICTCOUNT = 6;
	private static final Style DEFAULT = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
	private MapPanel panel;

	private JLabel biome;
	private JLabel magic;
	private JLabel cityName;
	//THREAT
	private JTextArea threatText;
	private JScrollPane threatScrollPane;
	//ENCOUNTER
	private JScrollPane npcScrollPane;
	private JPanel hexPanel;
	private JPanel regionPanel;
	private JLabel locationName2;
	private JTextArea city1;
	private JScrollPane cityScrollPane;
	private JScrollPane poiScrollPane;
	private JScrollPane dungeonScrollPane;
	private JTextArea hexNote1;
	private JScrollPane hexNoteScrollPane;
	public boolean expandHexNotePane;
	public boolean hideHexNotePane;
	private JTabbedPane detailsTabs;
	private int detailsSelectedIndex;
	private JTabbedPane regionTabs;
	private JTextField regionNameField;
	private JLabel citySizeLabel;
	private ArrayList<JTextPane> npcTexts;
	private ArrayList<JTextPane> dungeonTexts;
	private ArrayList<JTextPane> poiTexts;
	private ArrayList<JTextPane> dEntranceTexts;
	private JScrollPane dEntranceScrollPane;
	private ArrayList<JTextPane> factionTexts;
	private JScrollPane factionScrollPane;
	int selectedNPC;
	int selectedDungeon;
	int selectedPOI;
	int selectedDEncounter;
	int selectedFaction;
	int selectedEncounter;
	boolean changeSelected;
	//private EncountersPanel encounterPanel;
	private ArrayList<JTextPane> encounterTexts;
	private HexPanelGeneralStatPanel hexGeneralPanel;
	private DemographicsPanel demographicsPanel;

	public InfoPanel() {
		this.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
		//Tab 1
		this.hexPanel = new JPanel();
		hexPanel.setPreferredSize(new Dimension(WIDTH,WIDTH));
		hexPanel.setLayout(new BoxLayout(hexPanel, BoxLayout.Y_AXIS));
		

		hexGeneralPanel = new HexPanelGeneralStatPanel(this);
		hexPanel.add(hexGeneralPanel);
		
		demographicsPanel = new DemographicsPanel(this);
		hexPanel.add(demographicsPanel);

		detailsTabs = new JTabbedPane();

		//encounterPanel = new EncountersPanel(this);
		JPanel encounterPanel = new JPanel();
		encounterPanel.setLayout(new BoxLayout(encounterPanel, BoxLayout.Y_AXIS));
		encounterTexts = new ArrayList<JTextPane>();
		for(int i=0;i<EncountersPanel.ENCOUNTERCOUNT;i++) {
			encounterPanel.add(new JLabel("~~~~~ Encounter #"+(i+1)+" ~~~~~"));
			JTextPane encounteri = new JTextPane();
			//			npci.setLineWrap(true);
			//			npci.setWrapStyleWord(true);
			encounteri.setMaximumSize(new Dimension(WIDTH-20,9999));
			encounteri.addFocusListener(new EncounterFocusListener(encounteri,i));
			TextLinkMouseListener mouseAdapter = new TextLinkMouseListener(encounteri);
			encounteri.addMouseListener(mouseAdapter);
			encounteri.addMouseMotionListener(mouseAdapter);
			encounteri.setAlignmentX(LEFT_ALIGNMENT);
			encounteri.setCaret(new MyCaret());
			encounteri.setContentType("text/html");
			encounterPanel.add(encounteri);
			encounterTexts.add(encounteri);
		}
		JScrollPane encounterScrollPane = new JScrollPane(encounterPanel);
		encounterScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		encounterScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		detailsTabs.addTab("Encounters", encounterScrollPane);

		JPanel npcPanel = new JPanel();
		npcPanel.setLayout(new BoxLayout(npcPanel, BoxLayout.Y_AXIS));
		npcTexts = new ArrayList<JTextPane>();
		for(int i=0;i<NPCCOUNT;i++) {
			npcPanel.add(new JLabel("~~~~~ NPC #"+(i+1)+" ~~~~~"));
			JTextPane npci = new JTextPane();
			//			npci.setLineWrap(true);
			//			npci.setWrapStyleWord(true);
			npci.setMaximumSize(new Dimension(WIDTH-20,9999));
			npci.addFocusListener(new NPCFocusListener2(npci,i));
			npci.addMouseListener(new TextLinkMouseListener(npci));
			npci.setAlignmentX(LEFT_ALIGNMENT);
			npci.setCaret(new MyCaret());
			npci.setContentType("text/html");
			npcPanel.add(npci);
			npcTexts.add(npci);
		}
		npcScrollPane = new JScrollPane(npcPanel);
		npcScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		detailsTabs.addTab("NPCs", npcScrollPane);


		JPanel poiPanel = new JPanel();
		poiPanel.setLayout(new BoxLayout(poiPanel, BoxLayout.Y_AXIS));
		poiTexts = new ArrayList<JTextPane>();
		poiPanel.add(new JLabel("~~~~~ Inn ~~~~~"));
		JTextPane inn = new JTextPane();
		inn.setMaximumSize(new Dimension(WIDTH-20,9999));
		inn.addFocusListener(new POIFocusListener(inn,0));
		inn.addMouseListener(new TextLinkMouseListener(inn));
		inn.setAlignmentX(LEFT_ALIGNMENT);
		inn.setCaret(new MyCaret());
		inn.setContentType("text/html");
		poiPanel.add(inn);
		poiTexts.add(inn);
		for(int i=1;i<POICOUNT;i++) {
			poiPanel.add(new JLabel("~~~~~ Point of Interest #"+(i)+" ~~~~~"));
			JTextPane poii = new JTextPane();
			//			poii.setLineWrap(true);
			//			poii.setWrapStyleWord(true);
			poii.setMaximumSize(new Dimension(WIDTH-20,9999));
			poii.addFocusListener(new POIFocusListener(poii,i));
			poii.addMouseListener(new TextLinkMouseListener(poii));
			poii.setAlignmentX(LEFT_ALIGNMENT);
			poii.setCaret(new MyCaret());
			poii.setContentType("text/html");
			poiPanel.add(poii);
			poiTexts.add(poii);
		}
		poiScrollPane = new JScrollPane(poiPanel);
		poiScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		detailsTabs.addTab("Locations", poiScrollPane);

		JPanel dEntrancePanel = new JPanel();
		dEntrancePanel.setLayout(new BoxLayout(dEntrancePanel, BoxLayout.Y_AXIS));
		dEntranceTexts = new ArrayList<JTextPane>();
		for(int i=0;i<DUNGEONCOUNT;i++) {
			dEntrancePanel.add(new JLabel("~~~~~ Dungeon #"+(i+1)+" ~~~~~"));
			JTextPane poii = new JTextPane();
			//			poii.setLineWrap(true);
			//			poii.setWrapStyleWord(true);
			poii.setMaximumSize(new Dimension(WIDTH-20,9999));
			poii.addFocusListener(new DungeonEntranceFocusListener(poii,i));
			poii.addMouseListener(new TextLinkMouseListener(poii));
			poii.setAlignmentX(LEFT_ALIGNMENT);
			poii.setCaret(new MyCaret());
			poii.setContentType("text/html");
			dEntrancePanel.add(poii);
			dEntranceTexts.add(poii);
		}
		dEntranceScrollPane = new JScrollPane(dEntrancePanel);
		dEntranceScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		detailsTabs.addTab("Dungeon", dEntranceScrollPane);

		JPanel dungeonPanel = new JPanel();
		dungeonPanel.setLayout(new BoxLayout(dungeonPanel, BoxLayout.Y_AXIS));
		dungeonTexts = new ArrayList<JTextPane>();
		for(int i=0;i<EncountersPanel.ENCOUNTERCOUNT;i++) {
			dungeonPanel.add(new JLabel("~~~~~ Dungeon Encounter #"+(i+1)+" ~~~~~"));
			JTextPane encounteri = new JTextPane();
			//			encounteri.setLineWrap(true);
			//			encounteri.setWrapStyleWord(true);
			encounteri.setMaximumSize(new Dimension(WIDTH-20,9999));
			encounteri.addFocusListener(new DungeonEncounterFocusListener(encounteri,i));
			encounteri.addMouseListener(new TextLinkMouseListener(encounteri));
			encounteri.setAlignmentX(LEFT_ALIGNMENT);
			encounteri.setCaret(new MyCaret());
			encounteri.setContentType("text/html");
			dungeonPanel.add(encounteri);
			dungeonTexts.add(encounteri);
		}
		dungeonScrollPane = new JScrollPane(dungeonPanel);
		dungeonScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		detailsTabs.addTab("D.Encounters", dungeonScrollPane);

		hexNote1 = new JTextArea();
		hexNote1.setLineWrap(true);
		hexNote1.setWrapStyleWord(true);
		hexNote1.addFocusListener(new HexNoteFocusListener());
		hexNoteScrollPane = new JScrollPane(hexNote1);
		detailsTabs.addTab("Notes", hexNoteScrollPane);

		hexPanel.add(detailsTabs);

		this.addTab("                         Hex                         ", new JScrollPane(hexPanel));

		//Tab 1
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
				Point pos = panel.getBiomes().getAbsoluteRegion(panel.getSelectedGridPoint());
				PopulationModel pop = panel.getPopulation();
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

		threatText = new JTextArea("Threat");
		threatText.setLineWrap(true);
		threatText.setWrapStyleWord(true);
		threatText.addFocusListener(new ThreatFocusListener());
		threatScrollPane = new JScrollPane(threatText);
		threatScrollPane.setMaximumSize(new Dimension(9999,125));
		threatScrollPane.setPreferredSize(new Dimension(9999,125));
		regionPanel.add(threatScrollPane);

		regionPanel.add(getSeparator());

		cityName = new JLabel("name");
		regionPanel.add(cityName);


		regionTabs = new JTabbedPane();
		city1 = new JTextArea();
		city1.setLineWrap(true);
		city1.setWrapStyleWord(true);
		city1.addFocusListener(new CityFocusListener());
		cityScrollPane = new JScrollPane(city1);
		regionTabs.addTab("Parent City", cityScrollPane);

		JPanel factionPanel = new JPanel();
		factionPanel.setLayout(new BoxLayout(factionPanel, BoxLayout.Y_AXIS));
		factionTexts = new ArrayList<JTextPane>();
		for(int i=0;i<FACTIONCOUNT;i++) {
			factionPanel.add(new JLabel("~~~~~ Faction #"+(i+1)+" ~~~~~"));
			JTextPane factioni = new JTextPane();
			//			encounteri.setLineWrap(true);
			//			encounteri.setWrapStyleWord(true);
			factioni.setMaximumSize(new Dimension(WIDTH-20,9999));
			factioni.addFocusListener(new FactionFocusListener(factioni,i));
			factioni.addMouseListener(new TextLinkMouseListener(factioni));
			factioni.setAlignmentX(LEFT_ALIGNMENT);
			factioni.setCaret(new MyCaret());
			factioni.setContentType("text/html");
			factionPanel.add(factioni);
			factionTexts.add(factioni);
		}
		factionScrollPane = new JScrollPane(factionPanel);
		factionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		regionTabs.addTab("Factions", factionScrollPane);

		regionPanel.add(regionTabs);

		this.addTab("                        Region                        ", new JScrollPane(regionPanel));

		resetSelection();
		changeSelected = true;

		ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
		toolTipManager.setDismissDelay(Integer.MAX_VALUE); 
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
		AltitudeModel grid = panel.getGrid();
		PrecipitationModel precipitation = panel.getPrecipitation();
		BiomeModel biomes = panel.getBiomes();
		PopulationModel population = panel.getPopulation();
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

		String magics = panel.getMagic().getMagicType(pos).getName();
		this.magic.setText("Magic Type: "+magics);

		this.threatText.setText(getThreatText(pos));

		int transformedUniversalPopulation = population.getTransformedUniversalPopulation(pos);
		

		if(grid.isWater(pos)||precipitation.isLake(pos)) {
			this.city1.setText("None");

			zeroPopComponents();
		}else {
			positivePopComponents();


			for(int i = 0;i<this.encounterTexts.size();i++) {
				JTextPane pane = this.encounterTexts.get(i);
				if(i==selectedEncounter) {
					pane.setBackground(TEXTHIGHLIGHTCOLOR);
				}else {
					pane.setBackground(TEXTBACKGROUNDCOLOR);
				}
				writeStringToDocument(getEncounterText(pos,i), pane);

			}
			if(selectedEncounter>-1) this.encounterTexts.get(selectedEncounter).setCaretPosition(0);

			if(transformedUniversalPopulation>0) {
				detailsTabs.setEnabledAt(NPC_TAB_INDEX, true);
				for(int i = 0;i<this.npcTexts.size();i++) {
					JTextPane pane = this.npcTexts.get(i);
					if(i==selectedNPC) {
						pane.setBackground(TEXTHIGHLIGHTCOLOR);
					}else {
						pane.setBackground(TEXTBACKGROUNDCOLOR);
					}
					writeStringToDocument(getNPCText(pos,i), pane);
				}
				if(selectedNPC>-1) this.npcTexts.get(selectedNPC).setCaretPosition(0);
			}else {
				detailsTabs.setEnabledAt(NPC_TAB_INDEX, false);
			}

			for(int i = 0;i<this.poiTexts.size();i++) {
				JTextPane pane = this.poiTexts.get(i);
				if(i==selectedPOI) {
					pane.setBackground(TEXTHIGHLIGHTCOLOR);
				}else {
					pane.setBackground(TEXTBACKGROUNDCOLOR);
				}
				writeStringToDocument(getPOIText(pos,i,pos.equals(capital)), pane);
			}
			if(selectedPOI>-1) this.poiTexts.get(selectedPOI).setCaretPosition(0);

			for(int i = 0;i<this.dEntranceTexts.size();i++) {
				JTextPane pane = this.dEntranceTexts.get(i);
				if(i==selectedDungeon) {
					pane.setBackground(TEXTHIGHLIGHTCOLOR);
				}else {
					pane.setBackground(TEXTBACKGROUNDCOLOR);
				}
				writeStringToDocument(getDungeonText(pos,i), pane);
			}
			if(selectedDungeon>-1) this.dEntranceTexts.get(selectedDungeon).setCaretPosition(0);

			for(int i = 0;i<this.dungeonTexts.size();i++) {
				JTextPane pane = this.dungeonTexts.get(i);
				if(i==selectedDEncounter) {
					pane.setBackground(TEXTHIGHLIGHTCOLOR);
				}else {
					pane.setBackground(TEXTBACKGROUNDCOLOR);
				}
				writeStringToDocument(getDungeonEncounterText(pos,i), pane);
			}
			if(selectedDEncounter>-1) this.dungeonTexts.get(selectedDEncounter).setCaretPosition(0);

			if(population.isCity(capital)) {
				this.city1.setText(getCityText(capital));
				//this.city1.setCaretPosition(0);

				for(int i = 0;i<this.factionTexts.size();i++) {
					JTextPane pane = this.factionTexts.get(i);
					if(i==selectedFaction) {
						pane.setBackground(TEXTHIGHLIGHTCOLOR);
					}else {
						pane.setBackground(TEXTBACKGROUNDCOLOR);
					}
					writeStringToDocument(getFactionText(pos,i), pane);
				}
				if(selectedFaction>-1) this.factionTexts.get(selectedFaction).setCaretPosition(0);
			}else {
				enableCityTabs(false);
			}
		}

		this.hexNote1.setText(getHexNoteText(pos));
		super.paint(g);
		changeSelected = true;
	}

	private void writeStringToDocument(String string, JTextPane pane) {
		StyledDocument doc = pane.getStyledDocument();
		try {
			pane.setText("<html>");
			//doc.remove(0, doc.getLength());//delete contents
			int curlybrace = string.indexOf("{");
			int closebrace = -1;
			while(curlybrace>-1) {
				doc.insertString(doc.getLength(), string.substring(closebrace+1,curlybrace), DEFAULT);
				closebrace = string.indexOf("}", curlybrace);
				insertLink(pane,string.substring(curlybrace, closebrace+1));
				curlybrace = string.indexOf("{", closebrace);
			}
			doc.insertString(doc.getLength(), string.substring(closebrace+1), DEFAULT);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	private void insertLink(JTextPane pane, String link) throws BadLocationException {
		StyledDocument doc = pane.getStyledDocument();
		Style regularBlue = doc.addStyle("regularBlue", DEFAULT);
		StyleConstants.setForeground(regularBlue, Color.BLUE);
		StyleConstants.setUnderline(regularBlue, true);
		regularBlue.addAttribute("linkact", new ChatLinkAction(link, this));
		regularBlue.addAttribute("linkmouseover", new ChatLinkMouseoverAction(link, pane,this));
		String linkText = getLinkText(link);
		doc.insertString(doc.getLength(), linkText, regularBlue);
	}
	private String removeLinks(String string) {
		StringBuilder sb = new StringBuilder();
		int curlybrace = string.indexOf("{");
		int closebrace = -1;
		while(curlybrace>-1) {
			sb.append(string.substring(closebrace+1,curlybrace));
			closebrace = string.indexOf("}", curlybrace);
			String link = string.substring(curlybrace, closebrace+1);
			sb.append(getLinkText(link));
			curlybrace = string.indexOf("{", closebrace);
		}
		sb.append(string.substring(closebrace+1));
		return sb.toString();
	}

	private String getLinkText(String link) {
		Matcher matcher = Pattern.compile("\\{(\\D+):(-?\\d+),(-?\\d+),(\\d+)\\}").matcher(link);
		if(matcher.matches()) {
			if(Integer.valueOf(matcher.group(4))==0) {
				System.out.println(link);
			}
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
			Species species = panel.getPopulation().getMajoritySpecies(pos.x, pos.y);
			LocationNameModel names = panel.getNames();
			return names.getName(species.getCityNameGen(), pos);
		}else {
			BiomeModel biomes = panel.getBiomes();
			Point region = biomes.getAbsoluteRegion(pos);
			return biomes.getRegionName(region)+" " + WildernessNameGenerator.getBiomeName(biomes.getBiome(pos));
		}
	}

	public String getBiomeText(Point pos) {
		BiomeModel biomes = panel.getBiomes();
		MagicModel magic = panel.getMagic();
		String biome = biomes.getBiome(pos).getName();
		if(magic.isWeird(pos)) {
			Point region = biomes.getAbsoluteRegion(pos);
			biome = magic.getWeirdness(region).toLowerCase() + " "+biome;
		}
		return biome;
	}


	private String getThreatText(Point pos) {
		Point center = panel.getThreats().getCenter(pos);
		String threatText = panel.getRecord().getThreat(center);
		if(threatText==null) threatText = getDefaultThreatText(center);
		return threatText;
	}

	private String getDefaultThreatText(Point pos) {
		ThreatModel threats = panel.getThreats();
		Threat threat = threats.getThreat(pos);
		String string = threat.toString();
		return string;
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

	private String getEncounterText(Point pos,int index) {
		String encounterText = panel.getRecord().getEncounter(pos,index);
		if(encounterText==null) encounterText = getDefaultEncounterText(pos,index);
		return encounterText;
	}
	private String getDefaultEncounterText(Point pos,int index) {
		Encounter n = panel.getEncounters().getEncounter(index, pos);
		return n.toString(index+1);
	}
	private String getNPCText(Point pos,int index) {
		String npcText = panel.getRecord().getNPC(pos,index);
		if(npcText==null) npcText = getDefaultNPCText(pos,index);
		return npcText;
	}
	private String getDefaultNPCText(Point pos,int index) {
		NPC n = panel.getNpcs().getNPC(index, pos);
		return n.toString(index+1);
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
		SettlementModel cities = panel.getSettlements();
		Settlement city = cities.getSettlement(capital);
		StringBuilder c1Text = new StringBuilder();
		c1Text.append(city.toString());
		String string = c1Text.toString();
		return string;
	}
	private String getDistrictLinkText(Point capital,int index) {
		SettlementModel cities = panel.getSettlements();
		Settlement city = cities.getSettlement(capital);
		return city.getDistricts().get(index);
	}
	private String getFactionText(Point pos,int i) {
		String factionText = panel.getRecord().getFaction(pos,i);
		if(factionText==null) factionText = getDefaultFactionText(pos,i);
		return factionText;
	}
	private String getDefaultFactionText(Point capital,int i) {
		return panel.getSettlements().getFaction(i, capital).toString();
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
		return panel.getPois().getPOI(i, pos,isCity);
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
		AltitudeModel grid = panel.getGrid();
		PrecipitationModel precipitation = panel.getPrecipitation();
		if(grid.isWater(pos)||precipitation.isLake(pos)) {
			return "Inn: none";
		}else {
			LocationNameModel names = panel.getNames();
			return names.getInnText(pos);
		}
	}
	private String getDungeonText(Point pos,int i) {
		String poiText = panel.getRecord().getDungeon(pos,i);
		if(poiText==null) poiText = getDefaultDungeonText(pos,i);
		return poiText;
	}
	private String getDefaultDungeonText(Point pos,int i) {
		return panel.getDungeon().getDungeon(i, pos).toString(i+1);
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

	private String getDungeonEncounterText(Point pos,int i) {
		String dungeonText = panel.getRecord().getDungeonEncounter(pos,i);
		if(dungeonText==null) dungeonText = getDefaultDungeonEncounterText(pos,i);
		return dungeonText;
	}
	private String getDefaultDungeonEncounterText(Point pos,int i) {
		Encounter e = panel.getEncounters().getDungeonEncounter(i+20, pos);
		return e.toString(i+1);
	}

	private String getHexNoteText(Point pos) {
		if(panel.getRecord().getNote(pos)!=null) return panel.getRecord().getNote(pos);
		else return "";
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
		panel.recenter(new Point(x,y), true);
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
				//encounterTexts.get(index).setCaretPosition(0);
				this.repaint();
				break;
			}
			case NPC_TAB_INDEX: {
				selectedNPC=index;
				//npcTexts.get(index).setCaretPosition(0);
				this.repaint();
				break;
			}
			case LOCATION_TAB_INDEX: {
				selectedPOI=index;
				poiTexts.get(index).grabFocus();
				//poiTexts.get(index).setCaretPosition(0);
				break;
			}
			case DUNGEON_TAB_INDEX: {
				selectedDungeon=index;
				//dEntranceTexts.get(index).setCaretPosition(0);
				this.repaint();
				break;
			}
			case DUNGEON_ENCOUNTER_TAB_INDEX: {
				selectedDEncounter=index;
				//dungeonTexts.get(index).setCaretPosition(0);
				this.repaint();
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
		Point p = new Point(x,y);
		String result;
		switch(tab) {
		case "npc": result = getNPCText(p, index);break;
		case "location": {
			PopulationModel population = panel.getPopulation();
			boolean isCity = population.isCity(p);
			result = getPOIText(p, index, isCity);break;
		}
		case "dungeon": result = getDungeonText(p, index);break;
		case "faction": {
			PopulationModel population = panel.getPopulation();
			Point capital = population.getAbsoluteFealty(p);
			result = getFactionText(capital, index);break;
		}
		case "district": {
			PopulationModel population = panel.getPopulation();
			Point capital = population.getAbsoluteFealty(p);
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
			PopulationModel population = panel.getPopulation();
			boolean isCity = population.isCity(p);
			result = getPOILinkText(p, index, isCity);break;
		}
		case "dungeon": result = getDungeonLinkText(p, index);break;
		case "faction": result = getFactionLinkText(p, index);break;
		case "district": {
			PopulationModel population = panel.getPopulation();
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

	private final class EncounterFocusListener implements FocusListener {
		private final JTextPane encounteri;
		int index;

		private EncounterFocusListener(JTextPane encounteri, int i) {
			this.encounteri = encounteri;
			this.index = i;
		}
		public void focusGained(FocusEvent e) {
			InfoPanel.this.repaint();
		}
		public void focusLost(FocusEvent e) {
			String text = encounteri.getText();
			Point p = panel.getSelectedGridPoint();
			String defaultText = getDefaultEncounterText(p,index);
			if(text==null||"".equals(text)||text.equals(defaultText)) panel.getRecord().removeEncounter(p,index);
			else panel.getRecord().putEncounter(p,index, text);
		}
	}
	private final class NPCFocusListener2 implements FocusListener {
		private final JTextPane npci;
		int index;

		private NPCFocusListener2(JTextPane npci, int i) {
			this.npci = npci;
			this.index = i;
		}
		public void focusGained(FocusEvent e) {
			InfoPanel.this.repaint();
		}
		public void focusLost(FocusEvent e) {
			String text = npci.getText();
			Point p = panel.getSelectedGridPoint();
			String defaultText = removeLinks(getDefaultNPCText(p,index));
			if(text==null||"".equals(text)||text.equals(defaultText)) panel.getRecord().removeNPC(p,index);
			else panel.getRecord().putNPC(p,index, text);
		}
	}
	public class POIFocusListener implements FocusListener {
		private final JTextPane poii;
		int index;

		private POIFocusListener(JTextPane poii, int i) {
			this.poii = poii;
			this.index = i;
		}
		public void focusGained(FocusEvent e) {
			InfoPanel.this.repaint();
		}
		public void focusLost(FocusEvent e) {
			String text = poii.getText();
			Point p = panel.getSelectedGridPoint();
			boolean isCity = panel.getPopulation().isCity(p);
			String defaultText = removeLinks(getDefaultPOIText(p,index,isCity));
			if(text==null||"".equals(text)||text.equals(defaultText)) panel.getRecord().removeLocation(p,index);
			else panel.getRecord().putLocation(p,index, text);
		}
	}
	public class DungeonEntranceFocusListener implements FocusListener {
		private final JTextPane poii;
		int index;

		private DungeonEntranceFocusListener(JTextPane poii, int i) {
			this.poii = poii;
			this.index = i;
		}
		public void focusGained(FocusEvent e) {
			InfoPanel.this.repaint();
		}
		public void focusLost(FocusEvent e) {
			String text = poii.getText();
			Point p = panel.getSelectedGridPoint();
			String defaultText = removeLinks(getDefaultDungeonText(p,index));
			if(text==null||"".equals(text)||text.equals(defaultText)) panel.getRecord().removeDungeon(p,index);
			else panel.getRecord().putDungeon(p,index, text);
		}
	}
	public class DungeonEncounterFocusListener implements FocusListener {
		private final JTextPane dungeoni;
		int index;

		private DungeonEncounterFocusListener(JTextPane dungeoni, int i) {
			this.dungeoni = dungeoni;
			this.index = i;
		}
		@Override
		public void focusGained(FocusEvent e) {
			InfoPanel.this.repaint();
		}

		@Override
		public void focusLost(FocusEvent e) {
			String text = dungeoni.getText();
			Point p = panel.getSelectedGridPoint();
			String defaultText = removeLinks(getDefaultDungeonEncounterText(p,index));
			if(text==null||"".equals(text)||text.equals(defaultText)) panel.getRecord().removeDungeonEncounter(p, index);
			else panel.getRecord().putDungeonEncounter(p,index, text);
		}
	}
	public class FactionFocusListener implements FocusListener {
		private final JTextPane factioni;
		int index;

		private FactionFocusListener(JTextPane factioni, int i) {
			this.factioni = factioni;
			this.index = i;
		}
		@Override
		public void focusGained(FocusEvent e) {
			InfoPanel.this.repaint();
		}

		@Override
		public void focusLost(FocusEvent e) {
			String text = factioni.getText();
			Point p = panel.getSelectedGridPoint();
			String defaultText = removeLinks(getDefaultFactionText(p,index));
			if(text==null||"".equals(text)||text.equals(defaultText)) panel.getRecord().removeFaction(p, index);
			else panel.getRecord().putFaction(p,index, text);
		}
	}
	public class HexNoteFocusListener implements FocusListener {
		@Override
		public void focusGained(FocusEvent e) {}

		@Override
		public void focusLost(FocusEvent e) {
			String text = hexNote1.getText();
			Point p = panel.getSelectedGridPoint();
			if(text==null||"".equals(text)) panel.getRecord().removeNote(p);
			else panel.getRecord().putNote(p, text);
		}
	}
	public class CityFocusListener implements FocusListener {
		@Override
		public void focusGained(FocusEvent e) {}

		@Override
		public void focusLost(FocusEvent e) {
			String text = city1.getText();
			Point p = panel.getPopulation().getAbsoluteFealty(panel.getSelectedGridPoint());
			if(text==null||"".equals(text)||text.equals(removeLinks(getDefaultCityText(p)))) panel.getRecord().removeCity(p);
			else panel.getRecord().putCity(p, text);
		}
	}
	public class ThreatFocusListener implements FocusListener {
		@Override
		public void focusGained(FocusEvent e) {}

		@Override
		public void focusLost(FocusEvent e) {
			String text = threatText.getText();
			Point center = panel.getThreats().getCenter(panel.getSelectedGridPoint());
			if(text==null||"".equals(text)||text.equals(removeLinks(getDefaultThreatText(center)))) panel.getRecord().removeThreat(center);
			else panel.getRecord().putThreat(center, text);
		}
	}

	@Override
    public void repaint() {
		super.repaint();
	}

}
