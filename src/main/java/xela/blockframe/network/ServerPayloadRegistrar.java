package xela.blockframe.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xela.blockframe.network.payloads.records.ServerBoundMovementPayload;

import java.util.UUID;
/*
The server payload register actually registers and defines what to do when a packet is inbound
 */
public class ServerPayloadRegistrar {
    static public  void init(){
        registerDoubleJump();
    }


    static private void registerDoubleJump(){
        ServerPlayNetworking.registerGlobalReceiver(ServerBoundMovementPayload.TYPE, ((payload, context) -> {

            Log.info( LogCategory.LOG, "[SERVER] received packet");
            Entity entity = context.player().level().getEntity(UUID.fromString(payload.vecPayload().UUID));

            if (entity instanceof LivingEntity livingEntity){
                Log.info(LogCategory.LOG, "[SERVER] pushing with " + payload.vecPayload().pushVector.toString());
                livingEntity.push(payload.vecPayload().pushVector);
                livingEntity.getDeltaMovement().add(payload.vecPayload().pushVector);
                livingEntity.hurtMarked = true;
            }
        }));
    }

}
