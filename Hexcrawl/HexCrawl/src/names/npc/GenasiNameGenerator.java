package names.npc;

import data.Indexible;
import names.IndexibleNameGenerator;

public class GenasiNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Aelith","Bronte","Fiera","Solstice","Vael","Datol","Fels","Lazuli","Marmar","Sard","Flare","Hixs","Salana","Suna","Xanthi","Marilla",
			"Marinae","Mizu","Nereus","Thalia","Anemone","Nyra","Zonda","Skyla","Nuit","Baux","Salt","Anhydr","Micr","Turf","Jorah","Hakan","Emberi","Fathom","Rin","Galene",
			"Nereus","Mira","Tranquil","Kaikoa","Solstice","Airwyn","Zonda","Ayre","Skydancer","Moonstone","Rhodon","Turquoise","Terra","Weldy","Volkan","Flare","Raziel","Incendia",
			"Jara","Reva","Seafoam","Poseidon","Harbor","Glacis","Anil","Zephira","Eiros","Fiera","Stormie","Andes","Olivine","Quarry","Chrysoberyl","Mistral","Lumi","Talin",
			"Volkanis","Eldir","Zara","Reed","Muriel","Glacis","Calypso","Cascade","Hesper","Jolt","Anan","Zera","Solara","Sard","Azura","Knoll","Tanzanis","Charcoal","Zilar",
			"Esme","Sia","Smulder","Ember","Zyra","Cascadea","Cordelia","Squalos","Ripley","Flux","Austral","Hellfire","Deluge","Mist","Tsunami","Cliff","Hunk","Zircon","Wind",
			"Gale","Storm","Calcine","Shine","Ember","Splash","Vapor","Sea","Sandstone","Onyx","Callous","Funnel","Pipe","Breath","Sultry","Gleam","Singe","Rain","Soak","Agua",
			"Cobblestone","Soapstone","Callous","Hurricane","Waft","Breath","Broil","Volcano","Gleam","Plunge","Tidal","Flux","Garnet","Cliff","Alabaster","Blow","Storm","Blast",
			"Thermo","Glare","Fuego","Course","Splash","Stream","Pebble","Sediment","Geo","Celeste","Cruise"};
	

	@Override
	public String getName(int... val) {
		if(val.length<1) throw new IllegalArgumentException("Expected 1 or more values");
		return getElementFromArray(FIRST,val[0]);
	}


	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(FIRST,obj);
	}

}
