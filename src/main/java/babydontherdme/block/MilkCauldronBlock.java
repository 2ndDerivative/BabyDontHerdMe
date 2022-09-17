package babydontherdme.block;

import babydontherdme.block.cauldron.ModCauldronBehavior;
import babydontherdme.item.ModItems;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class MilkCauldronBlock extends AbstractCauldronBlock {
    public static BooleanProperty AGING = BooleanProperty.of("aging");
    public MilkCauldronBlock(Settings settings) {
        super(settings, ModCauldronBehavior.MILK_CAULDRON_BEHAVIOR);
        this.setDefaultState(this.stateManager.getDefaultState().with(Properties.AGE_3, 0).with(AGING, false));
    }

    public boolean isFull(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(state.get(AGING)){
            int age = state.get(Properties.AGE_3);
            if(age < 3){
                world.setBlockState(pos, state.with(Properties.AGE_3, age + 1));
            }
        }
    }
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGING) && state.get(Properties.AGE_3) < 3;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.AGE_3, AGING);
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return 3;
    }

    public static ActionResult enterRennet(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
        if (state.get(AGING)) {
            return ActionResult.PASS;
        } else {
            world.setBlockState(pos, state.with(AGING, true));
            return ActionResult.SUCCESS;
        }
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit){
        if(state.get(Properties.AGE_3) ==3){
            world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
            player.giveItemStack(new ItemStack(ModItems.RAW_CHEESE));
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
