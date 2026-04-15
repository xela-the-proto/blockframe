package xela.blockframe.client.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class PayloadRegistrar {
    public static void init(){
        PayloadTypeRegistry.serverboundPlay().register(ServerBoundMovementPayload.TYPE, ServerBoundMovementPayload.CODEC);
    }
}
