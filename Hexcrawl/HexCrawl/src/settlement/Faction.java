package settlement;

import general.Indexible;

public class Faction extends Indexible {
	String type;

	String trait;
	String goal;
	String name;

	public Faction(float... floats) {
		super(floats);
	}

	public String getType() {
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
	public void setType(String type) {
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
	
	public String toString(int i) {
		StringBuilder c1Text = new StringBuilder();
		c1Text.append("Factions #"+i+": "+this.getName()+"\r\n");
		c1Text.append("   Type: "+this.getType()+"\r\n");
		c1Text.append("   Trait: "+this.getTrait()+"\r\n");
		c1Text.append("   Goal: "+this.getGoal()+"\r\n");
		return c1Text.toString();
	}
	
	

}
