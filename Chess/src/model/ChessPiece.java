package model;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class ChessPiece {
	public static final Point NORTH = new Point(0,1);
	public static final Point NORTHEAST = new Point(1,1);
	public static final Point EAST = new Point(1,0);
	public static final Point SOUTHEAST = new Point(1,-1);
	public static final Point SOUTH = new Point(0,-1);
	public static final Point SOUTHWEST = new Point(-1,-1);
	public static final Point WEST = new Point(-1,0);
	public static final Point NORTHWEST = new Point(-1,1);
	public static List<ChessPiece> startingPieces;
	public Point pos;
	public ChessBoard board;
	public boolean color;
	public String type;
	public boolean hasMoved = false;
	public char letter;
	public char symbol;
	
	public abstract boolean threatens(Point p);
	
	public ChessPiece(Point p,ChessBoard b,boolean c){
		this.pos = p;
		this.board = b;
		this.color = c;
		if(board!=null) board.pieces.put(p, this);
	}
	
	public static ChessPiece create(Point p,ChessBoard b,boolean c,String type){
		if(type.equals(Pawn.TYPE)){
			return new Pawn(p,b,c);
		}else if(type.equals(Rook.TYPE)){
			return new Rook(p,b,c);
		}else if(type.equals(Knight.TYPE)){
			return new Knight(p,b,c);
		}else if(type.equals(Bishop.TYPE)){
			return new Bishop(p,b,c);
		}else if(type.equals(Queen.TYPE)){
			return new Queen(p,b,c);
		}else if(type.equals(King.TYPE)){
			return new King(p,b,c);
		}
		return null;
	}
	
	public static Point translate(Point p1, Point p2, int x){
		return new Point(p1.x+x*p2.x,p1.y+x*p2.y);
	}
	
	public static List<ChessPiece> getStartingPieces(){
		if(startingPieces==null){
			startingPieces = new ArrayList<ChessPiece>();
			startingPieces.add(ChessPiece.create(new Point(0,0), null, true, Rook.TYPE));
			startingPieces.add(ChessPiece.create(new Point(1,0), null, true, Knight.TYPE));
			startingPieces.add(ChessPiece.create(new Point(2,0), null, true, Bishop.TYPE));
			startingPieces.add(ChessPiece.create(new Point(3,0), null, true, Queen.TYPE));
			startingPieces.add(ChessPiece.create(new Point(4,0), null, true, King.TYPE));
			startingPieces.add(ChessPiece.create(new Point(5,0), null, true, Bishop.TYPE));
			startingPieces.add(ChessPiece.create(new Point(6,0), null, true, Knight.TYPE));
			startingPieces.add(ChessPiece.create(new Point(7,0), null, true, Rook.TYPE));
			startingPieces.add(ChessPiece.create(new Point(0,1), null, true, Pawn.TYPE));
			startingPieces.add(ChessPiece.create(new Point(1,1), null, true, Pawn.TYPE));
			startingPieces.add(ChessPiece.create(new Point(2,1), null, true, Pawn.TYPE));
			startingPieces.add(ChessPiece.create(new Point(3,1), null, true, Pawn.TYPE));
			startingPieces.add(ChessPiece.create(new Point(4,1), null, true, Pawn.TYPE));
			startingPieces.add(ChessPiece.create(new Point(5,1), null, true, Pawn.TYPE));
			startingPieces.add(ChessPiece.create(new Point(6,1), null, true, Pawn.TYPE));
			startingPieces.add(ChessPiece.create(new Point(7,1), null, true, Pawn.TYPE));
			startingPieces.add(ChessPiece.create(new Point(0,7), null, false, Rook.TYPE));
			startingPieces.add(ChessPiece.create(new Point(1,7), null, false, Knight.TYPE));
			startingPieces.add(ChessPiece.create(new Point(2,7), null, false, Bishop.TYPE));
			startingPieces.add(ChessPiece.create(new Point(3,7), null, false, Queen.TYPE));
			startingPieces.add(ChessPiece.create(new Point(4,7), null, false, King.TYPE));
			startingPieces.add(ChessPiece.create(new Point(5,7), null, false, Bishop.TYPE));
			startingPieces.add(ChessPiece.create(new Point(6,7), null, false, Knight.TYPE));
			startingPieces.add(ChessPiece.create(new Point(7,7), null, false, Rook.TYPE));
			startingPieces.add(ChessPiece.create(new Point(0,6), null, false, Pawn.TYPE));
			startingPieces.add(ChessPiece.create(new Point(1,6), null, false, Pawn.TYPE));
			startingPieces.add(ChessPiece.create(new Point(2,6), null, false, Pawn.TYPE));
			startingPieces.add(ChessPiece.create(new Point(3,6), null, false, Pawn.TYPE));
			startingPieces.add(ChessPiece.create(new Point(4,6), null, false, Pawn.TYPE));
			startingPieces.add(ChessPiece.create(new Point(5,6), null, false, Pawn.TYPE));
			startingPieces.add(ChessPiece.create(new Point(6,6), null, false, Pawn.TYPE));
			startingPieces.add(ChessPiece.create(new Point(7,6), null, false, Pawn.TYPE));
		}
		return startingPieces;
	}
	

}
