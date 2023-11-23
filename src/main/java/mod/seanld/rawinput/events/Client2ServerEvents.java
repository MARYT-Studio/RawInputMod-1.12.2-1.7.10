package mod.seanld.rawinput.events;

import mod.seanld.rawinput.RawInput;
import mod.seanld.rawinput.RawInputHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class Client2ServerEvents {
    @SubscribeEvent
    public void onClientConnectedToServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        RawInputHandler.shouldGetMouse = true;
        RawInput.LOGGER.debug("RawInput's threads restart for player disconnection from server.");
    }
    @SubscribeEvent
    public void onClientDisconnectionFromServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        RawInputHandler.shouldGetMouse = false;
        RawInput.LOGGER.debug("Terminate RawInput's threads for player disconnection from server.");
    }
}
