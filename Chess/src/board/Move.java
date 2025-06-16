package board;

import java.awt.Point;
import java.util.List;

public class Move {
	Point start;
	Point end;
	public Move(Point p1, Point p2) {
		this.start=p1;
		this.end=p2;
	}
	
	public String toString() {
		return getNotation(start)+"->"+getNotation(end);
	}
	
	public static String printMoveList(List<Move> moves) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(Move m:moves) {
			sb.append(m.toString()).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		return sb.toString();
	}
	

	public static String getNotation(Point p){
		return ((char) ('A'+p.x))+""+((char)('8'-p.y));
	}
}
