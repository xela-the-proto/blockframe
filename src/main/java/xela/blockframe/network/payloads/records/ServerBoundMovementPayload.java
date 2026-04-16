package xela.blockframe.network.payloads.records;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;
import xela.blockframe.BlockFrame;
import xela.blockframe.network.payloads.VectorPayloadCodec;
import xela.blockframe.network.payloads.classes.VectorPayload;



//x,y,z
public record ServerBoundMovementPayload(VectorPayload vecPayload)implements CustomPacketPayload {

    public static final Identifier BLOCKFRAME_MOVEMENT_ID = Identifier.fromNamespaceAndPath(BlockFrame.MOD_ID,
            "blockframe_movement");
    public static final CustomPacketPayload.Type<ServerBoundMovementPayload> TYPE =
            new CustomPacketPayload.Type<>(BLOCKFRAME_MOVEMENT_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerBoundMovementPayload> CODEC = StreamCodec.
            composite(VectorPayloadCodec.CODEC, ServerBoundMovementPayload::vecPayload, ServerBoundMovementPayload::new);

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

