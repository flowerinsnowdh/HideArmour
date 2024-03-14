package online.flowerinsnow.hidearmour.client.eci;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

@Environment(EnvType.CLIENT)
public final class RenderArmourCallback {
    private RenderArmourCallback() {
    }

    /**
     * 返回FAIL即取消事件
     */
    @Environment(EnvType.CLIENT)
    public interface Pre {
        Event<Pre> EVENT = EventFactory.createArrayBacked(Pre.class,
                listeners -> ((matrices, vertexConsumers, entity, armorSlot, light, model) -> {
                    for (Pre listener : listeners) {
                        ActionResult result = listener.preRenderArmour(matrices, vertexConsumers, entity, armorSlot, light, model);
                        if (result != ActionResult.PASS) {
                            return result;
                        }
                    }
                    return ActionResult.PASS;
                }));
        ActionResult preRenderArmour(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel<LivingEntity> model);
    }

    @Environment(EnvType.CLIENT)
    public interface Post {
        Event<Post> EVENT = EventFactory.createArrayBacked(Post.class,
                listeners -> ((matrices, vertexConsumers, entity, armorSlot, light, model) -> {
                    for (Post listener : listeners) {
                        ActionResult result = listener.postRenderArmour(matrices, vertexConsumers, entity, armorSlot, light, model);
                        if (result != ActionResult.PASS) {
                            return result;
                        }
                    }
                    return ActionResult.PASS;
                }));
        ActionResult postRenderArmour(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel<LivingEntity> model);
    }
}
