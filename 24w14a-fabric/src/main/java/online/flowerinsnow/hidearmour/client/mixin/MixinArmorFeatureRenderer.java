package online.flowerinsnow.hidearmour.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import online.flowerinsnow.hidearmour.client.eci.RenderArmourCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
@Environment(EnvType.CLIENT)
public class MixinArmorFeatureRenderer {
    @Inject(
            method = "renderArmor",
            at = @At("HEAD"),
            cancellable = true
    )
    private void preRenderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel<LivingEntity> model, CallbackInfo ci) {
        if (RenderArmourCallback.Pre.EVENT.invoker().preRenderArmour(matrices, vertexConsumers, entity, armorSlot, light, model) == ActionResult.FAIL) {
            ci.cancel();
        }
    }

    @Inject(
            method = "renderArmor",
            at = @At("RETURN")
    )
    private void postRenderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel<LivingEntity> model, CallbackInfo ci) {
        RenderArmourCallback.Post.EVENT.invoker().postRenderArmour(matrices, vertexConsumers, entity, armorSlot, light, model);
    }
}
