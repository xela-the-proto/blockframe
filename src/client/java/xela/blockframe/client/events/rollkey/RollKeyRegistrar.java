package xela.blockframe.client.events.rollkey;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import xela.blockframe.BlockFrame;

public class RollKeyRegistrar {
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(
            BlockFrame.MOD_ID, "blockframe"
    ));

    public static final String KEY_ROLL = "key.blockframe.roll";
}
