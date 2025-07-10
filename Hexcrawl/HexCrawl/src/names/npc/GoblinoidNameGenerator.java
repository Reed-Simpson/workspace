package names.npc;

import data.Indexible;
import names.IndexibleNameGenerator;
import util.Util;

public class GoblinoidNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Slog","Brun","Shaz","Ez","Krub","Blud","Bez","Gosmil","Zeeb","Scab","Kog","Treg","Tark","Gribna","Rip","Xlaz","Lorz","Erb","Grunsk","Thurg","Zog","Pulz",
			"Holug","Lulz","Blorg","Torl","Zarg","Lor","Tul","Tuz","Brool","Zig","Slag","Vaku","Wart","Mole","Zuz","Hugor","Sig","Flitch","Drulz","Worv","Klag","Scar","Morl","Zug","Mig",
			"Fogul","Xog","Vorg","Grom","Blarg","Xarg","Vorl","Murg","Worl","Grub","Xrag","Milg","Rorg","Grel","Grol","Narl","Brog","Orulz","Ooglo","Grig","Rok","Viklorg","Slob","Pug","Uz","Nog",
			"Arg","Moog","Urz","Lig","Zrag","Xorgul","Durg","Wik","Jerg","Zux","Alug","Slime","Shank","Og","Korlug","Iv","Zork","Limper","Smelg","Gorg","Mogz","Laz","Org","Eurg","Bik",
			"Vib","Bek","Hor","Kub","Lud","Ur","Peb","Fik","Meb","Xib","Vog","Zib","Zuz","Wuz","Gig","Zor","Pib","Hud","Hib","Nib","Nuz","Zud","Tog","Lub","Sor","Por","Yog","Mog","Huz","Vor",
			"Xik","Vud","Pud","Gib","Ot","Ut","Uz","Xud","Pog","Yor","Nik","Kor","Ub","Wub","Kib","Zog","Pik","Muz","Uk","Vik","Jub","Gik","Lek","Sik","Zib","Kig","Duz","Kog","Vuz","Mib","Ud",
			"Od","Keb","Mik","Bor","Wik","Sek","Lig","Gub","Ob","Uy","Sib","Ouz","Tig","Sud","Nud","Ror","Tor","Juz","Rog","Xeb","Bog","Zeb","Luz","Guz","Gud","Kud","Wib","Suz","Puz","Lib","Fub",
			"Sog","Og","Buz","Zik"};
	private static final String[] LAST = {"${elemental type}born","Kill${bodypart}","Gore${bodypart}","${basic color}${bodypart}","${basic material}${bodypart}","${basic color}${basic material}",
			"Bad${bodypart}","Keen${bodypart}","${basic material}born","${basic color}${elemental type}","${elemental type}${bodypart}","Dagger${bodypart}","${metal}sword","Grim${bodypart}",
			"${basic color}death","Death${elemental type}","Broken${weapon}","Bloodborn"};
	

	@Override
	public String getName(Indexible obj) {
		return Util.toCamelCase(Util.formatTableResult(getElementFromArray(FIRST,obj)+" "+getElementFromArray(LAST,obj),obj));
	}

}