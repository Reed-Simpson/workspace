package view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingWorker;

import controllers.DataController;
import data.AStarGraph;
import data.HexData;
import data.altitude.AltitudeModel;
import data.biome.BiomeModel;
import data.biome.BiomeType;
import data.precipitation.PrecipitationModel;
import io.AppData;
import io.SaveRecord;
import util.Counter;
import util.Pair;

public class MapPanel  extends JPanel{
	public static final int WIGGLERADIUS = 100;
	private static final int WIDEVIEW = 5;
	private static final long serialVersionUID = 6922738675563657970L;
	private static final Color LINE_COLOR = null;
	private static final double sqrt3 = Math.sqrt(3.0);
	public static final int MIN_SCALE = 1;
	private static final int MAX_SCALE = 500;
	private static final int HIDE_BORDERS_SCALE = 9;
	private static final int LOG_THRESHOLD = 200;
	private static final float RIVERRENDERGRANULARITY = 0.4f;
	private MapFrame frame;
	private ProgressBarDialog dialog;
	private Point center; //center represents the pixel offset from 0,0
	private Point mouseover;
	private double scale; // Scale represents the pixel distance from the center of one hex to an adjacent one
	private boolean isDragging = false;
	private int dragOffsetX, dragOffsetY;
	private HexData displayData;
	private SaveRecord record;
	private boolean showRivers;
	private boolean showCities;
	private AtomicBoolean printLoadingInfo;
	private boolean initializing;
	private boolean showDistance;
	private double distance;
	private long time;
	private HexData displayRegion;
	private ArrayList<Point> previous;
	int previousIndex;
	private DataController controller;
	private HashMap<Point,Pair<Color,Color>> colorCache;
	private boolean mouseoverHold;
	private HashMap<Point,List<Icon>> iconCache;
	private ConcurrentHashMap<Point,BasicSpline> splineCache;
	private boolean showIcons;
	private boolean printMode;

	public MapPanel(MapFrame frame, SaveRecord record) {
		this.frame = frame;
		this.showCities=true;
		this.showIcons=true;
		this.printMode = false;
		colorCache = new HashMap<Point,Pair<Color,Color>>();
		iconCache = new HashMap<Point,List<Icon>>();
		splineCache = new ConcurrentHashMap<Point,BasicSpline>();
		this.addMouseListener(new MouseAdapter());
		this.addMouseMotionListener(new MouseMotionAdapter());
		this.addMouseWheelListener(new MouseWheelAdapter());
		this.setDisplayData(HexData.ALTITUDE);
		this.setPreferredSize(new Dimension(800, 800));
		this.printLoadingInfo = new AtomicBoolean(true);
		previousIndex = 0;
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				preprocessThenRepaint();
			}
		});
		this.reloadFromSaveRecord(record);

	}

	public void reloadFromSaveRecord(SaveRecord record) {
		this.record = record;
		this.controller = new DataController(record);
		this.scale = record.getScale();
		this.previous=new ArrayList<Point>();
		this.dialog = new ProgressBarDialog(frame);
		this.recenter(record.getPos(),false);
	}
	public void initialize() {
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		if(!record.isInitialized()) {
			initializing = true;
			record.initialize(controller.getGrid(),controller.getPopulation());

			SwingWorker<Void, Integer> worker = new SwingWorker<Void,Integer>(){
				@Override
				protected Void doInBackground() throws Exception {
					try {
						calculateRivers();
					}catch(Exception e) {
						e.printStackTrace();
					}
					return null;
				}

			};
			worker.execute();
		}else {
			postinitialize(record);
			frame.setCursor(Cursor.getDefaultCursor());
		}
	}

	private void postinitialize(SaveRecord record) {
		recenter(record.getPos(),true);
		mouseover = getMiddleGridPoint();
		HashSet<Point> visible = controller.getGrid().getLineOfSight(record.getHero());
		record.addAllExplored(visible);
		record.setHasUnsavedData(false);
		this.preprocessThenRepaint();
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
		if(showDistance) return getMouseoverGridPoint();
		return getGridPoint(getMiddleOfScreen());
	}
	public Point getMiddleGridPoint() {
		return getGridPoint(getMiddleOfScreen());
	}
	public Point getMouseoverGridPoint() {
		return mouseover;
	}
	public Point getMiddleOfScreen() {
		return new Point(this.getWidth()/2, (int) (this.getHeight()/2));
	}
	public void recenter() {
		recenter(getMiddleGridPoint(),true);
	}
	public void recenter(Point p,boolean updatePrevious) {
		record.addExplored(p);
		if(updatePrevious&&previousIndex>-1) {
			for(int i=previous.size()-1;i>previousIndex;i--) {
				previous.remove(i);
			}
		}
		Point targetHexPos = getAbsolutePos(p);
		Point middle = getMiddleOfScreen();
		center= new Point(targetHexPos.x-middle.x, targetHexPos.y-middle.y);
		if(updatePrevious) {
			previous.add(getMiddleGridPoint());
			previousIndex=previous.size()-1;
		}
		record.setPos(MapPanel.this.getMiddleGridPoint());
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
		if(this.displayData!=displayData) {
			this.displayData = displayData;
			colorCache = new HashMap<Point,Pair<Color,Color>>();
			//			iconCache = new HashMap<Point,List<Icon>>();
		}
	}

	public void setDisplayRegion(HexData selectedItem) {
		this.displayRegion = selectedItem;
	}

	public int getStep() {
		int step = 1;
		if(scale<MIN_SCALE) {
			step = (int) (MIN_SCALE/scale);
		}
		return step;
	}

	@Override
	public void paintComponent(Graphics g){
		time = System.currentTimeMillis();
		Graphics2D g2 = (Graphics2D) g;
		int step = getStep();
		int displayScale = getDisplayScale();
		boolean wideview = scale<WIDEVIEW;
		Color borderColor = LINE_COLOR;
		if(scale<HIDE_BORDERS_SCALE||!showIcons) {
			borderColor = null;
		}
		drawHexes(g2, step, displayScale, borderColor);
		if(!wideview) {
			drawRivers(g2, step, displayScale, borderColor);
		}
		drawOceans(g2, step, displayScale, borderColor);
		drawHighlights(g2, step, displayScale);
		if(HexData.ECONOMY.equals(displayData)&&!wideview) {
			drawRoads(g2,step,displayScale,Color.RED);
			highlightTowns(g2,step,displayScale,Color.RED);
		}
		drawSymbols(g2, step, displayScale, borderColor);
		drawRegion(g2, displayScale);
		if(HexData.EXPLORATION.equals(displayData)) drawVoid(g2, displayScale);
		if(!this.printMode) drawCenterHex(g2, displayScale);
		if(!isDragging)drawDistanceMarker(g2, step, displayScale, Color.RED);
		drawLegend(g2, step, displayScale);

		dialog.removeProgressUI();
	}

	private int getDisplayScale() {
		int displayScale = (int)scale;
		if(scale<MIN_SCALE) {
			displayScale = MIN_SCALE;
		}
		return displayScale;
	}

	public void preprocessThenRepaint() {
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		SwingWorker<Void, Integer> worker = new MapPanelSwingWorker();
		worker.execute();
	}

	private synchronized void calculateHexColors() {
		Rectangle r = getRenderArea();
		int sum = (r.width-r.x);
		int step = getStep();
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		logger.log("Loading colors: ");
		if(printLoadingInfo.get()) {
			dialog.createProgressUI("Loading view data: ");
		}
		HashMap<Point,Pair<Color,Color>> newCache = new HashMap<Point,Pair<Color,Color>>();
		HashMap<Point,List<Icon>> newIconCache = new HashMap<Point,List<Icon>>();
		for(int i=r.x;i<r.width;i+=step) {
			for(int j=r.y;j<r.height;j+=step) {
				Point p = new Point(i,j);
				if(!controller.getGrid().isWater(p)) {
					Pair<Color,Color> cached = colorCache.get(p);
					if(cached==null) {
						Color color1 = getColor1(i,j,displayData);
						Color color2 = getColor2(i,j,displayData);
						if(color1==null) {
							color1 = color2;
							color2 = null;
						}else if(color1==BiomeType.TOWN.getColor()) {
							color1 = color2;
							color2 = BiomeType.TOWN.getColor();
						}
						cached = new Pair<Color, Color>(color1,color2);
						newCache.put(p, new Pair<Color,Color>(color1,color2));
					}
					newCache.put(p, cached);
				}

				List<Icon> icons = iconCache.get(p);
				if(icons==null) icons = getIcons(p);
				newIconCache.put(p, icons);
			}
			counter.increment();
		}
		colorCache = newCache;
		iconCache = newIconCache;
		dialog.removeProgressUI();
		logger.logln("Colors loaded "+(System.currentTimeMillis()-time)+" ms");
	}

	private Rectangle getRenderArea() {
		Point p1 = getGridPoint(-20-(int)scale,this.getHeight()+60+(int)scale);
		Point p2 = getGridPoint(this.getWidth()+20+(int)scale,-20-(int)scale);
		Rectangle r = new Rectangle(p1.x,p2.y,p2.x,p1.y);
		return r;
	}

	private List<Icon> getIcons(Point p) {
		BiomeType biome = controller.getBiomes().getBiome(p);
		return Icon.getIcons(biome);
	}


	private void drawHexes(Graphics2D g2, int step, int displayScale, Color borderColor) {
		Rectangle r = getRenderArea();
		int sum = (r.width-r.x);
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		logger.log("Drawing hexes: ");
		if(printLoadingInfo.get()) {
			dialog.createProgressUI("Drawing hexes: ");
		}
		float height = AltitudeModel.altitudeTransformation(controller.getPrecipitation().getLakeAltitude(getMiddleGridPoint()));
		for(int i=r.x;i<r.width;i+=step) {
			for(int j=r.y;j<r.height;j+=step) {
				Point p = new Point(i,j);
				BiomeType type = controller.getGrid().getAltitudeBiome(p);
				if(!BiomeType.WATER.equals(type)&&!BiomeType.SHALLOWS.equals(type)) {
					Pair<Color,Color> cached = colorCache.get(p);
					Color color1 = type.getColor();
					Color color2 = null;
					if(cached!=null) {
						color1 = cached.key1;
						color2 = cached.key2;
					}
					this.drawHex(g2, getScreenPos(i,j),borderColor,color1,color2,displayScale,null);
					if(cached==null) {
						Point offset = new Point(-35,35);
						g2.setFont(g2.getFont().deriveFont((float) (displayScale)));
						g2.setColor(Color.RED);
						g2.setComposite(AlphaComposite.SrcOver.derive(0.5f));
						g2.drawString("\u23F3", getScreenPos(i,j).x+(int)((offset.x*scale)/100), getScreenPos(i,j).y+(int)((offset.y*scale)/100));
						g2.setComposite(AlphaComposite.SrcOver);
					}
					drawSymbol(g2, height, p,false);
				}
			}
			counter.increment();
		}
		logger.logln("Hexes drawn "+(System.currentTimeMillis()-time)+" ms");
	}

	private void drawOceans(Graphics2D g2, int step, int displayScale, Color borderColor) {
		Rectangle r = getRenderArea();
		int sum = (r.width-r.x);
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		logger.log("Drawing oceans: ");
		if(printLoadingInfo.get()) {
			dialog.createProgressUI("Drawing oceans: ");
		}
		for(int i=r.x;i<r.width;i+=step) {
			for(int j=r.y;j<r.height;j+=step) {
				if(controller.getGrid().isWater(i,j)) {
					Color color1 = getColor1(i,j,displayData);
					Color color2 = getColor2(i,j,displayData);
					if(color1==null) {
						color1 = color2;
						color2 = null;
					}
					this.drawHex(g2, getScreenPos(i,j),borderColor,color1,color2,displayScale,null);
				}
			}
			counter.increment();
		}
		logger.logln("Oceans loaded "+(System.currentTimeMillis()-time)+" ms");
	}

	private void drawHighlights(Graphics2D g2, int step, int displayScale) {
		int strokeSize = displayScale/4;
		Point p1 = getGridPoint(-2*displayScale,this.getHeight()+4*displayScale);
		Point p2 = getGridPoint(this.getWidth()+2*displayScale,-2*displayScale);
		Stroke defaultStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(Math.max(strokeSize,1)));
		for(int i=p1.x;i<p2.x;i+=1) {
			for(int j=p2.y;j<p1.y;j+=1) {
				Point p = new Point(i,j);
				Color borderColor = record.getHighlight(p);
				if(borderColor!=null) this.drawHex(g2, getScreenPos(i,j),borderColor,null,null,Math.max((int)scale,1),null);
			}
		}
		g2.setStroke(defaultStroke);
	}

	private void drawCenterHex(Graphics2D g2, int displayScale) {
		int strokeSize = displayScale/7;
		Stroke defaultStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(Math.max(strokeSize,1)));
		Point p = this.getMiddleGridPoint();
		this.drawHex(g2, getScreenPos(p),Color.CYAN,null,null,Math.max((int)scale,1),null);
		g2.setStroke(defaultStroke);
	}

	private void drawSymbols(Graphics2D g2, int step, int displayScale, Color borderColor) {
		Rectangle r = getRenderArea();
		int sum = (r.width-r.x);
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		logger.log("Drawing symbols: ");
		if(printLoadingInfo.get()) {
			dialog.createProgressUI("Drawing symbols: ");
		}
		float height = AltitudeModel.altitudeTransformation(controller.getPrecipitation().getLakeAltitude(getMiddleGridPoint()));
		for(int i=r.x;i<r.width;i+=step) {
			for(int j=r.y;j<r.height;j+=step) {
				Point p = new Point(i,j);
				drawSymbol(g2, height, p,true);
			}
			counter.increment();
		}
		g2.setComposite(AlphaComposite.SrcOver);
		logger.logln("Symbols drawn "+(System.currentTimeMillis()-time)+" ms");
	}

	private void drawSymbol(Graphics2D g2, float height, Point p,boolean top) {
		Pair<Color, Color> base = colorCache.get(p);
		List<Icon> icons = iconCache.get(p);
		if(icons!=null && ((scale>8&&showIcons)||(scale>2&&BiomeType.CITY.getCh().equals(icons.get(0).getCh())))) {
			if(HexData.ALTITUDE.equals(displayData)) {
				g2.setColor(Color.black);
				float height_x = AltitudeModel.altitudeTransformation(controller.getPrecipitation().getLakeAltitude(p));
				int dheight = (int)(height_x-height);
				g2.setFont(g2.getFont().deriveFont((float) (scale/2)));
				if(dheight<0) g2.setColor(Color.red);
				g2.drawString(String.valueOf(dheight), (int) (getScreenPos(p).x-scale/2) ,getScreenPos(p).y );
			}else {
				for(Icon icon:icons) {
					Character ch = icon.getCh();
					if(ch!=null && icon.top == top) {
						if(base!=null) {
							Point offset = icon.offset;
							double cScale = icon.getScale();
							g2.setColor(base.key1);
							g2.setFont(g2.getFont().deriveFont((float) (scale*cScale)));
							g2.drawString(ch.toString(), getScreenPos(p).x+(int)((offset.x*scale)/100), getScreenPos(p).y+(int)((offset.y*scale)/100));
						}
						g2.setComposite(AlphaComposite.SrcOver.derive(icon.getOpacity()));
						Point offset = icon.offset;
						Color c = icon.getC();
						double cScale = icon.getScale();
						g2.setFont(g2.getFont().deriveFont((float) (scale*cScale)));
						if(c==null) c=LINE_COLOR;
						g2.setColor(c);
						g2.drawString(ch.toString(), getScreenPos(p).x+(int)((offset.x*scale)/100), getScreenPos(p).y+(int)((offset.y*scale)/100));
						g2.setComposite(AlphaComposite.SrcOver);
					}
				}
			}
		}
	}

	private void drawDistanceMarker(Graphics2D g2, int step, int displayScale, Color c) {
		if(showDistance&&mouseover!=null) {
			time = System.currentTimeMillis();
			Stroke defaultStroke = g2.getStroke();
			g2.setColor(c);
			g2.setStroke(new BasicStroke(1+displayScale/2));
			Point center = this.getMiddleGridPoint();
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

	private void drawLegend(Graphics2D g2, int step, int displayScale) {
		Stroke defaultStroke = g2.getStroke();
		if(this.printMode) {
			Point c = getMiddleOfScreen();
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.BLACK);
			g2.drawRect(c.x-550, c.y-425, 1100, 850);

		} else {
			int corneroffset = 50;
			int inset = 20;
			int lineDist = (int) (1000/scale);//(int) ((width-2*inset)*6.0/(scale*2));
			int width = 1000/3+2*inset;
			int height = 50;
			g2.setColor(Color.WHITE);
			g2.fillRect(corneroffset, this.getHeight()-corneroffset-height, width, height);
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.RED);
			g2.drawLine(corneroffset+inset, this.getHeight()-corneroffset-height/3, corneroffset+width-inset, this.getHeight()-corneroffset-height/3);
			g2.drawLine(corneroffset+inset, this.getHeight()-corneroffset-height/3, corneroffset+inset, this.getHeight()-corneroffset-height/2);
			g2.drawLine(corneroffset+width-inset, this.getHeight()-corneroffset-height/3, corneroffset+width-inset, this.getHeight()-corneroffset-height/2);
			g2.setFont(g2.getFont().deriveFont(12f));
			String str = lineDist+" miles";
			int stringwidth = g2.getFontMetrics().stringWidth(str);
			g2.drawString(str, corneroffset+width-inset/2-stringwidth, this.getHeight()-corneroffset-height*2/3);
			g2.setColor(Color.BLACK);
			g2.drawRect(corneroffset, this.getHeight()-corneroffset-height, width, height);
		}
		g2.setStroke(defaultStroke);
	}

	private synchronized void calculateRivers() {
		PrecipitationModel precipitation = controller.getPrecipitation();
		Rectangle r = getRenderArea();
		r.x = r.x-100;
		r.y = r.y-100;
		r.width = r.width + 200;
		r.height = r.height + 200;
		time = System.currentTimeMillis();
		int sum = (r.width-r.x);
		int loadingFactor = (r.height-r.y);
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		if(initializing) {
			dialog.createProgressUI("Initializing rivers: ");
			logger.log("Initializing rivers "+(sum*loadingFactor)+": ");
		}else {
			if(printLoadingInfo.get()) {
				dialog.createProgressUI("Loading rivers: ");
			}
			logger.log("Loading rivers "+(sum*loadingFactor)+": ");
		}
		for(int i=r.x;i<r.width;i+=1) {
			for(int j=r.y;j<r.height;j+=1) {
				if(!controller.getGrid().isWater(i,j)) {
					precipitation.getFlow(new Point(i,j));
				}
			}
			counter.increment();
		}
		if(initializing) {
			dialog.removeProgressUI();
			dialog.createProgressUI("Initializing lakes: ");
			counter.resetCounter();
			logger.log("Initializing lakes "+(sum*loadingFactor)+": ");
		}else {
			if(printLoadingInfo.get()) {
				dialog.createProgressUI("Loading lakes: ");
			}
			logger.logln("--(100%) Rivers loaded "+(System.currentTimeMillis()-time)+" ms");
			time = System.currentTimeMillis();
			counter.resetCounter();
			logger.log("Loading lakes "+(sum*loadingFactor)+": ");
		}
		for(int i=r.x;i<r.width;i+=1) {
			for(int j=r.y;j<r.height;j+=1) {
				Point p = new Point(i,j);
				if(!controller.getGrid().isWater(i,j)&&p.equals(precipitation.getFlow(p))) {
					precipitation.time = System.currentTimeMillis();
					precipitation.generateLake(p);
				}
			}
			counter.increment();
		}

		if(!initializing) logger.logln("--(100%) Lakes loaded "+(System.currentTimeMillis()-time)+" ms");
		counter.resetCounter();
		if(initializing) {
			dialog.removeProgressUI();
			dialog.createProgressUI("Initializing river volume: ");
			logger.log("Initializing river volume "+(sum*loadingFactor)+": ");
		}else {
			if(printLoadingInfo.get()) {
				dialog.createProgressUI("Loading river volume: ");
			}
			time = System.currentTimeMillis();
			logger.log("Loading river volume "+(sum*loadingFactor)+": ");
		}
		precipitation.newVolumeCache();
		for(int i=r.x;i<r.width;i+=1) {
			for(int j=r.y;j<r.height;j+=1) {
				if(!controller.getGrid().isWater(i,j)) {
					Point p = new Point(i,j);
					precipitation.updateFlowVolume(p);
				}
			}
			counter.increment();
		}
		precipitation.setNewVolumeCache();
		if(initializing) logger.logln("--(100%) Volumes Initialized "+(System.currentTimeMillis()-time)+" ms");
		else logger.logln("--(100%) Volumes loaded "+(System.currentTimeMillis()-time)+" ms");
		counter.resetCounter();
		if(initializing) {
			dialog.removeProgressUI();
			dialog.createProgressUI("Initializing river splines: ");
			logger.log("Initializing river splines "+(sum*loadingFactor)+": ");
		}else {
			if(printLoadingInfo.get()) {
				dialog.createProgressUI("Loading river splines: ");
			}
			time = System.currentTimeMillis();
			logger.log("Loading river splines "+(sum*loadingFactor)+": ");
		}
		ConcurrentHashMap<Point,BasicSpline> newcache = new ConcurrentHashMap<Point,BasicSpline>();
		for(int i=r.x;i<r.width;i+=1) {
			for(int j=r.y;j<r.height;j+=1) {
				if(!controller.getGrid().isWater(i,j)) {
					Point p = new Point(i,j);
					BasicSpline spline = splineCache.get(p);
					if(spline==null) spline = getRiverSpline(p);
					newcache.put(p, spline);
				}
			}
			counter.increment();
		}
		splineCache = newcache;
		if(initializing) logger.logln("--(100%) Initialized "+(System.currentTimeMillis()-time)+" ms");
		else logger.logln("--(100%) Splines loaded "+(System.currentTimeMillis()-time)+" ms");
		dialog.removeProgressUI();
		if(initializing) {
			frame.setCursor(Cursor.getDefaultCursor());
			record.initialize(controller.getGrid(),controller.getPopulation());
			initializing = false;
			postinitialize(record);
		}
	}
	private void drawRivers(Graphics2D g2, int step, int displayScale, Color borderColor) {
		int sum = splineCache.size();
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		if(printLoadingInfo.get()) {
			dialog.createProgressUI("Drawing rivers: ");
		}
		logger.log("Drawing rivers: ");
		Stroke defaultStroke = g2.getStroke();
		ConcurrentHashMap<Point,BasicSpline> cacheref = splineCache;
		try {
			for(Entry<Point,BasicSpline> e:cacheref.entrySet()) {
				drawCurvedRiver(g2, displayScale, e.getKey(),e.getValue());
				counter.increment();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		g2.setStroke(defaultStroke);
		dialog.removeProgressUI();
		logger.logln("Rivers drawn "+(System.currentTimeMillis()-time)+" ms");
	}

	private void drawCurvedRiver(Graphics2D g2, int displayScale, Point p0,BasicSpline spline) {
		Float volume = controller.getPrecipitation().getFlowVolume(p0);
		if(spline!=null && volume!=null) {
			float width = (float) (Math.sqrt(volume)/15.0f*displayScale);
			if(width>displayScale) width = displayScale;
			if(showRivers||width>(1+scale/20)) {
				drawSpline(g2, spline, width,BiomeType.RIVER.getColor(),p0);
			}
		}
	}

	private void drawSpline(Graphics2D g2, BasicSpline spline, float width,Color color,Point p0) {
		Point p1 = controller.getPrecipitation().getRiver(p0);
		double distance = calcDistance(p0, p1);
		g2.setColor(color);
		g2.setStroke(new BasicStroke(Math.max(width-1,0)));
		Point anchor = getScreenPos(p0);
		Point pointBefore = null;
		float step = (float) (1f/3/scale)/RIVERRENDERGRANULARITY;
		for(float f = 0; f<=1f/3+step/distance-width/12/scale/distance; f+=step/distance) {
			Point p = spline.getPoint(f);
			Point pnt = new Point((int)(anchor.x+p.x*scale/WIGGLERADIUS), (int)(anchor.y+p.y*scale/WIGGLERADIUS));
			g2.fillOval(pnt.x-(int)width/2, pnt.y-(int)width/2, (int)width, (int)width);
			if(pointBefore != null && width < 30*step*scale) {
				g2.drawLine(pnt.x, pnt.y, pointBefore.x, pointBefore.y);
			}
			pointBefore = pnt;
		}
	}

	private BasicSpline getRiverSpline(Point p0) {
		Point p1 = controller.getPrecipitation().getRiver(p0);
		if(p1!=null) {
			Point p2 = controller.getPrecipitation().getFlow(p1);
			Point p3 = controller.getPrecipitation().getFlow(p2);
			BasicSpline spline = new BasicSpline();
			spline.addPoint(wiggle(p0,p0));
			spline.addPoint(wiggle(p1,p0));
			spline.addPoint(wiggle(p2,p0));
			spline.addPoint(wiggle(p3,p0));
			spline.calcSpline();
			return spline;
		}else return null;
	}

	private Point wiggle(Point p,Point relativeTo) {
		Point p_ = getScreenPos(p);
		Point relative_ = getScreenPos(relativeTo);
		Point hexCenter = new Point((int)((p_.x-relative_.x)*WIGGLERADIUS/scale),(int)((p_.y-relative_.y)*WIGGLERADIUS/scale));
		if(controller.getGrid().isWater(p)) return hexCenter;
		else {
			Point wigglefactor = controller.getPrecipitation().getWiggleFactor(p);
			return new Point((int)(hexCenter.x+wigglefactor.x),(int)(hexCenter.y+wigglefactor.y));
		}
	}

	private void drawRegion(Graphics2D g2, int displayScale) {
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(100, dialog.getProgressBar());
		counter.setLog(logger);
		if(printLoadingInfo.get()) {
			dialog.createProgressUI("Drawing region: ");
		}
		logger.log("Drawing region: ");
		Point p = this.getSelectedGridPoint();
		Set<Point> region = null;
		if(HexData.NONE.equals(displayRegion)) {
			//do nothing
		}else if(HexData.MAGIC.equals(displayRegion)) {
			//region = magic.getRegion(p);
		}else if(HexData.THREAT.equals(displayRegion)) {
			region = controller.getThreats().getRegion(p);
		}else if(HexData.PRECIPITATION.equals(displayRegion)) {
			//region = precipitation.getRegion(p);
		}else if(HexData.ECONOMY.equals(displayRegion)) {
			//region = economy.getRegion(p);
		}else if (HexData.BIOME.equals(displayRegion)||controller.getPrecipitation().isLake(p)) {
			region = controller.getBiomes().getRegion(p);
		}else if(HexData.POPULATION.equals(displayRegion)) {
			region = controller.getPopulation().getRegion(p);
		}else {
			//do nothing
		}
		if(region!=null) {
			counter.resetCounter(region.size());
			for(Point p1:region) {
				this.drawHex(g2, getScreenPos(p1),Color.WHITE,null,null,displayScale,Color.WHITE);
				counter.increment();
			}
		}
		logger.logln("Region drawn "+(System.currentTimeMillis()-time)+" ms");
	}

	private void drawVoid(Graphics2D g2, int displayScale) {
		Point p1 = getGridPoint(-40,this.getHeight()+80);
		Point p2 = getGridPoint(this.getWidth()+40,-40);
		HashSet<Point> visible = controller.getGrid().getLineOfSight(record.getHero());
		for(int i=p1.x;i<p2.x;i+=1) {
			for(int j=p2.y;j<p1.y;j+=1) {
				Point p = new Point(i,j);
				if(!record.isExplored(p)) {
					this.drawHex(g2, getScreenPos(i,j),null,BiomeType.VOID.getColor(),null,displayScale,null);
				}else if(!visible.contains(p)) {
					g2.setComposite(AlphaComposite.SrcOver.derive(0.5f));
					this.drawHex(g2, getScreenPos(i,j),null,BiomeType.VOID.getColor(),null,displayScale,null);
					g2.setComposite(AlphaComposite.SrcOver);
				}
				if(p.equals(record.getHero())) {
					drawPawn(g2,p,Color.red,displayScale);
				}
			}
		}
	}

	private void drawPawn(Graphics2D g2, Point p, Color color, int displayScale) {
		Point p_ = getScreenPos(p);
		int diameter = displayScale*2/3;
		int[] xs1 = new int[] {p_.x,						p_.x+displayScale/2+1,				p_.x-displayScale/2-1};
		int[] ys1 = new int[] {(int)(p_.y-displayScale/sqrt3-1),	(int)(p_.y+displayScale*3/4/sqrt3+1),	(int)(p_.y+displayScale*3/4/sqrt3+1)};
		Polygon t1 = new Polygon(xs1,ys1,3);
		g2.setColor(LINE_COLOR);
		g2.fill(t1);
		g2.fillOval(p_.x-diameter/2-1, (int)(p_.y-displayScale/sqrt3)-diameter/2-1, diameter+2, diameter+2);

		int[] xs = new int[] {p_.x,						p_.x+displayScale/2,				p_.x-displayScale/2};
		int[] ys = new int[] {(int)(p_.y-displayScale/sqrt3),	(int)(p_.y+displayScale*3/4/sqrt3),	(int)(p_.y+displayScale*3/4/sqrt3)};
		Polygon t = new Polygon(xs,ys,3);
		g2.setColor(color);
		g2.fill(t);
		g2.fillOval(p_.x-diameter/2, (int)(p_.y-displayScale/sqrt3)-diameter/2, diameter, diameter);
	}

	private Color getColor1(int i,int j,HexData data) {
		float height = controller.getGrid().getHeight(i, j);
		BiomeType settlementType;
		Point p = new Point(i,j);
		if (height<BiomeModel.SHALLOWS_HEIGHT) {
			return BiomeType.WATER.getColor();
		}else if (height<BiomeModel.WATER_HEIGHT) {
			return BiomeType.SHALLOWS.getColor();
		}else if (!HexData.BIOME.equals(data)&&!HexData.EXPLORATION.equals(data)&&controller.getPrecipitation().isLake(p)) {
			return BiomeType.LAKE.getColor();
		}else if(showCities && (settlementType = controller.getPopulation().getSettlementType(p))!=null) {
			return settlementType.getColor();
		}
		return null;
	}
	private Color getColor2(int i,int j,HexData data) {
		Point p = new Point(i,j);
		if(HexData.MAGIC.equals(data)) {
			return controller.getMagic().getColor(i, j);
		}else if(controller.getPrecipitation().isLake(p)&&!HexData.BIOME.equals(data)&&!HexData.EXPLORATION.equals(data)) {
			return null;
		}else if (controller.getGrid().isWater(i, j)) {
			return null;
		}else if(HexData.PRECIPITATION.equals(data)) {
			return controller.getPrecipitation().getColor(controller.getPrecipitation().getPrecipitation(i, j));
		}else if(HexData.BIOME.equals(data) || HexData.EXPLORATION.equals(data)) {
			return controller.getBiomes().getBiome(i, j).getColor();
		}else if(HexData.POPULATION.equals(data)) {
			return controller.getPopulation().getColor(i, j);
		}else if(HexData.ECONOMY.equals(data)) {
			return controller.getEconomy().getColor(i, j);
		}else if(HexData.THREAT.equals(data)) {
			Point threatSource = controller.getThreats().getCenter(p);
			return controller.getThreats().getThreatCreatureType(threatSource).getColor();
		}else {
			float height = controller.getGrid().getHeight(i, j);
			return controller.getGrid().getColor(height);
		}
	}

	public void drawHex(Graphics2D g2,int x,int y,Color borderColor,Color background,Color center,int scale,Color crosshatch) {
		int[] xs = new int[] {x,						x+scale+1,				x+scale,				x,						x-scale,				x-scale};
		int[] ys = new int[] {(int)(y+scale*2/sqrt3),	(int)(y+scale/sqrt3+1),	(int)(y-scale/sqrt3),	(int)(y-scale*2/sqrt3),	(int)(y-scale/sqrt3),	(int)(y+scale/sqrt3)};
		Polygon p = new Polygon(xs,ys,6);
		if(background!=null) {
			g2.setColor(background);
			g2.fillPolygon(p);
			g2.drawPolygon(p);
		}
		if(borderColor!=null && background!=null) {
			g2.setColor(borderColor);
			g2.drawPolygon(p);
		}
		if(center!=null) {
			int[] xs2 = new int[] {x		,				x+scale/3,				x+scale/3,				x,						x-scale/3,				x-scale/3};
			int[] ys2 = new int[] {(int)(y+scale/3*2/sqrt3),	(int)(y+scale/3/sqrt3),	(int)(y-scale/3/sqrt3),	(int)(y-scale/3*2/sqrt3),	(int)(y-scale/3/sqrt3),	(int)(y+scale/3/sqrt3)};
			Polygon p2 = new Polygon(xs2,ys2,6);
			g2.setColor(center);
			g2.fillPolygon(p2);
			if(borderColor!=null && background==null) {
				g2.setColor(borderColor);
				g2.drawPolygon(p2);
			}
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
	public void highlightTowns(Graphics2D g2, int step, int displayScale, Color borderColor) {
		Rectangle r = getRenderArea();
		int sum = (r.width-r.x);
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		logger.log("Drawing towns: ");
		//int loadingFactor = (p1.y-p2.y);
		Counter counter = new Counter(sum, 5, logger);
		for(int i=r.x;i<r.width;i+=step) {
			for(int j=r.y;j<r.height;j+=step) {
				Point p = new Point(i,j);
				if(controller.getPopulation().isTown(p)) {
					Color color1 = getColor1(i,j,displayData);
					Color color2 = getColor2(i,j,displayData);
					if(color1==null) {
						color1 = color2;
						color2 = null;
					}else if(color1==BiomeType.TOWN.getColor()) {
						color1 = null;
						color2 = BiomeType.TOWN.getColor();
					}
					this.drawHex(g2, getScreenPos(i,j),borderColor,color1,color2,displayScale,null);
				}
			}
			counter.increment();
		}
		logger.logln("Towns drawn "+(System.currentTimeMillis()-time)+" ms");
	}
	private synchronized void loadRoads() {
		Rectangle r = getRenderArea();
		int sum = (r.width-r.x);
		int step = getStep();

		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		if(printLoadingInfo.get()) {
			dialog.createProgressUI("Loading roads: ");
		}
		logger.log("Loading roads: ");
		for(int i=r.x;i<r.width;i+=step) {
			for(int j=r.y;j<r.height;j+=step) {
				Point p = new Point(i,j);
				if(controller.getPopulation().isTown(p)) {
					controller.getEconomy().findTradeRoads(p);
				}
			}
			counter.increment();
		}
		dialog.removeProgressUI();
		logger.logln("Roads loaded "+(System.currentTimeMillis()-time)+" ms");
	}

	private void drawRoads(Graphics2D g2, int step, int displayScale, Color roadColor) {
		Rectangle r = getRenderArea();
		int sum = (r.width-r.x);
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		if(printLoadingInfo.get()) {
			dialog.createProgressUI("Drawing roads: ");
		}
		logger.log("Drawing roads: ");
		AStarGraph roads = controller.getEconomy().getRoads();
		for(int i=r.x;i<r.width;i+=step) {
			for(int j=r.y;j<r.height;j+=step) {
				Point p = new Point(i,j);
				Point p_ = getScreenPos(p);
				Point pWiggle = wiggle(p,p);
				Point pStart = new Point((int)(p_.x+pWiggle.x*scale/WIGGLERADIUS), (int)(p_.y+pWiggle.y*scale/WIGGLERADIUS));
				for(Point near:roads.getAdjacencyList(p)) {
					Point near_ = getScreenPos(near);
					Point nearWiggle = wiggle(near,near);
					Point pEnd = new Point((int)(near_.x+nearWiggle.x*scale/WIGGLERADIUS), (int)(near_.y+nearWiggle.y*scale/WIGGLERADIUS));
					int weight = roads.getEdgeWeight(p, near);
					Stroke defaultStroke = g2.getStroke();
					g2.setStroke(new BasicStroke(displayScale/(14-weight*6)+1));
					g2.setColor(roadColor);
					g2.drawLine(pStart.x, pStart.y, pEnd.x, pEnd.y);
					g2.setStroke(defaultStroke);
				}
			}
			counter.increment();
		}
		logger.logln("Roads drawn "+(System.currentTimeMillis()-time)+" ms");
	}
	public double calcDistance(Point a,Point b) {
		int dx=b.x-a.x;
		int dy=b.y-a.y;
		double distance;
		if(dx*dy>=0) {
			distance = Math.abs(dx+dy);
		}else {
			dx=Math.abs(dx);
			dy=Math.abs(dy);
			distance = Math.max(dx, dy);
		}
		return distance;
	}




	private final class MapPanelSwingWorker extends SwingWorker<Void, Integer> {
		@Override
		protected Void doInBackground() {
			try {
				printLoadingInfo.set(true);
				boolean wideview = scale<WIDEVIEW;
				if(!wideview) {
					calculateRivers();
				}
				calculateHexColors();
				if(HexData.ECONOMY.equals(displayData)&&!wideview) {
					loadRoads();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			frame.setCursor(Cursor.getDefaultCursor());
			printLoadingInfo.set(false);
			return null;
		}

		@Override
		protected void process(List<Integer> chunks) {
			int progress = chunks.get(chunks.size() - 1);
			System.out.println(progress);
		}

		@Override
		protected void done() {
			frame.repaint();
			printLoadingInfo.set(false);
		}
	}
	public class MouseAdapter implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			Point p = MapPanel.this.getGridPoint(e.getX(), e.getY());
			if(MouseEvent.BUTTON1==e.getButton()) {
				if(showDistance) {
					if(mouseoverHold&&p.equals(mouseover)) {
						mouseoverHold=false;
					}else {
						mouseoverHold=true;
						mouseover = p;
					}
					preprocessThenRepaint();
				}else{
					recenter(p,true);
					preprocessThenRepaint();
				}
			}else if(MouseEvent.BUTTON3==e.getButton()) {
				openMenu(e);
			}
		}
		private void openMenu(MouseEvent e) {
			Point gridPoint = getGridPoint(e.getPoint());
			JPopupMenu menu = new JPopupMenu();
			JMenuItem hero = new JMenuItem("Move Hero Marker");
			hero.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e1) {
					record.setHero(gridPoint);
					HashSet<Point> visible = controller.getGrid().getLineOfSight(gridPoint);
					record.addAllExplored(visible);
					frame.repaint();
				}
			});
			menu.add(hero);
			if(record.isExplored(gridPoint)) {
				JMenuItem explore = new JMenuItem("Unexplore");
				explore.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e1) {
						record.removeExplored(gridPoint);
						frame.repaint();
					}
				});
				menu.add(explore);
			}else {
				JMenuItem explore = new JMenuItem("Explore");
				explore.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e1) {
						record.addExplored(gridPoint);
						frame.repaint();
					}
				});
				menu.add(explore);
			}
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {
			if(MouseEvent.BUTTON1==e.getButton()) {
				isDragging = true;
				dragOffsetX = e.getX();
				dragOffsetY = e.getY();
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if(isDragging && MouseEvent.BUTTON1==e.getButton()) {
				isDragging = false;
				recenter();
				preprocessThenRepaint();
			}
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
				frame.repaint();
			}
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			Point gridPoint = MapPanel.this.getGridPoint(e.getX(),e.getY());
			if(mouseover!=null&&!mouseoverHold&&!MapPanel.this.mouseover.equals(gridPoint)){
				mouseover = gridPoint;
				if(showDistance) {
					Point center = MapPanel.this.getMiddleGridPoint();
					double distance = calcDistance(center,mouseover);
					MapPanel.this.setDistance(distance);
					frame.repaint();
				}
			}
		}
	}
	public class MouseWheelAdapter implements MouseWheelListener {
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(!printLoadingInfo.get()) {
				Point c = getMiddleGridPoint();
				double newscale = scale;
				double wheelRotation = -1*e.getWheelRotation()*scale/4;
				if(e.isShiftDown()) wheelRotation = wheelRotation*5;
				if(scale+wheelRotation<1) {
					newscale = 1;
				}else if(scale+wheelRotation>MAX_SCALE) {
					newscale = MAX_SCALE;
				}else {
					newscale = Math.floor(scale+wheelRotation);
				}
				if(newscale!=scale) {
					setScale(newscale);
					recenter(c,false);
					preprocessThenRepaint();
				}
			}
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
		File directory;
		String filename;
		if(prevFile!=null) {
			directory = prevFile.getAbsoluteFile().getParentFile();
			filename = prevFile.getName();
		}else {
			directory = AppData.getDirectory();
			filename = record.getSeed()+"_"+MapFrame.VERSION+".ser";
		}
		JFileChooser fileChooser = new JFileChooser(directory);
		fileChooser.setSelectedFile(new File(filename));
		if (fileChooser.showSaveDialog(this.getFrame()) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if(!file.getName().contains(".")) file = new File(file.getAbsolutePath()+".ser");
			record.setSaveLocation(file);
			record.setPos(this.getMiddleGridPoint());
			record.setScale(scale);
			if(record.save(frame.getAppData())) {
				frame.getAppData().removeRecent(prevFile);
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
		preprocessThenRepaint();
	}
	public boolean hasNext() {
		return previousIndex<previous.size()-1;
	}
	public void next() {
		if(!hasNext()) return;
		previousIndex++;
		recenter(previous.get(previousIndex),false);
		preprocessThenRepaint();
	}

	public MapFrame getFrame() {
		return frame;
	}

	public boolean isShowRivers() {
		return showRivers;
	}

	public void setShowRivers(boolean showRivers) {
		this.showRivers = showRivers;
		this.preprocessThenRepaint();
	}

	public void setShowCities(boolean selected) {
		this.showCities = selected;
		this.preprocessThenRepaint();
	}

	public boolean isPrintLoadingInfo() {
		return printLoadingInfo.get();
	}

	public void setPrintLoadingInfo(boolean printLoadingInfo) {
		this.printLoadingInfo.set(printLoadingInfo);
	}

	public void setShowDistance(boolean selected) {
		this.showDistance = selected;
		mouseoverHold = false;
		mouseover = getMiddleGridPoint();
		frame.repaint();
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

	public SaveRecord getRecord() {
		return record;
	}

	@Override
	public void repaint() {
		super.repaint();
	}

	public DataController getController() {
		return controller;
	}

	public void setHighlight(Color selectedColor) {
		Point p = this.getSelectedGridPoint();
		record.setHighlight(p,selectedColor);
	}

	public void setShowIcons(boolean selected) {
		this.showIcons = selected;
		this.preprocessThenRepaint();
	}
	public boolean isShowIcons() {
		return this.showIcons;
	}

	public boolean isShowCities() {
		return this.showCities;
	}

	public void print(BufferedImage image) {
		this.printMode = true;
		this.repaint();
		int result = JOptionPane.showConfirmDialog(this.frame,
				"Print this area?",
				"Confirm",
				JOptionPane.YES_NO_OPTION);
		if(result==JOptionPane.YES_OPTION) {
			this.paint(image.getGraphics());
		}
		this.printMode = false;
		this.repaint();
	}

}
