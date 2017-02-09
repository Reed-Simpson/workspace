package model;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import view.ChessboardPanel;

public class ChessBoard {
	public static int height = 8;
	public static int width = 8;

	public HashMap<Point,ChessPiece> pieces = new HashMap<Point,ChessPiece>();
	public int turnCount = 0;
	public ChessPiece selected;
	public ChessPiece[] kings = {};
	public Boolean check = null;
	public ChessPiece enPassant = null;
	public String gameStatus = null;

	public ChessBoard(){
		this.setBoard();
	}

	public void setBoard(){
		this.pieces = new HashMap<Point,ChessPiece>();
		for(ChessPiece p:ChessPiece.getStartingPieces()){
			ChessPiece p0 = ChessPiece.create(p.pos, this, p.color, p.type);
			this.pieces.put(p.pos, p0);
			if(p0.type.equals(King.TYPE)){
				ChessPiece[] newKings = new ChessPiece[this.kings.length+1];
				for(int i=0;i<this.kings.length;i++){
					newKings[i]=this.kings[i];
				}
				newKings[newKings.length-1]=p0;
				this.kings=newKings;
			}
		}
	}

	public boolean move(ChessPiece n, Point p,boolean test){
		boolean result = false;
		String special = "";

		ChessPiece n2 = null;
		Point p2 = null;
		if(n.type.equals(Pawn.TYPE)){
			//check is en Passant is being performed
			if((n.pos.x-p.x==1)) n2 = this.get(ChessPiece.translate(n.pos,ChessPiece.WEST,1));
			else if((n.pos.x-p.x==-1)) n2 = this.get(ChessPiece.translate(n.pos,ChessPiece.EAST,1));
			if(n2!=null){
				if(n2.equals(enPassant)) {
					p2 = n2.pos;
					pieces.remove(enPassant.pos);
					special = " - En Passant!";
				}else n2=null;
			}
			//check if en Passant is legal next turn
			if((n.pos.y-p.y)*(n.pos.y-p.y)==4) enPassant=n;
			else enPassant=null;
		}else if(n.type.equals(King.TYPE)){
			//check if castle is being performed
			Point dir = null;
			if(p.equals(ChessPiece.translate(n.pos,ChessPiece.EAST,2))) dir=ChessPiece.EAST;
			else if(p.equals(ChessPiece.translate(n.pos,ChessPiece.WEST,2))) dir=ChessPiece.WEST;
			if(dir!=null){
				p2 = p;
				while(this.isPositionLegal(p2)&&(n2=this.get(p2))==null){
					p2 = ChessPiece.translate(p2,dir,1);
				}
				if(n2!=null){
					if(n2.type.equals(Rook.TYPE)&&n2.color==n2.color&&n2.hasMoved==false){
						pieces.remove(n2.pos);
						n2.pos = ChessPiece.translate(p, dir, -1);
						pieces.put(ChessPiece.translate(p, dir, -1), n2);
						n2.hasMoved=true;
						special = " - Castle!";
					}else n2=null;
				}
			}
		}
		ChessPiece nx = pieces.get(p);
		Point px = n.pos;
		pieces.remove(px);
		n.pos = p;
		pieces.put(p, n);
		if(isInCheck(n.color)) result = false;
		else result = true;
		if(result==false||test){
			//return all pieces to or
			if(nx!=null) pieces.put(p, nx);
			else pieces.remove(p);
			n.pos = px;
			pieces.put(px, n);
			if(n2!=null){
				if(n2.type.equals(Pawn.TYPE)) pieces.put(p2, n2);//replace en passant removed pawn
				else if(n2.type.equals(Rook.TYPE)){//return castled rook to original position
					pieces.remove(n2.pos);
					pieces.put(p2, n2);
					n2.pos=p2;
				}
			}
		}else{
			boolean causedCheck = isInCheck(!n.color);
			boolean causedMate = isMate(!n.color);
			if(causedCheck) {
				if(causedMate){
					this.gameStatus = "Checkmate!";
					special+=" - Checkmate!";
				}else{
					special+=" - Check!";
				}
			}else{
				if(causedMate) {
					this.gameStatus = "Stalemate!";
					special+=" - Stalemate!";
				}
			}
			System.out.println(n.letter+" "+getNotation(px)+" "+getNotation(p)+special);
			n.hasMoved=true;
			this.turnCount++;
		}
		return result;
	}

	private boolean isMate(boolean color) {
		boolean result = true;
		for(int i=0;i<ChessBoard.height;i++){
			for(int j=0;j<ChessBoard.width;j++){
				if(isThreatened(new Point(j,i), !color)) return false;
			}
		}
		return result;
	}

	public String getNotation(Point p){
		return ((char) ('A'+p.x))+""+((char)('8'-p.y));
	}

	public ChessPiece get(Point p){
		return pieces.get(p);
	}

	public boolean isPositionLegal(Point p){
		if(p.x<0||p.x>=width||p.y<0||p.y>=height){
			return false;
		}else{
			return true;
		}
	}

	public boolean isThreatened(Point p, boolean color){
		for(ChessPiece n:new ArrayList<ChessPiece>(pieces.values())){
			if(n.color!=color&&n.threatens(p)) return true;
		}
		return false;
	}

	public boolean isInCheck(boolean color){
		boolean result = false;
		for(ChessPiece k:this.kings){
			if(k.color==color) result=isThreatened(k.pos,k.color);
			break;
		}
		return result;
	}

	public ChessboardPanel display(){
		return new ChessboardPanel(this);
	}

	public void select(int x, int y, int scale) {
		Point p = new Point(x/scale,y/scale);
		if(this.selected!=null){
			if(this.selected.pos.equals(p)) this.selected=null;
			else if(this.selected.threatens(p)){
				if(this.selected.pos.equals(p)||!this.isPositionLegal(p)||this.selected.color==(this.turnCount%2==0));
				else this.move(this.selected, p, false);
				this.selected=null;
			}
		}else{
			this.selected = this.get(p);
		}
	}
}
