package xela.blockframe.client.codec;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;

public class Vec3Codec {
    public static final StreamCodec<ByteBuf, Vec3> VEC3_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, v -> v.x,
            ByteBufCodecs.DOUBLE, v -> v.y,
            ByteBufCodecs.DOUBLE, v -> v.z,
            Vec3::new);
}
