package mod.seanld.rawinput.commands;

import mod.seanld.rawinput.RawInputHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class RescanCommand extends CommandBase {
	@Override
	public String getName() {
		return "rescan";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Rescans input devices: /rescan";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		RawInputHandler.getMouseManually();
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Rescanned!"));
	}
	@Override
	public int getRequiredPermissionLevel() {
		return -1;
	}
}
