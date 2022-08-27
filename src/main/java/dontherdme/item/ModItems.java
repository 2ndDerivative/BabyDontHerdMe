package dontherdme.item;

import dontherdme.Entrypoint;
import dontherdme.mixin.invoker.MusicDiscItemMixin;
import dontherdme.sound.ModSoundEvents;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
public class ModItems {
    public static final Item MUSIC_DISC_SHEEP;
    static {

        MUSIC_DISC_SHEEP =  register("music_disc_sheep", MusicDiscItemMixin.newDisk(15, ModSoundEvents.MUSIC_DISC_SHEEP, new Item.Settings().maxCount(1).group(ItemGroup.MISC).rarity(Rarity.RARE),130));
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
