package cn.flowerinsnow.hidearmour.screen;

import cn.flowerinsnow.hidearmour.common.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ScreenConfig extends Screen {
    private final Screen parent;
    private final Config config;

    public ScreenConfig(Screen parent, Config config) {
        super(Component.translatable("hide-armour.screen.config.title"));
        this.parent = parent;
        this.config = config;
    }

    @Override
    protected void init() {
        this.addRenderableWidget(
                Button.builder(
                        Component.translatable(this.config.enable ? "hide-armour.screen.config.enable.true" : "hide-armour.screen.config.enable.false"),
                        button -> {
                            Config config = ScreenConfig.this.config;
                            config.enable = !config.enable;
                            config.save();
                            button.setMessage(Component.translatable(config.enable ? "hide-armour.screen.config.enable.true" : "hide-armour.screen.config.enable.false"));
                        }
                )
                        .pos(this.width / 2 - 100, this.height / 2 - 42)
                        .size(201, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable(this.config.helmet ? "hide-armour.screen.config.hide-helmet.true" : "hide-armour.screen.config.hide-helmet.false"),
                        button -> {
                            Config config = ScreenConfig.this.config;
                            config.helmet = !config.helmet;
                            config.save();
                            button.setMessage(Component.translatable(config.helmet ? "hide-armour.screen.config.hide-helmet.true" : "hide-armour.screen.config.hide-helmet.false"));
                        }
                )
                        .pos(this.width / 2 - 100, this.height / 2 - 21)
                        .size(100, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable(this.config.chestplate ? "hide-armour.screen.config.hide-chestplate.true" : "hide-armour.screen.config.hide-chestplate.false"),
                        button -> {
                            Config config = ScreenConfig.this.config;
                            config.chestplate = !config.chestplate;
                            config.save();
                            button.setMessage(Component.translatable(config.chestplate ? "hide-armour.screen.config.hide-chestplate.true" : "hide-armour.screen.config.hide-chestplate.false"));
                        }
                )
                        .pos(this.width / 2 + 1, this.height / 2 - 21)
                        .size(100, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable(this.config.leggings ? "hide-armour.screen.config.hide-leggings.true" : "hide-armour.screen.config.hide-leggings.false"),
                        button -> {
                            Config config = ScreenConfig.this.config;
                            config.leggings = !config.leggings;
                            config.save();
                            button.setMessage(Component.translatable(config.leggings ? "hide-armour.screen.config.hide-leggings.true" : "hide-armour.screen.config.hide-leggings.false"));
                        }
                )
                        .pos(this.width / 2 - 100, this.height / 2)
                        .size(100, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable(this.config.boots ? "hide-armour.screen.config.hide-boots.true" : "hide-armour.screen.config.hide-boots.false"),
                        button -> {
                            Config config = ScreenConfig.this.config;
                            config.boots = !config.boots;
                            config.save();
                            button.setMessage(Component.translatable(config.boots ? "hide-armour.screen.config.hide-boots.true" : "hide-armour.screen.config.hide-boots.false"));
                        }
                )
                        .pos(this.width / 2 + 1, this.height / 2)
                        .size(100, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable("hide-armour.screen.config.done"),
                        button -> Minecraft.getInstance().setScreen(ScreenConfig.this.parent)
                )
                        .pos(this.width / 2 - 100, this.height / 2 + 21)
                        .size(201, 20)
                        .build()
        );
    }
}
