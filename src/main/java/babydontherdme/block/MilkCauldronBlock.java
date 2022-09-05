package babydontherdme.block;

import babydontherdme.block.cauldron.ModCauldronBehavior;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;

public class MilkCauldronBlock extends AbstractCauldronBlock {
    public MilkCauldronBlock(Settings settings) {
        super(settings, ModCauldronBehavior.MILK_CAULDRON_BEHAVIOR);
    }

    public boolean isFull(BlockState state) {
        return true;
    }
}
