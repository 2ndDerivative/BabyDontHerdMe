package babydontherdme.item;

import babydontherdme.Entrypoint;
import babydontherdme.block.ModBlocks;
import babydontherdme.mixin.invoker.MusicDiscItemMixin;
import babydontherdme.sound.ModSoundEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
public class ModItems {
    public static final Item MUSIC_DISC_SHEEP;
    public static final Item CROOK;
    public static final Item RENNET;
    public static final Item RAW_CHEESE;
    public static final Item SLIGHTLY_AGED_CHEESE;
    public static final Item AGED_CHEESE;
    public static final Item RIPE_CHEESE;
    public static final Item CHEESE_SLICE;
    static {
        CROOK = register("crook",new Item(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).maxDamage(1)));
        MUSIC_DISC_SHEEP =  register("music_disc_sheep", MusicDiscItemMixin.newDisk(15, ModSoundEvents.MUSIC_DISC_SHEEP, new Item.Settings().maxCount(1).group(ItemGroup.MISC).rarity(Rarity.RARE),130));
        RENNET = register("rennet", new Item(new FabricItemSettings().group(ItemGroup.MISC)));
        RAW_CHEESE = register("raw_cheese", new BlockItem(ModBlocks.RAW_CHEESE, new FabricItemSettings().group(ItemGroup.FOOD)));
        SLIGHTLY_AGED_CHEESE = register("slightly_aged_cheese", new BlockItem(ModBlocks.SLIGHTLY_AGED_CHEESE,new FabricItemSettings().group(ItemGroup.FOOD)));
        AGED_CHEESE = register("aged_cheese", new BlockItem(ModBlocks.AGED_CHEESE, new FabricItemSettings().group(ItemGroup.FOOD)));
        RIPE_CHEESE = register("ripe_cheese", new BlockItem(ModBlocks.RIPE_CHEESE, new FabricItemSettings().group(ItemGroup.FOOD)));
        CHEESE_SLICE = register("cheese_slice", new Item(new FabricItemSettings().group(ItemGroup.FOOD)));
    }
    public static void initialize(){
        Entrypoint.LOGGER.info("Registering Items.");
    }
    private static Item register(String id, Item item) {
        return register(Entrypoint.identify(id), item);
    }

    private static Item register(Identifier id, Item item) {
        return Registry.register(Registry.ITEM, id, item);
    }
}
