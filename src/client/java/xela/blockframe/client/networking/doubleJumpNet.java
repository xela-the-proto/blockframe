package xela.blockframe.client.networking;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;
import xela.blockframe.client.events.KeyHandler;

import java.util.List;

public class doubleJumpNet {

    public static void init(){
        registerDoubleJumpKeybind();
    }

    public static void registerDoubleJumpKeybind(){
        ClientTickEvents.END_CLIENT_TICK.register(client ->{
            while (KeyHandler.doubleJump.consumeClick()){
                if (client.player != null){
                    client.player.sendSystemMessage(Component.literal("sending"));
                }
            }
        });
    }

    private record ServerBoundMovementPayload(List<Vec3> pushVectors)implements CustomPacketPayload {

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return null;
        }
    }
}
