package xela.blockframe.data;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;
import xela.blockframe.BlockFrame;
import xela.blockframe.network.payloads.Codecs;

public class DataAttachments {
    public static final AttachmentType<Boolean> DOUBLE_JUMP_DATA_ATTACHMENT = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(BlockFrame.MOD_ID,"double_jump_attachment"),
            builder -> builder.initializer(() -> true)
                    .syncWith(Codecs.BOOL_STREAM_CODEC, AttachmentSyncPredicate.targetOnly())
            );
}
