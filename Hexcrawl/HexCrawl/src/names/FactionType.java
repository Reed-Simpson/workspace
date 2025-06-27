package names;


public enum FactionType {
	DARK_CULT("Dark Cult"),
	HERETICAL_SECT("Heretical Sect"),
	NATIONAL_CHURCH("National Church"),
	RELIGIOUS_ORDER("Religious Order"),
	RELIGIOUS_SECT("Religious Sect"),


	ART_MOVEMENT("Art Movement"),
	BEGGAR_GUILD("Beggar's Guild"),
	BLACK_MARKET("Black Market"),
	BROTHERHOOD("Brotherhood"),
	CITY_GUARD("City Guard"),
	CONSPIRACY("Conspiracy"),
	CRAFT_GUILD("Craft Guild"),
	CRIME_FAMILY("Crime Family"),
	CRIME_RING("Crime Ring"),
	EXPLORER_CLUB("Explorer's Club"),
	FREE_COMPANY("Free Company"),
	GOURMAND_CLUB("Gourmand Club"),
	HEIST_CREW("Heist Crew"),
	HIGH_COUNCIL("High Council"),
	HIRED_KILLERS("Hired Killers"),
	LOCAL_MILITIA("Local Militia"),
	NOBLE_HOUSE("Noble House"),
	OUTLANDER_CLAN("Outlander Clan"),
	OUTLAW_GANG("Outlaw Gang"),
	POLITICAL_PARTY("Political Party"),
	RESISTANCE("Resistance"),
	ROYAL_ARMY("Royal Army"),
	ROYAL_HOUSE("Royal House"),
	SCHOLAR_CIRCLE("Scholar's Circle"),
	SECRET_SOCIETY("Secret Society"),
	SPY_NETWORK("Spy Network"),
	STREET_ARTISTS("Street Artists"),
	STREET_GANG("Street Gang"),
	STREET_MUSICIANS("Street Musicians"),
	THEATER_TROUPE("Theater Troupe"),
	TRADE_COMPANY("Trade Company");
	
	public static final FactionType[] ABERRATION = {DARK_CULT,DARK_CULT,DARK_CULT,DARK_CULT,DARK_CULT,HERETICAL_SECT,HERETICAL_SECT,HERETICAL_SECT,NATIONAL_CHURCH,RELIGIOUS_ORDER,RELIGIOUS_SECT,
			ART_MOVEMENT,BROTHERHOOD,CONSPIRACY,CRAFT_GUILD,EXPLORER_CLUB,NOBLE_HOUSE,SCHOLAR_CIRCLE,SECRET_SOCIETY,STREET_ARTISTS,STREET_MUSICIANS,THEATER_TROUPE};

	String text;

	FactionType(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
	public String toString() {
		return text;
	}
	public boolean isFaith() {
		switch(this) {
		case DARK_CULT: case HERETICAL_SECT: case NATIONAL_CHURCH: case RELIGIOUS_ORDER: case RELIGIOUS_SECT:
			return true;
		default: return false;
		}
	}
}
