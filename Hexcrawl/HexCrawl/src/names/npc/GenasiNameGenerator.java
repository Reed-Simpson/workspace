package names.npc;

import data.Indexible;
import names.IndexibleNameGenerator;

public class GenasiNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Aelith","Bronte","Fiera","Solstice","Vael","Datol","Fels","Lazuli","Marmar","Sard","Flare","Hixs","Salana","Suna","Xanthi","Marilla","Marinae",
			"Mizu","Nereus","Thalia","Anemone","Nyra","Zonda","Skyla","Nuit","Baux","Salt","Anhydr","Micr","Turf","Jorah","Hakan","Emberi","Fathom","Rin","Galene","Mira","Tranquil","Kaikoa",
			"Airwyn","Ayre","Skydancer","Moonstone","Rhodon","Turquoise","Terra","Weldy","Volkan","Raziel","Incendia","Jara","Reva","Seafoam","Poseidon","Harbor","Glacis","Anil","Zephira",
			"Eiros","Stormie","Andes","Olivine","Quarry","Chrysoberyl","Mistral","Lumi","Talin","Volkanis","Eldir","Zara","Reed","Muriel","Calypso","Cascade","Hesper","Jolt","Anan","Zera",
			"Solara","Azura","Knoll","Tanzanis","Charcoal","Zilar","Esme","Sia","Smulder","Ember","Zyra","Cascadea","Cordelia","Squalos","Ripley","Flux","Austral","Hellfire","Deluge","Mist",
			"Tsunami","Cliff","Hunk","Zircon","Wind","Gale","Storm","Calcine","Shine","Splash","Vapor","Sea","Sandstone","Onyx","Callous","Funnel","Pipe","Breath","Sultry","Gleam","Singe",
			"Rain","Soak","Agua","Cobblestone","Soapstone","Hurricane","Waft","Broil","Volcano","Plunge","Tidal","Garnet","Alabaster","Blow","Blast","Thermo","Glare","Fuego","Course","Stream",
			"Pebble","Sediment","Geo","Celeste","Cruise","Magmis","Lumen","Kindra","Scorchis","Novis","Fenix","Azar","Fye","Scaldris","Luminus","Fumis","Tempris","Blaize","Melt","Cinder",
			"Pyre","Sun","Fume","Bright","Dante","Glow","Flint","Flame","Flash","Star","Arson","Ignite","Sear","Timber","Ignis","Molten","Inflamatta","Wick","Temper","Radia","Warmth",
			"Crackle","Spark","Sizzle","Char","Inferno","Smoke","Burn","Rhys","Kenna","Cyruz","Nero","Soleil","Sol","Ignatius","Ravi","Helios","Cyra","Ciro","Uri","Fiam","Fintan","Fiamma",
			"Cicero","Apollo","Pheonix","Vulcan","Agni","Drago","Ardea","Solina","Hito","Alev","Barak","Egan","Combusti","Inferni","Anala","Infernus","Bren","Elian","Hestia","Idalia","Tana",
			"Tan","Vesta","Kalama","Calida","Dzo","Lume","Tuz","Fuur","Ahi","Adir","Foco","Tulipalo","Ozone","Tumul","Vitalis","Salvus","Aros","Aerus","Hiss","Gust","Alizeh","Breeze","Skye",
			"Makani","Scirocco","Coro","Tempest","Aella","Zephyrine","Alya","Amaterasu","Araceli","Iris","Thora","Zephyr","Aria","Esen","Ilma","Kalani","Miku","Rai","Bayu","Naseem","Guthrie",
			"Adad","Aureole","Cloud","Vindr","Eyvindr","Kenye","Akash","Enlil","Wayra","Aura","Bolt","Blare","Puff","Sigh","Stratos","Typhoon","Flutter","Aviate","Sail","Whiff","Whirl",
			"Whisper","Atmos","Ascend","Drift","Flurry","Ventis","Bluster","Cyclone","Twister","Whisk","Glide","Ohm","Billow","Electra","Lucerne","Squall","Wuther","Aurora","Bise","Bora",
			"Xlokk (Shlok)","Moaza","Fohn","Samoon","Avel","Neven","Shu","Sepher","Ciel","Zenith","Nephele","Tondra","Zerua","Vapore","Hydrius","Salis","Rayne","Brine","Liquaxis","Aquara",
			"Vaporis","Flow","Vapir","Puddle","Glace","River","Glayze","Monse","Gush","Ocean","Brook","Current","Dew","Iglis","Aquos","Noelani","Indra","Rainn","Varsha","Talia","Tal","Lokni",
			"Rapid","Spring","Coast","Tide","Wave","Riptide","Shore","Crest","Geyser","Eddie","Mer","Pond","Laguna","Boil","Sublime","Spray","Hail","Spout","Aqua","Steam","Creek","Aalto",
			"Aenon","Ara","Delta","Firth","Hali","Kai","Fjord","Isla","Lake","Marina","Nerida","Rialta","Maris","Ripple","Sedna","Rio","Tahoe","Tali","Rippley","Meadow","Talise","Zarya",
			"Llyr","Marsh","Maya","Neptune","Po","Nile","Reef","Wade","Hydris","Grime","Therris","Sod","Slate","Fossilis","Granite","Dune","Grav","Basalt","Jade","Opal","Topaz","Ruby",
			"Emerald","Obsidian","Peridot","Rhodalite","Amethyst","Agate","Citrine","Tourmaline","Quartz","Tavertine","Mantle","Zirconia","Lime","Adakite","Marble","Fossil","Diorite",
			"Basanite","Gabbro","Porphyry","Norite","Breccia","Chalk","Tuff","Oolite","Phosphor","Lignite","Shale","Gneiss","Silt","Calcar","Mylon","Ochre","Schist","Coal","Felsite","Clay",
			"Dolomite","Lapis","Llanite","Gravel","Cobble","Calcrete","Igneus","Boulder","Skarn","Stone","Hill","Yardang","Rock","Atoll","Cape","Cove","Butte","Gulch","Lavaka","Tor","Tepui",
			"Valley","Canyon","Esker","Brimstone","Brick","Gem","Crater","Soil","Caliche"};
	


	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(FIRST,obj);
	}

}
