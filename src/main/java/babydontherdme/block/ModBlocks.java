package babydontherdme.block;

import babydontherdme.Entrypoint;
import babydontherdme.block.cauldron.ModCauldronBehavior;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block MILK_CAULDRON = register("milk_cauldron", new MilkCauldronBlock(AbstractBlock.Settings.copy(Blocks.CAULDRON)));

    public static void initialize(){
        Entrypoint.LOGGER.info("Initializing Blocks.");
        CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, ModCauldronBehavior.FILL_WITH_MILK);
    }

    private static Block register(String id, Block block){
        return Registry.register(Registry.BLOCK, Entrypoint.identify(id), block);
    }
}
