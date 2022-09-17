package babydontherdme.block;

import babydontherdme.Entrypoint;
import babydontherdme.block.cauldron.ModCauldronBehavior;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block MILK_CAULDRON;
    public static final Block RAW_CHEESE;
    public static final Block SLIGHTLY_AGED_CHEESE;
    public static final Block AGED_CHEESE;
    public static final Block RIPE_CHEESE;

    public static void initialize(){
        Entrypoint.LOGGER.info("Initializing Blocks.");
        ModCauldronBehavior.registerCauldronBehaviour();
    }

    private static Block register(String id, Block block){
        return Registry.register(Registry.BLOCK, Entrypoint.identify(id), block);
    }
    static{
        MILK_CAULDRON = register("milk_cauldron", new MilkCauldronBlock(FabricBlockSettings.copy(Blocks.CAULDRON).ticksRandomly()));
        RAW_CHEESE = register("raw_cheese", new CheeseBlock(Ripening.RipenessLevel.RAW, FabricBlockSettings.copy(Blocks.CAKE).ticksRandomly()));
        SLIGHTLY_AGED_CHEESE = register("slightly_aged_cheese", new CheeseBlock(Ripening.RipenessLevel.SLIGHTLY_AGED, FabricBlockSettings.copy(Blocks.CAKE).ticksRandomly()));
        AGED_CHEESE = register("aged_cheese", new CheeseBlock(Ripening.RipenessLevel.AGED, FabricBlockSettings.copy(Blocks.CAKE).ticksRandomly()));
        RIPE_CHEESE = register("ripe_cheese", new EdibleCheeseBlock(FabricBlockSettings.copy(Blocks.CAKE)));
    }
}
