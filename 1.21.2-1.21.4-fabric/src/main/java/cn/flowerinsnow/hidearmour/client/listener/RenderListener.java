package cn.flowerinsnow.hidearmour.client.listener;

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
import cn.flowerinsnow.hidearmour.client.config.Config;
import cn.flowerinsnow.hidearmour.client.eci.RenderArmourCallback;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class RenderListener implements RenderArmourCallback {
    @Override
    public ActionResult preRenderArmour(ArmorFeatureRenderer<? extends BipedEntityRenderState, ? extends BipedEntityModel<? extends BipedEntityRenderState>, ? extends BipedEntityModel<? extends BipedEntityRenderState>> instance, MatrixStack matrices, VertexConsumerProvider vertexConsumers, BipedEntityRenderState bipedEntityRenderState, ItemStack stack, EquipmentSlot slot, int light, BipedEntityModel<? extends BipedEntityRenderState> armorModel) {
        if (
                Config.ENABLE.getNotNull()
                        && bipedEntityRenderState instanceof PlayerEntityRenderState pers
                        && Optional.ofNullable(MinecraftClient.getInstance().player)
                        .map(ClientPlayerEntity::getId)
                        .map(id -> id == pers.id)
                        .orElse(false)
        ) {
            boolean hide = false;
            switch (slot) {
                case HEAD -> hide = Config.HELMET.getNotNull();
                case CHEST -> hide = Config.CHESTPLATE.getNotNull();
                case LEGS -> hide = Config.LEGGINGS.getNotNull();
                case FEET -> hide = Config.BOOTS.getNotNull();
            }
            return hide ? ActionResult.FAIL : ActionResult.PASS;
        }
        return ActionResult.PASS;
    }
}
