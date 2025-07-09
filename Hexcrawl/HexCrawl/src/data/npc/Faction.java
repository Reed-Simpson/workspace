package data.npc;

import data.Indexible;
import data.Reference;
import names.FactionType;
import util.Util;

public class Faction extends Indexible {
	FactionType type;

	String trait;
	String goal;
	String name;
	String domain;
	Reference leader;
	Reference member;

	public Faction(float... floats) {
		super(floats);
	}

	public Faction(int[] ints) {
		super(ints);
	}

	public FactionType getType() {
		return type;
	}

	public String getTrait() {
		return trait;
	}

	public String getGoal() {
		return goal;
	}
	
	public String getName() {
		return name;
	}
	public void setType(FactionType type) {
		this.type = type;
	}

	public void setTrait(String trait) {
		this.trait = trait;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}



	public Reference getLeader() {
		return leader;
	}

	public void setLeader(Reference leader) {
		this.leader = leader;
	}

	public Reference getMember() {
		return member;
	}

	public void setMember(Reference member) {
		this.member = member;
	}

	private String formatName() {
		String name = this.getName();
		if(this.getName().contains("${placeholder domain}")) {
			name = Util.replace(name,"${placeholder domain}",this.getDomain());
		}
		return Util.toCamelCase(name);
	}
	
	public String toString() {
		StringBuilder c1Text = new StringBuilder();
		c1Text.append(formatName()+"\r\n");
		if(this.getDomain()!=null) c1Text.append("   Domain: "+this.getDomain()+"\r\n");
		c1Text.append("   Type: "+this.getType()+"\r\n");
		c1Text.append("   Trait: "+this.getTrait()+"\r\n");
		c1Text.append("   Goal: "+this.getGoal());
		if(leader!=null) c1Text.append("\r\nLeader: "+leader.toString());
		if(member!=null) c1Text.append("\r\nMember: "+member.toString());
		return c1Text.toString();
	}
	
	

}
