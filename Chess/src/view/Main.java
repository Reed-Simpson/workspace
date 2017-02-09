package view;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import model.ChessBoard;

public class Main {
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Chess");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ChessBoard board = new ChessBoard();
		frame.add(board.display());
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}
