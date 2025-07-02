package data.npc;

import data.population.Species;
import util.Util;

public class NPC extends Creature{

	private transient Species species;
	private transient String job;
	private transient String asset;
	private transient String liability;
	private transient String goal;
	private transient String misfortune;
	private transient String method;
	private transient String appearance;
	private transient String detail;
	private transient String costume;
	private transient String personality;
	private transient String mannerism;
	private transient String secret;
	private transient String reputation;
	private transient String hobby;
	private transient String relationship;
	private transient String domain;
	private transient String name;
	private transient String subspecies;
	private transient String faction;
	private transient String[] descriptors;

	public NPC(float... floats) {
		super(floats);
	}
	public NPC(Species s) {
		super(0);
		this.species = s;
	}

	public NPC(int[] ints) {
		super(ints);
	}
	public Species getSpecies() {
		return species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public String getLiability() {
		return liability;
	}

	public void setLiability(String liability) {
		this.liability = liability;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getMisfortune() {
		return misfortune;
	}

	public void setMisfortune(String misfortune) {
		this.misfortune = misfortune;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAppearance() {
		return appearance;
	}

	public void setAppearance(String appearance) {
		this.appearance = appearance;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getCostume() {
		return costume;
	}

	public void setCostume(String costume) {
		this.costume = costume;
	}

	public String getPersonality() {
		return personality;
	}

	public void setPersonality(String personality) {
		this.personality = personality;
	}

	public String getMannerism() {
		return mannerism;
	}

	public void setMannerism(String mannerism) {
		this.mannerism = mannerism;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getReputation() {
		return reputation;
	}

	public void setReputation(String reputation) {
		this.reputation = reputation;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String toString() {
		StringBuilder e1Text = new StringBuilder();
		String name = (this.name!=null?this.name+", the ":"");
		String species = this.getSpecies().getSpeciesName();
		if(this.getSubspecies()!=null) species = this.getSubspecies();
		e1Text.append(name+Util.toCamelCase(species)+" "+this.getJob() + "\r\n");
		e1Text.append("• Descriptors: "+Util.parseArray(this.getDescriptors()));
		e1Text.append("\r\n");
		//appearance
		e1Text.append("• Appearance: "+this.getAppearance()+" with "+this.getDetail()+". ");
		e1Text.append("Their outfit appears to be "+this.getCostume()+". ");
		e1Text.append("\r\n");
		//social
		e1Text.append("• Social: Has a "+this.getPersonality()+" personality and is "+this.getMannerism()+". ");
		e1Text.append("Has a reputation for being "+this.getReputation()+". ");
		e1Text.append("Has a close relationship with a "+this.getRelationship()+". ");
		e1Text.append("\r\n");
		//personal
		e1Text.append("• Personal: Seeks "+this.getGoal()+". ");
		e1Text.append(this.getAsset()+" and adept at using "+this.getMethod()+". ");
		e1Text.append("Likes to study "+this.getHobby()+". ");
		e1Text.append("Practices the faith of "+this.getDomain()+".");
		e1Text.append("\r\n");
		//Liabilities
		e1Text.append("• Liabilities: "+this.getLiability()+" and is currently "+this.getMisfortune()+". ");
		e1Text.append("Secretly is "+this.getSecret()+". ");
		return formatText(e1Text.toString());
	}

	private String formatText(String string) {
		if(string.contains("${placeholder domain}")) {
			string = Util.replace(name,"${placeholder domain}",this.getDomain());
		}
		if(string.contains("${job placeholder}")) {
			string = Util.replace(name,"${job placeholder}",this.getJob());
		}
		return string;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubspecies() {
		return subspecies;
	}

	public void setSubspecies(String goblin) {
		this.subspecies = goblin;
	}
	public String getFaction() {
		return faction;
	}
	public void setFaction(String faction) {
		this.faction = faction;
	}
	public String[] getDescriptors() {
		return descriptors;
	}
	public void setDescriptors(String[] descriptors) {
		this.descriptors = descriptors;
	}

}
