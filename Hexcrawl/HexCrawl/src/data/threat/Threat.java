package data.threat;

import data.Indexible;
import data.npc.NPC;
import util.Util;

public class Threat extends Indexible{
	private CreatureType type;
	private CreatureSubtype subtype;
	private String name;
	private String motive;
	private String flaw;
	private String plan;
	private NPC npc;

	public Threat(float... floats) {
		super(floats);
	}
	public Threat(int... ints) {
		super(ints);
	}
	
	
	public CreatureType getType() {
		return type;
	}
	public void setType(CreatureType type) {
		this.type = type;
	}
	public CreatureSubtype getSubtype() {
		return subtype;
	}
	public void setSubtype(CreatureSubtype subtype) {
		this.subtype = subtype;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMotive() {
		return motive;
	}
	public void setMotive(String motive) {
		this.motive = motive;
	}
	public String getFlaw() {
		return flaw;
	}
	public void setFlaw(String flaw) {
		this.flaw = flaw;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public NPC getNPC() {
		return npc;
	}
	public void setNPC(NPC npc) {
		this.npc = npc;
	}
	
	public String toString() {
		StringBuilder e1Text = new StringBuilder();
		if(this.name!=null) {
			e1Text.append(name+"\r\n");
		}
		e1Text.append(Util.toCamelCase(this.getType().name()+" - "+this.getSubtype().getName())+"\r\n");
		e1Text.append("Motive: "+this.getMotive()+"\r\n");
		e1Text.append("Flaw: "+this.getFlaw()+"\r\n");
		e1Text.append("Plan: "+this.getPlan());
		return e1Text.toString();
	}
	

}
