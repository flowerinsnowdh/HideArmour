package online.flowerinsnow.hidearmour.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import online.flowerinsnow.hidearmour.client.HideArmourClient;
import online.flowerinsnow.hidearmour.client.config.Config;

import java.util.Timer;
import java.util.TimerTask;

public class GuiConfiguration extends Screen {
    private final Screen parent;
    private Timer resetReloadTimer;

    public GuiConfiguration(Screen parent) {
        super(Text.translatable("hidearmour.gui.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.addDrawableChild(ButtonWidget.builder(
                        Text.translatable("hidearmour.gui.config.enable", textTrueFalse(Config.ENABLE.getNotNull())),
                        button -> {
                            Config.ENABLE.set(!Config.ENABLE.getNotNull());
                            HideArmourClient.saveConfig();
                            button.setMessage(Text.translatable("hidearmour.gui.config.enable", textTrueFalse(Config.ENABLE.getNotNull())));
                        })
                .dimensions(
                        this.width / 2 - 100,
                        this.height / 2 - 40,
                        200, 20
                ).build()
        );

        this.addDrawableChild(ButtonWidget.builder(
                        Text.translatable("hidearmour.gui.config.helmet", textTrueFalse(Config.HELMET.getNotNull())),
                        button -> {
                            Config.HELMET.set(!Config.HELMET.getNotNull());
                            HideArmourClient.saveConfig();
                            button.setMessage(Text.translatable("hidearmour.gui.config.helmet", textTrueFalse(Config.HELMET.getNotNull())));
                        })
                .dimensions(
                        this.width / 2 - 100,
                        this.height / 2 - 20,
                        100, 20
                ).build()
        );
        this.addDrawableChild(ButtonWidget.builder(
                        Text.translatable("hidearmour.gui.config.chestplate", textTrueFalse(Config.CHESTPLATE.getNotNull())),
                        button -> {
                            Config.CHESTPLATE.set(!Config.CHESTPLATE.getNotNull());
                            HideArmourClient.saveConfig();
                            button.setMessage(Text.translatable("hidearmour.gui.config.chestplate", textTrueFalse(Config.CHESTPLATE.getNotNull())));
                        })
                .dimensions(
                        this.width / 2,
                        this.height / 2 - 20,
                        100, 20
                ).build()
        );
        this.addDrawableChild(ButtonWidget.builder(
                        Text.translatable("hidearmour.gui.config.leggings", textTrueFalse(Config.LEGGINGS.getNotNull())),
                        button -> {
                            Config.LEGGINGS.set(!Config.LEGGINGS.getNotNull());
                            HideArmourClient.saveConfig();
                            button.setMessage(Text.translatable("hidearmour.gui.config.leggings", textTrueFalse(Config.LEGGINGS.getNotNull())));
                        })
                .dimensions(
                        this.width / 2 - 100,
                        this.height / 2,
                        100, 20
                ).build()
        );
        this.addDrawableChild(ButtonWidget.builder(
                        Text.translatable("hidearmour.gui.config.boots", textTrueFalse(Config.BOOTS.getNotNull())),
                        button -> {
                            Config.BOOTS.set(!Config.BOOTS.getNotNull());
                            HideArmourClient.saveConfig();
                            button.setMessage(Text.translatable("hidearmour.gui.config.boots", textTrueFalse(Config.BOOTS.getNotNull())));
                        })
                .dimensions(
                        this.width / 2,
                        this.height / 2,
                        100, 20
                ).build()
        );

        this.addDrawableChild(ButtonWidget.builder(
                        Text.translatable("hidearmour.gui.config.reload.button"),
                        button -> {
                            HideArmourClient.reloadConfig();
                            button.setMessage(Text.translatable("hidearmour.gui.config.reload.success"));
                            if (resetReloadTimer != null) {
                                resetReloadTimer.cancel();
                            }
                            resetReloadTimer = new Timer();
                            resetReloadTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    button.setMessage(Text.translatable("hidearmour.gui.config.reload.button"));
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
                        Text.translatable("hidearmour.gui.config.done"),
                        button -> MinecraftClient.getInstance().setScreen(parent))
                .dimensions(
                        this.width / 2,
                        this.height / 2 + 20,
                        100, 20
                ).build()
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawTextWithShadow(
                this.textRenderer, this.title,
                this.width / 2 - this.textRenderer.getWidth(this.title) / 2,
                10, 0xFFFFFF
        );
        super.render(context, mouseX, mouseY, delta);
    }

    private MutableText textTrueFalse(boolean b) {
        return b ? Text.translatable("hidearmour.gui.config.true") : Text.translatable("hidearmour.gui.config.false");
    }
}
