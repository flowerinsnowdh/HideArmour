package cn.flowerinsnow.hidearmour.listener;

import cn.flowerinsnow.hidearmour.HideArmour;
import cn.flowerinsnow.hidearmour.common.config.Config;
import cn.flowerinsnow.hidearmour.event.PreRenderOwnArmourEvent;
import net.neoforged.bus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RenderOwnArmourListener {
    private final HideArmour main;

    public RenderOwnArmourListener(@NotNull HideArmour main) {
        this.main = Objects.requireNonNull(main);
    }

    @SubscribeEvent
    public void preRenderArmour(PreRenderOwnArmourEvent event) {
        Config config = this.main.getConfig();
        if (config.enable && switch (event.getSlot()) {
            case HEAD -> config.helmet;
            case CHEST -> config.chestplate;
            case LEGS -> config.leggings;
            case FEET -> config.boots;
            default -> false;
        }) {
            event.setCanceled(true);
        }
    }
}
