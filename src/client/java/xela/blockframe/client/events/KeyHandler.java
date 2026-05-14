package xela.blockframe.client.events;

import static xela.blockframe.client.events.doublejumpkey.DoubleJumpRegistrar.registerDoubleJumpKeybind;
import static xela.blockframe.client.events.rollkey.RollKeyRegistrar.registerRollKeybind;

public class KeyHandler {
    public static void init(){
        registerDoubleJumpKeybind();
        registerRollKeybind();
    }

}
