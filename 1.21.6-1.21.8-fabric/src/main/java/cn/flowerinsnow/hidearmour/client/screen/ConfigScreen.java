package cn.flowerinsnow.hidearmour.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import cn.flowerinsnow.hidearmour.client.HideArmourClient;
import cn.flowerinsnow.hidearmour.client.config.Config;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    private final Screen parent;
    private final Config config;

    private ConfigScreen(@NotNull Screen parent, @NotNull Config config) {
        super(Text.translatable("hide-armour.gui.config.title"));
        this.parent = parent;
        this.config = config;
    }

    public static @NotNull ConfigScreen create(@NotNull Screen parent, @NotNull Config config) {
        Objects.requireNonNull(parent, "parent");
        Objects.requireNonNull(config, "config");
        return new ConfigScreen(parent, config);
    }

    @Override
    protected void init() {
        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.translatable(this.config.enable ? "hide-armour.screen.config.enable.true" : "hide-armour.screen.config.enable.false"),
                        button -> {
                            ConfigScreen.this.config.enable = !ConfigScreen.this.config.enable;
                            ConfigScreen.this.config.save();
                            button.setMessage(Text.translatable(ConfigScreen.this.config.enable ? "hide-armour.screen.config.enable.true" : "hide-armour.screen.config.enable.false"));
                        }
                )
                        .position(this.width / 2 - 150, this.height / 2 - 40)
                        .size(300, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                                Text.translatable(this.config.helmet ? "hide-armour.screen.config.hide-helmet.true" : "hide-armour.screen.config.hide-helmet.false"),
                                button -> {
                                    ConfigScreen.this.config.helmet = !ConfigScreen.this.config.helmet;
                                    ConfigScreen.this.config.save();
                                    button.setMessage(Text.translatable(this.config.helmet ? "hide-armour.screen.config.hide-helmet.true" : "hide-armour.screen.config.hide-helmet.false"));
                                }
                        )
                        .position(this.width / 2 - 150, this.height / 2 - 20)
                        .size(150, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                                Text.translatable(this.config.chestplate ? "hide-armour.screen.config.hide-chestplate.true" : "hide-armour.screen.config.hide-chestplate.false"),
                                button -> {
                                    ConfigScreen.this.config.chestplate = !ConfigScreen.this.config.chestplate;
                                    ConfigScreen.this.config.save();
                                    button.setMessage(Text.translatable(this.config.chestplate ? "hide-armour.screen.config.hide-chestplate.true" : "hide-armour.screen.config.hide-chestplate.false"));
                                }
                        )
                        .position(this.width / 2, this.height / 2 - 20)
                        .size(150, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                                Text.translatable(this.config.leggings ? "hide-armour.screen.config.hide-leggings.true" : "hide-armour.screen.config.hide-leggings.false"),
                                button -> {
                                    ConfigScreen.this.config.leggings = !ConfigScreen.this.config.leggings;
                                    ConfigScreen.this.config.save();
                                    button.setMessage(Text.translatable(this.config.leggings ? "hide-armour.screen.config.hide-leggings.true" : "hide-armour.screen.config.hide-leggings.false"));
                                }
                        )
                        .position(this.width / 2 - 150, this.height / 2)
                        .size(150, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                                Text.translatable(this.config.boots ? "hide-armour.screen.config.hide-boots.true" : "hide-armour.screen.config.hide-boots.false"),
                                button -> {
                                    ConfigScreen.this.config.boots = !ConfigScreen.this.config.boots;
                                    ConfigScreen.this.config.save();
                                    button.setMessage(Text.translatable(this.config.boots ? "hide-armour.screen.config.hide-boots.true" : "hide-armour.screen.config.hide-boots.false"));
                                }
                        )
                        .position(this.width / 2, this.height / 2)
                        .size(150, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                                Text.translatable("hide-armour.screen.config.done"),
                                button ->
                                        MinecraftClient.getInstance().setScreen(ConfigScreen.this.parent)
                        )
                        .position(this.width / 2 - 150, this.height / 2 + 20)
                        .size(300, 20)
                        .build()
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawTextWithShadow(
                this.textRenderer, this.title,
                this.width / 2 - this.textRenderer.getWidth(this.title) / 2,
                10, 0xFFFFFF
        );
    }
}
