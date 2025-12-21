package cn.flowerinsnow.hidearmour.client.mixin;

import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerChestGetterEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntityModel.class)
public class MixinPlayerEntityModel {
    @Redirect(
            method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;",
                    ordinal = 0
            )
    )
    public ItemStack overwriteChest(LivingEntity instance, EquipmentSlot equipmentSlot) {
        ItemStack originalGet = instance.getEquippedStack(equipmentSlot);
        if (MinecraftClient.getInstance().player == instance) {
            return ClientPlayerChestGetterEvent.EVENT.invoker().onClientPlayerChestGet(originalGet);
        }
        return originalGet;
    }
}
