package cn.flowerinsnow.hidearmour.client.mixin;

import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerEquipmentGetterEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
        if (livingEntity != MinecraftClient.getInstance().player) {
            return;
        }
        cir.setReturnValue(ClientPlayerEquipmentGetterEvent.EVENT.invoker().onClientPlayerEquipmentGet(equipmentSlot, cir.getReturnValue()));
    }
}
