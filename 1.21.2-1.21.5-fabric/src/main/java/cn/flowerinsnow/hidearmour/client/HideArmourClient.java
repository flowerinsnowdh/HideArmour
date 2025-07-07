package cn.flowerinsnow.hidearmour.client;

import cc.carm.lib.configuration.source.ConfigurationHolder;
import cc.carm.lib.configuration.source.yaml.YAMLConfigFactory;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.crash.CrashReport;
import cn.flowerinsnow.hidearmour.client.config.Config;
import cn.flowerinsnow.hidearmour.client.eci.RenderArmourCallback;
import cn.flowerinsnow.hidearmour.client.listener.RenderListener;

@Environment(EnvType.CLIENT)
public class HideArmourClient implements ClientModInitializer {
    private static ConfigurationHolder<?> configurationHolder;
    @Override
    public void onInitializeClient() {
        HideArmourClient.configurationHolder = YAMLConfigFactory.from(FabricLoader.getInstance().getConfigDir().resolve("hide-amour.yml").toFile()).build();
        HideArmourClient.configurationHolder.initialize(Config.class);

        RenderArmourCallback.EVENT.register(new RenderListener());
    }

    public static void reloadConfig() {
        try {
            HideArmourClient.configurationHolder.reload();
        } catch (Exception e) {
            MinecraftClient.getInstance().setCrashReportSupplier(CrashReport.create(e, e.getMessage()));
            MinecraftClient.getInstance().stop();
        }
    }

    public static void saveConfig() {
        try {
            HideArmourClient.configurationHolder.save();
        } catch (Exception e) {
            MinecraftClient.getInstance().setCrashReportSupplier(CrashReport.create(e, e.getMessage()));
            MinecraftClient.getInstance().stop();
        }
    }
}
