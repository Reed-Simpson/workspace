package view.infopanels;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;

import view.InfoPanel;


public class TextLinkMouseListener implements MouseListener {
	private final JTextPane encounteri;
	private InfoPanel panel;

	public TextLinkMouseListener(JTextPane encounteri,InfoPanel info) {
		this.encounteri = encounteri;
		this.panel = info;
	}

	public void mouseReleased(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseClicked(MouseEvent e){
		Element ele = encounteri.getStyledDocument().getCharacterElement(encounteri.viewToModel(e.getPoint()));
		AttributeSet as = ele.getAttributes();
		ChatLinkAction fla = (ChatLinkAction)as.getAttribute("linkact");
		if(fla != null){
			fla.execute();
		}
	}
	
	public ChatLinkAction getAction(String text) {
		return new ChatLinkAction(text, panel);
	}


}