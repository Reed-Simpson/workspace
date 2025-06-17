package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import biome.BiomeModel;
import biome.BiomeType;
import dungeon.DungeonModel;
import economy.EconomicModel;
import encounters.EncounterModel;
import general.Counter;
import general.Graph;
import general.MyLogger;
import general.MapFrame;
import general.ProgressBarDialog;
import general.Util;
import io.AppData;
import io.SaveRecord;
import location.LocationModel;
import magic.MagicModel;
import map.AltitudeModel;
import map.HexData;
import names.LocationNameModel;
import npc.NPCModel;
import population.PopulationModel;
import precipitation.PrecipitationModel;
import settlement.SettlementModel;
import threat.ThreatModel;

public class MapPanel  extends JPanel{
	private static final long serialVersionUID = 6922738675563657970L;
	private static final Color LINE_COLOR = Color.BLACK;
	private static final double sqrt3 = Math.sqrt(3.0);
	public static final int MIN_SCALE = 1;
	private static final int MAX_SCALE = 500;
	private static final int HIDE_BORDERS_SCALE = 16;
	private static final int LOG_THRESHOLD = 500;
	private MapFrame frame;
	private ProgressBarDialog dialog;
	private AltitudeModel grid;
	private InfoPanel infoPanel;
	private Point center; //center represents the pixel offset from 0,0
	private Point mouseover;
	private double scale; // Scale represents the pixel distance from the center of one hex to an adjacent one
	private boolean isDragging = false;
	private int dragOffsetX, dragOffsetY;
	private HexData displayData;
	private PrecipitationModel precipitation;
	private BiomeModel biomes;
	private PopulationModel population;
	private MagicModel magic;
	private LocationNameModel names;
	private EconomicModel economy;
	private ThreatModel threats;
	private EncounterModel encounters;
	private NPCModel npcs;
	private SettlementModel settlements;
	private LocationModel pois;
	private DungeonModel dungeons;
	private SaveRecord record;
	private boolean showRivers;
	private boolean showCities;
	private boolean printLoadingInfo;
	private boolean initializing;
	private boolean showDistance;
	private double distance;
	private long time;
	private HexData displayRegion;
	private ArrayList<Point> previous;
	int previousIndex;

	public MapPanel(MapFrame frame, SaveRecord record,InfoPanel infoPanel, ProgressBarDialog dialog) {
		this.frame = frame;
		this.dialog = dialog;
		this.reloadFromSaveRecord(record);
		this.infoPanel=infoPanel;
		infoPanel.setPanel(this);
		this.addMouseListener(new MouseAdapter());
		this.addMouseMotionListener(new MouseMotionAdapter());
		this.addMouseWheelListener(new MouseWheelAdapter());
		this.setDisplayData(HexData.ALTITUDE);
		this.setPreferredSize(new Dimension(1000, 1000));
		this.printLoadingInfo = true;
		previousIndex = 0;
		this.repaint();
	}

	public void reloadFromSaveRecord(SaveRecord record) {
		this.record = record;
		this.grid=new AltitudeModel(record);
		this.precipitation = new PrecipitationModel(record,grid);
		this.population = new PopulationModel(record, grid, precipitation);
		this.magic = new MagicModel(record);
		this.biomes = new BiomeModel(record, grid, precipitation,population);
		this.economy = new EconomicModel(record,population,biomes,precipitation,grid);
		this.names = new LocationNameModel(record);
		this.npcs = new NPCModel(record, population);
		this.threats = new ThreatModel(record,npcs);
		this.settlements = new SettlementModel(record);
		this.pois = new LocationModel(record);
		this.dungeons = new DungeonModel(record);
		this.encounters = new EncounterModel(record,population);
		//this.recenter(record.getPos());
		this.printLoadingInfo = false;
		this.scale = record.getScale();
		this.previous=new ArrayList<Point>();
		if(!record.isInitialized()) {
			initializing = true;
			this.recenter(record.getPos(),false);
			record.initialize(grid,population);
			calculateRivers((int) scale);
			record.initialize(grid,population);
			initializing = false;
		}
		recenter(record.getPos(),true);
		frame.repaint();
		record.setHasUnsavedData(false);
	}

	public Point getScreenPos(Point p) {
		return getScreenPos(p.x, p.y);
	}
	public Point getScreenPos(int x0,int y0) {
		int x1=(int) (this.getWidth()/2+(x0)*scale*2+(y0)*scale-center.x);
		int y1=this.getHeight()/2+(int)((y0)*scale*3/sqrt3)-center.y;
		return new Point(x1,y1);
	}
	public Point getAbsolutePos(Point p) {
		return getAbsolutePos(p.x, p.y);
	}
	public Point getAbsolutePos(int x0,int y0) {
		int x1=(int) (this.getWidth()/2+(x0)*scale*2+(y0)*scale);
		int y1=this.getHeight()/2+(int)((y0)*scale*3/sqrt3);
		return new Point(x1,y1);
	}
	public Point getGridPoint(Point p) {
		return this.getGridPoint(p.x, p.y);
	}
	public Point getGridPoint(int x1, int y1) {
		double yn = (y1+center.y-(double)this.getHeight()/2.0)/scale;
		if(yn<0) yn-=1;
		else yn+=1;
		int y0 = (int)(yn*sqrt3/3);
		double xn = (x1+center.x-(double)this.getWidth()/2.0)/scale-y0;
		if(xn>0) xn+=1;
		else xn-=1;
		int x0 = (int)(xn/2);

		return new Point(x0,y0);
	}
	public Point getSelectedGridPoint() {
		return getGridPoint(getMiddleOfScreen());
	}
	public Point getMouseoverGridPoint() {
		return mouseover;
	}
	public Point getMiddleOfScreen() {
		return new Point(this.getWidth()/2, (int) (this.getHeight()/2+scale/2));
	}
	public void recenter() {
		recenter(getSelectedGridPoint(),true);
	}
	public void recenter(Point p,boolean updatePrevious) {
		if(updatePrevious&&previousIndex>-1) {
			for(int i=previous.size()-1;i>previousIndex;i--) {
				previous.remove(i);
			}
		}
		Point targetHexPos = getAbsolutePos(p);
		Point middle = getMiddleOfScreen();
		center= new Point(targetHexPos.x-middle.x, targetHexPos.y-middle.y);
		printLoadingInfo = true;
		if(updatePrevious) {
			previous.add(getSelectedGridPoint());
			previousIndex=previous.size()-1;
		}
	}
	public AltitudeModel getGrid() {
		return this.grid;
	}
	public double getScale() {
		return this.scale;
	}
	public void setScale(double d) {
		this.scale=d;
		record.setScale(scale);
	}

	public HexData getDisplayData() {
		return displayData;
	}
	public void setDisplayData(HexData displayData) {
		this.displayData = displayData;
	}

	public void setDisplayRegion(HexData selectedItem) {
		this.displayRegion = selectedItem;
	}

	@Override
	public void paint(Graphics g){
		time = System.currentTimeMillis();
		Graphics2D g2 = (Graphics2D) g;
		int step = 1;
		int displayScale = (int)scale;
		if(scale<MIN_SCALE) {
			step = (int) (MIN_SCALE/scale);
			displayScale = MIN_SCALE;
		}
		Color borderColor = LINE_COLOR;
		if(scale<HIDE_BORDERS_SCALE) {
			borderColor = null;
		}
		if(!isDragging)calculateRivers(displayScale);
		drawHexes(g2, step, displayScale, borderColor);
		if(showRivers) {
			drawRivers(g2, step, displayScale, borderColor);
		}
		drawOceans(g2, step, displayScale, borderColor);
		if(HexData.ECONOMY.equals(displayData)) {
			drawRoads(g2,step,displayScale,Color.RED);
			highlightTowns(g2,step,displayScale,Color.RED);
		}
		drawRegion(g2, displayScale);
		drawSelectedHex(g2, displayScale);
		drawSymbols(g2, step, displayScale, borderColor);
		if(!isDragging)drawDistanceMarker(g2, step, displayScale, Color.RED);

		printLoadingInfo = false;
		infoPanel.repaint();
		dialog.removeProgressUI();
	}

	private void drawHexes(Graphics2D g2, int step, int displayScale, Color borderColor) {
		Point p1 = getGridPoint(-2*displayScale,this.getHeight()+4*displayScale);
		Point p2 = getGridPoint(this.getWidth()+2*displayScale,-2*displayScale);
		int sum = (p2.x-p1.x);
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		if(printLoadingInfo) {
			logger.log("Loading hexes: ");
			dialog.createProgressUI("Loading hexes: ");
		}
		for(int i=p1.x;i<p2.x;i+=step) {
			for(int j=p2.y;j<p1.y;j+=step) {
				if(!grid.isWater(i,j)) {
					Color color1 = getColor1(i,j,displayData);
					Color color2 = getColor2(i,j,displayData);
					if(color1==null) {
						color1 = color2;
						color2 = null;
					}
					this.drawHex(g2, getScreenPos(i,j),borderColor,color1,color2,displayScale,null);
					if(borderColor!=null) {
						g2.setColor(borderColor);
						Character c = biomes.getBiome(i, j).getCh();
						if(c!=null) g2.drawString(c.toString(), getScreenPos(i,j).x-10, getScreenPos(i,j).y+6);
					}
				}
			}
			if(printLoadingInfo) counter.increment();
		}
		if(printLoadingInfo) logger.logln("Hexes loaded "+(System.currentTimeMillis()-time)+" ms");
	}

	private void drawOceans(Graphics2D g2, int step, int displayScale, Color borderColor) {
		Point p1 = getGridPoint(-2*displayScale,this.getHeight()+4*displayScale);
		Point p2 = getGridPoint(this.getWidth()+2*displayScale,-2*displayScale);
		int sum = (p2.x-p1.x);
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		if(printLoadingInfo) {
			logger.log("Drawing oceans: ");
			dialog.createProgressUI("Drawing oceans: ");
		}
		for(int i=p1.x;i<p2.x;i+=step) {
			for(int j=p2.y;j<p1.y;j+=step) {
				if(grid.isWater(i,j)) {
					Color color1 = getColor1(i,j,displayData);
					Color color2 = getColor2(i,j,displayData);
					if(color1==null) {
						color1 = color2;
						color2 = null;
					}
					this.drawHex(g2, getScreenPos(i,j),borderColor,color1,color2,displayScale,null);
					if(borderColor!=null) {
						g2.setColor(borderColor);
						Character c = biomes.getBiome(i, j).getCh();
						if(c!=null) g2.drawString(c.toString(), getScreenPos(i,j).x-10, getScreenPos(i,j).y+6);
					}
				}
			}
			if(printLoadingInfo) counter.increment();
		}
		if(printLoadingInfo) logger.logln("Oceans loaded "+(System.currentTimeMillis()-time)+" ms");
	}

	private void drawSelectedHex(Graphics2D g2, int displayScale) {
		Stroke defaultStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(2));
		Point p = this.getSelectedGridPoint();
		Color color1 = getColor1(p.x,p.y,displayData);
		Color color2 = getColor2(p.x,p.y,displayData);
		if(color1==null) {
			color1 = color2;
			color2 = null;
		}
		this.drawHex(g2, getScreenPos(p),Color.CYAN,color1,color2,displayScale,null);
		g2.setStroke(defaultStroke);
	}

	private void drawSymbols(Graphics2D g2, int step, int displayScale, Color borderColor) {
		if(borderColor!=null) {
			Point p1 = getGridPoint(-2*displayScale,this.getHeight()+4*displayScale);
			Point p2 = getGridPoint(this.getWidth()+2*displayScale,-2*displayScale);
			int sum = (p2.x-p1.x);
			MyLogger logger = new MyLogger(LOG_THRESHOLD);
			Counter counter = new Counter(sum, dialog.getProgressBar());
			counter.setLog(logger);
			if(printLoadingInfo) {
				logger.log("Drawing symbols: ");
				dialog.createProgressUI("Drawing symbols: ");
			}
			for(int i=p1.x;i<p2.x;i+=step) {
				for(int j=p2.y;j<p1.y;j+=step) {
					g2.setColor(borderColor);
					g2.setFont(g2.getFont().deriveFont(displayScale));
					Character c = biomes.getBiome(i, j).getCh();
					if(c!=null) g2.drawString(c.toString(), getScreenPos(i,j).x, getScreenPos(i,j).y);
				}
				if(printLoadingInfo) counter.increment();
			}
			logger.logln("Symbols drawn "+(System.currentTimeMillis()-time)+" ms");
		}
	}

	private void drawDistanceMarker(Graphics2D g2, int step, int displayScale, Color c) {
		if(showDistance&&mouseover!=null) {
			time = System.currentTimeMillis();
			Stroke defaultStroke = g2.getStroke();
			g2.setColor(c);
			g2.setStroke(new BasicStroke(1+displayScale/2));
			Point center = this.getSelectedGridPoint();
			Point midpoint;
			int dx = mouseover.x-center.x;
			int dy = mouseover.y-center.y;
			if(dx*dy>=0) {
				midpoint = new Point(mouseover.x,center.y);
			}else if(dx*dx>dy*dy){
				midpoint = new Point(center.x-dy,mouseover.y);
			}else {
				midpoint = new Point(mouseover.x,center.y-dx);
			}
			g2.drawLine(getScreenPos(center).x, getScreenPos(center).y, getScreenPos(midpoint).x, getScreenPos(midpoint).y);
			g2.drawLine(getScreenPos(midpoint).x, getScreenPos(midpoint).y, getScreenPos(mouseover).x, getScreenPos(mouseover).y);
			g2.setStroke(defaultStroke);
		}
	}

	private void calculateRivers(int displayScale) {
		Point p1 = getGridPoint(-2*displayScale,this.getHeight()+4*displayScale);
		Point p2 = getGridPoint(this.getWidth()+2*displayScale,-2*displayScale);
		p1.translate(-100, 100);
		p2.translate(100, -100);
		int sum = (p2.x-p1.x);
		int loadingFactor = (p1.y-p2.y);
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		if(printLoadingInfo) {
			dialog.createProgressUI("Loading rivers "+(sum*loadingFactor)+" ~300ms: ");
			logger.log("Loading rivers "+(sum*loadingFactor)+" ~300ms: ");
		}
		for(int i=p1.x;i<p2.x;i+=1) {
			for(int j=p2.y;j<p1.y;j+=1) {
				if(!grid.isWater(i,j)) {
					precipitation.getFlow(new Point(i,j));
				}
			}
			if(printLoadingInfo) counter.increment();
		}
		if(printLoadingInfo) logger.logln("--(100%) Rivers loaded "+(System.currentTimeMillis()-time)+" ms");

		if(initializing) {
			dialog.createProgressUI("Initializing ~2 minutes: ");
			logger.log("Initializing "+(sum*loadingFactor)+" ~100 seconds: ");
			counter.resetCounter();
		}
		if(printLoadingInfo) {
			dialog.createProgressUI("Loading lakes ~10 s: ");
			logger.log("Loading lakes "+(sum*loadingFactor)+" ~10000ms: ");
		}
		for(int i=p1.x;i<p2.x;i+=1) {
			for(int j=p2.y;j<p1.y;j+=1) {
				Point p = new Point(i,j);
				if(!grid.isWater(i,j)&&p.equals(precipitation.getFlow(p))) {
					precipitation.generateLake(p);
				}
			}
			if(printLoadingInfo) counter.increment();
		}
		if(printLoadingInfo) logger.logln("--(100%) Lakes loaded "+(System.currentTimeMillis()-time)+" ms");

		counter.resetCounter();
		if(printLoadingInfo) {
			dialog.createProgressUI("Loading river volume ~1 minute: ");
			logger.log("Loading river volume "+(sum*loadingFactor)+" ~60000ms: ");
		}
		for(int i=p1.x;i<p2.x;i+=1) {
			for(int j=p2.y;j<p1.y;j+=1) {
				if(!grid.isWater(i,j)) {
					//System.out.println(i+","+j);
					precipitation.updateFlowVolume(new Point(i,j), 0, 0);
					//					UpdateFlowVolumeThread thread = precipitation.getThread(new Point(i,j));
					//					thread.run();
				}
			}
			if(printLoadingInfo||initializing) counter.increment();
		}
		if(printLoadingInfo) logger.logln("--(100%) Volumes loaded "+(System.currentTimeMillis()-time)+" ms");
		if(initializing) logger.logln("--(100%) Initialized "+(System.currentTimeMillis()-time)+" ms");
	}
	private void drawRivers(Graphics2D g2, int step, int displayScale, Color borderColor) {
		Point p1 = getGridPoint(-2*displayScale,this.getHeight()+4*displayScale);
		Point p2 = getGridPoint(this.getWidth()+2*displayScale,-2*displayScale);
		p1.translate(-100, 100);
		p2.translate(100, -100);
		int sum = (p2.x-p1.x);
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		if(printLoadingInfo) {
			dialog.createProgressUI("Loading towns: ");
			logger.log("Loading towns: ");
		}
		for(int i=p1.x;i<p2.x;i+=step) {
			for(int j=p2.y;j<p1.y;j+=step) {
				if(!grid.isWater(i,j)) {
					Point p = new Point(i,j);
					Point pr = precipitation.getRiver(p);
					float volume = precipitation.getFlowVolume(p);
					int width = (int) (Math.sqrt(volume)/15.0*displayScale);
					if(width>displayScale) width = displayScale;
					if(width>1) this.drawRiver(g2, getScreenPos(i,j), getScreenPos(pr), width);
				}
			}
			if(printLoadingInfo) counter.increment();
		}
		if(printLoadingInfo) logger.logln("Rivers drawn "+(System.currentTimeMillis()-time)+" ms");
	}

	private void drawRegion(Graphics2D g2, int displayScale) {
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(100, dialog.getProgressBar());
		counter.setLog(logger);
		if(printLoadingInfo) {
			dialog.createProgressUI("Loading region: ");
			logger.log("Loading region: ");
		}
		Point p = this.getSelectedGridPoint();
		Set<Point> region = null;
		if(HexData.NONE.equals(displayRegion)) {
			//do nothing
		}else if(HexData.MAGIC.equals(displayRegion)) {
			//region = magic.getRegion(p);
		}else if(HexData.THREAT.equals(displayRegion)) {
			region = threats.getRegion(p);
		}else if(HexData.PRECIPITATION.equals(displayRegion)) {
			//region = precipitation.getRegion(p);
		}else if(HexData.ECONOMY.equals(displayRegion)) {
			//region = economy.getRegion(p);
		}else if (HexData.BIOME.equals(displayRegion)||precipitation.isLake(p)) {
			region = biomes.getRegion(p);
		}else if(HexData.POPULATION.equals(displayRegion)) {
			region = population.getRegion(p);
		}else {
			//do nothing
		}
		if(region!=null) {
			counter.resetCounter(region.size());
			for(Point p1:region) {
				this.drawHex(g2, getScreenPos(p1),Color.WHITE,null,null,displayScale,Color.WHITE);
				if(printLoadingInfo) counter.increment();
			}
		}
		if(printLoadingInfo) logger.logln("Region drawn "+(System.currentTimeMillis()-time)+" ms");
	}

	private Color getColor1(int i,int j,HexData data) {
		float height = grid.getHeight(i, j);
		if (height<BiomeModel.SHALLOWS_HEIGHT) {
			return BiomeType.WATER.getColor();
		}else if (height<BiomeModel.WATER_HEIGHT) {
			return BiomeType.SHALLOWS.getColor();
		}else {
			return null;
		}
	}
	private Color getColor2(int i,int j,HexData data) {
		Point p = new Point(i,j);
		if(precipitation.isLake(p)&&!HexData.BIOME.equals(data)) {
			return BiomeType.LAKE.getColor();
		}else if(showCities&&population.isCity(p)) {
			return BiomeType.CITY.getColor();
		}else if(showCities&&population.isTown(p)) {
			return BiomeType.TOWN.getColor();
		}else if(HexData.MAGIC.equals(data)) {
			return magic.getColor(i, j);
		}else if (grid.isWater(i, j)) {
			return null;
		}else if(HexData.PRECIPITATION.equals(data)) {
			return precipitation.getColor(precipitation.getPrecipitation(i, j));
		}else if(HexData.BIOME.equals(data)) {
			return biomes.getColor(biomes.getBiome(i, j));
		}else if(HexData.POPULATION.equals(data)) {
			return population.getColor(i, j);
		}else if(HexData.ECONOMY.equals(data)) {
			return economy.getColor(i, j);
		}else if(HexData.THREAT.equals(data)) {
			Point threatSource = threats.getCenter(p);
			return threats.getThreatCreatureType(threatSource).getColor();
		}else {
			float height = grid.getHeight(i, j);
			return grid.getColor(height);
		}
	}

	public void drawHex(Graphics2D g2,int x,int y,Color borderColor,Color background,Color center,int scale,Color crosshatch) {
		int[] xs = new int[] {x,						x+scale,				x+scale,				x,						x-scale,				x-scale};
		int[] ys = new int[] {(int)(y+scale*2/sqrt3),	(int)(y+scale/sqrt3),	(int)(y-scale/sqrt3),	(int)(y-scale*2/sqrt3),	(int)(y-scale/sqrt3),	(int)(y+scale/sqrt3)};
		Polygon p = new Polygon(xs,ys,6);
		if(background!=null) {
			g2.setColor(background);
			g2.fillPolygon(p);
			g2.drawPolygon(p);
		}
		if(borderColor!=null) {
			g2.setColor(borderColor);
			g2.drawPolygon(p);
		}
		if(center!=null) {
			int[] xs2 = new int[] {x		,				x+scale/3,				x+scale/3,				x,						x-scale/3,				x-scale/3};
			int[] ys2 = new int[] {(int)(y+scale/3*2/sqrt3),	(int)(y+scale/3/sqrt3),	(int)(y-scale/3/sqrt3),	(int)(y-scale/3*2/sqrt3),	(int)(y-scale/3/sqrt3),	(int)(y+scale/3/sqrt3)};
			Polygon p2 = new Polygon(xs2,ys2,6);
			g2.setColor(center);
			g2.fillPolygon(p2);
		}
		if(crosshatch!=null) {
			Stroke defaultStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(2));
			Point a1 = new Point(x-scale,(int)(y-scale/6/sqrt3));
			Point a2 = new Point(x+scale*5/6,(int)(y-scale*7/6/sqrt3));
			g2.drawLine(a1.x, a1.y, a2.x, a2.y);
			Point b1 = new Point(x+scale,(int)(y+scale/6/sqrt3));
			Point b2 = new Point(x-scale*5/6,(int)(y+scale*7/6/sqrt3));
			g2.drawLine(b1.x, b1.y, b2.x, b2.y);
			g2.setStroke(defaultStroke);
		}
	}
	public void drawHex(Graphics2D g2, Point p,Color borderColor,Color background,Color center,int scale,Color crosshatch) {
		this.drawHex(g2, p.x, p.y,borderColor,background,center,scale,crosshatch);
	}
	public void drawRiver(Graphics2D g2, Point p1, Point p2, int width) {
		Stroke defaultStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(width));
		g2.setColor(BiomeType.RIVER.getColor());
		g2.drawLine(p1.x, p1.y, p2.x, p2.y);
		g2.setStroke(defaultStroke);

	}
	public void highlightTowns(Graphics2D g2, int step, int displayScale, Color borderColor) {
		Point p1 = getGridPoint(-2*displayScale,this.getHeight()+4*displayScale);
		Point p2 = getGridPoint(this.getWidth()+2*displayScale,-2*displayScale);
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		logger.log("Loading towns: ");
		int sum = (p2.x-p1.x);
		//int loadingFactor = (p1.y-p2.y);
		Counter counter = new Counter(sum, 5, logger);
		for(int i=p1.x;i<p2.x;i+=step) {
			for(int j=p2.y;j<p1.y;j+=step) {
				Point p = new Point(i,j);
				if(population.isTown(p)) {
					Color color1 = getColor1(i,j,displayData);
					Color color2 = getColor2(i,j,displayData);
					if(color1==null) {
						color1 = color2;
						color2 = null;
					}
					this.drawHex(g2, getScreenPos(i,j),borderColor,color1,color2,displayScale,null);
					if(borderColor!=null) {
						g2.setColor(borderColor);
						Character c = biomes.getBiome(i, j).getCh();
						if(c!=null) g2.drawString(c.toString(), getScreenPos(i,j).x-10, getScreenPos(i,j).y+6);
					}
				}
			}
			if(printLoadingInfo) {
				counter.increment();
			}
		}
		if(printLoadingInfo) logger.logln("Towns loaded "+(System.currentTimeMillis()-time)+" ms");
	}
	private void drawRoads(Graphics2D g2, int step, int displayScale, Color roadColor) {
		Point p1 = getGridPoint(-2*displayScale,this.getHeight()+4*displayScale);
		Point p2 = getGridPoint(this.getWidth()+2*displayScale,-2*displayScale);

		int sum = (p2.x-p1.x);
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		if(printLoadingInfo) {
			logger.log("Loading roads: ");
			dialog.createProgressUI("Loading roads: ");
		}
		for(int i=p1.x;i<p2.x;i+=step) {
			for(int j=p2.y;j<p1.y;j+=step) {
				Point p = new Point(i,j);
				if(population.isTown(p)) {
					economy.findTradeRoads(p);
				}
			}
			if(printLoadingInfo) {
				counter.increment();
			}
		}
		if(printLoadingInfo) logger.logln("Roads loaded "+(System.currentTimeMillis()-time)+" ms");
		counter.resetCounter(sum, 5);
		if(printLoadingInfo) {
			logger.log("Drawing roads: ");
			dialog.createProgressUI("Drawing roads: ");
		}
		Graph<Point> roads = economy.getRoads();
		for(int i=p1.x;i<p2.x;i+=step) {
			for(int j=p2.y;j<p1.y;j+=step) {
				Point p = new Point(i,j);
				Point pScreen = getScreenPos(p);
				for(Point near:roads.getAdjacencyList(p)) {
					int weight = roads.getEdgeWeight(p, near);
					Point nScreen = getScreenPos(near);
					Stroke defaultStroke = g2.getStroke();
					g2.setStroke(new BasicStroke(displayScale/(14-weight*6)+1));
					g2.setColor(roadColor);
					g2.drawLine(pScreen.x, pScreen.y, nScreen.x, nScreen.y);
					g2.setStroke(defaultStroke);
				}
			}
			if(printLoadingInfo) {
				counter.increment();
			}
		}
		if(printLoadingInfo) logger.logln("Roads drawn "+(System.currentTimeMillis()-time)+" ms");
	}
	public void repaintFrame() {
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		topFrame.repaint();
	}




	public class MouseAdapter implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			recenter(MapPanel.this.getGridPoint(e.getX(), e.getY()),true);
			record.setPos(MapPanel.this.getSelectedGridPoint());
			repaintFrame();
		}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {
			isDragging = true;
			dragOffsetX = e.getX();
			dragOffsetY = e.getY();
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			isDragging = false;
			//recenter();
			//repaint();
		}
	}
	public class MouseMotionAdapter implements MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			if (isDragging) {
				int x1 = center.x-e.getX()+dragOffsetX;
				int y1 = center.y-e.getY()+dragOffsetY;
				center = new Point(x1,y1);
				dragOffsetX = e.getX();
				dragOffsetY = e.getY();
				repaintFrame();
			}
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			Point gridPoint = MapPanel.this.getGridPoint(e.getX(),e.getY());
			if((MapPanel.this.mouseover==null)||!MapPanel.this.mouseover.equals(gridPoint)){
				mouseover = gridPoint;
				if(showDistance) {
					Point center = MapPanel.this.getSelectedGridPoint();
					int dx=mouseover.x-center.x;
					int dy=mouseover.y-center.y;
					double distance;
					if(dx*dy>=0) {
						distance = Math.abs(dx+dy);
					}else {
						dx=Math.abs(dx);
						dy=Math.abs(dy);
						distance = Math.max(dx, dy);
					}
					MapPanel.this.setDistance(distance);
					repaintFrame();
				}
			}
		}
	}
	public class MouseWheelAdapter implements MouseWheelListener {
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			Point c = getSelectedGridPoint();
			double effectiveScale = scale;
			double wheelRotation = -1*e.getWheelRotation();
			if(e.isShiftDown()) wheelRotation = wheelRotation*5;
			if(effectiveScale+wheelRotation<1) {
				int cardinality = (int) (-1*Math.log10(effectiveScale));
				int remainder = (int) (10*effectiveScale/Math.pow(0.1, cardinality));
				effectiveScale = 1-10*cardinality-(10-remainder);

				effectiveScale = effectiveScale + wheelRotation;
			}else if(effectiveScale+wheelRotation>MAX_SCALE) {
				effectiveScale = MAX_SCALE;
			}else {
				effectiveScale = effectiveScale + wheelRotation;
			}
			System.out.println(effectiveScale+" "+scale+" "+wheelRotation);
			if(effectiveScale<1) {
				int cardinality = (int) ((effectiveScale-10)/-10);
				int remainder = (int) (effectiveScale-1+cardinality*10);
				setScale(Math.pow(0.1, cardinality)*remainder);
			}else {
				setScale(effectiveScale);
			}
			recenter(c,false);
			repaintFrame();
		}

	}

	public void save() {
		if(record.getSaveLocation()==null) {
			saveAs();
		}else {
			record.save(frame.getAppData());
		}
	}

	public void saveAs() {
		File prevFile = record.getSaveLocation();
		File directory= AppData.getDirectory();
		String filename = record.getSeed()+".ser";
		if(prevFile!=null) {
			directory = prevFile.getAbsoluteFile().getParentFile();
			filename = prevFile.getName();
		}
		JFileChooser fileChooser = new JFileChooser(directory);
		fileChooser.setSelectedFile(new File(filename));
		if (fileChooser.showSaveDialog(this.getFrame()) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if(!file.getName().contains(".")) file = new File(file.getAbsolutePath()+".ser");
			record.setSaveLocation(file);
			record.setPos(this.getSelectedGridPoint());
			record.setScale(scale);
			if(record.save(frame.getAppData())) {
				frame.getAppData().removeRecent(prevFile);
				prevFile.delete();
			}

		}
	}
	public boolean hasPrevious() {
		return previousIndex>0;
	}
	public void previous() {
		if(!hasPrevious()) return;
		previousIndex--;
		recenter(previous.get(previousIndex),false);
		this.repaintFrame();
	}
	public boolean hasNext() {
		return previousIndex<previous.size()-1;
	}
	public void next() {
		if(!hasNext()) return;
		previousIndex++;
		recenter(previous.get(previousIndex),false);
		this.repaintFrame();
	}

	public MapFrame getFrame() {
		return frame;
	}

	public boolean isShowRivers() {
		return showRivers;
	}

	public void setShowRivers(boolean showRivers) {
		this.showRivers = showRivers;
		this.repaint();
	}

	public void setShowCities(boolean selected) {
		this.showCities = selected;
		this.repaint();
	}

	public boolean isPrintLoadingInfo() {
		return printLoadingInfo;
	}

	public void setPrintLoadingInfo(boolean printLoadingInfo) {
		this.printLoadingInfo = printLoadingInfo;
	}

	public void setShowDistance(boolean selected) {
		this.showDistance = selected;
		repaint();
	}
	public boolean isShowDistance() {
		return this.showDistance;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public PrecipitationModel getPrecipitation() {
		return this.precipitation;
	}

	public BiomeModel getBiomes() {
		return this.biomes;
	}

	public PopulationModel getPopulation() {
		return this.population;
	}

	public MagicModel getMagic() {
		return this.magic;
	}

	public LocationNameModel getNames() {
		return this.names;
	}

	public ThreatModel getThreats() {
		return this.threats;
	}

	public EncounterModel getEncounters() {
		return this.encounters;
	}

	public NPCModel getNpcs() {
		return npcs;
	}

	public SettlementModel getSettlements() {
		return settlements;
	}

	public LocationModel getPois() {
		return pois;
	}

	public EconomicModel getEconomy() {
		return economy;
	}

	public DungeonModel getDungeon() {
		return dungeons;
	}

	public SaveRecord getRecord() {
		return record;
	}
	
	@Override
    public void repaint() {
		super.repaint();
	}

}
