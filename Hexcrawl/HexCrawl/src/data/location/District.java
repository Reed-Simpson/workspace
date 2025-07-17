package data.location;

public class District {
	private DistrictType type;
	private String weirdness;
	
	public District(DistrictType type) {
		this.type = type;
	}

	public String getWeirdness() {
		return weirdness;
	}

	public void setWeirdness(String weirdness) {
		this.weirdness = weirdness;
	}
	
	public String toString() {
		if(weirdness!=null) return weirdness+" "+type.toString();
		else return type.toString();
	}

}
