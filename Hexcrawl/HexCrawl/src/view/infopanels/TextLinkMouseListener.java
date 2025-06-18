package view.infopanels;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;


public class TextLinkMouseListener implements MouseListener,MouseMotionListener {
	private final JTextPane textPane;

	public TextLinkMouseListener(JTextPane textPane) {
		this.textPane = textPane;
	}

	public void mouseReleased(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseClicked(MouseEvent e){
		@SuppressWarnings("deprecation")
		Element ele = textPane.getStyledDocument().getCharacterElement(textPane.viewToModel(e.getPoint()));
		AttributeSet as = ele.getAttributes();
		ChatLinkAction fla = (ChatLinkAction)as.getAttribute("linkact");
		if(fla != null){
			fla.execute();
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		@SuppressWarnings("deprecation")
		Element ele = textPane.getStyledDocument().getCharacterElement(textPane.viewToModel(e.getPoint()));
		AttributeSet as = ele.getAttributes();
		ChatLinkMouseoverAction fla = (ChatLinkMouseoverAction)as.getAttribute("linkmouseover");
		if(fla != null){
			fla.execute();
		}else {
			
		}
	}


}