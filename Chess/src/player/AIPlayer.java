package player;

import board.ChessBoard;

public class AIPlayer implements ChessPlayer {
	int smartness = 10;

	@Override
	public ChessBoard makeAMove(ChessBoard board) {
		
		ChessBoard testBoard = board.clone();
		
		return null;
	}

	@Override
	public boolean isHuman() {
		return false;
	}

}
