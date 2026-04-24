package xela.blockframe.client.events.doublejumpkey;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;
import xela.blockframe.BlockFrame;
import xela.blockframe.network.payloads.classes.VectorPayload;
import xela.blockframe.network.payloads.records.ServerBoundMovementPayload;

import static xela.blockframe.data.DataAttachments.DOUBLE_JUMP_DATA_ATTACHMENT;


public class DoubleJumpRegister {
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(BlockFrame.MOD_ID, "blockframe"));
    public static final String KEY_DOUBLE_JUMP   = "key.blockframe.double_jump";
    public static KeyMapping doubleJump = KeyMappingHelper.registerKeyMapping( new KeyMapping(
            KEY_DOUBLE_JUMP,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_SPACE,
            CATEGORY
    ));



    public static void registerDoubleJumpKeybind(){

        ClientTickEvents.END_CLIENT_TICK.register(client ->{

            if (client.player != null){

                while (DoubleJumpRegister.doubleJump.consumeClick()){
                    BlockFrame.LOGGER.info(client.player.getAttached(DOUBLE_JUMP_DATA_ATTACHMENT).toString());
                    //TODO: Detection is wonky and doesnt feel smooth
                    if (client.player != null && Boolean.TRUE.equals(client.player.getAttached(DOUBLE_JUMP_DATA_ATTACHMENT))){
                        var longArray = new VectorPayload();
                        longArray.UUID = client.player.getStringUUID();
                        longArray.pushVector = new Vec3(0,0.25,0);

                        var payload = new ServerBoundMovementPayload(longArray);
                        //Send double jump
                        ClientPlayNetworking.send(payload);
                    }
                }

                if (!client.player.onGround()){
                     client.player.setAttached(DOUBLE_JUMP_DATA_ATTACHMENT, false);
                    BlockFrame.LOGGER.info("Attachment to false");
                }else if (client.player.getDeltaMovement().y > 0 && Boolean.TRUE.equals(client.player.getAttached(DOUBLE_JUMP_DATA_ATTACHMENT))){
                    client.player.setAttached(DOUBLE_JUMP_DATA_ATTACHMENT, true);
                }
            }

        });
    }
}
