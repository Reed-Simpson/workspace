package data.population;

public enum SettlementSize {
	NONE(Integer.MIN_VALUE,"None"),
	THORP(0,"Thorp"),
	HAMLET(81,"Hamlet"),
	VILLAGE(401,"Village"),
	SMALLTOWN(901,"Small Town"),
	LARGETOWN(2001,"Large Town"),
	SMALLCITY(5001,"Small City"),
	LARGECITY(12001,"Large City"),
	METROPOLIS(25001,"Metropolis");
	
	private int minpop;
	private String name;

	SettlementSize(int minpop, String name) {
		this.minpop = minpop;
		this.name=name;
	}

	public int getMinpop() {
		return minpop;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static SettlementSize getSettlementSize(int pop) {
		for(SettlementSize s:SettlementSize.getValues()) {
			if(pop>s.getMinpop()) return s;
		}
		return null;
	}
	
	private static SettlementSize[] getValues() {
		return new SettlementSize[] {METROPOLIS,LARGECITY,SMALLCITY,LARGETOWN,SMALLTOWN,VILLAGE,HAMLET,THORP,NONE};
	}

}
