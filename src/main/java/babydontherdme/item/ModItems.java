package babydontherdme.item;

import babydontherdme.Entrypoint;
import babydontherdme.mixin.invoker.MusicDiscItemMixin;
import babydontherdme.sound.ModSoundEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final Item MUSIC_DISC_SHEEP;
    public static final Item CROOK;
    static {
        CROOK = register("crook",new Item(new FabricItemSettings().maxCount(1).maxDamage(1)));
        MUSIC_DISC_SHEEP =  register("music_disc_sheep", MusicDiscItemMixin.newDisk(15, ModSoundEvents.MUSIC_DISC_SHEEP, new Item.Settings().maxCount(1).rarity(Rarity.RARE),130));
    }
    public static void initialize(){
        Entrypoint.LOGGER.info("Registering Items.");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((content) -> {
            content.add(CROOK);
            content.addAfter(Items.MUSIC_DISC_PIGSTEP, MUSIC_DISC_SHEEP);
        });
    }
    private static Item register(String id, Item item) {
        return register(Entrypoint.identify(id), item);
    }

    private static Item register(Identifier id, Item item) {
        return Registry.register(Registries.ITEM, id, item);
    }
}
