package data.monster.subtype;

import java.util.HashMap;

import data.WeightedTable;
import data.biome.BiomeType;
import data.population.Species;
import data.threat.CreatureType;
import names.IndexibleNameGenerator;

public enum BeastType implements Species {
	ALLOSAURUS,ANKYLOSAURUS,APE,AUROCHS,AXE_BEAK,BABOON,BADGER,BLACK_BEAR,BLOOD_HAWK,BOAR,BRONTOSAURUS,BROWN_BEAR,CAMEL,CAT,CONSTRICTOR_SNAKE,CRAB,CRANIUM_RAT,CROCODILE,
	DEEP_ROTHE,DEER,DEINONYCHUS,DIMETRODON,DIRE_WOLF,DOLPHIN,DRAFT_HORSE,EAGLE,ELEPHANT,ELK,FLYING_SNAKE,GIANT_APE,GIANT_BADGER,GIANT_BAT,GIANT_BOAR,GIANT_CENTIPEDE,
	GIANT_CONSTRICTOR_SNAKE,GIANT_CRAB,GIANT_CROCODILE,GIANT_EAGLE,GIANT_ELK,GIANT_FIRE_BEETLE,GIANT_FROG,GIANT_GOAT,GIANT_HYENA,GIANT_LIZARD,GIANT_OCTOPUS,GIANT_OWL,GIANT_POISONOUS_SNAKE,
	GIANT_RAT,GIANT_SCORPION,GIANT_SEA_HORSE,GIANT_SHARK,GIANT_SPIDER,GIANT_TOAD,GIANT_VULTURE,GIANT_WASP,GIANT_WEASEL,GIANT_WOLF_SPIDER,GOAT,HADROSAURUS,HUNTER_SHARK,HYENA,JACKAL,
	KILLER_WHALE,LION,LIZARD,MAMMOTH,MASTIFF,MULE,OWL,OX,PANTHER,PLESIOSAURUS,POISONOUS_SNAKE,POLAR_BEAR,PONY,PTERANODON,QUETZALCOATLUS,QUIPPER,RAT,RAVEN,REEF_SHARK,RHINOCEROS,
	RIDING_HORSE,ROTHE,SABERTOOTHED_TIGER,SCORPION,SPIDER,STEGOSAURUS,STIRGE,SWARM_OF_BATS,SWARM_OF_CRANIUM_RATS,SWARM_OF_INSECTS,SWARM_OF_POISONOUS_SNAKES,SWARM_OF_QUIPPERS,
	SWARM_OF_RATS,SWARM_OF_RAVENS,SWARM_OF_ROT_GRUBS,TIGER,TRICERATOPS,TYRANNOSAURUS_REX,VELOCIRAPTOR,VULTURE,WARHORSE,WOLF;
	
	private static HashMap<BiomeType,WeightedTable<Species>> habitats;
	
	public WeightedTable<Species> getSpecies(BiomeType type){
		if(habitats==null) populateHabitats();
		WeightedTable<Species> result = habitats.get(type);
		if(result==null) throw new IllegalStateException("Unrecognized Habitat Type:"+type.toString());
		return result;
	}
	
	private static void populateHabitats() {
		habitats = new HashMap<BiomeType,WeightedTable<Species>>();
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			list.put(OWL);
			list.put(BLOOD_HAWK);
			list.put(GIANT_OWL);
			list.put(BROWN_BEAR);
			list.put(POLAR_BEAR);
			list.put(SABERTOOTHED_TIGER);
			list.put(MAMMOTH);
			habitats.put(BiomeType.SNOW, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			list.put(CRAB);
			list.put(EAGLE);
			list.put(BLOOD_HAWK);
			list.put(DOLPHIN);
			list.put(GIANT_CRAB);
			list.put(POISONOUS_SNAKE);
			list.put(STIRGE);
			list.put(DIMETRODON);
			list.put(GIANT_LIZARD);
			list.put(GIANT_WOLF_SPIDER);
			list.put(PTERANODON);
			list.put(GIANT_EAGLE);
			list.put(GIANT_TOAD);
			list.put(PLESIOSAURUS);
			list.put(QUETZALCOATLUS);
			habitats.put(BiomeType.BEACH, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			list.put(CAT);
			list.put(HYENA);
			list.put(JACKAL);
			list.put(SCORPION);
			list.put(VULTURE);
			list.put(CAMEL);
			list.put(FLYING_SNAKE);
			list.put(MULE);
			list.put(POISONOUS_SNAKE);
			list.put(STIRGE);
			list.put(CONSTRICTOR_SNAKE);
			list.put(GIANT_LIZARD);
			list.put(GIANT_POISONOUS_SNAKE);
			list.put(GIANT_WOLF_SPIDER);
			list.put(SWARM_OF_INSECTS);
			list.put(GIANT_HYENA);
			list.put(GIANT_SPIDER);
			list.put(GIANT_TOAD);
			list.put(GIANT_VULTURE);
			list.put(LION);
			list.put(GIANT_CONSTRICTOR_SNAKE);
			list.put(GIANT_SCORPION);
			habitats.put(BiomeType.DESERT, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			list.put(BABOON);
			list.put(BADGER);
			list.put(CAT);
			list.put(DEER);
			list.put(HYENA);
			list.put(OWL);
			list.put(BLOOD_HAWK);
			list.put(FLYING_SNAKE);
			list.put(GIANT_RAT);
			list.put(GIANT_WEASEL);
			list.put(MASTIFF);
			list.put(POISONOUS_SNAKE);
			list.put(STIRGE);
			list.put(BOAR);
			list.put(CONSTRICTOR_SNAKE);
			list.put(ELK);
			list.put(GIANT_BADGER);
			list.put(GIANT_BAT);
			list.put(GIANT_FROG);
			list.put(GIANT_LIZARD);
			list.put(GIANT_OWL);
			list.put(GIANT_POISONOUS_SNAKE);
			list.put(GIANT_WOLF_SPIDER);
			list.put(PANTHER);
			list.put(SWARM_OF_RAVENS);
			list.put(VELOCIRAPTOR);
			list.put(WOLF);
			list.put(APE);
			list.put(BLACK_BEAR);
			list.put(GIANT_WASP);
			list.put(SWARM_OF_INSECTS);
			list.put(BROWN_BEAR);
			list.put(DEINONYCHUS);
			list.put(DIRE_WOLF);
			list.put(GIANT_HYENA);
			list.put(GIANT_SPIDER);
			list.put(GIANT_TOAD);
			list.put(TIGER);
			list.put(GIANT_BOAR);
			list.put(GIANT_CONSTRICTOR_SNAKE);
			list.put(GIANT_ELK);
			list.put(SWARM_OF_POISONOUS_SNAKES);
			list.put(STEGOSAURUS);
			list.put(BRONTOSAURUS);
			list.put(GIANT_APE);
			habitats.put(BiomeType.FOREST, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			list.put(CAT);
			list.put(DEER);
			list.put(EAGLE);
			list.put(GOAT);
			list.put(HYENA);
			list.put(JACKAL);
			list.put(VULTURE);
			list.put(BLOOD_HAWK);
			list.put(FLYING_SNAKE);
			list.put(GIANT_WEASEL);
			list.put(POISONOUS_SNAKE);
			list.put(STIRGE);
			list.put(AXE_BEAK);
			list.put(BOAR);
			list.put(ELK);
			list.put(GIANT_POISONOUS_SNAKE);
			list.put(GIANT_WOLF_SPIDER);
			list.put(HADROSAURUS);
			list.put(PANTHER);
			list.put(PTERANODON);
			list.put(RIDING_HORSE);
			list.put(ROTHE);
			list.put(VELOCIRAPTOR);
			list.put(WOLF);
			list.put(GIANT_GOAT);
			list.put(GIANT_WASP);
			list.put(SWARM_OF_INSECTS);
			list.put(DEINONYCHUS);
			list.put(GIANT_EAGLE);
			list.put(GIANT_HYENA);
			list.put(GIANT_VULTURE);
			list.put(LION);
			list.put(TIGER);
			list.put(ALLOSAURUS);
			list.put(AUROCHS);
			list.put(GIANT_BOAR);
			list.put(GIANT_ELK);
			list.put(RHINOCEROS);
			list.put(ANKYLOSAURUS);
			list.put(ELEPHANT);
			list.put(STEGOSAURUS);
			list.put(BRONTOSAURUS);
			list.put(TRICERATOPS);
			list.put(TYRANNOSAURUS_REX);
			habitats.put(BiomeType.GRASSLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			list.put(BABOON);
			list.put(EAGLE);
			list.put(GOAT);
			list.put(HYENA);
			list.put(RAVEN);
			list.put(VULTURE);
			list.put(BLOOD_HAWK);
			list.put(GIANT_WEASEL);
			list.put(MASTIFF);
			list.put(MULE);
			list.put(POISONOUS_SNAKE);
			list.put(STIRGE);
			list.put(AXE_BEAK);
			list.put(BOAR);
			list.put(ELK);
			list.put(GIANT_OWL);
			list.put(GIANT_WOLF_SPIDER);
			list.put(PANTHER);
			list.put(SWARM_OF_BATS);
			list.put(SWARM_OF_RAVENS);
			list.put(WOLF);
			list.put(GIANT_GOAT);
			list.put(SWARM_OF_INSECTS);
			list.put(BROWN_BEAR);
			list.put(DEINONYCHUS);
			list.put(DIRE_WOLF);
			list.put(GIANT_EAGLE);
			list.put(GIANT_HYENA);
			list.put(LION);
			list.put(AUROCHS);
			list.put(GIANT_BOAR);
			list.put(GIANT_ELK);
			list.put(QUETZALCOATLUS);
			habitats.put(BiomeType.ROCKYHILLS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			list.put(BABOON);
			list.put(LIZARD);
			list.put(SPIDER);
			list.put(FLYING_SNAKE);
			list.put(POISONOUS_SNAKE);
			list.put(STIRGE);
			list.put(AXE_BEAK);
			list.put(CONSTRICTOR_SNAKE);
			list.put(GIANT_FROG);
			list.put(GIANT_LIZARD);
			list.put(GIANT_POISONOUS_SNAKE);
			list.put(HADROSAURUS);
			list.put(PTERANODON);
			list.put(SWARM_OF_BATS);
			list.put(VELOCIRAPTOR);
			list.put(APE);
			list.put(GIANT_WASP);
			list.put(SWARM_OF_INSECTS);
			list.put(DEINONYCHUS);
			list.put(ALLOSAURUS);
			list.put(GIANT_BOAR);
			list.put(GIANT_CONSTRICTOR_SNAKE);
			list.put(SWARM_OF_POISONOUS_SNAKES);
			list.put(ANKYLOSAURUS);
			list.put(GIANT_SCORPION);
			list.put(STEGOSAURUS);
			list.put(BRONTOSAURUS);
			list.put(TRICERATOPS);
			list.put(GIANT_APE);
			list.put(TYRANNOSAURUS_REX);
			habitats.put(BiomeType.JUNGLE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			list.put(EAGLE);
			list.put(GOAT);
			list.put(BLOOD_HAWK);
			list.put(STIRGE);
			list.put(PTERANODON);
			list.put(SWARM_OF_BATS);
			list.put(GIANT_GOAT);
			list.put(GIANT_EAGLE);
			list.put(LION);
			list.put(AUROCHS);
			list.put(GIANT_ELK);
			list.put(QUETZALCOATLUS);
			list.put(SABERTOOTHED_TIGER);
			habitats.put(BiomeType.MOUNTAINS, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			list.put(RAT);
			list.put(RAVEN);
			list.put(GIANT_RAT);
			list.put(POISONOUS_SNAKE);
			list.put(STIRGE);
			list.put(CONSTRICTOR_SNAKE);
			list.put(DIMETRODON);
			list.put(GIANT_FROG);
			list.put(GIANT_LIZARD);
			list.put(GIANT_POISONOUS_SNAKE);
			list.put(HADROSAURUS);
			list.put(SWARM_OF_RATS);
			list.put(SWARM_OF_RAVENS);
			list.put(CROCODILE);
			list.put(SWARM_OF_INSECTS);
			list.put(SWARM_OF_ROT_GRUBS);
			list.put(GIANT_SPIDER);
			list.put(GIANT_TOAD);
			list.put(GIANT_CONSTRICTOR_SNAKE);
			list.put(SWARM_OF_POISONOUS_SNAKES);
			list.put(GIANT_CROCODILE);
			habitats.put(BiomeType.WETLAND, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			list.put(CRANIUM_RAT);
			list.put(GIANT_FIRE_BEETLE);
			list.put(GIANT_RAT);
			list.put(STIRGE);
			list.put(DEEP_ROTHE);
			list.put(GIANT_BAT);
			list.put(GIANT_CENTIPEDE);
			list.put(GIANT_LIZARD);
			list.put(GIANT_POISONOUS_SNAKE);
			list.put(SWARM_OF_BATS);
			list.put(SWARM_OF_INSECTS);
			list.put(SWARM_OF_ROT_GRUBS);
			list.put(GIANT_SPIDER);
			list.put(GIANT_TOAD);
			list.put(GIANT_CONSTRICTOR_SNAKE);
			list.put(POLAR_BEAR);
			list.put(SWARM_OF_CRANIUM_RATS);
			habitats.put(BiomeType.VOID, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			list.put(QUIPPER);
			list.put(DOLPHIN);
			list.put(CONSTRICTOR_SNAKE);
			list.put(GIANT_SEA_HORSE);
			list.put(REEF_SHARK);
			list.put(GIANT_OCTOPUS);
			list.put(SWARM_OF_QUIPPERS);
			list.put(GIANT_CONSTRICTOR_SNAKE);
			list.put(HUNTER_SHARK);
			list.put(PLESIOSAURUS);
			list.put(KILLER_WHALE);
			list.put(GIANT_SHARK);
			habitats.put(BiomeType.LAKE, list);
		}
		{
			WeightedTable<Species> list = new WeightedTable<Species>();
			list.put(CAT);
			list.put(CRANIUM_RAT);
			list.put(GOAT);
			list.put(RAT);
			list.put(RAVEN);
			list.put(FLYING_SNAKE);
			list.put(GIANT_RAT);
			list.put(MASTIFF);
			list.put(MULE);
			list.put(PONY);
			list.put(STIRGE);
			list.put(DRAFT_HORSE);
			list.put(GIANT_CENTIPEDE);
			list.put(GIANT_POISONOUS_SNAKE);
			list.put(OX);
			list.put(RIDING_HORSE);
			list.put(SWARM_OF_BATS);
			list.put(SWARM_OF_RATS);
			list.put(SWARM_OF_RAVENS);
			list.put(CROCODILE);
			list.put(GIANT_WASP);
			list.put(SWARM_OF_INSECTS);
			list.put(WARHORSE);
			list.put(GIANT_SPIDER);
			list.put(SWARM_OF_CRANIUM_RATS);
			habitats.put(BiomeType.CITY, list);
		}
	}

	@Override
	public IndexibleNameGenerator getNameGen() {
		return CreatureType.BEAST.getNameGen();
	}
	
	




}
