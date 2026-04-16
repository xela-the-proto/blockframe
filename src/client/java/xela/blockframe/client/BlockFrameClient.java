package xela.blockframe.client;

import net.fabricmc.api.ClientModInitializer;
import xela.blockframe.BlockFrame;
import xela.blockframe.client.events.KeyHandler;

public class BlockFrameClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockFrame.LOGGER.info(":3");
		KeyHandler.init();
	}
}