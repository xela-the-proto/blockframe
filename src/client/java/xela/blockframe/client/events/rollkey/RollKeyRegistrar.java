package xela.blockframe.client.events.rollkey;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;
import xela.blockframe.BlockFrame;
import xela.blockframe.network.payloads.classes.VectorPayload;
import xela.blockframe.network.payloads.records.ServerBoundMovementPayload;

import static xela.blockframe.client.events.doublejumpkey.DoubleJumpRegistrar.CATEGORY;

public class RollKeyRegistrar {


    public static final String KEY_ROLL = "key.blockframe.roll";
    public static KeyMapping Roll = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            KEY_ROLL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_SHIFT,
            //Category can only be specified in 1 keybind file, else at startup it faisl to launch mc
            CATEGORY
    ));

    private static int landingCooldown = 20;
    private static int ticksPassed = 0;
    private static boolean hasBeenPressed = false;

    public static void registerRollKeybind(){
        ClientTickEvents.END_CLIENT_TICK.register(client ->{
            ticksPassed++;
            if (landingCooldown > 0){
                while(Roll.consumeClick());
                landingCooldown = landingCooldown - 1;
            }else if ( landingCooldown <= 0){
                while (Roll.consumeClick()) {
                    //TODO: is this the sweetspot?
                    if (hasBeenPressed && ticksPassed < 15 && client.player != null) {
                        landingCooldown = 20;
                        var payload = new VectorPayload();
                        payload.UUID = client.player.getStringUUID();
                        var pushVec = client.player.getLookAngle();
                        payload.pushVector = pushVec.add(new Vec3(0, 0.25, 0));
                        payload.typeof = "ROLL";
                        ClientPlayNetworking.send(new ServerBoundMovementPayload(payload));
                        BlockFrame.LOGGER.info(String.valueOf(ticksPassed));
                        ticksPassed = 0;
                        hasBeenPressed = false;
                    }else {
                        ticksPassed = 0;
                        hasBeenPressed = true;
                        return;
                    }
                }
            }


        });
    }
}
