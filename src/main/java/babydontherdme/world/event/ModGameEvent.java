package babydontherdme.world.event;

import babydontherdme.BabyDontHerdMe;
import net.fabricmc.fabric.api.registry.SculkSensorFrequencyRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.event.GameEvent;

public class ModGameEvent {
    public static final RegistryEntry.Reference<GameEvent> PLAYER_WHISTLE_RECALL = register("recall");
    public static final RegistryEntry.Reference<GameEvent> PLAYER_WHISTLE_GO = register("go");

    private static RegistryEntry.Reference<GameEvent> register(String id){
        return Registry.registerReference(Registries.GAME_EVENT, BabyDontHerdMe.identify(id), new GameEvent(32));
    }
    public static void initialize(){
        BabyDontHerdMe.LOGGER.info("Registering GameEvents.");
        RegistryKey<GameEvent> player_whistle_recall = RegistryKey.of(RegistryKeys.GAME_EVENT, BabyDontHerdMe.identify("recall"));
        SculkSensorFrequencyRegistry.register(player_whistle_recall, 15);
        RegistryKey<GameEvent> player_whistle_go = RegistryKey.of(RegistryKeys.GAME_EVENT, BabyDontHerdMe.identify("go"));
        SculkSensorFrequencyRegistry.register(player_whistle_go, 15);
    }
}
