package xela.blockframe.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import xela.blockframe.network.payloads.records.ServerBoundMovementPayload;

/*
The client registrar handles the Registry of payloads
 */
public class ClientPayloadRegistrar {
    public static void init(){
        PayloadTypeRegistry.serverboundPlay().register(ServerBoundMovementPayload.TYPE, ServerBoundMovementPayload.CODEC);
    }
}
