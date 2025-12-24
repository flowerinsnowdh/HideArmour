package cn.flowerinsnow.hidearmour.client.eci;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public interface ClientPlayerBlockHeadGetterEvent {
    Event<ClientPlayerBlockHeadGetterEvent> EVENT = EventFactory.createArrayBacked(ClientPlayerBlockHeadGetterEvent.class, listeners -> (itemModelResolver, output, item) -> {
        for (ClientPlayerBlockHeadGetterEvent listener : listeners) {
            ActionResult interactionResult = listener.onClientPlayerBlockHeadGet(itemModelResolver, output, item);
            if (interactionResult != ActionResult.PASS) {
                return interactionResult;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult onClientPlayerBlockHeadGet(ItemModelManager itemModelResolver, ItemRenderState output, ItemStack item);
}
