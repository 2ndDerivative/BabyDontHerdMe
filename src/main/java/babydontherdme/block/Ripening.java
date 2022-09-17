package babydontherdme.block;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.block.*;

public interface Ripening extends Degradable<Ripening.RipenessLevel> {
    Supplier<BiMap<Block, Block>> RIPENESS_LEVEL_INCREASES = Suppliers.memoize(() -> ImmutableBiMap.<Block,Block>builder()
            .put(ModBlocks.RAW_CHEESE, ModBlocks.SLIGHTLY_AGED_CHEESE)
            .put(ModBlocks.SLIGHTLY_AGED_CHEESE, ModBlocks.AGED_CHEESE)
            .put(ModBlocks.AGED_CHEESE, ModBlocks.RIPE_CHEESE).build());

    static Optional<Block> getIncreasedRipenessBlock(Block block) {
        return Optional.ofNullable((Block)((BiMap<?, ?>)RIPENESS_LEVEL_INCREASES.get()).get(block));
    }

    default Optional<BlockState> getDegradationResult(BlockState state) {
        return getIncreasedRipenessBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
    }

    default float getDegradationChanceMultiplier() {
        return this.getDegradationLevel() == Ripening.RipenessLevel.RAW ? 0.75F : 1.0F;
    }

    enum RipenessLevel {
        RAW,
        SLIGHTLY_AGED,
        AGED,
        RIPE;

        RipenessLevel() {
        }
    }
}
