package board;

import java.awt.Point;

public class Rook extends ChessPiece {
	private static final char LETTER = 'R';
	public static final Character SYMBOL1 = '\u2656';
	public static final Character SYMBOL2 = '\u265C';
	public static final String TYPE = "rook";

	public Rook(Point p,ChessBoard b,boolean c){
		super(p,b,c);
		this.type = Rook.TYPE;
		this.letter = LETTER;
		if(c) this.symbol=SYMBOL2;
		else this.symbol=SYMBOL1;
	}

	@Override
	public boolean threatens(Point p) {
		boolean result = false;
		Point dir = null;
		if(this.board.isPositionLegal(p)&&!p.equals(this.pos)){
			if(p.x==pos.x){
				if(p.y>pos.y) dir=NORTH;
				else dir=SOUTH;
				result = true;
			}else if(p.y==pos.y){
				if(p.x>pos.x) dir=EAST;
				else dir=WEST;
				result = true;
			}
			if(result){
				Point p2 = translate(pos,dir,1);
				while(!p2.equals(p)&&result){
					if(board.get(p2)!=null) result=false;
					p2.translate(dir.x, dir.y);
				}
				if(result&&board.get(p)!=null&&board.get(p).color==this.color) result= false;
			}
		}
		if(result) result=board.move(this, p, true);
		return result;
	}

}
