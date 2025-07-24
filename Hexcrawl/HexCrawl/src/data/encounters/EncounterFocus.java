package data.encounters;

import data.Indexible;
import data.WeightedTable;

public enum EncounterFocus {
	Remote_Event("Learn a rumor"),
	Ambiguous_Event("Ambiguous Event"),
	New_NPC("Meet a new NPC"),
	NPC_Action("Known npc acts"),
	NPC_Negative("Negative impact on known NPC"),
	NPC_Positive("Positive impact on known NPC"),
	Move_Toward_A_Thread("Progress existing thread"),
	Move_Away_From_A_Thread("Obstacle to existing thread"),
	Close_A_Thread("Resolve existing thread"),
	PC_Negative("Negative impact on PC"),
	PC_Positive("Positive impact on PC"),
	Current_Context("Current Context");
	
	private String text;
	
	private EncounterFocus(String text) {
		this.text = text;
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
}
