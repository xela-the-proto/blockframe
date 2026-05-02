//Yea i used claude for this i couldn't figure it out :(
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
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(BlockFrame.MOD_ID, "blockframe")
    );
    public static final String KEY_DOUBLE_JUMP = "key.blockframe.double_jump";
    public static KeyMapping doubleJump = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            KEY_DOUBLE_JUMP,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_SPACE,
            CATEGORY
    ));

    public static boolean hasJumped = false;
    public static boolean resetFlag = false;
    private static boolean wasOnGround = true;
    private static int landingCooldown = 0;

    public static void registerDoubleJumpKeybind() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.level == null) return;
            /*
            Check every tick where we are, if we weren't on ground
            and now we are, we need to await before applying any double jump logic
            */
            boolean isOnGround = client.player.onGround();
            boolean justLanded = !wasOnGround && isOnGround;

            /*
            If we just landed, we should reset all the flags to make the double jump but void inputs and
            wait a couple ticks
             */
            if (justLanded) {
                resetFlag = true;
                hasJumped = false;
                landingCooldown = 2;
                while (DoubleJumpRegister.doubleJump.consumeClick()) { /* scarta */ }
                wasOnGround = true;
                return;
            }

            //we store what happened this tick to check what happens in the next
            wasOnGround = isOnGround;

            /*
            Actually delay the double jump logic
             */
            if (landingCooldown > 0) {
                landingCooldown--;
                while (DoubleJumpRegister.doubleJump.consumeClick()) { /* scarta */ }
                return;
            }

            while (DoubleJumpRegister.doubleJump.consumeClick()) {
                if (isOnGround) continue;

                if (hasJumped && resetFlag) {
                    var payload = new VectorPayload();
                    payload.UUID = client.player.getStringUUID();
                    payload.pushVector = new Vec3(0, 0.25, 0);
                    ClientPlayNetworking.send(new ServerBoundMovementPayload(payload));
                    hasJumped = false;
                    resetFlag = false;
                } else if (resetFlag) {
                    hasJumped = true;
                }
            }
        });
    }
}