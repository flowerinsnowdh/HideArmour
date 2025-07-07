package cn.flowerinsnow.hidearmour.client.gui;

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

import java.util.Timer;
import java.util.TimerTask;

@Environment(EnvType.CLIENT)
public class GuiConfiguration extends Screen {
    private final Screen parent;
    private Timer resetReloadTimer;
    private ButtonWidget buttonEnable;
    private ButtonWidget buttonHelmet;
    private ButtonWidget buttonChestplate;
    private ButtonWidget buttonLeggings;
    private ButtonWidget buttonBoots;

    public GuiConfiguration(Screen parent) {
        super(Text.translatable("hide-armour.gui.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.addDrawableChild(buttonEnable = ButtonWidget.builder(
                        Text.translatable("hide-armour.gui.config.enable", textTrueFalse(Config.ENABLE.getNotNull())),
                        button -> {
                            Config.ENABLE.set(!Config.ENABLE.getNotNull());
                            button.setMessage(Text.translatable("hide-armour.gui.config.enable", textTrueFalse(Config.ENABLE.getNotNull())));
                        })
                .dimensions(
                        this.width / 2 - 100,
                        this.height / 2 - 40,
                        200, 20
                ).build()
        );

        this.addDrawableChild(buttonHelmet = ButtonWidget.builder(
                        Text.translatable("hide-armour.gui.config.helmet", textTrueFalse(Config.HELMET.getNotNull())),
                        button -> {
                            Config.HELMET.set(!Config.HELMET.getNotNull());
                            button.setMessage(Text.translatable("hide-armour.gui.config.helmet", textTrueFalse(Config.HELMET.getNotNull())));
                        })
                .dimensions(
                        this.width / 2 - 100,
                        this.height / 2 - 20,
                        100, 20
                ).build()
        );
        this.addDrawableChild(buttonChestplate = ButtonWidget.builder(
                        Text.translatable("hide-armour.gui.config.chestplate", textTrueFalse(Config.CHESTPLATE.getNotNull())),
                        button -> {
                            Config.CHESTPLATE.set(!Config.CHESTPLATE.getNotNull());
                            button.setMessage(Text.translatable("hide-armour.gui.config.chestplate", textTrueFalse(Config.CHESTPLATE.getNotNull())));
                        })
                .dimensions(
                        this.width / 2,
                        this.height / 2 - 20,
                        100, 20
                ).build()
        );
        this.addDrawableChild(buttonLeggings = ButtonWidget.builder(
                        Text.translatable("hide-armour.gui.config.leggings", textTrueFalse(Config.LEGGINGS.getNotNull())),
                        button -> {
                            Config.LEGGINGS.set(!Config.LEGGINGS.getNotNull());
                            button.setMessage(Text.translatable("hide-armour.gui.config.leggings", textTrueFalse(Config.LEGGINGS.getNotNull())));
                        })
                .dimensions(
                        this.width / 2 - 100,
                        this.height / 2,
                        100, 20
                ).build()
        );
        this.addDrawableChild(buttonBoots = ButtonWidget.builder(
                        Text.translatable("hide-armour.gui.config.boots", textTrueFalse(Config.BOOTS.getNotNull())),
                        button -> {
                            Config.BOOTS.set(!Config.BOOTS.getNotNull());
                            button.setMessage(Text.translatable("hide-armour.gui.config.boots", textTrueFalse(Config.BOOTS.getNotNull())));
                        })
                .dimensions(
                        this.width / 2,
                        this.height / 2,
                        100, 20
                ).build()
        );

        this.addDrawableChild(ButtonWidget.builder(
                        Text.translatable("hide-armour.gui.config.reload.button"),
                        button -> {
                            HideArmourClient.reloadConfig();
                            buttonEnable.setMessage(Text.translatable("hide-armour.gui.config.enable", textTrueFalse(Config.ENABLE.getNotNull())));
                            buttonHelmet.setMessage(Text.translatable("hide-armour.gui.config.helmet", textTrueFalse(Config.HELMET.getNotNull())));
                            buttonChestplate.setMessage(Text.translatable("hide-armour.gui.config.chestplate", textTrueFalse(Config.CHESTPLATE.getNotNull())));
                            buttonLeggings.setMessage(Text.translatable("hide-armour.gui.config.leggings", textTrueFalse(Config.LEGGINGS.getNotNull())));
                            buttonBoots.setMessage(Text.translatable("hide-armour.gui.config.boots", textTrueFalse(Config.BOOTS.getNotNull())));
                            button.setMessage(Text.translatable("hide-armour.gui.config.reload.success"));
                            if (resetReloadTimer != null) {
                                resetReloadTimer.cancel();
                            }
                            resetReloadTimer = new Timer();
                            resetReloadTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    button.setMessage(Text.translatable("hide-armour.gui.config.reload.button"));
                                    resetReloadTimer = null;
                                }
                            }, 3000L);
                        })
                .dimensions(
                        this.width / 2 - 100,
                        this.height / 2 + 20,
                        100, 20
                ).build()
        );
        this.addDrawableChild(ButtonWidget.builder(
                        Text.translatable("hide-armour.gui.config.done"),
                        button -> {
                            MinecraftClient.getInstance().setScreen(parent);
                            HideArmourClient.saveConfig();
                        })
                .dimensions(
                        this.width / 2,
                        this.height / 2 + 20,
                        100, 20
                ).build()
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

    private MutableText textTrueFalse(boolean b) {
        return b ? Text.translatable("hide-armour.gui.config.true") : Text.translatable("hide-armour.gui.config.false");
    }
}
