package online.flowerinsnow.hidearmour.client.listener;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import online.flowerinsnow.hidearmour.client.eci.RenderArmourCallback;

@Environment(EnvType.CLIENT)
public class RenderListener implements RenderArmourCallback.Pre {
    @Override
    public ActionResult preRenderArmour(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel<LivingEntity> model) {
        return entity.equals(MinecraftClient.getInstance().player) ? ActionResult.SUCCESS : ActionResult.PASS;
    }
}
