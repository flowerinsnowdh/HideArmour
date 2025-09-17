package cn.flowerinsnow.hidearmour.client.listener;

import cn.flowerinsnow.hidearmour.client.HideArmourClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import cn.flowerinsnow.hidearmour.client.eci.RenderArmourCallback;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class RenderListener implements RenderArmourCallback {
    @Override
    public ActionResult preRenderArmour(ArmorFeatureRenderer<? extends BipedEntityRenderState, ? extends BipedEntityModel<? extends BipedEntityRenderState>, ? extends BipedEntityModel<? extends BipedEntityRenderState>> instance, MatrixStack matrices, VertexConsumerProvider vertexConsumers, BipedEntityRenderState bipedEntityRenderState, ItemStack stack, EquipmentSlot slot, int light, BipedEntityModel<? extends BipedEntityRenderState> armorModel) {
        if (
                HideArmourClient.instance().config().enable
                        && bipedEntityRenderState instanceof PlayerEntityRenderState pers
                        && Optional.ofNullable(MinecraftClient.getInstance().player)
                        .map(ClientPlayerEntity::getId)
                        .map(id -> id == pers.id)
                        .orElse(false)
        ) {
            boolean hide = false;
            switch (slot) {
                case HEAD -> hide = HideArmourClient.instance().config().helmet;
                case CHEST -> hide = HideArmourClient.instance().config().chestplate;
                case LEGS -> hide = HideArmourClient.instance().config().leggings;
                case FEET -> hide = HideArmourClient.instance().config().boots;
            }
            return hide ? ActionResult.FAIL : ActionResult.PASS;
        }
        return ActionResult.PASS;
    }
}
