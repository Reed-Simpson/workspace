package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import controllers.DataController;
import data.AStarGraph;
import data.HexData;
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
	private boolean printLoadingInfo;
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
	private HashMap<Point,BasicSpline> splineCache;
	private boolean showIcons;

	public MapPanel(MapFrame frame, SaveRecord record) {
		this.frame = frame;
		this.showCities=true;
		this.showIcons=false;
		colorCache = new HashMap<Point,Pair<Color,Color>>();
		iconCache = new HashMap<Point,List<Icon>>();
		splineCache = new HashMap<Point,BasicSpline>();
		this.addMouseListener(new MouseAdapter());
		this.addMouseMotionListener(new MouseMotionAdapter());
		this.addMouseWheelListener(new MouseWheelAdapter());
		this.setDisplayData(HexData.ALTITUDE);
		this.setPreferredSize(new Dimension(800, 800));
		this.printLoadingInfo = true;
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
		//this.recenter(record.getPos());
		//		this.printLoadingInfo = false;
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
					calculateRivers();
					return null;
				}
				@Override
				protected void process(List<Integer> chunks) {
					int progress = chunks.get(chunks.size() - 1);
					System.out.println(progress);
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
		return new Point(this.getWidth()/2, (int) (this.getHeight()/2+scale/2));
	}
	public void recenter() {
		recenter(getMiddleGridPoint(),true);
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
			previous.add(getMiddleGridPoint());
			previousIndex=previous.size()-1;
		}
		record.setPos(MapPanel.this.getMiddleGridPoint());
	}
	public double getScale() {
		return this.scale;
	}
	public void setScale(double d) {
//		if(this.scale<WIDEVIEW&&d>=WIDEVIEW) {
//			colorCache = new HashMap<Point,Pair<Color,Color>>();
//			iconCache = new HashMap<Point,List<Icon>>();
//		}
		this.scale=d;
		record.setScale(scale);
	}

	public HexData getDisplayData() {
		return displayData;
	}
	public void setDisplayData(HexData displayData) {
		if(this.displayData!=displayData) {
			printLoadingInfo = true;
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
		drawCenterHex(g2, displayScale);
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
		SwingWorker<Void, Integer> worker = new SwingWorker<Void,Integer>(){
			@Override
			protected Void doInBackground() throws Exception {
				printLoadingInfo = true;
				boolean wideview = scale<WIDEVIEW;
				if(!isDragging&&!wideview) {
					calculateRivers();
				}
				calculateHexColors();
				if(HexData.ECONOMY.equals(displayData)&&!wideview) {
					loadRoads();
				}
	            frame.setCursor(Cursor.getDefaultCursor());
	    		printLoadingInfo = false;
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
				printLoadingInfo = false;
			}

		};
		worker.execute();
	}

	private synchronized void calculateHexColors() {
		Point p1 = getGridPoint(-20,this.getHeight()+60);
		Point p2 = getGridPoint(this.getWidth()+20,-20);
		int sum = (p2.x-p1.x);
		int step = getStep();
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		if(printLoadingInfo) {
			logger.log("Loading colors: ");
			dialog.createProgressUI("Loading view data: ");
		}
		HashMap<Point,Pair<Color,Color>> newCache = new HashMap<Point,Pair<Color,Color>>();
		HashMap<Point,List<Icon>> newIconCache = new HashMap<Point,List<Icon>>();
		for(int i=p1.x;i<p2.x;i+=step) {
			for(int j=p2.y;j<p1.y;j+=step) {
				Point p = new Point(i,j);
				if(!controller.getGrid().isWater(p)) {
					Pair<Color,Color> cached = colorCache.get(p);
					if(cached==null) {
						Color color1 = getColor1(i,j,displayData);
						Color color2 = getColor2(i,j,displayData);
						if(color1==null) {
							color1 = color2;
							color2 = null;
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
			if(printLoadingInfo) counter.increment();
		}
		colorCache = newCache;
		iconCache = newIconCache;
		dialog.removeProgressUI();
		if(printLoadingInfo) logger.logln("Colors loaded "+(System.currentTimeMillis()-time)+" ms");
	}

	private List<Icon> getIcons(Point p) {
		List<Icon> result = new ArrayList<Icon>();
		BiomeType biome = controller.getBiomes().getBiome(p);
		Character c = biome.getCh();
		Point offset;
		if(BiomeType.CITY.getCh().equals(c)) {
			offset = new Point(-80,60);
			result.add( new Icon(c, offset, Color.black,2));
		}else if(BiomeType.WATER.getCh().equals(c)) {
			offset = new Point(-80,10);
			result.add( new Icon(c, offset, Color.black,0.7));
			offset = new Point(-30,60);
			result.add( new Icon(c, offset, Color.black,0.7));
			offset = new Point(10,-10);
			result.add( new Icon(c, offset, Color.black,0.7));
		}else if(BiomeType.ROCKYHILLS.getCh().equals(c)) {
			offset = new Point(-20,20);
			result.add( new Icon(c, offset, Color.black,1));
			offset = new Point(40,-20);
			result.add( new Icon(c, offset, Color.black,1));
		}else if(BiomeType.CLIFFS.getCh().equals(c)) {
			offset = new Point(-60,0);
			result.add( new Icon(c, offset, Color.black,0.7));
			offset = new Point(-20,40);
			result.add( new Icon(c, offset, Color.black,0.7));
			offset = new Point(20,0);
			result.add( new Icon(c, offset, Color.black,0.7));
		}else if(BiomeType.MOUNTAINS.getCh().equals(c)) {
			offset = new Point(-30,30);
			result.add( new Icon(c, offset, Color.black,1.5));
			offset = new Point(10,10);
			result.add( new Icon(BiomeType.ROCKYHILLS.getCh(), offset, Color.black,1.5));
		}else if(BiomeType.DESERT.getCh().equals(c)) {
			offset = new Point(-60,40);
			result.add( new Icon(c, offset, Color.black,1));
			offset = new Point(-20,60);
			result.add( new Icon(c, offset, Color.black,1));
			offset = new Point(20,20);
			result.add( new Icon(c, offset, Color.black,1));
		}else {
			offset = new Point(-60,20);
			result.add( new Icon(c, offset, Color.black,0.7));
			offset = new Point(-20,40);
			result.add( new Icon(c, offset, Color.black,0.7));
			offset = new Point(20,0);
			result.add( new Icon(c, offset, Color.black,0.7));
		}
		return result;
	}

	private void drawHexes(Graphics2D g2, int step, int displayScale, Color borderColor) {
		Point p1 = getGridPoint(-20,this.getHeight()+60);
		Point p2 = getGridPoint(this.getWidth()+20,-20);
		int sum = (p2.x-p1.x);
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		if(printLoadingInfo) {
			logger.log("Drawing hexes: ");
			dialog.createProgressUI("Drawing hexes: ");
		}
		for(int i=p1.x;i<p2.x;i+=step) {
			for(int j=p2.y;j<p1.y;j+=step) {
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
						g2.drawString("\u23F3", getScreenPos(i,j).x+(int)((offset.x*scale)/100), getScreenPos(i,j).y+(int)((offset.y*scale)/100));
					}
				}
			}
			if(printLoadingInfo) counter.increment();
		}
		if(printLoadingInfo) logger.logln("Hexes drawn "+(System.currentTimeMillis()-time)+" ms");
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
			if(printLoadingInfo) counter.increment();
		}
		if(printLoadingInfo) logger.logln("Oceans loaded "+(System.currentTimeMillis()-time)+" ms");
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
				List<Icon> icons = iconCache.get(new Point(i,j));
				if(icons!=null) {
					if((scale>8&&showIcons)||(scale>2&&BiomeType.CITY.getCh().equals(icons.get(0).getCh()))) {
					for(Icon icon:icons) {
						Character ch = icon.getCh();
						Point offset = icon.offset;
						Color c = icon.getC();
						double cScale = icon.getScale();
						g2.setFont(g2.getFont().deriveFont((float) (displayScale*cScale)));
						if(c==null) c=borderColor;
						g2.setColor(c);
						if(ch!=null) g2.drawString(ch.toString(), getScreenPos(i,j).x+(int)((offset.x*scale)/100), getScreenPos(i,j).y+(int)((offset.y*scale)/100));
					}
					}
				}
			}
			if(printLoadingInfo) counter.increment();
		}
		logger.logln("Symbols drawn "+(System.currentTimeMillis()-time)+" ms");
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
		int corneroffset = 50;
		int inset = 20;
		int width = 400;
		int height = 50;
		int lineDist = (int) ((width-2*inset)*6.0/(scale*2));
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
		g2.setStroke(defaultStroke);
	}

	private synchronized void calculateRivers() {
		PrecipitationModel precipitation = controller.getPrecipitation();
		Point p1 = getGridPoint(-40,this.getHeight()+80);
		Point p2 = getGridPoint(this.getWidth()+40,-40);
		p1.translate(-100, 100);
		p2.translate(100, -100);
		time = System.currentTimeMillis();
		int sum = (p2.x-p1.x);
		int loadingFactor = (p1.y-p2.y);
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		if(initializing) {
			dialog.createProgressUI("Initializing rivers: ");
			logger.log("Initializing rivers "+(sum*loadingFactor)+": ");
		}else if(printLoadingInfo) {
			dialog.createProgressUI("Loading rivers: ");
			logger.log("Loading rivers "+(sum*loadingFactor)+": ");
		}
		for(int i=p1.x;i<p2.x;i+=1) {
			for(int j=p2.y;j<p1.y;j+=1) {
				if(!controller.getGrid().isWater(i,j)) {
					precipitation.getFlow(new Point(i,j));
				}
			}
			if(printLoadingInfo||initializing) counter.increment();
		}
		if(initializing) {
			dialog.removeProgressUI();
			dialog.createProgressUI("Initializing lakes: ");
			logger.log("Initializing lakes "+(sum*loadingFactor)+": ");
			counter.resetCounter();
		}else if(printLoadingInfo) {
			logger.logln("--(100%) Rivers loaded "+(System.currentTimeMillis()-time)+" ms");
			time = System.currentTimeMillis();
			dialog.removeProgressUI();
			counter.resetCounter();
			dialog.createProgressUI("Loading lakes: ");
			logger.log("Loading lakes "+(sum*loadingFactor)+": ");
		}
		for(int i=p1.x;i<p2.x;i+=1) {
			for(int j=p2.y;j<p1.y;j+=1) {
				Point p = new Point(i,j);
				if(!controller.getGrid().isWater(i,j)&&p.equals(precipitation.getFlow(p))) {
					precipitation.time = System.currentTimeMillis();
					precipitation.generateLake(p);
				}
			}
			if(printLoadingInfo||initializing) counter.increment();
		}

		counter.resetCounter();
		if(initializing) {
			dialog.removeProgressUI();
			dialog.createProgressUI("Initializing river volume: ");
			logger.log("Initializing river volume "+(sum*loadingFactor)+": ");
		}else if(printLoadingInfo) {
			logger.logln("--(100%) Lakes loaded "+(System.currentTimeMillis()-time)+" ms");
			time = System.currentTimeMillis();
			dialog.removeProgressUI();
			dialog.createProgressUI("Loading river volume: ");
			logger.log("Loading river volume "+(sum*loadingFactor)+": ");
		}
		precipitation.resetVolumeCache();
		for(int i=p1.x;i<p2.x;i+=1) {
			for(int j=p2.y;j<p1.y;j+=1) {
				if(!controller.getGrid().isWater(i,j)) {
					Point p = new Point(i,j);
					precipitation.updateFlowVolume(p);
				}
			}
			if(printLoadingInfo||initializing) counter.increment();
		}
		if(printLoadingInfo) logger.logln("--(100%) Volumes loaded "+(System.currentTimeMillis()-time)+" ms");
		if(initializing) logger.logln("--(100%) Volumes Initialized "+(System.currentTimeMillis()-time)+" ms");
		counter.resetCounter();
		if(initializing) {
			dialog.removeProgressUI();
			dialog.createProgressUI("Initializing river splines: ");
			logger.log("Initializing river splines "+(sum*loadingFactor)+": ");
		}else if(printLoadingInfo) {
			time = System.currentTimeMillis();
			dialog.removeProgressUI();
			dialog.createProgressUI("Loading river splines: ");
			logger.log("Loading river splines "+(sum*loadingFactor)+": ");
		}
		HashMap<Point,BasicSpline> newcache = new HashMap<Point,BasicSpline>();
		for(int i=p1.x;i<p2.x;i+=1) {
			for(int j=p2.y;j<p1.y;j+=1) {
				if(!controller.getGrid().isWater(i,j)) {
					Point p = new Point(i,j);
					BasicSpline spline = splineCache.get(p);
					if(spline==null) spline = getRiverSpline(p);
					newcache.put(p, spline);
					//todo initialize splines
				}
			}
			if(printLoadingInfo||initializing) counter.increment();
		}
		splineCache = newcache;
		if(printLoadingInfo) logger.logln("--(100%) Splines loaded "+(System.currentTimeMillis()-time)+" ms");
		if(initializing) logger.logln("--(100%) Initialized "+(System.currentTimeMillis()-time)+" ms");
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
		if(printLoadingInfo) {
			dialog.createProgressUI("Drawing rivers: ");
			logger.log("Drawing rivers: ");
		}
		Stroke defaultStroke = g2.getStroke();
		for(Entry<Point,BasicSpline> e:splineCache.entrySet()) {
			drawCurvedRiver(g2, displayScale, e.getKey(),e.getValue());
			if(printLoadingInfo) counter.increment();
		}
		g2.setStroke(defaultStroke);
		dialog.removeProgressUI();
		if(printLoadingInfo) logger.logln("Rivers drawn "+(System.currentTimeMillis()-time)+" ms");
	}

	private void drawCurvedRiver(Graphics2D g2, int displayScale, Point p0,BasicSpline spline) {
		if(spline!=null) {
			float volume = controller.getPrecipitation().getFlowVolume(p0);
			float width = (float) (Math.sqrt(volume)/15.0f*displayScale);
			if(width>displayScale) width = displayScale;
			if(showRivers||width>1.5) {
				drawSpline(g2, spline, width,BiomeType.RIVER.getColor(),p0);
			}
		}
	}

	private void drawSpline(Graphics2D g2, BasicSpline spline, float width,Color color,Point p0) {
		Point p1 = controller.getPrecipitation().getRiver(p0);
		double distance = calcDistance(p0, p1);
		Point anchor = getScreenPos(p0);
		Point pointBefore = null;
		float step = (float) (1f/3/scale)/RIVERRENDERGRANULARITY;
		for(float f = 0; f<=1f/3+step/distance; f+=step/distance) {
			Point p = spline.getPoint(f);
			Point pnt = new Point((int)(anchor.x+p.x*scale/WIGGLERADIUS), (int)(anchor.y+p.y*scale/WIGGLERADIUS));
			g2.setColor(color);
			g2.fillOval(pnt.x-(int)width/2, pnt.y-(int)width/2, (int)width, (int)width);

			if(pointBefore != null && width < 30*step*scale) {
				g2.setColor(color);
				g2.setStroke(new BasicStroke(Math.max(width-1,0)));
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
//		Point hexCenter = getScreenPos(p);
//		if(controller.getGrid().isWater(p)) return hexCenter;
//		else {
//			Point wigglefactor = controller.getPrecipitation().getWiggleFactor(p);
//			return new Point((int)(hexCenter.x+wigglefactor.x*scale/WIGGLERADIUS/2),(int)(hexCenter.y+wigglefactor.y*scale/WIGGLERADIUS/2));
//		}
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
		if(printLoadingInfo) {
			dialog.createProgressUI("Drawing region: ");
			logger.log("Drawing region: ");
		}
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
				if(printLoadingInfo) counter.increment();
			}
		}
		if(printLoadingInfo) logger.logln("Region drawn "+(System.currentTimeMillis()-time)+" ms");
	}

	private Color getColor1(int i,int j,HexData data) {
		float height = controller.getGrid().getHeight(i, j);
		Point p = new Point(i,j);
		if (height<BiomeModel.SHALLOWS_HEIGHT) {
			return BiomeType.WATER.getColor();
		}else if (height<BiomeModel.WATER_HEIGHT) {
			return BiomeType.SHALLOWS.getColor();
		}else if (!HexData.BIOME.equals(data)&&controller.getPrecipitation().isLake(p)) {
			return BiomeType.LAKE.getColor();
		}else if(showCities) {
			BiomeType settlementType = controller.getPopulation().getSettlementType(p);
			if(settlementType!=null) {
				return settlementType.getColor();
			}
		}
		return null;
	}
	private Color getColor2(int i,int j,HexData data) {
		Point p = new Point(i,j);
		if(HexData.MAGIC.equals(data)) {
			return controller.getMagic().getColor(i, j);
		}else if(controller.getPrecipitation().isLake(p)&&!HexData.BIOME.equals(data)) {
			return null;
		}else if (controller.getGrid().isWater(i, j)) {
			return null;
		}else if(HexData.PRECIPITATION.equals(data)) {
			return controller.getPrecipitation().getColor(controller.getPrecipitation().getPrecipitation(i, j));
		}else if(HexData.BIOME.equals(data)) {
			return controller.getBiomes().getColor(controller.getBiomes().getBiome(i, j));
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
	public void highlightTowns(Graphics2D g2, int step, int displayScale, Color borderColor) {
		Point p1 = getGridPoint(-2*displayScale,this.getHeight()+4*displayScale);
		Point p2 = getGridPoint(this.getWidth()+2*displayScale,-2*displayScale);
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		logger.log("Drawing towns: ");
		int sum = (p2.x-p1.x);
		//int loadingFactor = (p1.y-p2.y);
		Counter counter = new Counter(sum, 5, logger);
		for(int i=p1.x;i<p2.x;i+=step) {
			for(int j=p2.y;j<p1.y;j+=step) {
				Point p = new Point(i,j);
				if(controller.getPopulation().isTown(p)) {
					Color color1 = getColor1(i,j,displayData);
					Color color2 = getColor2(i,j,displayData);
					if(color1==null) {
						color1 = color2;
						color2 = null;
					}
					this.drawHex(g2, getScreenPos(i,j),borderColor,color1,color2,displayScale,null);
				}
			}
			if(printLoadingInfo) {
				counter.increment();
			}
		}
		if(printLoadingInfo) logger.logln("Towns drawn "+(System.currentTimeMillis()-time)+" ms");
	}
	private synchronized void loadRoads() {
		Point p1 = getGridPoint(-40,this.getHeight()+80);
		Point p2 = getGridPoint(this.getWidth()+40,-40);
		int step = getStep();

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
				if(controller.getPopulation().isTown(p)) {
					controller.getEconomy().findTradeRoads(p);
				}
			}
			if(printLoadingInfo) {
				counter.increment();
			}
		}
		dialog.removeProgressUI();
		if(printLoadingInfo) logger.logln("Roads loaded "+(System.currentTimeMillis()-time)+" ms");
	}

	private void drawRoads(Graphics2D g2, int step, int displayScale, Color roadColor) {
		Point p1 = getGridPoint(-2*displayScale,this.getHeight()+4*displayScale);
		Point p2 = getGridPoint(this.getWidth()+2*displayScale,-2*displayScale);

		int sum = (p2.x-p1.x);
		MyLogger logger = new MyLogger(LOG_THRESHOLD);
		Counter counter = new Counter(sum, dialog.getProgressBar());
		counter.setLog(logger);
		if(printLoadingInfo) {
			logger.log("Drawing roads: ");
			dialog.createProgressUI("Drawing roads: ");
		}
		AStarGraph roads = controller.getEconomy().getRoads();
		for(int i=p1.x;i<p2.x;i+=step) {
			for(int j=p2.y;j<p1.y;j+=step) {
				Point p = new Point(i,j);
				Point p_ = getScreenPos(p);
				Point pWiggle = wiggle(p,p);
				Point pStart = new Point((int)(p_.x+pWiggle.x*scale/WIGGLERADIUS), (int)(p_.y+pWiggle.y*scale/WIGGLERADIUS));
				for(Point near:roads.getAdjacencyList(p)) {
					Point near_ = getScreenPos(near);
					Point nearWiggle = wiggle(near,near);
					Point pEnd = new Point((int)(near_.x+nearWiggle.x*scale/WIGGLERADIUS), (int)(near_.y+nearWiggle.y*scale/WIGGLERADIUS));
					int weight = roads.getEdgeWeight(p, near);
//					Point nScreen = wiggle(near,p);
					Stroke defaultStroke = g2.getStroke();
					g2.setStroke(new BasicStroke(displayScale/(14-weight*6)+1));
					g2.setColor(roadColor);
					g2.drawLine(pStart.x, pStart.y, pEnd.x, pEnd.y);
					g2.setStroke(defaultStroke);
				}
			}
			if(printLoadingInfo) {
				counter.increment();
			}
		}
		if(printLoadingInfo) logger.logln("Roads drawn "+(System.currentTimeMillis()-time)+" ms");
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




	public class MouseAdapter implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			Point p = MapPanel.this.getGridPoint(e.getX(), e.getY());
			if(showDistance) {
				if(mouseoverHold&&p.equals(mouseover)) {
					mouseoverHold=false;
				}else {
					mouseoverHold=true;
					mouseover = p;
				}
				preprocessThenRepaint();
			}else if(!printLoadingInfo) {
				recenter(p,true);
				preprocessThenRepaint();
			}
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
			recenter();
			preprocessThenRepaint();
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
			if(!printLoadingInfo) {
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
		File directory= AppData.getDirectory();
		String filename = record.getSeed()+"_"+MapFrame.VERSION+".ser";
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
		return printLoadingInfo;
	}

	public void setPrintLoadingInfo(boolean printLoadingInfo) {
		this.printLoadingInfo = printLoadingInfo;
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

}
