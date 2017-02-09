package control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import model.ChessBoard;
import view.ChessboardPanel;

public class MouseAdapter implements MouseListener {
	public ChessBoard board;
	public ChessboardPanel panel;
	
	public MouseAdapter(ChessBoard board, ChessboardPanel panel){
		this.board = board;
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		board.select(e.getX(),e.getY(),ChessboardPanel.scale);
		panel.repaint();
		if(board.gameStatus!=null) JOptionPane.showMessageDialog(null, board.gameStatus);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
