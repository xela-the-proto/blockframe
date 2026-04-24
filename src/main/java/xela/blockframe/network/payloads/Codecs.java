package xela.blockframe.network.payloads;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;
import xela.blockframe.network.payloads.classes.VectorPayload;
/*
Individual files for custom codecs
 */
public class Codecs {
    public static final StreamCodec<ByteBuf, VectorPayload> VECTOR_PAYLOAD_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE,   v -> v.pushVector.x,
            ByteBufCodecs.DOUBLE, v -> v.pushVector.y,
            ByteBufCodecs.DOUBLE, v -> v.pushVector.z,
            ByteBufCodecs.STRING_UTF8, v -> v.UUID,
            //Initializer
            (x, y, z, uuid) -> {
                VectorPayload p = new VectorPayload();
                p.pushVector = new Vec3(x, y, z);
                p.UUID = uuid;
                return p;
            });

    public static final StreamCodec<ByteBuf, Boolean> BOOL_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, v -> v,
            //Initializer
            (x) -> {
                return x;
            });
}
