package model;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class King extends ChessPiece {
	public static final char LETTER = 'K';
	public static final Character SYMBOL1 = '\u2654';
	public static final Character SYMBOL2 = '\u265A';
	public static final String TYPE = "king";
	public static List<Point> POINTS = Arrays.asList(NORTH,NORTHEAST,EAST,SOUTHEAST,SOUTH,SOUTHWEST,WEST,NORTHWEST);

	public King(Point p,ChessBoard b,boolean c){
		super(p,b,c);
		this.type = King.TYPE;
		this.letter = King.LETTER;
		if(c) this.symbol=SYMBOL2;
		else this.symbol=SYMBOL1;
	}

	@Override
	public boolean threatens(Point p) {
		boolean result = false;
		Point dp = translate(p,pos,-1);
		if(this.board.isPositionLegal(p)&&!p.equals(this.pos)){
		    if(POINTS.contains(dp)){
				if(board.get(p)==null||board.get(p).color!=this.color) result=true;
		    }else if(this.hasMoved==false){
		    	Point dir = null;
		    	if(p.equals(translate(pos,EAST,2))) dir=EAST;
		    	else if(p.equals(translate(pos,WEST,2))) dir=WEST;
		    	if(dir!=null){
		    		ChessPiece n = null;
		    		while(board.isPositionLegal(p)&&(n=board.get(p))==null){
		    			p = translate(p,dir,1);
		    		}
		    		if(n!=null&&n.type.equals(Rook.TYPE)&&n.color==this.color&&n.hasMoved==false){
		    			if(!board.isInCheck(this.color)&&this.threatens(translate(pos,dir,1))) result=true;
		    		}
		    	}
		    }
		}
		if(result) result=board.move(this, p, true);
		return result;

	}

}
