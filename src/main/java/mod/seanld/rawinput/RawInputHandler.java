package mod.seanld.rawinput;

import net.java.games.input.Controller;
import net.java.games.input.DirectAndRawInputEnvironmentPlugin;
import net.java.games.input.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.ArrayUtils;

@SuppressWarnings({"BusyWait"})
public class RawInputHandler {
    public static Controller[] controllers;
    public static Controller[] mouseControllers;

    public static Mouse mouse;
    public static int dx = 0;
    public static int dy = 0;

    public static int worldJoinTimer;

    public static boolean shouldGetMouse = true;

    public static void init() {

        startInputThread(false);

    }

    public static void startInputThread(boolean manually) {
        Thread inputThread = new Thread(() -> {
            do {
                if (mouse != null && Minecraft.getMinecraft().currentScreen == null) {
                    mouse.poll();
                    dx += (int) mouse.getX().getPollData();
                    dy += (int) mouse.getY().getPollData();
                } else if (mouse != null) {
                    mouse.poll();
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    RawInput.LOGGER.error(e.getStackTrace());
                }
            } while (shouldGetMouse);
            RawInput.LOGGER.debug(String.format("Input Thread is terminated. %s-triggered scan is not executed.", manually ? "manually" : "auto"));
        });
        inputThread.setName("inputThread");
        inputThread.start();
    }

    public static void getMouse(boolean manually) {
        Thread getMouseThread = new Thread(() -> {
            if (shouldGetMouse) {
                DirectAndRawInputEnvironmentPlugin directEnv = new DirectAndRawInputEnvironmentPlugin();
                controllers = directEnv.getControllers();

                mouseControllers = null;
                mouse = null;

                for (Controller i : controllers) {
                    if (i.getType() == Controller.Type.MOUSE) {
                        mouseControllers = ArrayUtils.add(mouseControllers, i);
                    }
                }

                while (mouse == null) {
                    if (mouseControllers != null) {
                        for (Controller i : mouseControllers) {
                            i.poll();
                            float mouseX = ((Mouse) i).getX().getPollData();

                            if (mouseX > 0.1f || mouseX < -0.1f) {
                                mouse = ((Mouse) i);
                            }
                        }
                    }
                }
            } else {
                RawInput.LOGGER.debug(String.format("getMouse Thread is terminated. %s-triggered scan is not executed.", manually ? "manually" : "auto"));
            }
        });
        getMouseThread.setName("getMouseThread");
        getMouseThread.start();
    }

    public static void getMouseManually() {
        shouldGetMouse = true;
        getMouse(true);
    }

    public static void toggleRawInput() {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        float saveYaw = player.rotationYaw;
        float savePitch = player.rotationPitch;

        if (Minecraft.getMinecraft().mouseHelper instanceof RawMouseHelper) {
            Minecraft.getMinecraft().mouseHelper = new MouseHelper();
            Minecraft.getMinecraft().mouseHelper.grabMouseCursor();
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Toggled OFF"));
        } else {
            Minecraft.getMinecraft().mouseHelper = new RawMouseHelper();
            Minecraft.getMinecraft().mouseHelper.grabMouseCursor();
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Toggled ON"));
        }
        player.rotationYaw = saveYaw;
        player.rotationPitch = savePitch;
    }
}


