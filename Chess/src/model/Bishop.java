package model;

import java.awt.Point;

public class Bishop extends ChessPiece {
	private static final char LETTER = 'B';
	public static final Character SYMBOL1 = '\u2657';
	public static final Character SYMBOL2 = '\u265D';
	public static String TYPE = "bishop";

	public Bishop(Point p,ChessBoard b,boolean c){
		super(p,b,c);
		this.type = Bishop.TYPE;
		this.letter = LETTER;
		if(c) this.symbol=SYMBOL2;
		else this.symbol=SYMBOL1;
	}

	@Override
	public boolean threatens(Point p) {
		boolean result = false;
		Point dir = null;
		Point dp = translate(p,pos,-1);
		if(this.board.isPositionLegal(p)&&!p.equals(this.pos)){
			if(dp.x==dp.y){
				if(dp.x>0) dir=NORTHEAST;
				else dir=SOUTHWEST;
				result = true;
			}else if(dp.x==dp.y*-1){
				if(dp.x>0) dir=SOUTHEAST;
				else dir=NORTHWEST;
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
