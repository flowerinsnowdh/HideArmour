package online.flowerinsnow.hidearmour.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import online.flowerinsnow.hidearmour.config.Config;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("NullableProblems")
@SideOnly(Side.CLIENT)
public class CommandHideArmour extends CommandBase {
    @Override
    public String getName() {
        return "hidearmour";
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<>(Collections.singleton("hidearmor"));
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "hidearmour.command.usage";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.equals(Minecraft.getMinecraft().player);
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            List<String> subCommands = new ArrayList<>(Arrays.asList("true", "false", "status", "reload"));
            subCommands.removeIf(s -> !s.toLowerCase().startsWith(args[0].toLowerCase()));
            return subCommands;
        }
        return new ArrayList<>();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "true":
                    Config.enable.set(true);
                    sender.sendMessage(new TextComponentTranslation("hidearmour.enable"));
                    return;
                case "false":
                    Config.enable.set(false);
                    sender.sendMessage(new TextComponentTranslation("hidearmour.disable"));
                    return;
                case "status":
                    sender.sendMessage(new TextComponentTranslation("hidearmour.status.header"));
                    sender.sendMessage(new TextComponentTranslation("hidearmour.status.full", booleanText(Config.enable.getBoolean())));
                    sender.sendMessage(new TextComponentTranslation("hidearmour.status.helmet", booleanText(Config.helmet.getBoolean())));
                    sender.sendMessage(new TextComponentTranslation("hidearmour.status.chestplate", booleanText(Config.chestplate.getBoolean())));
                    sender.sendMessage(new TextComponentTranslation("hidearmour.status.leggings", booleanText(Config.leggings.getBoolean())));
                    sender.sendMessage(new TextComponentTranslation("hidearmour.status.boots", booleanText(Config.boots.getBoolean())));
                    return;
                case "reload":
                    Config.reloadConfig();
                    sender.sendMessage(new TextComponentTranslation("hidearmour.command.reload"));
                    return;
            }
        }
        throw new WrongUsageException(getUsage(sender));
    }

    private String booleanText(boolean b) {
        return b ? "§atrue" : "§cfalse";
    }
}
