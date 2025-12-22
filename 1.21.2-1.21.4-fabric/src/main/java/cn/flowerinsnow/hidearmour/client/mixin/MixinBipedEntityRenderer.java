package cn.flowerinsnow.hidearmour.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import cn.flowerinsnow.hidearmour.client.eci.RenderArmourCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BipedEntityRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class MixinBipedEntityRenderer {
    @Inject(
            method = "getEquippedStack",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void getEquippedStack(LivingEntity livingEntity, EquipmentSlot equipmentSlot, CallbackInfoReturnable<ItemStack> cir) {
        if (livingEntity != Minecraft.getInstance().player) {
            return;
        }
        cir.setReturnValue(ClientPlayerEquipmentGetterEvent.EVENT.invoker().onClientPlayerEquipmentGet(equipmentSlot, cir.getReturnValue()));
    }
}
