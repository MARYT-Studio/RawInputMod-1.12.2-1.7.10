package mod.seanld.rawinput.keybindings;

import mod.seanld.rawinput.RawInputHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeybindingHandler {

    public static KeyBinding toggleKey;
    public static KeyBinding rescanKey;

    public static void init() {
        toggleKey = new KeyBinding("Toggle Raw Input", Keyboard.CHAR_NONE, "Raw Input");
        ClientRegistry.registerKeyBinding(toggleKey);

        rescanKey = new KeyBinding("Rescan Key", Keyboard.CHAR_NONE, "Raw Input");
        ClientRegistry.registerKeyBinding(rescanKey);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (toggleKey.isPressed()) {
            RawInputHandler.toggleRawInput();
        }

        if (rescanKey.isPressed()) {
            RawInputHandler.getMouseManually();
        }
    }
}
