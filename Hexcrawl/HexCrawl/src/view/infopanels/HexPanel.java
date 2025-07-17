package view.infopanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import data.HexData;
import data.altitude.AltitudeModel;
import data.population.PopulationModel;
import data.precipitation.PrecipitationModel;
import view.HighlightColor;
import view.InfoPanel;
import view.MapPanel;
import view.MyTextPane;

@SuppressWarnings("serial")
public class HexPanel extends JPanel{
	public final int NPC_TAB_INDEX;
	public final int LOCATION_TAB_INDEX;
	public final int ENCOUNTER_TAB_INDEX;
	public final int MISSIONS_TAB_INDEX;

	private InfoPanel info;
	private MapPanel panel;
	private HexPanelGeneralStatPanel hexGeneralPanel;
	private DemographicsPanel demographicsPanel;
	private JTabbedPane detailsTabs;
	
	private ArrayList<MyTextPane> npcTexts;
	private ArrayList<MyTextPane> proprietorTexts;
	private ArrayList<MyTextPane> poiTexts;
	private ArrayList<MyTextPane> dEntranceTexts;
	private MyTextPane hexNote1;
	private ArrayList<MyTextPane> encounterTexts;
	private JPanel encounterPanel;
	private ArrayList<MyTextPane> missionsTexts;
	private JPanel missionsPanel;
	
	private int selectedEncounter;
	private int selectedMission;
	private int selectedNPC;
	private int selectedPOI;
	private int selectedDungeon;
	private boolean changeSelected;

	public HexPanel(InfoPanel info) {
		this.info = info;
		this.panel = info.getPanel();
		this.setPreferredSize(new Dimension(InfoPanel.INFOPANELWIDTH,InfoPanel.INFOPANELWIDTH));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


		hexGeneralPanel = new HexPanelGeneralStatPanel(info.getPanel().getController(),info.getPanel());
		this.add(hexGeneralPanel);

		demographicsPanel = new DemographicsPanel(info.getPanel());
		this.add(demographicsPanel);

		detailsTabs = new JTabbedPane();
		detailsTabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		JPanel npcPanel = new JPanel();
		npcPanel.setLayout(new BoxLayout(npcPanel, BoxLayout.Y_AXIS));
		npcTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<InfoPanel.NPCCOUNT;i++) {
			npcPanel.add(new JLabel("~~~~~ NPC #"+(i+1)+" ~~~~~"));
			MyTextPane npci = new MyTextPane(info, i, HexData.NPC);
			npci.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
			npcPanel.add(npci);
			npcTexts.add(npci);
		}
		proprietorTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<InfoPanel.POICOUNT;i++) {
			npcPanel.add(new JLabel("~~~~~ Proprietor #"+(i+1)+" ~~~~~"));
			MyTextPane npci = new MyTextPane(info, i, HexData.PROPRIETOR);
			npci.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
			npcPanel.add(npci);
			proprietorTexts.add(npci);
		}
		JScrollPane npcScrollPane = new JScrollPane(npcPanel);
		npcScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		detailsTabs.addTab("NPCs", npcScrollPane);
		this.NPC_TAB_INDEX = detailsTabs.getTabCount()-1;


		JPanel poiPanel = new JPanel();
		poiPanel.setLayout(new BoxLayout(poiPanel, BoxLayout.Y_AXIS));
		poiTexts = new ArrayList<MyTextPane>();
		poiPanel.add(new JLabel("~~~~~ Inn ~~~~~"));
		MyTextPane inn = new MyTextPane(info, 0, HexData.LOCATION);
		inn.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
		poiPanel.add(inn);
		poiTexts.add(inn);
		for(int i=1;i<InfoPanel.POICOUNT;i++) {
			poiPanel.add(new JLabel("~~~~~ Point of Interest #"+(i)+" ~~~~~"));
			MyTextPane poii = new MyTextPane(info, i, HexData.LOCATION);
			poii.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
			poiPanel.add(poii);
			poiTexts.add(poii);
		}
		JScrollPane poiScrollPane = new JScrollPane(poiPanel);
		poiScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		detailsTabs.addTab("Locations", poiScrollPane);
		this.LOCATION_TAB_INDEX = detailsTabs.getTabCount()-1;

		dEntranceTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<InfoPanel.DUNGEONCOUNT;i++) {
			poiPanel.add(new JLabel("~~~~~ Dungeon #"+(i+1)+" ~~~~~"));
			MyTextPane poii = new MyTextPane(info, i, HexData.DUNGEON);
			poii.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
			poiPanel.add(poii);
			dEntranceTexts.add(poii);
		}

		JPanel hexNotePanel = new JPanel();
		hexNotePanel.setLayout(new BorderLayout());
		JPanel highlightPanel = new JPanel();
		highlightPanel.add(new JLabel("highlight:"));
		JComboBox<HighlightColor> highlightMenu = new JComboBox<HighlightColor>(HighlightColor.values());
		highlightMenu.setMaximumSize(new Dimension(100,20));
		highlightMenu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				info.getPanel().setHighlight(((HighlightColor) highlightMenu.getSelectedItem()).getColor());
			}
		});
		highlightPanel.add(highlightMenu);
		hexNotePanel.add(highlightPanel,BorderLayout.NORTH);
		hexNote1 = new MyTextPane(info, -1, HexData.NONE);
		hexNotePanel.add(hexNote1,BorderLayout.CENTER);
		JScrollPane hexNoteScrollPane = new JScrollPane(hexNotePanel);
		detailsTabs.addTab("Notes", hexNoteScrollPane);


		encounterPanel = new JPanel();
		encounterPanel.setLayout(new BoxLayout(encounterPanel, BoxLayout.Y_AXIS));
		encounterTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<info.getPanel().getRecord().getEncounters(info.getPanel().getSelectedGridPoint()).size();i++) {
			MyTextPane pane = createEncounter();
			pane.doPaint();
		}
		JScrollPane encounterScrollPane = new JScrollPane(encounterPanel);
		encounterScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		encounterScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		detailsTabs.addTab("Encounters", encounterScrollPane);
		this.ENCOUNTER_TAB_INDEX = detailsTabs.getTabCount()-1;
		

		missionsPanel = new JPanel();
		missionsPanel.setLayout(new BoxLayout(missionsPanel, BoxLayout.Y_AXIS));
		missionsTexts = new ArrayList<MyTextPane>();
		for(int i=0;i<info.getPanel().getRecord().getMissions(info.getPanel().getSelectedGridPoint()).size()||i<3;i++) {
			MyTextPane pane = createMission();
			pane.doPaint();
		}
		JScrollPane missionsScrollPane = new JScrollPane(missionsPanel);
		missionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		missionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		detailsTabs.addTab("Missions", missionsScrollPane);
		this.MISSIONS_TAB_INDEX = detailsTabs.getTabCount()-1;

		this.add(detailsTabs);
		

		selectedEncounter = -1;
		selectedMission = -1;
		selectedNPC = -1;
		selectedPOI = -1;
		selectedDungeon = -1;
	}
	

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		changeSelected = false;
		Point pos = panel.getSelectedGridPoint();
		AltitudeModel grid = panel.getController().getGrid();
		PrecipitationModel precipitation = panel.getController().getPrecipitation();
		PopulationModel population = panel.getController().getPopulation();

		int transformedUniversalPopulation = population.getTransformedUniversalPopulation(pos);


		if(grid.isWater(pos)||precipitation.isLake(pos)) {
			zeroPopComponents();
		}else {
			positivePopComponents();

			for(int i = 0;i<this.encounterTexts.size();i++) {
				MyTextPane pane = this.encounterTexts.get(i);
				pane.setHighlight(i==selectedEncounter);
				pane.doPaint();
			}

			for(int i = 0;i<this.missionsTexts.size();i++) {
				MyTextPane pane = this.missionsTexts.get(i);
				pane.setHighlight(i==selectedMission);
				pane.doPaint();
			}

			if(transformedUniversalPopulation>0) {
				detailsTabs.setEnabledAt(NPC_TAB_INDEX, true);
				for(int i = 0;i<this.npcTexts.size();i++) {
					MyTextPane pane = this.npcTexts.get(i);
					pane.setHighlight(i==selectedNPC);
					pane.doPaint();
				}
				if(selectedNPC>-1) {
					this.npcTexts.get(selectedNPC).setCaretPosition(0);
				}
				
				for(int i = 0;i<this.proprietorTexts.size();i++) {
					MyTextPane pane = this.proprietorTexts.get(i);
					pane.doPaint();
				}
			}else {
				detailsTabs.setEnabledAt(NPC_TAB_INDEX, false);
			}

			for(int i = 0;i<this.poiTexts.size();i++) {
				MyTextPane pane = this.poiTexts.get(i);
				pane.setHighlight(i==selectedPOI);
				pane.doPaint();
			}
			if(selectedPOI>-1) this.poiTexts.get(selectedPOI).setCaretPosition(0);

			for(int i = 0;i<this.dEntranceTexts.size();i++) {
				MyTextPane pane = this.dEntranceTexts.get(i);
				pane.setHighlight(i==selectedDungeon);
				pane.doPaint();
			}
			if(selectedDungeon>-1) this.dEntranceTexts.get(selectedDungeon).setCaretPosition(0);
		}

		hexNote1.doPaint();
		changeSelected = true;
	}
	public void positivePopComponents() {
		detailsTabs.setEnabledAt(ENCOUNTER_TAB_INDEX, true);
		detailsTabs.setEnabledAt(NPC_TAB_INDEX, true);
		detailsTabs.setEnabledAt(LOCATION_TAB_INDEX, true);
		detailsTabs.setEnabledAt(MISSIONS_TAB_INDEX, true);
	}

	public void zeroPopComponents() {
		detailsTabs.setEnabledAt(ENCOUNTER_TAB_INDEX, false);
		detailsTabs.setEnabledAt(NPC_TAB_INDEX, false);
		detailsTabs.setEnabledAt(LOCATION_TAB_INDEX, false);
		detailsTabs.setEnabledAt(MISSIONS_TAB_INDEX, false);
	}

	public MyTextPane createDungeonEncounter() {
		int i = encounterTexts.size();
		MyTextPane encounteri = new MyTextPane(info, i, HexData.D_ENCOUNTER);
		encounteri.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
		encounteri.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		encounterPanel.add(encounteri,0);
		encounterTexts.add(encounteri);
		return encounteri;
	}

	public MyTextPane createEncounter() {
		int i = encounterTexts.size();
		MyTextPane encounteri = new MyTextPane(info, i, HexData.ENCOUNTER);
		encounteri.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
		encounteri.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		encounterPanel.add(encounteri,0);
		encounterTexts.add(encounteri);
		return encounteri;
	}

	public MyTextPane createMission() {
		int i = missionsTexts.size();
		MyTextPane encounteri = new MyTextPane(info, i, HexData.MISSION);
		encounteri.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
		encounteri.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		missionsPanel.add(encounteri,0);
		missionsTexts.add(encounteri);
		return encounteri;
	}

	public boolean isChangeSelected() {
		return changeSelected;
	}

	public void setDetailsTab(int index) {
		this.detailsTabs.setSelectedIndex(index);
	}


	public int getSelectedEncounter() {
		return selectedEncounter;
	}


	public void setSelectedEncounter(int selectedEncounter) {
		this.selectedEncounter = selectedEncounter;
	}


	public int getSelectedMission() {
		return selectedMission;
	}


	public void setSelectedMission(int selectedMission) {
		this.selectedMission = selectedMission;
	}


	public int getSelectedNPC() {
		return selectedNPC;
	}


	public void setSelectedNPC(int selectedNPC) {
		this.selectedNPC = selectedNPC;
	}


	public int getSelectedPOI() {
		return selectedPOI;
	}


	public void setSelectedPOI(int selectedPOI) {
		this.selectedPOI = selectedPOI;
	}


	public int getSelectedDungeon() {
		return selectedDungeon;
	}


	public void setSelectedDungeon(int selectedDungeon) {
		this.selectedDungeon = selectedDungeon;
	}
}
