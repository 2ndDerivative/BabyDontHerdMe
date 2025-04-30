package babydontherdme;

import babydontherdme.access.WolfEntityMixinInterface;
import babydontherdme.advancement.criterion.ModCriteria;
import babydontherdme.block.jukebox.ModJukeboxSongs;
import babydontherdme.item.ModItems;
import babydontherdme.sound.ModSoundEvents;
import babydontherdme.whistle.GoPayload;
import babydontherdme.whistle.RecallPayload;
import babydontherdme.world.event.ModGameEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.CustomPayload.Id;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.event.GameEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BabyDontHerdMe implements ModInitializer {
	public static final String MODID = "baby_dont_herd_me";
	public static final String MODNAME = "Baby Don't Herd Me";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODNAME);

	public static final double WOLF_WHISTLE_RANGE = 50.0;

	@Override
	public void onInitialize() {
		LOGGER.info("What is love?");
		ModItems.initialize();
		ModSoundEvents.initialize();
		ModGameEvent.initialize();
		ModCriteria.initialize();
		ModJukeboxSongs.initialize();
		setWhistle(RecallPayload.ID, RecallPayload.CODEC, ModGameEvent.PLAYER_WHISTLE_RECALL, ModSoundEvents.RECALL, 0);
		setWhistle(GoPayload.ID, GoPayload.CODEC, ModGameEvent.PLAYER_WHISTLE_GO, ModSoundEvents.GO_WHISTLE, 1200);
	}
	public static Identifier identify(String id){
		return Identifier.of(MODID, id);
	}
	private <T extends CustomPayload> void setWhistle(Id<T> payloadId, PacketCodec<RegistryByteBuf, T> codec, RegistryEntry<GameEvent> gameEvent, SoundEvent soundEvent, int setWolfTime) {
		PayloadTypeRegistry.playC2S().register(payloadId, codec);
		ServerPlayNetworking.registerGlobalReceiver(payloadId,
				(t, context)->context.server().execute(()->{
					ServerPlayerEntity player = context.player();
					player.emitGameEvent(gameEvent);
					player.getWorld().playSoundFromEntity(player, player, soundEvent, SoundCategory.PLAYERS, 4.0f, 1.0f);
					player.playSoundToPlayer(soundEvent, SoundCategory.PLAYERS, 4.0f, 1.0f);
					List<WolfEntity> dogs = player.getWorld().getEntitiesByClass(WolfEntity.class,
							player.getBoundingBox().expand(WOLF_WHISTLE_RANGE, 10.0, WOLF_WHISTLE_RANGE),
							(wolf)->wolf.isOwner(player));
					for(WolfEntity dog : dogs) {
						((WolfEntityMixinInterface) dog).setHerdingTime(setWolfTime);
					}
				}));
	}
}
