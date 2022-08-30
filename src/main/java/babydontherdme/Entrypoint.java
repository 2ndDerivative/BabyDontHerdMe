package babydontherdme;

import babydontherdme.item.ModItems;
import babydontherdme.sound.ModSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Entrypoint implements ModInitializer {
	public static final String MODID = "baby_dont_herd_me";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		LOGGER.info("What is love?");
		ModItems.initialize();
		ModSoundEvents.initialize();
	}
	public static Identifier identify(String id){
		return new Identifier(MODID + ":" + id);
	}
}
