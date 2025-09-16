package cn.flowerinsnow.hidearmour;

import cn.flowerinsnow.hidearmour.config.Config;
import cn.flowerinsnow.hidearmour.listener.RenderOwnArmourListener;
import cn.flowerinsnow.hidearmour.screen.ScreenConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(HideArmour.MODID)
public class HideArmour {
    public static final String MODID = "hide_armour";
    private static HideArmour instance;

    public HideArmour(IEventBus modEventBus, ModContainer modContainer) {
        HideArmour.instance = this;

        modEventBus.addListener(this::onClientSetup);

        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.CONFIG_SPEC);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        this.initListeners();

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (container, modListScreen) -> new ScreenConfig(modListScreen)
        );
    }

    private void initListeners() {
        NeoForge.EVENT_BUS.register(new RenderOwnArmourListener());
    }

    public static HideArmour getInstance() {
        return HideArmour.instance;
    }
}
