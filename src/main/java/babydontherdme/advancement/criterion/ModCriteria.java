package babydontherdme.advancement.criterion;

import net.minecraft.advancement.criterion.Criteria;

public class ModCriteria {
    public static final HerdingWolfCriterion HERDED_ANIMALS_WITH_WOLF = Criteria.register(new HerdingWolfCriterion());

    public static void initialize(){
        //Entrypoint.LOGGER.info("Registering Criteria.");
    }
}
