package view;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
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
import data.Reference;
import util.Util;
import view.infopanels.TextLinkAction;

@SuppressWarnings("serial")
public class MyTextPane extends JTextPane {
	private static final Style DEFAULT = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
	private final Style basic;
	private InfoPanel info;
	private String rawText;
	private DataController controller;
	private int index;
	private HexData type;
	private HashMap<Interval,Interval> links;
	private Reference ref;

	public MyTextPane(InfoPanel info,int index,HexData type) {
		this.info = info;
		this.controller = info.getPanel().getController();
		this.index = index;
		this.type = type;
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.setCaret(new NoScrollCaret());
		this.setContentType("text/html");
		this.setText("");
		this.addFocusListener(new TextFocusListener(type));
		TextpaneMouseListener mouseAdapter = new TextpaneMouseListener();
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		DefaultStyledDocument doc = (DefaultStyledDocument) this.getStyledDocument();
		this.basic = getDefaultStyle(doc);
		doc.setDocumentFilter(new MyDocumentFilter());
	}
	public MyTextPane(InfoPanel info,HexData type) {
		this(info,0,type);
	}

	public void setText(String t) {
		this.rawText = t;
		super.setText("");
		links = new HashMap<Interval,Interval>();
		this.writeStringToDocument(t);
	}
	public void doPaint() {
		String text = controller.getText(getType(), getPoint(), getIndex());
		this.setText(text);
	}

	private void writeStringToDocument(String string) {
		StyledDocument doc = this.getStyledDocument();
		try {
			Matcher matcher = Reference.PATTERN.matcher(string);
			int closebrace = -1;
			while(matcher.find()) {
				doc.insertString(doc.getLength(), string.substring(closebrace+1,matcher.start()), basic);
				Interval linkInterval = insertLink(string.substring(matcher.start(), matcher.end()));
				links.put(linkInterval, new Interval(matcher.start(),matcher.end()));
				closebrace = matcher.end()-1;
			}
			doc.insertString(doc.getLength(), string.substring(closebrace+1), basic);

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
	
	private Style getDefaultStyle(StyledDocument doc) {
		Style basic = doc.addStyle("basic", DEFAULT);
		basic.addAttribute("linkmouseover", new MouseoverAction(null, this,info));
		return basic;
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
		Matcher matcher = Reference.PATTERN.matcher(link);
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
		Matcher matcher = Reference.PATTERN.matcher(string);
		int closebrace = -1;
		while(matcher.find()) {
			sb.append(string.substring(closebrace+1,matcher.start()));
			closebrace = matcher.end()-1;
			String link = string.substring(matcher.start(), matcher.end());
			sb.append(getLinkText(link));
		}
		sb.append(string.substring(closebrace+1));
		return sb.toString();
	}

	private Point getPoint() {
		if(this.ref!=null) return Util.denormalizePos(this.ref.getPoint(),controller.getRecord().getZero());
		else return info.getPanel().getSelectedGridPoint();
	}
	private HexData getType() {
		if(this.ref!=null) return this.ref.getType();
		else return type;
	}
	private int getIndex() {
		if(this.ref!=null) return this.ref.getIndex();
		else return index;
	}
	public void setRef(Reference reference) {
		this.ref = reference;
	}


	private final class TextFocusListener implements FocusListener {

		private TextFocusListener(HexData type) {
		}
		public void focusGained(FocusEvent e) {
		}
		public void focusLost(FocusEvent e) {
			String text = MyTextPane.this.getRawText();
			controller.updateData(getType(), text, getPoint(), getIndex());
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
			if(textLink==null) {
				textPane.setToolTipText(null);
			} else {
				Matcher matcher = Pattern.compile("\\{(\\D+):(-?\\d+),(-?\\d+),(\\d+)\\}\\$").matcher(textLink);
				if(matcher.matches()) {
					HexData type = HexData.get(matcher.group(1));
					String tooltipText = info.getToolTipText(
							type,
							Integer.valueOf(matcher.group(2)),
							Integer.valueOf(matcher.group(3)),
							Integer.valueOf(matcher.group(4))-1);
					tooltipText = removeLinks(tooltipText.replaceAll("\r\n", "<br>"));
					textPane.setToolTipText("<html><div style=\"width:300px\">"+tooltipText+"</div>");
				}
			}
		}

		@Override
		public void actionPerformed(ActionEvent e){
			execute();
		}
	}
	private class TextpaneMouseListener implements MouseListener,MouseMotionListener {
		public void mouseReleased(MouseEvent e) {
			if(e.isPopupTrigger()) {
				doPopupMenu(e);
			}
		}
		public void mousePressed(MouseEvent e) {
			if(e.isPopupTrigger()) {
				doPopupMenu(e);
			}
		}
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

		private void doPopupMenu(MouseEvent e) {
			JPopupMenu menu = new JPopupMenu();
			if(HexData.CHARACTER.equals(type)) {
				if("None".equals(getRawText())) return;
				JMenuItem remove = new JMenuItem("Remove From Characters List");
				remove.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						info.removeCharacter(index);
					}
				});
				menu.add(remove);
			}else {
				JMenuItem add = new JMenuItem("Add To Characters List");
				add.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						info.addCharacter(type,getPoint(),index);
					}
				});
				menu.add(add);
			}
			menu.show(e.getComponent(), e.getX(), e.getY());
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
				deleteRawText(a,b);
				writeStringToDocument(fb, rawText);
			}else {
				super.remove(fb, offset, length); // Allow the removal
			}
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			if(info.isChangeSelected()) {
				String substring = rawText.substring(0, offset);
				int openbracket = substring.lastIndexOf("{");
				int closebracket = substring.lastIndexOf("}$");
				if(text.endsWith("}")&&openbracket>closebracket) {
					String endswith = substring.substring(openbracket);
					if(endswith.matches(Reference.PARTIAL)) {
						text+="$";
					}
				}
				int a = formattedIndexToRaw(offset);
				int b = formattedIndexToRaw(offset+length);
				replaceRawText(text,a,b);
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
				Matcher matcher = Reference.PATTERN.matcher(string);
				int closebrace = -1;
				while(matcher.find()) {
					super.insertString(fb, doc.getLength(), string.substring(closebrace+1,matcher.start()), basic);
					closebrace = matcher.end()-1;
					Interval linkInterval = insertLink(fb,string.substring(matcher.start(), matcher.end()));
					links.put(linkInterval, new Interval(matcher.start(), matcher.end()));
				}
				super.insertString(fb, doc.getLength(), string.substring(closebrace+1), basic);
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
