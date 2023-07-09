package online.flowerinsnow.hidearmour.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import online.flowerinsnow.hidearmour.client.command.CommandHideArmour;
import online.flowerinsnow.hidearmour.client.config.Config;
import online.flowerinsnow.hidearmour.client.eci.RenderArmourCallback;
import online.flowerinsnow.hidearmour.client.listener.RenderListener;

import java.io.IOException;

@Environment(EnvType.CLIENT)
public class HidearmourClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        try {
            Config.loadConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CommandHideArmour.register();

        RenderArmourCallback.Pre.EVENT.register(new RenderListener());
    }
}
