package view.infopanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import controllers.DataController;
import data.altitude.AltitudeModel;
import data.biome.BiomeModel;
import data.population.PopulationModel;
import data.population.SettlementSize;
import data.population.NPCSpecies;
import data.precipitation.PrecipitationModel;
import names.LocationNameModel;
import names.wilderness.WildernessNameGenerator;
import util.Util;
import view.InfoPanel;
import view.MapPanel;

@SuppressWarnings("serial")
public class HexPanelGeneralStatPanel extends JPanel{
	private InfoPanel info;
	
	private JLabel pos;
	private JLabel locationName;
	private JLabel biome2;
	private JLabel height;
	private JLabel precipitation;
	private JLabel roads;
	private JLabel innName;
	private JLabel innText;

	public HexPanelGeneralStatPanel(InfoPanel panel) {
		this.info = panel;
		this.setLayout(new BorderLayout());
		JPanel posData = new JPanel();
		posData.setLayout(new BorderLayout());

		JPanel dummy1 = new JPanel();
		dummy1.setLayout(new BorderLayout());
		pos = new JLabel("0,0");
		dummy1.add(pos,BorderLayout.NORTH);


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
		this.add(dummy1,BorderLayout.NORTH);

		JPanel roadsPanel = new JPanel();
		roadsPanel.setLayout(new BoxLayout(roadsPanel, BoxLayout.Y_AXIS));
		this.roads = new JLabel("Roads: ");
		roadsPanel.add(roads);
		JPanel dummy2 = new JPanel();
		dummy2.setLayout(new BoxLayout(dummy2, BoxLayout.X_AXIS));
		dummy2.add(roadsPanel);
		dummy2.setAlignmentX(RIGHT_ALIGNMENT);
		this.add(dummy2,BorderLayout.CENTER);

	}

	public void dopaint(){
		MapPanel panel = info.getPanel();
		DataController controller = panel.getController();
		Point pos;
		if(panel.isShowDistance()) pos = panel.getMouseoverGridPoint();
		else pos = panel.getSelectedGridPoint();
		Point zero = panel.getRecord().getZero();
		PopulationModel population = controller.getPopulation();
		BiomeModel biomes = controller.getBiomes();
		AltitudeModel grid = controller.getGrid();
		PrecipitationModel precipitation = controller.getPrecipitation();

		this.setPos("Coords: "+Util.posString(pos,zero));

		float pop = population.getUniversalPopulation(pos);
		int popScale = population.getPopScale(pos) ;
		int popValue = population.demoTransformInt(pop, popScale);
		Point capital = population.getAbsoluteFealty(pos);
		Point region = biomes.getAbsoluteRegion(pos);
		if(population.isCity(pos)) {
			String cityname = getRegionNameText(capital,true);
			SettlementSize size = SettlementSize.getSettlementSize(popValue);
			this.setLocation("CITY NAME: ★ "+cityname+" ("+size.getName()+")");
		}else if(population.isTown(pos)) {
			SettlementSize size = SettlementSize.getSettlementSize(popValue);
			String townname = getRegionNameText(pos,true);
			this.setLocation("Town Name: ⬤ "+townname+" ("+size.getName()+")");
		}else {
			String locationname = getRegionNameText(region,false);
			this.setLocation("Region Name: "+locationname);
		}

		float altitudeTransformation = AltitudeModel.altitudeTransformation(grid.getHeight(pos));
		String heightString = new DecimalFormat ("#0.0").format(altitudeTransformation);
		this.setHeight("Average Altitude: "+heightString+" ft");

		float precipitationTransformation = PrecipitationModel.precipitationTransformation(precipitation.getPrecipitation(pos));
		String precipitationString = new DecimalFormat ("#0.0").format(precipitationTransformation);
		this.setPrecipitation("Annual Precipitation: "+precipitationString+" mm");

		String biome = info.getBiomeText(pos);
		this.setBiome("Biome Type: "+biome);
		
		if(grid.isWater(pos)||precipitation.isLake(pos))  this.setRoads("Roads: none");
		else this.setRoads("Roads: "+controller.getEconomy().getRoadDescription(pos));
		
	}
	
	
	
	@SuppressWarnings("unused")
	private JSeparator getSeparator() {
		JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
		separator.setMaximumSize(new Dimension(9999,1));
		return separator;
	}
	public void setPos(String string) {
		this.pos.setText(string);
	}
	public void setLocation(String string) {
		this.locationName.setText(string);
	}
	public void setHeight(String string) {
		this.height.setText(string);
	}
	public void setPrecipitation(String string) {
		this.precipitation.setText(string);
	}
	public void setBiome(String string) {
		this.biome2.setText(string);
	}
	public void setRoads(String string) {
		this.roads.setText(string);
	}
	public void setInnName(String string) {
		this.innName.setText(string);
	}
	public void setInnDescription(String string) {
		this.innText.setText(string);
	}
	

	private String getRegionNameText(Point pos,boolean isCity) {
		String regionNameText = info.getPanel().getRecord().getRegionName(pos);
		if(regionNameText==null) regionNameText = getDefaultRegionNameText(pos,isCity);
		return regionNameText;
	}

	private String getDefaultRegionNameText(Point pos,boolean isCity) {
		DataController controller = info.getPanel().getController();
		if(isCity) {
			NPCSpecies species = controller.getPopulation().getMajoritySpecies(pos.x, pos.y);
			LocationNameModel names = controller.getNames();
			return names.getName(species.getCityNameGen(), pos);
		}else {
			BiomeModel biomes = controller.getBiomes();
			Point region = biomes.getAbsoluteRegion(pos);
			return biomes.getRegionName(region)+" " + WildernessNameGenerator.getBiomeName(biomes.getBiome(pos));
		}
	}

}
