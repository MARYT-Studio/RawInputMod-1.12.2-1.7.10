package mod.seanld.rawinput.events;

import mod.seanld.rawinput.RawInput;
import mod.seanld.rawinput.RawInputHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientTickEvent {
    @SubscribeEvent
    public void timer(TickEvent.ClientTickEvent event) {
        if (RawInputHandler.worldJoinTimer >= 0) {
            RawInputHandler.worldJoinTimer--;
            return;
        }
        if (RawInputHandler.shouldGetMouse) {
            RawInputHandler.getMouse(false);
            RawInputHandler.shouldGetMouse = false;
            RawInput.LOGGER.debug("Automatic rescan finished, terminates RawInputMod's threads.");
        }
    }
}
