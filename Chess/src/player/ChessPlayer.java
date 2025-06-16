package player;

import board.ChessBoard;

public interface ChessPlayer {
	
	public ChessBoard makeAMove(ChessBoard board);
	
	public boolean isHuman();

}
