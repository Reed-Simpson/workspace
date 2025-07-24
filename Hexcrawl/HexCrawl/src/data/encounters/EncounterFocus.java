package data.encounters;

import data.HexData;
import data.Indexible;
import data.WeightedTable;

public enum EncounterFocus {
	Remote_Event("Learn a rumor",null),
	Ambiguous_Event("Ambiguous Event",null),
	New_NPC("Meet a new NPC",HexData.NPC),
	NPC_Action("Known npc acts",HexData.CHARACTER),
	NPC_Negative("Negative impact on known NPC",HexData.CHARACTER),
	NPC_Positive("Positive impact on known NPC",HexData.CHARACTER),
	Move_Toward_A_Thread("Progress existing thread",HexData.THREAD),
	Move_Away_From_A_Thread("Obstacle to existing thread",HexData.THREAD),
	Close_A_Thread("Resolve existing thread",HexData.THREAD),
	PC_Negative("Negative impact on PC",null),
	PC_Positive("Positive impact on PC",null),
	Current_Context("Current Context",null);
	
	private String text;
	private HexData linkType;
	
	private EncounterFocus(String text,HexData linkType) {
		this.text = text;
		this.linkType = linkType;
	}
	
	
	
	private static WeightedTable<EncounterFocus> encounterFocus;

	private static void populateEncounterFocus() {
		encounterFocus = new WeightedTable<EncounterFocus>();
		encounterFocus.put(Remote_Event,5);
		encounterFocus.put(Ambiguous_Event,5);
		encounterFocus.put(New_NPC,10);
		encounterFocus.put(NPC_Action,20);
		encounterFocus.put(NPC_Negative,5);
		encounterFocus.put(NPC_Positive,5);
		encounterFocus.put(Move_Toward_A_Thread,5);
		encounterFocus.put(Move_Away_From_A_Thread,10);
		encounterFocus.put(Close_A_Thread,5);
		encounterFocus.put(PC_Negative,10);
		encounterFocus.put(PC_Positive,5);
		encounterFocus.put(Current_Context,15);
	}
	public static EncounterFocus getFocus(Indexible e) {
		if(encounterFocus==null) populateEncounterFocus();
		return encounterFocus.getByWeight(e);
	}
	public String toString() {
		return text;
	}
	public HexData getLinkType() {
		return linkType;
	}
}
