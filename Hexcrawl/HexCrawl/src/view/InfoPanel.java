package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;
import javax.swing.ToolTipManager;

import data.HexData;
import data.Reference;
import util.Util;
import view.infopanels.CampaignPanel;
import view.infopanels.HexPanel;
import view.infopanels.RegionPanel;

@SuppressWarnings("serial")
public class InfoPanel extends JTabbedPane{
	public static final int RECENT_HISTORY_COUNT = 20;
	public static final int TAB_TITLE_LENGTH = 34;
	public static final int INFOPANELWIDTH = 450;
	public static final int ENCOUNTERCOUNT = 20;
	public static final int NPCCOUNT = 20;
	public static final int POICOUNT = 20;
	public static final int DUNGEONCOUNT = 6;
	public static final int FACTIONCOUNT = 6;
	public static final int FAITHCOUNT = 5;
	public static final int DISTRICTCOUNT = 6;
	public static final int MONSTERCOUNT = 8;


	private MapPanel panel;

	//ENCOUNTER
	private HexPanel hexPanel;
	private RegionPanel regionPanel;
	private CampaignPanel campaignPanel;
	boolean changeSelected;


	public InfoPanel(MapPanel panel) {
		this.panel = panel;
		this.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
		this.setPreferredSize(new Dimension(INFOPANELWIDTH,800));
		this.setMaximumSize(new Dimension(INFOPANELWIDTH,99999));
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		hexPanel = new HexPanel(this);
		this.addTab(Util.pad("Hex", TAB_TITLE_LENGTH), hexPanel);

		this.regionPanel = new RegionPanel(this);
		this.addTab(Util.pad("Region", TAB_TITLE_LENGTH), regionPanel);

		campaignPanel = new CampaignPanel(this);
		this.addTab(Util.pad("Campaign", TAB_TITLE_LENGTH), (campaignPanel));

		changeSelected = true;

		ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
		toolTipManager.setDismissDelay(Integer.MAX_VALUE); 
		toolTipManager.setInitialDelay(0);
	}

	public MyTextPane createDungeonEncounter() {
		return hexPanel.createDungeonEncounter();
	}

	public MyTextPane createEncounter() {
		return hexPanel.createEncounter();
	}

	public MyTextPane createMission() {
		return hexPanel.createMission();
	}

	public void removeDungeonEncounter(int index) {
		hexPanel.removeDungeonEncounter(index);
	}

	public void removeEncounter(int index) {
		hexPanel.removeEncounter(index);
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		changeSelected = false;
		changeSelected = true;
	}



	public void setPanel(MapPanel panel) {
		this.panel = panel;
	}
	public void selectTabAndIndex(Reference ref) {
		selectTabAndIndex(ref.getType().getText(), ref.getPoint().x, ref.getPoint().y, ref.getIndex());
	}

	public void selectTabAndIndex(String tab, int x, int y, int index) {
		HexData type = HexData.get(tab);
		Point displayPos = new Point(x,y);
		Point actualPos = Util.denormalizePos(displayPos, panel.getRecord().getZero());
		panel.recenter(actualPos, true);
		switch(type) {
		case NPC: {
			hexPanel.setSelectedNPC(index);
			selectTab(0,hexPanel.NPC_TAB_INDEX,index);break;
		}
		case LOCATION: {
			hexPanel.setSelectedPOI(index);
			selectTab(0,hexPanel.LOCATION_TAB_INDEX,index);break;
		}
		case DUNGEON: {
			hexPanel.setSelectedDungeon(index);
			selectTab(0,hexPanel.LOCATION_TAB_INDEX,index);break;
		}
		case ENCOUNTER: {
			hexPanel.setSelectedEncounter(index);
			selectTab(0,hexPanel.ENCOUNTER_TAB_INDEX,index);break;
		}
		case D_ENCOUNTER: {
			hexPanel.setSelectedEncounter(index);
			selectTab(0,hexPanel.ENCOUNTER_TAB_INDEX,index);break;
		}
		case MISSION: {
			hexPanel.setSelectedMission(index);
			selectTab(0,hexPanel.MISSIONS_TAB_INDEX,index);break;
		}
		case DISTRICT: selectTab(1,regionPanel.CITY_TAB_INDEX,index);break;
		case FACTION: {
			regionPanel.setSelectedFaction(index);
			selectTab(1,regionPanel.FACTION_TAB_INDEX,index);break;
		}
		case FAITH: {
			regionPanel.setSelectedFaith(index);
			selectTab(1,regionPanel.FAITH_TAB_INDEX,index);break;
		}
		case FACTION_NPC: {
			regionPanel.setSelectedFactionNPC(index);
			selectTab(1,regionPanel.FACTION_NPC_TAB_INDEX,index);break;
		}
		case MINION: {
			regionPanel.setSelectedMinion(index);
			selectTab(1,regionPanel.MINIONS_TAB_INDEX,index);break;
		}
		case BEAST: {
			regionPanel.setSelectedBeast(index);
			selectTab(1,regionPanel.BEASTS_TAB_INDEX,index);break;
		}
		case MONSTER: {
			regionPanel.setSelectedMonster(index);
			selectTab(1,regionPanel.BEASTS_TAB_INDEX,index);break;
		}
		case THREATMONSTER:{
			regionPanel.setSelectedThreatMonster(index);
			selectTab(1,regionPanel.BEASTS_TAB_INDEX,index);break;
		}
		case CHARACTER: selectTab(2,0,index);break;
		case TOWN: selectTab(1,regionPanel.CITY_TAB_INDEX,index);break;
		default: throw new IllegalArgumentException("unrecognized tab name: "+tab);
		}
		panel.preprocessThenRepaint();
	}

	private void selectTab(int maintab, int subtab,int index) {
		this.setSelectedIndex(maintab);
		if(maintab==0) {
			hexPanel.setDetailsTab(subtab);
		}else {
			regionPanel.setRegionTabs(subtab);
		}
	}
	public MapPanel getPanel() {
		return panel;
	}
	public boolean isChangeSelected() {
		return changeSelected;
	}

	public void addCharacter(HexData type, Point point, int index) {
		campaignPanel.addCharacter(type, point, index);
	}

	public void removeCharacter(int index) {
		campaignPanel.removeCharacter(index);
	}

}
