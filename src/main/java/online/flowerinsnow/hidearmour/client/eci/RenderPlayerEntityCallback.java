package online.flowerinsnow.hidearmour.client.eci;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.ActionResult;

@Environment(EnvType.CLIENT)
public final class RenderPlayerEntityCallback {
    private RenderPlayerEntityCallback() {
    }

    public interface Pre {
        Event<Pre> EVENT = EventFactory.createArrayBacked(Pre.class,
                listeners -> ((abstractClientPlayerEntity, f, g, matrixStack, vertexConsumerProvider, i) -> {
                    for (Pre listener : listeners) {
                        ActionResult result = listener.preRenderPlayer(abstractClientPlayerEntity, f, g, matrixStack, vertexConsumerProvider, i);
                        if (result != ActionResult.PASS) {
                            return result;
                        }
                    }
                    return ActionResult.PASS;
                }));
        /**
         * 返回SUCCESS即取消事件
         */
        ActionResult preRenderPlayer(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i);
    }

    public interface Post {
        Event<Pre> EVENT = EventFactory.createArrayBacked(Pre.class,
                listeners -> ((abstractClientPlayerEntity, f, g, matrixStack, vertexConsumerProvider, i) -> {
                    for (Pre listener : listeners) {
                        ActionResult result = listener.preRenderPlayer(abstractClientPlayerEntity, f, g, matrixStack, vertexConsumerProvider, i);
                        if (result != ActionResult.PASS) {
                            return result;
                        }
                    }
                    return ActionResult.PASS;
                }));

        ActionResult postRenderPlayer(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i);
    }
}
