package babydontherdme;

import babydontherdme.access.WolfEntityMixinInterface;
import babydontherdme.item.ModItems;
import babydontherdme.sound.ModSoundEvents;
import babydontherdme.world.event.ModGameEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.network.message.MessageType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Entrypoint implements ModInitializer {
	public static final String MODID = "baby_dont_herd_me";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final Identifier WHISTLE_PACKET = identify("whistle");

	@Override
	public void onInitialize() {
		LOGGER.info("What is love?");
		ModItems.initialize();
		ModSoundEvents.initialize();
		ModGameEvent.initialize();
		ServerPlayNetworking.registerGlobalReceiver(WHISTLE_PACKET,
				(server, player,handler,buf,response)->server.execute(()->{
					player.emitGameEvent(ModGameEvent.PLAYER_WHISTLE);
					player.getWorld().playSoundFromEntity(null, player, ModSoundEvents.WHISTLE, SoundCategory.PLAYERS, 2.0f, 1.0f);
					int simD = server.getPlayerManager().getSimulationDistance();
					List<WolfEntity> dogs =  player.getWorld().getEntitiesByClass(WolfEntity.class,
							new Box(player.getPos().getX()-simD,-64,player.getPos().getZ()-simD,
									player.getPos().getX()+simD,320,player.getPos().getZ()+simD),
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
