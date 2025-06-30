package data.threat;

import data.npc.Creature;
import data.npc.NPC;
import data.population.Species;
import util.Util;

public class Threat extends Creature{
	private CreatureType type;
	private CreatureSubtype subtype;
	private Species minionSpecies;
	private String name;
	private String motive;
	private String flaw;
	private String plan;
	private String domain;
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
	public Species getMinionSpecies() {
		return minionSpecies;
	}
	public void setMinionSpecies(Species species) {
		this.minionSpecies = species;
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
			if(name.contains("${placeholder domain}")) {
				name = Util.replace(name,"${placeholder domain}",this.getDomain());
			}
			if(name.contains("${job placeholder}")) {
				name = Util.replace(name,"${job placeholder}",this.getDomain());
			}
			e1Text.append(name+"\r\n");
		}
		e1Text.append(Util.toCamelCase(this.getType().name()+" - "+this.getSubtype().getName())+"\r\n");
		e1Text.append("Motive: "+this.getMotive()+"\r\n");
		e1Text.append("Flaw: "+this.getFlaw()+"\r\n");
		e1Text.append("Plan: "+this.getPlan());
		return e1Text.toString();
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	@Override
	public Species getSpecies() {
		return this.getSubtype();
	}
	@Override
	public void setSpecies(Species s) {
		if(s instanceof CreatureSubtype) {
			this.setSubtype((CreatureSubtype) s);
		}else {
			throw new UnsupportedOperationException("Threat does not accept non-threat species types");
		}
	}
	

}
