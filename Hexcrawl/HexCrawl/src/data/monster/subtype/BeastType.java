package data.monster.subtype;

import java.util.HashMap;

import data.WeightedTable;
import data.biome.BiomeType;
import data.population.Species;
import data.threat.CreatureType;
import names.IndexibleNameGenerator;

public enum BeastType implements Species {
	ALBATROSS,ALLOSAURUS,ANKYLOSAURUS,ANT,APE,AUROCHS,AXE_BEAK,BABOON,BADGER,BAT,BEETLE,BIRD_OF_PARADISE,BLACK_BEAR,BLOOD_HAWK,BOAR,BRONTOSAURUS,BROWN_BEAR,CAMEL,CAT,CENTIPEDE,CONSTRICTOR_SNAKE,
	CRAB,CRANIUM_RAT,CROCODILE,DEEP_ROTHE,DEER,DEINONYCHUS,DIMETRODON,DIRE_WOLF,DOLPHIN,DRAFT_HORSE,EAGLE,ELEPHANT,ELK,FLYING_SNAKE,FROG,GIANT_FIRE_BEETLE,GOAT,HADROSAURUS,HYENA,JACKAL,
	KILLER_WHALE,LION,LIZARD,MAMMOTH,MASTIFF,MULE,OCTOPUS,OWL,OX,PANTHER,PLESIOSAURUS,POISONOUS_SNAKE,POLAR_BEAR,PONY,PTERANODON,QUETZALCOATLUS,QUIPPER,RAT,RAVEN,RHINOCEROS,
	RIDING_HORSE,ROTHE,SABERTOOTHED_TIGER,SCORPION,SEA_HORSE,SHARK,SPIDER,STEGOSAURUS,STIRGE,SWARM_OF_INSECTS,
	SWARM_OF_ROT_GRUBS,TIGER,TOAD,TRICERATOPS,TYRANNOSAURUS_REX,VELOCIRAPTOR,VULTURE,WARHORSE,WASP,WEASEL,WOLF,WOLF_SPIDER;

	/*
	 * ",,Butterfly,Condor,Crane,Crow,Dragonfly,Falcon,Firefly,Flamingo,Fly,Flying squirrel,Goose,Gull,Hummingbird,Kingfisher,Locust,Magpie,Mantis,Mockingbird,Mosquito,"+
			"Moth,Parrot,Peacock,Pelican,Pteranodon,Rooster,Sparrow,Swan,Woodpecker"
			
			"Armadillo,Caterpillar,Chameleon,Cockroach,Ferret,Fox,Giraffe,Mole,Ostrich,Ox,Porcupine,Rabbit,Raccoon,Sheep,Slug,Snail,Squirrel,Wolverine"
			
			"Alligator,Amoeba,Anglerfish,Beaver,Clam,Eel,Hippopotamus,Jellyfish,Leech,"+
			"Lobster,Manatee,Manta ray,Muskrat,Narwhal,Newt,Otter,Penguin,Platypus,Pufferfish,Salamander,"+
			"Sea anemone,Sea urchin,Seal,Shrimp,Squid,Swordfish,Tadpole,Turtle,Walrus,Whale"
	 */

	private static HashMap<BiomeType,WeightedTable<Species>> habitats;

	public static WeightedTable<Species> getSpecies(BiomeType type){
		if(habitats==null) populateHabitats();
		WeightedTable<Species> result = habitats.get(type);
		if(result==null) throw new IllegalStateException("Unrecognized Habitat Type:"+type.toString());
		return result;
	}

	private static void populateHabitats() {
		habitats = new HashMap<BiomeType,WeightedTable<Species>>();
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {OWL,BLOOD_HAWK,BROWN_BEAR,POLAR_BEAR,SABERTOOTHED_TIGER,MAMMOTH};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.SNOW, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {ALBATROSS,CRAB,EAGLE,BLOOD_HAWK,DOLPHIN,POISONOUS_SNAKE,STIRGE,DIMETRODON,LIZARD,WOLF_SPIDER,PTERANODON,TOAD,
					PLESIOSAURUS,QUETZALCOATLUS};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.BEACH, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {ANT,BEETLE,CAT,HYENA,JACKAL,SCORPION,VULTURE,CAMEL,FLYING_SNAKE,MULE,POISONOUS_SNAKE,STIRGE,CONSTRICTOR_SNAKE,LIZARD,
					WOLF_SPIDER,SWARM_OF_INSECTS,HYENA,SPIDER,TOAD,VULTURE,LION};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.DESERT, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {ANT,BABOON,BADGER,BEETLE,CAT,DEER,HYENA,OWL,BLOOD_HAWK,FLYING_SNAKE,RAT,WEASEL,MASTIFF,POISONOUS_SNAKE,STIRGE,BOAR,CONSTRICTOR_SNAKE,ELK,
					BAT,FROG,LIZARD,WOLF_SPIDER,PANTHER,RAVEN,VELOCIRAPTOR,WOLF,APE,BLACK_BEAR,WASP,SWARM_OF_INSECTS,BROWN_BEAR,DEINONYCHUS,DIRE_WOLF,HYENA,SPIDER,TOAD,TIGER,
					STEGOSAURUS,BRONTOSAURUS};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.FOREST, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {ANT,CAT,DEER,EAGLE,GOAT,HYENA,JACKAL,VULTURE,BLOOD_HAWK,FLYING_SNAKE,WEASEL,POISONOUS_SNAKE,STIRGE,AXE_BEAK,BOAR,ELK,
					WOLF_SPIDER,HADROSAURUS,PANTHER,PTERANODON,RIDING_HORSE,ROTHE,VELOCIRAPTOR,WOLF,WASP,SWARM_OF_INSECTS,DEINONYCHUS,
					LION,TIGER,ALLOSAURUS,AUROCHS,RHINOCEROS,ANKYLOSAURUS,ELEPHANT,STEGOSAURUS,BRONTOSAURUS,TRICERATOPS,TYRANNOSAURUS_REX};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.GRASSLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {ANT,BABOON,EAGLE,GOAT,HYENA,RAVEN,VULTURE,BLOOD_HAWK,WEASEL,MASTIFF,MULE,POISONOUS_SNAKE,STIRGE,AXE_BEAK,BOAR,ELK,OWL,
					WOLF_SPIDER,PANTHER,BAT,RAVEN,WOLF,SWARM_OF_INSECTS,BROWN_BEAR,DEINONYCHUS,DIRE_WOLF,LION,AUROCHS,QUETZALCOATLUS};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.ROCKYHILLS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {ANT,BABOON,BEETLE,BIRD_OF_PARADISE,LIZARD,SPIDER,FLYING_SNAKE,POISONOUS_SNAKE,STIRGE,AXE_BEAK,CONSTRICTOR_SNAKE,FROG,
					HADROSAURUS,PTERANODON,BAT,VELOCIRAPTOR,APE,WASP,SWARM_OF_INSECTS,DEINONYCHUS,ALLOSAURUS,BOAR,
					ANKYLOSAURUS,SCORPION,STEGOSAURUS,BRONTOSAURUS,TRICERATOPS,TYRANNOSAURUS_REX};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.JUNGLE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {ANT,EAGLE,GOAT,BLOOD_HAWK,STIRGE,PTERANODON,BAT,LION,AUROCHS,ELK,QUETZALCOATLUS,SABERTOOTHED_TIGER};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.MOUNTAINS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {ANT,BEETLE,RAT,RAVEN,POISONOUS_SNAKE,STIRGE,CONSTRICTOR_SNAKE,DIMETRODON,FROG,LIZARD,HADROSAURUS,
					CROCODILE,SWARM_OF_INSECTS,SWARM_OF_ROT_GRUBS,SPIDER,TOAD};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WETLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {ANT,BEETLE,CRANIUM_RAT,GIANT_FIRE_BEETLE,RAT,STIRGE,DEEP_ROTHE,BAT,CENTIPEDE,LIZARD,POISONOUS_SNAKE,
					SWARM_OF_INSECTS,SWARM_OF_ROT_GRUBS,SPIDER,TOAD,CONSTRICTOR_SNAKE,POLAR_BEAR};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.VOID, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {QUIPPER,CONSTRICTOR_SNAKE,PLESIOSAURUS};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.LAKE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {ALBATROSS,QUIPPER,DOLPHIN,CONSTRICTOR_SNAKE,SEA_HORSE,OCTOPUS,KILLER_WHALE,
					SHARK};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.WATER, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			Species[] array = new Species[] {BEETLE,CAT,CRANIUM_RAT,GOAT,RAT,RAVEN,FLYING_SNAKE,MASTIFF,MULE,PONY,STIRGE,DRAFT_HORSE,CENTIPEDE,POISONOUS_SNAKE,OX,RIDING_HORSE,
					BAT,CROCODILE,WASP,SWARM_OF_INSECTS,WARHORSE,SPIDER};
			for(Species s:array) list.put(s);
			habitats.put(BiomeType.CITY, list);
			}
		}

		@Override
		public IndexibleNameGenerator getNameGen() {
			return CreatureType.BEAST.getNameGen();
		}






	}
