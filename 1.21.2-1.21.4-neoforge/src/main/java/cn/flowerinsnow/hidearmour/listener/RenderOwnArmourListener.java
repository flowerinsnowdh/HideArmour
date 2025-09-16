package cn.flowerinsnow.hidearmour.listener;

import cn.flowerinsnow.hidearmour.config.Config;
import cn.flowerinsnow.hidearmour.event.PreRenderOwnArmourEvent;
import net.neoforged.bus.api.SubscribeEvent;

public class RenderOwnArmourListener {
    @SubscribeEvent
    public void preRenderArmour(PreRenderOwnArmourEvent event) {
        Config config = Config.CONFIG;
        if (config.enable.isTrue() && switch (event.getSlot()) {
            case HEAD -> config.helmet.isTrue();
            case CHEST -> config.chestplate.isTrue();
            case LEGS -> config.leggings.isTrue();
            case FEET -> config.boots.isTrue();
            default -> false;
        }) {
            event.setCanceled(true);
        }
    }
}
