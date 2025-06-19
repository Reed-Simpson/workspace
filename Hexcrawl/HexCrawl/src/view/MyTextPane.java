package view;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import controllers.DataController;
import data.HexData;
import view.infopanels.ChatLinkAction;
import view.infopanels.ChatLinkMouseoverAction;
import view.infopanels.TextLinkMouseListener;

@SuppressWarnings("serial")
public class MyTextPane extends JTextPane {
	private static final Style DEFAULT = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
	private InfoPanel info;
	private String rawText;
	private DataController controller;
	private int index;
	HexData type;

	
	public MyTextPane(InfoPanel info,int index,HexData type) {
		this.info = info;
		this.controller = info.getPanel().getController();
		this.index = index;
		this.type = type;
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.setCaret(new NoScrollCaret());
		this.setContentType("text/html");
		this.addFocusListener(new TextFocusListener(type));
		TextLinkMouseListener mouseAdapter = new TextLinkMouseListener(this);
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
	}
	
	public void setText(String t) {
		this.rawText = t;
		super.setText("");
		this.writeStringToDocument(t);
	}
	
	public void doPaint() {
		String text = controller.getText(type, info.getPanel().getSelectedGridPoint(), index);
		this.setText(text);
	}

	private void writeStringToDocument(String string) {
		StyledDocument doc = this.getStyledDocument();
		try {
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

	public String getLinkText(String link) {
		Matcher matcher = Pattern.compile("\\{(\\D+):(-?\\d+),(-?\\d+),(\\d+)\\}").matcher(link);
		if(matcher.matches()) {
			if(Integer.valueOf(matcher.group(4))==0) {
				System.out.println(link);
			}
			link = controller.getLinkText(
					matcher.group(1),
					Integer.valueOf(matcher.group(2)),
					Integer.valueOf(matcher.group(3)),
					Integer.valueOf(matcher.group(4))-1);
		}
		return link;
	}

	private String removeLinks(String string) {
		StringBuilder sb = new StringBuilder();
		int curlybrace = string.indexOf("{");
		int closebrace = -1;
		while(curlybrace>-1) {
			sb.append(string.substring(closebrace+1,curlybrace));
			closebrace = string.indexOf("}", curlybrace);
			String link = string.substring(curlybrace, closebrace+1);
			sb.append(getLinkText(link));
			curlybrace = string.indexOf("{", closebrace);
		}
		sb.append(string.substring(closebrace+1));
		return sb.toString();
	}


	private final class TextFocusListener implements FocusListener {

		private TextFocusListener(HexData type) {
		}
		public void focusGained(FocusEvent e) {
			System.out.println("focus gained");
		}
		public void focusLost(FocusEvent e) {
			System.out.println("focus lost");
			String text = MyTextPane.this.getRawText();
			Point p = info.getPanel().getSelectedGridPoint();
			controller.updateData(type, text, p, index);
		}
	}
	
	private class NoScrollCaret extends DefaultCaret {
		@Override
		protected void adjustVisibility(Rectangle nloc) {

		}
	}
}
