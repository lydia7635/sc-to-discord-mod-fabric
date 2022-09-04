package net.lydia7635.screenshottodiscord;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScreenshotToDiscord implements ClientModInitializer {
	public static final String MOD_ID = "screenshottodiscord";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("Hello Fabric world!");
	}
}
