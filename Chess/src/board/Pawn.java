package board;

import java.awt.Point;

public class Pawn extends ChessPiece {
	private static final char LETTER = 'P';
	public static final Character SYMBOL1 = '\u2659';
	public static final Character SYMBOL2 = '\u265F';
	public static final String TYPE = "pawn";

	public Pawn(Point p,ChessBoard b,boolean c){
		super(p,b,c);
		this.type = Pawn.TYPE;
		this.letter = LETTER;
		if(c) this.symbol=SYMBOL2;
		else this.symbol=SYMBOL1;
	}

	@Override
	public boolean threatens(Point p) {
		boolean result = false;
		Point dir;
		if(this.board.isPositionLegal(p)&&!p.equals(this.pos)){
			if(this.color) dir=NORTH;
			else dir=SOUTH;
			Point p2 = translate(pos,dir,1);
			if(p.equals(p2)) {
				if(board.get(p)==null) result = true;
			}else if(p.equals(translate(p2,EAST,1))||p.equals(translate(p2,WEST,1))){
				if(board.get(p)!=null&&board.get(p).color!=this.color) result = true;
				else{
					Point dir2;
					if(p.equals(translate(p2,EAST,1))) dir2 = EAST;
					else dir2 = WEST;
					ChessPiece n = board.get(translate(pos, dir2, 1));
					if(n!=null&&n.equals(board.enPassant)&&n.color!=this.color) result=true;
				}
			}else if(p.equals(translate(pos,dir,2))){
				if(!this.hasMoved&&board.get(translate(pos,dir,1))==null&&board.get(p)==null) result = true;
			}
		}
		if(result) result=board.move(this, p, true);
		return result;
	}
}


