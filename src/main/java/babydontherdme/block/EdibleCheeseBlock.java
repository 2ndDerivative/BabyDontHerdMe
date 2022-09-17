package babydontherdme.block;

import babydontherdme.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class EdibleCheeseBlock extends CheeseBlock{
    public static final int MAX_BITES = 6;
    public static final IntProperty BITES;
    protected static final VoxelShape[] BITES_TO_SHAPE;
    public EdibleCheeseBlock(AbstractBlock.Settings settings) {
        super(RipenessLevel.RIPE, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(BITES, 0));
    }
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int i = state.get(BITES);
        if (i < 6) {
            world.setBlockState(pos, state.with(BITES, i + 1), 3);
        } else {
            world.removeBlock(pos, false);
            world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
        }
        player.giveItemStack(new ItemStack(ModItems.CHEESE_SLICE));
        return ActionResult.SUCCESS;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BITES_TO_SHAPE[state.get(BITES)];
    }
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return getComparatorOutput(state.get(BITES));
    }
    public static int getComparatorOutput(int bites) {
        return (7 - bites) * 2;
    }
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
    static {
        BITES = IntProperty.of("bites", 0, MAX_BITES);
        BITES_TO_SHAPE = new VoxelShape[]{Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.createCuboidShape(3.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.createCuboidShape(5.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.createCuboidShape(7.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.createCuboidShape(9.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.createCuboidShape(11.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.createCuboidShape(13.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D)};
    }
}
