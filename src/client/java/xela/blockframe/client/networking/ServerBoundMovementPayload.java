package xela.blockframe.client.networking;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3fc;
import xela.blockframe.BlockFrame;
import xela.blockframe.client.events.KeyHandler;

import java.util.List;
                                        //x,y,z
public record ServerBoundMovementPayload(long[] pushVector)implements CustomPacketPayload {

    public static final Identifier BLOCKFRAME_MOVEMENT_ID = Identifier.fromNamespaceAndPath(BlockFrame.MOD_ID,
            "blockframe_movement");
    public static final CustomPacketPayload.Type<ServerBoundMovementPayload> TYPE =
            new CustomPacketPayload.Type<>(BLOCKFRAME_MOVEMENT_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerBoundMovementPayload> CODEC = StreamCodec.
            composite(
                    ByteBufCodecs.LONG_ARRAY, ServerBoundMovementPayload::pushVector, ServerBoundMovementPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return null;
    }
}