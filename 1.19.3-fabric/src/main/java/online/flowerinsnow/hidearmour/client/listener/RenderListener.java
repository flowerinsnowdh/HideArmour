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
import online.flowerinsnow.hidearmour.client.config.Config;
import online.flowerinsnow.hidearmour.client.eci.RenderArmourCallback;

@Environment(EnvType.CLIENT)
public class RenderListener implements RenderArmourCallback.Pre {
    @Override
    public ActionResult preRenderArmour(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel<LivingEntity> model) {
        if (!entity.equals(MinecraftClient.getInstance().player) || !Config.getConfig().getBooleanValue("enable")) {
            return ActionResult.PASS;
        }
        boolean hide = false;
        switch (armorSlot) {
            case HEAD -> hide = Config.getConfig().getBooleanValue("helmet");
            case CHEST -> hide = Config.getConfig().getBooleanValue("chestplate");
            case LEGS -> hide = Config.getConfig().getBooleanValue("leggings");
            case FEET -> hide = Config.getConfig().getBooleanValue("boots");
        }
        return hide ? ActionResult.SUCCESS : ActionResult.PASS;
    }
}
