package cn.flowerinsnow.hidearmour;

import cn.flowerinsnow.hidearmour.common.config.Config;
import cn.flowerinsnow.hidearmour.common.provider.ModEnvironmentProvider;
import cn.flowerinsnow.hidearmour.listener.RenderOwnArmourListener;
import cn.flowerinsnow.hidearmour.screen.ScreenConfig;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;

@Mod(HideArmour.MODID)
public class HideArmour {
    public static final String MODID = "hide_armour";
    private static HideArmour instance;

    private Config config;

    public HideArmour(IEventBus modEventBus, ModContainer modContainer) {
        HideArmour.instance = this;

        modEventBus.addListener(this::onClientSetup);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        this.initConfig();
        this.initListeners();

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (container, modListScreen) -> new ScreenConfig(modListScreen, HideArmour.this.config)
        );
    }

    private void initConfig() {
        this.config = new Config(new ModEnvironmentProvider() {
            @Override
            public @NotNull InputStream getDefaultConfigAsStream() {
                return Optional.ofNullable(HideArmour.class.getResourceAsStream("/config.toml"))
                        .orElseGet(() -> {
                            AssertionError err = new AssertionError();
                            this.crash(err, "config.toml not found in jar file");
                            throw err;
                        });
            }

            @Override
            public @NotNull Path getConfigFile() {
                return FMLPaths.CONFIGDIR.get().resolve(HideArmour.MODID + ".toml");
            }

            @Override
            public void crash(@NotNull Throwable throwable, @NotNull String msg) {
                Minecraft mc = Minecraft.getInstance();
                Minecraft.crash(mc, mc.gameDirectory, CrashReport.forThrowable(throwable, msg));
            }
        });

        this.config.load();
    }

    private void initListeners() {
        NeoForge.EVENT_BUS.register(new RenderOwnArmourListener(this));
    }

    public static HideArmour getInstance() {
        return HideArmour.instance;
    }

    public Config getConfig() {
        return this.config;
    }
}
