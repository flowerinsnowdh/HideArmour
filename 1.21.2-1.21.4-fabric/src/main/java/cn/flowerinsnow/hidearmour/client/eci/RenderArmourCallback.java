package cn.flowerinsnow.hidearmour.client.eci;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

@Environment(EnvType.CLIENT)
public interface RenderArmourCallback {
    /**
     * 返回 FAIL 时取消操作
     */
    Event<RenderArmourCallback> EVENT = EventFactory.createArrayBacked(RenderArmourCallback.class,
            listeners -> ((renderer, matrices, vertexConsumers, bipedEntityRenderState, stack, slot, light, armorSlot) -> {
                for (RenderArmourCallback listener : listeners) {
                    ActionResult result = listener.preRenderArmour(renderer, matrices, vertexConsumers, bipedEntityRenderState, stack, slot, light, armorSlot);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            }));

    ActionResult preRenderArmour(ArmorFeatureRenderer<? extends BipedEntityRenderState, ? extends BipedEntityModel<? extends BipedEntityRenderState>, ? extends BipedEntityModel<? extends BipedEntityRenderState>> instance, MatrixStack matrices, VertexConsumerProvider vertexConsumers, BipedEntityRenderState bipedEntityRenderState, ItemStack stack, EquipmentSlot slot, int light, BipedEntityModel<? extends BipedEntityRenderState> armorModel);
}
