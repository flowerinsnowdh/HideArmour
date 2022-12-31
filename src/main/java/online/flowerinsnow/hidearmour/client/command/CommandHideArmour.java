package online.flowerinsnow.hidearmour.client.command;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import online.flowerinsnow.hidearmour.client.config.Config;

import java.io.IOException;

@Environment(EnvType.CLIENT)
public class CommandHideArmour {
    public static void register() {
        // noinspection DataFlowIssue
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> dispatcher
                        .register(CommandManager.literal("hidearmour")
                                .requires(source -> source.getEntity().equals(MinecraftClient.getInstance().player))
                                .then(CommandManager.literal("true")
                                        .executes(context -> {
                                            Config.getConfig().set("enable", true);
                                            context.getSource().sendFeedback(Text.translatable("hidearmour.enable"), false);
                                            return 0;
                                        }))
                                .then(CommandManager.literal("false")
                                        .executes(context -> {
                                            Config.getConfig().set("enable", false);
                                            context.getSource().sendFeedback(Text.translatable("hidearmour.disable"), false);
                                            return 0;
                                        }))
                                .then(CommandManager.literal("status")
                                        .executes(context -> {
                                            ServerCommandSource source = context.getSource();
                                            source.sendFeedback(Text.translatable("hidearmour.status.header"), false);
                                            source.sendFeedback(
                                                    Text.translatable("hidearmour.status.full", booleanText(Config.getConfig().getBooleanValue("enable"))), false
                                            );
                                            source.sendFeedback(Text.translatable("hidearmour.status.helmet", booleanText(Config.getConfig().getBooleanValue("helmet"))), false);
                                            source.sendFeedback(Text.translatable("hidearmour.status.chestplate", booleanText(Config.getConfig().getBooleanValue("chestplate"))), false);
                                            source.sendFeedback(Text.translatable("hidearmour.status.leggings", booleanText(Config.getConfig().getBooleanValue("leggings"))), false);
                                            source.sendFeedback(Text.translatable("hidearmour.status.boots", booleanText(Config.getConfig().getBooleanValue("boots"))), false);
                                            return 0;
                                        }))
                                .then(CommandManager.literal("reload")
                                        .executes(context -> {
                                            try {
                                                Config.loadConfig();
                                                context.getSource().sendFeedback(Text.translatable("hidearmour.command.reload"), false);
                                                return 0;
                                            } catch (IOException e) {
                                                context.getSource().sendError(Text.literal(e.toString()));
                                                return -1;
                                            }
                                        }))
                                .executes(context -> {
                                    context.getSource().sendFeedback(Text.translatable("hidearmour.command.usage"), false);
                                    return 0;
                                }))
                );
    }

    private static String booleanText(boolean b) {
        return b ? "§atrue" : "§cfalse";
    }
}
