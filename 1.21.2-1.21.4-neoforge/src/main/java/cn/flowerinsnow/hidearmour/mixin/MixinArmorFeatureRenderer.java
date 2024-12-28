package cn.flowerinsnow.hidearmour.mixin;

import cn.flowerinsnow.hidearmour.event.PreRenderOwnArmourEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(HumanoidArmorLayer.class)
@OnlyIn(Dist.CLIENT)
public abstract class MixinArmorFeatureRenderer {
    @Unique
    private boolean hide_armour$ownRendering;

    @Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/HumanoidRenderState;FF)V",
            at = @At("HEAD")
    )
    public void preRender(PoseStack arg0, MultiBufferSource arg1, int arg2, HumanoidRenderState arg3, float arg4, float arg5, CallbackInfo ci) {
        if (arg3 instanceof PlayerRenderState prs &&
                Optional.ofNullable(Minecraft.getInstance().player)
                        .map(LocalPlayer::getId)
                        .map(id -> prs.id == id)
                        .orElse(false)
        ) {
            this.hide_armour$ownRendering = true;
        }
    }

    @Redirect(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/HumanoidRenderState;FF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V"
            )
    )
    public void render(HumanoidArmorLayer<? extends HumanoidRenderState, ? extends HumanoidModel<? extends HumanoidRenderState>, ? extends HumanoidModel<? extends HumanoidRenderState>> instance, PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, EquipmentSlot slot, int light, HumanoidModel<? extends HumanoidRenderState> armorModel) {
        PreRenderOwnArmourEvent event = new PreRenderOwnArmourEvent(instance, matrices, vertexConsumers, stack, slot, light, armorModel);
        if (!this.hide_armour$ownRendering || !NeoForge.EVENT_BUS.post(event).isCanceled()) {
            this.renderArmorPiece(
                    event.getMatrices(),
                    event.getVertexConsumers(),
                    event.getStack(),
                    event.getSlot(),
                    event.getLight(),
                    event.getArmorModel()
            );
        }
    }

    @Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/HumanoidRenderState;FF)V",
            at = @At("RETURN")
    )
    public void postRender(PoseStack arg0, MultiBufferSource arg1, int arg2, HumanoidRenderState arg3, float arg4, float arg5, CallbackInfo ci) {
        this.hide_armour$ownRendering = false;
    }

    @Shadow
    protected abstract void renderArmorPiece(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, EquipmentSlot slot, int light, HumanoidModel<? extends HumanoidRenderState> armorModel);
}
