package view.infopanels;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import data.Reference;
import view.InfoPanel;

@SuppressWarnings("serial")
public abstract class AbstractPanel extends JPanel{
	private static final Style DEFAULT = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
	InfoPanel info;


	public AbstractPanel(InfoPanel info) {
		this.info=info;
	}
	
	public abstract void doPaint();

	protected void writeStringToDocument(String string, JTextPane pane) {
		StyledDocument doc = pane.getStyledDocument();
		try {
			doc.remove(0, doc.getLength());//delete contents
			int curlybrace = string.indexOf("{");
			int closebrace = -1;
			doc.insertString(doc.getLength(), "test", DEFAULT);
			doc.insertString(doc.getLength(), "\r\ntest", DEFAULT);
			while(curlybrace>-1) {
				doc.insertString(doc.getLength(), string.substring(closebrace+1,curlybrace), DEFAULT);
				closebrace = string.indexOf("}", curlybrace);
				insertLink(pane,string.substring(curlybrace, closebrace+1));
				curlybrace = string.indexOf("{", closebrace);
			}
			doc.insertString(doc.getLength(), string.substring(closebrace+1), DEFAULT);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	private void insertLink(JTextPane pane, String textLink) throws BadLocationException {
		StyledDocument doc = pane.getStyledDocument();
		Style regularBlue = doc.addStyle("regularBlue", DEFAULT);
		StyleConstants.setForeground(regularBlue, Color.BLUE);
		StyleConstants.setUnderline(regularBlue, true);
		regularBlue.addAttribute("linkact", new TextLinkAction(new Reference(textLink), info));
		doc.insertString(doc.getLength(), textLink, regularBlue);

	}
}
