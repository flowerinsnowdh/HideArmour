package cn.flowerinsnow.hidearmour.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import cn.flowerinsnow.hidearmour.client.config.HideArmourConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    private final Screen parent;
    private final HideArmourConfig config;

    private ConfigScreen(@NotNull Screen parent, @NotNull HideArmourConfig config) {
        super(Text.translatable("hide-armour.screen.config.title"));
        this.parent = parent;
        this.config = config;
    }

    public static @NotNull ConfigScreen create(@NotNull Screen parent, @NotNull HideArmourConfig config) {
        Objects.requireNonNull(parent, "parent");
        Objects.requireNonNull(config, "config");
        return new ConfigScreen(parent, config);
    }

    @Override
    protected void init() {
        this.addDrawableChild(
                ButtonWidget.builder(
                                translateTextWithParamTrueFalse("hide-armour.screen.config.enable", this.config.enable()),
                                button -> {
                                    boolean invert = ConfigScreen.this.config.invertEnable();
                                    ConfigScreen.this.config.save();
                                    button.setMessage(translateTextWithParamTrueFalse("hide-armour.screen.config.enable", invert));
                                }
                        )
                        .position(this.width / 2 - 150, this.height / 2 - 70)
                        .size(300, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                                translateTextWithParamTrueFalse("hide-armour.screen.config.hide.head.helmet", this.config.headHelmet()),
                                button -> {
                                    boolean invert = ConfigScreen.this.config.invertHeadHelmet();
                                    ConfigScreen.this.config.save();
                                    button.setMessage(translateTextWithParamTrueFalse("hide-armour.screen.config.hide.head.helmet", invert));
                                }
                        )
                        .position(this.width / 2 - 150, this.height / 2 - 45)
                        .size(150, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                                translateTextWithParamTrueFalse("hide-armour.screen.config.hide.chest.chestplate", this.config.chestChestplate()),
                                button -> {
                                    boolean invert = ConfigScreen.this.config.invertChestChestplate();
                                    ConfigScreen.this.config.save();
                                    button.setMessage(translateTextWithParamTrueFalse("hide-armour.screen.config.hide.chest.chestplate", invert));
                                }
                        )
                        .position(this.width / 2, this.height / 2 - 45)
                        .size(150, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                                translateTextWithParamTrueFalse("hide-armour.screen.config.hide.leggings", this.config.leggings()),
                                button -> {
                                    boolean invert = ConfigScreen.this.config.invertLeggings();
                                    ConfigScreen.this.config.save();
                                    button.setMessage(translateTextWithParamTrueFalse("hide-armour.screen.config.hide.leggings", invert));
                                }
                        )
                        .position(this.width / 2 - 150, this.height / 2 - 20)
                        .size(150, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                                translateTextWithParamTrueFalse("hide-armour.screen.config.hide.boots", this.config.boots()),
                                button -> {
                                    boolean invert = ConfigScreen.this.config.invertBoots();
                                    ConfigScreen.this.config.save();
                                    button.setMessage(translateTextWithParamTrueFalse("hide-armour.screen.config.hide.boots", invert));
                                }
                        )
                        .position(this.width / 2, this.height / 2 - 20)
                        .size(150, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                                translateTextWithParamTrueFalse("hide-armour.screen.config.hide.head.skull", this.config.headSkull()),
                                button -> {
                                    boolean invert = ConfigScreen.this.config.invertHeadSkull();
                                    ConfigScreen.this.config.save();
                                    button.setMessage(translateTextWithParamTrueFalse("hide-armour.screen.config.hide.head.skull", invert));
                                }
                        )
                        .position(this.width / 2 - 150, this.height / 2 + 5)
                        .size(150, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                                translateTextWithParamTrueFalse("hide-armour.screen.config.hide.head.block", this.config.headBlock()),
                                button -> {
                                    boolean invert = ConfigScreen.this.config.invertHeadBlock();
                                    ConfigScreen.this.config.save();
                                    button.setMessage(translateTextWithParamTrueFalse("hide-armour.screen.config.hide.head.block", invert));
                                }
                        )
                        .position(this.width / 2, this.height / 2 + 5)
                        .size(150, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                                translateTextWithParamTrueFalse("hide-armour.screen.config.hide.chest.elytra", this.config.chestElytra()),
                                button -> {
                                    boolean invert = ConfigScreen.this.config.invertChestElytra();
                                    ConfigScreen.this.config.save();
                                    button.setMessage(translateTextWithParamTrueFalse("hide-armour.screen.config.hide.chest.elytra", invert));
                                }
                        )
                        .position(this.width / 2 - 150, this.height / 2 + 30)
                        .size(300, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                                Text.translatable("hide-armour.screen.config.done"),
                                button -> MinecraftClient.getInstance().setScreen(ConfigScreen.this.parent)
                        )
                        .position(this.width / 2 - 150, this.height / 2 + 55)
                        .size(300, 20)
                        .build()
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        //noinspection DataFlowIssue
        context.drawCenteredTextWithShadow(
                this.textRenderer, this.title,
                this.width / 2, 20, Formatting.WHITE.getColorValue()
        );
    }

    private static MutableText translateTextWithParamTrueFalse(String key, boolean value) {
        return Text.translatable(key, translateTextOfTrueFalse(value));
    }

    public static MutableText translateTextOfTrueFalse(boolean value) {
        return value ? Text.translatable("hide-armour.screen.config.constant.true").formatted(Formatting.GREEN) : Text.translatable("hide-armour.screen.config.constant.false").formatted(Formatting.RED);
    }
}
