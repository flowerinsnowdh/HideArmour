package cn.flowerinsnow.hidearmour.client.mixin;

import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerChestGetterEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElytraFeatureRenderer.class)
@Environment(EnvType.CLIENT)
public class MixinElytraFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> {
    @Unique
    private boolean clientPlayer;

    @Inject(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
            at = @At("HEAD")
    )
    private void captureVar3Entity(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        this.clientPlayer = MinecraftClient.getInstance().player == livingEntity;
    }

    @ModifyVariable(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
            at = @At(
                    value = "STORE",
                    ordinal = 0
            ),
            index = 11
    )
    public ItemStack overwriteChestGet(ItemStack value) {
        if (this.clientPlayer) {
            return ClientPlayerChestGetterEvent.EVENT.invoker().onClientPlayerChestGet(value);
        }
        return value;
    }
}
