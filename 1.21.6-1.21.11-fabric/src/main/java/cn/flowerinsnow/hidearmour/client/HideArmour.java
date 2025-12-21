package cn.flowerinsnow.hidearmour.client;

import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerBlockHeadGetterEvent;
import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerWornHeadGetterEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import cn.flowerinsnow.hidearmour.client.config.HideArmourConfig;
import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerEquipmentGetterEvent;
import cn.flowerinsnow.hidearmour.client.listener.HideArmourListener;

@Environment(EnvType.CLIENT)
public class HideArmour implements ClientModInitializer {
    public static final String MODID = "hide-armour";

    private static HideArmour instance;
    private HideArmourConfig config;

    @Override
    public void onInitializeClient() {
        instance = this;

        this.config = HideArmourConfig.create();
        this.config.init();

        HideArmourListener hideArmourListener = new HideArmourListener(this);
        ClientPlayerEquipmentGetterEvent.EVENT.register(hideArmourListener);
        ClientPlayerWornHeadGetterEvent.TYPE.register(hideArmourListener);
        ClientPlayerWornHeadGetterEvent.PROFILE.register(hideArmourListener);
        ClientPlayerBlockHeadGetterEvent.EVENT.register(hideArmourListener);
    }

    public HideArmourConfig config() {
        return this.config;
    }

    public static HideArmour instance() {
        return instance;
    }
}
