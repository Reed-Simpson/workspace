package view;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import controllers.DataController;
import data.HexData;
import view.infopanels.TextLinkAction;

@SuppressWarnings("serial")
public class MyTextPane extends JTextPane {
	private static final Style DEFAULT = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
	private InfoPanel info;
	private String rawText;
	private DataController controller;
	private int index;
	private HexData type;
	private HashMap<Interval,Interval> links;


	public MyTextPane(InfoPanel info,int index,HexData type) {
		this.info = info;
		this.controller = info.getPanel().getController();
		this.index = index;
		this.type = type;
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.setCaret(new NoScrollCaret());
		this.setContentType("text/html");
		this.addFocusListener(new TextFocusListener(type));
		TextLinkMouseListener mouseAdapter = new TextLinkMouseListener();
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		//this.getStyledDocument().addDocumentListener(new MyDocumentListener());
		DefaultStyledDocument doc = (DefaultStyledDocument) this.getStyledDocument();
		doc.setDocumentFilter(new MyDocumentFilter());
	}

	public void setText(String t) {
		this.rawText = t;
		super.setText("");
		links = new HashMap<Interval,Interval>();
		this.writeStringToDocument(t);
	}

	public void doPaint() {
		String text = controller.getText(type, info.getPanel().getSelectedGridPoint(), index);
		this.setText(text);
	}

	public void touch() {
		this.setText(rawText);
	}

	private void writeStringToDocument(String string) {
		StyledDocument doc = this.getStyledDocument();
		try {
			int curlybrace = string.indexOf("{");
			int closebrace = -1;
			while(curlybrace>-1) {
				doc.insertString(doc.getLength(), string.substring(closebrace+1,curlybrace), DEFAULT);
				closebrace = string.indexOf("}$", curlybrace)+1;
				if(closebrace!=0) {
					Interval linkInterval = insertLink(string.substring(curlybrace, closebrace+1));
					links.put(linkInterval, new Interval(curlybrace,closebrace+1));
					curlybrace = string.indexOf("{", closebrace);
				}else {
					closebrace = curlybrace-1;
					break;
				}
			}
			doc.insertString(doc.getLength(), string.substring(closebrace+1), DEFAULT);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	private Interval insertLink(String link) throws BadLocationException {
		StyledDocument doc = this.getStyledDocument();
		Style regularBlue = getLinkStyle(link, doc);
		String linkText = getLinkText(link);
		Interval result = new Interval(doc.getLength(),doc.getLength()+linkText.length());
		doc.insertString(doc.getLength(), linkText, regularBlue);
		return result;
	}

	private Style getLinkStyle(String link, StyledDocument doc) {
		Style regularBlue = doc.addStyle("regularBlue", DEFAULT);
		StyleConstants.setForeground(regularBlue, Color.BLUE);
		StyleConstants.setUnderline(regularBlue, true);
		regularBlue.addAttribute("linkact", new TextLinkAction(link, info));
		regularBlue.addAttribute("linkmouseover", new MouseoverAction(link, this,info));
		return regularBlue;
	}

	public String getRawText() {
		return rawText;
	}

	public String getLinkText(String link) {
		Matcher matcher = Pattern.compile("\\{(\\D+):(-?\\d+),(-?\\d+),(\\d+)\\}\\$").matcher(link);
		if(matcher.matches()) {
			link = controller.getLinkText(
					matcher.group(1),
					Integer.valueOf(matcher.group(2)),
					Integer.valueOf(matcher.group(3)),
					Integer.valueOf(matcher.group(4))-1);
		}
		return link;
	}

	private int formattedIndexToRaw(int formatted) {
		int raw = formatted;
		for(Entry<Interval,Interval> e:links.entrySet()) {
			Interval rawInterval = e.getValue();
			Interval formattedInterval = e.getKey();
			if(formattedInterval.getB()<=formatted) raw+=rawInterval.size()-formattedInterval.size();
			else if(formattedInterval.getA()<formatted) raw+=formattedInterval.getA()-formatted+rawInterval.size()-1;
		}
		return raw;
	}
	public void insertRawText(String string, int a) {
		rawText = rawText.substring(0, a)+string+rawText.substring(a);
	}
	public void replaceRawText(String string, int a,int b) {
		rawText = rawText.substring(0, a)+string+rawText.substring(b);
	}

	public void deleteRawText(int a, int b) {
		rawText = rawText.substring(0, a)+rawText.substring(b);
	}

	private String removeLinks(String string) {
		StringBuilder sb = new StringBuilder();
		int curlybrace = string.indexOf("{");
		int closebrace = -1;
		while(curlybrace>-1) {
			sb.append(string.substring(closebrace+1,curlybrace));
			closebrace = string.indexOf("}$", curlybrace)+1;
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
			//System.out.println(getRawText());
		}
		public void focusLost(FocusEvent e) {
			//System.out.println("focus lost");
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
	private class MouseoverAction extends AbstractAction    {
		private String textLink;
		private final JTextPane textPane;
		private InfoPanel info;

		public MouseoverAction(String textLink,JTextPane textPane,InfoPanel info){
			this.textLink = textLink;
			this.textPane = textPane;
			this.info = info;
		}

		protected void execute(){
			Matcher matcher = Pattern.compile("\\{(\\D+):(-?\\d+),(-?\\d+),(\\d+)\\}\\$").matcher(textLink);
			if(matcher.matches()) {
				String tooltipText = info.getToolTipText(
						matcher.group(1),
						Integer.valueOf(matcher.group(2)),
						Integer.valueOf(matcher.group(3)),
						Integer.valueOf(matcher.group(4))-1);
				tooltipText = removeLinks(tooltipText.replaceAll("\r\n", "<br>"));
				textPane.setToolTipText("<html><div style=\"width:300px\">"+tooltipText+"</div>");
			}
		}

		@Override
		public void actionPerformed(ActionEvent e){
			execute();
		}
	}
	private class TextLinkMouseListener implements MouseListener,MouseMotionListener {
		public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e){
			@SuppressWarnings("deprecation")
			Element ele = MyTextPane.this.getStyledDocument().getCharacterElement(MyTextPane.this.viewToModel(e.getPoint()));
			AttributeSet as = ele.getAttributes();
			TextLinkAction fla = (TextLinkAction)as.getAttribute("linkact");
			if(fla != null){
				fla.execute();
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {}
		@Override
		public void mouseMoved(MouseEvent e) {
			@SuppressWarnings("deprecation")
			Element ele = MyTextPane.this.getStyledDocument().getCharacterElement(MyTextPane.this.viewToModel(e.getPoint()));
			AttributeSet as = ele.getAttributes();
			MouseoverAction fla = (MouseoverAction)as.getAttribute("linkmouseover");
			if(fla != null){
				fla.execute();
			}else {

			}
		}
	}
	private class Interval {
		int a;
		int b;
		public Interval(int a,int b) {
			this.a=a;
			this.b=b;
		}
		public int getA() {
			return a;
		}
		public int getB() {
			return b;
		}
		public int size() {
			return b-a;
		}
		public String toString() {
			return "["+a+"-"+b+"]";
		}
	}
	private class MyDocumentFilter extends DocumentFilter {

		@Override
		public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			if(info.isChangeSelected()) {
				int a = formattedIndexToRaw(offset);
				int b = formattedIndexToRaw(offset+length);
				//System.out.println("a:"+a+"-b:"+b);
				deleteRawText(a,b);
				//System.out.println(rawText);
				writeStringToDocument(fb, rawText);
			}else {
				super.remove(fb, offset, length); // Allow the removal
			}
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			//System.out.println("replace");
			if(info.isChangeSelected()) {
				int openbracket = rawText.lastIndexOf("{");
				int closebracket = rawText.lastIndexOf("}$");
				if(text.endsWith("}")&&openbracket>closebracket) {
					String endswith = rawText.substring(openbracket);
					if(endswith.matches("\\{(\\D+):(-?\\d+),(-?\\d+),(\\d+)")) {
						text+="$";
					}
				}
				int a = formattedIndexToRaw(offset);
				int b = formattedIndexToRaw(offset+length);
				System.out.println("a:"+a+"-b:"+b);
				replaceRawText(text,a,b);
				System.out.println(text);
				writeStringToDocument(fb, rawText);
			}else {
				super.replace(fb, offset, length, text, attrs); // Allow the replacement with modified text
			}
		}


		private void writeStringToDocument(FilterBypass fb, String string) throws BadLocationException {
			StyledDocument doc = MyTextPane.this.getStyledDocument();
			super.remove(fb, 0, doc.getLength());
			links = new HashMap<Interval,Interval>();
			try {
				int curlybrace = string.indexOf("{");
				int closebrace = -1;
				while(curlybrace>-1) {
					super.insertString(fb, doc.getLength(), string.substring(closebrace+1,curlybrace), DEFAULT);
					closebrace = string.indexOf("}$", curlybrace)+1;
					if(closebrace!=0) {
						Interval linkInterval = insertLink(fb,string.substring(curlybrace, closebrace+1));
						links.put(linkInterval, new Interval(curlybrace,closebrace+1));
						curlybrace = string.indexOf("{", closebrace);
					}else {
						closebrace = curlybrace-1;
						break;
					}
				}
				super.insertString(fb, doc.getLength(), string.substring(closebrace+1), DEFAULT);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}

		private Interval insertLink(FilterBypass fb, String link) throws BadLocationException {
			StyledDocument doc = MyTextPane.this.getStyledDocument();
			Style regularBlue = getLinkStyle(link, doc);
			String linkText = getLinkText(link);
			Interval result = new Interval(doc.getLength(),doc.getLength()+linkText.length());
			super.insertString(fb, doc.getLength(), linkText, regularBlue);
			return result;
		}
	}
}
