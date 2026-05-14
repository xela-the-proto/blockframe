package xela.blockframe.events;

public class ServerEventRegistrar {
    public static void init(){
        JoinEvent.registerJoinEvent();
    }
}
