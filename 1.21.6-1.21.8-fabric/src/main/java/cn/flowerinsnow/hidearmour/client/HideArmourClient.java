package cn.flowerinsnow.hidearmour.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import cn.flowerinsnow.hidearmour.client.config.Config;
import cn.flowerinsnow.hidearmour.client.eci.RenderArmourCallback;
import cn.flowerinsnow.hidearmour.client.listener.RenderListener;

@Environment(EnvType.CLIENT)
public class HideArmourClient implements ClientModInitializer {
    private static HideArmourClient instance;
    private Config config;

    @Override
    public void onInitializeClient() {
        instance = this;

        this.config = Config.create(FabricLoader.getInstance().getConfigDir().resolve("hide-armour.toml"));
        this.config.load();

        RenderArmourCallback.EVENT.register(new RenderListener());
    }

    public Config config() {
        return this.config;
    }

    public static HideArmourClient instance() {
        return instance;
    }
}
