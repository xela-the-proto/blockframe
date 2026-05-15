package xela.blockframe.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xela.blockframe.BlockFrame;
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

            BlockFrame.LOGGER.debug("[SERVER] received packet");
            Entity entity = context.player().level().getEntity(UUID.fromString(payload.vecPayload().UUID));

            if (entity instanceof LivingEntity livingEntity && !entity.level().isClientSide()){
                BlockFrame.LOGGER.debug("[SERVER] pushing with " + payload.vecPayload().pushVector.toString() + " Typeof:"+ payload.vecPayload().typeof);
                livingEntity.push(payload.vecPayload().pushVector);
                livingEntity.getDeltaMovement().add(payload.vecPayload().pushVector);
                livingEntity.hurtMarked = true;
                livingEntity.playSound(SoundEvents.SAND_PLACE, 10.0F, 1.0F);
            }
        }));
    }

}
