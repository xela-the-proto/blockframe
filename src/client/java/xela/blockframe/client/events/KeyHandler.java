package xela.blockframe.client.events;

import static xela.blockframe.client.events.doublejumpkey.DoubleJumpRegister.registerDoubleJumpKeybind;


public class KeyHandler {
    public static void init(){
        registerDoubleJumpKeybind();
    }

}
