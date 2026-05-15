package xela.blockframe.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xela.blockframe.BlockFrame;
import xela.blockframe.network.payloads.records.ServerBoundMovementPayload;

import java.util.UUID;

///The server payload register actually registers and defines what to do when a packet is inbound
/// It's important to note: the server HANDLES MOVEMENT!
/// For example, the player starts sprinting, untile the server gives the okey to the clint the player CANNOT SPRINT
/// This is as far as i understand whats happening

public class ServerPayloadRegistrar {
    static public  void init(){
        registerDoubleJump();
    }


    static private void registerDoubleJump(){
        ServerPlayNetworking.registerGlobalReceiver(ServerBoundMovementPayload.TYPE, ((payload, context) -> {

            BlockFrame.LOGGER.debug("[SERVER] received packet");
            Entity entity = context.player().level().getEntity(UUID.fromString(payload.vecPayload().UUID));

            if (entity instanceof ServerPlayer && !entity.level().isClientSide()){
                BlockFrame.LOGGER.debug("[SERVER] pushing with {} Typeof:{}", payload.vecPayload().pushVector.toString(), payload.vecPayload().typeof);
                entity.push(payload.vecPayload().pushVector);

                //Get the player entity as a generic Entity class to get connection (?)
                ((ServerPlayer)entity).connection.send(new ClientboundSetEntityMotionPacket(entity));
                BlockFrame.LOGGER.debug("[SERVER] delta movement {} ",  entity.getDeltaMovement());

                entity.playSound(SoundEvents.SAND_PLACE, 10.0F, 1.0F);
            }
        }));
    }

}
