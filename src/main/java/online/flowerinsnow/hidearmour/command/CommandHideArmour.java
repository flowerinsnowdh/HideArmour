package online.flowerinsnow.hidearmour.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import online.flowerinsnow.hidearmour.config.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class CommandHideArmour extends CommandBase {
    @Override
    public String getCommandName() {
        return "hidearmour";
    }

    @Override
    public List<String> getCommandAliases() {
        return new ArrayList<>(Collections.singleton("hidearmor"));
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "hidearmour.command.usage";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender.equals(Minecraft.getMinecraft().thePlayer);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            List<String> subCommands = new ArrayList<>(Arrays.asList("true", "false", "status"));
            subCommands.removeIf(s -> !s.toLowerCase().startsWith(args[0].toLowerCase()));
            return subCommands;
        }
        return new ArrayList<>();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "true":
                    Config.enable.set(true);
                    sender.addChatMessage(new ChatComponentTranslation("hidearmour.enable"));
                    return;
                case "false":
                    Config.enable.set(false);
                    sender.addChatMessage(new ChatComponentTranslation("hidearmour.disable"));
                    return;
                case "status":
                    sender.addChatMessage(new ChatComponentTranslation("hidearmour.status.header"));
                    sender.addChatMessage(new ChatComponentTranslation("hidearmour.status.full", booleanText(Config.enable.getBoolean())));
                    sender.addChatMessage(new ChatComponentTranslation("hidearmour.status.helmet", booleanText(Config.helmet.getBoolean())));
                    sender.addChatMessage(new ChatComponentTranslation("hidearmour.status.chestplate", booleanText(Config.chestplate.getBoolean())));
                    sender.addChatMessage(new ChatComponentTranslation("hidearmour.status.leggings", booleanText(Config.leggings.getBoolean())));
                    sender.addChatMessage(new ChatComponentTranslation("hidearmour.status.boots", booleanText(Config.boots.getBoolean())));
                    return;
            }
        }
        throw new WrongUsageException(getCommandUsage(sender));
    }

    private String booleanText(boolean b) {
        return b ? "§atrue" : "§cfalse";
    }
}
