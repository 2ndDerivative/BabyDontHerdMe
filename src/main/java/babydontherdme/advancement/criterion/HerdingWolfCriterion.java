package babydontherdme.advancement.criterion;

import babydontherdme.Entrypoint;
import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class HerdingWolfCriterion extends AbstractCriterion<HerdingWolfCriterion.Conditions> {
    public static final Identifier ID = Entrypoint.identify("herded_animals");

    public HerdingWolfCriterion(){}

    public HerdingWolfCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended extended, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        NumberRange.IntRange intRange = NumberRange.IntRange.fromJson(jsonObject.get("number"));
        return new HerdingWolfCriterion.Conditions(extended, intRange);
    }
    public void trigger(ServerPlayerEntity player, int number){
        this.trigger(player, (conditions) -> conditions.inRange(number));
    }

    public Identifier getId(){return ID;}

    public static class Conditions extends AbstractCriterionConditions {
        private final NumberRange.IntRange number;

        public Conditions(EntityPredicate.Extended player, NumberRange.IntRange intRange) {
            super(ID, player);
            this.number = intRange;
        }
        public boolean inRange(int animals) {
            return number.test(animals);
        }
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject jsonObject = super.toJson(predicateSerializer);
            jsonObject.add("number", this.number.toJson());
            return jsonObject;
        }
    }
}
