package view.infopanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import data.encounters.Encounter;
import view.InfoPanel;

@SuppressWarnings("serial")
public class EncountersPanel extends AbstractPanel{
	public static final int ENCOUNTERCOUNT = 20;
	private ArrayList<JTextPane> encounterTexts;
	private int selectedEncounter;

	public EncountersPanel(InfoPanel infopanel) {
		super(infopanel);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		encounterTexts = new ArrayList<JTextPane>();
		for(int i=0;i<EncountersPanel.ENCOUNTERCOUNT;i++) {
			this.add(new JLabel("~~~~~ Encounter #"+(i+1)+" ~~~~~"));
			JTextPane encounteri = new JTextPane();
			//			npci.setLineWrap(true);
			//			npci.setWrapStyleWord(true);
			encounteri.setMaximumSize(new Dimension(WIDTH-20,9999));
			encounteri.addFocusListener(new EncounterFocusListener(encounteri,i));
			encounteri.addMouseListener(new TextLinkMouseListener(encounteri));
			encounteri.setAlignmentX(LEFT_ALIGNMENT);
			this.add(encounteri);
			encounterTexts.add(encounteri);
		}
	}

	@Override
	public void doPaint() {
		Point pos;
		if(info.getPanel().isShowDistance()) pos = info.getPanel().getMouseoverGridPoint();
		else pos = info.getPanel().getSelectedGridPoint();

		for(int i = 0;i<this.encounterTexts.size();i++) {
			JTextPane pane = this.encounterTexts.get(i);
			if(i==selectedEncounter) {
				pane.setBackground(Color.YELLOW);
			}else {
				pane.setBackground(Color.WHITE);
			}
			writeStringToDocument(getEncounterText(pos,i), pane);
		}
		this.encounterTexts.get(selectedEncounter).setCaretPosition(0);
	}

	private String getEncounterText(Point pos,int index) {
		String encounterText = info.getPanel().getRecord().getEncounter(pos,index);
		if(encounterText==null) encounterText = getDefaultEncounterText(pos,index);
		return encounterText;
	}
	private String getDefaultEncounterText(Point pos,int index) {
		Encounter n = info.getPanel().getController().getEncounters().getEncounter(index, pos);
		return n.toString(index+1);
	}


	private final class EncounterFocusListener implements FocusListener {
		private final JTextPane encounteri;
		int index;

		private EncounterFocusListener(JTextPane encounteri, int i) {
			this.encounteri = encounteri;
			this.index = i;
		}
		public void focusGained(FocusEvent e) {
			selectedEncounter = index;
		}
		public void focusLost(FocusEvent e) {
			String text = encounteri.getText();
			Point p = info.getPanel().getSelectedGridPoint();
			String defaultText = getDefaultEncounterText(p,index);
			if(text==null||"".equals(text)||text.equals(defaultText)) info.getPanel().getRecord().removeNPC(p,index);
			else info.getPanel().getRecord().putNPC(p,index, text);
		}
	}


	public void selectTab(int index) {
		encounterTexts.get(index).setCaretPosition(0);
		selectedEncounter=index;
	}

}