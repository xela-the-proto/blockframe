package xela.blockframe.client.events;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import xela.blockframe.BlockFrame;
import xela.blockframe.client.networking.ServerBoundMovementPayload;

public class KeyHandler {
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(BlockFrame.MOD_ID, "blockframe"));
    public static final String KEY_DOUBLE_JUMP   = "key.blockframe.double_jump";
    public static KeyMapping doubleJump = KeyMappingHelper.registerKeyMapping( new KeyMapping(
            KEY_DOUBLE_JUMP,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_SPACE,
            CATEGORY
    ));

    public static void init(){
        registerDoubleJumpKeybind();
    }

    public static void registerDoubleJumpKeybind(){
        ClientTickEvents.END_CLIENT_TICK.register(client ->{
            while (KeyHandler.doubleJump.consumeClick()){
                if (client.player != null){
                    client.player.sendSystemMessage(Component.literal("sending"));
                    var longArray = new long[0,0,0];
                    var payload = new ServerBoundMovementPayload(longArray);
                    ClientPlayNetworking.send(payload);


                }
            }
        });
    }


}
