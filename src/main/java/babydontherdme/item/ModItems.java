package babydontherdme.item;

import java.util.function.Function;

import babydontherdme.BabyDontHerdMe;
import babydontherdme.block.jukebox.ModJukeboxSongs;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final Item MUSIC_DISC_SHEEP = register("music_disc_sheep", Item::new, new Item.Settings()
        .maxCount(1).rarity(Rarity.RARE)
        .jukeboxPlayable(ModJukeboxSongs.SHEEP));
    public static final Item CROOK = register("crook", Item::new, new Item.Settings().maxCount(1).maxDamage(1));

    public static void initialize(){
        BabyDontHerdMe.LOGGER.info("Registering Items.");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((content) -> {
            content.add(CROOK);
            content.addAfter(Items.MUSIC_DISC_PIGSTEP, MUSIC_DISC_SHEEP);
        });
    }

    private static Item register(String raw_id, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, BabyDontHerdMe.identify(raw_id));
        return Items.register(registryKey, factory, settings);
    }
}
