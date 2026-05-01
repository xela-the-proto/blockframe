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


public class DoubleJumpRegister {
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(BlockFrame.MOD_ID, "blockframe"));
    public static final String KEY_DOUBLE_JUMP   = "key.blockframe.double_jump";
    public static KeyMapping doubleJump = KeyMappingHelper.registerKeyMapping( new KeyMapping(
            KEY_DOUBLE_JUMP,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_SPACE,
            CATEGORY
    ));


    public static boolean hasJumped = false;
    public static boolean resetFlag = false;

    public static void registerDoubleJumpKeybind(){

        try {
            ClientTickEvents.END_CLIENT_TICK.register(client ->{

                if (client.player != null){
                    var blockpos = client.player.blockPosition().below();
                    double verticalDistance = client.player.position().y - blockpos.getY()-1;


                    if (verticalDistance == 0){
                        resetFlag = true;
                    }

                    while (DoubleJumpRegister.doubleJump.consumeClick()){
                        //PLayer jumped so next input we send a packet to push the player
                        if (hasJumped && verticalDistance != 0){
                            var longArray = new VectorPayload();
                            longArray.UUID = client.player.getStringUUID();
                            longArray.pushVector = new Vec3(0,0.25,0);

                            var payload = new ServerBoundMovementPayload(longArray);
                            //Send double jump
                            ClientPlayNetworking.send(payload);
                            hasJumped = false;
                            resetFlag = false;
                        }else if (resetFlag){
                            //reset jump
                            hasJumped = true;
                        }
                    }
                }

            });
        } catch (NullPointerException e) {
            BlockFrame.LOGGER.error("Player doesnt have double jump data!");
        }
    }
}
