package babydontherdme.advancement.criterion;

import babydontherdme.BabyDontHerdMe;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModCriteria {
    public static final HerdingWolfCriterion HERDED_ANIMALS_WITH_WOLF = register("herded_animals_with_wolf", new HerdingWolfCriterion());

	private static <T extends Criterion<?>> T register(String id, T criterion) {
		return Registry.register(Registries.CRITERION, BabyDontHerdMe.identify(id), criterion);
	}
    public static void initialize(){}
}
