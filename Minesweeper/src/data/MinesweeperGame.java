package data;
import java.awt.Point;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;


public class MinesweeperGame {
	final int x;
	final int y;
	final int mines;
	final int[][] map;
	final boolean[][] revealed;
	final boolean[][] flagged;
	boolean lost;
	boolean won;
	int remainingsquares;
	private Long startTime=null;
	private Long endTime=null;
	private int BBBV = 0;

	public static void main(String[] args){
		MinesweeperGame game = new MinesweeperGame(10,10,10);
		System.out.println(game.toString());

		Scanner scan = new Scanner(System.in);
		while(!game.lost&&!game.won){
			int x = scan.nextInt();
			int y = scan.nextInt();
			game.guess(x, y, false);
			System.out.println(game.toString());
		}
		scan.close();
	}
	
	public boolean lost(){
		return this.lost;
	}
	public boolean won(){
		return this.won;
	}
	public int mines(){
		return this.mines;
	}
	public int x(){
		return this.x;
	}
	public int y(){
		return this.y;
	}
	public int map(int x,int y){
		return map[x][y];
	}
	public boolean revealed(int x,int y){
		return revealed[x][y];
	}
	public boolean flagged(int x,int y){
		return flagged[x][y];
	}

	public MinesweeperGame(int x, int y, int mines){
		this.x=x;
		this.y=y;
		this.mines=Math.min(mines, x*y-1);
		map = new int[this.x][this.y];
		revealed = new boolean[this.x][this.y];
		flagged = new boolean[this.x][this.y];
		lost=false;
		won=false;

		Random rand = new Random();
		HashSet<Point> minelocations = new HashSet<Point>();
		for(int i=0;i<this.mines;i++){
			int x1 = rand.nextInt(this.x);
			int y1 = rand.nextInt(this.y);
			Point dim = new Point(x1,y1);
			while(minelocations.contains(dim)){
				x1 = rand.nextInt(this.x);
				y1 = rand.nextInt(this.y);
				dim = new Point(x1,y1);
			}
			minelocations.add(dim);
			map[x1][y1] = Integer.MIN_VALUE;
			for(Point d:getAdjacent(dim)){
				if(d.x>=0&&d.x<this.x&&d.y>=0&&d.y<this.y){
					map[d.x][d.y]++;
				}
			}
		}
		this.checkWin();
	}

	public String toString(){
		StringBuilder result = new StringBuilder();
		result.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\r\n");
		result.append("  0123456789\r\n");
		result.append("  ||||||||||\r\n");
		for(int i=0;i<y;i++){
			result.append(i+"-");
			for(int j=0;j<x;j++){
				if(!revealed[j][i]&&!this.won){
					if(flagged[j][i]) result.append("F");
					else result.append("#");
				}else if(map[j][i]>0){
					result.append(map[j][i]);
				}else if(map[j][i]==0){
					result.append(" ");
				}else{
					result.append("X");
				}
			}
			result.append("\r\n");
		}
		if(lost) result.append("You have lost this game!\r\n");
		else if(won) result.append("You have won this game!\r\n");
		else result.append(this.remainingsquares+"\r\n");
		result.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		return result.toString();
	}

	public boolean guess(int x, int y, boolean ignoreFlags){
		if(x<0||y<0||x>=this.x||y>=this.y) return false;
		if(!ignoreFlags&&flagged[x][y]) return false;
		flagged[x][y]=false;
		if(revealed[x][y]) return false;
		revealed[x][y] = true;
		int thing = map[x][y];
		if(thing<0) this.lost = true;
		else if(thing==0){
			guessAll(x,y,true);
		}

		this.checkWin();
		if(remainingsquares==0) this.won = true;
		return true;
	}

	public boolean guessAll(int x,int y,boolean ignoreFlags){
		boolean result = false;
		if(!ignoreFlags){
			if(flagged[x][y]) return false;
			int flagCount = 0;
			for(Point p:getAdjacent(new Point(x,y))){
				if(this.isFlagged(p.x, p.y)) flagCount++;
			}
			if(flagCount<this.map[x][y]) return false;
		}
		for(Point d:getAdjacent(new Point(x,y))){
			if(guess(d.x,d.y,ignoreFlags)) result=true;
		}
		return result;
	}

	private int checkWin(){
		int count = 0;
		for(int i=0;i<this.x;i++){
			for(int j=0;j<this.y;j++){
				if(!this.revealed[i][j]&&this.map[i][j]>=0){
					count++;
				}
			}
		}

		remainingsquares = count;
		return count;
	}
	
	public int getNumFlags(){
		int count=0;
		for(int i=0;i<flagged.length;i++){
			for(int j=0;j<flagged[0].length;j++){
				if(flagged[i][j]&&!revealed[i][j]) count++;
			}
		}
		return count;
	}

	public boolean flag(int x,int y){
		if(x<0||y<0||x>=this.x||y>=this.y) return false;
		flagged[x][y]=!flagged[x][y];

		return flagged[x][y];
	}

	public boolean isFlagged(int x,int y){
		if(x<0||y<0||x>=this.x||y>=this.y) return false;
		else return flagged[x][y];	
	}

	public void start(){
		if(this.startTime==null) this.startTime=System.nanoTime();
	}
	public void end(){
		if(this.endTime==null) this.endTime=System.nanoTime();
	}
	public long time(){
		if(this.startTime==null) return 0;
		else if(this.endTime==null) return System.nanoTime()-this.startTime;
		else return this.endTime-this.startTime;
	}
	public long timeSeconds(){
		if(this.startTime==null) return 0;
		else return Math.min(this.time()/1000000000L+1,999);
	}
	public boolean started(){
		return this.startTime!=null;
	}
	public boolean finished(){
		return this.endTime!=null;
	}
	
	public int BBBV(){
		if(BBBV!=0) return BBBV;
		int rating = 0;
		HashSet<Point> squares = new HashSet<Point>();
		for(int x=0;x<map.length;x++){
			for(int y=0;y<map[0].length;y++){
				squares.add(new Point(x, y));
			}
		}
		while(!squares.isEmpty()){
			Point p = squares.iterator().next();
			if(map[p.x][p.y]<0){
				squares.remove(p);
			}else if(map[p.x][p.y]==0){
				rating++;
				recursiveRemove(p, squares);
			}else{
				boolean solo = true;
				for(Point adj:getAdjacent(p)){
					if(adj.x>=0&&adj.x<this.x&&adj.y>=0&&adj.y<this.y){
						if(map[adj.x][adj.y]==0){
							solo=false;
							recursiveRemove(adj, squares);
						}
					}
				}
				if(solo) squares.remove(p);
				rating++;
			}
		}
		BBBV=rating;
		return rating;
	}
	private boolean recursiveRemove(Point d,HashSet<Point> squares){
		if(squares.contains(d)){
			squares.remove(d);
			if(map[d.x][d.y]==0){
				for(Point adj:getAdjacent(d)){
					recursiveRemove(adj, squares);
				}
			}
			return true;
		}
		return false;
	}
	
	private static Point[] getAdjacent(Point p){
		Point[] result = new Point[8];
		result[0]=new Point(p.x+1, p.y-1);
		result[1]=new Point(p.x+1, p.y);
		result[2]=new Point(p.x+1, p.y+1);
		result[3]=new Point(p.x, p.y+1);
		result[4]=new Point(p.x-1, p.y+1);
		result[5]=new Point(p.x-1, p.y);
		result[6]=new Point(p.x-1, p.y-1);
		result[7]=new Point(p.x, p.y-1);
		return result;
	}

}
