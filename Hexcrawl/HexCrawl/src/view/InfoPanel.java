package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

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

@SuppressWarnings("serial")
public class InfoPanel extends JTabbedPane{
	private static final int WIDTH = 450;
	public static final int ENCOUNTERCOUNT = 20;
	public static final int NPCCOUNT = 20;
	public static final int POICOUNT = 20;
	public static final int DUNGEONCOUNT = 6;
	private MapPanel panel;

	private JLabel pos;
	private JLabel height;
	private JLabel precipitation;
	private JLabel biome;
	private JLabel biome2;
	private JTextArea demographics;
	private JLabel magic;
	private JLabel locationName;
	private JLabel cityName;
	private JLabel demoLabel;
	//THREAT
	private JTextArea threatText;
	private JScrollPane threatScrollPane;
	//ENCOUNTER
	private JScrollPane encounterScrollPane;
	private JScrollPane npcScrollPane;
	private JPanel hexPanel;
	private JPanel regionPanel;
	private JLabel locationName2;
	private JLabel innName;
	private JLabel innText;
	private JTextArea city1;
	private JScrollPane cityScrollPane;
	private JScrollPane poiScrollPane;
	private JLabel roads;
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
	private ArrayList<JTextPane> encounterTexts;
	private ArrayList<JTextPane> dungeonTexts;
	private ArrayList<JTextPane> poiTexts;
	private ArrayList<JTextPane> dEntranceTexts;
	private JScrollPane dEntranceScrollPane;

	public InfoPanel() {
		this.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
		//Tab 1
		this.hexPanel = new JPanel();
		JPanel dummy0 = new JPanel();
		dummy0.setLayout(new BorderLayout());
		hexPanel.setPreferredSize(new Dimension(WIDTH,WIDTH));
		hexPanel.setLayout(new BoxLayout(hexPanel, BoxLayout.Y_AXIS));
		JPanel posData = new JPanel();
		posData.setLayout(new BorderLayout());

		JPanel dummy1 = new JPanel();
		dummy1.setLayout(new BorderLayout());
		pos = new JLabel("0,0");
		dummy1.add(pos,BorderLayout.NORTH);
		//posData.add(pos);


		JPanel regionNamePanelHEX = new JPanel();
		regionNamePanelHEX.setLayout(new BorderLayout());
		locationName = new JLabel("name");
		regionNamePanelHEX.add(locationName,BorderLayout.NORTH);
		biome2 = new JLabel("Grassland");
		regionNamePanelHEX.add(biome2,BorderLayout.CENTER);
		posData.add(regionNamePanelHEX,BorderLayout.NORTH);

		height = new JLabel("0.5");
		posData.add(height,BorderLayout.CENTER);

		precipitation = new JLabel("0.5");
		posData.add(precipitation,BorderLayout.SOUTH);

		dummy1.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.WEST);
		dummy1.add(posData,BorderLayout.CENTER);
		dummy1.setAlignmentX(LEFT_ALIGNMENT);
		dummy1.add(getSeparator(),BorderLayout.SOUTH);
		dummy0.add(dummy1,BorderLayout.NORTH);

		JPanel innPanel = new JPanel();
		innPanel.setLayout(new BoxLayout(innPanel, BoxLayout.Y_AXIS));
		this.roads = new JLabel("Roads: ");
		innPanel.add(roads);
		this.innName = new JLabel("Inn: ");
		innPanel.add(innName);
		this.innText = new JLabel("Description");
		innPanel.add(innText);
		innPanel.add(getSeparator());
		JPanel dummy2 = new JPanel();
		dummy2.setLayout(new BoxLayout(dummy2, BoxLayout.X_AXIS));
		dummy2.add(innPanel);
		dummy2.setAlignmentX(RIGHT_ALIGNMENT);
		dummy0.add(dummy2,BorderLayout.CENTER);

		demoLabel = new JLabel("Demographics: ");
		JPanel dummy3 = new JPanel();
		dummy3.setLayout(new BoxLayout(dummy3, BoxLayout.X_AXIS));
		dummy3.setAlignmentX(RIGHT_ALIGNMENT);
		dummy3.add(demoLabel);
		dummy0.add(dummy3,BorderLayout.SOUTH);
		hexPanel.add(dummy0);
		demographics = new JTextArea();
		demographics.setEditable(false);
		demographics.setLineWrap(true);
		demographics.setWrapStyleWord(true);
		demographics.setHighlighter(null);
		JScrollPane p = new JScrollPane(demographics);
		p.setMinimumSize(new Dimension(9999,52));
		p.setMaximumSize(new Dimension(9999,52));
		p.setPreferredSize(new Dimension(9999,52));
		hexPanel.add(p);

		detailsTabs = new JTabbedPane();

		JPanel encounterPanel = new JPanel();
		encounterPanel.setLayout(new BoxLayout(encounterPanel, BoxLayout.Y_AXIS));
		encounterTexts = new ArrayList<JTextPane>();
		for(int i=0;i<ENCOUNTERCOUNT;i++) {
			encounterPanel.add(new JLabel("~~~~~ Encounter #"+(i+1)+" ~~~~~"));
			JTextPane encounteri = new JTextPane();
			//encounteri.setLineWrap(true);
			//encounteri.setWrapStyleWord(true);
			encounteri.setMaximumSize(new Dimension(WIDTH-20,9999));
			encounteri.addFocusListener(new EncounterFocusListener(encounteri,i));
			encounteri.setAlignmentX(LEFT_ALIGNMENT);
			encounterPanel.add(encounteri);
			encounterTexts.add(encounteri);
		}
		encounterScrollPane = new JScrollPane(encounterPanel);
		encounterScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
			npci.setAlignmentX(LEFT_ALIGNMENT);
			npcPanel.add(npci);
			npcTexts.add(npci);
		}
		npcScrollPane = new JScrollPane(npcPanel);
		npcScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		detailsTabs.addTab("NPCs", npcScrollPane);


		JPanel poiPanel = new JPanel();
		poiPanel.setLayout(new BoxLayout(poiPanel, BoxLayout.Y_AXIS));
		poiTexts = new ArrayList<JTextPane>();
		for(int i=0;i<POICOUNT;i++) {
			poiPanel.add(new JLabel("~~~~~ Point of Interest #"+(i+1)+" ~~~~~"));
			JTextPane poii = new JTextPane();
//			poii.setLineWrap(true);
//			poii.setWrapStyleWord(true);
			poii.setMaximumSize(new Dimension(WIDTH-20,9999));
			poii.addFocusListener(new POIFocusListener(poii,i));
			poii.setAlignmentX(LEFT_ALIGNMENT);
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
			poii.setAlignmentX(LEFT_ALIGNMENT);
			dEntrancePanel.add(poii);
			dEntranceTexts.add(poii);
		}
		dEntranceScrollPane = new JScrollPane(dEntrancePanel);
		dEntranceScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		detailsTabs.addTab("Dungeon", dEntranceScrollPane);
		
		JPanel dungeonPanel = new JPanel();
		dungeonPanel.setLayout(new BoxLayout(dungeonPanel, BoxLayout.Y_AXIS));
		dungeonTexts = new ArrayList<JTextPane>();
		for(int i=0;i<ENCOUNTERCOUNT;i++) {
			dungeonPanel.add(new JLabel("~~~~~ Dungeon Encounter #"+(i+1)+" ~~~~~"));
			JTextPane encounteri = new JTextPane();
//			encounteri.setLineWrap(true);
//			encounteri.setWrapStyleWord(true);
			encounteri.setMaximumSize(new Dimension(WIDTH-20,9999));
			encounteri.addFocusListener(new DungeonEncounterFocusListener(encounteri,i));
			encounteri.setAlignmentX(LEFT_ALIGNMENT);
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
		hexNote1.setCaretPosition(0);
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
		city1.setCaretPosition(0);
		regionTabs.addTab("Parent City", cityScrollPane);
		regionPanel.add(regionTabs);

		this.addTab("                        Region                        ", new JScrollPane(regionPanel));
		encounterScrollPane.getVerticalScrollBar().setValue(0);

	}

	@Override
	public void paint(Graphics g){
		Point pos;
		if(panel.isShowDistance()) pos = panel.getMouseoverGridPoint();
		else pos = panel.getSelectedGridPoint();
		AltitudeModel grid = panel.getGrid();
		PrecipitationModel precipitation = panel.getPrecipitation();
		BiomeModel biomes = panel.getBiomes();
		PopulationModel population = panel.getPopulation();
		LocationNameModel names = panel.getNames();
		Point zero = panel.getRecord().getZero();
		this.pos.setText("Coords: "+Util.posString(pos,zero));

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
				this.locationName.setText("CITY NAME: ★ "+cityname+" ("+size.getName()+")");
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
				this.locationName.setText("Town Name: ⬤ "+townname+" ("+size.getName()+")");
				this.locationName2.setText("Town Name: ⬤ ");
				this.regionNameField.setText(townname);
				this.citySizeLabel.setText(" ("+size.getName()+")");
				this.cityName.setText(cityText);
			}else {
				String cityname = getRegionNameText(capital,true)+" ("+Util.posString(capital,zero)+")";
				if(!population.isCity(capital))cityname = "None";
				String locationname = getRegionNameText(region,false);
				String cityText = "Parent City: "+cityname;
				this.locationName.setText("Region Name: "+locationname);
				this.locationName2.setText("Region Name: ");
				this.regionNameField.setText(locationname);
				this.citySizeLabel.setText(null);
				this.cityName.setText(cityText);
			}
		}else {
			String locationname = getRegionNameText(region,false);
			String cityText = "Parent City: none";
			this.locationName.setText("Region Name: "+locationname);
			this.locationName2.setText("Region Name: ");
			this.regionNameField.setText(locationname);
			this.citySizeLabel.setText(null);
			this.cityName.setText(cityText);
		}

		float altitudeTransformation = AltitudeModel.altitudeTransformation(grid.getHeight(pos));
		String heightString = new DecimalFormat ("#0.0").format(altitudeTransformation);
		this.height.setText("Average Altitude: "+heightString+" ft");

		float precipitationTransformation = PrecipitationModel.precipitationTransformation(precipitation.getPrecipitation(pos));
		String precipitationString = new DecimalFormat ("#0.0").format(precipitationTransformation);
		this.precipitation.setText("Annual Precipitation: "+precipitationString+" mm");

		String biome = getBiomeText(pos);
		this.biome.setText("Biome Type: "+biome);
		this.biome2.setText("Biome Type: "+biome);

		String magics = panel.getMagic().getMagicType(pos).getName();
		this.magic.setText("Magic Type: "+magics);


		this.demographics.setText(getDemoString(pos));
		this.threatText.setText(getThreatText(pos));

		int transformedUniversalPopulation = population.getTransformedUniversalPopulation(pos);
		this.demoLabel.setText("Demographics: "+getDemoLabelText(pop, popScale,transformedUniversalPopulation));
		if(grid.isWater(pos)||precipitation.isLake(pos))  this.roads.setText("Roads: none");
		else this.roads.setText("Roads: "+panel.getEconomy().getRoadDescription(pos));

		if(grid.isWater(pos)||precipitation.isLake(pos)) {
			this.innName.setText("Inn: none");
			this.innText.setText("");
			this.city1.setText("None");

			zeroPopComponents();
		}else {

			this.innName.setText("Inn: "+names.getInnName(pos));
			this.innText.setText("Quirk: "+names.getInnQuirk(pos));

			positivePopComponents();

			for(int i = 0;i<NPCCOUNT;i++) {
				this.encounterTexts.get(i).setText(getEncounterText(pos,i));
			}
			this.encounterTexts.get(0).setCaretPosition(0);

			if(transformedUniversalPopulation>0) {
				enableNPCTab(true);
				for(int i = 0;i<NPCCOUNT;i++) {
					this.npcTexts.get(i).setText(getNPCText(pos,i));
				}
				this.npcTexts.get(0).setCaretPosition(0);
			}else {
				enableNPCTab(false);
			}

			for(int i = 0;i<POICOUNT;i++) {
				this.poiTexts.get(i).setText(getPOIText(pos,i,pos.equals(capital)));
			}
			this.poiTexts.get(0).setCaretPosition(0);
			
			for(int i = 0;i<DUNGEONCOUNT;i++) {
				this.dEntranceTexts.get(i).setText(getDungeonText(pos,i));
			}
			this.dEntranceTexts.get(0).setCaretPosition(0);

			for(int i = 0;i<NPCCOUNT;i++) {
				this.dungeonTexts.get(i).setText(getDungeonEncounterText(pos,i));
			}
			this.dungeonTexts.get(0).setCaretPosition(0);

			if(population.isCity(capital)) {
				this.city1.setText(getCityText(capital));
				this.city1.setCaretPosition(0);
				regionTabs.setEnabledAt(0, true);
				city1.setEnabled(true);
			}else {
				this.city1.setText("None");
				regionTabs.setEnabledAt(0, false);
				city1.setEnabled(false);
			}
		}

		this.hexNote1.setText(getHexNoteText(pos));
		super.paint(g);
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

	private String getBiomeText(Point pos) {
		BiomeModel biomes = panel.getBiomes();
		MagicModel magic = panel.getMagic();
		String biome = biomes.getBiome(pos).getName();
		if(magic.isWeird(pos)) {
			Point region = biomes.getAbsoluteRegion(pos);
			biome = magic.getWeirdness(region).toLowerCase() + " "+biome;
		}
		return biome;
	}

	private String getDemoLabelText(float pop, int popScale, int transformedUniversalPopulation) {
		if(transformedUniversalPopulation>0) {
			return panel.getPopulation().demoTransformString(pop,pop, popScale);
		}else {
			return "None";
		}
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
		enableEncountersTab(true);
		enableNPCTab(true);
		enableLocationsTab(true);
		enableDungeonTab(true);
		enableDungeonEncounterTab(true);
		if(detailsSelectedIndex>-1) {
			detailsTabs.setSelectedIndex(detailsSelectedIndex);
			detailsSelectedIndex = -1;
		}
	}

	private void zeroPopComponents() {
		if(detailsSelectedIndex==-1) detailsSelectedIndex = detailsTabs.getSelectedIndex();
		enableEncountersTab(false);
		enableNPCTab(false);
		enableLocationsTab(false);
		enableDungeonTab(false);
		enableDungeonEncounterTab(false);
		detailsTabs.setSelectedIndex(5);
	}

	private void enableEncountersTab(boolean value) {
		detailsTabs.setEnabledAt(0, value);
	}
	private void enableNPCTab(boolean value) {
		detailsTabs.setEnabledAt(1, value);
	}
	private void enableLocationsTab(boolean value) {
		detailsTabs.setEnabledAt(2, value);
	}
	private void enableDungeonTab(boolean value) {
		detailsTabs.setEnabledAt(3, value);
	}
	private void enableDungeonEncounterTab(boolean value) {
		detailsTabs.setEnabledAt(4, value);
	}

	private String getEncounterText(Point pos,int index) {
		String encounterText = panel.getRecord().getEncounter(pos,index);
		if(encounterText==null) encounterText = getDefaultEncounterText(pos,index);
		return encounterText;
	}

	private String getDefaultEncounterText(Point pos, int i) {
		Encounter e = panel.getEncounters().getEncounter(i, pos);
		return e.toString(i+1);
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
		c1Text.append("~~~~~ Factions ~~~~~\r\n");
		for(int i=0;i<6;i++) {
			c1Text.append(cities.getFaction(i, capital).toString(i+1));
		}
		String string = c1Text.toString();
		return string;
	}

	private String getPOIText(Point pos,int i, boolean isCity) {
		String poiText = panel.getRecord().getLocation(pos,i);
		if(poiText==null) poiText = getDefaultPOIText(pos,i,isCity);
		return poiText;
	}
	private String getDefaultPOIText(Point pos,int i, boolean isCity) {
		return panel.getPois().getPOI(i, pos,isCity);
	}
	private String getDungeonText(Point pos,int i) {
		String poiText = panel.getRecord().getDungeon(pos,i);
		if(poiText==null) poiText = getDefaultDungeonText(pos,i);
		return poiText;
	}
	private String getDefaultDungeonText(Point pos,int i) {
		return panel.getDungeon().getDungeon(i, pos).toString(i+1);
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

	private String getDemoString(Point pos) {
		PopulationModel population = panel.getPopulation();
		int pop = population.getTransformedUniversalPopulation(pos);
		LinkedHashMap<Species,Integer> demographics = population.getTransformedDemographics(pos);
		String demoString = "";
		for(Species s:demographics.keySet()) {
			if(demographics.get(s)!=null&&demographics.get(s)>0) {
				demoString+=s.name()+" "+population.demoTransformString(demographics.get(s),pop)+", ";
			}
		}
		if(demoString.length()>2) {
			demoString = demoString.substring(0, demoString.length()-2);
		}else {
			demoString = "none";
		}
		return demoString;
	}
	private JSeparator getSeparator() {
		JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
		separator.setMaximumSize(new Dimension(9999,1));
		return separator;
	}

	public class EncounterFocusListener implements FocusListener {
		private final JTextPane encounteri;
		int index;

		private EncounterFocusListener(JTextPane encounteri, int i) {
			this.encounteri = encounteri;
			this.index = i;
		}
		@Override
		public void focusGained(FocusEvent e) {}

		@Override
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
		public void focusGained(FocusEvent e) {}
		public void focusLost(FocusEvent e) {
			String text = npci.getText();
			Point p = panel.getSelectedGridPoint();
			String defaultText = getDefaultNPCText(p,index);
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
		public void focusGained(FocusEvent e) {}
		public void focusLost(FocusEvent e) {
			String text = poii.getText();
			Point p = panel.getSelectedGridPoint();
			boolean isCity = panel.getPopulation().isCity(p);
			String defaultText = getDefaultPOIText(p,index,isCity);
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
		public void focusGained(FocusEvent e) {}
		public void focusLost(FocusEvent e) {
			String text = poii.getText();
			Point p = panel.getSelectedGridPoint();
			String defaultText = getDefaultDungeonText(p,index);
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
		public void focusGained(FocusEvent e) {}

		@Override
		public void focusLost(FocusEvent e) {
			String text = dungeoni.getText();
			Point p = panel.getSelectedGridPoint();
			String defaultText = getDefaultDungeonEncounterText(p,index);
			if(text==null||"".equals(text)||text.equals(defaultText)) panel.getRecord().removeDungeonEncounter(p, index);
			else panel.getRecord().putDungeonEncounter(p,index, text);
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
			if(text==null||"".equals(text)||text.equals(getDefaultCityText(p))) panel.getRecord().removeCity(p);
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
			if(text==null||"".equals(text)||text.equals(getDefaultThreatText(center))) panel.getRecord().removeThreat(center);
			else panel.getRecord().putThreat(center, text);
		}

	}

}
