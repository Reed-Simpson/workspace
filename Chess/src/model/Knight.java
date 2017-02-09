package model;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class Knight extends ChessPiece {
	private static final char LETTER = 'N';
	public static final Character SYMBOL1 = '\u2658';
	public static final Character SYMBOL2 = '\u265E';
	public static final String TYPE = "knight";
	public static final Point NNE = new Point(1,2);
	public static final Point ENE = new Point(2,1);
	public static final Point ESE = new Point(2,-1);
	public static final Point SSE = new Point(1,-2);
	public static final Point SSW = new Point(-1,-2);
	public static final Point WSW = new Point(-2,-1);
	public static final Point WNW = new Point(-2,1);
	public static final Point NNW = new Point(-1,2);
	private static List<Point> POINTS = Arrays.asList(NNE,ENE,ESE,SSE,SSW,WSW,WNW,NNW);

	public Knight(Point p,ChessBoard b,boolean c){
		super(p,b,c);
		this.type = Knight.TYPE;
		this.letter = LETTER;
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
		    }
		}
		if(result) result=board.move(this, p, true);
		return result;

	}

}
