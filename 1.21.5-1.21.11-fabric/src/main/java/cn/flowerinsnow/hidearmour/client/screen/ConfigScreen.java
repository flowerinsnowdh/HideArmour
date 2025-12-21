package cn.flowerinsnow.hidearmour.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import cn.flowerinsnow.hidearmour.client.config.HideArmourConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    private final Screen parent;
    private final HideArmourConfig config;

    private ConfigScreen(@NotNull Screen parent, @NotNull HideArmourConfig config) {
        super(Component.translatable("hide-armour.screen.config.title"));
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
        this.addRenderableWidget(
                Button.builder(
                        translateComponentWithParamTrueFalse("hide-armour.screen.config.enable", this.config.enable()),
                        button -> {
                            boolean invert = ConfigScreen.this.config.invertEnable();
                            ConfigScreen.this.config.save();
                            button.setMessage(translateComponentWithParamTrueFalse("hide-armour.screen.config.enable", invert));
                        }
                )
                        .pos(this.width / 2 - 150, this.height / 2 - 70)
                        .size(300, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        translateComponentWithParamTrueFalse("hide-armour.screen.config.hide.head.helmet", this.config.headHelmet()),
                        button -> {
                            boolean invert = ConfigScreen.this.config.invertHeadHelmet();
                            ConfigScreen.this.config.save();
                            button.setMessage(translateComponentWithParamTrueFalse("hide-armour.screen.config.hide.head.helmet", invert));
                        }
                )
                        .pos(this.width / 2 - 150, this.height / 2 - 45)
                        .size(150, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        translateComponentWithParamTrueFalse("hide-armour.screen.config.hide.chest.chestplate", this.config.chestChestplate()),
                        button -> {
                            boolean invert = ConfigScreen.this.config.invertChestChestplate();
                            ConfigScreen.this.config.save();
                            button.setMessage(translateComponentWithParamTrueFalse("hide-armour.screen.config.hide.chest.chestplate", invert));
                        }
                )
                        .pos(this.width / 2, this.height / 2 - 45)
                        .size(150, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        translateComponentWithParamTrueFalse("hide-armour.screen.config.hide.leggings", this.config.leggings()),
                        button -> {
                            boolean invert = ConfigScreen.this.config.invertLeggings();
                            ConfigScreen.this.config.save();
                            button.setMessage(translateComponentWithParamTrueFalse("hide-armour.screen.config.hide.leggings", invert));
                        }
                )
                        .pos(this.width / 2 - 150, this.height / 2 - 20)
                        .size(150, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        translateComponentWithParamTrueFalse("hide-armour.screen.config.hide.boots", this.config.boots()),
                        button -> {
                            boolean invert = ConfigScreen.this.config.invertBoots();
                            ConfigScreen.this.config.save();
                            button.setMessage(translateComponentWithParamTrueFalse("hide-armour.screen.config.hide.boots", invert));
                        }
                )
                        .pos(this.width / 2, this.height / 2 - 20)
                        .size(150, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        translateComponentWithParamTrueFalse("hide-armour.screen.config.hide.head.skull", this.config.headSkull()),
                        button -> {
                            boolean invert = ConfigScreen.this.config.invertHeadSkull();
                            ConfigScreen.this.config.save();
                            button.setMessage(translateComponentWithParamTrueFalse("hide-armour.screen.config.hide.head.skull", invert));
                        }
                )
                        .pos(this.width / 2 - 150, this.height / 2 + 5)
                        .size(150, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        translateComponentWithParamTrueFalse("hide-armour.screen.config.hide.head.block", this.config.headBlock()),
                        button -> {
                            boolean invert = ConfigScreen.this.config.invertHeadBlock();
                            ConfigScreen.this.config.save();
                            button.setMessage(translateComponentWithParamTrueFalse("hide-armour.screen.config.hide.head.block", invert));
                        }
                )
                        .pos(this.width / 2, this.height / 2 + 5)
                        .size(150, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        translateComponentWithParamTrueFalse("hide-armour.screen.config.hide.chest.elytra", this.config.chestElytra()),
                        button -> {
                            boolean invert = ConfigScreen.this.config.invertChestElytra();
                            ConfigScreen.this.config.save();
                            button.setMessage(translateComponentWithParamTrueFalse("hide-armour.screen.config.hide.chest.elytra", invert));
                        }
                )
                        .pos(this.width / 2 - 150, this.height / 2 + 30)
                        .size(300, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable("hide-armour.screen.config.done"),
                        button -> Minecraft.getInstance().setScreen(ConfigScreen.this.parent)
                )
                        .pos(this.width / 2 - 150, this.height / 2 + 55)
                        .size(300, 20)
                        .build()
        );
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        super.render(graphics, mouseX, mouseY, a);
        graphics.drawCenteredString(
                this.font, this.title,
                this.width / 2, 20, 0xFFFFFF
        );
    }

    private static MutableComponent translateComponentWithParamTrueFalse(String key, boolean value) {
        return Component.translatable(key, translateComponentOfTrueFalse(value));
    }

    public static MutableComponent translateComponentOfTrueFalse(boolean value) {
        return value ? Component.translatable("hide-armour.screen.config.constant.true").withStyle(ChatFormatting.GREEN) : Component.translatable("hide-armour.screen.config.constant.false").withStyle(ChatFormatting.RED);
    }
}
