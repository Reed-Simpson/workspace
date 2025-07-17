package view.infopanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import data.HexData;
import data.altitude.AltitudeModel;
import data.biome.BiomeModel;
import data.population.NPCSpecies;
import data.population.PopulationModel;
import data.population.SettlementModel;
import data.population.SettlementSize;
import data.precipitation.PrecipitationModel;
import names.LocationNameModel;
import names.wilderness.WildernessNameGenerator;
import util.Util;
import view.InfoPanel;
import view.MapPanel;
import view.MyTextPane;

@SuppressWarnings("serial")
public class RegionPanel extends JPanel{
	private MapPanel panel;

	public final int CITY_TAB_INDEX;
	public final int FACTION_TAB_INDEX;
	public final int FAITH_TAB_INDEX;
	public final int FACTION_NPC_TAB_INDEX;
	public final int MINIONS_TAB_INDEX;
	public final int BEASTS_TAB_INDEX;
	
	private JLabel locationName2;
	private JTextField regionNameField;
	private JLabel citySizeLabel;
	private JLabel biome;
	private JLabel magic;
	private JLabel cityName;

	private MyTextPane threatText;
	private JTabbedPane regionTabs;
	private MyTextPane city1;
	private ArrayList<MyTextPane> factionTexts;
	private ArrayList<MyTextPane> faithsTexts;
	private ArrayList<MyTextPane> factionNPCTexts;
	private ArrayList<MyTextPane> minionsTexts;
	private ArrayList<MyTextPane> beastsTexts;
	private ArrayList<MyTextPane> threatMonsterTexts;
	private MyTextPane event;
	private ArrayList<MyTextPane> cityHistoryTexts;
	private ArrayList<MyTextPane> historyTexts;

	private int selectedFaction;
	private int selectedFaith;
	private int selectedFactionNPC;
	private int selectedMinion;
	private int selectedBeast;
	private int selectedMonster;
	private int selectedCityHistory;
	private int selectedHistory;

	private ArrayList<MyTextPane> districtTexts;

	private int selectedDistrict;

	private MyTextPane neighbors;

	public RegionPanel(InfoPanel info) {
		panel = info.getPanel();
		this.setPreferredSize(new Dimension(InfoPanel.INFOPANELWIDTH,InfoPanel.INFOPANELWIDTH));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


		JPanel dummy4 = new JPanel(new BorderLayout());
		JPanel regionNamePanel = new JPanel();
		regionNamePanel.setLayout(new BoxLayout(regionNamePanel, BoxLayout.X_AXIS));
		locationName2 = new JLabel("Region Name: ");
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

		biome = new JLabel("Biome Type: ");
		biome.setMaximumSize(new Dimension(400,20));
		dummy4.add(biome,BorderLayout.CENTER);

		magic = new JLabel("Magic Type: ");
		dummy4.add(magic,BorderLayout.SOUTH);

		dummy4.setMaximumSize(new Dimension(400,65));
		this.add(dummy4);
		this.add(getSeparator());

		threatText = new MyTextPane(info, 0, HexData.THREAT);
		JScrollPane threatScrollPane = new JScrollPane(threatText);
		threatScrollPane.setMaximumSize(new Dimension(9999,375));
		threatScrollPane.setPreferredSize(new Dimension(9999,375));
		this.add(threatScrollPane);

		this.add(getSeparator());

		cityName = new JLabel("Parent City: ");
		this.add(cityName);
		

		regionTabs = new JTabbedPane();
		
		//City tab
		JPanel cityPanel = new JPanel();
		cityPanel.setLayout(new BoxLayout(cityPanel, BoxLayout.Y_AXIS));
		
		city1 = new MyTextPane(info, 0, HexData.CITY);
		city1.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
		cityPanel.add(city1);
		this.CITY_TAB_INDEX = regionTabs.getTabCount()-1;
		
		districtTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<InfoPanel.DISTRICTCOUNT;i++) {
			cityPanel.add(new JLabel("~~~~~ District #"+(i+1)+" ~~~~~"));
			MyTextPane districti = new MyTextPane(info, i, HexData.DISTRICT);
			districti.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
			districti.setPreferredSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,50));
			cityPanel.add(districti);
			districtTexts.add(districti);
		}
		JScrollPane cityScrollPane = new JScrollPane(cityPanel);
		cityScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		regionTabs.addTab("Parent City", cityScrollPane);

		cityPanel.add(new JLabel("~~~~~ Neighbors ~~~~~"));
		neighbors = new MyTextPane(info, -1, HexData.NONE);
		neighbors.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
		neighbors.setEditable(false);
		cityPanel.add(neighbors);

		//Faction tab
		JPanel factionPanel = new JPanel();
		factionPanel.setLayout(new BoxLayout(factionPanel, BoxLayout.Y_AXIS));
		factionTexts = new ArrayList<MyTextPane>();
		factionPanel.add(new JLabel("~~~~~ City Leadership ~~~~~"));
		MyTextPane leadership = new MyTextPane(info, 0, HexData.FACTION);
		leadership.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
		factionPanel.add(leadership);
		factionTexts.add(leadership);
		for(int i=1;i<InfoPanel.FACTIONCOUNT;i++) {
			factionPanel.add(new JLabel("~~~~~ Faction #"+(i)+" ~~~~~"));
			MyTextPane factioni = new MyTextPane(info, i, HexData.FACTION);
			factioni.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
			factionPanel.add(factioni);
			factionTexts.add(factioni);
		}
		JScrollPane factionScrollPane = new JScrollPane(factionPanel);
		factionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		regionTabs.addTab("Factions", factionScrollPane);
		this.FACTION_TAB_INDEX = regionTabs.getTabCount()-1;

		faithsTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<InfoPanel.FAITHCOUNT;i++) {
			factionPanel.add(new JLabel("~~~~~ Faith #"+(i+1)+" ~~~~~"));
			MyTextPane factioni = new MyTextPane(info, i, HexData.FAITH);
			factioni.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
			factionPanel.add(factioni);
			faithsTexts.add(factioni);
		}
		this.FAITH_TAB_INDEX = regionTabs.getTabCount()-1;


		//Faction NPCs tab
		JPanel factionNPCPanel = new JPanel();
		factionNPCPanel.setLayout(new BoxLayout(factionNPCPanel, BoxLayout.Y_AXIS));
		factionNPCTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<(InfoPanel.FACTIONCOUNT+InfoPanel.FAITHCOUNT)*2;i++) {
			factionNPCPanel.add(new JLabel("~~~~~ NPC #"+(i+1)+" ~~~~~"));
			MyTextPane minion = new MyTextPane(info, i, HexData.FACTION_NPC);
			minion.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
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
		MyTextPane threatFaction = new MyTextPane(info, 0, HexData.MINION);
		threatFaction.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
		minionsPanel.add(threatFaction);
		minionsTexts.add(threatFaction);
		for(int i=1;i<InfoPanel.NPCCOUNT;i++) {
			minionsPanel.add(new JLabel("~~~~~ Minion #"+(i)+" ~~~~~"));
			MyTextPane minion = new MyTextPane(info, i, HexData.MINION);
			minion.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
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
		for(int i=0;i<InfoPanel.MONSTERCOUNT;i++) {
			beastsPanel.add(new JLabel("~~~~~ Regional Monster #"+(i+1)+" ~~~~~"));
			MyTextPane beast = new MyTextPane(info, i, HexData.MONSTER);
			beast.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
			beastsPanel.add(beast);
			beastsTexts.add(beast);
		}
		threatMonsterTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<InfoPanel.MONSTERCOUNT;i++) {
			beastsPanel.add(new JLabel("~~~~~ Threat Monster #"+(i+1)+" ~~~~~"));
			MyTextPane beast = new MyTextPane(info, i, HexData.THREATMONSTER);
			beast.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
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
		event = new MyTextPane(info, 0, HexData.EVENT);
		event.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
		event.setPreferredSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,5));
		event.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		historyPanel.add(event);
		
		cityHistoryTexts = new ArrayList<MyTextPane>();
		historyPanel.add(new JLabel("~~~~~ Recent History ~~~~~"));
		for(int i=0;i<InfoPanel.RECENT_HISTORY_COUNT;i++) {
			MyTextPane history = new MyTextPane(info, i, HexData.CITYHISTORY);
			history.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
			history.setPreferredSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,5));
			history.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
			historyPanel.add(history);
			cityHistoryTexts.add(history);
		}
		historyTexts = new ArrayList<MyTextPane>();
		historyPanel.add(new JLabel("~~~~~ Ancient History ~~~~~"));
		for(int i=0;i<4;i++) {
			MyTextPane history = new MyTextPane(info, i, HexData.HISTORY);
			history.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
			history.setPreferredSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,5));
			history.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
			historyPanel.add(history);
			historyTexts.add(history);
		}
		historyPanel.add(Box.createVerticalGlue());
		JScrollPane historyScrollPane = new JScrollPane(historyPanel);
		historyScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		regionTabs.addTab("History", historyScrollPane);
		

		selectedFaction = -1;
		selectedFaith = -1;
		selectedFactionNPC = -1;
		selectedMinion = -1;
		selectedBeast = -1;
		selectedMonster = -1;
		selectedCityHistory = -1;
		selectedHistory = -1;

		this.add(regionTabs);
	}

	@Override
	public void paintComponent(Graphics g){
		PopulationModel population = panel.getController().getPopulation();
		BiomeModel biomes = panel.getController().getBiomes();
		AltitudeModel grid = panel.getController().getGrid();
		PrecipitationModel precipitation = panel.getController().getPrecipitation();

		Point pos = panel.getSelectedGridPoint();
		Point capital = population.getAbsoluteFealty(pos);
		Point zero = panel.getRecord().getZero();
		Point region = biomes.getAbsoluteRegion(pos);
		NPCSpecies species = population.getMajoritySpecies(pos.x, pos.y);
		float pop = population.getUniversalPopulation(pos);
		int popScale = population.getPopScale(pos) ;
		int popValue = population.demoTransformInt(pop, popScale);
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
		
		SettlementModel cities = panel.getController().getSettlements();
		neighbors.setText(cities.getNeighborText(capital));

		for(int i = 0;i<this.districtTexts.size();i++) {
			MyTextPane pane = this.districtTexts.get(i);
			pane.setHighlight(i==selectedDistrict);
			pane.doPaint();
		}

		if(grid.isWater(pos)||precipitation.isLake(pos)) {
			enableCityTabs(false);
		}else {
			if(population.isCity(capital)) {
				enableCityTabs(true);
			}else {
				enableCityTabs(false);
			}
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
	private JSeparator getSeparator() {
		JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
		separator.setMaximumSize(new Dimension(9999,1));
		return separator;
	}

	private void enableCityTabs(boolean enabled) {
		//regionTabs.setEnabledAt(CITY_TAB_INDEX, enabled);
		//regionTabs.setEnabledAt(FACTION_TAB_INDEX, enabled);
		city1.setEnabled(enabled);
		if(!enabled) {
			this.city1.setText("None");
		}
	}

	public void setRegionTabs(int subtab) {
		this.regionTabs.setSelectedIndex(subtab);
	}

	public int getSelectedFaction() {
		return selectedFaction;
	}

	public void setSelectedFaction(int selectedFaction) {
		this.selectedFaction = selectedFaction;
	}

	public int getSelectedFaith() {
		return selectedFaith;
	}

	public void setSelectedFaith(int selectedFaith) {
		this.selectedFaith = selectedFaith;
	}

	public int getSelectedFactionNPC() {
		return selectedFactionNPC;
	}

	public void setSelectedFactionNPC(int selectedFactionNPC) {
		this.selectedFactionNPC = selectedFactionNPC;
	}

	public int getSelectedMinion() {
		return selectedMinion;
	}

	public void setSelectedMinion(int selectedMinion) {
		this.selectedMinion = selectedMinion;
	}

	public int getSelectedBeast() {
		return selectedBeast;
	}

	public void setSelectedBeast(int selectedBeast) {
		this.selectedBeast = selectedBeast;
	}

	public int getSelectedMonster() {
		return selectedMonster;
	}

	public void setSelectedMonster(int selectedMonster) {
		this.selectedMonster = selectedMonster;
	}

	public int getSelectedCityHistory() {
		return selectedCityHistory;
	}

	public void setSelectedCityHistory(int selectedCityHistory) {
		this.selectedCityHistory = selectedCityHistory;
	}

	public int getSelectedHistory() {
		return selectedHistory;
	}

	public void setSelectedHistory(int selectedHistory) {
		this.selectedHistory = selectedHistory;
	}

}
