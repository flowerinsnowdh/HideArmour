package cn.flowerinsnow.hidearmour.screen;

import cn.flowerinsnow.hidearmour.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ScreenConfig extends Screen {
    private final Screen parent;

    public ScreenConfig(Screen parent) {
        super(Component.translatable("hide-armour.screen.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.addRenderableWidget(
                Button.builder(
                        Component.translatable(Config.CONFIG.enable.isTrue() ? "hide-armour.screen.config.enable.true" : "hide-armour.screen.config.enable.false"),
                        button -> {
                            Config.CONFIG.enable.set(!Config.CONFIG.enable.getAsBoolean());
                            Config.CONFIG_SPEC.save();
                            button.setMessage(Component.translatable(Config.CONFIG.enable.isTrue() ? "hide-armour.screen.config.enable.true" : "hide-armour.screen.config.enable.false"));
                        }
                )
                        .pos(this.width / 2 - 100, this.height / 2 - 42)
                        .size(201, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable(Config.CONFIG.helmet.isTrue() ? "hide-armour.screen.config.hide-helmet.true" : "hide-armour.screen.config.hide-helmet.false"),
                        button -> {
                            Config.CONFIG.helmet.set(!Config.CONFIG.helmet.getAsBoolean());
                            Config.CONFIG_SPEC.save();
                            button.setMessage(Component.translatable(Config.CONFIG.helmet.isTrue() ? "hide-armour.screen.config.hide-helmet.true" : "hide-armour.screen.config.hide-helmet.false"));
                        }
                )
                        .pos(this.width / 2 - 100, this.height / 2 - 21)
                        .size(100, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable(Config.CONFIG.chestplate.isTrue() ? "hide-armour.screen.config.hide-chestplate.true" : "hide-armour.screen.config.hide-chestplate.false"),
                        button -> {
                            Config.CONFIG.chestplate.set(!Config.CONFIG.chestplate.getAsBoolean());
                            Config.CONFIG_SPEC.save();
                            button.setMessage(Component.translatable(Config.CONFIG.chestplate.isTrue() ? "hide-armour.screen.config.hide-chestplate.true" : "hide-armour.screen.config.hide-chestplate.false"));
                        }
                )
                        .pos(this.width / 2 + 1, this.height / 2 - 21)
                        .size(100, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable(Config.CONFIG.leggings.isTrue() ? "hide-armour.screen.config.hide-leggings.true" : "hide-armour.screen.config.hide-leggings.false"),
                        button -> {
                            Config.CONFIG.leggings.set(!Config.CONFIG.leggings.getAsBoolean());
                            Config.CONFIG_SPEC.save();
                            button.setMessage(Component.translatable(Config.CONFIG.leggings.isTrue() ? "hide-armour.screen.config.hide-leggings.true" : "hide-armour.screen.config.hide-leggings.false"));
                        }
                )
                        .pos(this.width / 2 - 100, this.height / 2)
                        .size(100, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable(Config.CONFIG.boots.isTrue() ? "hide-armour.screen.config.hide-boots.true" : "hide-armour.screen.config.hide-boots.false"),
                        button -> {
                            Config.CONFIG.boots.set(!Config.CONFIG.boots.getAsBoolean());
                            Config.CONFIG_SPEC.save();
                            button.setMessage(Component.translatable(Config.CONFIG.boots.isTrue() ? "hide-armour.screen.config.hide-boots.true" : "hide-armour.screen.config.hide-boots.false"));
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

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);
        graphics.drawCenteredString(this.font, this.title,
                this.width / 2 - this.font.width(this.title) / 2, 10,
                0xFFFFFF
        );
    }
}
