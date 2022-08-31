package babydontherdme;

import babydontherdme.access.WolfEntityMixinInterface;
import babydontherdme.item.ModItems;
import babydontherdme.sound.ModSoundEvents;
import babydontherdme.world.event.ModGameEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Entrypoint implements ModInitializer {
	public static final String MODID = "baby_dont_herd_me";
	public static final String MODNAME = "Baby Don't Herd Me";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODNAME);

	public static final Identifier WHISTLE_PACKET = identify("whistle");
	public static final double WOLF_WHISTLE_RANGE = 50.0;

	@Override
	public void onInitialize() {
		LOGGER.info("What is love?");
		ModItems.initialize();
		ModSoundEvents.initialize();
		ModGameEvent.initialize();
		ServerPlayNetworking.registerGlobalReceiver(WHISTLE_PACKET,
				(server, player,handler,buf,response)->server.execute(()->{
					player.emitGameEvent(ModGameEvent.PLAYER_WHISTLE);
					player.getWorld().playSoundFromEntity(null, player, ModSoundEvents.WHISTLE, SoundCategory.PLAYERS, 4.0f, 1.0f);
					List<WolfEntity> dogs =  player.getWorld().getEntitiesByClass(WolfEntity.class,
							player.getBoundingBox().expand(WOLF_WHISTLE_RANGE, 10.0, WOLF_WHISTLE_RANGE),
							(wolf)->wolf.isOwner(player));
					for(WolfEntity dog : dogs) {
						((WolfEntityMixinInterface) dog).setHerdingTime(0);
					}
				}));
	}
	public static Identifier identify(String id){
		return new Identifier(MODID + ":" + id);
	}
}
