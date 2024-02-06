package online.flowerinsnow.hidearmour.client;

import cc.carm.lib.configuration.EasyConfiguration;
import cc.carm.lib.configuration.core.source.ConfigurationProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.crash.CrashReport;
import online.flowerinsnow.hidearmour.client.config.Config;
import online.flowerinsnow.hidearmour.client.eci.RenderArmourCallback;
import online.flowerinsnow.hidearmour.client.listener.RenderListener;

@Environment(EnvType.CLIENT)
public class HideArmourClient implements ClientModInitializer {
    private static ConfigurationProvider<?> provider;
    @Override
    public void onInitializeClient() {
        provider = EasyConfiguration.from(FabricLoader.getInstance().getConfigDir().resolve("hideamour.yml").toFile());
        provider.initialize(Config.class);

        RenderArmourCallback.Pre.EVENT.register(new RenderListener());
    }

    public static void reloadConfig() {
        try {
            provider.reload();
        } catch (Exception e) {
            MinecraftClient.getInstance().setCrashReportSupplier(CrashReport.create(e, e.getMessage()));
            MinecraftClient.getInstance().stop();
        }
    }

    public static void saveConfig() {
        try {
            provider.save();
        } catch (Exception e) {
            MinecraftClient.getInstance().setCrashReportSupplier(CrashReport.create(e, e.getMessage()));
            MinecraftClient.getInstance().stop();
        }
    }
}
