package online.flowerinsnow.hidearmour.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import online.flowerinsnow.hidearmour.client.command.CommandHideArmour;
import online.flowerinsnow.hidearmour.client.eci.RenderPlayerEntityCallback;
import online.flowerinsnow.hidearmour.client.listener.RenderListener;

@Environment(EnvType.CLIENT)
public class HidearmourClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CommandHideArmour.register();

        RenderListener renderListener = new RenderListener();
        RenderPlayerEntityCallback.Pre.EVENT.register(renderListener);
        RenderPlayerEntityCallback.Post.EVENT.register(renderListener);
    }
}
