package xela.blockframe.client.events;

import static xela.blockframe.client.events.doublejumpkey.DoubleJumpRegistrar.registerDoubleJumpKeybind;


public class KeyHandler {
    public static void init(){
        registerDoubleJumpKeybind();
    }

}
