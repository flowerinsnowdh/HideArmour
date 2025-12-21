package cn.flowerinsnow.hidearmour.client.mixin;

import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerEquipmentGetterEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HumanoidMobRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class MixinHumanoidMobRenderer<S extends HumanoidRenderState, M extends HumanoidModel<S>, A extends HumanoidModel<S>> {
    @Inject(
            method = "getEquipmentIfRenderable",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void getEquipmentIfRenderable(LivingEntity livingEntity, EquipmentSlot equipmentSlot, CallbackInfoReturnable<ItemStack> cir) {
        if (livingEntity != Minecraft.getInstance().player) {
            return;
        }
        cir.setReturnValue(ClientPlayerEquipmentGetterEvent.EVENT.invoker().onClientPlayerEquipmentGet(equipmentSlot, cir.getReturnValue()));
    }
}
