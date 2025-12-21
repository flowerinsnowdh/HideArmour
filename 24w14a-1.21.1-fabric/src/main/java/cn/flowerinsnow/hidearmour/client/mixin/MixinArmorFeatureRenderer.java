package cn.flowerinsnow.hidearmour.client.mixin;

import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerArmourGetterEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class MixinArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> {
    @Unique
    private boolean clientPlayer;
    @Unique
    private EquipmentSlot armorSlot;

    @Inject(
            method = "renderArmor",
            at = @At("HEAD")
    )
    private void captureVar3EntityVar4ArmorSlot(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci) {
        this.clientPlayer = MinecraftClient.getInstance().player == entity;
        this.armorSlot = armorSlot;
    }

    @ModifyVariable(
            method = "renderArmor",
            at = @At(
                    value = "STORE",
                    ordinal = 0
            ),
            index = 7
    )
    public ItemStack overwriteEquippedStackGet(ItemStack value) {
        if (this.clientPlayer) {
            return ClientPlayerArmourGetterEvent.EVENT.invoker().onClientPlayerEquipmentGet(this.armorSlot, value);
        }
        return value;
    }
}
