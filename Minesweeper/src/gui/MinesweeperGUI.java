package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import data.MinesweeperGame;
import data.RecordList;
import data.RecordList.Difficulty;


public class MinesweeperGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	//private static enum Difficulty {EASY, MEDIUM, HARD, CUSTOM}

	/*
	 * These variables can be modified to adjust the game experience
	 */
	private static final boolean GENEROUSMULLIGAN = false;
	private static final int SCALE = 25;
	private static final int GUI_UPDATE_INTERVAL = 200;
	private static final Difficulty DEFAULTDIFFICULTY = Difficulty.EASY;
	private static final Font SYMBOLSFONT = loadFont("resources/Symbola.ttf");
	private static final Font LETTERSFONT = new Font("Arial", Font.BOLD, SCALE-3);
	private static final Character BOMBCHARACTER = '\u26EF';
	private static final Character FLAGCHARACTER = '\u2691'; //'\u2690';
	private static final String DEFAULTSAVEFILE = System.getProperty("user.home")+ File.separator + "Minesweeper"+ File.separator + "Minesweeper.dat";
	/*
	 * -------------------------------------------------------------
	 */


	private JPanel panel;
	private MinesweeperGame game;

	private JLabel timerLabel;
	private JLabel BVLabel;
	private JLabel clickLabel;
	private JLabel mineLabel;
	private ButtonGroup group;
	private ButtonModel selectedDifficulty;
	private Difficulty currentDifficulty;
	private int clickCount;
	private RecordList statistics;
	//private String saveFile;

	public static void main(String[] args) throws InterruptedException{
		new MinesweeperGUI();
	}

	private static Font loadFont(String file){
		Font font;
		try{
			font = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream(file));
			font = font.deriveFont(Font.BOLD,(float)SCALE);
		}catch(Exception e){
			font = new Font("Arial", Font.BOLD, SCALE);
			System.out.println("Error: Failed to load font");
		}
		return font;
	}

	public MinesweeperGUI() throws InterruptedException{
		super();
		JMenuBar menuBar=setUpMenuBar(new JMenuBar());
		this.panel = new Surface();

		this.game=newGame(MinesweeperGUI.DEFAULTDIFFICULTY);
		currentDifficulty=DEFAULTDIFFICULTY;

		setUpFrame(this);

		this.add(this.panel,BorderLayout.CENTER);
		this.add(menuBar, BorderLayout.NORTH);
		this.add(setUpStatsPanel(),BorderLayout.SOUTH);
		this.statistics=new RecordList();
		this.statistics.load(DEFAULTSAVEFILE);
		this.setVisible(true);
		Timer timer = new Timer(GUI_UPDATE_INTERVAL, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String time = "  time: "+padding(String.valueOf(game.timeSeconds()),3)+"  ";
				String clicks = "  clicks: "+padding(String.valueOf(clickCount),3)+"  ";
				String mines;
				if(game.lost()) mines="  mines: 0/"+game.mines()+"  ";
				else if(game.won()) mines="  mines: "+game.mines()+"/"+game.mines()+"  ";
				else mines = "  mines: "+game.getNumFlags()+"/"+game.mines()+"  ";
				timerLabel.setText(time);
				clickLabel.setText(clicks);
				mineLabel.setText(mines);
			}
		});
		timer.start();
		this.pack();
		this.setLocationRelativeTo(null);
	}

	private String padding(String s,int size){
		while(s.length()<size){s=" "+s;}
		return s;
	}

	private JFrame setUpFrame(JFrame frame){
		frame.setTitle("Minesweeper!");
		//frame.setSize(SCALE*game.x()+XOFFSET, SCALE*game.y()+YOFFSET);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		return frame;
	}

	private JMenuBar setUpMenuBar(JMenuBar menuBar){
		JMenu menu = new JMenu(" File ");
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("New Game",KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				game=newGame(game.x(), game.y(), game.mines());
			}});
		menu.add(menuItem);

		menuItem = new JMenuItem("Statistics");
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new StatsWindow(MinesweeperGUI.this,statistics,currentDifficulty);
				//JOptionPane.showMessageDialog(MinesweeperGUI.this, "Not Yet Implemented, sorry!");
			}});
		menu.add(menuItem);


		menu.addSeparator();
		menu.add(new JLabel("Difficulty:"));

		group = new ButtonGroup();
		JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("Easy");
		rbMenuItem.setSelected(true);
		rbMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(group.isSelected(selectedDifficulty)) return;
				int n = JOptionPane.showConfirmDialog(MinesweeperGUI.this, "Would you like to start a new game?", "New Game?", JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_OPTION){
					MinesweeperGUI.this.game=newGame(Difficulty.EASY);
					selectedDifficulty=group.getSelection();
					currentDifficulty=Difficulty.EASY;
					MinesweeperGUI.this.setLocationRelativeTo(null);
				}
				else group.setSelected(selectedDifficulty, true);
			}});
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Medium");
		rbMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(group.isSelected(selectedDifficulty)) return;
				int n = JOptionPane.showConfirmDialog(MinesweeperGUI.this, "Would you like to start a new game?", "New Game?", JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_OPTION){
					MinesweeperGUI.this.game=newGame(Difficulty.MEDIUM);
					selectedDifficulty=group.getSelection();
					currentDifficulty=Difficulty.MEDIUM;
					MinesweeperGUI.this.setLocationRelativeTo(null);
				}
				else group.setSelected(selectedDifficulty, true);
			}});
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Hard");
		rbMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(group.isSelected(selectedDifficulty)) return;
				int n = JOptionPane.showConfirmDialog(MinesweeperGUI.this, "Would you like to start a new game?", "New Game?", JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_OPTION){
					MinesweeperGUI.this.game=newGame(Difficulty.HARD);
					selectedDifficulty=group.getSelection();
					currentDifficulty=Difficulty.HARD;
					MinesweeperGUI.this.setLocationRelativeTo(null);
				}
				else group.setSelected(selectedDifficulty, true);
			}});
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Custom");
		rbMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String s = (String)JOptionPane.showInputDialog(
						MinesweeperGUI.this,
						"Enter desired specs: \"width, height, mines\"",
						"New Game?",
						JOptionPane.PLAIN_MESSAGE,
						null,
						null,
						game.x()+", "+game.y()+", "+game.mines());
				if(s==null){
					group.setSelected(selectedDifficulty, true);
					return;
				}
				String[] input = s.split("[,\\s]\\s*");
				try{
					int x=Integer.parseInt(input[0]);
					int y=Integer.parseInt(input[1]);
					int mines=Integer.parseInt(input[2]);
					MinesweeperGUI.this.game=newGame(x,y,mines);
					selectedDifficulty=group.getSelection();
					currentDifficulty=Difficulty.CUSTOM;
					MinesweeperGUI.this.setLocationRelativeTo(null);
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(MinesweeperGUI.this,"Error parsing inputs", "Inane error", JOptionPane.ERROR_MESSAGE);
					group.setSelected(selectedDifficulty, true);
				}catch(ArrayIndexOutOfBoundsException e){
					JOptionPane.showMessageDialog(MinesweeperGUI.this,"Error parsing inputs", "Inane error", JOptionPane.ERROR_MESSAGE);
					group.setSelected(selectedDifficulty, true);
				}
			}});
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		selectedDifficulty=group.getSelection();

		menu.addSeparator();
		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}});
		menu.add(menuItem);


		menuBar.add(Box.createHorizontalGlue());
		BVLabel=new JLabel();
		menuBar.add(BVLabel);
		return menuBar;
	}

	private JPanel setUpStatsPanel(){
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		clickLabel=new JLabel("clicks");
		timerLabel=new JLabel("time");
		mineLabel=new JLabel("mines");
		p.add(clickLabel);
		p.add(Box.createHorizontalGlue());
		p.add(mineLabel);
		p.add(Box.createHorizontalGlue());
		p.add(timerLabel);


		return p;
	}

	private void endGamePopup(boolean win){
		game.end();
		panel.repaint();
		String message;
		int icon;
		if(win){
			statistics.addRecord(game.time(), clickCount, game.BBBV(), currentDifficulty);
			message="You Won!";
			icon=JOptionPane.INFORMATION_MESSAGE;
		}else{
			statistics.addLoss(0);
			message="You Lost!";
			icon=JOptionPane.ERROR_MESSAGE;
		}
		statistics.save(DEFAULTSAVEFILE);
		int n = JOptionPane.showOptionDialog(this,
				"Time: "+game.timeSeconds()+" seconds",
				message,
				JOptionPane.YES_NO_OPTION,
				icon,
				null,     //do not use a custom Icon
				new Object[]{"Play Again","Exit"},  //the titles of buttons
				"Play Again"); //default button title
		if(n==JOptionPane.YES_OPTION){
			this.game=newGame(Difficulty.CUSTOM);
		}else{
			System.exit(0);
		}
	}

	public MinesweeperGame newGame(int x, int y, int mines){
		MinesweeperGame game = new MinesweeperGame(x,y,mines);
		this.clickCount=0;
		this.BVLabel.setText("3BV: ??  ");
		//this.setSize(SCALE*x+XOFFSET, SCALE*y+YOFFSET);
		this.panel.setPreferredSize(new Dimension(SCALE*game.x(),SCALE*game.y()));
		this.setVisible(true);
		this.validate();
		this.repaint();
		this.pack();
		return game;
	}
	public MinesweeperGame newGame(Difficulty difficulty){
		MinesweeperGame game;
		switch(difficulty){
		case EASY: game = newGame(9,9,10); break;
		case MEDIUM: game = newGame(16,16,40); break;
		case HARD: game = newGame(30,16,99); break;
		default: game = newGame(this.game.x(),this.game.y(),this.game.mines());
		}
		return game;
	}
	public void mulligan(int x, int y) {
		if(GENEROUSMULLIGAN){
			while(game.map(x,y)!=0){
				this.game=newGame(Difficulty.CUSTOM);
			}
		}else{
			while(game.map(x,y)<0){
				this.game=newGame(Difficulty.CUSTOM);
			}
		}
	}


	private class Surface extends JPanel{
		private static final long serialVersionUID = 1L;

		public Surface(){
			this.addMouseListener(new ClickListener());
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			drawSquares(g);
			drawGrid(g);
		}

		private void drawGrid(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			g2d.setColor(Color.gray);
			for(int i=0;i<=game.x();i++){
				g2d.drawLine(i*SCALE, 0, i*SCALE, SCALE*game.y());
			}
			for(int i=0;i<=game.y();i++){
				g2d.drawLine(0, i*SCALE, SCALE*game.x(), i*SCALE);
			}
		}

		private void drawSquares(Graphics g){
			Graphics2D g2d = (Graphics2D) g;

			for(int i=0;i<game.y();i++){
				for(int j=0;j<game.x();j++){
					if(!game.revealed(j,i)&&!game.won()&&!game.lost()){
						drawUnrevealedSquare(g2d, j, i);
					}else if(game.map(j,i)>=0){
						drawRevealedSquare(g2d, j, i,game.map(j,i));
					}else{
						g2d.setFont(SYMBOLSFONT);
						Character ch = BOMBCHARACTER;
						if(!g2d.getFont().canDisplay(ch)) ch='B';
						g2d.setColor(getColor(-1));
						Point center = center(ch.toString(),g2d,1,1);
						g2d.drawString(ch.toString(), j*SCALE+center.x, i*SCALE+center.y);
					}
				}
			}

		}

		private void drawRevealedSquare(Graphics2D g2d, int x, int y, int value) {
			if(value>0){
				g2d.setFont(LETTERSFONT);
				g2d.setColor(getColor(value));
				Point center = center(String.valueOf(value),g2d,1,2);
				g2d.drawString(String.valueOf(value), x*SCALE+center.x, y*SCALE+center.y);
			}
		}

		private void drawUnrevealedSquare(Graphics2D g2d, int x, int y) {
			g2d.setColor(Color.lightGray);
			//int border = SCALE/10;
			//g2d.fillRect(x*SCALE+1+border, y*SCALE+1+border, SCALE-1-border*2, SCALE-1-border*2);
			g2d.fill3DRect(x*SCALE+1, y*SCALE+1, SCALE-1, SCALE-1,true);
			if(game.flagged(x,y)){
				g2d.setFont(SYMBOLSFONT.deriveFont((float)SCALE*5/8));
				Character ch = FLAGCHARACTER;
				if(!g2d.getFont().canDisplay(ch)) ch='F';
				g2d.setColor(Color.magenta);
				Point center = center(ch.toString(),g2d,1,2);
				g2d.drawString(ch.toString(), x*SCALE+center.x, y*SCALE+center.y);
			}
		}

		private Point center(String s,Graphics2D g,int xoffset,int yoffset){
			Rectangle2D bounds = g.getFont().getStringBounds(s, g.getFontRenderContext());
			int x=(int) (SCALE-bounds.getWidth())/2;
			int y=(int) (SCALE-bounds.getHeight())/2;
			//System.out.println(s+" "+bounds.getMinX()+" "+bounds.getMinY()+" "+bounds.getMaxX()+" "+bounds.getMaxY()+" "+bounds.getWidth()+" "+bounds.getHeight());
			return new Point(x+xoffset,y-(int)bounds.getMinY()+yoffset);
		}

		private Color getColor(int val){
			Color result;
			if(val<0){//mine color
				result=new Color(200,0,0);
			}else if(val==1){
				result=Color.blue;
			}else if(val==2){
				result=Color.green;
			}else if(val==3){
				result=Color.red;
			}else if(val==4){
				result=new Color(77,176,230);
			}else if(val==5){
				result=new Color(156, 93, 82);
			}else if(val==6){
				result=Color.cyan;
			}else if(val==7){
				result=Color.black;
			}else if(val==2){
				result=Color.gray;
			}else{
				result=Color.cyan;
			}
			return result;
		}
	}

	private class ClickListener implements MouseListener{
		public void mouseClicked(MouseEvent e) {/*do nothing*/}
		public void mouseEntered(MouseEvent e) {/*do nothing*/}
		public void mouseExited(MouseEvent e) {/*do nothing*/}
		public void mousePressed(MouseEvent e) {/*do nothing*/}
		public void mouseReleased(MouseEvent e) {
			int x=e.getX();
			int y=e.getY();
			if(x%MinesweeperGUI.SCALE==0||y%MinesweeperGUI.SCALE==0) return;
			x/=MinesweeperGUI.SCALE;
			y/=MinesweeperGUI.SCALE;
			if(e.getButton()==MouseEvent.BUTTON3){//right click
				if(game.started()){
					game.flag(x, y);
					clickCount++;
				}
			}else if(e.getClickCount()==2){//double click
				if(game.guessAll(x, y, false))clickCount+=2;
			}else{//other (single click)
				if(!game.started()) mulligan(x,y);
				if(game.guess(x, y, false)) clickCount++;
				game.start();
				BVLabel.setText("3BV: "+game.BBBV()+"  ");
			}
			if(game.lost()) endGamePopup(false);
			else if(game.won()) endGamePopup(true);
			panel.repaint();
		}
	}


}
