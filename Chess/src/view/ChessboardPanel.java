package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

import board.ChessBoard;
import board.ChessPiece;
import user.MouseAdapter;

public class ChessboardPanel extends JPanel {
	private static final long serialVersionUID = 7251905047371109577L;
	public static final int scale = 50;
	public static final Color COLOR1 = Color.WHITE;
	public static final Color COLOR2 = Color.BLACK;
	public static final Color BGCOLOR1 = Color.WHITE;
	public static final Color BGCOLOR2 = Color.GRAY;
	public static final Color HIGHLIGHT1 = Color.YELLOW;
	public static final Color HIGHLIGHT2 = Color.GREEN;
	public static final Color OUTLINE = Color.DARK_GRAY;
	public static final Font DEFAULTFONT = new Font("Monospaced", Font.BOLD, scale*4/5);
	

	public ChessBoard board;

	public ChessboardPanel(ChessBoard board){
		this.board=board;
		this.setPreferredSize(new Dimension(scale*ChessBoard.width+1, scale*ChessBoard.height+1));
		this.addMouseListener(new MouseAdapter(board,this));
		this.repaint();
	}

	@Override
	public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(DEFAULTFONT);
        for(int i=0;i<=ChessBoard.width;i++){
        	g2.drawLine(i*scale, 0, i*scale, ChessBoard.height*scale);
        }
        for(int i=0;i<=ChessBoard.height;i++){
        	g2.drawLine(0, i*scale, ChessBoard.width*scale, i*scale);
        }
        for(int i=0;i<ChessBoard.height;i++){
        	for(int j=0;j<ChessBoard.width;j++){
        		Point t = new Point(j,i);
        		if(this.board.selected!=null&&t.equals(this.board.selected.pos)) g2.setColor(HIGHLIGHT1);
        		else if(this.board.selected!=null&&this.board.selected.threatens(t)) g2.setColor(HIGHLIGHT2);
        		else if((i+j)%2==0) g2.setColor(BGCOLOR1);
        		else g2.setColor(BGCOLOR2);
        		ChessPiece p = board.get(t);
        		g2.fillRect(j*scale+1, i*scale+1, scale-1, scale-1);
        		if(p!=null){
        			int x=j*scale+scale/12;
        			int y=i*scale+scale*16/20;
        			if(p.color) g2.setColor(COLOR2);
        			else g2.setColor(COLOR1);
        			drawStringWithOutline(g2, p.symbol, x, y);
        		}
        	}
        }
	}
	
	public static void drawStringWithOutline(Graphics2D g2,char ch,int x,int y){
		Color c = g2.getColor();
		g2.setColor(OUTLINE);
		g2.drawString(String.valueOf(ch), x+1, y);
		g2.drawString(String.valueOf(ch), x, y+1);
		g2.drawString(String.valueOf(ch), x-1, y);
		g2.drawString(String.valueOf(ch), x, y-1);
		g2.setColor(c);
		g2.drawString(String.valueOf(ch), x, y);
	}


}
