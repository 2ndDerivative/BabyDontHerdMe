package babydontherdme.world.event;

import babydontherdme.Entrypoint;
import net.fabricmc.fabric.api.registry.SculkSensorFrequencyRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;

public class ModGameEvent {
    public static final GameEvent PLAYER_WHISTLE = register("whistle");

    private static GameEvent register(String id){
        return Registry.register(Registry.GAME_EVENT, Entrypoint.identify(id), new GameEvent(id, 32));
    }
    public static void initialize(){
        Entrypoint.LOGGER.info("Registering GameEvents.");
        SculkSensorFrequencyRegistry.register(PLAYER_WHISTLE, 15);
    }
}
