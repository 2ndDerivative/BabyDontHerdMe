package dontherdme;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Entrypoint implements ModInitializer {
	public static final String MODID = "dont_herd_me";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		LOGGER.info("What is love?");
	}
}
