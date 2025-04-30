package babydontherdme.advancement.criterion;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;

public class HerdingWolfCriterion extends AbstractCriterion<HerdingWolfCriterion.Conditions> {
    
    public void trigger(ServerPlayerEntity player, int number){
        this.trigger(player, conditions -> conditions.test(number));
    }

    public record Conditions(Optional<LootContextPredicate> player, NumberRange.IntRange sheepCount) implements AbstractCriterion.Conditions {
        public static final Codec<HerdingWolfCriterion.Conditions> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(HerdingWolfCriterion.Conditions::player),
                NumberRange.IntRange.CODEC.optionalFieldOf("sheepCount", NumberRange.IntRange.ANY).forGetter(HerdingWolfCriterion.Conditions::sheepCount)
            )
            .apply(instance, HerdingWolfCriterion.Conditions::new)
        );

        public static AdvancementCriterion<HerdingWolfCriterion.Conditions> create(
            NumberRange.IntRange sheepRange
        ) {
            return ModCriteria.HERDED_ANIMALS_WITH_WOLF.create(new HerdingWolfCriterion.Conditions(Optional.empty(), sheepRange));
        }

        public boolean test(int animals) {
            return sheepCount.test(animals);
        }
        @Override
        public Optional<LootContextPredicate> player() {
            return player;
        }
    }

    @Override
    public Codec<Conditions> getConditionsCodec() {
        return Conditions.CODEC;
    }
}
