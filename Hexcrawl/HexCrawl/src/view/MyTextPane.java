package view;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import data.HexData;
import view.infopanels.ChatLinkAction;
import view.infopanels.ChatLinkMouseoverAction;

@SuppressWarnings("serial")
public class MyTextPane extends JTextPane {
	private static final Style DEFAULT = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
	private InfoPanel info;
	private String rawText;

	
	public MyTextPane(InfoPanel info) {
		this.info = info;
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.setCaret(new MyCaret());
		this.setContentType("text/html");
	}
	
	public void setText(String t) {
		this.rawText = t;
		this.writeStringToDocument(t);
	}

	private void writeStringToDocument(String string) {
		StyledDocument doc = this.getStyledDocument();
		try {
			super.setText("<html>");
			//doc.remove(0, doc.getLength());//delete contents
			int curlybrace = string.indexOf("{");
			int closebrace = -1;
			while(curlybrace>-1) {
				doc.insertString(doc.getLength(), string.substring(closebrace+1,curlybrace), DEFAULT);
				closebrace = string.indexOf("}", curlybrace);
				insertLink(string.substring(curlybrace, closebrace+1));
				curlybrace = string.indexOf("{", closebrace);
			}
			doc.insertString(doc.getLength(), string.substring(closebrace+1), DEFAULT);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	private void insertLink(String link) throws BadLocationException {
		StyledDocument doc = this.getStyledDocument();
		Style regularBlue = doc.addStyle("regularBlue", DEFAULT);
		StyleConstants.setForeground(regularBlue, Color.BLUE);
		StyleConstants.setUnderline(regularBlue, true);
		regularBlue.addAttribute("linkact", new ChatLinkAction(link, info));
		regularBlue.addAttribute("linkmouseover", new ChatLinkMouseoverAction(link, this,info));
		String linkText = info.getLinkText(link);
		doc.insertString(doc.getLength(), linkText, regularBlue);
	}

	public String getRawText() {
		return rawText;
	}


	private final class TextFocusListener implements FocusListener {
		int index;
		HexData type;

		private TextFocusListener(int i,HexData type) {
			this.index = i;
			this.type = type;
		}
		public void focusGained(FocusEvent e) {
			info.repaint();
		}
		public void focusLost(FocusEvent e) {
			String text = MyTextPane.this.getRawText();
			Point p = info.getPanel().getSelectedGridPoint();
			String defaultText = info.getDefaultEncounterText(p,index);
			System.out.println("encounter text check: "+text.equals(defaultText));
			if(text==null||"".equals(text)||text.equals(defaultText)) info.getPanel().getRecord().removeEncounter(p,index);
			else info.getPanel().getRecord().putEncounter(p,index, text);
		}
	}
}
