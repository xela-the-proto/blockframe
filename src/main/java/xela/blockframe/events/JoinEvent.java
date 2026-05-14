package xela.blockframe.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.world.InteractionResult;
import xela.blockframe.BlockFrame;

import static xela.blockframe.data.DataAttachments.DOUBLE_JUMP_DATA_ATTACHMENT;

public class JoinEvent {
    public static void registerJoinEvent(){
        ServerPlayerEvents.JOIN.register((player) -> {
            BlockFrame.LOGGER.info("[BlockFrame] Attaching double jump data");
            if (!player.hasAttached(DOUBLE_JUMP_DATA_ATTACHMENT)) {
                player.getAttachedOrCreate(DOUBLE_JUMP_DATA_ATTACHMENT);
                player.setAttached(DOUBLE_JUMP_DATA_ATTACHMENT, false);

            }
        });
    }
}
